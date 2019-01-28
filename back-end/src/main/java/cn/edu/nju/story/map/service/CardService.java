package cn.edu.nju.story.map.service;

import cn.edu.nju.story.map.vo.*;
import org.springframework.data.domain.Page;

/**
 * CardServicee
 *
 * @author xuan
 * @date 2019-01-28
 */
public interface CardService {


    /**
     * 创建新的卡片
     * @param userId
     * @param projectId
     * @param createCardVO
     * @return
     */
    boolean createCard(Long userId, Long projectId, CreateCardVO createCardVO);


    /**
     * 分页获取项目中的卡片
     * @param userId
     * @param projectId
     * @param pageableVO
     * @return
     */
    Page<CardVO> queryProjectCard(Long userId, Long projectId, PageableVO pageableVO);

    /**
     * 删除项目中的卡片
     * @param userId
     * @param cardId
     * @return
     */
    boolean deleteCardById(Long userId, Long cardId);

    /**
     * 获取卡片的详情
     * @param userId
     * @param cardId
     * @return
     */
    CardDetailsVO queryCardDetailsById(Long userId, Long cardId);

    /**
     * 修改项目的详情
     * @param userId
     * @param modifyCardVO
     * @param cardId
     * @return
     */
    boolean modifyCard(Long userId, Long cardId, ModifyCardVO modifyCardVO);



}
