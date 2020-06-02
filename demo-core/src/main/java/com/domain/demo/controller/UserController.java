package com.domain.demo.controller;

import com.domain.demo.api.IUserRestApi;
import com.domain.demo.dto.UserDTO;
import com.domain.demo.params.UserQueryParam;
import com.domain.demo.params.UserUpdateParam;
import com.domain.demo.service.UserService;
import com.domain.demo.util.RestApiResult;
import com.domain.demo.util.RestResultUtil;
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
    public RestApiResult<UserDTO> getByUid(@Validated @RequestBody UserQueryParam param) {
        try{
            return RestResultUtil.success(userService.getUserById(param.getUid()), UserDTO.class);
        } catch (Exception e) {
            log.error("e:",e);
        }
        return RestApiResult.faild();
    }

    @ApiOperation("新增用户")
    @Override
    public String addUser(@Validated @RequestBody UserUpdateParam param) {
        return null;
    }

    @ApiOperation("更新用户")
    @Override
    public String updateUser(@Validated @RequestBody UserUpdateParam param) {
        return null;
    }
}
