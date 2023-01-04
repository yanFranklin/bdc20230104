package cn.gtmap.realestate.common.core.service.feign.inquiry;

import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcYhcxRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/11/28
 * @description 银行查询
 */
@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface BdcYhcxFeignService extends BdcYhcxRestService{
}
