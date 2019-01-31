package cn.edu.nju.story.map.service.impl;

import cn.edu.nju.story.map.constants.ErrorCode;
import cn.edu.nju.story.map.entity.*;
import cn.edu.nju.story.map.exception.DefaultErrorException;
import cn.edu.nju.story.map.repository.*;
import cn.edu.nju.story.map.service.MapService;
import cn.edu.nju.story.map.utils.ListIndexUtils;
import cn.edu.nju.story.map.vo.MapDetailsVO;
import cn.edu.nju.story.map.vo.TargetLocationVO;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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





        }else if(Objects.nonNull(targetLocationVO.getTargetEpicId())){
            // 移动到其他的epic中
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


        if(CollectionUtils.isEmpty(epicIndexList)){
            return MapDetailsVO.builder()
                    .epicList(new ArrayList<>())
                    .build();
        }

        List<EpicEntity> epicEntities = StreamSupport.stream(epicRepository.findAllById(epicIndexList).spliterator(), true).collect(Collectors.toList());

//        List<Long> featureId



        return null;
    }




}
