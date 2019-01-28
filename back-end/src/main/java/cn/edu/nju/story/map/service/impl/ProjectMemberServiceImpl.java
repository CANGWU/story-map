package cn.edu.nju.story.map.service.impl;

import cn.edu.nju.story.map.constants.ErrorCode;
import cn.edu.nju.story.map.constants.PrivilegeGroup;
import cn.edu.nju.story.map.constants.ProjectMemberState;
import cn.edu.nju.story.map.entity.ProjectEntity;
import cn.edu.nju.story.map.entity.ProjectMemberEntity;
import cn.edu.nju.story.map.exception.DefaultErrorException;
import cn.edu.nju.story.map.repository.ProjectMemberRepository;
import cn.edu.nju.story.map.repository.ProjectRepository;
import cn.edu.nju.story.map.service.ProjectMemberService;
import cn.edu.nju.story.map.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
            return new PageImpl<>(new ArrayList<>(), projectMemberEntities.getPageable(), projectMemberEntities.getSize());
        }else {

            Map<Long, ProjectEntity> projectEntityMap = StreamSupport.stream(projectRepository.findAllById(projectMemberEntities.map(ProjectMemberEntity::getProjectId)).spliterator(), true)
            .collect(Collectors.toMap(ProjectEntity::getId, i->i));

            return projectMemberEntities.map( i -> new ProjectInvitationVO(i.getId(), projectEntityMap.getOrDefault(i.getProjectId(), new ProjectEntity()).getName()));

        }

    }


    @Override
    public boolean inviteProjectMember(Long userId, Long projectId, List<InviteProjectMemberVO> newMemberList) {
        // 是否具备足够的权限
        if(!projectMemberRepository.existsByProjectIdAndUserIdAndStateAndBelongPrivilegeGroup(projectId,
                userId, ProjectMemberState.OK.getState(), PrivilegeGroup.MASTER.getLevel())){
            throw new DefaultErrorException(ErrorCode.FORBIDDEN);
        }




        return false;
    }

    @Override
    public ProjectMembersVO queryProjectMembers(Long userId, Long projectId) {


        return null;
    }

    @Override
    public List<ProjectMemebrVO> queryInvitingProjectMembers(Long userId, Long projectId) {
        return null;
    }
}
