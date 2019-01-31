package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.form.ModifyGroupForm;
import cn.edu.nju.story.map.utils.BeanUtils;
import lombok.Data;

/**
 * @author xuan
 * @create 2019-01-30 22:29
 **/
@Data
public class ModifyGroupVO {

    private String name;

    public ModifyGroupVO(ModifyGroupForm modifyGroupForm){
        BeanUtils.copyPropertiesSkipNull(modifyGroupForm, this);
    }

}
