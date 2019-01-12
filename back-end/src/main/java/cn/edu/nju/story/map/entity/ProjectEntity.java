package cn.edu.nju.story.map.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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
     * 创建用户Id
     */
    private String creatorId;

    /**
     * 项目描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 项目状态
     * {@link cn.edu.nju.story.map.constants.ProjectState}
     */
    private Integer state;



}
