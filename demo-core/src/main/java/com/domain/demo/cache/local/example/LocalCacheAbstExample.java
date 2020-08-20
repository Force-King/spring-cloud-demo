package com.domain.demo.cache.local.example;

import com.domain.demo.cache.local.AbstractGuavaLocalCache;
import com.domain.demo.cache.local.ILocalCache;
import com.domain.demo.dao.UserDao;
import com.domain.demo.entity.User;
import com.google.common.cache.Cache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Description 本地缓存通用接口使用案例
 *              <br/> 分布式环境下，使用该种方式可做到缓存同步
 *              <br/> 这里以User为例，如需实时通知刷新缓存，需维护枚举类：CacheValueTypeEnum，
 *              将type值提供给通知方即可，通知服务localCacheService.notifyCacheUpdate(int key, String type)
 *
 * @author CleverApe
 * @Date 2020-08-20
 * @Version V1.0
 */

@Component
@Slf4j
public class LocalCacheAbstExample extends AbstractGuavaLocalCache<Integer, User> implements ILocalCache<Integer, User> {

    @Autowired
    private UserDao userDao;

    private Cache<Integer, User> cache;

    @PostConstruct
    void init() {
        this.cache = super.getCache(new RemovalListener<Integer, User>() {

            @Override
            public void onRemoval(RemovalNotification<Integer, User> notification) {
                Integer key = LocalCacheAbstExample.super.getExpiredKey(notification);
                if (key != null) {
                    syncLocalCacheFromDB(key);
                }
                log.info("-------- 执行 LocalCache 移除监听回调成功 ---------");
            }
        });
    }

    @Override
    public User getLocalCache(Integer key) {
        User cache = super.get(key);
        return cache != null ? cache : syncLocalCacheFromDB(key);
    }

    @Override
    public User syncLocalCacheFromDB(Integer key) {
        User user = userDao.getUserById(key);
        put(key, user);
        return user;
    }

}
