package cn.gtmap.realestate.common.core.service.feign.realestate_e_certificate;

import cn.gtmap.realestate.common.core.service.rest.realestate_e_certificate.BdcZzxxRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @program: realestate
 * @description: 南通电子证照信息推送
 * @author: <a href="mailto:fh@gtmap.cn">fh</a>
 * @create:
 **/
@FeignClient(name = "${app.services.e-certificate-app:e-certificate-app}")
public interface BdcZzxxFeignService extends BdcZzxxRestService {
}
