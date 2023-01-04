package cn.gtmap.realestate.common.core.service.feign.inquiry;

import cn.gtmap.realestate.common.core.service.rest.inquiry.CollectorRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:fanghao@gtmap.cn">fanghao</a>
 * @version 1.0, 2022/07/22
 * @description
 */
@FeignClient(name = "${app.services.collector-app:collector-app}")
public interface CollectorFeignService extends CollectorRestService {
}
