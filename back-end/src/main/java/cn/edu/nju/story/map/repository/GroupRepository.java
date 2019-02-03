package cn.edu.nju.story.map.repository;

import cn.edu.nju.story.map.entity.GroupEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * GroupRepository
 *
 * @author xuan
 * @date 2019-01-31
 */
public interface GroupRepository extends PagingAndSortingRepository<GroupEntity, Long> {
}
