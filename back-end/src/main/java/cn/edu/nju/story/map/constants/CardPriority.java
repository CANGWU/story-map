package cn.edu.nju.story.map.constants;

import java.util.Objects;

/**
 * CardPriority
 *
 * @author xuan
 * @date 2019-01-29
 */
public enum CardPriority {



    HIGHEST(0, "非常高"),
    HIGH(1, "高"),
    MEDIUM(2, "中"),
    LOW(3, "低");




    private Integer priority;

    private String description;


    private CardPriority(Integer priority, String description){
        this.priority = priority;
        this.description = description;
    }


    public static CardPriority getInstance(Integer priority){

        for(CardPriority cardPriority: CardPriority.values()){
            if(Objects.equals(cardPriority.priority, priority)){
                return cardPriority;
            }
        }
        return null;

    }
}
