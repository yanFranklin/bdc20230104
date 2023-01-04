package cn.gtmap.realestate.common.core.service.feign.init.xuancheng;

import cn.gtmap.realestate.common.core.service.rest.init.xuancheng.BdcZnshRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @description: 智能审核
 * @author: <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @create: 2022-05-12
 **/
@FeignClient(name = "${app.services.init-app:init-app}")
public interface BdcZnshFeignService extends BdcZnshRestService {
}
