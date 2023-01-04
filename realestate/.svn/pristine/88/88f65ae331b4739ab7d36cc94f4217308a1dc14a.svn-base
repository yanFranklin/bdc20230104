package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlCxcsService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlCxcsDO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlCxcsRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/5/11
 * @description 不动产受理查询参数信息RestController服务
 */
@RestController
@Api(tags = "不动产收费信息服务接口")
public class BdcSlCxcsRestController extends BaseController implements BdcSlCxcsRestService {

    @Autowired
    BdcSlCxcsService bdcSlCxcsService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "通过工作流实例ID获取不动产受理查询参数", notes = "通过工作流实例ID获取不动产受理查询参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")
    })
    public List<BdcSlCxcsDO> getBdcSlCxcs(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcSlCxcsService.queryBdcSlCxcsByGzlslid(gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "保存不动产查询参数", notes = "保存不动产查询参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlCxcsDO", value = "不动产受理查询参数DO", required = true, dataType = "BdcSlCxcsDO", paramType = "body")
    })
    public void saveBdcSlCxcsByGzlslid(@RequestBody BdcSlCxcsDO bdcSlCxcsDO) {
        this.bdcSlCxcsService.saveBdcSlCxcsByGzlslid(bdcSlCxcsDO);
    }
}
