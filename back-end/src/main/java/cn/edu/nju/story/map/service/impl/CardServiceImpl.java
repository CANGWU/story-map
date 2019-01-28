package cn.edu.nju.story.map.service.impl;

import cn.edu.nju.story.map.service.CardService;
import cn.edu.nju.story.map.vo.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * CardServiceImpl
 *
 * @author xuan
 * @date 2019-01-28
 */

@Service
public class CardServiceImpl implements CardService {
    @Override
    public boolean createCard(Long userId, Long projectId, CreateCardVO createCardVO) {
        return false;
    }

    @Override
    public Page<CardVO> queryProjectCard(Long userId, Long projectId, PageableVO pageableVO) {
        return null;
    }

    @Override
    public boolean deleteCardById(Long userId, Long cardId) {
        return false;
    }

    @Override
    public CardDetailsVO queryCardDetailsById(Long userId, Long cardId) {
        return null;
    }

    @Override
    public boolean modifyCard(Long userId, Long cardId, ModifyCardVO modifyCardVO) {
        return false;
    }
}
