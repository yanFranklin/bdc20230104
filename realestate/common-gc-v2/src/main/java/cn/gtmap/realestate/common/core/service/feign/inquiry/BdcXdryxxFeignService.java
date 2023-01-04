package cn.gtmap.realestate.common.core.service.feign.inquiry;


import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcXdRyxxRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 限定人员信息feign接口
 * @date : 2021/8/3 14:07
 */
@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface BdcXdryxxFeignService extends BdcXdRyxxRestService {
}
