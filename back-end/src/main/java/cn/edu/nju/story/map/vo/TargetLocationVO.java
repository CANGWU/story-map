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
    public TargetLocationVO(TargetLocationForm targetLocationForm) {
        BeanUtils.copyProperties(targetLocationForm, this);
    }

}
