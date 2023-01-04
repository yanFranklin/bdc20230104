package cn.gtmap.realestate.common.matcher;

import cn.gtmap.gtc.workflow.clients.manage.ProcessInstanceClient;
import cn.gtmap.gtc.workflow.domain.common.ProcessMonthCreateFrequencyDto;
import cn.gtmap.gtc.workflow.domain.manage.ProcessInstanceData;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/03/24
 * @description  大云工作流接口类V2.x版本适配
 */
@Component
public class ProcessInstanceClientMatcher {
    private static final Logger logger = LoggerFactory.getLogger(ProcessInstanceClientMatcher.class);

    @Autowired
    private ProcessInstanceClient processInstanceClientV2;


    /**
     * 直接启动流程实例（参数个数按照V1.x版本组织，对业务层统一）
     * @param processDefKey             流程定义的唯一标识符
     * @param userName                  用户名
     * @Param verifyRoleDepartment     (验证角色下方部门)不传不验证,传1开启验证
     */
    public TaskData directStartProcessInstance(String processDefKey, String userName, String processInstanceName, String regionCode, Integer days) {
        return processInstanceClientV2.directStartProcessInstance(processDefKey, userName, processInstanceName, days);
    }


    /**
     * 获取流程实例
     * @param processInstanceId
     * @return
     */
    public ProcessInstanceData getProcessInstance(String processInstanceId) {
        return processInstanceClientV2.getProcessInstance(processInstanceId);
    }

    /**
     * 流程启动时间触发接口
     * @param processInsId 流程实例id
     */
    public void exeAfterCreateForProcessStart(String processInsId) {
        processInstanceClientV2.exeAfterCreateForProcessStart(processInsId);
    }

    /**
     * 设定默认启动角色（参数个数按照V1.x版本组织，对业务层统一）
     * @param processDefKey             流程定义的唯一标识符
     * @param roleCodes    角色编码， 英文逗号间隔
     * @Param verifyRoleDepartment     (验证角色下方部门)不传不验证,传1开启验证
     * @return
     * @throws Exception
     */
    public TaskData directStartByRole(String processDefKey, String roleCodes, String regionCode, Integer verifyRoleDepartment, String processInstanceName) {
        return processInstanceClientV2.directStartByRole(processDefKey, roleCodes, processInstanceName);
    }

    /**
     * 设置流程优先级
     * @param procInsId
     * @param priority
     * @param reason
     * @throws Exception
     */
    public void setProcPriority(String procInsId, Integer priority, String reason){
        try {
            processInstanceClientV2.setProcPriority(procInsId, priority, reason);
        } catch (Exception e) {
            logger.error("设置流程优先级异常", e);
        }
    }

    /**
     * 设置任务优先级
     * @param taskId
     * @param priority
     * @param reason
     * @throws Exception
     */
    public void setTaskPriority(String taskId, Integer priority, String reason) {
        try {
            processInstanceClientV2.setTaskPriority(taskId, priority, reason);
        } catch (Exception e) {
            logger.error("设置任务优先级异常", e);
        }
    }

    /**
     * 查询流程每月限制创建配置以及当月已创建数量
     * @param processDefKey 流程定义key
     * @return 返回配置及本月已创建次数
     */
    public Map<String, Integer> countCreateProcessFrequencyForMonth(String processDefKey) {
        ProcessMonthCreateFrequencyDto pmcfd = processInstanceClientV2.countCreateProcessFrequencyForMonth(processDefKey);
        if(null == pmcfd) {
            return null;
        }

        Map<String, Integer> result = new HashMap<>();
        result.put("processConfigNum", pmcfd.getProcessConfigNum());
        result.put("currentMonthCreateNum", pmcfd.getCurrentMonthCreateNum());
        return result;
    }
}
