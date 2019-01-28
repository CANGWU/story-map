package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.form.CreateCardForm;
import cn.edu.nju.story.map.utils.BeanUtils;
import lombok.Data;

/**
 * CreateCardVO
 *
 * @author xuan
 * @date 2019-01-28
 */
@Data
public class CreateCardVO {
    public CreateCardVO(CreateCardForm createCardForm) {

        BeanUtils.copyProperties(createCardForm, this);

    }
}
