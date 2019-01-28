package cn.edu.nju.story.map.form;

import lombok.Data;

/**
 * 邀请项目成员表单
 * @author xuan
 * @create 2019-01-22 23:06
 **/
@Data
public class InviteProjectMemberForm {


    /**
     * 用户Id
     */
    private Long userId;


    /**
     * 用户所属权限组
     * {@link cn.edu.nju.story.map.constants.PrivilegeGroup}
     */
    private Integer privilegeGroup;



}
