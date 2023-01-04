package cn.gtmap.realestate.common.core.service.feign.etl;

import cn.gtmap.realestate.common.core.service.rest.etl.HtbaQlrRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @program: realestate
 * @description: 合同备案权利人feign服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-21 18:08
 **/
@FeignClient(name = "${app.services.etl-app:etl-app}")
public interface HtbaQlrFeignService extends HtbaQlrRestService {
}
