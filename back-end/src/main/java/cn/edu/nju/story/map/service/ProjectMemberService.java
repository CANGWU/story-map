package cn.edu.nju.story.map.service;

import cn.edu.nju.story.map.vo.InvitationProjectMemberVO;
import cn.edu.nju.story.map.vo.ProjectInvitationVO;
import cn.edu.nju.story.map.vo.ProjectMembersVO;
import cn.edu.nju.story.map.vo.ProjectMemebrVO;
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
     * @return
     */
    Page<ProjectInvitationVO> queryMyProjectInvitation(Long userId);


    /**
     * 邀请用户加入项目
     * @param userId
     * @param projectId
     * @param newMemberList
     * @return
     */
    boolean invitationProjectMember(Long userId, Long projectId, List<InvitationProjectMemberVO> newMemberList);


    /**
     * 获取项目的参与者
     * @param projectId
     * @return
     */
    ProjectMembersVO queryProjectMembers(Long projectId);

    /**
     * 获取邀请中的项目参与者
     * @param projectId
     * @return
     */
    List<ProjectMemebrVO> queryInvitationProjectMembers(Long projectId);

}
