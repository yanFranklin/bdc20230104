package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlDyaqService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlDyaqDO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlDyaqRestService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/3/30
 * @description 受理抵押rest服务
 */
@RestController
@Api(tags = "受理抵押rest服务")
public class BdcSlDyaqRestController implements BdcSlDyaqRestService {

    @Autowired
    BdcSlDyaqService bdcSlDyaqService;

    @Override
    public BdcSlDyaqDO queryBdcSlDyaqByXmid(@PathVariable(value = "xmid") String xmid){
        return bdcSlDyaqService.queryBdcSlDyaqByXmid(xmid);

    }
}
