package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.entity.ProjectEntity;
import cn.edu.nju.story.map.utils.BeanUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.sql.Timestamp;

/**
 * ProjectDetailsVO
 *
 * @author xuan
 * @date 2019-01-26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDetailsVO {

    /**
     * 项目Id
     */
    private Long id;

    /**
     * 项目名
     */
    private String name;

    /**
     * 项目标识
     */
    private String sign;

    /**
     * 项目描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 创建用户
     */
    private UserVO creatorUser;


    public ProjectDetailsVO(ProjectEntity projectEntity, UserVO userVO){
        BeanUtils.copyProperties(projectEntity, this, "creatorUserId");
        this.creatorUser = userVO;
    }

}
