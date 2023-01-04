package cn.gtmap.realestate.init.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcCdxxDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcCdxxQO;
import cn.gtmap.realestate.common.core.service.rest.init.BdcCdxxRestService;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.init.core.service.BdcCdxxService;
import cn.gtmap.realestate.init.util.Constants;
import cn.gtmap.realestate.init.util.DozerUtils;
import cn.gtmap.realestate.init.web.BaseController;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: realestate
 * @description: 查档信息rest服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-09-23 17:09
 **/
@RestController
@Api("查档查询接口服务")
public class BdcCdxxRestController extends BaseController implements BdcCdxxRestService {
    @Autowired
    DozerUtils dozerUtils;
    @Autowired
    BdcCdxxService bdcCdxxService;


    /**
     * @param bdcCdxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询查档信息
     * @date : 2020/9/23 17:54
     */
    @ApiOperation(value = "根据查询参数返回不动产查档信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcCdxxQO", value = "不动产查档信息封装查询对象", dataType = "BdcCdxxQO", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public BdcCdxxDO queryBdcCdxx(@RequestBody BdcCdxxQO bdcCdxxQO){
        if (!CheckParameter.checkAnyParameter(bdcCdxxQO)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER)+ bdcCdxxQO.toString());
        }
        BdcCdxxDO bdcCdxxDO = new BdcCdxxDO();
        dozerUtils.sameBeanDateConvert(bdcCdxxQO, bdcCdxxDO, false);
        return bdcCdxxService.queryBdcCdxx(bdcCdxxDO);
    }


    @ApiOperation(value = "保存更新不动产查档信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcCdxxDO", value = "不动产查档信息对象", dataType = "BdcCdxxDO", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public BdcCdxxDO saveBdcCdxx(@RequestBody BdcCdxxDO bdcCdxxDO) {
        return bdcCdxxService.saveBdcCdxx(bdcCdxxDO);
    }

    @ApiOperation(value = "删除不动产查档信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "cdxxid", value = "查档信息id",required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "xmid", value = "项目id", dataType = "String",required = false, paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public int deleteBdcCdxx(@RequestParam(value = "cdxxid",required = false) String cdxxid,@RequestParam(value = "xmid",required = false) String xmid) {
        return bdcCdxxService.deleteBdcCdxx(cdxxid,xmid);
    }
}
