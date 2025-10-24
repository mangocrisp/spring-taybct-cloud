package io.github.mangocrisp.spring.taybct.admin.file;

import io.github.mangocrisp.spring.taybct.tool.cloud.annotation.TayBctCloudConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author xijieyin
 */
@TayBctCloudConfig
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
// 指定要扫描的Mapper类的包的路径
@MapperScan({"io.github.mangocrisp.**.mapper"})
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }
}
