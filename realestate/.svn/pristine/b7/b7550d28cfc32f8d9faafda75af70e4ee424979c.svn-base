package cn.gtmap.realestate.common.core.service.rest.exchange;


import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @date 2022/11/2
 * @description 淮安，中国邮政接口
 */
public interface BdcEmsRestService {

    /**
     * @description: 2.2.5.	【新一代寄递平台:下单取号】接口,工作流事件
     * @author: <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @date: 2022/11/2 9:36
     * @param processInsId
     * @return: void
     **/
    @PostMapping("/realestate-exchange/rest/v1.0/ems/xdqh")
    BdcCommonResponse emsXdqh(@RequestParam(name = "processInsId") String processInsId, @RequestParam(name = "currentUserName") String currentUserName);
}
