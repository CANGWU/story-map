package cn.edu.nju.story.map.repository;

import cn.edu.nju.story.map.entity.CardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

/**
 * CardRepository
 *
 * @author xuan
 * @date 2019-01-29
 */
public interface CardRepository extends PagingAndSortingRepository<CardEntity, Long> {


    /**
     * 获取一个项目中的最大number
     * @param projectId
     * @return
     */
    Optional<CardEntity> findFirstByBelongProjectIdOrderByNumberDesc(Long projectId);


    /**
     * 分页获取项目中的卡片
     * @param projectId
     * @param pageable
     * @return
     */
    Page<CardEntity> findByBelongProjectIdOrderByCreateTimeDesc(Long projectId, Pageable pageable);

    /**
     * 分页获取项目中没有map的卡片
     * @param projectId
     * @param pageable
     * @return
     */
    Page<CardEntity> findByBelongProjectIdAndBelongFeatureIdIsNullAndBelongGroupIdIsNullOrderByCreateTimeDesc(Long projectId, Pageable pageable);

    /**
     * 根据featureId查询卡片
     * @param featureId
     * @return
     */
    boolean existsByBelongFeatureId(Long featureId);



    /**
     * 根据groupId查询卡片
     * @param groupId
     * @return
     */
    boolean existsByBelongGroupId(Long groupId);


    /**
     * 获取项目中已经map的卡片
     * @param projectId
     * @return
     */
    List<CardEntity> findByBelongProjectIdAndBelongFeatureIdIsNotNullAndBelongGroupIdIsNotNull(Long projectId);


}
