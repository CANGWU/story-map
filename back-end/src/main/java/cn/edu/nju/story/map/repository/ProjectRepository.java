package cn.edu.nju.story.map.repository;

import cn.edu.nju.story.map.entity.ProjectEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * ProjectRepository
 *
 * @author xuan
 * @date 2019-01-28
 */
public interface ProjectRepository extends PagingAndSortingRepository<ProjectEntity, Long> {


    /**
     * 是否出现该标识的项目
     * @param sign
     * @return
     */
    boolean existsBySign(String sign);

}
