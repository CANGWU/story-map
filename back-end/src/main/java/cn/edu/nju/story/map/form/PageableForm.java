package cn.edu.nju.story.map.form;

import lombok.Data;
import net.sf.oval.constraint.NotNull;

/**
 * @author xuan
 * @create 2019-01-22 23:14
 **/
@Data
public class PageableForm {

    @NotNull
    private Integer pageSize;

    @NotNull
    private Integer pageNumber;

}
