package com.demo;

import io.github.mangocrisp.spring.taybct.tool.core.util.sm.SM2Coder;
import io.github.mangocrisp.spring.taybct.tool.core.util.sm.SM2Properties;
import io.github.mangocrisp.spring.taybct.tool.core.util.sm.SM3Coder;
import io.github.mangocrisp.spring.taybct.tool.core.util.sm.SM4Coder;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author XiJieYin <br> 2024/6/13 17:50
 */
@SpringBootTest
public class SMDemo {

    @SneakyThrows
    @Test
    public void sm2Key() {
        SM2Coder.genSM2CACert("sm2", "gx-cloud", 7776000
                , certificateBuilder -> {
                });
    }

    @SneakyThrows
    @Test
    public void sm3Key() {
        SM3Coder.genSM3SecretKey();
    }

    @SneakyThrows
    @Test
    public void sm4Key() {
        SM4Coder.genSM4SecretKey();
    }

    @SneakyThrows
    @Test
    public void userPassword() {
        SM3Coder.En sm3 = new SM3Coder.En();
        SM4Coder.En sm4 = new SM4Coder.En();
        SM2Coder.ini(new SM2Properties());
        // 前端登录
        System.out.println(SM2Coder.encryptWebData("123456"));
        // 047cf8e34845d1af2e7070a13838f57ea8c3965b9fb6da7a9a86787c90cbe24b47640f0e5cb328096cc22af0df9168d15c923c6e7cdac089827c52f8b0534f52566843612af121f18a7ea0c7cd5dc88359104524cc164b3de508fae8fe5f93b74e5e8685674bba
        String sm3En = sm3.apply("123456");
        System.out.println(sm3En);
        // 9b8e883d8cd671a466c3fce1df202554819adf51ab3b39caca161e01f2049dc6
        String apply = sm4.apply(sm3En);
        // 数据库存储
        System.out.println(apply);
        // 3fdMgY4y5FGuLkP2W/hCqk0EHGpjW8PS8g380muddkNFhmmnSlorKuKsZKvR+v1Zys2lzjP0+Qf2MFrzZ+5GyHrH6EumGlRo7oE7edUMTZw=
        System.out.println(sm4.apply("440111xxxx1044"));
    }
}
