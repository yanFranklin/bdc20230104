package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlCdBlxxRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @program: realestate
 * @description: 查档信息feign服务
 * @author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
 * @create: 2020/08/12
 **/
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcSlCdBlxxFeignService extends BdcSlCdBlxxRestService {
}
