package com.domain.demo.service.impl;

import com.domain.demo.annotation.DataSourceAnnotation;
import com.domain.demo.dao.UserDao;
import com.domain.demo.entity.User;
import com.domain.demo.enums.DBName;
import com.domain.demo.service.UserService;
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

    @Override
    @DataSourceAnnotation(dbName = DBName.DEMO)
    public int addUser(User user) {
        return userDao.addUser(user);
    }
}
