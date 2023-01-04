package cn.gtmap.realestate.common.core.service.rest.portal;

import cn.gtmap.realestate.common.core.dto.portal.BdcZdpjDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 派件规则服务
 *
 * @author <a href="mailto:lixin1@gtmap.cn>lixin</a>"
 * @version 1.0, 2020/09/15
 */
public interface BdcPjgzRestService {
    /**
     * 自动派件规则设置
     *
     * @param bdcZdpjDTO 自动派件对象
     * @param processInsId 工作流实例ID
     * @param currentNodeName 派件节点名称
     * @return {String}  用户名
     * @author <a href="mailto:lixin1@gtmap.cn>lixin</a>"
     */
    @PostMapping("/realestate-portal-ui/rest/v1.0/pjgz")
    String pjgz(@RequestBody List<BdcZdpjDTO> bdcZdpjDTO,@RequestParam(value = "processInsId",required = false) String processInsId,@RequestParam(value = "currentNodeName",required = false) String currentNodeName);
}
