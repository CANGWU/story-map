package cn.edu.nju.story.map.service;

import cn.edu.nju.story.map.vo.*;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * ProjectMemberService
 *
 * @author xuan
 * @date 2019-01-26
 */
public interface ProjectMemberService {


    /**
     * 同意邀请，加入项目
     * @param userId
     * @param invitationId
     * @return
     */
    boolean agreeInvitation(Long userId, Long invitationId);


    /**
     * 拒绝邀请
     * @param userId
     * @param invitationId
     * @return
     */
    boolean rejectInvitation(Long userId, Long invitationId);

    /**
     * 获取我的项目邀请
     * @param userId
     * @param pageable
     * @return
     */
    Page<ProjectInvitationVO> queryMyProjectInvitation(Long userId, PageableVO pageable);


    /**
     * 邀请用户加入项目
     * @param userId
     * @param projectId
     * @param newMemberList
     * @return
     */
    boolean inviteProjectMember(Long userId, Long projectId, List<InviteProjectMemberVO> newMemberList);


    /**
     * 获取项目的参与者
     * @param projectId
     * @param userId
     * @return
     */
    ProjectMembersVO queryProjectMembers(Long userId, Long projectId);

    /**
     * 获取邀请中的项目参与者
     * @param projectId
     * @param userId
     * @return
     */
    List<ProjectMemebrVO> queryInvitingProjectMembers(Long userId, Long projectId);

}
