package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.entity.CardEntity;
import cn.edu.nju.story.map.utils.BeanUtils;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * CardDetailsVO
 *
 * @author xuan
 * @date 2019-01-28
 */
@Data
public class CardDetailsVO {


    private Long id;

    /**
     * 所属项目Id
     */
    private Long belongProjectId;

    /**
     * 父级卡片Id，必须同属一个项目
     */
    private Long seniorCardId;

    /**
     * 构建卡片数结构，避免循环节点，样式为cardId-cardId
     */
    private String path;

    /**
     * 编号
     * 每个项目内唯一
     */
    private Integer number;

    /**
     * 创建用户
     */
    private UserVO creatorUser;

    /**
     * 卡片标题
     */
    private String title;

    /**
     * 卡片类型
     * {@link cn.edu.nju.story.map.constants.CardType}
     */
    private Integer type;

    /**
     * 内容
     */
    private String context;


    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 故事点
     */
    private Integer storyPoint;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 截止时间
     */
    private Timestamp expireTime;


    /**
     * 估计时间
     */
    private Long evaluateTime;

    /**
     * 状态
     * {@link cn.edu.nju.story.map.constants.CardState}
     */
    private Integer state;

    public CardDetailsVO(CardEntity cardEntity, UserVO userVO){
        BeanUtils.copyProperties(cardEntity, this, "creatorUserId");
        this.creatorUser = userVO;
    }
}
