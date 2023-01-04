package cn.gtmap.realestate.common.core.service.feign.inquiry;

import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcCommonRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @Author: wangyinghao
 * @Description: 一般查询
 */
@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface BdcCommonFeignService extends BdcCommonRestService {
}
