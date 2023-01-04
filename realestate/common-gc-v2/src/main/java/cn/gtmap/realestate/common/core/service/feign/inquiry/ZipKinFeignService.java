package cn.gtmap.realestate.common.core.service.feign.inquiry;

import cn.gtmap.realestate.common.core.service.rest.inquiry.ZipKinRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:fanghao@gtmap.cn">fanghao</a>
 * @version 1.0, 2022/07/18
 * 获取zipKin 耗时相关信息
 */
@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface ZipKinFeignService extends ZipKinRestService {
}
