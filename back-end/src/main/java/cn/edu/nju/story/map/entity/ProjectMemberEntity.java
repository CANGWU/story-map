package cn.edu.nju.story.map.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * ProjectMemberEntity
 * 成员关系表
 * @author xuan
 * @create 2019-01-12 23:20
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
// 项目Id和用户Id唯一索引保证一个用户只能参与一个项目一次
@Table(name = "project_member",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"pro_id", "user_id"})})
public class ProjectMemberEntity {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 项目Id
     */
    @Column(name = "pro_id")
    private Long projectId;

    /**
     * 用户Id
     */
    @Column(name = "user_id")
    private Long userId;


    /**
     * 所属的权限组
     * {@link cn.edu.nju.story.map.constants.PrivilegeGroup}
     */
    @Column(name = "belong_privilege_group")
    private Long  belongPrivilegeGroup;

    /**
     * 成员状态
     * {@link cn.edu.nju.story.map.constants.MemberState}
     */
    private Integer state;




}
