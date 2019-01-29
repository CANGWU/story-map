package cn.edu.nju.story.map.constants;

import java.util.Objects;

/**
 * @author xuan
 * @create 2019-01-12 23:39
 **/
public enum  CardType {




    STORY(0, "故事卡片"),
    TASK(1, "任务卡片"),
    BUG(2, "Bug卡片");


    private Integer type;

    private String description;


    private CardType(Integer type, String description){
        this.type = type;
        this.description = description;
    }


    public static CardType getInstance(Integer type){

        for(CardType cardType: CardType.values()){
            if(Objects.equals(cardType.type, type)){
                return cardType;
            }
        }
        return null;

    }
}
