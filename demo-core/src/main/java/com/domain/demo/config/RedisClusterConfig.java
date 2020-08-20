package com.domain.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @Description Redis集群初始化配置
 * @Classname RedisClusterConfig
 * @Author CleverApe
 * @Date 2020-08-20
 * @Version V1.0
 */
@Configuration
@Slf4j
public class RedisClusterConfig {

    @Value("${spring.redis.cluster.nodes}")
    private String clusterNodeStr;

    @Value("${spring.redis.timeou}")
    private int timeout;

    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWait;

    @Bean
    public JedisCluster getJedisCluster() {
        String[] cNodes = clusterNodeStr.split(",");
        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        for (String node : cNodes) {
            String[] hp = node.split(":");
            nodes.add(new HostAndPort(hp[0], Integer.parseInt(hp[1])));
        }

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        JedisCluster jedisCluster = new JedisCluster(nodes, timeout, maxIdle, jedisPoolConfig);
        log.info("------------------- RedisCluster Init Succeed 😊 -------------------");
        return jedisCluster;
    }
}
