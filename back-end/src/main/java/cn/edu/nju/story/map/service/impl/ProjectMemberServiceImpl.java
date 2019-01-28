package cn.edu.nju.story.map.service.impl;

import cn.edu.nju.story.map.service.ProjectMemberService;
import cn.edu.nju.story.map.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ProjectMemberServiceImpl
 *
 * @author xuan
 * @date 2019-01-26
 */
@Service
@Slf4j
public class ProjectMemberServiceImpl implements ProjectMemberService {
    @Override
    public boolean agreeInvitation(Long userId, Long invitationId) {
        return false;
    }

    @Override
    public boolean rejectInvitation(Long userId, Long invitationId) {
        return false;
    }

    @Override
    public Page<ProjectInvitationVO> queryMyProjectInvitation(Long userId, PageableVO pageable) {
        return null;
    }


    @Override
    public boolean inviteProjectMember(Long userId, Long projectId, List<InviteProjectMemberVO> newMemberList) {
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
