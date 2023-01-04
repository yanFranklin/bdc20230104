package cn.gtmap.realestate.common.core.service.feign.engine;

import cn.gtmap.realestate.common.core.service.rest.engine.BdcGzGdZgzRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @Version 1.0 2020/7/7 14:48
 * @description 固定子规则服务接口
 */
@FeignClient(name = "${app.services.engine-app:engine-app}")
public interface BdcGzGdZgzFeignService extends BdcGzGdZgzRestService {
}
