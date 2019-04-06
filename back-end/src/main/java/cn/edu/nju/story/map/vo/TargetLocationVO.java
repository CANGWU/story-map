package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.form.TargetLocationForm;
import cn.edu.nju.story.map.utils.BeanUtils;
import lombok.Data;

/**
 * TargetLocationVO
 *
 * @author xuan
 * @date 2019-01-30
 */
@Data
public class TargetLocationVO {


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
    private Long precursor;

    public TargetLocationVO(TargetLocationForm targetLocationForm) {
        BeanUtils.copyProperties(targetLocationForm, this);
    }

}
