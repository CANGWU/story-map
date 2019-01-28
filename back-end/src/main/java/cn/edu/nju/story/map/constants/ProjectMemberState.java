package cn.edu.nju.story.map.constants;

import lombok.Getter;

import java.util.Objects;

/**
 * ProjectMemberState
 *
 * @author xuan
 * @date 2019-01-26
 */
@Getter
public enum  ProjectMemberState {


    INVITING(0, "邀请中"),
    OK(1,"正常");




    private Integer state;


    private String description;


    private ProjectMemberState(Integer state, String description){
        this.state = state;
        this.description = description;
    }

    public static ProjectMemberState getInstance(Integer state){

        for(ProjectMemberState projectMemberState : ProjectMemberState.values()){

            if(Objects.equals(projectMemberState.state, state)){
                return projectMemberState;
            }

        }
        return null;

    }

}
