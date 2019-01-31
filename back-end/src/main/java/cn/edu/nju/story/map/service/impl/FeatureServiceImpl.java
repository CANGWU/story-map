package cn.edu.nju.story.map.service.impl;

import cn.edu.nju.story.map.constants.ErrorCode;
import cn.edu.nju.story.map.entity.EpicEntity;
import cn.edu.nju.story.map.entity.FeatureEntity;
import cn.edu.nju.story.map.exception.DefaultErrorException;
import cn.edu.nju.story.map.repository.CardRepository;
import cn.edu.nju.story.map.repository.EpicRepository;
import cn.edu.nju.story.map.repository.FeatureRepository;
import cn.edu.nju.story.map.service.FeatureService;
import cn.edu.nju.story.map.service.PermissionService;
import cn.edu.nju.story.map.utils.BeanUtils;
import cn.edu.nju.story.map.utils.ListIndexUtils;
import cn.edu.nju.story.map.vo.CreateFeatureVO;
import cn.edu.nju.story.map.vo.FeatureDetailsVO;
import cn.edu.nju.story.map.vo.ModifyFeatureVO;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * FeatureServiceImpl
 *
 * @author xuan
 * @date 2019-01-31
 */
@Service
@Slf4j
public class FeatureServiceImpl implements FeatureService {

    @Autowired
    PermissionService permissionService;

    @Autowired
    FeatureRepository featureRepository;

    @Autowired
    EpicRepository epicRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    EpicServiceImpl epicService;

    @Override
    @Transactional
    public FeatureDetailsVO createFeature(Long userId, Long epicId, CreateFeatureVO createFeatureVO) {

        EpicEntity epicEntity = epicService.queryEpicAndCheckPrivilege(userId, epicId);

        FeatureEntity featureEntity = FeatureEntity.builder()
                .name(createFeatureVO.getName())
                .belongProjectId(epicEntity.getBelongProjectId())
                .belongEpicId(epicId)
                .build();


        List<Long> featureIndexList = Objects.isNull(epicEntity.getFeatureIndexList()) ? new ArrayList<>() : JSON.parseArray(epicEntity.getFeatureIndexList(), Long.class);

        if(Objects.isNull(featureIndexList)){
            featureIndexList = new ArrayList<>();
        }

        featureEntity = featureRepository.save(featureEntity);
        ListIndexUtils.adjustIndexList(featureIndexList, createFeatureVO.getPrecursor(), featureEntity.getId());

        epicEntity.setFeatureIndexList(JSON.toJSONString(featureIndexList));
        epicRepository.save(epicEntity);

        return new FeatureDetailsVO(featureEntity);
    }

    @Override
    @Transactional
    public boolean deleteFeatureById(Long userId, Long featureId) {

        FeatureEntity featureEntity = queryFeatureAndCheckPrivilege(userId, featureId);

        // feature下是否存在card
        if(cardRepository.existsByBelongFeatureId(featureId)){
            throw new DefaultErrorException(ErrorCode.FEATURE_CAN_NOT_DELETED);
        }

        // 删除featureId
        featureRepository.deleteById(featureId);

        // 删除epic中的序列
        Optional<EpicEntity> epicEntityOptional = epicRepository.findById(featureEntity.getBelongEpicId());
        if(epicEntityOptional.isPresent()){
            EpicEntity epicEntity = epicEntityOptional.get();
            List<Long> featureIndexList = Objects.isNull(epicEntity.getFeatureIndexList()) ? new ArrayList<>() : JSON.parseArray(epicEntity.getFeatureIndexList(), Long.class);

            if(!CollectionUtils.isEmpty(featureIndexList)){
                if(featureIndexList.remove(featureId)){
                    epicEntity.setFeatureIndexList(JSON.toJSONString(featureIndexList));
                    epicRepository.save(epicEntity);
                }

            }

        }

        return true;
    }

    @Override
    public FeatureDetailsVO queryFeatureDetailsById(Long userId, Long featureId) {

        FeatureEntity featureEntity = queryFeatureAndCheckPrivilege(userId, featureId);

        return new FeatureDetailsVO(featureEntity);
    }

    @Override
    public boolean modifyFeature(Long userId, Long featureId, ModifyFeatureVO modifyFeatureVO) {

        FeatureEntity featureEntity = queryFeatureAndCheckPrivilege(userId, featureId);

        BeanUtils.copyPropertiesSkipNull(modifyFeatureVO, featureEntity);

        featureRepository.save(featureEntity);

        return true;
    }


    FeatureEntity queryFeatureAndCheckPrivilege(Long userId, Long featureId){

        Optional<FeatureEntity> featureEntityOptional = featureRepository.findById(featureId);

        if(!featureEntityOptional.isPresent()){
            throw new DefaultErrorException(ErrorCode.FEATURE_NOT_EXISTED);
        }

        FeatureEntity featureEntity = featureEntityOptional.get();

        //是否具备操作权限
        if(!permissionService.hasMasterPrivilege(userId, featureEntity.getBelongProjectId())){
            throw new DefaultErrorException(ErrorCode.FORBIDDEN);
        }

        return featureEntity;
    }
}
