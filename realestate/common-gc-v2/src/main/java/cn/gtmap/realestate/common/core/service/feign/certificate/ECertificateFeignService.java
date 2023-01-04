package cn.gtmap.realestate.common.core.service.feign.certificate;

import cn.gtmap.realestate.common.core.service.rest.certificate.ECertificateRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/12/17
 * @description 电子证照fegin服务
 */
@FeignClient(name = "${app.services.certificate-app:certificate-app}")
public interface ECertificateFeignService extends ECertificateRestService {
}
