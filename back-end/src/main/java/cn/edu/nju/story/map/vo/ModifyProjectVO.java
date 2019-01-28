package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.form.ModifyProjectForm;
import cn.edu.nju.story.map.utils.BeanUtils;

/**
 * ModifyProjectVO
 *
 * @author xuan
 * @date 2019-01-26
 */
public class ModifyProjectVO {
    public ModifyProjectVO(ModifyProjectForm modifyProjectForm) {
        BeanUtils.copyProperties(modifyProjectForm, this);
    }
}
