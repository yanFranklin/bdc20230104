package cn.gtmap.realestate.common.core.service.feign.inquiry.changzhou;

import cn.gtmap.realestate.common.core.service.rest.inquiry.changzhou.BdcZzqzCzRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 电子签章操作
 *
 * @author <a href="mailto:lixin1@lixin.com">lixin</a>
 * @version 下午8:32 2021/1/11
 */
@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface BdcZzqzCzFeginService extends BdcZzqzCzRestService {
}
