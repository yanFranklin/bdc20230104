package cn.gtmap.realestate.portal.ui.service.impl;

import cn.gtmap.gtc.workflow.domain.manage.NodeVariableDTO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.portal.ui.core.dto.EventDTO;
import cn.gtmap.realestate.portal.ui.core.dto.WorkFlowDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * 不动产工作流业务类
 *
 * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0, 2020/06/05
 */
public abstract class BdcWorkFlowAbstactService {
    /**
     * 签名验证并且签名
     *
     * @param gzlslid 工作流 DTO 集合
     * @return {String} 是否签名成功，成功返回 success, 失败返回 ""
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    public String isAbandon(String gzlslid) {
        return StringUtils.EMPTY;
    }

    /**
     * 撤销流程前事件
     *
     * @param workFlowDTO reason gzlslid 为必填项
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    public void abandonTask(WorkFlowDTO workFlowDTO) {
    }

    /**
     * 办结事件
     *
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     * @version 10:48 上午 2020/8/10
     */
    public void processEnd(String taskId) {
    }

    /**
     * 删除前执行事件
     *
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     * @version 10:56 上午 2020/8/10
     */
    public void processBeforeDelete(EventDTO eventDTO,BdcXmDO bdcXmDO, String userName) {
    }

    /**
     * 删除后执行事件
     *
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     * @version 10:56 上午 2020/8/10
     * @param bdcXmDO
     * @param userName
     * @param reason
     */
    public void processAfterDelete(BdcXmDO bdcXmDO, String userName, String reason) {
    }

    /**
     * 组织转发参数
     *
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     * @version 10:56 上午 2020/8/10
     */
    public List<NodeVariableDTO> getNodeVariableDTOS(String taskId) {
        return Collections.emptyList();
    }
}
