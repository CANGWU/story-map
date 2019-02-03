package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.entity.EpicEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    public EpicVO(EpicEntity epicEntity){
        this.id = epicEntity.getId();
        this.name = epicEntity.getName();
        featureList = new ArrayList<>();
    }

}
