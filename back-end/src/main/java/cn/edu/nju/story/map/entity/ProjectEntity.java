package cn.edu.nju.story.map.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Project
 * 项目实体类
 * @author xuan
 * @create 2019-01-12 12:36
 **/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "project")
public class ProjectEntity {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 项目名
     */
    private String name;


    /**
     * 项目标识
     */
    private String sign;

    /**
     * 创建用户Id
     */
    @Column(name = "creator_user_id")
    private String creatorUserId;

    /**
     * 项目描述
     */
    private String description;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Timestamp createTime;

    /**
     * 项目状态
     * {@link cn.edu.nju.story.map.constants.ProjectState}
     */
    private Integer state;



}
