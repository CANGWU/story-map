package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;

/**
 * @author xuan
 * @create 2019-01-05 15:44
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {


    @Id
    private Long id;

    /**
     * 用户名
     */
    private String username;


    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;


    public UserVO(UserEntity userEntity){
        BeanUtils.copyProperties(userEntity, this, "password");
    }

}
