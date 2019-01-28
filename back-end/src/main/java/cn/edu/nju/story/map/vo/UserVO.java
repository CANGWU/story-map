package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.entity.UserEntity;
import cn.edu.nju.story.map.utils.BeanUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
     * token
     */
    @ApiModelProperty("用户token")
    private String token;



    public UserVO(UserEntity userEntity, String token){
        BeanUtils.copyProperties(userEntity, this, "password");
        this.token = token;
    }


    public UserVO(UserEntity userEntity){
        BeanUtils.copyProperties(userEntity, this, "password");
    }

}
