package cn.gtmap.realestate.common.core.service.feign.natural;

import cn.gtmap.realestate.common.core.service.rest.natural.ZrzyYwxxRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/25
 * @description 自然资源业务信息
 */
@FeignClient(name = "${app.services.natural-app:natural-app}")
public interface ZrzyYwxxFeignService extends ZrzyYwxxRestService {
}
