package cn.edu.nju.story.map.utils;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ListIndexUtils
 *
 * @author xuan
 * @date 2019-01-31
 */
public class ListIndexUtils {


    /**
     * 调整列表的顺序
     * @param indexList
     * @param precursor
     * @param newResourceId
     * @return
     */
    public static void adjustIndexList(List<Long> indexList, Long precursor, Long newResourceId){
        if(Objects.isNull(precursor)){
            indexList.add(0, newResourceId);
        }else {
            int index = indexList.indexOf(precursor);
            if(index == -1){
                indexList.add(newResourceId);
            }else {
                indexList.add(index + 1, newResourceId);
            }
        }
    }





}
