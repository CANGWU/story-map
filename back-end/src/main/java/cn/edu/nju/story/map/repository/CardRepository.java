package cn.edu.nju.story.map.repository;

import cn.edu.nju.story.map.entity.CardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

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



}
