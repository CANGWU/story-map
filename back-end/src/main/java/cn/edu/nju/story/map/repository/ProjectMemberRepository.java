package cn.edu.nju.story.map.repository;

import cn.edu.nju.story.map.entity.ProjectMemberEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * ProjectMemberRepository
 *
 * @author xuan
 * @date 2019-01-28
 */
public interface ProjectMemberRepository extends PagingAndSortingRepository<ProjectMemberEntity, Long> {


    boolean existsByProjectIdAndUserId(Long projectId, Long userId);
}
