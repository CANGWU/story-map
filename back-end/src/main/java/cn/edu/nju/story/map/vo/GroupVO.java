package cn.edu.nju.story.map.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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


}
