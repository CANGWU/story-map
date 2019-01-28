package cn.edu.nju.story.map.service;

import cn.edu.nju.story.map.vo.*;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author xuan
 * @create 2019-01-22 23:21
 **/
public interface ProjectService {


    /**
     * 创建新的项目
     * @param userId 创建者用户Id
     * @param name
     * @param sign
     * @param description
     * @param newMemberList
     * @return
     */

    boolean createProject(Long userId, String name, String sign, String description, List<InviteProjectMemberVO> newMemberList);

    /**
     * 获取项目的详情
     * @param userId
     * @param projectId
     * @return
     */
    ProjectDetailsVO queryProjectDetailsById(Long userId, Long projectId);


    /**
     * 修改项目信息
     * @param userId
     * @param projectId
     * @param modifyProjectVO
     * @return
     */
    boolean modifyProject(Long userId, Long projectId, ModifyProjectVO modifyProjectVO);


    /**
     * 删除项目，仅创建者可删除
     * @param userId
     * @param projectId
     * @return
     */
    boolean deleteProjectById(Long userId, Long projectId);


    /**
     * 分页获取我参与的项目
     * @param userId
     * @param pageableVO
     * @return
     */
    Page<ProjectVO> queryJoinedProject(Long userId, PageableVO pageableVO);


    /**
     * 获取我创建的项目
     * @param userId
     * @param pageableVO
     * @return
     */
    Page<ProjectVO> queryMyProject(Long userId, PageableVO pageableVO);




}
