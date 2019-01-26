package cn.edu.nju.story.map.service.impl;

import cn.edu.nju.story.map.service.ProjectMemberService;
import cn.edu.nju.story.map.vo.InvitationProjectMemberVO;
import cn.edu.nju.story.map.vo.ProjectInvitationVO;
import cn.edu.nju.story.map.vo.ProjectMembersVO;
import cn.edu.nju.story.map.vo.ProjectMemebrVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * ProjectMemberServiceImpl
 *
 * @author xuan
 * @date 2019-01-26
 */
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
    public Page<ProjectInvitationVO> queryMyProjectInvitation(Long userId) {
        return null;
    }

    @Override
    public boolean invitationProjectMember(Long userId, Long projectId, List<InvitationProjectMemberVO> newMemberList) {
        return false;
    }

    @Override
    public ProjectMembersVO queryProjectMembers(Long projectId) {
        return null;
    }

    @Override
    public List<ProjectMemebrVO> queryInvitationProjectMembers(Long projectId) {
        return null;
    }
}
