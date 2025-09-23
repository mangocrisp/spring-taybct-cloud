package io.github.mangocrisp.spring.taybct.module.od;

import io.github.mangocrisp.spring.taybct.tool.cloud.annotation.TayBctCloudConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 启动类
 *
 * @author xijieyin
 */
@TayBctCloudConfig
@SpringBootApplication
@Slf4j
@EnableCaching
public class OnlineDocApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineDocApplication.class, args);
    }
}
