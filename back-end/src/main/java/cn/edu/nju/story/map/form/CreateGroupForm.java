package cn.edu.nju.story.map.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * @author xuan
 * @create 2019-01-30 22:28
 **/
@Data
public class CreateGroupForm {


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
