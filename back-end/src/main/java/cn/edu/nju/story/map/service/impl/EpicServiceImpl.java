package cn.edu.nju.story.map.service.impl;

import cn.edu.nju.story.map.service.EpicService;
import cn.edu.nju.story.map.service.PermissionService;
import cn.edu.nju.story.map.vo.CreateEpicVO;
import cn.edu.nju.story.map.vo.EpicDetailsVO;
import cn.edu.nju.story.map.vo.ModifyEpicVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * EpicServiceImpl
 *
 * @author xuan
 * @date 2019-01-30
 */
@Service
@Slf4j
public class EpicServiceImpl implements EpicService {

    @Autowired
    PermissionService permissionService

    @Override
    public EpicDetailsVO createEpic(Long userId, Long projectId, CreateEpicVO createEpicVO) {
        return null;
    }

    @Override
    public boolean deleteEpicById(long userId, Long epicId) {
        return false;
    }

    @Override
    public EpicDetailsVO queryEpicDetailsById(long userId, Long epicId) {
        return null;
    }

    @Override
    public boolean modifyEpic(long userId, Long epicId, ModifyEpicVO modifyEpicVO) {
        return false;
    }
}
