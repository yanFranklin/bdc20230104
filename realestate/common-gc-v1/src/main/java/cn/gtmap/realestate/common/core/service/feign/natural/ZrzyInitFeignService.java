package cn.gtmap.realestate.common.core.service.feign.natural;

import cn.gtmap.realestate.common.core.service.rest.natural.ZrzyInitRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/11/1
 * @description
 */
@FeignClient(name = "${app.services.natural-app:natural-app}")
public interface ZrzyInitFeignService extends ZrzyInitRestService {
}
