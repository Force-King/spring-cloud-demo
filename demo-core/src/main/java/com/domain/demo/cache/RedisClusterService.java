package com.domain.demo.cache;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.params.SetParams;

import java.util.Map;
import java.util.Objects;

/**
 * @author CleverApe
 * @Classname RedisClusterService
 * @Description RedisCluster服务类
 * @Date 2020-08-20
 * @Version V1.0
 */
@Component
@Slf4j
public class RedisClusterService {

    @Autowired
    private JedisCluster jedisCluster;

    /**
     * String类型get
     *
     * @param key
     * @return
     */
    public String get(String key) {
        String ret = null;
        try {
            ret = jedisCluster.get(key);
        } catch (Exception e) {
            log.error("key = {}, Exception:", key, e);
        }
        return ret;
    }

    /**
     * String类型set
     *
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    public String setex(String key, String value, int seconds) {
        String ret = null;
        try {
            ret = jedisCluster.setex(key, seconds, value);
        } catch (Exception e) {
            log.error("key = {}, value = {}, seconds = {}, Exception:", key, value, seconds, e);
        }
        return ret;
    }

    /**
     * setnx <br/>ps:用于分布式锁
     *
     * @param key
     * @param value
     * @return
     */
    public Long setnx(String key, String value) {
        Long ret = null;
        try {
            ret = jedisCluster.setnx(key, value);
        } catch (Exception e) {
            log.error("key = {}, value = {}, Exception:", key, value, e);
        }
        return ret;
    }

    /**
     * 删除缓存
     *
     * @param key
     */
    public void delete(String key) {
        try {
            jedisCluster.del(key);
        } catch (Exception e) {
            log.error("key={}, Exception:", key, e);
        }
    }

    /**
     * hash类set
     *
     * @param key
     * @param field
     * @param value
     * @param seconds 过期时间
     * @return
     */
    public boolean hset(String key, String field, String value, Integer seconds) {
        boolean rs = false;
        try {
            jedisCluster.hset(key, field, value);
            if (seconds != null) {
                jedisCluster.expire(key, seconds);
            }
            rs = true;
        } catch (Exception e) {
            log.error("key = {}, value = {}, ttl = {}s , Exception:", key, value, seconds, e);
        }
        return rs;
    }

    /**
     * hash类型get
     *
     * @param key
     * @param field
     * @return
     */
    public String hget(String key, String field) {
        String ret = null;
        try {
            ret = jedisCluster.hget(key, field);
        } catch (Exception e) {
            log.error("key = {}, field = {}, Exception:", key, field, e);
        }
        return ret;
    }

    /**
     * 注意：集群环境，此方法慎用，性能较差
     *
     * @param key
     * @return
     */
    public Map<String, String> hgetAll(String key) {
        Map<String, String> ret = null;
        try {
            ret = jedisCluster.hgetAll(key);
        } catch (Exception e) {
            log.error("key = {}, Exception:", key, e);
        }
        return ret;
    }

    /**
     * 注意：集群环境，此方法慎用，性能可能较差
     *
     * @param key
     * @param hash
     * @return
     */
    public String hmset(String key, Map<String, String> hash) {
        String ret = null;
        try {
            ret = jedisCluster.hmset(key, hash);
        } catch (Exception e) {
            log.error("key = {}, hash = {}, Exception:", key, hash, e);
        }
        return ret;
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @return
     */
    public boolean exists(String key) {
        boolean rs = false;
        try {
            rs = jedisCluster.exists(key);
        } catch (Exception e) {
            log.error("key = {}, Exception:", key, e);
        }
        return rs;
    }

    /**
     * 带过期时间的hmset
     *
     * @param key     缓存key
     * @param map     完整的哈希表结构
     * @param seconds 缓存过期时间
     * @return
     */
    public boolean hsetAll(String key, Map<String, String> map, Integer seconds) {
        boolean rs = false;
        try {
            jedisCluster.hmset(key, map);
            if (seconds != null) {
                jedisCluster.expire(key, seconds);
            }
            rs = true;
        } catch (Exception e) {
            log.error("key = {}, value = {}, ttl = {}s , Exception:", key, JSONObject.toJSONString(map), seconds, e);
        }
        return rs;
    }

    /**
     * setnx + ex
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean setNxAndEx(String key, String value, int time) {
        try {
            SetParams setParams = new SetParams();
            setParams.nx();
            setParams.ex(time);
            value = jedisCluster.set(key, value, setParams);

            return Objects.equals("OK", value);
        } catch (Exception e) {
            log.error("key = {}, value = {}, Exception:", key, value, e);
        }
        return false;
    }

    /**
     * hash长度
     * @param key
     * @return
     */
    public long hlen(String key) {
        try {
            return jedisCluster.hlen(key);
        } catch (Exception e) {
            log.error("key = {}, Exception:", key, e);
            return 0;
        }
    }


}
