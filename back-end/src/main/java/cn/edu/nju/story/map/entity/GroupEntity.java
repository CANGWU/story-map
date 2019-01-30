package cn.edu.nju.story.map.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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

    /**
     * 创建的顺序
     */
    private Integer number;


}
