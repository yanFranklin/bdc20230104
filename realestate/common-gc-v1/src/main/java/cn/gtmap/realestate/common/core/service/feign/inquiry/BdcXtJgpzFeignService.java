package cn.gtmap.realestate.common.core.service.feign.inquiry;

import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcXtJgpzRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/11/27
 * @description 机构配置
 */
@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface BdcXtJgpzFeignService extends BdcXtJgpzRestService{
}
