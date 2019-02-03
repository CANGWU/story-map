package cn.edu.nju.story.map.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author xuan
 * @create 2019-01-13 00:02
 **/


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "epic")
public class EpicEntity {

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
     * 特性的排序记录
     */
    @Column(name = "feature_index")
    private String featureIndexList;


}
