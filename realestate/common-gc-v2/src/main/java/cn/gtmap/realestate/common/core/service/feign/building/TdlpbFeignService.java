package cn.gtmap.realestate.common.core.service.feign.building;

import cn.gtmap.realestate.common.core.service.rest.building.TdlpbRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/08/04
 * @Description: 土地楼盘表相关服务
 */
@FeignClient("${app.services.building-app:building-app}")
public interface TdlpbFeignService extends TdlpbRestService {
}
