package cn.edu.nju.story.map.controller;

import cn.edu.nju.story.map.form.CreateProjectForm;
import cn.edu.nju.story.map.form.ModifyProjectForm;
import cn.edu.nju.story.map.form.PageableForm;
import cn.edu.nju.story.map.service.ProjectService;
import cn.edu.nju.story.map.utils.OvalValidatorUtils;
import cn.edu.nju.story.map.utils.UserIdUtils;
import cn.edu.nju.story.map.vo.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ProjectController
 *
 * @author xuan
 * @date 2019-01-16
 */

@RestController
@RequestMapping("/project")
public class ProjectController {


    @Autowired
    ProjectService projectService;


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "创建一个新的项目", response = ProjectDetailsVO.class)
    public SimpleResponseVO createNewProject(ServletRequest request, @RequestBody CreateProjectForm createProject){
        long userId = UserIdUtils.praseUserIdFromRequest(request);
        OvalValidatorUtils.validate(createProject);
        List<InviteProjectMemberVO> newMemberList = CollectionUtils.isEmpty(createProject.getInvitationMembers()) ? new ArrayList<>() : createProject.getInvitationMembers().stream().map(InviteProjectMemberVO::new).collect(Collectors.toList());
        return SimpleResponseVO.OK(projectService.createProject(userId, createProject.getName(), createProject.getSign(), createProject.getDescription(), newMemberList));

    }


    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取项目的详情", response = ProjectDetailsVO.class)
    public SimpleResponseVO queryProjectDetails(ServletRequest request, @PathVariable Long projectId){

        long userId = UserIdUtils.praseUserIdFromRequest(request);
        return SimpleResponseVO.OK(projectService.queryProjectDetailsById(userId, projectId));

    }


    @RequestMapping(value = "/{projectId}", method = RequestMethod.PUT)
    @ApiOperation(value = "修改项目的详情", response = boolean.class)
    public SimpleResponseVO modifyProject(ServletRequest request, @PathVariable Long projectId, @RequestBody ModifyProjectForm modifyProjectForm){

        long userId = UserIdUtils.praseUserIdFromRequest(request);
        return SimpleResponseVO.OK(projectService.modifyProject(userId, projectId, new ModifyProjectVO(modifyProjectForm)));

    }

    @RequestMapping(value = "/list/joined", method = RequestMethod.POST)
    @ApiOperation(value = "获取我参与的项目", response = ProjectVO.class)
    public SimpleResponseVO queryJoinedProject(ServletRequest request, @RequestBody PageableForm pageableForm){

        OvalValidatorUtils.validate(pageableForm);
        long userId = UserIdUtils.praseUserIdFromRequest(request);
        return SimpleResponseVO.OK(projectService.queryJoinedProject(userId, new PageableVO(pageableForm)));

    }


    @RequestMapping(value = "/list/my", method = RequestMethod.POST)
    @ApiOperation(value = "获取我创建的项目", response = ProjectVO.class)
    public SimpleResponseVO queryMyProject(ServletRequest request, @RequestBody PageableForm pageableForm){

        OvalValidatorUtils.validate(pageableForm);
        long userId = UserIdUtils.praseUserIdFromRequest(request);
        return SimpleResponseVO.OK(projectService.queryMyProject(userId, new PageableVO(pageableForm)));

    }

    @RequestMapping(value = "/{projectId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除项目", response = boolean.class)
    public SimpleResponseVO deleteProjectById(ServletRequest request, @PathVariable Long projectId){

        long userId = UserIdUtils.praseUserIdFromRequest(request);
        return SimpleResponseVO.OK(projectService.deleteProjectById(userId, projectId));

    }




}
