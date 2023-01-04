package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlTdcrjRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/8
 * @description 出让金服务
 */
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcSlTdcrjFeignService extends BdcSlTdcrjRestService {


}
