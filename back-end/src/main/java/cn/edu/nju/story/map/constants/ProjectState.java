package cn.edu.nju.story.map.constants;

import lombok.Getter;

import java.util.Objects;

/**
 * @author xuan
 * @create 2019-01-12 12:41
 **/

@Getter
public enum  ProjectState {

    OK(0, "正常运行");


    private Integer state;

    private String description;



    private ProjectState(Integer state, String description){
        this.state = state;
        this.description = description;
    }


    public static ProjectState getInstance(Integer state){

        for(ProjectState projectState : ProjectState.values()){
            if(Objects.equals(projectState.state, state)){
                return projectState;
            }
        }
        return null;
    }


}
