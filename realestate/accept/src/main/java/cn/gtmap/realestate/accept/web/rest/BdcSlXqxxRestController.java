package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlXqxxService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXqxxDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXqxxQO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlXqxxRestService;
import cn.gtmap.realestate.common.util.CheckParameter;

import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: realestate
 * @description: 需求流转信息rest服务
 * @author: <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @create: 2021-2-23 17:09
 **/
@RestController
@Api(tags = "需求流转查询接口服务")
public class BdcSlXqxxRestController extends BaseController implements BdcSlXqxxRestService {

    @Autowired
    BdcSlXqxxService bdcSlXqxxService;
    /**
     * @param bdcSlXqxxQO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 查询需求流转信息
     * @date : 2023/2/23 17:54
     */
    @ApiOperation(value = "根据查询参数返回需求流转信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSlXqxxQO", value = "不动产需求流转信息封装查询对象", dataType = "bdcSlXqxxQO", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public BdcSlXqxxDO queryBdcSlXqxx(@RequestBody BdcSlXqxxQO bdcSlXqxxQO){
        if (!CheckParameter.checkAnyParameter(bdcSlXqxxQO)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER)+ bdcSlXqxxQO.toString());
        }
        BdcSlXqxxDO bdcSlXqxxDO = new BdcSlXqxxDO();
        BeanUtils.copyProperties(bdcSlXqxxQO,bdcSlXqxxDO);
        return bdcSlXqxxService.queryBdcSlXqxx(bdcSlXqxxDO);
    }

    /**
     * @param bdcSlXqxxDO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 保存需求流转信息
     * @date : 2021/2/23 17:54
     */
    @ApiOperation(value = "保存需求流转信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSlXqxxDO", value = "不动产需求流转信息封装对象", dataType = "bdcSlXqxxDO", paramType = "body")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public BdcSlXqxxDO saveBdcSlXqxx(@RequestBody BdcSlXqxxDO bdcSlXqxxDO) {
        return bdcSlXqxxService.saveBdcSlXqxx(bdcSlXqxxDO);
    }

    /**
     * @param xqxxid
     * @param xmid
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 删除需求流转信息
     * @date : 2021/2/23 17:54
     */
    @Override
    @ApiOperation(value = "删除不动产需求流转信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "xqxxid", value = "需求信息id",required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "xmid", value = "项目id", dataType = "String",required = false, paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    public int deleteBdcSlXqxx(@RequestParam(value = "xqxxid", required = false) String xqxxid, @RequestParam(value = "xmid", required = false) String xmid) {
        return bdcSlXqxxService.deleteBdcSlXqxx(xqxxid,xmid);
    }


    @Override
    @ApiOperation(value = "用于删除流程时，同时删除需求流转信息内容")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInsId", value = "工作流实例ID",required = false, dataType = "String", paramType = "query"),
    })
    @ResponseStatus(HttpStatus.OK)
    public void deleteBdcSlXqxxByGzlslid(@RequestParam(value = "processInsId") String processInsId) {
        bdcSlXqxxService.deleteBdcSlXqxxByGzlslid(processInsId);
    }
}
