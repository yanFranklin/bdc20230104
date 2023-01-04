package cn.gtmap.realestate.common.core.service.feign.inquiry.huaian;

import cn.gtmap.realestate.common.core.service.rest.inquiry.huaian.BdcGzlTjXmxxRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 2.0, 2022/6/27
 * @description 工作量统计Feign 接口，根据项目表信息统计工作量
 */
@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface BdcGzltjXmxxFeignService extends BdcGzlTjXmxxRestService {
}
