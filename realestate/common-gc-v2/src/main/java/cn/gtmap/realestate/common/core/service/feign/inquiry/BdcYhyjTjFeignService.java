package cn.gtmap.realestate.common.core.service.feign.inquiry;

import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcYhyjTjRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2020-07-31
 * @description 银行月结统计Feign接口
 */
@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface BdcYhyjTjFeignService extends BdcYhyjTjRestService {
}
