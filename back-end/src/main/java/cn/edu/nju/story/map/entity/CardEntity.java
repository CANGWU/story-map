package cn.edu.nju.story.map.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author xuan
 * @create 2019-01-12 23:34
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "card")
public class CardEntity {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 所属项目Id
     */
    @Column(name = "belong_pro_id")
    private Long belongProjectId;

    /**
     * 父级卡片Id，必须同属一个项目
     */
    private Long senior_card_id;

    /**
     * 构建卡片数结构，避免循环节点，样式为cardId-cardId
     */
    private String path;

    /**
     * 编号
     * 每个项目内唯一
     */
    private Integer number;

    /**
     * 创建用户Id
     */
    @Column(name = "creator_user_id")
    private Long creatorUserId;

    /**
     * 卡片标题
     */
    private String title;

    /**
     * 卡片类型
     * {@link cn.edu.nju.story.map.constants.CardType}
     */
    private Integer type;

    /**
     * 内容
     */
    private String context;


    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 故事点
     */
    private Integer storyPoint;

    /**
     * 截止时间
     */
    @Column(name = "expire_time")
    private Timestamp expireTime;


    /**
     * 估计时间
     */
    @Column(name = "evaluate_time")
    private Long evaluateTime;

    /**
     * 状态
     * {@link cn.edu.nju.story.map.constants.CardState}
     */
    private Integer state;






}
