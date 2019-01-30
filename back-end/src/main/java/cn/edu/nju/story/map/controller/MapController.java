package cn.edu.nju.story.map.controller;

import cn.edu.nju.story.map.form.MoveLocationForm;
import cn.edu.nju.story.map.form.TargetLocationForm;
import cn.edu.nju.story.map.service.MapService;
import cn.edu.nju.story.map.utils.OvalValidatorUtils;
import cn.edu.nju.story.map.utils.UserIdUtils;
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


    @RequestMapping(value = "/epic/move/{epicId}", method = RequestMethod.POST)
    @ApiOperation(value = "修改map中epic的位置", response = boolean.class)
    public boolean modifyEpicLocation(ServletRequest request, @PathVariable Long epicId, @RequestBody TargetLocationForm targetLocationForm){

        OvalValidatorUtils.validate(targetLocationForm);
        long userId = UserIdUtils.praseUserIdFromRequest(request);
        return mapService.moveEpicLocation(userId, epicId, new TargetLocationVO(targetLocationForm));

    }








}
