package cn.gtmap.realestate.common.core.service.feign.etl;

import cn.gtmap.realestate.common.core.service.rest.etl.HlwCreateRestService;
import cn.gtmap.realestate.common.core.service.rest.etl.ShareDsjjDataRestService;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-06-26
 * @description 共享登记数据给大数据局使用
 */
@FeignClient("etl-app")
public interface ShareDsjjDataFeignService extends ShareDsjjDataRestService {
}
