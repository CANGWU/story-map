package cn.edu.nju.story.map.service.impl;

import cn.edu.nju.story.map.constants.ErrorCode;
import cn.edu.nju.story.map.constants.PrivilegeGroup;
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
import cn.edu.nju.story.map.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

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

        return false;
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


        return null;
    }

    @Override
    public boolean modifyProject(Long userId, Long projectId, ModifyProjectVO modifyProjectVO) {
        return false;
    }

    @Override
    public boolean deleteProjectById(Long userId, Long projectId) {
        return false;
    }

    @Override
    public Page<ProjectVO> queryJoinedProject(Long userId, PageableVO pageableVO) {
        return null;
    }

    @Override
    public Page<ProjectVO> queryMyProject(Long userId, PageableVO pageableVO) {
        return null;
    }
}
