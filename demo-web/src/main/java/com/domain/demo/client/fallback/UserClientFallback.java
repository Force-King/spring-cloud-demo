package com.domain.demo.client.fallback;

import com.domain.demo.client.UserClient;
import com.domain.demo.dto.UserDTO;
import com.domain.demo.enums.RequestResultEnum;
import com.domain.demo.params.UserQueryParam;
import com.domain.demo.util.RestApiResult;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Description 用户服务接口-熔断、降级
 * @Classname UserClientFallback
 * @Author CleverApe
 * @Date 2020-05-29 15:56
 * @Version V1.0
 */
@Component
public class UserClientFallback implements FallbackFactory<UserClient> {

    @Override
    public UserClient create(Throwable throwable) {
        return new UserClient() {
            @Override
            public RestApiResult<UserDTO> getByUid(UserQueryParam param) {
                return RestApiResult.buildEnum(RequestResultEnum.SERVER_BUSY);
            }
        };
    }
}
