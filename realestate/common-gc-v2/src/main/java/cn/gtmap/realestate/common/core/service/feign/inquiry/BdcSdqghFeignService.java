package cn.gtmap.realestate.common.core.service.feign.inquiry;

import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcSdqghRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2019-07-10
 * @description 水电气信息
 */
@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface BdcSdqghFeignService extends BdcSdqghRestService {
}
