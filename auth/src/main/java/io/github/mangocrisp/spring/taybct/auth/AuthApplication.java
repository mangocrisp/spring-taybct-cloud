package io.github.mangocrisp.spring.taybct.auth;

import io.github.mangocrisp.spring.taybct.tool.cloud.annotation.TayBctCloudConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@TayBctCloudConfig
@SpringBootApplication
@EnableDubbo
public class AuthApplication {
    public static void main(String[] args) {

        SpringApplication.run(AuthApplication.class, args);

    }
}
