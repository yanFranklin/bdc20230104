package cn.gtmap.realestate.common.core.service.feign.certificate;

import cn.gtmap.realestate.common.core.service.rest.certificate.BdcYjdRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/3/1
 * @description 移交单服务
 */
@FeignClient(name = "${app.services.certificate-app:certificate-app}")
public interface BdcYjdFeignService extends BdcYjdRestService {
}
