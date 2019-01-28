package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.form.PageableForm;
import cn.edu.nju.story.map.utils.BeanUtils;
import lombok.Data;
import org.springframework.data.domain.Sort;

/**
 * @author xuan
 * @create 2019-01-22 23:39
 **/
@Data
public class PageableVO {

    private Integer pageSize;

    private Integer pageNumber;

    private Sort sort;

    public PageableVO(PageableForm pageableForm){
        BeanUtils.copyProperties(pageableForm, this);
        if(this.pageSize == null){
            this.pageSize = 20;
        }
        if(this.pageNumber == null){
            this.pageSize = 0;
        }
    }
}