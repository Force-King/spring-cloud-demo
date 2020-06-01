package com.domain.demo.remote.client;

import com.domain.demo.remote.client.fallback.UserClientFallback;
import com.domain.demo.dto.UserDTO;
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
    RestApiResult<UserDTO> getByUid(@Validated @RequestBody UserQueryParam param);
}
