package com.br.demo.service.impl;

import com.br.demo.dao.UserDao;
import com.br.demo.entity.User;
import com.br.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Classname UserServiceImpl
 * @Author CleverApe
 * @Date 2020-05-27 17:01
 * @Version V1.0
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer uid) {
        log.info("uid:{}",uid);
        return userDao.getUserById(uid);
    }
}
