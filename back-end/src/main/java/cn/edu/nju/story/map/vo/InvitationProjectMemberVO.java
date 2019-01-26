package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.form.InvitationProjectMemberForm;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * InvitationProjectMemberFormVO
 *
 * @author xuan
 * @date 2019-01-26
 */

@Data
public class InvitationProjectMemberVO {


    /**
     * 用户Id
     */
    private Long userId;


    /**
     * 用户所属权限组
     * {@link cn.edu.nju.story.map.constants.PrivilegeGroup}
     */
    private Integer privilegeGroup;


    public InvitationProjectMemberVO(InvitationProjectMemberForm form){
        BeanUtils.copyProperties(form, this);
    }

}
