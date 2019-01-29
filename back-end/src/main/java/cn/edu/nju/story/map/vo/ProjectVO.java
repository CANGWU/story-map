package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.entity.ProjectEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ProjectVO
 *
 * @author xuan
 * @date 2019-01-26
 */

@Data
@NoArgsConstructor
public class ProjectVO {


    private Long id;

    private String name;


    public ProjectVO(ProjectEntity projectEntity){
        this.id = projectEntity.getId();
        this.name = projectEntity.getName();
    }


}
