package cn.edu.nju.story.map.repository;

import cn.edu.nju.story.map.entity.ProjectMemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

/**
 * ProjectMemberRepository
 *
 * @author xuan
 * @date 2019-01-28
 */
public interface ProjectMemberRepository extends PagingAndSortingRepository<ProjectMemberEntity, Long> {


    /**
     * 用户和项目是否存在关系
     * @param projectId
     * @param userId
     * @return
     */
    boolean existsByProjectIdAndUserId(Long projectId, Long userId);

    /**
     * 获取用户和项目的关系
     * @param projectId
     * @param userId
     * @return
     */
    Optional<ProjectMemberEntity> findByProjectIdAndUserId(Long projectId, Long userId);

    /**
     * 获取用户和项目的关系
     * @param id
     * @param userId
     * @return
     */
    Optional<ProjectMemberEntity> findByIdAndUserId(Long id, Long userId);


    /**
     * 用户和项目是否存在有效关系
     * @param projectId
     * @param userId
     * @param state
     * @param belongPrivilegeGroup
     * @return
     */
    boolean existsByProjectIdAndUserIdAndStateAndBelongPrivilegeGroup(Long projectId, Long userId, Integer state, Integer belongPrivilegeGroup);


    /**
     * 获取用户参与的项目
     * @param userId
     * @param state
     * @return
     */
    Page<ProjectMemberEntity> findByUserIdAndStateOrderByCreateTimeDesc(Long userId, Integer state, Pageable pageable);


    /**
     * 根据项目id和用户查询关系
     * @param projectId
     * @param userIds
     * @return
     */
    List<ProjectMemberEntity> findByProjectIdAndUserIdIn(Long projectId, List<Long> userIds);


    /**
     * 获取项目中的成员
     * @param projectId
     * @param state
     * @return
     */
    List<ProjectMemberEntity> findByProjectIdAndState(Long projectId, Integer state);

    /**
     * 删除项目成员
     * @param projectId
     */
    void deleteByProjectId(Long projectId);
}
