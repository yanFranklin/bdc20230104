package cn.gtmap.realestate.common.matcher;

import cn.gtmap.gtc.workflow.clients.define.v1.ProcessModelClient;
import cn.gtmap.gtc.workflow.domain.define.ProcessModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/03/24
 * @description  大云模型类 V2.x版本适配
 */
@Component
public class ProcessModelClientMatcher {
    @Autowired
    private ProcessModelClient processModelClient;


    /**
     * 根据条件查询流程定义信息
     * @param name 名称
     * @param category 业务分类
     * @param statusKey 流程定义状态
     * @param userName 创建人员
     * @param modelType 流程类型
     * @return 返回对应的流程定义信息
     */
    public List<ProcessModel> queryProcessModelsByCondition(String name, String category, String statusKey, String userName, Integer modelType) {
        return processModelClient.queryProcessModelsAll(null, null, userName, category, 0);
    }
}
