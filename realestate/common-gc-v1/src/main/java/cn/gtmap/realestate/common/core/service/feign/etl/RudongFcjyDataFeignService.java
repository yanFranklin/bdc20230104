package cn.gtmap.realestate.common.core.service.feign.etl;

import cn.gtmap.realestate.common.core.service.rest.etl.RudongFcjyDataRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/4/2
 * @description
 */
@FeignClient(name = "${app.services.etl-app:etl-app}")
public interface RudongFcjyDataFeignService extends RudongFcjyDataRestService {


}
