package cn.edu.nju.story.map.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ProjectMembersVO
 *
 * @author xuan
 * @date 2019-01-26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMembersVO {

    /**
     *
     */
    List<ProjectMemebrVO> masterMembers;

    List<ProjectMemebrVO> slaveMembers;


}
