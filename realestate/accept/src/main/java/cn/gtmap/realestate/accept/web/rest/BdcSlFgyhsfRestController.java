package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlFgyhsfService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlFgyhsfDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlFghysfDTO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlFgyhsfRestService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/10/22/9:58
 * @Description:
 */
@RestController
public class BdcSlFgyhsfRestController extends BaseController implements BdcSlFgyhsfRestService {

    @Autowired
    BdcSlFgyhsfService bdcSlFgyhsfService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "保存房改优惠售房信息", notes = " 保存房改优惠售房信息服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSlFgyhsfDO", value = "bdcSlFgyhsfDO", required = true, dataType = "BdcSlFgyhsfDO", paramType = "query")})
    public Integer saveOrUpdateBdcSlFgyhsf(@RequestBody BdcSlFgyhsfDO bdcSlFgyhsfDO) {
        return bdcSlFgyhsfService.saveOrUpdateBdcSlFgyhsf(bdcSlFgyhsfDO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流id获取BdcSlFgyhsf信息", notes = " 根据工作流id获取BdcSlFgyhsf信息服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "gzlslid", required = true, dataType = "String", paramType = "query")})
    public BdcSlFgyhsfDO getBdcSlFgyhsf(@RequestParam(name="gzlslid") String gzlslid) {
        return bdcSlFgyhsfService.getBdcSlFgyhsf(gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public BdcSlFghysfDTO getBdcSlFghysfDTO(@RequestParam(name="gzlslid") String gzlslid) {
        return bdcSlFgyhsfService.getBdcSlFghysfDTO(gzlslid);
    }
}
