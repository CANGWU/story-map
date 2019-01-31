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



    /**
     * 前驱
     */
    private Long precursor;

    /**
     * epic标题
     */
    private String name;


    public CreateFeatureVO(CreateFeatureForm createFeatureForm) {
        BeanUtils.copyPropertiesSkipNull(createFeatureForm, this);
    }
}
