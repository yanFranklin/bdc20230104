package cn.gtmap.realestate.init.web.rest.xuancheng;

import cn.gtmap.realestate.common.core.dto.init.znsh.BdcSjjyDTO;

import cn.gtmap.realestate.common.core.service.rest.init.xuancheng.BdcZnshRestService;
import cn.gtmap.realestate.init.core.service.BdcZnshService;
import cn.gtmap.realestate.init.web.BaseController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0, 2022/5/12
 * @description 智能审核
 */
@RestController
@Api(tags = "智能审核")
public class BdcZnshRestController extends BaseController implements BdcZnshRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcZnshRestController.class);

    @Autowired
    BdcZnshService bdcZnshService;

    @Override
    public BdcSjjyDTO znsh(String xmid) {
        return bdcZnshService.znsh(xmid);
    }
}
