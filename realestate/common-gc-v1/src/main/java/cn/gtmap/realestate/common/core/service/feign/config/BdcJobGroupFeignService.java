package cn.gtmap.realestate.common.core.service.feign.config;

import cn.gtmap.realestate.common.core.service.rest.job.BdcJobGroupRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
 * @version 1.0, 2022/01/01 15:12
 * @description jobGroup服务接口
 */
@FeignClient(name = "${app.services.realestate-config:realestate-config}")
public interface BdcJobGroupFeignService extends BdcJobGroupRestService {
}
