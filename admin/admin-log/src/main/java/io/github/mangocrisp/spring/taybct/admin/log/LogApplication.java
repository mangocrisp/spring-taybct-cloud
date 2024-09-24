package io.github.mangocrisp.spring.taybct.admin.log;

import feign.Logger;
import io.github.mangocrisp.spring.taybct.tool.cloud.annotation.TayBctCloudConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author xijieyin
 * <br> 2021/12/11 16:58
 */
@TayBctCloudConfig
@SpringBootApplication
public class LogApplication {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    public static void main(String[] args) {
        SpringApplication.run(LogApplication.class, args);
    }
}
