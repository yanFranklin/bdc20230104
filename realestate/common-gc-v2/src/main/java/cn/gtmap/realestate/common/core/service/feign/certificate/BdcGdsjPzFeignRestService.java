package cn.gtmap.realestate.common.core.service.feign.certificate;

import cn.gtmap.realestate.common.core.service.rest.certificate.BdcGdsjPzRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/11/20
 * @description 归档配置
 */
@FeignClient(name = "${app.services.certificate-app:certificate-app}")
public interface BdcGdsjPzFeignRestService extends BdcGdsjPzRestService {
}
