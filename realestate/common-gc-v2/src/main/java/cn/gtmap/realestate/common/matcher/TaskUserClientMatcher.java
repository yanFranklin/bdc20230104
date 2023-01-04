package cn.gtmap.realestate.common.matcher;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.manage.TaskUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/03/24
 * @description  大云流程定义活动节点人员 V2.x版本适配
 */
@Component
public class TaskUserClientMatcher {
    @Autowired
    private TaskUserClient taskUserClient;

    /**
     * 根据角色id获取所有人员（参数个数按照V1.x版本组织，对业务层统一）
     * @param allRoleAry
     * @return
     */
    public List<UserDto> getAllUsersByRoleId(String forwardActivityId, String taskId, Integer enabled, List<String> allRoleAry) {
        return taskUserClient.getAllUsersByRoleId(forwardActivityId, taskId, allRoleAry);
    }
}
