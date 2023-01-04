package cn.gtmap.realestate.common.core.service.feign.certificate;

import cn.gtmap.realestate.common.core.service.rest.certificate.CertificateWorkflowRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/1/21
 * @description 证书归档工作流feign服务接口
 */
@FeignClient(name = "${app.services.certificate-app:certificate-app}")
public interface CertificateWorkflowFeignService extends CertificateWorkflowRestService {
}
