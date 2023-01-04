package cn.gtmap.realestate.common.core.service.feign.natural;

import cn.gtmap.realestate.common.core.service.rest.natural.ZrzyShxxRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href ="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
 * @version 1.3, 2018/11/3
 * @description 审核信息接口
 */
@FeignClient(name = "${app.services.natural-app:natural-app}")
public interface ZrzyShxxFeignService extends ZrzyShxxRestService {
}
