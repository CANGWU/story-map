package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.form.CreateFeatureForm;
import cn.edu.nju.story.map.utils.BeanUtils;
import lombok.Data;

/**
 * @author xuan
 * @create 2019-01-30 22:14
 **/
@Data
public class CreateFeatureVO {
    public CreateFeatureVO(CreateFeatureForm createFeatureForm) {
        BeanUtils.copyPropertiesSkipNull(createFeatureForm, this);
    }
}
