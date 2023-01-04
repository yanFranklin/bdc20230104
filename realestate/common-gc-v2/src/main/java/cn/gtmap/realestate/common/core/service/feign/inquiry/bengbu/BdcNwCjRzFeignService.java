package cn.gtmap.realestate.common.core.service.feign.inquiry.bengbu;

import cn.gtmap.realestate.common.core.service.rest.inquiry.bengbu.BdcNwCjRzRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href ="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 2021/01/25
 * @description 蚌埠_互联网+业务内网创建日志查询
 */
@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface BdcNwCjRzFeignService extends BdcNwCjRzRestService {
}
