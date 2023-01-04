package cn.gtmap.realestate.portal.ui.service;

import cn.gtmap.realestate.common.core.dto.portal.BdcZdpjDTO;

import java.util.List;

/**
 * 派件规则
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0, 2020/09/15
 */
public interface BdcPjgzService {
    /**
     * 派件规则处理 <br/>
     * 根据平台参数处理，返回派件用户名
     *
     * @param bdcZdpjDTO 自动派件对象
     * @param processInsId 工作流实例ID
     * @param currentNodeName 派件节点名称
     * @return {String} 用户名，未匹配到对应用户返回 null
     */
    String pjgz(List<BdcZdpjDTO> bdcZdpjDTO,String processInsId,String currentNodeName);
}
