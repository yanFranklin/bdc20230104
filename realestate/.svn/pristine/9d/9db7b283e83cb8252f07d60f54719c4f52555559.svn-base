package cn.gtmap.realestate.etl.service.td;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2022/6/15
 * @description 土地数据转换策略工厂
 */
@Component
public class TdConvertStrategyFactory {

    private static Map<Integer, TdConvertAbstractService> strategyMap = new ConcurrentHashMap<>();

    /**
     * 根据权利类型获取土地数据转换的实现类
     * @param qllx 权利类型
     * @return 土地数据转换实现类
     */
    public TdConvertAbstractService getInvokeService(Integer qllx){
        return strategyMap.get(qllx);
    }

    /**
     * 注册实现类
     * @param qllx    权利类型
     * @param service 土地数据转换实现类
     */
    public static void register(Integer qllx, TdConvertAbstractService service){
        if(null == qllx || null == service){
            return;
        }
        strategyMap.put(qllx, service);
    }
}
