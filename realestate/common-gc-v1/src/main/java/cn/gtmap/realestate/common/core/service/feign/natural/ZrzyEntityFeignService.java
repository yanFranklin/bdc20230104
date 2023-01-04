package cn.gtmap.realestate.common.core.service.feign.natural;

import cn.gtmap.realestate.common.core.service.rest.natural.ZrzyEntityRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/25
 * @description 实体公共操作FeignClient服务接口
 */
@FeignClient(name = "${app.services.natural-app:natural-app}")
public interface ZrzyEntityFeignService extends ZrzyEntityRestService {
}
