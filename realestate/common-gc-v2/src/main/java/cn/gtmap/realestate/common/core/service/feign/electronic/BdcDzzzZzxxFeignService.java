package cn.gtmap.realestate.common.core.service.feign.electronic;

import cn.gtmap.realestate.common.core.service.rest.electronic.BdcDzzzZzxxRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 电子证照信息相关服务
 */
@FeignClient("electronic-app")
public interface BdcDzzzZzxxFeignService extends BdcDzzzZzxxRestService{
}
