package io.github.mangocrisp.spring.taybct.module.lf;

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
public class LogicFlowApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogicFlowApplication.class, args);
    }
}
