package cn.edu.nju.story.map.service.impl;

import cn.edu.nju.story.map.constants.ErrorCode;
import cn.edu.nju.story.map.constants.PrivilegeGroup;
import cn.edu.nju.story.map.constants.ProjectMemberState;
import cn.edu.nju.story.map.constants.ProjectState;
import cn.edu.nju.story.map.entity.ProjectEntity;
import cn.edu.nju.story.map.entity.ProjectMemberEntity;
import cn.edu.nju.story.map.entity.UserEntity;
import cn.edu.nju.story.map.exception.DefaultErrorException;
import cn.edu.nju.story.map.repository.ProjectMemberRepository;
import cn.edu.nju.story.map.repository.ProjectRepository;
import cn.edu.nju.story.map.repository.UserRepository;
import cn.edu.nju.story.map.service.ProjectMemberService;
import cn.edu.nju.story.map.service.ProjectService;
import cn.edu.nju.story.map.utils.BeanUtils;
import cn.edu.nju.story.map.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * ProjectServiceImpl
 *
 * @author xuan
 * @date 2019-01-26
 */
@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {


    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectMemberService projectMemberService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectMemberRepository projectMemberRepository;

    @Override
    public boolean createProject(Long userId, String name, String sign, String description, List<InviteProjectMemberVO> newMemberList) {


        if(projectRepository.existsBySign(sign)){
            throw new DefaultErrorException(ErrorCode.SIGN_IS_EXISTED);
        }

        // 创建新的项目到数据库
        ProjectEntity newProject = ProjectEntity.builder()
                .name(name)
                .sign(sign)
                .description(description)
                .creatorUserId(userId)
                .createTime(new Timestamp(System.currentTimeMillis()))
                .state(ProjectState.OK.getState())
                .build();

        newProject = projectRepository.save(newProject);

        // 添加参与者
        newMemberList.add(new InviteProjectMemberVO(userId, PrivilegeGroup.MASTER.getLevel()));

        projectMemberService.inviteProjectMember(userId, newProject.getId(), newMemberList);

        return true;
    }

    @Override
    public ProjectDetailsVO queryProjectDetailsById(Long userId, Long projectId) {

        Optional<ProjectEntity> projectEntityOptional = projectRepository.findById(projectId);

        if(!projectEntityOptional.isPresent()){
            throw new DefaultErrorException(ErrorCode.PROJECT_NOT_EXISTED);
        }

        if(!projectMemberRepository.existsByProjectIdAndUserId(projectId, userId)){
            throw new DefaultErrorException(ErrorCode.FORBIDDEN);
        }

        Optional<UserEntity> createUser = userRepository.findById(userId);


        return new ProjectDetailsVO(projectEntityOptional.get(), createUser.map(UserVO::new).orElse(null));
    }

    @Override
    public boolean modifyProject(Long userId, Long projectId, ModifyProjectVO modifyProjectVO) {

        Optional<ProjectEntity> projectEntityOptional = projectRepository.findById(projectId);

        if(!projectEntityOptional.isPresent()){
            throw new DefaultErrorException(ErrorCode.PROJECT_NOT_EXISTED);
        }

        // 非项目管理员权限
        if(!projectMemberRepository.existsByProjectIdAndUserIdAndStateAndBelongPrivilegeGroup(projectId
        , userId, ProjectMemberState.OK.getState(), PrivilegeGroup.MASTER.getLevel())){
            throw new DefaultErrorException(ErrorCode.FORBIDDEN);
        }

        ProjectEntity projectEntity = projectEntityOptional.get();

        BeanUtils.copyPropertiesSkipNull(modifyProjectVO, projectEntity);

        projectRepository.save(projectEntity);

        return true;
    }

    @Override
    public boolean deleteProjectById(Long userId, Long projectId) {

        // 仅创建者可以删除
        if(projectRepository.existsByIdAndCreatorUserId(projectId, userId)){
            projectRepository.deleteById(projectId);
            return true;
        }else {
            throw new DefaultErrorException(ErrorCode.FORBIDDEN);
        }

    }

    @Override
    public Page<ProjectVO> queryJoinedProject(Long userId, PageableVO pageableVO) {

        Page<ProjectMemberEntity> projectMemberEntities = projectMemberRepository.findByUserIdAndStateOrderByCreateTimeDesc(
                userId, ProjectMemberState.OK.getState(), PageRequest.of(pageableVO.getPageNumber(), pageableVO.getPageSize())
        );

        if(projectMemberEntities.isEmpty()){
            return new PageImpl<>(new ArrayList<>(), projectMemberEntities.getPageable(), projectMemberEntities.getSize());
        }else {
            return new PageImpl<>(StreamSupport.stream(projectRepository.findAllById(projectMemberEntities.get().map(ProjectMemberEntity::getProjectId).collect(Collectors.toList())).spliterator(), true)
                   .map(ProjectVO::new).collect(Collectors.toList()), projectMemberEntities.getPageable(), projectMemberEntities.getSize());

        }

    }

    @Override
    public Page<ProjectVO> queryMyProject(Long userId, PageableVO pageableVO) {

        return projectRepository.findByCreatorUserIdOrderByCreateTimeDesc(userId, PageRequest.of(pageableVO.getPageNumber(), pageableVO.getPageSize())).map(ProjectVO::new);
    }
}
