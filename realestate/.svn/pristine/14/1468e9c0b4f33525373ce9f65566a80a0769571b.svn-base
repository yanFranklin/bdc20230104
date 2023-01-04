package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcCqtjDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcCqTjQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcCqtjRestService;
import cn.gtmap.realestate.inquiry.service.BdcCqtjService;
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
 * @version: 2022/07/02/18:57
 * @Description:
 */
@RestController
public class BdcCqtjRestController implements BdcCqtjRestService {
    @Autowired
    BdcCqtjService bdcCqtjService;

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询超期台账")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "bdcCqTjQO", value = "bdcCqTjQO", required = false, paramType = "query")})
    @Override
    public List<BdcCqtjDTO> bdcCqtjMx(@RequestBody BdcCqTjQO bdcCqTjQO) {
        return bdcCqtjService.bdcCqtjMx(bdcCqTjQO);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询超期统计图表")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "bdcCqTjQO", value = "bdcCqTjQO", required = false, paramType = "query")})
    @Override
    public Object bdcCqtjMxTb(@RequestBody BdcCqTjQO bdcCqTjQO) {
        return bdcCqtjService.bdcCqtjMxTb(bdcCqTjQO);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询效能台账")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "bdcCqTjQO", value = "bdcCqTjQO", required = false, paramType = "query")})
    @Override
    public List<BdcCqtjDTO> xntjmx(@RequestBody BdcCqTjQO bdcCqTjQO) {
        return bdcCqtjService.xntjmx(bdcCqTjQO);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询超期统计图表")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "bdcCqTjQO", value = "bdcCqTjQO", required = false, paramType = "query")})
    @Override
    public Object xntjCount(@RequestBody BdcCqTjQO bdcCqTjQO) {
        return bdcCqtjService.xntjCount(bdcCqTjQO);
    }
}
