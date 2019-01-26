package cn.edu.nju.story.map.service.impl;

import cn.edu.nju.story.map.service.ProjectService;
import cn.edu.nju.story.map.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ProjectServiceImpl
 *
 * @author xuan
 * @date 2019-01-26
 */
@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {
    @Override
    public boolean createProject(Long uesrId, String name, String sign, String description, List<InvitationProjectMemberVO> newMemberList) {
        return false;
    }

    @Override
    public ProjectDetailsVO queryProjectDetailsById(Long userId, Long projectId) {
        return null;
    }

    @Override
    public boolean modifyProject(Long userId, ModifyProjectVO modifyProjectVO) {
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
