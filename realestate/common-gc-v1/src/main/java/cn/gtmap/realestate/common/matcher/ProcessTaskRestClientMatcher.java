package cn.gtmap.realestate.common.matcher;

import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/03/24
 * @description  大云工作任务接口类 V1.x版本适配
 */
@Component
public class ProcessTaskRestClientMatcher {
    @Autowired
    private ProcessTaskRestClient processTaskRestClient;

    /**
     * 根据用户登录名查询所有代办的流程定义Key查询条件(管理员可查询全部)
     * @param userName 用户名
     * @return 返回数据
     */
    public List<Map<String, String>> queryUserProcessDefKeyConditionForBacklog(String userName) {
        return processTaskRestClient.queryUserProcessDefKeyConditionForBacklog(userName);
    }
}
