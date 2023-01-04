package cn.gtmap.realestate.common.core.service.feign.inquiry;

import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcDpCxRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/04/07/14:35
 * @Description:
 */
@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface BdcDpCxFeignService extends BdcDpCxRestService {
}
