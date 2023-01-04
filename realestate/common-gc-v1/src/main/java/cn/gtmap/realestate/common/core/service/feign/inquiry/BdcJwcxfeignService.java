package cn.gtmap.realestate.common.core.service.feign.inquiry;

import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcJwcxRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/11/4
 * @description
 */
@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface BdcJwcxfeignService extends BdcJwcxRestService {
}
