package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.form.ModifyCardForm;
import cn.edu.nju.story.map.utils.BeanUtils;
import lombok.Data;

import java.sql.Timestamp;

/**
 * ModifyCardVO
 *
 * @author xuan
 * @date 2019-01-28
 */
@Data
public class ModifyCardVO {

    /**
     * 标题
     */
    private String title;



    /**
     * 内容
     */
    private String context;

    /**
     * 优先级
     * {@link }
     */
    private Integer priority;

    /**
     * 故事点
     */
    private Integer storyPoint;

    /**
     * 截止时间
     */
    private Timestamp expireTime;

    /**
     * 估计时间
     */
    private Timestamp evaluateTime;

    /**
     * 状态
     * {@link cn.edu.nju.story.map.constants.CardState}
     *
     */
    private Integer state;

    public ModifyCardVO(ModifyCardForm modifyCardForm) {
        BeanUtils.copyProperties(modifyCardForm, this);
    }
}
