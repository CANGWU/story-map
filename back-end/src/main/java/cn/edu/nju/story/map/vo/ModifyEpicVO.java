package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.form.ModifyEpicForm;
import cn.edu.nju.story.map.utils.BeanUtils;
import lombok.Data;

/**
 * ModifyEpicVO
 *
 * @author xuan
 * @date 2019-01-30
 */
@Data
public class ModifyEpicVO {

    private String name;

    public ModifyEpicVO(ModifyEpicForm modifyEpicForm) {
        BeanUtils.copyProperties(modifyEpicForm, this);
    }
}
