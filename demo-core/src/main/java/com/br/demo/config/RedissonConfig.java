package com.br.demo.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author CleverApe
 * @Classname RedissonConfig
 * @Description Redisson客户端初始化Bean
 * @Date 2019-07-17 10:28
 * @Version V1.0
 */
@Configuration
public class RedissonConfig {

    private static Logger logger = LogManager.getLogger(RedissonConfig.class);

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private String redisPort;

    @Bean
    public RedissonClient getRedisson() {
        Config config = new Config();
        config.setTransportMode(TransportMode.EPOLL);
        config.useClusterServers()
                // use "rediss://" for SSL connection
                .addNodeAddress(redisHost+":"+redisPort);
        RedissonClient redisson = Redisson.create(config);

        logger.info("------------------- Redisson Client Init Succeed -------------------");
        return redisson;
    }
}
