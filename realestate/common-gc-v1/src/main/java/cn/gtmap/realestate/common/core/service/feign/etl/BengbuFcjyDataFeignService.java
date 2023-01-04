package cn.gtmap.realestate.common.core.service.feign.etl;

import cn.gtmap.realestate.common.core.service.rest.etl.BengbuFcjyDataRestService;
import org.springframework.cloud.netflix.feign.FeignClient;


/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-05-12
 * @description 蚌埠-读取房产交易数据服务
 */
@FeignClient("etl-app")
public interface BengbuFcjyDataFeignService extends BengbuFcjyDataRestService {
}
