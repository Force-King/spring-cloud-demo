package com.domain.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringCloudApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = { "com.domain.demo.remote.client"})
@Slf4j
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(WebApplication.class);
        springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);
        log.info("----------- WebApplication Start Success ------------");
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
