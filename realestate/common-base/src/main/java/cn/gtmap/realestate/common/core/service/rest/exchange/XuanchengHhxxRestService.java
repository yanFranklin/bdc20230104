package cn.gtmap.realestate.common.core.service.rest.exchange;

import cn.gtmap.realestate.common.core.dto.exchange.xuancheng.hhxx.HhxxResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
 * @version 1.0  2022-12-19
 * @description 宣城
 */
public interface XuanchengHhxxRestService {

    /**
     * @param jsonStr
     * @return
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @description 宣城火化信息
     */
    @PostMapping("/realestate-exchange/rest/v1.0/xuancheng/zyzygh/hhxx")
    String hhxx(@RequestBody String jsonStr);

}
