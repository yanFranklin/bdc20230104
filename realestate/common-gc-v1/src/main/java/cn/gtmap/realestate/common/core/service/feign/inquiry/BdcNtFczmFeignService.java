package cn.gtmap.realestate.common.core.service.feign.inquiry;

import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcNtFczmRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/11/02
 * @description (南通)不动产房产证明相关处理Feign接口定义
 */
@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface BdcNtFczmFeignService extends BdcNtFczmRestService {
}
