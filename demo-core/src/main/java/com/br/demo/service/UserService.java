package com.br.demo.service;

import com.br.demo.entity.User;

/**
 * @Description User接口
 * @Classname UserService
 * @Author guifei.qin
 * @Date 2020-05-27 17:00
 * @Version V1.0
 */
public interface UserService {

    User getUserById(Integer uid);
}
