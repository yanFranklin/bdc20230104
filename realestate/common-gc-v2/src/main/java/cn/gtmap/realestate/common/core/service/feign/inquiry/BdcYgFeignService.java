package cn.gtmap.realestate.common.core.service.feign.inquiry;

import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcYgRestService;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcYhcxRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a herf="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0, 2021/01/20
 * @description 不动产预告查询
 */
@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface BdcYgFeignService extends BdcYgRestService {
}
