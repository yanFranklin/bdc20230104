package cn.gtmap.realestate.common.core.service.rest.realestate_e_certificate;

import cn.gtmap.realestate.common.core.domain.electronic.BdcDzzzZzxxDO;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.BdcDzzzYwxxDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcXmZsDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:fanghao@gtmap.cn">fanghao</a>
 * @version 1.0, 2022/08/25
 * @description
 */
public interface BdcZzxxRestService {
    /**
     * 根据zzbs获取证照信息
     *
     * @param bdcXmZsDTO
     * @return
     */
    @PostMapping("/realestate-e-certificate/feign/v1.0/zzxx/zzcx")
    BdcDzzzZzxxDO getZzxxByzzbs(@RequestBody BdcXmZsDTO bdcXmZsDTO);

    /**
     * 根据zzid获取证照业务信息
     * @param zzid
     * @return
     */
    @PostMapping("/realestate-e-certificate/feign/v1.0/zzxx/zzywcx")
    public BdcDzzzYwxxDTO getZzywxxByzzid(@RequestParam(value = "zzid", required = true) String zzid);

}
