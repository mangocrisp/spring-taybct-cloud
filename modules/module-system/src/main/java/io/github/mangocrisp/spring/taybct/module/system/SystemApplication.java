package io.github.mangocrisp.spring.taybct.module.system;

import io.github.mangocrisp.spring.taybct.module.system.listener.CloseListener;
import io.github.mangocrisp.spring.taybct.module.system.listener.StartListener;
import io.github.mangocrisp.spring.taybct.tool.cloud.annotation.TayBctCloudConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 启动类
 *
 * @author xijieyin
 */
//@EnableDubbo
@TayBctCloudConfig
@SpringBootApplication
@Slf4j
@EnableCaching
@EnableDubbo
public class SystemApplication {

    public static void main(String[] args) {
        long beginTime = System.currentTimeMillis();
        SpringApplication sa = new SpringApplication(SystemApplication.class);
        // 程序启动监听器
        sa.addListeners(new StartListener());
        // 程序停止监听器
        sa.addListeners(new CloseListener());
        sa.run(args);
        long time = System.currentTimeMillis() - beginTime;
        log.info("\33[40;32;0m" + "启动成功！耗时 : " + time + "\t\t\t\33[0m");
    }

}
