package cn.gtmap.realestate.common.core.service.feign.pub;
/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2018/12/14
 * @description
 */

import cn.gtmap.realestate.common.core.service.rest.pub.TdxxRestService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "realestate-public")
public interface TdxxFeignRestService extends TdxxRestService {
}
