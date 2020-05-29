package com.br.demo.util;

import com.br.demo.entity.BaseEntity;
import org.springframework.beans.BeanUtils;

/**
 * @Description Bean转换工具类
 * @Classname BeanUtil
 * @Author CleverApe
 * @Date 2020-05-29 13:46
 * @Version V1.0
 */
public class BeanUtil {

    public static <T> T EntityToDto(BaseEntity baseEntity, Class<T> targetClass) throws IllegalAccessException, InstantiationException {
        T target = targetClass.newInstance();
        BeanUtils.copyProperties(baseEntity, target);
        return target;
    }
}
