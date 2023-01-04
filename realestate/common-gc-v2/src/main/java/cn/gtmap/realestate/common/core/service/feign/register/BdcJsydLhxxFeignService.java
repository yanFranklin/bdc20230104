package cn.gtmap.realestate.common.core.service.feign.register;

import cn.gtmap.realestate.common.core.service.rest.register.BdcJsydLhxxRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/17
 * @description 建设用地量化信息feign服务
 */
@FeignClient(name = "${app.services.register-app:register-app}")
public interface BdcJsydLhxxFeignService extends BdcJsydLhxxRestService {
}
