package cn.gtmap.realestate.register.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.domain.BdcDysjZbPzDO;
import cn.gtmap.realestate.common.core.service.rest.register.BdcDypzRestService;
import cn.gtmap.realestate.register.service.BdcDypzService;
import cn.gtmap.realestate.register.web.main.BaseController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/1/17
 * @description 不动产打印配置表服务
 */
@RestController
@Api(tags = "不动产打印配置表服务")
public class BdcDypzRestController extends BaseController implements BdcDypzRestService {

    @Autowired
    BdcDypzService bdcDypzService;

    /**
     * @param bdcDysjPzDO
     * @return List<BdcDysjPzDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取不动产打印配置主表数据
     */
    @Override
    public List<BdcDysjPzDO> listBdcDysjPz(@RequestBody BdcDysjPzDO bdcDysjPzDO) {
        return bdcDypzService.listBdcDysjPz(bdcDysjPzDO);
    }

    /**
     * @param bdcDysjZbPzDO
     * @return List<BdcDysjZbPzDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取打印配置子表数据
     */
    @Override
    public List<BdcDysjZbPzDO> listBdcDysjZbPz(@RequestBody BdcDysjZbPzDO bdcDysjZbPzDO) {
        return bdcDypzService.listBdcDysjZbPz(bdcDysjZbPzDO);
    }
}
