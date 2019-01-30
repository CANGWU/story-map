package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.form.CreateEpicForm;
import cn.edu.nju.story.map.utils.BeanUtils;
import lombok.Data;

/**
 * CreateEpicVO
 *
 * @author xuan
 * @date 2019-01-30
 */
@Data
public class CreateEpicVO {
    public CreateEpicVO(CreateEpicForm createEpicForm) {
        BeanUtils.copyProperties(createEpicForm, this);
    }
}
