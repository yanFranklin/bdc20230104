package cn.gtmap.realestate.common.core.service.feign.register;

import cn.gtmap.realestate.common.core.service.rest.register.BdcSendMsgRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:huanghui@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/11/24
 * @description 发送短信相关服务FeignClient调用定义
 */
@FeignClient(name = "${app.services.register-app:register-app}")
public interface BdcSendMsgFeignService extends BdcSendMsgRestService {
}
