package cn.edu.nju.story.map.form;

import lombok.Data;
import net.sf.oval.constraint.Range;

import java.sql.Timestamp;

/**
 * ModifyCardForm
 *
 * @author xuan
 * @date 2019-01-28
 */
@Data
public class ModifyCardForm {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String context;


    /**
     *  优先级
     */
    @Range(max = 3, min = 0, message = "卡片优先级")
    private Integer priority;

    /**
     *  故事点
     */
    private Integer storyPoint;

    /**
     * 截止时间
     */
    private Timestamp expireTime;


    /**
     * 估算时间
     */
    private Timestamp evaluateTime;

    /**
     * 卡片状态
     */
    @Range(max = 3, min = 0, message = "卡片状态")
    private Integer state;

}
