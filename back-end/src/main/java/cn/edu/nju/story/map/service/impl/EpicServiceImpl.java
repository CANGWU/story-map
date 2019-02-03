package cn.edu.nju.story.map.service.impl;

import cn.edu.nju.story.map.constants.ErrorCode;
import cn.edu.nju.story.map.entity.EpicEntity;
import cn.edu.nju.story.map.entity.ProjectEntity;
import cn.edu.nju.story.map.exception.DefaultErrorException;
import cn.edu.nju.story.map.repository.EpicRepository;
import cn.edu.nju.story.map.repository.ProjectRepository;
import cn.edu.nju.story.map.service.EpicService;
import cn.edu.nju.story.map.service.PermissionService;
import cn.edu.nju.story.map.utils.BeanUtils;
import cn.edu.nju.story.map.utils.ListIndexUtils;
import cn.edu.nju.story.map.vo.CreateEpicVO;
import cn.edu.nju.story.map.vo.EpicDetailsVO;
import cn.edu.nju.story.map.vo.ModifyEpicVO;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * EpicServiceImpl
 *
 * @author xuan
 * @date 2019-01-30
 */
@Service
@Slf4j
public class EpicServiceImpl implements EpicService {

    @Autowired
    PermissionService permissionService;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    EpicRepository epicRepository;

    @Autowired
    ProjectServiceImpl projectService;


    @Override
    @Transactional
    public EpicDetailsVO createEpic(Long userId, Long projectId, CreateEpicVO createEpicVO) {

        ProjectEntity projectEntity = projectService.queryProjectAndCheckPrivilege(userId, projectId);

        List<Long> epicIndexList = Objects.isNull(projectEntity.getEpicIndexList()) ? new ArrayList<>() : JSON.parseArray(projectEntity.getEpicIndexList(), Long.class);

        if(Objects.isNull(epicIndexList)){
            epicIndexList = new ArrayList<>();
        }

        // 添加epic
        EpicEntity epicEntity = EpicEntity.builder()
                .name(createEpicVO.getName())
                .belongProjectId(projectId)
                .featureIndexList(JSON.toJSONString(new ArrayList<>()))
                .build();


        epicEntity = epicRepository.save(epicEntity);
        ListIndexUtils.adjustIndexList(epicIndexList, createEpicVO.getPrecursor(), epicEntity.getId());

        projectEntity.setEpicIndexList(JSON.toJSONString(epicIndexList));
        projectRepository.save(projectEntity);

        return new EpicDetailsVO(epicEntity);
    }

    @Override
    @Transactional
    public boolean deleteEpicById(Long userId, Long epicId) {

        EpicEntity epicEntity = queryEpicAndCheckPrivilege(userId, epicId);

        // epic下是否存在feature
        if(Objects.nonNull(epicEntity.getFeatureIndexList()) &&
                !CollectionUtils.isEmpty(JSON.parseArray(epicEntity.getFeatureIndexList(), Long.class))){
            throw new DefaultErrorException(ErrorCode.EPIC_CAN_NOT_DELETED);
        }

        // 删除epic
        epicRepository.deleteById(epicId);

        // 删除project中的序列
        Optional<ProjectEntity> projectEntityOptional = projectRepository.findById(epicEntity.getBelongProjectId());
        if(projectEntityOptional.isPresent()){
            ProjectEntity projectEntity = projectEntityOptional.get();
            List<Long> epicIndexList = Objects.isNull(projectEntity.getEpicIndexList()) ? new ArrayList<>() : JSON.parseArray(projectEntity.getEpicIndexList(), Long.class);

            if(!CollectionUtils.isEmpty(epicIndexList)){
                if(epicIndexList.remove(epicId)){
                    projectEntity.setEpicIndexList(JSON.toJSONString(epicIndexList));
                    projectRepository.save(projectEntity);
                }

            }

        }
        return true;
    }

    @Override
    public EpicDetailsVO queryEpicDetailsById(Long userId, Long epicId) {

        EpicEntity epicEntity = queryEpicAndCheckPrivilege(userId, epicId);
        return new EpicDetailsVO(epicEntity);
    }

    @Override
    public boolean modifyEpic(Long userId, Long epicId, ModifyEpicVO modifyEpicVO) {


        EpicEntity epicEntity = queryEpicAndCheckPrivilege(userId, epicId);

        BeanUtils.copyPropertiesSkipNull(modifyEpicVO, epicEntity);

        epicRepository.save(epicEntity);

        return true;
    }


    EpicEntity queryEpicAndCheckPrivilege(Long userId, Long epicId){

        Optional<EpicEntity> epicEntityOptional = epicRepository.findById(epicId);

        if(!epicEntityOptional.isPresent()){
            throw new DefaultErrorException(ErrorCode.EPIC_NOT_EXISTED);
        }

        EpicEntity epicEntity = epicEntityOptional.get();

        //是否具备操作权限
        if(!permissionService.hasMasterPrivilege(userId, epicEntity.getBelongProjectId())){
            throw new DefaultErrorException(ErrorCode.FORBIDDEN);
        }

        return epicEntity;
    }

}
