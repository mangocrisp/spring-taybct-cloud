package com.demo;

import com.alibaba.fastjson2.JSONObject;
import io.github.mangocrisp.spring.taybct.tool.core.message.apilog.ApiLogDTO;
import org.junit.Test;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <pre>
 *
 * </pre>
 *
 * @author XiJieYin
 * @since 2024/9/1 03:10
 */
@SpringBootTest
public class Test1 {

    @Test
    public void t1() {
        ApiLogDTO apiLogDTO = new ApiLogDTO();
        apiLogDTO.setClient("1");
        JSONObject from = JSONObject.from(apiLogDTO);
        System.out.println(from);
        //System.out.println(JSONObject.toJSONString(apiLogDTO));
        //System.out.println(apiLogDTO.getPayload());
    }

    @Test
    public void t2() {
        // %n表示换行
        // \33[32;4m表示改变字体
        // \33[0m 变回原样
        // 背景颜色代号(41-46)
        // 前景色代号(31-36)
        //前景色代号和背景色代号可选，就是或可以写，也可以不写
        // 数字+m：1加粗；3斜体；4下划线
        // 格式：System.out.println("\33[前景色代号;背景色代号;数字m");
        for (int background = 41; background <= 46; background++) {
            for (int font = 31; font <= 36; font++) {
                System.out.format("前景色[字体]是%d,背景色是%d------\33[%d;%d;4m我是博主\33[0m %n", font, background, font, background);
            }
        }
    }

    @Test
    public void t3() {
        String appName = "app";
        String localPort = "8080";
        String profile = "dev";
        String applicationVersion = String.format(" 项目版本:[\33[0m\33[31;46;1m%s\33[0m\33[3m\33[46;1m]", "3.3.3");
        StringBuilder sb = new StringBuilder();
        sb.append("\33[0m\r\n\33[3m\33[46;1m        　　 ");
        sb.append("\33[0m\r\n\33[3m\33[46;1m        　　 ＿ ＿");
        sb.append("\33[0m\r\n\33[3m\33[46;1m　　　　　／＞　　   フ");
        sb.append("\33[0m\r\n\33[3m\33[46;1m　　　　　| 　_　 _ l");
        sb.append("\33[0m\r\n\33[3m\33[46;1m 　 　　 ／` ミ＿xノ  ~miao");
        sb.append("\33[0m\r\n\33[3m\33[46;1m　　 　 /　　　 　 |");
        sb.append("\33[0m\r\n\33[3m\33[46;1m 　　　/　 ヽ　　 ﾉ");
        sb.append("\33[0m\r\n\33[3m\33[46;1m　 　 │　　|　|　| ");
        sb.append("\33[0m\r\n\33[3m\33[46;1m　／￣|　　 |　|　|");
        sb.append("\33[0m\r\n\33[3m\33[46;1m | (￣ヽ＿_ヽ_)__)");
        sb.append("\33[0m\r\n\33[3m\33[46;1m 　＼二つ                  ");
        sb.append("\33[0m\r\n\33[46;4m 　                     ");
        sb.append("\33[0m\r\n\33[3m\33[46;1m 　                     ");
        System.out.format("%s" +
                        "\33[0m\r\n\33[3m\33[46;1m 模块：[\33[0m\33[31;46;1m%s\33[0m\33[3m\33[46;1m] 启动完成 " +
                        "\33[0m\r\n\33[3m\33[46;1m 当前使用的端口:[\33[0m\33[31;46;1m%s\33[0m\33[3m\33[46;1m] " +
                        "\33[0m\r\n\33[3m\33[46;1m 环境变量:[\33[0m\33[31;46;1m%s\33[0m\33[3m\33[46;1m] " +
                        "\33[0m\r\n\33[3m\33[46;1m%s" +
                        "\33[0m\r\n\33[3m\33[46;1m spring-taybct 版本:[\33[31;46;1m3.2.x\33[0m\33[3m\33[46;1m] " +
                        "\33[0m\r\n\33[3m\33[46;1m spring boot 版本:[\33[0m\33[31;46;1m%s\33[0m\33[3m\33[46;1m] " +
                        "\33[0m\r\n\33[3m\33[46;1m java 版本:[\33[0m\33[31;46;1m%s\33[0m\33[3m\33[46;1m " +
                        "\33[0m\r\n\33[3m\33[46;1m\33[4m 　                     \33[0m\33[3m\33[46;1m" +
                        "\33[0m\r\n\33[3m\33[46;1m 　                     \33[0m\r\n"
                , sb, appName, localPort, profile, applicationVersion, SpringBootVersion.getVersion(), System.getProperty("java.version"));
    }
}
