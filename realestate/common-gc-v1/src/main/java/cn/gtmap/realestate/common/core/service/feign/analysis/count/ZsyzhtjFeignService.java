package cn.gtmap.realestate.common.core.service.feign.analysis.count;

import cn.gtmap.realestate.common.core.service.rest.analysis.count.ZsyzhtjRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author: <a href="@mailto:hynkoala@163.com">hanyaning</a>
 * @version: V1.0, 2019.03.05
 * @description: 证书印制号统计feign接口类
 */
@FeignClient("analysis-app")
public interface ZsyzhtjFeignService extends ZsyzhtjRestService {
}