package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlFpXgjlService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlFpxxXgjlDO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlFpXgjlRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "不动产发票修改记录服务接口")
public class BdcSlFpXgjlRestController extends BaseController implements BdcSlFpXgjlRestService {

    @Autowired
    BdcSlFpXgjlService bdcSlFpXgjlService;

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页查询不动产受理发票修改信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "json", value = "不动产发票修改记录信息参数JSON", required = false, paramType = "query")})
    public Page<BdcSlFpxxXgjlDO> listBdcSlFpXgjlByPage(Pageable pageable, String json) {
        return this.bdcSlFpXgjlService.listBdcSlFpXgjlByPage(pageable, json);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询不动产发票信息修改记录信息", notes = "查询不动产发票信息修改记录信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlFpxxXgjlDO", value = "不动产受理发票信息修改记录", required = true, dataType = "BdcSlFpxxXgjlDO", paramType = "body")
    })
    public List<BdcSlFpxxXgjlDO> listBdcSlFpXgjl(@RequestBody BdcSlFpxxXgjlDO bdcSlFpxxXgjlDO) {
        return this.bdcSlFpXgjlService.listBdcSlFpXgjl(bdcSlFpxxXgjlDO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "保存或新增不动产受理发票修改记录", notes = "保存或新增不动产受理发票修改记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlFpxxXgjlDO", value = "不动产受理发票信息修改记录", required = true, dataType = "BdcSlFpxxXgjlDO", paramType = "body")
    })
    public void saveBdcSlFpxxXgjl(@RequestBody BdcSlFpxxXgjlDO bdcSlFpxxXgjlDO) {
        this.bdcSlFpXgjlService.saveBdcSlFpxxXgjl(bdcSlFpxxXgjlDO);
    }
}
