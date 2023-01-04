package cn.gtmap.realestate.common.core.service.rest.rules;

import cn.gtmap.realestate.common.core.dto.rules.BdcLwxxDTO;
import cn.gtmap.realestate.common.core.qo.rules.BdcGzZhQO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 2018/11/15,1.0
 * @description
 */
public interface BdcGzQzyzRestService {

    /**
     * @param bdcGzZhQO
     * @return List<CheckInfoDTO>
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 根据BdcGzZhQO获取验证信息
     */
    @RequestMapping(path = "/realestate-rules/rest/v1.0/qzyz/list", method = RequestMethod.POST)
    List<BdcLwxxDTO> listForceValidateCheckInfo(@RequestBody BdcGzZhQO bdcGzZhQO);

    /**
     * @param processInsId
     * @return List<BdcLwxxDTO>
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据gzlslid获取转发验证
     */
    @RequestMapping(path = "/realestate-rules/rest/v1.0/qzyz", method = RequestMethod.POST)
    List<BdcLwxxDTO> listForceValidateCheckInfoForTrans(@RequestParam(name = "processInsId", required = true) String processInsId);
}
