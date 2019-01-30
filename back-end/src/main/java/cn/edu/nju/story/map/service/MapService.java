package cn.edu.nju.story.map.service;

import cn.edu.nju.story.map.vo.TargetLocationVO;

/**
 * MapService
 *
 * @author xuan
 * @date 2019-01-30
 */
public interface MapService {

    /**
     * 移动epic位置
     * @param userId
     * @param epicId
     * @param targetLocationVO
     * @return
     */
    boolean moveEpicLocation(long userId, Long epicId, TargetLocationVO targetLocationVO);

    /**
     * 移动feature位置
     * @param userId
     * @param featureId
     * @param targetLocationVO
     * @return
     */
    boolean moveFeatureLocation(long userId, Long featureId, TargetLocationVO targetLocationVO);

    /**
     * 移动卡片的位置
     * @param userId
     * @param cardId
     * @param targetLocationVO
     * @return
     */
    boolean moveCardLocation(long userId, Long cardId, TargetLocationVO targetLocationVO);
}
