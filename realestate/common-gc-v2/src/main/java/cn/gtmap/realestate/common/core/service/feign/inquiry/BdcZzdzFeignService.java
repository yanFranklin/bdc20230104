package cn.gtmap.realestate.common.core.service.feign.inquiry;

import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcZzdzRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2019/10/31
 * @description  自助打证接口feign
 */
//@FeignClient("inquiry-app")
@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface BdcZzdzFeignService extends BdcZzdzRestService {
}
