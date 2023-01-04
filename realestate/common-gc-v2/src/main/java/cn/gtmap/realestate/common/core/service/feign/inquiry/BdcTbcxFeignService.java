package cn.gtmap.realestate.common.core.service.feign.inquiry;

import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcTbcxRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2021-04-14
 * @description 调拨查询
 */
@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface BdcTbcxFeignService extends BdcTbcxRestService {
}
