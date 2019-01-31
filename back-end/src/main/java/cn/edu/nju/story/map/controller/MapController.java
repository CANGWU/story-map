package cn.edu.nju.story.map.controller;

import cn.edu.nju.story.map.form.TargetLocationForm;
import cn.edu.nju.story.map.service.MapService;
import cn.edu.nju.story.map.utils.OvalValidatorUtils;
import cn.edu.nju.story.map.utils.UserIdUtils;
import cn.edu.nju.story.map.vo.MapDetailsVO;
import cn.edu.nju.story.map.vo.TargetLocationVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;

/**
 * MapController
 *
 * @author xuan
 * @date 2019-01-16
 */

@RestController
@RequestMapping("/map")
public class MapController {


    @Autowired
    MapService mapService;


    @RequestMapping(value = "/epic/move/{epicId}", method = RequestMethod.PUT)
    @ApiOperation(value = "修改map中epic的位置", response = boolean.class)
    public boolean modifyEpicLocation(ServletRequest request, @PathVariable Long epicId, @RequestBody TargetLocationForm targetLocationForm){

        OvalValidatorUtils.validate(targetLocationForm);
        long userId = UserIdUtils.praseUserIdFromRequest(request);
        return mapService.moveEpicLocation(userId, epicId, new TargetLocationVO(targetLocationForm));

    }


    @RequestMapping(value = "/feature/move/{featureId}", method = RequestMethod.PUT)
    @ApiOperation(value = "修改map中featureId的位置", response = boolean.class)
    public boolean modifyFeatureLocation(ServletRequest request, @PathVariable Long featureId, @RequestBody TargetLocationForm targetLocationForm){

        OvalValidatorUtils.validate(targetLocationForm);
        long userId = UserIdUtils.praseUserIdFromRequest(request);
        return mapService.moveFeatureLocation(userId, featureId, new TargetLocationVO(targetLocationForm));

    }


    @RequestMapping(value = "/card/move/{cardId}", method = RequestMethod.PUT)
    @ApiOperation(value = "修改map中card的位置,全部置空表示恢复未映射", response = boolean.class)
    public boolean modifyCardLocation(ServletRequest request, @PathVariable Long cardId, @RequestBody TargetLocationForm targetLocationForm){

        OvalValidatorUtils.validate(targetLocationForm);
        long userId = UserIdUtils.praseUserIdFromRequest(request);
        return mapService.moveCardLocation(userId, cardId, new TargetLocationVO(targetLocationForm));

    }


    @RequestMapping(value = "/details", method = RequestMethod.GET)
    @ApiOperation(value = "获取map所有数据", response = MapDetailsVO.class)
    public MapDetailsVO queryMapDetails(ServletRequest request, @RequestParam Long projectId){

        long userId = UserIdUtils.praseUserIdFromRequest(request);
        return mapService.queryMapDetails(userId, projectId);

    }










}
