package com.domain.demo.remote.client;

import com.domain.demo.api.IUserRestApi;
import com.domain.demo.remote.client.fallback.UserClientFallback;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Description 用户服务Client
 * @Classname UserClient
 * @Author CleverApe
 * @Date 2020-05-29 15:26
 * @Version V1.0
 */
@FeignClient(value = "demo-core", fallbackFactory = UserClientFallback.class)
public interface UserClient extends IUserRestApi {}
