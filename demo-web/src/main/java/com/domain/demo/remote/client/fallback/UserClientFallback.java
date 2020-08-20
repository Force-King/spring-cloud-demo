package com.domain.demo.remote.client.fallback;

import com.domain.demo.params.UserUpdateParam;
import com.domain.demo.remote.client.UserClient;
import com.domain.demo.dto.UserDTO;
import com.domain.demo.enums.RequestResultEnum;
import com.domain.demo.params.UserQueryParam;
import com.domain.demo.util.RestApiResult;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Description 用户服务接口-熔断、降级
 * @Classname UserClientFallback
 * @Author CleverApe
 * @Date 2020-05-29 15:56
 * @Version V1.0
 */
@Component
@Slf4j
public class UserClientFallback implements FallbackFactory<UserClient> {

    @Override
    public UserClient create(Throwable throwable) {
        log.error("异常信息：{}", throwable.toString());
        return new UserClient() {
            @Override
            public RestApiResult<UserDTO> getByUid(UserQueryParam param) {
                return RestApiResult.buildEnum(RequestResultEnum.SERVER_BUSY);
            }

            @Override
            public String addUser(UserUpdateParam param) {
                return null;
            }

            @Override
            public String updateUser(UserUpdateParam param) {
                return null;
            }
        };
    }
}
