package cn.gtmap.realestate.common.core.service.feign.register;

import cn.gtmap.realestate.common.core.service.rest.register.BdcXxblLogRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 信息补录日志 feign 服务
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/6/11 10:07
 */
@FeignClient(name = "${app.services.register-app:register-app}")
public interface BdcXxblLogFeignService extends BdcXxblLogRestService {
}
