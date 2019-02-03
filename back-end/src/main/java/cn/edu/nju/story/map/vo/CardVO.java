package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.entity.CardEntity;
import lombok.Data;

import java.sql.Timestamp;

/**
 * CardVO
 *
 * @author xuan
 * @date 2019-01-28
 */
@Data
public class CardVO {


    private Long id;

    private Integer number;

    private String title;

    private Integer type;

    private Timestamp createTime;

    private Integer state;

    private UserVO creatorUser;

    private Long belongGroupId;

    private Long belongFeatureId;




    public CardVO(CardEntity cardEntity, UserVO creatorUser){

        this.id = cardEntity.getId();
        this.number = cardEntity.getNumber();
        this.title = cardEntity.getTitle();
        this.type = cardEntity.getType();
        this.createTime = cardEntity.getCreateTime();
        this.state = cardEntity.getState();
        this.creatorUser = creatorUser;

    }

    public CardVO(CardEntity cardEntity){
        this.id = cardEntity.getId();
        this.number = cardEntity.getNumber();
        this.title = cardEntity.getTitle();
        this.type = cardEntity.getType();
        this.createTime = cardEntity.getCreateTime();
        this.state = cardEntity.getState();
        this.belongFeatureId = cardEntity.getBelongFeatureId();
        this.belongGroupId = cardEntity.getBelongGroupId();
    }




}
