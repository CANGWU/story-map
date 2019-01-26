package cn.edu.nju.story.map.form;

import lombok.Data;

import java.util.List;

/**
 * CreateProjectEntity
 *
 * @author xuan
 * @date 2019-01-17
 */
@Data
public class CreateProjectForm {




    private String name;

    private String sign;

    private String description;

    private List<AddProjectMemberForm> newMembers;




}
