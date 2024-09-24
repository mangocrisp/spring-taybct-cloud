package io.github.mangocrisp.spring.taybct.admin.monitor.config;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import reactor.core.publisher.Mono;

/**
 * @author xijieyin
 * <br> 2021/12/3 9:18
 * 服务状态监听
 */
@Slf4j
@AutoConfiguration
public class StatusChangeNotifier extends AbstractStatusChangeNotifier {

    public StatusChangeNotifier(InstanceRepository repository) {
        super(repository);
    }

    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
        return Mono.fromRunnable(() -> {
            if (event instanceof InstanceStatusChangedEvent) {
                String status = ((InstanceStatusChangedEvent) event).getStatusInfo().getStatus();
                switch (status) {
                    // 健康检查没通过
                    case "DOWN":
                        log.error("健康检查没通过！");
                        break;
                    // 服务离线
                    case "OFFLINE":
                        log.error("服务离线！");
                        break;
                    // 服务上线
                    case "UP":
                        log.info("服务上线！");
                        break;
                    // 服务未知异常
                    case "UNKNOWN":
                        log.error("服务未知异常！");
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
