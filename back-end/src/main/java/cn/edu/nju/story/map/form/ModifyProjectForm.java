package cn.edu.nju.story.map.form;

import lombok.Data;

/**
 * ModifyProjectForm
 *
 * @author xuan
 * @date 2019-01-28
 */
@Data
public class ModifyProjectForm {


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

}
