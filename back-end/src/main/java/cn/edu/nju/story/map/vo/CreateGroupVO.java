package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.form.CreateGroupForm;
import cn.edu.nju.story.map.utils.BeanUtils;
import lombok.Data;

/**
 * @author xuan
 * @create 2019-01-30 22:28
 **/
@Data
public class CreateGroupVO {

    public CreateGroupVO(CreateGroupForm createGroupForm){
        BeanUtils.copyPropertiesSkipNull(createGroupForm, this);
    }

}
