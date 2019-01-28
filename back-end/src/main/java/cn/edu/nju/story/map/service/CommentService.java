package cn.edu.nju.story.map.service;

import cn.edu.nju.story.map.vo.CommentVO;
import cn.edu.nju.story.map.vo.PageableVO;
import lombok.Data;
import org.springframework.data.domain.Page;

/**
 * CommentService
 *
 * @author xuan
 * @date 2019-01-28
 */
public interface CommentService {

    /**
     * 添加评论
     * @param userId
     * @param cardId
     * @param content
     * @param toCommentId
     * @return
     */
    boolean addComment(Long userId, Long cardId, String content, Long toCommentId);


    /**
     * 删除评论
     * @param userId
     * @param commentId
     * @return
     */
    boolean deleteCommentById(Long userId, Long commentId);


    /**
     * 分页获取card下的评论
     * @param userId
     * @param cardId
     * @param pageable
     * @return
     */
    Page<CommentVO> queryCardComment(Long userId, Long cardId, PageableVO pageable);


    /**
     * 修改评论
     * @param userId
     * @param commentId
     * @param content
     * @return
     */
    boolean modifyComment(Long userId, Long commentId, String content);


}
