package cn.gtmap.realestate.common.util;

import cn.gtmap.gtc.workflow.clients.manage.FlowableNodeClient;
import cn.gtmap.gtc.workflow.clients.manage.ProcessDefinitionClient;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.gtc.workflow.domain.manage.UserTaskDto;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.matcher.FlowableNodeClientMatcher;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/12/16
 * @description 获取工作流信息
 */
@Component
public class WorkFlowUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkFlowUtils.class);
    @Autowired
    FlowableNodeClientMatcher flowableNodeClient;
    @Autowired
    ProcessDefinitionClient processDefinitionClient;
    @Autowired
    ProcessTaskClient processTaskClient;

    /**
     * @param gzlslid
     * @return List<UserTaskData>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 根据工作流实例id获取节点信息
     */
    public List<UserTaskDto> getUserTaskByGzlslid(String gzlslid) {
        if (StringUtils.isEmpty(gzlslid)) {
            throw new MissingArgumentException("获取平台用户任务信息缺少参数gzlslid，请检查对应参数是否正确！");
        }
        return flowableNodeClient.getAllUserTaskByProcInsId(gzlslid);
    }

    /**
     * @param gzlslid
     * @return List<UserTaskData>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取审核节点信息
     */
    public List<UserTaskDto> listShjdxx(String gzlslid) {
        if (StringUtils.isEmpty(gzlslid)) {
            throw new MissingArgumentException("gzlslid,请检查在获取审核节点信息时传入的参数是否正确！");
        }
        List<UserTaskDto> userTaskDataList = null;
        try {
            userTaskDataList = getUserTaskByGzlslid(gzlslid);
            userTaskDataList = userTaskDataList.stream().filter(task ->
                    CollectionUtils.isNotEmpty(
                            task.getFormProperties().stream().filter(property ->
                                    StringUtils.equalsIgnoreCase(property.getName(), CommonConstantUtils.BDC_SHJD_PZ) &&
                                            Boolean.valueOf(property.getVariable()))
                                    .collect(Collectors.toList())
                    )
            ).collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.error("获取平台节点信息失败_" + e.getMessage(), e);
            userTaskDataList = initDefaultShjdxx();
        }
        return userTaskDataList;
    }

    /**
     * @param
     * @return List<UserTaskData>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 初始化默认审核节点信息 初审 复审 核定
     */
    public List<UserTaskDto> initDefaultShjdxx() {
        UserTaskDto cs = new UserTaskDto();
        UserTaskDto fs = new UserTaskDto();
        UserTaskDto hd = new UserTaskDto();
        cs.setName(CommonConstantUtils.BDC_SHJD_CS);
        cs.setId(CommonConstantUtils.BDC_SHJDID_CS);
        fs.setName(CommonConstantUtils.BDC_SHJD_FS);
        fs.setId(CommonConstantUtils.BDC_SHJDID_FS);
        hd.setName(CommonConstantUtils.BDC_SHJD_HD);
        hd.setId(CommonConstantUtils.BDC_SHJDID_HD);
        return Lists.newArrayList(cs, fs, hd);
    }

    /**
     * @param
     * @return 流程定义信息
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取流程定义信息
     * suspensionState : 1 正常 2 锁定
     */
    public List<ProcessDefData> getAllProcessDefData() {
        return processDefinitionClient.getAllProcessDefData();
    }

    /**
     * @param gzldyid
     * @return return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 根据工作流定义ID获取流程信息
     * suspensionState : 1 正常 2 锁定
     */
    public ProcessDefData getAllProcessDefDataByGzldyid(String gzldyid) {
        if(StringUtils.isBlank(gzldyid)){
            throw new MissingArgumentException("根据工作流定义ID获取流程信息");
        }
        return processDefinitionClient.getProcessDefByProcessDefKey(gzldyid);
    }

    /**
     * @param
     * @return 节点信息
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取节点信息
     */
    public List<UserTaskDto> getUserTasks(String processDefKey) {
        return flowableNodeClient.getAllUserTaskByProcDefKey(processDefKey);
    }

    /**
     * @param taskId
     * @return TaskData
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据任务ID获取任务信息
     */
    public TaskData getTaskById(String taskId) {
        return processTaskClient.getTaskById(taskId);
    }
}
