package cn.gtmap.realestate.common.core.service.feign.inquiry;

import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcPlcxRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2019-11-15
 * @description 批量查询
 */
@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface BdcPlcxFeignService extends BdcPlcxRestService {
}
