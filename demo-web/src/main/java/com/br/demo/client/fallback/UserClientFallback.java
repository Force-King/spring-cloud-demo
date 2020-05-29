package com.br.demo.client.fallback;

import com.br.demo.client.UserClient;
import com.br.demo.dto.UserDto;
import com.br.demo.enums.RequestResultEnum;
import com.br.demo.params.UserQueryParam;
import com.br.demo.util.RestApiResult;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Description 用户服务接口-熔断、降级
 * @Classname UserClientFallback
 * @Author guifei.qin
 * @Date 2020-05-29 15:56
 * @Version V1.0
 */
@Component
public class UserClientFallback implements FallbackFactory<UserClient> {

    @Override
    public UserClient create(Throwable throwable) {
        return new UserClient() {
            @Override
            public RestApiResult<UserDto> getByUid(UserQueryParam param) {
                return RestApiResult.buildEnum(RequestResultEnum.SERVER_BUSY);
            }
        };
    }
}
