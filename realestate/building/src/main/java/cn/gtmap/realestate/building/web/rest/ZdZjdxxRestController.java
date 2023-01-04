package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.core.service.ZdZjdxxService;
import cn.gtmap.realestate.building.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.building.ZdZjdxxDO;
import cn.gtmap.realestate.common.core.service.rest.building.ZdZjdxxRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/14
 * @description 宅基地相关服务
 */
@RestController
@Api(tags = "宅基地服务接口")
public class ZdZjdxxRestController extends BaseController implements ZdZjdxxRestService {
    @Autowired
    private ZdZjdxxService zdZjdxxService;

    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.ZdZjdxxDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据BDCDYH查询宅基地信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询宅基地基本信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public List<ZdZjdxxDO> listZjdxxByBdcdy(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return zdZjdxxService.listZjdxxByBdcdy(bdcdyh);
    }

}