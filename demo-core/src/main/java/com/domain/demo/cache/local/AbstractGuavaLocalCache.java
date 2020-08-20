package com.domain.demo.cache.local;

import com.google.common.cache.*;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author CleverApe
 * @Classname LocalCache
 * @Description 本地缓存抽象类 <br/> 通用接口场景建议使用该类
 * @Date 2020-08-20
 * @Version V1.0
 */
public abstract class AbstractGuavaLocalCache<K, V> {

    private static final int DEFAULT_SIZE = 100;
    private static final int MAX_SIZE = 1000000;
    private static final int EXPIRE_TIME = 5;
    private TimeUnit timeUnit = TimeUnit.MINUTES;

    protected Cache<K, V> cache;

    public Cache<K, V> getCache(RemovalListener<K, V> removalListener) {

        Executor executor = Executors.newSingleThreadExecutor();
        RemovalListener<K, V> removalListenerAsyn = RemovalListeners.asynchronous(removalListener, executor);

        cache = CacheBuilder.newBuilder()
                .initialCapacity(DEFAULT_SIZE)
                .maximumSize(MAX_SIZE)
                .expireAfterWrite(EXPIRE_TIME, timeUnit)
                .removalListener(removalListenerAsyn)
                .build();
        return cache;
    }

    public Cache<K, V> getCache(RemovalListener<K, V> removalListener, int expireTime) {

        Executor executor = Executors.newSingleThreadExecutor();
        RemovalListener<K, V> removalListenerAsyn = RemovalListeners.asynchronous(removalListener, executor);

        cache = CacheBuilder.newBuilder()
                .initialCapacity(DEFAULT_SIZE)
                .maximumSize(MAX_SIZE)
                .expireAfterWrite(expireTime, timeUnit)
                .removalListener(removalListenerAsyn)
                .build();
        return cache;
    }


    public V get(K key) {
        return cache.getIfPresent(key);
    }

    public void put(K key, V value) {
        cache.put(key, value);
        //TODO put 缓存时，发送MQ,通知其他节点同步缓存

    }

    public void remove(K key) {
        cache.asMap().remove(key);
    }

    public K getExpiredKey(RemovalNotification<K, V> notification) {
        if (notification.getCause() == RemovalCause.REPLACED) {
            return null;
        }
        K key = notification.getKey();
        return key;
    }


}
