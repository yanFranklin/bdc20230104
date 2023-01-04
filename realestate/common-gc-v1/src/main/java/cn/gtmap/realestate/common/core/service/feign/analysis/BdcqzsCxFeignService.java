package cn.gtmap.realestate.common.core.service.feign.analysis;

import cn.gtmap.realestate.common.core.service.rest.analysis.BdcqzsCxRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author: <a href="@mailto:hynkoala@163.com">hanyaning</a>
 * @version: V1.0, 2019.03.01
 * @description: 不动产权证书查询
 */
@FeignClient(value = "analysis-app", url = "127.0.0.1:8808")
public interface BdcqzsCxFeignService extends BdcqzsCxRestService {
}