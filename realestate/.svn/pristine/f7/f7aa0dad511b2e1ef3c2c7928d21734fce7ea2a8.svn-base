package cn.gtmap.realestate.common.core.service.rest.register;

import cn.gtmap.realestate.common.core.dto.register.BdcSendMsgDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
 * @version 1.0, 2022/8/1
 * @description 发送短信服务FeignClient调用定义
 */
public interface BdcSendMsgRestService {

    @RequestMapping(value = "/realestate-register/rest/v1.0/sendMessage", method = RequestMethod.POST)
    void sendMsg(@RequestBody BdcSendMsgDTO bdcSendMsgDTO);

    @RequestMapping(value = "/realestate-register/rest/v1.0/sendSmsMsg", method = RequestMethod.POST)
    void sendSmsMsg(@RequestBody Map map, @RequestParam(name = "mainid") String mainid);
}
