package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.form.InviteProjectMemberForm;
import cn.edu.nju.story.map.utils.BeanUtils;
import lombok.Data;

/**
 * InvitationProjectMemberFormVO
 *
 * @author xuan
 * @date 2019-01-26
 */

@Data
public class InviteProjectMemberVO {


    /**
     * 用户Id
     */
    private Long userId;


    /**
     * 用户所属权限组
     * {@link cn.edu.nju.story.map.constants.PrivilegeGroup}
     */
    private Integer privilegeGroup;


    public InviteProjectMemberVO(InviteProjectMemberForm form){
        BeanUtils.copyProperties(form, this);
    }

}
