package cn.edu.nju.story.map.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * CreateEpicForm
 *
 * @author xuan
 * @date 2019-01-30
 */
@Data
public class CreateEpicForm {


    /**
     * 前驱
     */
    @ApiModelProperty("位置前驱，如果位于最前传则不传递")
    private Long precursor;

    /**
     * epic标题
     */
    @NotNull
    @NotBlank
    private String name;


}
