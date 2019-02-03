package cn.edu.nju.story.map.controller;


import cn.edu.nju.story.map.form.CreateGroupForm;
import cn.edu.nju.story.map.form.ModifyGroupForm;
import cn.edu.nju.story.map.service.GroupService;
import cn.edu.nju.story.map.utils.OvalValidatorUtils;
import cn.edu.nju.story.map.utils.UserIdUtils;
import cn.edu.nju.story.map.vo.CreateGroupVO;
import cn.edu.nju.story.map.vo.GroupDetailsVO;
import cn.edu.nju.story.map.vo.ModifyGroupVO;
import cn.edu.nju.story.map.vo.SimpleResponseVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;

/**
 * @author xuan
 * @create 2019-01-30 22:23
 **/
@RestController
@RequestMapping("/group")
public class GroupController {



    @Autowired
    GroupService groupService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "创建一个新的group", response = GroupDetailsVO.class)
    public SimpleResponseVO createGroup(ServletRequest request, @RequestParam Long projectId, @RequestBody CreateGroupForm createGroupForm){


        long userId = UserIdUtils.praseUserIdFromRequest(request);
        OvalValidatorUtils.validate(createGroupForm);
        return SimpleResponseVO.OK(groupService.createGroup(userId, projectId, new CreateGroupVO(createGroupForm)));

    }


    @RequestMapping(value = "/{groupId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除group", response = boolean.class)
    public SimpleResponseVO deleteGroupById(ServletRequest request, @PathVariable Long groupId){

        long userId = UserIdUtils.praseUserIdFromRequest(request);
        return SimpleResponseVO.OK(groupService.deleteGroupById(userId, groupId));

    }


    @RequestMapping(value = "/{groupId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取Group详情", response = GroupDetailsVO.class)
    public SimpleResponseVO queryGroupDetailsById(ServletRequest request, @PathVariable("groupId") Long groupId){

        long userId = UserIdUtils.praseUserIdFromRequest(request);
        return SimpleResponseVO.OK(groupService.queryGroupDetailsById(userId, groupId));
    }


    @RequestMapping(value = "/{groupId}", method = RequestMethod.PUT)
    @ApiOperation(value = "修改卡片详情", response = boolean.class)
    public SimpleResponseVO modifyGroup(ServletRequest request, @PathVariable("groupId") Long groupId, @RequestBody ModifyGroupForm modifyGroupForm){

        OvalValidatorUtils.validate(modifyGroupForm);
        long userId = UserIdUtils.praseUserIdFromRequest(request);
        return SimpleResponseVO.OK(groupService.modifyGroup(userId, groupId, new ModifyGroupVO(modifyGroupForm)));
    }



}
