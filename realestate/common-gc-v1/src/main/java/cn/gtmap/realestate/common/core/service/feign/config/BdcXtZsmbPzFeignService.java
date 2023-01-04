package cn.gtmap.realestate.common.core.service.feign.config;

import cn.gtmap.realestate.common.core.service.rest.config.BdcXtZsmbPzRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/01/17
 * @description  配置系统证书模板服务FeignCliet接口
 */
@FeignClient(name = "${app.services.realestate-config:realestate-config}")
public interface BdcXtZsmbPzFeignService extends BdcXtZsmbPzRestService{
}
