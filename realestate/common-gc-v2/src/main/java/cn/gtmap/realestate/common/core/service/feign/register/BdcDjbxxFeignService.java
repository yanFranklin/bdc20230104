package cn.gtmap.realestate.common.core.service.feign.register;

import cn.gtmap.realestate.common.core.service.rest.register.BdcDjbxxRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/12/25
 * @description 登记簿信息
 */
@FeignClient(name = "${app.services.register-app:register-app}")
public interface BdcDjbxxFeignService extends BdcDjbxxRestService {
}
