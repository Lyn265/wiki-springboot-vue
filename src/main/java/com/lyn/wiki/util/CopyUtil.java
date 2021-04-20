package com.lyn.wiki.util;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class CopyUtil {
    /**
     * 单体对象复制
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T copy(Object source,Class<T> clazz){
        if(source == null){
            return null;
        }
        T obj = null;
        try {
            obj = clazz.getDeclaredConstructor().newInstance();
        }catch (Exception e){
            e.getStackTrace();
            return null;
        }
        BeanUtils.copyProperties(source,obj);
        return obj;
    }

    /**
     * 列表复制
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> copyList(List source, Class<T> clazz){
        List<T> target = new ArrayList<>();
        if (!CollectionUtils.isEmpty(source)){
            for (Object obj : source) {
                T newObj = copy(obj,clazz);
                target.add(newObj);
            }
        }
        return target;
    }
}
