package cn.gtmap.realestate.common.core.service.feign.natural;

import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcDtcxRestService;
import cn.gtmap.realestate.common.core.service.rest.natural.ZrzyDtcxRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author: <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version: V1.0, 2019/07/16
 * @description:
 */
@FeignClient(name = "${app.services.natural-app:natural-app}")
public interface ZrzyDtcxFeignService extends ZrzyDtcxRestService {
}