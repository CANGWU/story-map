package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.form.ModifyFeatureForm;
import cn.edu.nju.story.map.utils.BeanUtils;
import lombok.Data;

/**
 * @author xuan
 * @create 2019-01-30 22:21
 **/

@Data
public class ModifyFeatureVO {
    public ModifyFeatureVO(ModifyFeatureForm modifyFeatureForm) {
        BeanUtils.copyPropertiesSkipNull(modifyFeatureForm, this);
    }
}
