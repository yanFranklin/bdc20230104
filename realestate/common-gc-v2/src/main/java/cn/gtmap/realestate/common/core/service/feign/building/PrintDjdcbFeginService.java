package cn.gtmap.realestate.common.core.service.feign.building;

import cn.gtmap.realestate.common.core.service.rest.building.PrintDjdcbRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/10/15
 * @description
 */
@FeignClient("${app.services.building-app:building-app}")
public interface PrintDjdcbFeginService extends PrintDjdcbRestService {
}
