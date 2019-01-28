package cn.edu.nju.story.map.controller;

import cn.edu.nju.story.map.form.InviteProjectMemberForm;
import cn.edu.nju.story.map.form.PageableForm;
import cn.edu.nju.story.map.service.ProjectMemberService;
import cn.edu.nju.story.map.utils.UserIdUtils;
import cn.edu.nju.story.map.vo.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ProjectMemberController
 *
 * @author xuan
 * @date 2019-01-26
 */

@RestController
@RequestMapping("/member")
public class ProjectMemberController {



    @Autowired
    ProjectMemberService projectMemberService;


    @RequestMapping(value = "/invite/agree", method = RequestMethod.POST)
    @ApiOperation(value = "同意邀请", response = boolean.class)
    public boolean agreeInvitation(ServletRequest request, @RequestParam Long invitationId){
        long userId = UserIdUtils.praseUserIdFromRequest(request);
        return projectMemberService.agreeInvitation(userId, invitationId);
    }

    @RequestMapping(value = "/invite/reject", method = RequestMethod.POST)
    @ApiOperation(value = "拒绝邀请", response = boolean.class)
    public boolean rejectInvitation(ServletRequest request, @RequestParam Long invitationId){

        long userId = UserIdUtils.praseUserIdFromRequest(request);
        return projectMemberService.rejectInvitation(userId, invitationId);
    }

    @RequestMapping(value = "/invite/my", method = RequestMethod.POST)
    @ApiOperation(value = "分页获取我的邀请", response = ProjectInvitationVO.class)
    public Page<ProjectInvitationVO> queryMyProjectInvitation(ServletRequest request, @RequestBody PageableForm pageableForm) {

        long userId = UserIdUtils.praseUserIdFromRequest(request);
        return projectMemberService.queryMyProjectInvitation(userId, new PageableVO(pageableForm));
    }

    @RequestMapping(value = "/invite", method = RequestMethod.POST)
    @ApiOperation(value = "邀请用户加入项目", response = boolean.class)
    public boolean inviteProjectMember(ServletRequest request, @RequestParam Long projectId, @RequestBody List<InviteProjectMemberForm> newMembers){

        long userId = UserIdUtils.praseUserIdFromRequest(request);
        return projectMemberService.inviteProjectMember(userId, projectId, newMembers.stream().map(InviteProjectMemberVO::new).collect(Collectors.toList()));

    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "获取项目的参与者", response = ProjectMembersVO.class)
    public ProjectMembersVO queryProjectMembers(ServletRequest request, @RequestParam Long projectId){

        long userId = UserIdUtils.praseUserIdFromRequest(request);
        return projectMemberService.queryProjectMembers(userId, projectId);
    }

    @RequestMapping(value = "/invite/list", method = RequestMethod.GET)
    @ApiOperation(value = "获取邀请中的项目成员列表", response = ProjectMemebrVO.class)
    public List<ProjectMemebrVO> queryInvitingProjectMembers(ServletRequest request, @RequestParam Long projectId){

        long userId = UserIdUtils.praseUserIdFromRequest(request);
        return projectMemberService.queryInvitingProjectMembers(userId, projectId);
    }




}
