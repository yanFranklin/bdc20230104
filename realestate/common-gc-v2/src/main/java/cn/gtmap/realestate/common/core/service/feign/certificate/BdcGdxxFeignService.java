package cn.gtmap.realestate.common.core.service.feign.certificate;

import cn.gtmap.realestate.common.core.service.rest.certificate.BdcGdxxRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/2/22
 * @description 归档信息服务
 */
@FeignClient(name = "${app.services.certificate-app:certificate-app}")
public interface BdcGdxxFeignService extends BdcGdxxRestService {
}
