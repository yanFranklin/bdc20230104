package cn.gtmap.realestate.portal.ui.service.impl;

import cn.gtmap.gtc.clients.RoleManagerClient;
import cn.gtmap.gtc.sso.domain.dto.RoleDto;
import cn.gtmap.gtc.workflow.domain.manage.ForwardTaskDto;
import cn.gtmap.realestate.portal.ui.core.vo.ForWardTaskVO;
import cn.gtmap.realestate.portal.ui.service.GeneralForwardRule;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 正常派件转发规则
 *
 * @author <a href="mailto:lixin1@lixin.com">lixin</a>
 * @version 4:44 下午 2020/10/19
 */
@Service
public class NormalForwardRule implements GeneralForwardRule {
    private static final Logger LOGGER = LoggerFactory.getLogger(NormalForwardRule.class);
    @Autowired
    private RoleManagerClient roleManagerClient;

    /**
     * 正常流转处理转发角色和用户信息<br>
     *
     * <ul>
     *     <li>selectUserNames 有值则表明配置了转发默认角色和用户，角色从 selectRole 中获取</li>
     *     <li>用户信息正常不需要此时赋值，由页面选择角色后再请求接口渲染</li>
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

        List<String> roleIds = forwardTaskDto.getRoleIds();
        // 处理角色列表，person 不做处理，由前端 ajax 请求再渲染
        if (CollectionUtils.isNotEmpty(roleIds)) {
            List<RoleDto> roleDtos;
            // 存在默认转发人，直接获取对应的角色
            if (StringUtils.isNotBlank(forwardTaskDto.getSelectUserNames())) {
                LOGGER.error("配置的转发角色:{}", JSON.toJSONString(forwardTaskDto));
                RoleDto roleDetail = roleManagerClient.getRoleDetail(forwardTaskDto.getSelectRoleIds());
                roleDtos = Collections.singletonList(roleDetail);
            } else {
                roleDtos = roleManagerClient.queryRolesByIds(roleIds);
            }
            forWardTaskVO.setRoleDtoList(roleDtos);
        }

        return forWardTaskVO;
    }
}
