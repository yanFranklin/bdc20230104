package cn.gtmap.realestate.portal.ui.service.impl;

import cn.gtmap.realestate.common.core.ex.AppException;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 不动产工作流相关分解器
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0, 2019/3/4
 */
@Service
public class BdcWorkFlowServiceFactory {
    /**
     * service 集合
     */
    private static final Map<String, BdcWorkFlowAbstactService> serviceMap = new HashedMap(2);

    /**
     * 注册服务到集合中
     *
     * @param version                   版本
     * @param bdcWorkFlowAbstactService 工作流服务
     */
    public static void registerService(String version, BdcWorkFlowAbstactService bdcWorkFlowAbstactService) {
        serviceMap.put(version, bdcWorkFlowAbstactService);
    }

    /**
     * 获取工作流服务
     *
     * @return 工作流服务
     */
    public static BdcWorkFlowAbstactService getWorkFlowService(String version) {
        return serviceMap.get(version);
    }

}
