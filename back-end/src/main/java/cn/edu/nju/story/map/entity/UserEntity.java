package cn.edu.nju.story.map.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * User
 * @author xuan
 * @create 2019-01-05 15:26
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class UserEntity {


    @Id
    @GeneratedValue
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 邮箱
     */

    private String email;

    /**
     * 用户状态
     * {@link cn.edu.nju.story.map.constants.UserState}
     */
    private Integer state;


}
