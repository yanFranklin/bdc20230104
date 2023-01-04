package cn.gtmap.realestate.common.core.service.feign.init;

import cn.gtmap.realestate.common.core.service.rest.init.BdcPrintDataRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href ="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.3, 2019/5/31
 * @description 打印数据
 */
@FeignClient(name = "${app.services.init-app:init-app}")
public interface BdcPrintDataFeignService extends BdcPrintDataRestService {
}
