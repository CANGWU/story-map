package cn.edu.nju.story.map.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author xuan
 * @create 2019-01-13 00:01
 **/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class CommentEntity {


    @Id
    @GeneratedValue
    private Long id;


    /**
     * 评论人用户Id
     */
    private Long userId;


    /**
     * 评论的卡片Id
     */
    private Long cardId;


    /**
     * 评论时间
     */
    @Column(name = "create_time")
    private Timestamp createTime;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 是否存在对应评论
     */
    private Long toCommentId;
}
