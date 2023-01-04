package cn.gtmap.realestate.common.core.service.feign.building;

import cn.gtmap.realestate.common.core.service.rest.building.ZdJsydLhxxRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/22
 * @description 宗地建设用地量化信息feign服务
 */
@FeignClient("${app.services.building-app:building-app}")
public interface ZdJsydLhxxFeignService extends ZdJsydLhxxRestService {



}
