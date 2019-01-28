package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.form.ModifyCardForm;
import cn.edu.nju.story.map.utils.BeanUtils;
import lombok.Data;

/**
 * ModifyCardVO
 *
 * @author xuan
 * @date 2019-01-28
 */
@Data
public class ModifyCardVO {
    public ModifyCardVO(ModifyCardForm modifyCardForm) {
        BeanUtils.copyProperties(modifyCardForm, this);
    }
}
