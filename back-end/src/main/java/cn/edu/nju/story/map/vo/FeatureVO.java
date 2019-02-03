package cn.edu.nju.story.map.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * FeatureVO
 *
 * @author xuan
 * @date 2019-01-31
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeatureVO {

    private Long id;

    private String name;

    private List<GroupVO> groupList;

}
