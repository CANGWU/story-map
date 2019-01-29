package cn.edu.nju.story.map.repository;

import cn.edu.nju.story.map.entity.ProjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    /**
     * 项目是否由改用户创建
     * @param projectId
     * @param creatorUserId
     * @return
     */
    boolean existsByIdAndCreatorUserId(Long projectId, Long creatorUserId);


    /**
     * 分页获取我创建的项目
     * @param creatorUserId
     * @param pageable
     * @return
     */
    Page<ProjectEntity> findByCreatorUserIdOrderByCreateTimeDesc(Long creatorUserId, Pageable pageable);


}
