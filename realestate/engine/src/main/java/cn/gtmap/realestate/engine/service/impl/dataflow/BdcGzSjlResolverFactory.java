package cn.gtmap.realestate.engine.service.impl.dataflow;

import cn.gtmap.realestate.common.core.dto.engine.BdcGzSjlDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.engine.service.BdcGzSjlService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/4
 * @description 不动产规则数据流逻辑实现
 */
@Service
public class BdcGzSjlResolverFactory {
    /**
     * 数据流执行实例
     */
    private static Map<String, BdcGzSjlService> serviceMap = new ConcurrentHashMap<>(2);

    /**
     * 注册数据流实现类（SQL、服务）
     */
    public static void registerResolver(String type, BdcGzSjlService resolverService){
        serviceMap.put(type, resolverService);
    }


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDTO 子规则
     * @return {Map} 数据流执行结果，保存格式 Map<数据流结果保存变量，执行结果值>
     * @description
     *      获取子规则数据流执行结果
     */
    public <T> Map<String, T> getBdcZgzSjlResult(BdcGzZgzDTO bdcGzZgzDTO) throws Exception {
        if(null == bdcGzZgzDTO || CollectionUtils.isEmpty(bdcGzZgzDTO.getBdcGzSjlDTOList())){
            throw  new AppException(ErrorCode.RULE_NO_ZGZ_SJL_ERROR, "子规则执行异常：未定义子规则或数据流空");
        }

        // 缓存数据流执行结果
        Map<String, T> sjlResultMap = new HashMap<>(bdcGzZgzDTO.getBdcGzSjlDTOList().size());

        // 根据服务或者SQL类型执行对应方式数据获取
        // sjlResultMap 每次作为参数动态传入执行，因为前面的数据流结果可能是下一个数据流的参数
        for (BdcGzSjlDTO bdcGzSjlDTO : bdcGzZgzDTO.getBdcGzSjlDTOList()) {
            sjlResultMap.put(bdcGzSjlDTO.getJgblbs(), this.executeBdcGzSjl(bdcGzSjlDTO, sjlResultMap));
        }
        return sjlResultMap;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzSjlDTO 数据流信息实体
     * @param sjlResultMap 数据流执行结果
     * @return {T} 数据流执行结果
     * @description
     *      调用服务或者SQL处理类执行数据流数据查询
     */
    private <T> T executeBdcGzSjl(BdcGzSjlDTO bdcGzSjlDTO, Map<String, T> sjlResultMap) throws Exception {
        // 先判断数据流类型
        BdcGzSjlService resolver = serviceMap.get(bdcGzSjlDTO.getSjly());

        // 再执行数据流，保存结果
        return resolver.getSjlExecuteResult(bdcGzSjlDTO, sjlResultMap);
    }
}
