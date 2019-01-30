package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.form.ModifyEpicForm;
import cn.edu.nju.story.map.utils.BeanUtils;

/**
 * ModifyEpicVO
 *
 * @author xuan
 * @date 2019-01-30
 */
public class ModifyEpicVO {
    public ModifyEpicVO(ModifyEpicForm modifyEpicForm) {
        BeanUtils.copyProperties(modifyEpicForm, this);
    }
}
