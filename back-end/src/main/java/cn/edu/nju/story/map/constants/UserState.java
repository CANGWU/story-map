package cn.edu.nju.story.map.constants;

import lombok.Getter;

import java.util.Objects;

/**
 * @author xuan
 * @create 2019-01-15 10:09
 **/
@Getter
public enum  UserState {

    VERIFYING(0, "验证中"),
    OK(1, "正常");




    private Integer state;
    private String description;

    private UserState(Integer state, String description){
        this.state = state;
        this.description = description;
    }


    public static UserState getInstance(Integer state){

        for(UserState userState : UserState.values()){

            if(Objects.equals(userState.state, state)){
                return userState;
            }

        }
        return null;

    }
}
