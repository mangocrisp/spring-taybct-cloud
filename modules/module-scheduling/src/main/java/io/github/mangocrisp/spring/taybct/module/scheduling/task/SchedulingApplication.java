package io.github.mangocrisp.spring.taybct.module.scheduling.task;

import feign.Logger;
import io.github.mangocrisp.spring.taybct.tool.cloud.annotation.TayBctCloudConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

/**
 * @author xijieyin <br> 2022/11/1 19:04
 * @since 1.1.0
 */
@TayBctCloudConfig
@SpringBootApplication
@EnableCaching
public class SchedulingApplication {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    public static void main(String[] args) {
        SpringApplication.run(SchedulingApplication.class, args);
    }

}
