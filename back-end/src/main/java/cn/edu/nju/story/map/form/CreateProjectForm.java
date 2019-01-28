package cn.edu.nju.story.map.form;

import lombok.Data;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.util.List;

/**
 * CreateProjectEntity
 *
 * @author xuan
 * @date 2019-01-17
 */
@Data
public class CreateProjectForm {



    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String sign;

    private String description;

    private List<InviteProjectMemberForm> invitationMembers;



}
