package cn.edu.nju.story.map.constants;

import lombok.Data;
import lombok.Getter;

import java.util.Objects;

/**
 * @author xuan
 * @create 2019-01-12 23:26
 **/

@Getter
public enum  PrivilegeGroup {



    NO(-1, "与你无关"),
    MASTER(0, "大佬大佬"),
    SLAVE(1, "只能看戏的一般人");



    private Integer level;

    private String description;


    private PrivilegeGroup(Integer level, String description){
        this.level = level;
        this.description = description;
    }

    public static PrivilegeGroup getInstance(Integer level){

        for (PrivilegeGroup privilegeGroup : PrivilegeGroup.values()){

            if(Objects.equals(privilegeGroup.level, level)){
                return privilegeGroup;
            }
        }

        return null;


    }


}
