package cn.gtmap.realestate.init.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcJtcyDO;
import cn.gtmap.realestate.common.core.dto.init.BdcJtcySaveDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcJtcyQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.service.rest.init.BdcJtcyRestService;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.init.core.service.BdcJtcyService;
import cn.gtmap.realestate.init.util.Constants;
import cn.gtmap.realestate.init.util.DozerUtils;
import cn.gtmap.realestate.init.web.BaseController;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/8/4
 * @description 不动产家庭成员数据接口
 */
@RestController
@Api(tags = "不动产家庭成员数据接口")
public class BdcJtcyRestController extends BaseController implements BdcJtcyRestService {

    @Autowired
    private BdcJtcyService bdcJtcyService;

    @Autowired
    private DozerUtils dozerUtils;

    @ApiOperation(value = "根据查询参数返回不动产家庭成员信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcJtcyQO", value = "不动产家庭成员封装查询对象", dataType = "BdcJtcyQO", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcJtcyDO> listBdcJtcy(@RequestBody BdcJtcyQO bdcJtcyQO){
        if (!CheckParameter.checkAnyParameter(bdcJtcyQO)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER)+ JSONObject.toJSONString(bdcJtcyQO));
        }
        BdcJtcyDO bdcJtcyDO = new BdcJtcyDO();
        dozerUtils.sameBeanDateConvert(bdcJtcyQO, bdcJtcyDO, false);
        return bdcJtcyService.queryJtcy(bdcJtcyDO);

    }

    @ApiOperation(value = "根据权利人参数获取家庭成员列表")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcQlrQO", value = "权利人查询参数", dataType = "BdcQlrQO", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcJtcyDO> listBdcJtcyByQlr(@RequestBody BdcQlrQO bdcQlrQO){
        return bdcJtcyService.listBdcJtcyByQlr(bdcQlrQO);
    }

    @ApiOperation(value = "根据家庭成员ID和流程ID删除或生成新版本家庭成员信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "jtcyid", value = "家庭成员ID",dataType = "String",paramType = "path"),@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID",dataType = "String",paramType = "path")})
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Override
    public void deleteBdcJtcyByGzlslid(@PathVariable("jtcyid") String jtcyid,@PathVariable("gzlslid") String gzlslid){
        bdcJtcyService.deleteBdcJtcyByGzlslid(jtcyid,gzlslid);

    }

    @ApiOperation(value = "批量保存家庭成员集合")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcJtcySaveDTO", value = "家庭成员批量保存对象", dataType = "BdcJtcySaveDTO", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void saveBatchJtcyxx(@RequestBody BdcJtcySaveDTO bdcJtcySaveDTO){
        bdcJtcyService.saveBatchJtcyxx(bdcJtcySaveDTO);

    }

    @ApiOperation(value = "批量删除当前流程家庭成员")
    @ApiImplicitParams({@ApiImplicitParam(name = "qlrid", value = "权利人ID",dataType = "String",paramType = "path"),@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID",dataType = "String",paramType = "path")})
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Override
    public void deleteBatchJtcyByGzlslid(@PathVariable("qlrid") String qlrid,@PathVariable("gzlslid") String gzlslid){
        bdcJtcyService.deleteBatchJtcyByGzlslid(qlrid, gzlslid);

    }

    @Override
    @ApiOperation(value = "根据户主的证件号信息查询家庭成员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "zjh", value = "证件号信息",dataType = "String",paramType = "path")
    })
    @ResponseStatus(HttpStatus.OK)
    public List<BdcJtcyDO> queryJtcyxxByHzzjh(@PathVariable(value = "zjh") String zjh) {
        return this.bdcJtcyService.queryJtcyxxByhzZjh(zjh);
    }
}
