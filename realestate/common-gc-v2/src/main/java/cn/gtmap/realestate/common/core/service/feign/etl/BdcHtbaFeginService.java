package cn.gtmap.realestate.common.core.service.feign.etl;

import cn.gtmap.realestate.common.core.service.rest.etl.BdcHtbaRestService;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
 * @version 1.0  2022-11-29
 * @description 蚌埠-读取房产交易数据服务
 */
@FeignClient(name = "${app.services.etl-app:etl-app}")
public interface BdcHtbaFeginService extends BdcHtbaRestService {
}
