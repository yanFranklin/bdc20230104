package cn.gtmap.realestate.init.web.rest;


import cn.gtmap.realestate.common.core.domain.BdcDjyyDyfsGxDO;
import cn.gtmap.realestate.common.core.service.rest.init.BdcDjyyDjfsGxRestService;
import cn.gtmap.realestate.init.core.service.BdcDjyyDyfsGxService;
import cn.gtmap.realestate.init.core.service.impl.BdcDjyyDyfsGxServiceImpl;
import cn.gtmap.realestate.init.web.BaseController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/08/02/14:46
 * @Description:
 */
@RestController
public class BdcDjyyDyfsGxRestController extends BaseController implements BdcDjyyDjfsGxRestService {

    @Autowired
    BdcDjyyDyfsGxService bdcDjyyDyfsGxService;

    /**
     * @param bdcDjyyDyfsGxDO
     * @return
     * @author <a href ="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 查询
     */
    @Override
    @ApiOperation(value ="获取登记原因、抵押方式关系 信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDjyyDyfsGxDO", value = "登记原因、抵押方式关系", required = true, dataType = "BdcDjyyDyfsGxDO")})
    @ResponseStatus(HttpStatus.OK)
    public List<BdcDjyyDyfsGxDO> listBdcDjyyDyfsGx(@RequestBody BdcDjyyDyfsGxDO bdcDjyyDyfsGxDO) {
        return bdcDjyyDyfsGxService.listBdcDjyyDyfsGx(bdcDjyyDyfsGxDO);
    }
}
