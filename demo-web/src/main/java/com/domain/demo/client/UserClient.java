package com.domain.demo.client;

import com.domain.demo.client.fallback.UserClientFallback;
import com.domain.demo.dto.UserDto;
import com.domain.demo.params.UserQueryParam;
import com.domain.demo.util.RestApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description 用户服务Client
 * @Classname UserClient
 * @Author CleverApe
 * @Date 2020-05-29 15:26
 * @Version V1.0
 */
@FeignClient(value = "core-service", fallbackFactory = UserClientFallback.class)
@RequestMapping("/user")
public interface UserClient {

    @PostMapping("/getByUid")
    RestApiResult<UserDto> getByUid(@Validated @RequestBody UserQueryParam param);
}
