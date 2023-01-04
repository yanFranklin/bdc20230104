package cn.gtmap.realestate.common.core.service.feign.natural;

import cn.gtmap.realestate.common.core.service.rest.natural.ZrzyXtJgRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/3
 * @description 业务初始化不动产权利人信息接口
 */
@FeignClient(name = "${app.services.natural-app:natural-app}")
public interface ZrzyXtJgFeignService extends ZrzyXtJgRestService {
}
