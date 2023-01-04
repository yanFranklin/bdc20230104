package cn.gtmap.realestate.portal.ui.web.rest;


import cn.gtmap.realestate.common.core.dto.portal.BdcZdpjDTO;
import cn.gtmap.realestate.common.core.service.rest.portal.BdcPjgzRestService;
import cn.gtmap.realestate.portal.ui.service.BdcPjgzService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 派件规则
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0, 2020/09/15
 */
@RestController
@Api(tags = "派件规则接口")
public class BdcPjgzRestController implements BdcPjgzRestService {

    /**
     * 派件规则服务
     */
    @Autowired
    BdcPjgzService bdcPjgzService;

    /**
     * 派件规则处理 <br/>
     * 根据平台参数处理，返回派件用户名
     *
     * @param bdcZdpjDTO 自动派件对象
     * @return {String} 用户名
     */
    @Override
    public String pjgz(@RequestBody List<BdcZdpjDTO> bdcZdpjDTO, @RequestParam(value = "processInsId",required = false) String processInsId, @RequestParam(value = "currentNodeName",required = false) String currentNodeName) {
        return bdcPjgzService.pjgz(bdcZdpjDTO,processInsId,currentNodeName);
    }
}
