package cn.gtmap.realestate.common.core.service.feign.natural;

import cn.gtmap.realestate.common.core.service.rest.natural.ZrzyZdRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/23
 * @description 字典服务
 */
@FeignClient(name = "${app.services.natural-app:natural-app}")
public interface ZrzyZdFeignService extends ZrzyZdRestService {
}
