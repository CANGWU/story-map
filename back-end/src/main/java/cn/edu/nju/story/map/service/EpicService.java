package cn.edu.nju.story.map.service;

import cn.edu.nju.story.map.vo.CreateEpicVO;
import cn.edu.nju.story.map.vo.EpicDetailsVO;
import cn.edu.nju.story.map.vo.ModifyEpicVO;

/**
 * EpicService
 *
 * @author xuan
 * @date 2019-01-30
 */

public interface EpicService {


    /**
     * 创建一个新的epic
     * @param userId
     * @param projectId
     * @param createEpicVO
     * @return
     */
    EpicDetailsVO createEpic(Long userId, Long projectId, CreateEpicVO createEpicVO);

    /**
     * 删除epic
     * @param userId
     * @param epicId
     * @return
     */
    boolean deleteEpicById(Long userId, Long epicId);

    /**
     * 获取epic详情
     * @param userId
     * @param epicId
     * @return
     */
    EpicDetailsVO queryEpicDetailsById(Long userId, Long epicId);

    /**
     * 修改epic
     * @param userId
     * @param epicId
     * @param modifyEpicVO
     * @return
     */
    boolean modifyEpic(Long userId, Long epicId, ModifyEpicVO modifyEpicVO);
}
