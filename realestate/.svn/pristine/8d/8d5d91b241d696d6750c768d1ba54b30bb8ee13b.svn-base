package cn.gtmap.realestate.common.core.service.rest.building;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
 * @version 1.0  2022/10/13
 * @description 地籍调查表打印服务
 */
public interface PrintDjdcbRestService {

    /**
     * @param bdcdyh
     * @return xml
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 地籍调查表打印服务
     */
    @GetMapping("/building/rest/v1.0/djdcb/print")
    String printDjdcb(@RequestParam("dylx") String dylx, @RequestParam(name = "qjgldm", required = false) String qjgldm, @RequestParam("bdcdyh") String bdcdyh);
}
