package cn.edu.nju.story.map.form;

import lombok.Data;
import lombok.NonNull;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.Range;

import javax.persistence.Column;
import java.sql.Timestamp;

/**
 * CreateCardForm
 *
 * @author xuan
 * @date 2019-01-28
 */
@Data
public class CreateCardForm {


    /**
     * 卡片标题
     */
    @NotNull
    @NotBlank
    private String title;

    /**
     * 卡片类型
     * {@link cn.edu.nju.story.map.constants.CardType}
     */
    @NotNull
    @Range(max = 2, min = 0, message = "卡片类型")
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
     * 截止时间
     */
    private Timestamp expireTime;


    /**
     * 估计时间
     */
    private Long evaluateTime;





}
