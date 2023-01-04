package cn.gtmap.realestate.common.core.service.feign.natural;

import cn.gtmap.realestate.common.core.service.rest.natural.ZrzyGgRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:wangyinghao@gtmap.cn">wangyinghao</a>
 * @version 1.0  2021/10/25
 * @description
 */
@FeignClient(name = "${app.services.natural-app:natural-app}")
public interface ZrzyGgFeignService extends ZrzyGgRestService {
}
