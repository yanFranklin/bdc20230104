package cn.gtmap.realestate.common.core.service.feign.inquiry;

import cn.gtmap.realestate.common.core.service.rest.inquiry.xuancheng.BdcLqCxRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @version 1.0, 2022/11/25
 * @description 林权查询服务
 */
@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface BdcLqCxFeignService extends BdcLqCxRestService {
}
