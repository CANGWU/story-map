package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.entity.GroupEntity;
import lombok.Data;

/**
 * @author xuan
 * @create 2019-01-30 22:28
 **/
@Data
public class GroupDetailsVO {



    private Long id;

    private String name;

    public GroupDetailsVO(GroupEntity groupEntity) {
        this.id = groupEntity.getId();
        this.name = groupEntity.getName();
    }
}
