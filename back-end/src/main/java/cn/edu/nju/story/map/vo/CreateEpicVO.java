package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.form.CreateEpicForm;
import cn.edu.nju.story.map.utils.BeanUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CreateEpicVO
 *
 * @author xuan
 * @date 2019-01-30
 */
@Data
@NoArgsConstructor
public class CreateEpicVO {


    /**
     * 前驱
     */
    private Long precursor;

    /**
     * epic标题
     */
    private String name;

    public CreateEpicVO(CreateEpicForm createEpicForm) {
        BeanUtils.copyProperties(createEpicForm, this);
    }
}
