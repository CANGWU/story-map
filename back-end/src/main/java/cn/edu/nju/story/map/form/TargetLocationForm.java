package cn.edu.nju.story.map.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * TargetLocationForm
 *
 * @author xuan
 * @date 2019-01-30
 */
@Data
public class TargetLocationForm {

    /**
     * 目标epic
     */
    private Long targetEpicId;

    /**
     * 目标feature
     */
    private Long targetFeatureId;

    /**
     * 目标group
     */
    private Long targetGroupId;


    /**
     * 前驱
     */
    @ApiModelProperty("位置前驱，如果位于最前传则不传递")
    private Long precursor;

}
