package com.br.demo.util;

import com.br.demo.entity.BaseEntity;
import org.springframework.beans.BeanUtils;

/**
 * @Description TODO
 * @Classname ResultUtil
 * @Author guifei.qin
 * @Date 2020-05-28 18:25
 * @Version V1.0
 */
public class RestResultUtil {

    public static <T> RestApiResult<T> success(BaseEntity baseEntity, Class<T> targetClass) throws IllegalAccessException, InstantiationException {
        RestApiResult<T> ret = new RestApiResult<>();
        T target = targetClass.newInstance();
        BeanUtils.copyProperties(baseEntity, target);
        return ret.success(target);
    }

}
