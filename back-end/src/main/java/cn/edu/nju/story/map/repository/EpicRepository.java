package cn.edu.nju.story.map.repository;

import cn.edu.nju.story.map.entity.EpicEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * EpicRepository
 *
 * @author xuan
 * @date 2019-01-31
 */
public interface EpicRepository extends PagingAndSortingRepository<EpicEntity, Long> {
}
