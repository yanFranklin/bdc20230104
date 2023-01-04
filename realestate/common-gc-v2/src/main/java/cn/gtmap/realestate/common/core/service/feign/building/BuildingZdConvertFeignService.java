package cn.gtmap.realestate.common.core.service.feign.building;

import cn.gtmap.realestate.common.core.service.rest.building.BuildingZdConvertRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-14
 * @description 楼盘表字典项转换服务
 */
@FeignClient(name = "${app.services.building-app:building-app}")
public interface BuildingZdConvertFeignService extends BuildingZdConvertRestService{
}
