package cn.gtmap.realestate.common.core.service.rest.refleshconfig;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/11/09/15:47
 * @Description:
 */
public interface EtlAppRefleshConfigRestService {
    @GetMapping(value = "/realestate-engine/rest/v1.0/refleshConfig")
    String refleshConfigValue(@RequestParam(name = "bdcTsywPzDOStr") String bdcTsywPzDOStr) throws Exception;
}
