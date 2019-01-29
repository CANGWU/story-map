package cn.edu.nju.story.map.service.impl;

import cn.edu.nju.story.map.constants.CardState;
import cn.edu.nju.story.map.constants.ErrorCode;
import cn.edu.nju.story.map.constants.PrivilegeGroup;
import cn.edu.nju.story.map.constants.ProjectMemberState;
import cn.edu.nju.story.map.entity.CardEntity;
import cn.edu.nju.story.map.entity.ProjectMemberEntity;
import cn.edu.nju.story.map.entity.UserEntity;
import cn.edu.nju.story.map.exception.DefaultErrorException;
import cn.edu.nju.story.map.repository.CardRepository;
import cn.edu.nju.story.map.repository.ProjectMemberRepository;
import cn.edu.nju.story.map.repository.UserRepository;
import cn.edu.nju.story.map.service.CardService;
import cn.edu.nju.story.map.service.PermissionService;
import cn.edu.nju.story.map.utils.BeanUtils;
import cn.edu.nju.story.map.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.smartcardio.Card;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * CardServiceImpl
 *
 * @author xuan
 * @date 2019-01-28
 */

@Service
public class CardServiceImpl implements CardService {


    @Autowired
    ProjectMemberRepository projectMemberRepository;

    @Autowired
    PermissionService permissionService;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    UserRepository userRepository;

    private ReentrantLock lock = new ReentrantLock();

    @Override
    public  CardDetailsVO createCard(Long userId, Long projectId, CreateCardVO createCardVO) {

        // 是否具备足够的权限
        if(!permissionService.hasMasterPrivilege(userId, projectId)){
            throw new DefaultErrorException(ErrorCode.FORBIDDEN);
        }

        CardEntity newCard = new CardEntity();
        BeanUtils.copyProperties(createCardVO, newCard);
        newCard.setBelongProjectId(projectId);
        newCard.setCreatorUserId(userId);
        newCard.setState(CardState.NEW.getState());
        newCard.setCreateTime(new Timestamp(System.currentTimeMillis()));

        // 加锁锁定number
        try {
            lock.lock();
            Optional<CardEntity> maxNumberCardOptional = cardRepository.findFirstByBelongProjectIdOrderByNumberDesc(projectId);

            if (maxNumberCardOptional.isPresent()) {
                newCard.setNumber(maxNumberCardOptional.get().getNumber() + 1);
            } else {
                newCard.setNumber(1);
            }
            cardRepository.save(newCard);
        }catch (Exception ignore) {
            throw new DefaultErrorException(ErrorCode.DATA_BASE_EXCEPTION);
        }finally {
            lock.unlock();
        }
        Optional<UserEntity> creatorUser = userRepository.findById(userId);

        return new CardDetailsVO(newCard, creatorUser.map(UserVO::new).orElse(null));    }

    @Override
    public Page<CardVO> queryProjectCard(Long userId, Long projectId, PageableVO pageableVO) {

        if(!permissionService.hasSimplePrivilege(userId, projectId)){
            throw new DefaultErrorException(ErrorCode.FORBIDDEN);
        }

        Page<CardEntity> cardEntities = cardRepository.findByBelongProjectIdOrderByCreateTimeDesc(projectId, PageRequest.of(pageableVO.getPageNumber(), pageableVO.getPageSize()));

        if(cardEntities.isEmpty()){

            return new PageImpl<>(new ArrayList<>(), cardEntities.getPageable(), cardEntities.getTotalElements());
        }else {
            Map<Long, UserVO> userVOMap = StreamSupport.stream(userRepository.findAllById(cardEntities.map(CardEntity::getCreatorUserId).getContent()).spliterator(), true).map(UserVO::new).collect(Collectors.toMap(UserVO::getId, i -> i));
            return cardEntities.map( i -> new CardVO(i, userVOMap.get(i.getCreatorUserId())));

        }

    }

    @Override
    public boolean deleteCardById(Long userId, Long cardId) {

        Optional<CardEntity> cardEntityOptional = cardRepository.findById(cardId);

        if(!cardEntityOptional.isPresent()){
            throw new DefaultErrorException(ErrorCode.CARD_NOT_EXISTED);
        }

        if(!permissionService.hasMasterPrivilege(userId, cardEntityOptional.get().getBelongProjectId())){
            throw new DefaultErrorException(ErrorCode.FORBIDDEN);
        }

        cardRepository.deleteById(cardId);

        return true;
    }

    @Override
    public CardDetailsVO queryCardDetailsById(Long userId, Long cardId) {


        Optional<CardEntity> cardEntityOptional = cardRepository.findById(cardId);

        if(!cardEntityOptional.isPresent()){
            throw new DefaultErrorException(ErrorCode.CARD_NOT_EXISTED);
        }

        if(!permissionService.hasSimplePrivilege(userId, cardEntityOptional.get().getBelongProjectId())){
            throw new DefaultErrorException(ErrorCode.FORBIDDEN);
        }

        Optional<UserEntity> creatorUser = userRepository.findById(cardEntityOptional.get().getCreatorUserId());

        return new CardDetailsVO(cardEntityOptional.get(), creatorUser.map(UserVO::new).orElse(null));
    }

    @Override
    public boolean modifyCard(Long userId, Long cardId, ModifyCardVO modifyCardVO) {

        Optional<CardEntity> cardEntityOptional = cardRepository.findById(cardId);

        if(!cardEntityOptional.isPresent()){
            throw new DefaultErrorException(ErrorCode.CARD_NOT_EXISTED);
        }

        if(!permissionService.hasMasterPrivilege(userId, cardEntityOptional.get().getBelongProjectId())){
            throw new DefaultErrorException(ErrorCode.FORBIDDEN);
        }

        CardEntity cardEntity = cardEntityOptional.get();

        BeanUtils.copyPropertiesSkipNull(modifyCardVO, cardEntity);

        cardRepository.save(cardEntity);

        return true;
    }
}
