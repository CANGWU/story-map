package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.entity.GroupEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * GroupVO
 *
 * @author xuan
 * @date 2019-01-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupVO {

    private Long id;

    private String name;

    private List<CardVO> cardList;


    public GroupVO(GroupEntity groupEntity) {

        this.id = groupEntity.getId();
        this.name = groupEntity.getName();

        cardList = new ArrayList<>();

    }
}
