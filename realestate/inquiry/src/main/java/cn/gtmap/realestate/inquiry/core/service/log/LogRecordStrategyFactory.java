package cn.gtmap.realestate.inquiry.core.service.log;

import cn.gtmap.realestate.common.util.BeansResolveUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2022-06-02
 * @description 日志记录策略工厂
 */
@Component
public class LogRecordStrategyFactory {

    @Autowired
    BeansResolveUtils beansResolveUtils;

    /**
     * 根据@LogNote注解的custom参数去SpringApplication中找对应日志类型的实现类
     * @param type 日志类型
     * @return 日志类型自定义实现类
     */
    public LogCustomRecordService getCustomLogRecord(String type){
        return beansResolveUtils.getBeanByName(type);
    }
}
