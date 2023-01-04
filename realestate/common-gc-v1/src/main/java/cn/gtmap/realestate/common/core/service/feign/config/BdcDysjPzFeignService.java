package cn.gtmap.realestate.common.core.service.feign.config;

import cn.gtmap.realestate.common.core.service.rest.config.BdcDysjPzRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/5/13
 * @description 打印数据配置服务
 */
@FeignClient(name = "${app.services.realestate-config:realestate-config}")
public interface BdcDysjPzFeignService extends BdcDysjPzRestService {
}
