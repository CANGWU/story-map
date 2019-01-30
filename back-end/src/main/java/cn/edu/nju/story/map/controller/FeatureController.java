package cn.edu.nju.story.map.controller;

import cn.edu.nju.story.map.form.CreateFeatureForm;
import cn.edu.nju.story.map.form.ModifyFeatureForm;
import cn.edu.nju.story.map.service.FeatureService;
import cn.edu.nju.story.map.utils.OvalValidatorUtils;
import cn.edu.nju.story.map.utils.UserIdUtils;
import cn.edu.nju.story.map.vo.CreateFeatureVO;
import cn.edu.nju.story.map.vo.FeatureDetailsVO;
import cn.edu.nju.story.map.vo.ModifyFeatureVO;
import cn.edu.nju.story.map.vo.SimpleResponseVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;

/**
 * @author xuan
 * @create 2019-01-30 22:10
 **/

@RestController
@RequestMapping("/feature")
public class FeatureController {


    @Autowired
    FeatureService featureService;


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "创建一个新的feature", response = FeatureDetailsVO.class)
    public SimpleResponseVO createFeature(ServletRequest request, @RequestParam Long epicId, @RequestBody CreateFeatureForm createFeatureForm){


        long userId = UserIdUtils.praseUserIdFromRequest(request);
        OvalValidatorUtils.validate(createFeatureForm);
        return SimpleResponseVO.OK(featureService.createFeature(userId, epicId, new CreateFeatureVO(createFeatureForm)));

    }


    @RequestMapping(value = "/{featureId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除feature", response = boolean.class)
    public SimpleResponseVO deleteFeatureById(ServletRequest request, @PathVariable Long featureId){

        long userId = UserIdUtils.praseUserIdFromRequest(request);
        return SimpleResponseVO.OK(featureService.deleteFeatureById(userId, featureId));

    }


    @RequestMapping(value = "/{featureId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取feature详情", response = FeatureDetailsVO.class)
    public SimpleResponseVO queryFeatureDetailsById(ServletRequest request, @PathVariable("featureId") Long featureId){

        long userId = UserIdUtils.praseUserIdFromRequest(request);
        return SimpleResponseVO.OK(featureService.queryFeatureDetailsById(userId, featureId));
    }


    @RequestMapping(value = "/{featureId}", method = RequestMethod.PUT)
    @ApiOperation(value = "修改卡片详情", response = boolean.class)
    public SimpleResponseVO modifyFeature(ServletRequest request, @PathVariable("featureId") Long featureId, @RequestBody ModifyFeatureForm modifyFeatureForm){

        OvalValidatorUtils.validate(modifyFeatureForm);
        long userId = UserIdUtils.praseUserIdFromRequest(request);
        return SimpleResponseVO.OK(featureService.modifyFeature(userId, featureId, new ModifyFeatureVO(modifyFeatureForm)));
    }




}
