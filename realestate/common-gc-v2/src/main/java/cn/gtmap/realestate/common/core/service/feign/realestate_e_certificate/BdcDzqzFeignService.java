package cn.gtmap.realestate.common.core.service.feign.realestate_e_certificate;

import cn.gtmap.realestate.common.core.service.rest.realestate_e_certificate.BdcDzqzRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @program: realestate
 * @description: 常州电子签章feign服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-03-10 09:49
 **/
@FeignClient(name = "${app.services.e-certificate-app:e-certificate-app}")
public interface BdcDzqzFeignService extends BdcDzqzRestService {
}
