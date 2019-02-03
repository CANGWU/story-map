package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.form.CreateCardForm;
import cn.edu.nju.story.map.utils.BeanUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * CreateCardVO
 *
 * @author xuan
 * @date 2019-01-28
 */
@Data
@NoArgsConstructor
public class CreateCardVO {



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
     * 截止时间
     */
    private Timestamp expireTime;


    /**
     * 估计时间
     */
    private Long evaluateTime;

    /**
     * 所属分组
     */
    private Long belongGroupId;


    /**
     * 所属特性
     */
    private Long belongFeatureId;

    /**
     * 前驱
     */
    private Long precursor;




    public CreateCardVO(CreateCardForm createCardForm) {

        BeanUtils.copyProperties(createCardForm, this);

    }
}
