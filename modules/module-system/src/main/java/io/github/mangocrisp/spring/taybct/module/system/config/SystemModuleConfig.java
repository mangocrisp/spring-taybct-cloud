package io.github.mangocrisp.spring.taybct.module.system.config;

import cn.afterturn.easypoi.handler.inter.IExcelDictHandler;
import io.github.mangocrisp.spring.taybct.api.system.handle.ExcelDictHandlerImpl;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysDictService;
import io.github.mangocrisp.spring.taybct.tool.core.poi.easypoi.service.IExcelService;
import io.github.mangocrisp.spring.taybct.tool.core.poi.easypoi.service.impl.ExcelServiceImpl;
import io.github.mangocrisp.spring.taybct.tool.core.poi.easypoi.util.EasyPOIUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

/**
 * <pre>
 * 系统模块配置
 * </pre>
 *
 * @author XiJieYin
 * @since 2024/12/9 15:08
 */
@AutoConfiguration
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
public class SystemModuleConfig {


    @Bean
    public IExcelDictHandler excelDictHandler(ISysDictService sysDictService) {
        IExcelDictHandler excelDictHandler = new ExcelDictHandlerImpl(sysDictService::cache);
        EasyPOIUtil.excelDictHandler = excelDictHandler;
        return excelDictHandler;
    }

    @Bean
    public IExcelService excelService() {
        return new ExcelServiceImpl();
    }

}
