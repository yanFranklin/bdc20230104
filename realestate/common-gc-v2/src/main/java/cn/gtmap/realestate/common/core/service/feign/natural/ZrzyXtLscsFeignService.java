package cn.gtmap.realestate.common.core.service.feign.natural;

import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcXtLscsRestService;
import cn.gtmap.realestate.common.core.service.rest.natural.ZrzyXtLscsRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>"
 * @version 1.0, 2021/01/14
 * @description 临时参数表操作服务
 */
@FeignClient(name = "${app.services.natural-app:natural-app}")
public interface ZrzyXtLscsFeignService extends ZrzyXtLscsRestService {
}
