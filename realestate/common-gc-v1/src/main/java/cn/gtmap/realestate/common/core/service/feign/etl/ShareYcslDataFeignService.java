package cn.gtmap.realestate.common.core.service.feign.etl;

import cn.gtmap.realestate.common.core.service.rest.etl.ShareYcslDataRestService;
import org.springframework.cloud.netflix.feign.FeignClient;


/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-04-26
 * @description 共享一窗受理数据服务
 */
@FeignClient("etl-app")
public interface ShareYcslDataFeignService extends ShareYcslDataRestService {
}
