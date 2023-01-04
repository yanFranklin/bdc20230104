package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlSfxmJmzcGxService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmJmzcGxDO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlSfxmJmzcGxRestService;
import io.swagger.annotations.Api;
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
 * @program: realestate
 * @description: 收费项目减免政策关系
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-09-09 17:02
 **/
@RestController
@Api(tags = "不动产受理收费项目减免政策关系rest服务")
public class BdcSlSfxmJmzcGxRestController extends BaseController implements BdcSlSfxmJmzcGxRestService {
    @Autowired
    BdcSlSfxmJmzcGxService bdcSlSfxmJmzcGxService;

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流定义ID获取不动产登记小类配置", notes = "根据工作流定义ID获取不动产登记小类配置服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSlSfxmJmzcGxDO", value = "查询条件", required = false, dataType = "BdcSlSfxmJmzcGxDO", paramType = "body")})
    public List<BdcSlSfxmJmzcGxDO> listSfxmJmzcGx(@RequestBody BdcSlSfxmJmzcGxDO bdcSlSfxmJmzcGxDO) {
        return bdcSlSfxmJmzcGxService.listSlSfxmJmzcGx(bdcSlSfxmJmzcGxDO.getJmzt(), bdcSlSfxmJmzcGxDO.getJmzcbz());
    }
}
