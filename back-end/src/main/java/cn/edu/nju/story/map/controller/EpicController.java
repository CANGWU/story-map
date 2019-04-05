package cn.edu.nju.story.map.controller;

import cn.edu.nju.story.map.form.CreateEpicForm;
import cn.edu.nju.story.map.form.ModifyEpicForm;
import cn.edu.nju.story.map.service.EpicService;
import cn.edu.nju.story.map.utils.OvalValidatorUtils;
import cn.edu.nju.story.map.utils.UserIdUtils;
import cn.edu.nju.story.map.vo.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;

/**
 * EpicController
 *
 * @author xuan
 * @date 2019-01-30
 */
@RestController
@RequestMapping("/epic")
public class EpicController {


    @Autowired
    EpicService epicService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "创建一个新的epic", response = EpicDetailsVO.class)
    public SimpleResponseVO createEpic(ServletRequest request, @RequestParam Long projectId, @RequestBody CreateEpicForm createEpicForm){


        long userId = UserIdUtils.praseUserIdFromRequest(request);
        OvalValidatorUtils.validate(createEpicForm);
        return SimpleResponseVO.OK(epicService.createEpic(userId, projectId, new CreateEpicVO(createEpicForm)));

    }


    @RequestMapping(value = "/{epicId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除epic", response = boolean.class)
    public SimpleResponseVO deleteEpicById(ServletRequest request, @PathVariable Long epicId){

        long userId = UserIdUtils.praseUserIdFromRequest(request);
        return SimpleResponseVO.OK(epicService.deleteEpicById(userId, epicId));

    }


    @RequestMapping(value = "/{epicId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取epic详情", response = EpicDetailsVO.class)
    public SimpleResponseVO queryCardDetailsById(ServletRequest request, @PathVariable("epicId") Long epicId){

        long userId = UserIdUtils.praseUserIdFromRequest(request);
        return SimpleResponseVO.OK(epicService.queryEpicDetailsById(userId, epicId));
    }


    @RequestMapping(value = "/{epicId}", method = RequestMethod.PUT)
    @ApiOperation(value = "修改卡片详情", response = boolean.class)
    public SimpleResponseVO modifyEpic(ServletRequest request, @PathVariable("epicId") Long epicId, @RequestBody ModifyEpicForm modifyEpicForm){

        OvalValidatorUtils.validate(modifyEpicForm);
        long userId = UserIdUtils.praseUserIdFromRequest(request);
        return SimpleResponseVO.OK(epicService.modifyEpic(userId, epicId, new ModifyEpicVO(modifyEpicForm)));
    }







}
