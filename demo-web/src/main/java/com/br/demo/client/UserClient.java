package com.br.demo.client;

import com.br.demo.client.fallback.UserClientFallback;
import com.br.demo.dto.UserDto;
import com.br.demo.params.UserQueryParam;
import com.br.demo.util.RestApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description 用户服务Client
 * @Classname UserClient
 * @Author guifei.qin
 * @Date 2020-05-29 15:26
 * @Version V1.0
 */
@FeignClient(value = "core-service", fallbackFactory = UserClientFallback.class)
@RequestMapping("/user")
public interface UserClient {

    @PostMapping("/getByUid")
    RestApiResult<UserDto> getByUid(@Validated @RequestBody UserQueryParam param);
}
