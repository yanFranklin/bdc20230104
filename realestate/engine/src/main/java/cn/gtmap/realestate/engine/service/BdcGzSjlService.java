package cn.gtmap.realestate.engine.service;

import cn.gtmap.realestate.common.core.dto.engine.BdcGzSjlDTO;
import cn.gtmap.realestate.common.core.service.InterfaceCode;

import java.net.URISyntaxException;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/5
 * @description 不动产规则子规则数据流实例接口定义
 */
public interface BdcGzSjlService {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzSjlDTO 数据流
     * @param sjlResultMap 数据流执行结果集
     * @return {Map} 数据流执行结果
     * @description 获取子规则数据流执行结果
     */
    <T> T getSjlExecuteResult(BdcGzSjlDTO bdcGzSjlDTO, Map<String, T> sjlResultMap) throws Exception;
}
