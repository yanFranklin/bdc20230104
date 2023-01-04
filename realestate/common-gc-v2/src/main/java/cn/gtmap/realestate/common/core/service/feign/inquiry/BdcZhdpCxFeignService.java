package cn.gtmap.realestate.common.core.service.feign.inquiry;

import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcZhdpCxRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/7/12
 * @description 查询子系统：综合大屏-所有中心办理业务的集合展示
 */
//@FeignClient("inquiry-app")
@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface BdcZhdpCxFeignService extends BdcZhdpCxRestService{
}
