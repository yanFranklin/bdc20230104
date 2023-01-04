package cn.gtmap.realestate.common.core.service.feign.naturalresource;

import cn.gtmap.realestate.common.core.service.rest.naturalresource.NaturalJbzkRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:wangyinghao@gtmap.cn">wangyinghao</a>
 * @version 1.0  2019-01-23
 * @description 为自然资源子系统提供
 * 自然资源基本状况分页查询服务
 */
@FeignClient(name = "${app.services.natural-resource-app:natural-resource-app}")
public interface NaturalJbzkFeignService extends NaturalJbzkRestService {


}
