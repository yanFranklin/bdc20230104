package cn.gtmap.realestate.common.core.service.feign.inquiry.bengbu;

import cn.gtmap.realestate.common.core.service.rest.inquiry.bengbu.BdcDwgzglRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href ="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 16:15 2020/7/27
 * @description 单位公章相关接口
 */
@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface BdcDwgzglFeignService extends BdcDwgzglRestService {
}
