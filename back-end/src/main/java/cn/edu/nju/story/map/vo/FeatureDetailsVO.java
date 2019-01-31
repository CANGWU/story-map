package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.entity.FeatureEntity;
import lombok.Data;

/**
 * @author xuan
 * @create 2019-01-30 22:15
 **/

@Data
public class FeatureDetailsVO {

    private Long id;


    private String name;

    public FeatureDetailsVO(FeatureEntity featureEntity) {
        this.id = featureEntity.getId();
        this.name = featureEntity.getName();
    }
}
