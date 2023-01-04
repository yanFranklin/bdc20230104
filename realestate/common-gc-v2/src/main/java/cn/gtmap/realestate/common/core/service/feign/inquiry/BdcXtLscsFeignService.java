package cn.gtmap.realestate.common.core.service.feign.inquiry;

import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcBjscTjRestService;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcXtLscsRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>"
 * @version 1.0, 2021/01/14
 * @description 临时参数表操作服务
 */
@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface BdcXtLscsFeignService extends BdcXtLscsRestService {
}
