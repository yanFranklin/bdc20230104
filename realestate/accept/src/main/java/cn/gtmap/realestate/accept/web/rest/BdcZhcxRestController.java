package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcZhcxService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcZhcxLogDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcGzltjXmxxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcYbtjQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.GzltjQO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcZhcxRestService;
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
 * @description 不动产综合查询rest服务
 */
@RestController
@Api(tags = "不动产综合查询服务")
public class BdcZhcxRestController extends BaseController implements BdcZhcxRestService {


    @Autowired
    private BdcZhcxService bdcZhcxService;

    /**
     * @param bdcZhcxLogDO
     * @description 综合查询
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "综合查询", notes = "保存综合查询台账查询和打印操作日志")
    public void insertZhcxLog(@RequestBody BdcZhcxLogDO bdcZhcxLogDO){
        bdcZhcxService.insertZhcxLog(bdcZhcxLogDO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("统计人员打印有房无房证明、权属证明、登记簿的工作量信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzltjQO", value = "工作量统计qo对象", required = false, paramType = "body"),
    })
    public List<BdcGzltjXmxxDTO> countRyPrintxx(@RequestBody GzltjQO gzltjQO) {
        return this.bdcZhcxService.countRyPrintxx(gzltjQO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("统计人员综合查询量信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzltjQO", value = "工作量统计qo对象", required = false, paramType = "body"),
    })
    public List<BdcGzltjXmxxDTO> countRyZhcxxx(@RequestBody GzltjQO gzltjQO) {
        return this.bdcZhcxService.countRyZhcxxx(gzltjQO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("统计部门打印有房无房证明、权属证明、登记簿的工作量信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzltjQO", value = "工作量统计qo对象", required = false, paramType = "body"),
    })
    public List<BdcGzltjXmxxDTO> countBmPrintxx(@RequestBody GzltjQO gzltjQO) {
        return this.bdcZhcxService.countBmPrintxx(gzltjQO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("统计部门综合查询的工作量信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzltjQO", value = "工作量统计qo对象", required = false, paramType = "body"),
    })
    public List<BdcGzltjXmxxDTO> countBmZhcxxx(@RequestBody GzltjQO gzltjQO) {
        return this.bdcZhcxService.countBmZhcxxx(gzltjQO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("统计部门综合查询的工作量信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "listBdcYbtjDTO", value = "月报统计的查询结果", required = false, paramType = "body"),
    })
    public Integer countYbtjZhcx(@RequestBody BdcYbtjQO bdcYbtjQO) {
        return this.bdcZhcxService.countYbtjZhcx(bdcYbtjQO);
    }
}
