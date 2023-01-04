package cn.gtmap.realestate.common.matcher;

import cn.gtmap.gtc.workflow.clients.manage.ProcessDefinitionClient;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
 * @version 1.0, 2022/10/24
 * @description  大云模型类 V2.x版本适配
 */
@Component
public class ProcessDefinitionClientMatcher {

    @Autowired
    private ProcessDefinitionClient processDefinitionClient;

    /**
     * 根据条件查询流程定义信息
     * @param category 业务分类
     * @return 返回对应的流程定义信息
     */
    public List<ProcessDefData> getProcessDefDataForCategory(String category) {
        return processDefinitionClient.getProcessDefDataForCategory(category);
    }

    /**
     * 根据条件查询流程定义信息
     * @return 返回全部的流程定义信息
     */
    public List<ProcessDefData> getAllProcessDefData(){
        return processDefinitionClient.getAllProcessDefData();
    }

}

