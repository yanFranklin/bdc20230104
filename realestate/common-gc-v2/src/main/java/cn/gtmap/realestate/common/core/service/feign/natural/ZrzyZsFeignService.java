package cn.gtmap.realestate.common.core.service.feign.natural;

import cn.gtmap.realestate.common.core.service.rest.natural.ZrzyZsRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author wyh
 * @version 1.0
 * @date 2021/11/5 11:16
 */
@FeignClient(name = "${app.services.natural-app:natural-app}")
public interface ZrzyZsFeignService extends ZrzyZsRestService {
}
