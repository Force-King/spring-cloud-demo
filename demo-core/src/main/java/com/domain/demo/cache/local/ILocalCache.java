package com.domain.demo.cache.local;

/**
 * @author CleverApe
 * @Classname ILocalCache
 * @Description 本地缓存接口
 * @Date 2020-08-20
 * @Version V1.0
 */
public interface ILocalCache<K, V> {

    /**
     * 从本地缓存中获取数据
     * @param key
     * @return
     */
    V getLocalCache(K key);

    /**
     * 将数据库信息同步到缓存
     * @param key
     * @return
     */
    V syncLocalCacheFromDB(K key);

}
