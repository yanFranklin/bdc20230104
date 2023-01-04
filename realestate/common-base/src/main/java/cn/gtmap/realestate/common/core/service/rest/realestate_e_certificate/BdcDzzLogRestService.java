package cn.gtmap.realestate.common.core.service.rest.realestate_e_certificate;

import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzLogDO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 电子证照日志服务类
 *
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 */
public interface BdcDzzLogRestService {
    /**
     * @param dzzzLogDO
     * @return
     * @description 查询电子证照操作日志
     */
    @PostMapping(path = "/realestate-e-certificate/rest/log/queryByCzlx")
    DzzzLogDO queryLogByCzlx(DzzzLogDO dzzzLogDO);

}
