package cn.gtmap.realestate.portal.ui.service.impl;

import cn.gtmap.gtc.clients.RoleManagerClient;
import cn.gtmap.gtc.sso.domain.dto.RoleDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.matcher.TaskUserClientMatcher;
import cn.gtmap.gtc.workflow.domain.manage.ForwardTaskDto;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.portal.ui.core.vo.ForWardTaskVO;
import cn.gtmap.realestate.portal.ui.service.GeneralForwardRule;
import cn.gtmap.realestate.portal.ui.web.rest.BdcForwardController;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 自动派件转发规则
 *
 * @author <a href="mailto:lixin1@lixin.com">lixin</a>
 * @version 4:44 下午 2020/10/19
 */
@Service
public class AutoForwardRule implements GeneralForwardRule {

    private static final int ADMIN = 1;
    private static final Logger LOGGER = LoggerFactory.getLogger(AutoForwardRule.class);
    @Autowired
    private RoleManagerClient roleManagerClient;

    @Autowired
    private UserManagerUtils userManagerUtils;

    @Autowired
    private TaskUserClientMatcher taskUserClient;

    /**
     * 自动处理转发角色和用户信息<br>
     *
     * <ul>
     *     <li>自动转发通过 defaultRoleId 和 defaultUsername 赋值给 ForWardTaskVO</li>
     *     <li>管理员会通过 defaultRoleId 查询出所有在自动派件中配置的用户，而不是根据 defaultUsername 去查询</li>
     * </ul>
     *
     * @param forwardTaskDto forwardTaskDto
     * @return cn.gtmap.realestate.portal.ui.core.vo.ForWardTaskVO
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    @Override
    public ForWardTaskVO getForWardTaskVO(ForwardTaskDto forwardTaskDto) {
        ForWardTaskVO forWardTaskVO = new ForWardTaskVO();
        forWardTaskVO.setForwardTaskDto(forwardTaskDto);

        // 自动转发根据 defaultRoleId 和 defaultUsername 去取值
        String defaultRoleId = forwardTaskDto.getDefaultRoleId();

        if (StringUtils.isNotBlank(defaultRoleId)) {
            List<RoleDto> roleDtos = roleManagerClient.queryRolesByIds(Collections.singletonList(defaultRoleId));
            LOGGER.info("获取到的自动转发角色:{}",roleDtos);
            forWardTaskVO.setRoleDtoList(roleDtos);
        }

        String defaultUserName = forwardTaskDto.getDefaultUserName();

        if (StringUtils.isNotBlank(defaultUserName)) {
            UserDto user = userManagerUtils.getUserByName(defaultUserName);
            forWardTaskVO.setPersonList(Collections.singletonList(user));
        }

        // 管理员可以查询出所有在自动派件名单中的人员信息
        if (isAdmin()) {
            List<UserDto> users = taskUserClient.getAllUsersByRoleId(forwardTaskDto.getActivityId(),
                    forwardTaskDto.getTaskId(), 1, Collections.singletonList(defaultRoleId));
            forWardTaskVO.setPersonList(users);
        }

        return forWardTaskVO;
    }


    /**
     * 判断当前用户是否是管理员
     *
     * @return {boolean} true: 管理员
     */
    public boolean isAdmin() {
        UserDto currentUser = userManagerUtils.getCurrentUser();
        return currentUser.getAdmin() == ADMIN;
    }
}
