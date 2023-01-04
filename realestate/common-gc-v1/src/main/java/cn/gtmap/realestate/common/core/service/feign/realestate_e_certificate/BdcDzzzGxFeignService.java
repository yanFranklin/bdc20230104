package cn.gtmap.realestate.common.core.service.feign.realestate_e_certificate;

import cn.gtmap.realestate.common.core.service.rest.realestate_e_certificate.BdcDzzzGxRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @version 1.0, 2020/02/17 8:50
 * @description 电子证照共享接口
 */
@FeignClient("e-certificate-app")
public interface BdcDzzzGxFeignService extends BdcDzzzGxRestService {
}
