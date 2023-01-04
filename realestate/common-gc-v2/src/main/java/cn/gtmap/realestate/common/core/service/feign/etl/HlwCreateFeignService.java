package cn.gtmap.realestate.common.core.service.feign.etl;

import cn.gtmap.realestate.common.core.service.rest.etl.HlwCreateRestService;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-04-26
 * @description 共享一窗受理数据服务
 */
@FeignClient(name = "${app.services.etl-app:etl-app}")
public interface HlwCreateFeignService extends HlwCreateRestService {
}