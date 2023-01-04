package cn.gtmap.realestate.common.core.service.feign.analysis;

import cn.gtmap.realestate.common.core.service.rest.analysis.BdcCqXxRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2018/12/20
 * @description 产权信息查询
 */
@FeignClient(value = "analysis-app", url = "127.0.0.1:8808")
public interface BdcCqXxFeignService extends BdcCqXxRestService {

}
