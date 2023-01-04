package cn.gtmap.realestate.init.web.rest;


import cn.gtmap.realestate.common.core.domain.BdcHmdDO;
import cn.gtmap.realestate.common.core.service.rest.init.BdcHmdRestService;
import cn.gtmap.realestate.init.core.service.BdcHmdService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2021/10/25
 * @description 不动产黑名单Rest服务
 */
@RestController
@Api(tags = "不动产黑名单Rest服务")
public class BdcHmdRestController implements BdcHmdRestService {

    @Autowired
    private BdcHmdService bdcHmdService;

    @Override
    @ApiOperation(value = "更新不动产黑名单信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcHmdDO", value = "不动产黑名单", dataType = "BdcHmdDO", paramType = "body")})
    @ResponseStatus(HttpStatus.OK)
    public void updateBdcHmd(@RequestBody BdcHmdDO bdcHmdDO) {
        this.bdcHmdService.updateBdcHmd(bdcHmdDO);
    }

    @Override
    @ApiOperation(value = "新增不动产黑名单信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcHmdDO", value = "不动产黑名单", dataType = "BdcHmdDO", paramType = "body")})
    @ResponseStatus(HttpStatus.OK)
    public BdcHmdDO saveBdcHmd(@RequestBody BdcHmdDO bdcHmdDO) {
        return this.bdcHmdService.saveBdcHmd(bdcHmdDO);
    }

    @Override
    @ApiOperation(value = "解锁不良记录")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", dataType = "String", paramType = "param")})
    @ResponseStatus(HttpStatus.OK)
    public void jsBljl(@RequestParam(name="processInsId") String processInsId) {
        this.bdcHmdService.jsBljl(processInsId);
    }

    @Override
    @ApiOperation(value = "批量删除不动产黑名单")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "hmdIdList", value = "黑名单id集合", dataType = "List<String>", paramType = "body")})
    @ResponseStatus(HttpStatus.OK)
    public void batchDeleteBdcHmd(@RequestBody List<String> hmdIdList) {
        this.bdcHmdService.batchDeleteBdcHmd(hmdIdList);
    }

    @Override
    @ApiOperation(value = "查询不动产黑名单信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcHmdDO", value = "黑名单DO", dataType = "BdcHmdDO", paramType = "body")})
    @ResponseStatus(HttpStatus.OK)
    public List<BdcHmdDO> queryBdcHmd(@RequestBody BdcHmdDO bdcHmdDO) {
        return this.bdcHmdService.queryBdcHmd(bdcHmdDO);
    }

    @Override
    @ApiOperation(value = "批量解锁不良记录")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcHmdDOList", value = "黑名单DO集合", dataType = "List", paramType = "body")})
    @ResponseStatus(HttpStatus.OK)
    public void jsBljlxx(@RequestBody List<BdcHmdDO> bdcHmdDOList) {
        this.bdcHmdService.jsBljlxx(bdcHmdDOList);
    }
}
