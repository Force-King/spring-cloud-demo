package com.domain.demo.aspect;

import com.alibaba.fastjson.JSONObject;
import com.domain.demo.enums.RequestResultEnum;
import com.domain.demo.util.RestApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @Description Controller 统一返回封装
 * @Classname ResultResponseAdvice
 * @Author CleverApe
 * @Date 2020-08-03 14:29
 * @Version V1.0
 */
@ControllerAdvice
@Slf4j
public class ResultResponseAdvice implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // 下面处理统一返回结果（统一code、msg、sign 加密等）
        if(body == null) {
            return null;
        } else if(body instanceof RestApiResult) {
            return body;
        } else {
            JSONObject jsonBody = (JSONObject) JSONObject.toJSON(body);
            if("400".equals(jsonBody.getString("status"))) {
                body = RestApiResult.buildEnum(RequestResultEnum.PARAMETER_IS_NULL);
            }
            if("500".equals(jsonBody.getString("status"))) {
                body = RestApiResult.buildEnum(RequestResultEnum.SERVER_EXP);
            }
        }
        return body;
    }


}
