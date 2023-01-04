package cn.gtmap.realestate.common.core.service.feign.register;

import cn.gtmap.realestate.common.core.service.rest.register.BdcQzxxRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/12/23 17:39
 * @description 评价器签名接口
 */
@FeignClient(name = "${app.services.register-app:register-app}")
public interface BdcQzxxFeginService extends BdcQzxxRestService {
}
