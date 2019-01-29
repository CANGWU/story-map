package cn.edu.nju.story.map.service;

/**
 * PermissionService
 *
 * @author xuan
 * @date 2019-01-29
 */
public interface PermissionService {


    /**
     *  拥有控制权限
     * @param userId
     * @param projectId
     * @return
     */
    boolean hasMasterPrivilege(Long userId, Long projectId);

    /**
     * 拥有普通及以上权限
     * @param userId
     * @param projectId
     * @return
     */
    boolean hasSimplePrivilege(Long userId, Long projectId);


    /**
     * 拥有项目最高权限
     * @param userId
     * @param projectId
     * @return
     */
    boolean hasCreatorPrivilege(Long userId, Long projectId);

}
