package cn.gtmap.realestate.common.core.service.feign.realestate_e_certificate;

import cn.gtmap.realestate.common.core.service.rest.realestate_e_certificate.BdcDzzzClRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * 存量电子证照
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 */
@FeignClient("e-certificate-app")
public interface BdcDzzzClFeignService extends BdcDzzzClRestService {
}
