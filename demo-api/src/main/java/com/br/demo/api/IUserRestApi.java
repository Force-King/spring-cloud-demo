package com.br.demo.api;

import com.br.demo.dto.UserDto;
import com.br.demo.params.UserQueryParam;
import com.br.demo.params.UserUpdateParam;
import com.br.demo.util.RestApiResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description 用户RestApi
 * @Classname IUserRestApi
 * @Author guifei.qin
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
