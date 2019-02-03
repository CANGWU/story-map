package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.form.CreateGroupForm;
import cn.edu.nju.story.map.utils.BeanUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * @author xuan
 * @create 2019-01-30 22:28
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateGroupVO {

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

    public CreateGroupVO(CreateGroupForm createGroupForm){
        BeanUtils.copyPropertiesSkipNull(createGroupForm, this);
    }

}
