package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlTfxxService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlTfxxDO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlTfxxQO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlTfxxRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "不动产收费信息服务接口")
public class BdcSlTfxxRestController  extends BaseController implements BdcSlTfxxRestService {

    @Autowired
    BdcSlTfxxService bdcSlTfxxService;

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页查询不动产受理退费信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "json", value = "退费查询信息参数JSON", required = false, paramType = "query")})
    public Page<BdcSlTfxxDO> listBdcSlTfxxByPage(Pageable pageable, String json) {
        return bdcSlTfxxService.listBdcSlTfxx(pageable, json);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询不动产退费信息", notes = "查询不动产退费信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlTfxxQO", value = "不动产受理退费信息QO", required = true, dataType = "BdcSlTfxxQO", paramType = "body")
    })
    public List<BdcSlTfxxDO> listbdcSlTfxx(@RequestBody BdcSlTfxxQO bdcSlTfxxQO) {
        return this.bdcSlTfxxService.queryBdcSlTfxxList(bdcSlTfxxQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "生成退费信息内容", notes = "生成退费信息内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "slbh", value = "受理编号", required = true, dataType = "String", paramType = "param")
    })
    public List<BdcSlTfxxDO> generateTfxx(@RequestParam(value="slbh") String slbh) {
        return this.bdcSlTfxxService.generateTfxx(slbh);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "批量保存或新增不动产受理退费信息", notes = "批量保存或新增不动产受理退费信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlTfxxDOList", value = "不动产受理退费信息集合", required = true, dataType = "List<BdcSlTfxxDO>", paramType = "body")
    })
    public void plSaveBdcSlTfxx(@RequestBody List<BdcSlTfxxDO> bdcSlTfxxDOList) {
        this.bdcSlTfxxService.plSaveBdcSlTfxx(bdcSlTfxxDOList);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "批量删除不动产退费信息", notes = "批量删除不动产退费信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tfxxidList", value = "退费信息ID集合", required = true, dataType = "List<String>", paramType = "body")
    })
    public void plRemoveBdcSlTfxx(@RequestBody List<String> tfxxidList) {
        this.bdcSlTfxxService.plRemoveBdcSlTfxx(tfxxidList);
    }
}
