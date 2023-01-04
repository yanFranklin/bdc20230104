package cn.gtmap.realestate.common.core.service.feign.exchange;

import cn.gtmap.realestate.common.core.service.rest.exchange.NantongQiService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @Description //南通气过户信息接口
 * @Date 2022/5/16 14:13
 **/
@FeignClient(name = "${app.services.exchange-app:exchange-app}")
public interface NantongQiFeignService extends NantongQiService {
}
