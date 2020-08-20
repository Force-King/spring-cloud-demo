package com.domain.demo.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalListeners;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author CleverApe
 * @Classname LocalCacheUtil
 * @Description GuavaCache工具类
 * @Date 2020-08-20
 * @Version V1.0
 */
public final class LocalCacheUtil<K, V> {

    private static final Logger logger = LogManager.getLogger(LocalCacheUtil.class);

    private static final int DEFAULT_SIZE = 100;
    private static final int MAX_SIZE = 1000000;
    private static final int EXPIRE_TIME  = 120;
    private TimeUnit timeUnit = TimeUnit.MINUTES;

    private Cache<K, V> cache;

    public LocalCacheUtil(RemovalListener<K, V> removalListener) {
        Executor executor = Executors.newSingleThreadExecutor();
        RemovalListener<K, V> removalListenerAsyn = RemovalListeners.asynchronous(removalListener, executor);

        this.cache = CacheBuilder.newBuilder()
                .initialCapacity(DEFAULT_SIZE)
                .maximumSize(MAX_SIZE)
                .expireAfterAccess(EXPIRE_TIME, timeUnit)
                .removalListener(removalListenerAsyn)
                .build();
    }

    public V get(K key) {
        return cache.getIfPresent(key);
    }

    public void put(K key, V value) {
        cache.put(key, value);
    }

    public void remove(K key) {
        cache.asMap().remove(key);
    }
}
