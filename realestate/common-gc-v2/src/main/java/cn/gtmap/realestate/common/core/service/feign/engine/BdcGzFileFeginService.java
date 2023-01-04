package cn.gtmap.realestate.common.core.service.feign.engine;

import cn.gtmap.realestate.common.core.service.rest.engine.BdcGzFileRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/3/19 18:43
 * @description
 */
@FeignClient(name = "${app.services.engine-app:engine-app}")
public interface BdcGzFileFeginService extends BdcGzFileRestService {
}
