package cn.edu.nju.story.map.controller;

import cn.edu.nju.story.map.form.CreateProjectForm;
import cn.edu.nju.story.map.service.ProjectService;
import cn.edu.nju.story.map.utils.OvalValidatorUtils;
import cn.edu.nju.story.map.utils.UserIdUtils;
import cn.edu.nju.story.map.vo.InvitationProjectMemberVO;
import cn.edu.nju.story.map.vo.ProjectDetailsVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

@RestController("/project")
public class ProjectController {


    @Autowired
    ProjectService projectService;


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "创建一个新的项目", response = boolean.class)
    public boolean createNewProject(ServletRequest request, CreateProjectForm createProject){

        long userId = UserIdUtils.praseUserIdFromRequest(request);
        OvalValidatorUtils.validate(createProject);
        List<InvitationProjectMemberVO> newMemberList = CollectionUtils.isEmpty(createProject.getInvitationMembers()) ? new ArrayList<>() : createProject.getInvitationMembers().stream().map(InvitationProjectMemberVO::new).collect(Collectors.toList());
        return projectService.createProject(userId, createProject.getName(), createProject.getSign(), createProject.getDescription(), newMemberList);

    }


    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取项目的详情", response = ProjectDetailsVO.class)
    public ProjectDetailsVO queryProjectDetails(ServletRequest request, @PathVariable Long projectId){

        long userId = UserIdUtils.praseUserIdFromRequest(request);
        return projectService.queryProjectDetailsById(userId, projectId);

    }




}
