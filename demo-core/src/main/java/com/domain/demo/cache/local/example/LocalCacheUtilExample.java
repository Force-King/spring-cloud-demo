package com.domain.demo.cache.local.example;

import com.domain.demo.entity.User;
import com.domain.demo.util.LocalCacheUtil;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author CleverApe
 * @Classname LocalCacheUtilExample
 * @Description 本地缓存工具类使用案例
 * @Date 2019-03-18 18:43
 * @Version V1.0
 */

@Slf4j
public class LocalCacheUtilExample {

    private static class CacheExampleListener implements RemovalListener<Integer, User> {

        @Override
        public void onRemoval(RemovalNotification<Integer, User> removalNotification) {

            //Do something
            log.info("-------- 执行 LocalCache 移除监听回调成功 ---------");
        }
    }

    public static void main(String[] args) {

        LocalCacheUtil<Integer, User> cache = new LocalCacheUtil(new CacheExampleListener());

        User user = new User();
        user.setUid(100001);
        user.setUserName("用户名");
        user.setCreateTime(new Date());
        cache.put(user.getUid(), user);

    }
}
