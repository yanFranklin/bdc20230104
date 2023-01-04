package cn.gtmap.realestate.portal.ui.service;

import cn.gtmap.gtc.workflow.domain.manage.ForwardTaskDto;
import cn.gtmap.realestate.portal.ui.core.vo.ForWardTaskVO;

/**
 * 工作流规则
 *
 * @author <a href="mailto:lixin1@lixin.com">lixin</a>
 * @version 3:29 下午 2020/10/19
 */
public interface GeneralForwardRule {

    /**
     * 处理转发角色和用户信息
     *
     * @param forwardTaskDto forwardTaskDto
     * @return cn.gtmap.realestate.portal.ui.core.vo.ForWardTaskVO
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    ForWardTaskVO getForWardTaskVO(ForwardTaskDto forwardTaskDto);
}
