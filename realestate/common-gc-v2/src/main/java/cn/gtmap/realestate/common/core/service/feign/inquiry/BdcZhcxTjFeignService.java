package cn.gtmap.realestate.common.core.service.feign.inquiry;

import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcZhcxTjRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/7/17
 * @description 查询子系统：综合查询打印证明统计
 */
@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface BdcZhcxTjFeignService extends BdcZhcxTjRestService {
}
