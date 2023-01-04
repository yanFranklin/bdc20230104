package cn.gtmap.realestate.common.core.service.feign.certificate;

import cn.gtmap.realestate.common.core.service.rest.certificate.BdcZsRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/11/14
 * @description  不动产证书业务服务接口FeignClient定义
 */
@FeignClient(name = "${app.services.certificate-app:certificate-app}")
public interface BdcZsFeignService extends BdcZsRestService{
}
