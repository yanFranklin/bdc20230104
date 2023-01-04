package cn.gtmap.realestate.common.core.service.feign.natural;

import cn.gtmap.realestate.common.core.service.rest.natural.ZrzyPrintRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/11/14
 * @description 不动产打印
 */
@FeignClient(name = "${app.services.natural-app:natural-app}")
public interface ZrzyPrintFeignService extends ZrzyPrintRestService {
}
