package cn.edu.nju.story.map.service;

import cn.edu.nju.story.map.vo.CreateGroupVO;
import cn.edu.nju.story.map.vo.GroupDetailsVO;
import cn.edu.nju.story.map.vo.ModifyGroupVO;

/**
 * @author xuan
 * @create 2019-01-30 22:11
 **/
public interface GroupService {
    /**
     * 创建一个新的分组
     * @param userId
     * @param projectId
     * @param createGroupVO
     * @return
     */
    GroupDetailsVO createGroup(long userId, Long projectId, CreateGroupVO createGroupVO);

    /**
     * 删除一个特性
     * @param userId
     * @param groupId
     * @return
     */
    boolean deleteGroupById(long userId, Long groupId);

    /**
     * 查询一个特性的详情
     * @param userId
     * @param groupId
     * @return
     */
    GroupDetailsVO queryGroupDetailsById(long userId, Long groupId);

    /**
     * 修改一个特性
     * @param userId
     * @param groupId
     * @param modifyGroupVO
     * @return
     */
    boolean modifyGroup(long userId, Long groupId, ModifyGroupVO modifyGroupVO);
}
