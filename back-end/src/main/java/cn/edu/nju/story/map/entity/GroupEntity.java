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
@Table(name = "my_group")
public class GroupEntity {



    @Id
    @GeneratedValue
    private Long id;

    /**
     * 名称
     */
    private String name;


    @Column(name = "belong_pro_id")
    private Long belongProjectId;





}
