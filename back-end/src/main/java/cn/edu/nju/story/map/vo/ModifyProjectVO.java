package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.form.ModifyProjectForm;
import cn.edu.nju.story.map.utils.BeanUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ModifyProjectVO
 *
 * @author xuan
 * @date 2019-01-26
 */
@Data
@NoArgsConstructor
public class ModifyProjectVO {


    /**
     * 项目名
     */
    private String name;

    /**
     * 项目标识
     */
    private String sign;

    /**
     * 项目描述
     */
    private String description;




    public ModifyProjectVO(ModifyProjectForm modifyProjectForm) {
        BeanUtils.copyProperties(modifyProjectForm, this);
    }
}
