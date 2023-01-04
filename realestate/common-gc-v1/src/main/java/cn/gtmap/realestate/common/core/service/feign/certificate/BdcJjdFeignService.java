package cn.gtmap.realestate.common.core.service.feign.certificate;

import cn.gtmap.realestate.common.core.service.rest.certificate.BdcJjdRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * 交接单服务接口
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/8/27 18:16
 */
@FeignClient(name = "${app.services.certificate-app:certificate-app}")
public interface BdcJjdFeignService extends BdcJjdRestService {
}
