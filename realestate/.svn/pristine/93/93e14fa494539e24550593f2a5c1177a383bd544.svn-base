package cn.gtmap.realestate.exchange.web;


import cn.gtmap.realestate.exchange.core.domain.BdcXmDO;
import cn.gtmap.realestate.exchange.service.AccessModelHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


@RestController
@RequestMapping(value = "/rest/v1.0/test")
public class PushDataRestController {


    @Autowired
    AccessModelHandlerService accessModelHandlerService;

    @GetMapping("/data")
    public void test() {
        BdcXmDO bdcXmDO = new BdcXmDO();
        bdcXmDO.setGzldyid("smMwR2SaARTp8MYm");
        bdcXmDO.setXmid("61IF2653QPJP600V");
        bdcXmDO.setBdcdyh("340111306001GB00010F00050308");
        accessModelHandlerService.access(bdcXmDO, new ArrayList<>());
    }

}
