package com.domain.demo.api;

import com.domain.demo.dto.UserDto;
import com.domain.demo.params.UserQueryParam;
import com.domain.demo.params.UserUpdateParam;
import com.domain.demo.util.RestApiResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description 用户RestApi
 * @Classname IUserRestApi
 * @Author CleverApe
 * @Date 2020-05-29 13:17
 * @Version V1.0
 */
@RequestMapping("/user")
public interface IUserRestApi {


    @PostMapping("/getByUid")
    RestApiResult<UserDto> getByUid(@Validated @RequestBody UserQueryParam param);

    @PostMapping("/addUser")
    String addUser(@RequestParam UserUpdateParam param);

    @PostMapping("/updateUser")
    String updateUser(@RequestParam UserUpdateParam param);

}
