package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.accept.core.service.BdcSlJbxxService;
import cn.gtmap.realestate.accept.service.BdcJbxxService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlCshDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlJbxxQO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlJbxxRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/5
 * @description 不动产受理基本信息rest服务
 */
@RestController
@Api(tags = "不动产受理基本信息服务")
public class BdcSlJbxxRestController extends BaseController implements BdcSlJbxxRestService {
    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 不动产受理基本信息数据服务
     */
    @Autowired
    private BdcSlJbxxService bdcSlJbxxService;
    @Autowired
    private BdcJbxxService bdcJbxxService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据基本信息ID获取不动产受理基本信息", notes = "根据基本信息ID获取不动产受理基本信息服务")
    @ApiImplicitParam(name = "jbxxid", value = "基本信息ID", required = true, dataType = "String", paramType = "path")
    public BdcSlJbxxDO queryBdcSlJbxxByJbxxid(@PathVariable(value = "jbxxid") String jbxxid) {
        return bdcSlJbxxService.queryBdcSlJbxxByJbxxid(jbxxid);
    }


    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例ID获取不动产受理基本信息", notes = "根据工作流实例ID获取不动产受理基本信息服务")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")
    public BdcSlJbxxDO queryBdcSlJbxxByGzlslid(@PathVariable(value = "gzlslid") String gzlslid){
        return bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
    }

    /**
     * @param gzlslids
     * @return 不动产受理基本信息
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 根据工作流实例ID获取不动产受理基本信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例ID获取不动产受理基本信息", notes = "根据工作流实例ID获取不动产受理基本信息服务")
    @ApiImplicitParam(name = "gzlslids", value = "工作流实例ID", required = true)
    public List<BdcSlJbxxDO> queryBdcSlJbxxByGzlslids(@RequestBody List<String> gzlslids) {
        return bdcSlJbxxService.queryBdcSlJbxxByGzlslids(gzlslids);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据slbh获取不动产受理基本信息", notes = "根据slbh获取不动产受理基本信息服务")
    @ApiImplicitParam(name = "slbh", value = "slbh", required = true, dataType = "String", paramType = "path")
    public BdcSlJbxxDO queryBdcSlJbxxBySlbh(@PathVariable(value = "slbh") String slbh,@RequestParam(name = "type", required = false) String type){
        return bdcSlJbxxService.queryBdcSlJbxxBySlbh(slbh,type);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "新增不动产受理基本信息", notes = "新增不动产受理基本信息服务服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSlCshDTO", value = "受理初始化信息DTO", dataType = "BdcSlCshDTO", paramType = "body")})
    public BdcSlJbxxDO insertBdcSlJbxx(@RequestBody BdcSlCshDTO bdcSlCshDTO) {
        return bdcJbxxService.insertBdcSlJbxx(bdcSlCshDTO);
    }


    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "新增不动产受理基本信息", notes = "新增不动产受理基本信息服务")
    @ApiImplicitParam(name = "bdcSlJbxxDO", value = "新增不动产受理基本信息", required = true, dataType = "BdcSlJbxxDO")
    public BdcSlJbxxDO insertBdcSlJbxx(@RequestBody BdcSlJbxxDO bdcSlJbxxDO) {
        return bdcSlJbxxService.insertBdcSlJbxx(bdcSlJbxxDO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新不动产受理基本信息", notes = "更新不动产受理基本信息服务")
    @ApiImplicitParam(name = "bdcSlJbxxDO", value = "新增不动产受理基本信息", required = true, dataType = "BdcSlJbxxDO")
    public Integer updateBdcSlJbxx(@RequestBody BdcSlJbxxDO bdcSlJbxxDO) {
        return bdcSlJbxxService.updateBdcSlJbxx(bdcSlJbxxDO);
    }


    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据基本信息ID删除不动产受理基本信息", notes = "根据基本信息ID删除不动产受理基本信息服务")
    @ApiImplicitParam(name = "jbxxid", value = "基本信息ID", required = true, dataType = "String", paramType = "path")
    public Integer deleteBdcSlJbxxByJbxxid(@PathVariable(value = "jbxxid") String jbxxid) {
        return bdcSlJbxxService.deleteBdcSlJbxxByJbxxid(jbxxid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "处理重复受理编号", notes = "处理重复受理编号服务")
    @ApiImplicitParam(name = "slbh", value = "重复受理编号", required = true, dataType = "String", paramType = "path")
    public String dealSameSlbh(@PathVariable(value = "slbh") String slbh){
        return bdcJbxxService.dealSameSlbh(slbh);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取配置的商品房工作流定义id", notes = "获取配置的商品房工作流定义id")
    public String spfGzldyid(){
        return bdcJbxxService.spfGzldyid();
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据用户信息获取用户登记机构", notes = "根据用户信息获取用户登记机构")
    public String queryDjjgByUser(@RequestBody UserDto userDto){
        return bdcJbxxService.queryDjjgByUser(userDto);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "存在规则例外保存不动产受理基本信息", notes = "存在规则例外保存不动产受理基本信息")
    public BdcSlJbxxDO saveGzlwBdcSlJbxx(@RequestBody BdcSlCshDTO bdcSlCshDTO){
        return bdcJbxxService.saveGzlwBdcSlJbxx(bdcSlCshDTO);

    }

    /**
     * @param bdcSlJbxxQO 不动产受理基本信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 查询不动产受理基本信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询不动产受理基本信息", notes = "查询不动产受理基本信息")
    @ApiImplicitParam(name = "bdcSlHsxxQO", value = "不动产受理核税信息查询条件", required = true, dataType = "bdcSlHsxxQO")
    public List<BdcSlJbxxDO> listBdcSlJbxxByJbxxQO(@RequestBody BdcSlJbxxQO bdcSlJbxxQO){
        return bdcSlJbxxService.listBdcSlJbxxByJbxxQO(bdcSlJbxxQO);

    }


}
