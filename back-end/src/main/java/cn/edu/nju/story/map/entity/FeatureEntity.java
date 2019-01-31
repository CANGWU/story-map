package cn.edu.nju.story.map.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author xuan
 * @create 2019-01-13 00:04
 **/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "feature")
public class FeatureEntity {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 所属项目
     */
    @Column(name = "belong_pro_id")
    private Long belongProjectId;


    /**
     * 所属Epic
     */
    @Column(name = "belong_epic_id")
    private Long belongEpicId;



}
