package cn.edu.nju.story.map.service.impl;

import cn.edu.nju.story.map.constants.ErrorCode;
import cn.edu.nju.story.map.entity.GroupEntity;
import cn.edu.nju.story.map.entity.ProjectEntity;
import cn.edu.nju.story.map.exception.DefaultErrorException;
import cn.edu.nju.story.map.repository.CardRepository;
import cn.edu.nju.story.map.repository.GroupRepository;
import cn.edu.nju.story.map.repository.ProjectRepository;
import cn.edu.nju.story.map.service.GroupService;
import cn.edu.nju.story.map.service.PermissionService;
import cn.edu.nju.story.map.utils.BeanUtils;
import cn.edu.nju.story.map.utils.ListIndexUtils;
import cn.edu.nju.story.map.vo.CreateGroupVO;
import cn.edu.nju.story.map.vo.GroupDetailsVO;
import cn.edu.nju.story.map.vo.ModifyGroupVO;
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
 * GroupServiceImpl
 *
 * @author xuan
 * @date 2019-01-31
 */
@Slf4j
@Service
public class GroupServiceImpl implements GroupService {


    @Autowired
    PermissionService permissionService;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    ProjectServiceImpl projectService;

    @Autowired
    CardRepository cardRepository;


    @Override
    @Transactional
    public GroupDetailsVO createGroup(long userId, Long projectId, CreateGroupVO createGroupVO) {

        ProjectEntity projectEntity = projectService.queryProjectAndCheckPrivilege(userId, projectId);

        List<Long> groupIndexList = Objects.isNull(projectEntity.getGroupIndexList()) ? new ArrayList<>() : JSON.parseArray(projectEntity.getGroupIndexList(), Long.class);

        if(Objects.isNull(groupIndexList)){
            groupIndexList = new ArrayList<>();
        }

        // 添加group
        GroupEntity groupEntity = GroupEntity.builder()
                .name(createGroupVO.getName())
                .belongProjectId(projectId)
                .build();


        groupEntity = groupRepository.save(groupEntity);
        ListIndexUtils.adjustIndexList(groupIndexList, createGroupVO.getPrecursor(), groupEntity.getId());

        projectEntity.setGroupIndexList(JSON.toJSONString(groupIndexList));
        projectRepository.save(projectEntity);

        return new GroupDetailsVO(groupEntity);

    }

    @Override
    @Transactional
    public boolean deleteGroupById(long userId, Long groupId) {
        GroupEntity groupEntity = queryGroupAndCheckPrivilege(userId, groupId);

        // group下是否存在card
        if(cardRepository.existsByBelongGroupId(groupId)){
            throw new DefaultErrorException(ErrorCode.GROUP_CAN_NOT_DELETED);
        }

        // 删除group
        groupRepository.deleteById(groupId);

        // 删除project中的序列
        Optional<ProjectEntity> projectEntityOptional = projectRepository.findById(groupEntity.getBelongProjectId());
        if(projectEntityOptional.isPresent()){
            ProjectEntity projectEntity = projectEntityOptional.get();
            List<Long> groupIndexList = Objects.isNull(projectEntity.getGroupIndexList()) ? new ArrayList<>() : JSON.parseArray(projectEntity.getGroupIndexList(), Long.class);

            if(!CollectionUtils.isEmpty(groupIndexList)){
                if(groupIndexList.remove(groupId)){
                    projectEntity.setGroupIndexList(JSON.toJSONString(groupIndexList));
                    projectRepository.save(projectEntity);
                }

            }

        }
        return true;
    }

    @Override
    public GroupDetailsVO queryGroupDetailsById(long userId, Long groupId) {

        GroupEntity groupEntity = queryGroupAndCheckPrivilege(userId, groupId);
        return new GroupDetailsVO(groupEntity);
    }

    @Override
    public boolean modifyGroup(long userId, Long groupId, ModifyGroupVO modifyGroupVO) {

        GroupEntity groupEntity = queryGroupAndCheckPrivilege(userId, groupId);

        BeanUtils.copyPropertiesSkipNull(modifyGroupVO, groupEntity);

        groupRepository.save(groupEntity);

        return true;
    }


    private GroupEntity queryGroupAndCheckPrivilege(Long userId, Long groupId){

        Optional<GroupEntity> groupEntityOptional = groupRepository.findById(groupId);

        if(!groupEntityOptional.isPresent()){
            throw new DefaultErrorException(ErrorCode.GROUP_NOT_EXISTED);
        }

        GroupEntity groupEntity = groupEntityOptional.get();

        //是否具备操作权限
        if(!permissionService.hasMasterPrivilege(userId, groupEntity.getBelongProjectId())){
            throw new DefaultErrorException(ErrorCode.FORBIDDEN);
        }

        return groupEntity;
    }
}
