package cn.edu.nju.story.map.service.impl;

import cn.edu.nju.story.map.constants.ErrorCode;
import cn.edu.nju.story.map.constants.PrivilegeGroup;
import cn.edu.nju.story.map.constants.ProjectMemberState;
import cn.edu.nju.story.map.entity.ProjectEntity;
import cn.edu.nju.story.map.entity.ProjectMemberEntity;
import cn.edu.nju.story.map.exception.DefaultErrorException;
import cn.edu.nju.story.map.repository.ProjectMemberRepository;
import cn.edu.nju.story.map.repository.ProjectRepository;
import cn.edu.nju.story.map.repository.UserRepository;
import cn.edu.nju.story.map.service.PermissionService;
import cn.edu.nju.story.map.service.ProjectMemberService;
import cn.edu.nju.story.map.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * ProjectMemberServiceImpl
 *
 * @author xuan
 * @date 2019-01-26
 */
@Service
@Slf4j
public class ProjectMemberServiceImpl implements ProjectMemberService {

    @Autowired
    ProjectMemberRepository projectMemberRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PermissionService permissionService;

    @Override
    public boolean agreeInvitation(Long userId, Long invitationId) {


        // 是否存在该邀请
        Optional<ProjectMemberEntity> projectMemberEntityOptional =
                projectMemberRepository.findByIdAndUserId(invitationId, userId);

        if(!projectMemberEntityOptional.isPresent()){
            throw new DefaultErrorException(ErrorCode.INVALID_INVITATION);
        }

        ProjectMemberEntity projectMemberEntity = projectMemberEntityOptional.get();

        // 修改项目成员状态为正常
        if(Objects.equals(projectMemberEntity.getState(), ProjectMemberState.INVITING.getState())){
            projectMemberEntity.setState(ProjectMemberState.OK.getState());
            projectMemberRepository.save(projectMemberEntity);
        }

        return true;
    }

    @Override
    public boolean rejectInvitation(Long userId, Long invitationId) {

        // 是否存在该邀请
        Optional<ProjectMemberEntity> projectMemberEntityOptional =
                projectMemberRepository.findByIdAndUserId(invitationId, userId);

        if(projectMemberEntityOptional.isPresent()){
            ProjectMemberEntity projectMemberEntity = projectMemberEntityOptional.get();
            // 删除项目成员信息
            if(Objects.equals(projectMemberEntity.getState(), ProjectMemberState.INVITING.getState())){
                projectMemberRepository.deleteById(invitationId);
                return true;
            }
        }
        throw new DefaultErrorException(ErrorCode.INVALID_INVITATION);
    }

    @Override
    public Page<ProjectInvitationVO> queryMyProjectInvitation(Long userId, PageableVO pageable) {


        Page<ProjectMemberEntity> projectMemberEntities = projectMemberRepository
                .findByUserIdAndStateOrderByCreateTimeDesc(userId, ProjectMemberState.INVITING.getState(), PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));

        if(projectMemberEntities.isEmpty()){
            return new PageImpl<>(new ArrayList<>(), projectMemberEntities.getPageable(), projectMemberEntities.getTotalElements());
        }else {

            Map<Long, ProjectEntity> projectEntityMap = StreamSupport.stream(projectRepository.findAllById(projectMemberEntities.map(ProjectMemberEntity::getProjectId).getContent()).spliterator(), false)
            .collect(Collectors.toMap(ProjectEntity::getId, i->i));

            return projectMemberEntities.map( i -> new ProjectInvitationVO(i.getId(), projectEntityMap.getOrDefault(i.getProjectId(), new ProjectEntity()).getName()));

        }

    }


    @Override
    public boolean inviteProjectMember(Long userId, Long projectId, List<InviteProjectMemberVO> newMemberList) {

        if(CollectionUtils.isEmpty(newMemberList)){
            return false;
        }

        // 是否具备足够的权限
        if(!permissionService.hasCreatorPrivilege(userId, projectId) && !permissionService.hasMasterPrivilege(userId, projectId)){
            throw new DefaultErrorException(ErrorCode.FORBIDDEN);
        }

        newMemberList = new ArrayList<>(newMemberList.stream().collect(Collectors.toMap(InviteProjectMemberVO::getUserId, i -> i, (v1, v2) -> {
            if(v1.getPrivilegeGroup() > v2.getPrivilegeGroup()){
                return v1;
            }else {
                return v2;
            }
        })).values());


        List<ProjectMemberEntity> existedProjectMembers = projectMemberRepository.findByProjectIdAndUserIdIn(projectId, newMemberList.stream().map(InviteProjectMemberVO::getUserId).collect(Collectors.toList()));
        Map<Long, ProjectMemberEntity> projectMemberEntityMap = existedProjectMembers.stream().collect(Collectors.toMap(ProjectMemberEntity::getUserId, i -> i));

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        List<ProjectMemberEntity> projectMemberEntities = newMemberList.stream().filter(
                i -> !projectMemberEntityMap.containsKey(i.getUserId()) && Objects.nonNull(PrivilegeGroup.getInstance(i.getPrivilegeGroup()))
        ).map( i -> ProjectMemberEntity.builder()
                .projectId(projectId)
                .userId(i.getUserId())
                .belongPrivilegeGroup(i.getPrivilegeGroup())
                .state(Objects.equals(userId, i.getUserId()) ? ProjectMemberState.OK.getState() :ProjectMemberState.INVITING.getState())
                .createTime(currentTime)
                .build()).collect(Collectors.toList());

        if(!CollectionUtils.isEmpty(projectMemberEntities)){
            projectMemberRepository.saveAll(projectMemberEntities);
        }

        return true;
    }

    @Override
    public ProjectMembersVO queryProjectMembers(Long userId, Long projectId) {

        // 是否具备足够的权限
        if(!permissionService.hasSimplePrivilege(userId, projectId)){
            throw new DefaultErrorException(ErrorCode.FORBIDDEN);
        }

        List<ProjectMemberEntity> memberEntities = projectMemberRepository.findByProjectIdAndState(projectId, ProjectMemberState.OK.getState());

        if(CollectionUtils.isEmpty(memberEntities)){
            return ProjectMembersVO.builder()
                    .masterMembers(new ArrayList<>())
                    .slaveMembers(new ArrayList<>())
                    .build();
        }

        Map<Long, UserVO> userVOMap = StreamSupport.stream(userRepository.findAllById(memberEntities.stream().map(ProjectMemberEntity::getUserId).collect(Collectors.toList())).spliterator(), true).map(UserVO::new).collect(Collectors.toMap(UserVO::getId, i -> i));

        List<UserVO> master = memberEntities.stream().filter( i -> Objects.equals(i.getBelongPrivilegeGroup(), PrivilegeGroup.MASTER.getLevel()))
                .map( i -> userVOMap.get(i.getUserId())).collect(Collectors.toList());

        List<UserVO> slave = memberEntities.stream().filter( i -> Objects.equals(i.getBelongPrivilegeGroup(), PrivilegeGroup.SLAVE.getLevel()))
                .map( i -> userVOMap.get(i.getUserId())).collect(Collectors.toList());

        return ProjectMembersVO.builder()
                .masterMembers(master)
                .slaveMembers(slave)
                .build();
    }

    @Override
    public List<UserVO> queryInvitingProjectMembers(Long userId, Long projectId) {
        // 是否具备足够的权限
        if(!permissionService.hasSimplePrivilege(userId, projectId)){
            throw new DefaultErrorException(ErrorCode.FORBIDDEN);
        }

        List<ProjectMemberEntity> memberEntities = projectMemberRepository.findByProjectIdAndState(projectId, ProjectMemberState.INVITING.getState());

        if(CollectionUtils.isEmpty(memberEntities)){
            return new ArrayList<>();
        }

        Map<Long, UserVO> userVOMap = StreamSupport.stream(userRepository.findAllById(memberEntities.stream().map(ProjectMemberEntity::getUserId).collect(Collectors.toList())).spliterator(), true).map(UserVO::new).collect(Collectors.toMap(UserVO::getId, i -> i));

        return memberEntities.stream().map( i -> userVOMap.get(i.getUserId())).collect(Collectors.toList());

    }
}
