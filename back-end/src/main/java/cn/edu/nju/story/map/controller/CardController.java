package cn.edu.nju.story.map.controller;

import cn.edu.nju.story.map.form.CreateCardForm;
import cn.edu.nju.story.map.form.ModifyCardForm;
import cn.edu.nju.story.map.form.PageableForm;
import cn.edu.nju.story.map.service.CardService;
import cn.edu.nju.story.map.utils.OvalValidatorUtils;
import cn.edu.nju.story.map.utils.UserIdUtils;
import cn.edu.nju.story.map.vo.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;

/**
 * CardController
 *
 * @author xuan
 * @date 2019-01-16
 */
@RestController
@RequestMapping("/card")
public class CardController {



    @Autowired
    CardService cardService;


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "在项目下创建一个新的卡片", response = CardDetailsVO.class)
    public CardDetailsVO createCard(ServletRequest request, @RequestParam Long projectId, @RequestBody CreateCardForm createCardForm){

        long userId = UserIdUtils.praseUserIdFromRequest(request);
        OvalValidatorUtils.validate(createCardForm);
        return cardService.createCard(userId, projectId, new CreateCardVO(createCardForm));
    }


    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "分页获取项目中的卡片", response = CardVO.class)
    public Page<CardVO> queryProjectCard(ServletRequest request, @RequestParam Long projectId, @RequestBody PageableForm pageableForm){

        OvalValidatorUtils.validate(pageableForm);
        long userId = UserIdUtils.praseUserIdFromRequest(request);
        return cardService.queryProjectCard(userId, projectId, new PageableVO(pageableForm));
    }


    @RequestMapping(value = "/{cardId}",method = RequestMethod.DELETE)
    @ApiOperation(value = "删除卡片", response = boolean.class)
    public boolean deleteCardById(ServletRequest request, @PathVariable("cardId") Long cardId){

        long userId = UserIdUtils.praseUserIdFromRequest(request);
        return cardService.deleteCardById(userId, cardId);
    }

    @RequestMapping(value = "/{cardId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取卡片详情", response = CardDetailsVO.class)
    public CardDetailsVO queryCardDetailsById(ServletRequest request, @PathVariable("cardId") Long cardId){

        long userId = UserIdUtils.praseUserIdFromRequest(request);
        return cardService.queryCardDetailsById(userId, cardId);
    }


    @RequestMapping(value = "/{cardId}", method = RequestMethod.PUT)
    @ApiOperation(value = "修改卡片详情", response = boolean.class)
    public boolean modifyCard(ServletRequest request, @PathVariable("cardId") Long cardId, @RequestBody ModifyCardForm modifyCardForm){

        OvalValidatorUtils.validate(modifyCardForm);
        long userId = UserIdUtils.praseUserIdFromRequest(request);
        return cardService.modifyCard(userId, cardId, new ModifyCardVO(modifyCardForm));
    }


}
