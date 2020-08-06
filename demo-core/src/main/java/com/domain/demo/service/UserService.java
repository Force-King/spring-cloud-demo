package com.domain.demo.service;

import com.domain.demo.entity.User;

/**
 * @Description User接口
 * @Classname UserService
 * @Author CleverApe
 * @Date 2020-05-27 17:00
 * @Version V1.0
 */
public interface UserService {

    User getUserById(Integer uid);

    int addUser(User user);
}
