package cn.edu.nju.story.map.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * EpicVO
 *
 * @author xuan
 * @date 2019-01-31
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EpicVO {

    private Long id;

    private String name;


    private List<FeatureVO> featureList;

}
