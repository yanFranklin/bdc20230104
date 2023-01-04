package cn.gtmap.realestate.common.core.service.feign.natural;

import cn.gtmap.realestate.common.core.service.rest.natural.ZrzyGzyzRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/7/22
 * @description
 */
@FeignClient(name = "${app.services.natural-app:natural-app}")
public interface ZrzyGzyzFeignService extends ZrzyGzyzRestService {
}
