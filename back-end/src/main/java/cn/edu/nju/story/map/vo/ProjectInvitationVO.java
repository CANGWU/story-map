package cn.edu.nju.story.map.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ProjectInvitaiotnVO
 *
 * @author xuan
 * @date 2019-01-26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectInvitationVO {


    private Long invitationId;

    private String projectName;



}
