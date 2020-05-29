package com.br.demo.controller;

import com.br.demo.api.IUserRestApi;
import com.br.demo.dto.UserDto;
import com.br.demo.params.UserQueryParam;
import com.br.demo.params.UserUpdateParam;
import com.br.demo.service.UserService;
import com.br.demo.util.RestApiResult;
import com.br.demo.util.RestResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 用户Rest接口实现
 * @Classname UserController
 * @Author CleverApe
 * @Date 2020-05-14 14:31
 * @Version V1.0
 */
@RestController
@Slf4j
@Api(value = "用户")
public class UserController implements IUserRestApi {


    @Autowired
    private UserService userService;


    @ApiOperation("查询用户")
    @Override
    public RestApiResult<UserDto> getByUid(@Validated @RequestParam UserQueryParam param) {
        try{
            return RestResultUtil.success(userService.getUserById(param.getUid()),UserDto.class);
        } catch (Exception e) {
            log.error("e:",e);
        }
        return RestApiResult.faild();
    }

    @ApiOperation("新增用户")
    @Override
    public String addUser(@Validated @RequestParam UserUpdateParam param) {
        return null;
    }

    @ApiOperation("更新用户")
    @Override
    public String updateUser(@Validated @RequestParam UserUpdateParam param) {
        return null;
    }
}
