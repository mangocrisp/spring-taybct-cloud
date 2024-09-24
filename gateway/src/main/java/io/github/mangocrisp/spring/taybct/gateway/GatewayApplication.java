package io.github.mangocrisp.spring.taybct.gateway;

import io.github.mangocrisp.spring.taybct.tool.core.auto.ControllerRegisterAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xijieyin
 * <br> 2021/12/2 15:45
 * 网关启动类
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, ControllerRegisterAutoConfigure.class})
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
