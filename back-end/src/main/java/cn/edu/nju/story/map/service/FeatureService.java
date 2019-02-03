package cn.edu.nju.story.map.service;

import cn.edu.nju.story.map.vo.CreateFeatureVO;
import cn.edu.nju.story.map.vo.FeatureDetailsVO;
import cn.edu.nju.story.map.vo.ModifyFeatureVO;

/**
 * @author xuan
 * @create 2019-01-30 22:11
 **/
public interface FeatureService {
    /**
     * 创建一个新的特性
     * @param userId
     * @param epicId
     * @param createFeatureVO
     * @return
     */
    FeatureDetailsVO createFeature(Long userId, Long epicId, CreateFeatureVO createFeatureVO);

    /**
     * 删除一个特性
     * @param userId
     * @param featureId
     * @return
     */
    boolean deleteFeatureById(Long userId, Long featureId);

    /**
     * 查询一个特性的详情
     * @param userId
     * @param featureId
     * @return
     */
    FeatureDetailsVO queryFeatureDetailsById(Long userId, Long featureId);

    /**
     * 修改一个特性
     * @param userId
     * @param featureId
     * @param modifyFeatureVO
     * @return
     */
    boolean modifyFeature(Long userId, Long featureId, ModifyFeatureVO modifyFeatureVO);
}
