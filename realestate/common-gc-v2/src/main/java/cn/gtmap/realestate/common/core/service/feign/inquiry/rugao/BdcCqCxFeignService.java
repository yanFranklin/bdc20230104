package cn.gtmap.realestate.common.core.service.feign.inquiry.rugao;

import cn.gtmap.realestate.common.core.service.rest.inquiry.rugao.BdcCqCxRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0, 2020/01/19
 * @description
 */
@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface BdcCqCxFeignService extends BdcCqCxRestService {
}
