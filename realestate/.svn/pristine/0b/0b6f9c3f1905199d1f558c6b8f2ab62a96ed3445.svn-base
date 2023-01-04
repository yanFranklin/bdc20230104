package cn.gtmap.realestate.init.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcCdBlxxDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcBlxxQO;
import cn.gtmap.realestate.common.core.service.rest.init.BdcCdBlxxRestService;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.init.core.service.BdcCdBlxxService;
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
 * @description: 补录信息restController
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-09-24 09:21
 **/
@RestController
@Api("查档补录接口服务")
public class BdcCdBlxxRestController extends BaseController implements BdcCdBlxxRestService {
    @Autowired
    DozerUtils dozerUtils;
    @Autowired
    BdcCdBlxxService bdcCdBlxxService;
    /**
     * @param bdcBlxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询补录信息
     * @date : 2020/9/23 17:54
     */
    @ApiOperation(value = "根据查询参数返回不动产补录信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcBlxxQO", value = "不动产补录信息封装查询对象", dataType = "BdcBlxxQO", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public BdcCdBlxxDO queryBdcBlxx(@RequestBody BdcBlxxQO bdcBlxxQO) {
        if (!CheckParameter.checkAnyParameter(bdcBlxxQO)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER)+ bdcBlxxQO.toString());
        }
        BdcCdBlxxDO bdcCdBlxxDO = new BdcCdBlxxDO();
        dozerUtils.sameBeanDateConvert(bdcBlxxQO, bdcCdBlxxDO, false);
        return bdcCdBlxxService.queryBdcBlxx(bdcCdBlxxDO);
    }

    /**
     * @param bdcCdBlxxDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存补录信息
     * @date : 2020/9/23 17:54
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
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除补录信息
     * @date : 2020/9/23 17:54
     */
    @ApiOperation(value = "删除不动产补录信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "blxxid", value = "补录信息id", dataType = "String", required = false,paramType = "query"),
            @ApiImplicitParam(name = "xmid", value = "项目id", dataType = "String",required = false,paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public int deleteBdcBlxx(@RequestParam(name = "blxxid",required = false) String blxxid,@RequestParam(name = "xmid",required = false) String xmid) {
        return bdcCdBlxxService.deleteBdcBlxx(blxxid,xmid);
    }
}
