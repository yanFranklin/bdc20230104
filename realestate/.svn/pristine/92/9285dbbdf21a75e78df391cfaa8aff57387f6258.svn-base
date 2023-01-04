package cn.gtmap.realestate.certificate.core.support.config;

import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @description 电子证照初始化
 */
@Component
public class InitConfig {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @EventListener
    public void init(ApplicationReadyEvent applicationReadyEvent){
        Constants.responseMap = ResponseEnum.getResponseEnumMap();
        logger.info("responseMap is inited！");
    }
}
