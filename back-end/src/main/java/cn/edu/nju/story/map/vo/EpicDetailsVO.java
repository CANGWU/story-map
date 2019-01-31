package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.entity.EpicEntity;
import lombok.Data;

/**
 * EpicDetailsVO
 *
 * @author xuan
 * @date 2019-01-30
 */
@Data
public class EpicDetailsVO {

    private Long id;

    private String name;


    public EpicDetailsVO(EpicEntity epicEntity){
        this.id = epicEntity.getId();
        this.name = epicEntity.getName();
    }


}
