package cn.gtmap.realestate.common.core.service.feign.pub;
/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2018/12/14
 * @description
 */

import cn.gtmap.realestate.common.core.service.rest.pub.FwxxRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "realestate-public")
public interface FwxxFeignRestService extends FwxxRestService {
}
