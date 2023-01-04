package cn.gtmap.realestate.common.core.service.feign.inquiry;

import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcSjptCxRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 省级平台查询
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0  2019-09-04
 */
@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface BdcSjptCxFeignService extends BdcSjptCxRestService {
}
