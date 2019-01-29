package cn.edu.nju.story.map.service.impl;

import cn.edu.nju.story.map.constants.PrivilegeGroup;
import cn.edu.nju.story.map.constants.ProjectMemberState;
import cn.edu.nju.story.map.repository.ProjectMemberRepository;
import cn.edu.nju.story.map.repository.ProjectRepository;
import cn.edu.nju.story.map.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * PermissionServiceImpl
 *
 * @author xuan
 * @date 2019-01-29
 */
@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {


    @Autowired
    ProjectMemberRepository projectMemberRepository;
    @Autowired
    ProjectRepository projectRepository;

    @Override
    public boolean hasMasterPrivilege(Long userId, Long projectId) {
        return projectMemberRepository.existsByProjectIdAndUserIdAndStateAndBelongPrivilegeGroup(projectId,
                userId, ProjectMemberState.OK.getState(), PrivilegeGroup.MASTER.getLevel());
    }

    @Override
    public boolean hasSimplePrivilege(Long userId, Long projectId) {
        return projectMemberRepository.existsByProjectIdAndUserId(projectId, userId);
    }

    @Override
    public boolean hasCreatorPrivilege(Long userId, Long projectId) {
        return projectRepository.existsByIdAndCreatorUserId(projectId, userId);
    }


}
