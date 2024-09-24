package io.github.mangocrisp.spring.taybct.module.system.controller;

import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiLog;
import io.github.mangocrisp.spring.taybct.tool.core.constant.OperateType;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author XiJieYin <br> 2024/5/13 16:25
 */
@RestController
@RequestMapping("demo")
public class DemoController {

    @GetMapping("test1/{name}")
    @ApiLog(title = "test1", description = "test1", type = OperateType.QUERY)
    public R<?> test1(@PathVariable String name) {
        return R.data(name);
    }
}
