package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlCdBlxxService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcCdBlxxDO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlCdBlxxRestService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/08/12/14:56
 * @Description:
 */
@RestController
@Api("查档补录接口服务")
public class BdcSlCdBlxxRestController extends BaseController implements BdcSlCdBlxxRestService {

    @Autowired
    BdcSlCdBlxxService bdcCdBlxxService;
    /**
     * @param bdcCdBlxxDO
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 查询补录信息
     * @date : 2020/8/12
     */
    @ApiOperation(value = "根据查询参数返回不动产补录信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcCdBlxxDO", value = "不动产补录信息封装查询对象", dataType = "BdcCdBlxxDO", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public BdcCdBlxxDO queryBdcBlxx(@RequestBody BdcCdBlxxDO bdcCdBlxxDO) {
        return bdcCdBlxxService.queryBdcBlxx(bdcCdBlxxDO);
    }

    /**
     * @param bdcCdBlxxDO
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 保存补录信息
     * @date : 2020/8/12
     */
    @ApiOperation(value = "保存更新不动产补录信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcCdBlxxDO", value = "不动产补录信息对象", dataType = "BdcCdBlxxDO", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public BdcCdBlxxDO saveBdcBlxx(@RequestBody BdcCdBlxxDO bdcCdBlxxDO) {
        return bdcCdBlxxService.saveBdcBlxx(bdcCdBlxxDO);
    }

    /**
     * @param blxxid
     * @param xmid
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 删除补录信息
     * @date : 2020/8/12
     */
    @ApiOperation(value = "删除不动产补录信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "blxxid", value = "补录信息id", dataType = "String", required = false,paramType = "query"),
            @ApiImplicitParam(name = "xmid", value = "项目id", dataType = "String",required = false,paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public int deleteBdcBlxx(@RequestParam(name = "blxxid",required = false) String blxxid, @RequestParam(name = "xmid",required = false) String xmid) {
        return bdcCdBlxxService.deleteBdcBlxx(blxxid,xmid);
    }

}
