package io.github.mangocrisp.spring.taybct.module.system.controller;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysDict;
import io.github.mangocrisp.spring.taybct.module.system.controller.impl.SysDictControllerRegister;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysDictService;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiVersion;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.WebLog;
import io.github.mangocrisp.spring.taybct.tool.core.request.SqlQueryParams;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统字典相关接口
 *
 * @author xijieyin <br> 2022/8/5 21:26
 * @see SysDict
 * @see ISysDictService
 * @since 1.0.0
 */
@RestController
@Slf4j
public class SysDictController extends SysDictControllerRegister {

    @WebLog
    @Override
    public R<List<? extends SysDict>> list(SysDict dto, SqlQueryParams sqlQueryParams) {
        log.info("v1 这种继承修改是不改版本号，只是修改框架里面的可能不满足的部分");
        return R.data(getBaseService().customizeList(dto, sqlQueryParams));
    }

    @Operation(summary = "获取列表")
    @PostMapping("list")
    @WebLog
    @ApiVersion(2)
    public R<List<? extends SysDict>> listV2(@RequestBody SysDict dto, SqlQueryParams sqlQueryParams, Integer i) {
        log.info("v2 这种修改是完全不用框架里面的方法，自定义一个方法, i{}", i);
        return R.data(getBaseService().customizeList(dto, sqlQueryParams));
    }

}
