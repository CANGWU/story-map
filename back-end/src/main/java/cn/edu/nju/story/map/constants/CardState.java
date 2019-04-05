package cn.edu.nju.story.map.constants;

import lombok.Getter;

import java.util.Objects;

/**
 * @author xuan
 * @create 2019-01-12 23:52
 **/
@Getter
public enum  CardState {


    NEW(0, "新建中"),
    WORKING(1, "开发中"),
    VERIFYING(2, "验证中"),
    FINISH(3, "已完成");




    private Integer state;

    private String description;


    private CardState(Integer state, String description){
        this.state = state;
        this.description = description;
    }


    public static CardState getInstance(Integer state){

        for(CardState cardState: CardState.values()){
            if(Objects.equals(cardState.state, state)){
                return cardState;
            }
        }
        return null;

    }


}
