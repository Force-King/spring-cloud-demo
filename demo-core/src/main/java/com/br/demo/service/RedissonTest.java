package com.br.demo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author guifei.qin
 * @Classname RedissonTest
 * @Description TODO
 * @Date 2019-07-16 20:15
 * @Version V1.0
 */
public class RedissonTest {

    private static Logger logger = LogManager.getLogger(RedissonTest.class);

    @Autowired
    private RedissonClient redissonClient;

    public static void main(String[] args) {
        Executor executor = Executors.newFixedThreadPool(10);

        executor.execute(new Runnable() {

            @Override
            public void run() {
                logger.info("---进入线程："+Thread.currentThread().getName());
                new RedissonTest().testLock();
            }
        });
    }

    public  void testLock() {
        RLock lock = redissonClient.getLock("ReentrantLock");
        try {
            boolean res = lock.tryLock(2, 10, TimeUnit.SECONDS);
            if (res) {
                logger.info("---线程" + Thread.currentThread().getName() + "获取锁---");
                Thread.sleep(200);
            }
        } catch (Exception e) {
            logger.error("获取锁异常："+e);
        } finally {
            lock.unlock();
            logger.info("---线程" + Thread.currentThread().getName() + "释放锁---");
        }
    }
}
