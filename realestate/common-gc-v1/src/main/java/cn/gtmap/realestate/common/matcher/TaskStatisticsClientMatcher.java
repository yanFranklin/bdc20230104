package cn.gtmap.realestate.common.matcher;

import cn.gtmap.gtc.workflow.clients.statistics.TaskStatisticsClient;
import cn.gtmap.gtc.workflow.domain.statistics.OrgTaskStatisticsInfo;
import cn.gtmap.gtc.workflow.domain.statistics.UserTaskStatisticsInfo;
import cn.gtmap.gtc.workflow.domain.statistics.UserTaskStatisticsTimeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/03/30
 * @description  大云工作流统计 V1.x版本适配
 */
@Component
public class TaskStatisticsClientMatcher {
    @Autowired
    private TaskStatisticsClient taskStatisticsClient;


    /**
     * 查询部门统计信息
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @param orgId       部门id
     * @param recursion  是否递归查询 如果递归查询会将部门下的子部门信息也进行统计
     * @param category   业务类型
     * @param taskName   任务名称
     * @param statisticsType StatisticsType Enum
     * @return 返回统计信息
     */
    public List<OrgTaskStatisticsInfo> getStatisticsOfAllOrgs(Long startTime, Long endTime, String orgId, boolean recursion, String category, String taskName, String statisticsType) {
        return taskStatisticsClient.getStatisticsOfAllOrgs(startTime, endTime, orgId, recursion, category, taskName, statisticsType);
    }

    /**
     * 按流程-节点统计流程各节点办理时间
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param orgId     部门ID
     * @param category  业务类型
     * @param taskName  任务名称
     * @return
     */
    public List<UserTaskStatisticsTimeDto> getUserStatisticsTime(Long startTime, Long endTime, String orgId, String category, String taskName) {
        return taskStatisticsClient.getUserStatisticsTime(startTime, endTime, orgId, category, taskName);
    }

    /**
     * 查询某个部门的统计信息
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param orgId     部门ID
     * @param category  业务类型
     * @param taskName  任务名称
     * @param statisticsType  StatisticsType Enum
     * @return
     */
    public List<UserTaskStatisticsInfo> getStatisticsByOrg(Long startTime, Long endTime, String orgId, String category, String taskName, String statisticsType) {
        return taskStatisticsClient.getStatisticsByOrg(startTime, endTime, orgId, category, taskName, statisticsType);
    }
}
