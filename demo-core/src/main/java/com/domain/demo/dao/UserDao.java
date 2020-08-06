package com.domain.demo.dao;

import com.domain.demo.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @Description 用户DAO
 * @Classname UserDao
 * @Author CleverApe
 * @Date 2020-05-27 16:47
 * @Version V1.0
 */
@Repository
public interface UserDao {

    /**
     * 通过uid查询用户信息
     * @param uid
     * @return
     */
    User getUserById(Integer uid);

    int addUser(User user);
}
