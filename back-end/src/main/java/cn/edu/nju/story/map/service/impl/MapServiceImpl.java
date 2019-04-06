package cn.edu.nju.story.map.service.impl;

import cn.edu.nju.story.map.constants.ErrorCode;
import cn.edu.nju.story.map.entity.*;
import cn.edu.nju.story.map.exception.DefaultErrorException;
import cn.edu.nju.story.map.repository.*;
import cn.edu.nju.story.map.service.MapService;
import cn.edu.nju.story.map.utils.ListIndexUtils;
import cn.edu.nju.story.map.vo.*;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * MapServiceImpl
 *
 * @author xuan
 * @date 2019-01-31
 */
@Service
@Slf4j
public class MapServiceImpl implements MapService {


    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectServiceImpl projectService;

    @Autowired
    private EpicServiceImpl epicService;

    @Autowired
    private EpicRepository epicRepository;

    @Autowired
    private FeatureServiceImpl featureService;

    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private CardServiceImpl cardService;

    @Autowired
    CardRepository cardRepository;


    @Override
    public boolean moveEpicLocation(Long userId, Long epicId, TargetLocationVO targetLocationVO) {


        // 只有一种移动方式，即epic之间的前后顺序
        EpicEntity epicEntity = epicService.queryEpicAndCheckPrivilege(userId, epicId);

        // 删除project中的序列
        Optional<ProjectEntity> projectEntityOptional = projectRepository.findById(epicEntity.getBelongProjectId());

        if(!projectEntityOptional.isPresent()){
            throw new DefaultErrorException(ErrorCode.PROJECT_NOT_EXISTED);
        }

        ProjectEntity projectEntity = projectEntityOptional.get();

        List<Long> epicIndexList = Objects.isNull(projectEntity.getEpicIndexList()) ? new ArrayList<>() : JSON.parseArray(projectEntity.getEpicIndexList(), Long.class);

        if(Objects.isNull(epicIndexList)){
            epicIndexList = new ArrayList<>();
        }

        ListIndexUtils.adjustIndexList(epicIndexList, targetLocationVO.getPrecursor(), epicEntity.getId());

        projectEntity.setEpicIndexList(JSON.toJSONString(epicIndexList));
        projectRepository.save(projectEntity);

        return true;
    }

    @Override
    public boolean moveFeatureLocation(Long userId, Long featureId, TargetLocationVO targetLocationVO) {


        // 1. epic内的feature之间的前后顺序
        // 2. 移动到其他epic中
        FeatureEntity featureEntity = featureService.queryFeatureAndCheckPrivilege(userId, featureId);


        if(Objects.isNull(targetLocationVO.getTargetEpicId()) || Objects.equals(targetLocationVO.getTargetEpicId(), featureEntity.getBelongEpicId())){
            // epic内的feature之间的移动

            Optional<EpicEntity> epicEntityOptional = epicRepository.findById(targetLocationVO.getTargetEpicId());

            if(!epicEntityOptional.isPresent()){
                throw new DefaultErrorException(ErrorCode.EPIC_NOT_EXISTED);
            }

            EpicEntity epicEntity = epicEntityOptional.get();

            List<Long> featureIndexList = Objects.isNull(epicEntity.getFeatureIndexList()) ? new ArrayList<>() : JSON.parseArray(epicEntity.getFeatureIndexList(), Long.class);

            if(Objects.isNull(featureIndexList)){
                featureIndexList = new ArrayList<>();
            }

            featureIndexList.remove(featureEntity.getId());

            ListIndexUtils.adjustIndexList(featureIndexList, targetLocationVO.getPrecursor(), featureEntity.getId());

            epicEntity.setFeatureIndexList(JSON.toJSONString(featureIndexList));
            epicRepository.save(epicEntity);

        }else if(Objects.nonNull(targetLocationVO.getTargetEpicId())){
            // 移动到其他的epic中

            Map<Long, EpicEntity> epicEntityMap = StreamSupport.stream(epicRepository.findAllById(Arrays.asList(targetLocationVO.getTargetEpicId(), featureEntity.getBelongEpicId())).spliterator(), true).collect(Collectors.toMap(EpicEntity::getId, i -> i));

            if(epicEntityMap.values().size() < 2){
                throw new DefaultErrorException(ErrorCode.EPIC_NOT_EXISTED);
            }

            EpicEntity beforeEpic = epicEntityMap.get(featureEntity.getBelongEpicId());
            EpicEntity targetEpic = epicEntityMap.get(targetLocationVO.getTargetEpicId());

            // 删除原有记录
            List<Long> beforeFeatureIndexList = Objects.isNull(beforeEpic.getFeatureIndexList()) ? new ArrayList<>() : JSON.parseArray(beforeEpic.getFeatureIndexList(), Long.class);
            if(Objects.isNull(beforeFeatureIndexList)){
                beforeFeatureIndexList = new ArrayList<>();
            }
            beforeFeatureIndexList.remove(featureEntity.getId());
            beforeEpic.setFeatureIndexList(JSON.toJSONString(beforeFeatureIndexList));

            // 添加新的记录
            List<Long> targetFeatureIndexList = Objects.isNull(targetEpic.getFeatureIndexList()) ? new ArrayList<>() : JSON.parseArray(targetEpic.getFeatureIndexList(), Long.class);
            if(Objects.isNull(targetFeatureIndexList)){
                targetFeatureIndexList = new ArrayList<>();
            }
            ListIndexUtils.adjustIndexList(targetFeatureIndexList, targetLocationVO.getPrecursor(), featureEntity.getId());
            targetEpic.setFeatureIndexList(JSON.toJSONString(targetFeatureIndexList));

            epicRepository.saveAll(Arrays.asList(beforeEpic, targetEpic));

        }

        return true;
    }

    @Override
    public boolean moveCardLocation(Long userId, Long cardId, TargetLocationVO targetLocationVO) {

        // 1. 移动到不同的feature
        // 2. 移动到不同的group

        CardEntity cardEntity = cardService.queryCardAndCheckPrivilege(userId, cardId);


        if(Objects.nonNull(targetLocationVO.getTargetFeatureId())){
            // 移动到不同的feature
            Optional<FeatureEntity> featureEntityOptional = featureRepository.findById(targetLocationVO.getTargetFeatureId());

            if(!featureEntityOptional.isPresent()){
                throw new DefaultErrorException(ErrorCode.FATHER_NOT_EXISTED);
            }

            FeatureEntity featureEntity = featureEntityOptional.get();

            if(! Objects.equals(featureEntity.getBelongProjectId(), cardEntity.getBelongProjectId())){
                throw new DefaultErrorException(ErrorCode.FORBIDDEN);
            }

            cardEntity.setBelongFeatureId(targetLocationVO.getTargetFeatureId());
        }

        if(Objects.nonNull(targetLocationVO.getTargetGroupId())){
            // 移动到不同的group

            Optional<GroupEntity> groupEntityOptional = groupRepository.findById(targetLocationVO.getTargetGroupId());

            if(!groupEntityOptional.isPresent()){
                throw new DefaultErrorException(ErrorCode.FATHER_NOT_EXISTED);
            }

            GroupEntity groupEntity = groupEntityOptional.get();

            if(!Objects.equals(groupEntity.getBelongProjectId(), cardEntity.getBelongGroupId())){
                throw new DefaultErrorException(ErrorCode.FORBIDDEN);
            }

            cardEntity.setBelongGroupId(targetLocationVO.getTargetGroupId());

        }

        if(Objects.isNull(targetLocationVO.getTargetFeatureId()) && Objects.isNull(targetLocationVO.getTargetGroupId())){
            cardEntity.setBelongProjectId(null);
            cardEntity.setBelongFeatureId(null);
        }

        cardRepository.save(cardEntity);

        return true;
    }

    @Override
    public MapDetailsVO queryMapDetails(Long userId, Long projectId) {

        ProjectEntity projectEntity = projectService.queryProjectAndCheckPrivilege(userId, projectId);

        List<Long> epicIndexList = Objects.isNull(projectEntity.getEpicIndexList()) ? new ArrayList<>() : JSON.parseArray(projectEntity.getEpicIndexList(), Long.class);

        List<Long> groupIndexList = Objects.isNull(projectEntity.getGroupIndexList()) ? new ArrayList<>() : JSON.parseArray(projectEntity.getGroupIndexList(), Long.class);

        List<EpicEntity> epicEntities = CollectionUtils.isEmpty(epicIndexList) ? new ArrayList<>() : StreamSupport.stream(epicRepository.findAllById(epicIndexList).spliterator(), true).collect(Collectors.toList());

        List<GroupEntity> groupEntities = CollectionUtils.isEmpty(groupIndexList) ? new ArrayList<>() : StreamSupport.stream(groupRepository.findAllById(groupIndexList).spliterator(), true).collect(Collectors.toList());


        if(CollectionUtils.isEmpty(epicIndexList)){
            return MapDetailsVO.builder()
                    .epicList(new ArrayList<>())
                    .groupList(groupEntities.stream().map(GroupVO::new).collect(Collectors.toList()))
                    .build();
        }

        epicEntities.sort((o1, o2) -> {
            int index1 = epicIndexList.indexOf(o1.getId());
            int index2 = epicIndexList.indexOf(o2.getId());
            return index1 - index2;
        });

        groupEntities.sort((o1, o2) -> {
            int index1 = groupIndexList.indexOf(o1.getId());
            int index2 = groupIndexList.indexOf(o2.getId());
            return index1 - index2;
        });




        List<Long> featureIds = new ArrayList<>();

        epicEntities.forEach( i -> {
            List<Long> tmpFeatureIds = Objects.isNull(i.getFeatureIndexList()) ? new ArrayList<>() : JSON.parseArray(i.getFeatureIndexList(), Long.class);

            if(CollectionUtils.isEmpty(tmpFeatureIds)){
                return;
            }
            featureIds.addAll(tmpFeatureIds);

        });

        if(CollectionUtils.isEmpty(featureIds)){
            return MapDetailsVO.builder()
                    .epicList(epicEntities.stream().map(EpicVO::new).collect(Collectors.toList()))
                    .groupList(groupEntities.stream().map(GroupVO::new).collect(Collectors.toList()))
                    .build();

        }
        Map<Long, FeatureEntity> featureEntityMap = StreamSupport.stream(featureRepository.findAllById(featureIds).spliterator(), true).collect(Collectors.toMap(FeatureEntity::getId, i -> i));

        Map<Long, List<CardVO>> cardVOGroupByFeatureId = cardRepository.findByBelongProjectIdAndBelongFeatureIdIsNotNullAndBelongGroupIdIsNotNull(projectId).stream().map(CardVO::new).collect(Collectors.groupingBy(CardVO::getBelongFeatureId));


        return MapDetailsVO.builder()
                .groupList(groupEntities.stream().map(GroupVO::new).collect(Collectors.toList()))
                .epicList(epicEntities.stream().map(i -> {

                    EpicVO epicVO =  EpicVO.builder()
                            .id(i.getId())
                            .name(i.getName())
                            .build();

                    List<Long> tmpFeatureIds = Objects.isNull(i.getFeatureIndexList()) ? new ArrayList<>() : JSON.parseArray(i.getFeatureIndexList(), Long.class);

                    List<FeatureVO> featureVOS = CollectionUtils.isEmpty(tmpFeatureIds) ? new ArrayList<>() :
                            tmpFeatureIds.stream().map( j -> {

                                FeatureEntity feature = featureEntityMap.get(j);
                                if(Objects.isNull(feature)){
                                    return null;
                                }

                                List<GroupVO> groupVOList = new ArrayList<>();

                                FeatureVO featureVO = FeatureVO.builder()
                                        .id(feature.getId())
                                        .name(feature.getName())
                                        .groupList(groupVOList)
                                        .build();

                                List<CardVO> cardVOS = cardVOGroupByFeatureId.get(j);

                                Map<Long, List<CardVO>> cardVOGroupByGroupId = CollectionUtils.isEmpty(cardVOS) ? new HashMap<>() :
                                        cardVOS.stream().collect(Collectors.groupingBy(CardVO::getBelongGroupId));

                                for(GroupEntity group : groupEntities){

                                    List<CardVO> cards = cardVOGroupByGroupId.get(group.getId());
                                    GroupVO groupVO = GroupVO.builder()
                                            .id(group.getId())
                                            .name(group.getName())
                                            .cardList(Objects.isNull(cards) ? new ArrayList<>() : cards)
                                            .build();
                                    groupVOList.add(groupVO);
                                }


                                return featureVO;
                            }).filter(Objects::nonNull).collect(Collectors.toList());

                    epicVO.setFeatureList(featureVOS);
                    return epicVO;

                }).collect(Collectors.toList()))
                .build();
    }




}
