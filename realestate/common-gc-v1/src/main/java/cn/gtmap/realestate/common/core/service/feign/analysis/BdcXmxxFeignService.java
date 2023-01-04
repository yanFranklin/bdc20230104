package cn.gtmap.realestate.common.core.service.feign.analysis;

import cn.gtmap.realestate.common.core.service.rest.analysis.BdcXmxxRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2019/3/11
 * @description 项目信息
 */
@FeignClient(value = "analysis-app", url = "127.0.0.1:8808")
public interface BdcXmxxFeignService extends BdcXmxxRestService {
}
