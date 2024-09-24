package com.demo;

import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.baomidou.dynamic.datasource.toolkit.CryptoUtils;
import io.github.mangocrisp.spring.taybct.tool.core.util.sm.SM2Coder;
import io.github.mangocrisp.spring.taybct.tool.core.util.sm.SM2Properties;
import io.github.mangocrisp.spring.taybct.tool.core.util.sm.SM4Coder;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class CryptoUtilsTest {

    @SneakyThrows
    @Test
    public void test1() {
        String[] strings = CryptoUtils.genKeyPair(512);
        System.out.println("私钥：" + strings[0]);
        System.out.println("公钥：" + strings[1]);
        String password = CryptoUtils.encrypt(strings[0], "password");
        System.out.println(password);
        String decrypt = CryptoUtils.decrypt(strings[1], password);
        System.out.println(decrypt);
    }

    @SneakyThrows
    @Test
    public void test2() {
        SM2Properties properties = new SM2Properties();
        SM2Coder.ini(properties);
        String password = SM2Coder.encryptWebData("password");
        System.out.println(password);
        String decrypted = SM2Coder.decryptWebData(password);
        System.out.println(decrypted);
    }

    @SneakyThrows
    @Test
    public void test3() {
        SymmetricCrypto sm4 = SM4Coder.getSM4();
        String url = sm4.encryptBase64("jdbc:postgresql://localhost:5432/taybct?currentSchema=public");
        String username = sm4.encryptBase64("postgres");
        String password = sm4.encryptBase64("password");
        String driverClass = sm4.encryptBase64("org.postgresql.Driver");
        System.out.println(url);
        System.out.println(username);
        System.out.println(password);
        System.out.println(driverClass);
        System.out.println(sm4.decryptStr(url));
        System.out.println(sm4.decryptStr(username));
        System.out.println(sm4.decryptStr(password));
        System.out.println(sm4.decryptStr(driverClass));
    }
}
