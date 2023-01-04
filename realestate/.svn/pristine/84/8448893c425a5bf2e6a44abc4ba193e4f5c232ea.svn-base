package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.service.lpb.BdcPrintDjdcbService;
import cn.gtmap.realestate.common.core.service.rest.building.PrintDjdcbRestService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: realestate
 * @description: 地籍调查表打印服务
 * @author: <a href="mailto:duwei@gtmap.cn">duwei</a>
 * @create: 2022-10-13
 **/
@RestController
@Api(tags = "地籍调查表打印服务")
public class PrintDjdcbRestController implements PrintDjdcbRestService {

    @Autowired
    BdcPrintDjdcbService bdcPrintDjdcbService;

    /**
     * @param bdcdyh
     * @return xml
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 地籍调查表打印服务
     */
    @Override
    public String printDjdcb(@RequestParam("dylx") String dylx, @RequestParam(name = "qjgldm", required = false) String qjgldm, @RequestParam("bdcdyh") String bdcdyh) {
        return bdcPrintDjdcbService.printDjdcb(dylx, qjgldm, bdcdyh);
    }
}
