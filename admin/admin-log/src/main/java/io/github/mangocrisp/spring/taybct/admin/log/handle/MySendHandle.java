package io.github.mangocrisp.spring.taybct.admin.log.handle;

import com.alibaba.fastjson2.JSONObject;
import io.github.mangocrisp.spring.taybct.admin.log.domain.ApiLog;
import io.github.mangocrisp.spring.taybct.admin.log.service.IApiLogService;
import io.github.mangocrisp.spring.taybct.tool.core.message.IMessageSendService;
import io.github.mangocrisp.spring.taybct.tool.core.message.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author xijieyin
 * <br> 2022/7/27 11:06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MySendHandle implements IMessageSendService {

    final IApiLogService apiLogService;

    @Override
    public void send(Message message) {
        // 日志管理模块，自己就是管理模块，所以这里直接就写库了
        ApiLog apiLog = JSONObject.parseObject(message.getPayload(), ApiLog.class);
        apiLogService.save(apiLog);
    }

}
