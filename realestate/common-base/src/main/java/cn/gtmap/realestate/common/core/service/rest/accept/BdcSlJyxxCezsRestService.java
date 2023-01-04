package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlJyxxCezsDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJyxxDO;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/05/13
 * @description 受理交易差额征收信息rest服务
 */
public interface BdcSlJyxxCezsRestService {

    /**
     * @param bdcSlJyxxCezsDO 不动产受理交易差额征收信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 保存不动产受理交易差额征收信息
     */
    @PutMapping("/realestate-accept/rest/v1.0/jyxxcezs/")
    BdcSlJyxxCezsDO saveBdcSlJyxxCezs(@RequestBody BdcSlJyxxCezsDO bdcSlJyxxCezsDO);
}
