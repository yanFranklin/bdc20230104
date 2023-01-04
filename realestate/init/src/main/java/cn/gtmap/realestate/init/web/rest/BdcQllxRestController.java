package cn.gtmap.realestate.init.web.rest;

import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.init.BdcDyaqDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcQlxxRequestDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.rest.init.BdcQllxRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.LogActionConstans;
import cn.gtmap.realestate.init.core.service.BdcFdcq3GyxxService;
import cn.gtmap.realestate.init.core.service.BdcFdcqFdcqxmService;
import cn.gtmap.realestate.init.core.service.BdcFwFsssService;
import cn.gtmap.realestate.init.core.service.BdcQllxService;
import cn.gtmap.realestate.init.util.Constants;
import cn.gtmap.realestate.init.web.BaseController;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/11/1
 * @description 权利信息相关接口
 */
@RestController
@Api(tags = "不动产权利信息服务接口")
public class BdcQllxRestController extends BaseController implements BdcQllxRestService {
    @Autowired
    private BdcQllxService bdcQllxService;
    @Autowired
    private BdcFdcqFdcqxmService bdcFdcqFdcqxmService;
    @Autowired
    private BdcFdcq3GyxxService bdcFdcq3GyxxService;
    @Autowired
    private BdcFwFsssService bdcFwFsssService;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    /**
     * @param qllx 权利类型
     * @return {BdcQl} 不动产权利空对象
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 通过qllx确认权利
     */
    @ApiOperation(value = "通过qllx确认权利")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "qllx", value = "权利类型", required = true, dataType = "String", paramType = "path")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public BdcQl makeSureQllx(@PathVariable("qllx") String qllx) {
        return bdcQllxService.makeSureQllx(qllx);
    }

    /**
     * 通过项目id查询权利基本信息
     *
     * @param xmid
     * @return
     */
    @ApiOperation(value = "查询权利基本信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public BdcQl queryQlxx(@PathVariable("xmid") String xmid) {
        return bdcQllxService.queryQllxDO(xmid);
    }

    @ApiOperation(value = "通过项目ID查询原权利基本信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public BdcQl queryBdcYqlxx(@PathVariable("xmid") String xmid){
        return bdcQllxService.queryBdcYqlxx(xmid);

    }

    /**
     * @param xmids@return {BdcQl} 不动产权利
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 通过项目ID查询原权利基本信息
     */
    @Override
    public List<BdcQl> queryBdcYqlxxs(@RequestParam("xmids") String xmids) {
        return bdcQllxService.queryBdcYqlxxs(xmids);
    }

    /**
     * @param slbh 受理编号
     * @return {List} 权利信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取当前流程生成的所有权利信息
     */
    @ApiOperation(value = "获取当前流程生成的所有权利信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "slbh", value = "受理编号", required = true, dataType = "String", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcQl> listQlxxBySlbh(@RequestParam("slbh") String slbh) {
        if (StringUtils.isBlank(slbh)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return bdcQllxService.listQlxxDO(slbh,null);
    }

    /**
     * @param processInsId 工作流实例ID
     * @return {List} 权利信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取当前流程生成的所有权利信息
     */
    @ApiOperation(value = "获取当前流程生成的所有权利信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcQl> listQlxxByProcessInsId(@RequestParam("processInsId") String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return bdcQllxService.listQlxxDO(null,processInsId);
    }

    /**
     * @param processInsId 工作流实例ID
     * @return {List} 权利类型
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取当前流程生成的所有权利类型
     */
    @ApiOperation(value = "获取当前流程生成的所有权利类型")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<String> listQllxByProcessInsId(@RequestParam("processInsId") String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return bdcQllxService.listQllx(processInsId);
    }

    /**
     * @param qlid 权利id
     * @return {List} 项目内多幢项目信息
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取当前房地产权的项目内多幢项目信息
     */
    @ApiOperation(value = "查询当前房地产权的项目内多幢项目信息(根据幢号和总层数排序)")
    @ApiImplicitParam(name = "qlid", value = "权利ID", required = true, dataType = "String", paramType = "path")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcFdcqFdcqxmDO> listFdcqXm(@PathVariable("qlid") String qlid) {
        return bdcFdcqFdcqxmService.queryFdcqxmListByQlid(qlid);
    }

    /**
     * @param qlid 权利id
     * @return {List} 建筑物区分所有权业主共有部分登记信息_共有部分信息
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取建筑物区分所有权业主共有部分登记信息_共有部分信息
     */
    @ApiOperation(value = "查询当前建筑物区分所有权业主共有部分登记信息_共有部分信息")
    @ApiImplicitParam(name = "qlid", value = "权利ID", required = true, dataType = "String", paramType = "path")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcFdcq3GyxxDO> listFdcq3Gyxx(@PathVariable("qlid") String qlid) {
        return bdcFdcq3GyxxService.queryFdcq3GyxxListByQlid(qlid);
    }

    /**
     * @param slbh 受理编号
     * @return {List} 注销权利信息
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取当前流程所注销的所有权利信息
     */
    @ApiOperation(value = "获取当前流程注销的所有权利信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "slbh", value = "受理编号", required = true, dataType = "String", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcQl> listZxQlxxBySlbh(@RequestParam("slbh") String slbh) {
        if (StringUtils.isBlank(slbh)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return bdcQllxService.listZxQlxxDO(slbh,null);
    }

    /**
     * @param processInsId 工作流实例ID
     * @return {List} 注销权利信息
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取当前流程所注销的所有权利信息
     */
    @ApiOperation(value = "获取当前流程注销的所有权利信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcQl> listZxQlxxByProcessInsId(@RequestParam("processInsId") String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return bdcQllxService.listZxQlxxDO(null,processInsId);
    }

    /**
     * @param processInsId 工作流实例ID
     * @return {List} 注销权利类型
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取当前流程所注销的所有权利类型
     */
    @ApiOperation(value = "获取当前流程所注销的所有权利类型")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<String> listZxQllxByProcessInsId(@RequestParam("processInsId") String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return bdcQllxService.listZxQllx(processInsId);
    }

    /**
     * @param slbh 受理编号
     * @return {List} 注销权利信息
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取当前流程需还原的已注销的所有权利信息
     */
    @ApiOperation(value = "获取当前流程需还原的已注销的所有权利信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "slbh", value = "受理编号", required = true, dataType = "String", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcQl> listHyYzxQlxxBySlbh(@RequestParam("slbh") String slbh) {
        if (StringUtils.isBlank(slbh)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return bdcQllxService.listHyYzxQlxx(slbh,null);
    }

    /**
     * @param processInsId 工作流实例ID
     * @return {List} 注销权利信息
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取当前流程需还原的已注销的所有权利信息
     */
    @ApiOperation(value = "获取当前流程需还原的已注销的所有权利信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcQl> listHyYzxQlxxByProcessInsId(@RequestParam("processInsId") String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return bdcQllxService.listHyYzxQlxx(null,processInsId);
    }

    /**
     * @param bdcTdsyqDO 权利信息
     * @return 更新数量
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 修改BDC_TDSYQ信息
     */
    @ApiOperation(value = "修改土地所有权信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcTdsyqDO", value = "土地所有权信息", required = true, dataType = "BdcTdsyqDO")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public int updateTdsyq(@RequestBody BdcTdsyqDO bdcTdsyqDO) {
        return bdcQllxService.updateBdcQl(bdcTdsyqDO);
    }

    /**
     * @param bdcJsydsyqDO 权利信息
     * @return 更新数量
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 修改BDC_JSYDSYQ信息
     */
    @ApiOperation(value = "修改建设用地使用权信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcJsydsyqDO", value = "建设用地使用权信息", required = true, dataType = "BdcJsydsyqDO")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public int updateJsydsyq(@RequestBody BdcJsydsyqDO bdcJsydsyqDO) {
        return bdcQllxService.updateBdcQl(bdcJsydsyqDO);
    }


    /**
     * @param bdcFdcqDO 权利信息
     * @return 更新数量
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 修改BDC_FDCQ信息
     */
    @ApiOperation(value = "修改房地产权信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcFdcqDO", value = "房地产权信息", required = true, dataType = "BdcFdcqDO")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public int updateFdcq(@RequestBody BdcFdcqDO bdcFdcqDO) {
        return bdcQllxService.updateBdcQl(bdcFdcqDO);
    }

    /**
     * @param bdcTdcbnydsyqDO 权利信息
     * @return 更新数量
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 修改BDC_TDCBNYDSYQ信息
     */
    @ApiOperation(value = "修改土地承包经营权、农用地使用权信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcTdcbnydsyqDO", value = "土地承包经营权、农用地使用权信息", required = true, dataType = "BdcTdcbnydsyqDO")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public int updateTdcbnydsyq(@RequestBody BdcTdcbnydsyqDO bdcTdcbnydsyqDO) {
        return bdcQllxService.updateBdcQl(bdcTdcbnydsyqDO);
    }

    /**
     * @param bdcLqDO 权利信息
     * @return 更新数量
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 修改BDC_LQ信息
     */
    @ApiOperation(value = "修改林权信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcLqDO", value = "林权信息", required = true, dataType = "BdcLqDO")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public int updateLq(@RequestBody BdcLqDO bdcLqDO) {
        return bdcQllxService.updateBdcQl(bdcLqDO);
    }

    /**
     * @param bdcHysyqDO 权利信息
     * @return 更新数量
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 修改BDC_HYSYQ信息
     */
    @ApiOperation(value = "修改海域（含无居民海岛）使用权信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcHysyqDO", value = "海域（含无居民海岛）使用权信息", required = true, dataType = "BdcHysyqDO")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public int updateHysyq(@RequestBody BdcHysyqDO bdcHysyqDO) {
        return bdcQllxService.updateBdcQl(bdcHysyqDO);
    }

    /**
     * @param bdcGjzwsyqDO 权利信息
     * @return 更新数量
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 修改BDC_GJZWSYQ信息
     */
    @ApiOperation(value = "修改构（建）筑物所有权信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcGjzwsyqDO", value = "构（建）筑物所有权信息", required = true, dataType = "BdcGjzwsyqDO")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public int updateGjzwsyq(@RequestBody BdcGjzwsyqDO bdcGjzwsyqDO) {
        return bdcQllxService.updateBdcQl(bdcGjzwsyqDO);
    }

    /**
     * @param bdcDyiqDO 权利信息
     * @return 更新数量
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 修改BDC_DYIQ信息
     */
    @ApiOperation(value = "修改地役权信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDyiqDO", value = "地役权信息", required = true, dataType = "BdcDyiqDO")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public int updateDyiq(@RequestBody BdcDyiqDO bdcDyiqDO) {
        return bdcQllxService.updateBdcQl(bdcDyiqDO);
    }

    /**
     * @param bdcQtxgqlDO 权利信息
     * @return 更新数量
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 修改BDC_QTXGQL信息
     */
    @ApiOperation(value = "修改其他相关权利信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcQtxgqlDO", value = "其他相关权利信息", required = true, dataType = "BdcQtxgqlDO")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public int updateQtxgql(@RequestBody BdcQtxgqlDO bdcQtxgqlDO) {
        return bdcQllxService.updateBdcQl(bdcQtxgqlDO);
    }

    /**
     * @param bdcFdcq3DO 权利信息
     * @return 更新数量
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 修改BDC_FDCQ3信息
     */
    @ApiOperation(value = "修改建筑物区分所有权业主共有部分登记信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcFdcq3DO", value = "建筑物区分所有权业主共有部分登记信息", required = true, dataType = "BdcFdcq3DO")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public int updateFdcq3(@RequestBody BdcFdcq3DO bdcFdcq3DO) {
        return bdcQllxService.updateBdcQl(bdcFdcq3DO);
    }

    /**
     * @param bdcDyaqDO 权利信息
     * @return 更新数量
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 修改BDC_DYAQ信息
     */
    @ApiOperation(value = "修改抵押权信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDyaqDO", value = "抵押权信息", required = true, dataType = "BdcDyaqDO")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public int updateDyaq(@RequestBody BdcDyaqDO bdcDyaqDO) {
        return bdcQllxService.updateBdcQl(bdcDyaqDO);
    }

    /**
     * @param bdcYgDO 权利信息
     * @return 更新数量
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 修改BDC_YG信息
     */
    @ApiOperation(value = "修改预告登记信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcYgDO", value = "预告登记信息", required = true, dataType = "BdcYgDO")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public int updateYg(@RequestBody BdcYgDO bdcYgDO) {
        return bdcQllxService.updateBdcQl(bdcYgDO);
    }

    /**
     * @param bdcYyDO 权利信息
     * @return 更新数量
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 修改BDC_YY信息
     */
    @ApiOperation(value = "修改异议登记信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcYyDO", value = "异议登记信息", required = true, dataType = "BdcYyDO")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public int updateYy(@RequestBody BdcYyDO bdcYyDO) {
        return bdcQllxService.updateBdcQl(bdcYyDO);
    }

    /**
     * @param bdcCfDO 权利信息
     * @return 更新数量
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 修改BDC_CF信息
     */
    @ApiOperation(value = "修改查封登记信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcCfDO", value = "查封登记信息", required = true, dataType = "BdcCfDO")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public int updateCf(@RequestBody BdcCfDO bdcCfDO) {
        return bdcQllxService.updateBdcQl(bdcCfDO);
    }

    /**
     * @param bdcFdcqFdcqxmDO 权利信息
     * @return 更新数量
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 修改BDC_FDCQ_FDCQXM信息
     */
    @ApiOperation(value = "修改房地产权（项目内多幢）项目信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcFdcqFdcqxmDO", value = "房地产权（项目内多幢）项目信息", required = true, dataType = "BdcFdcqFdcqxmDO")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public int updateFdcqxm(@RequestBody BdcFdcqFdcqxmDO bdcFdcqFdcqxmDO) {
        return bdcQllxService.updateObj(bdcFdcqFdcqxmDO, bdcFdcqFdcqxmDO.getFzid());
    }

    @ApiOperation(value = "根据权利ID批量更新房地产权（项目内多幢）项目信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "qlid", value = "权利ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "bdcFdcqFdcqxmDO", value = "房地产权（项目内多幢）项目信息", required = true, dataType = "BdcFdcqFdcqxmDO")
            })
    @ResponseStatus(HttpStatus.OK)
    @Override
    public int updateFdcqxmByQlid(@PathVariable(value = "qlid") String qlid, @RequestBody BdcFdcqFdcqxmDO bdcFdcqFdcqxmDO) {
        return this.bdcFdcqFdcqxmService.updateFdcqxmByQlid(qlid, bdcFdcqFdcqxmDO);
    }

    /**
     * @param bdcFdcq3GyxxDO 权利信息
     * @return 更新数量
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 修改BDC_FDCQ3_GYXX信息
     */
    @ApiOperation(value = "修改建筑物区分所有权业主共有部分登记_共有部分信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcFdcq3GyxxDO", value = "建筑物区分所有权业主共有部分登记_共有部分信息", required = true, dataType = "BdcFdcq3GyxxDO")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public int updateFdcq3Gyxx(@RequestBody BdcFdcq3GyxxDO bdcFdcq3GyxxDO) {
        return bdcQllxService.updateObj(bdcFdcq3GyxxDO, bdcFdcq3GyxxDO.getGyxxid());
    }


    /**
     * 根据项目ID获取附属设施集合数据
     *
     * @param xmid
     * @return List<BdcFwfsssDO> 附属设施集合
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @ApiOperation(value = "根据项目ID获取附属设施集合数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "被解封的项目id", required = true, dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcFwfsssDO> listFwfsss(@PathVariable("xmid") String xmid) {
        return bdcFwFsssService.queryFwFsssListByXmid(xmid);
    }

    @ApiOperation(value = "批量更新不动产权利")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDjxxUpdateQO", value = "登记信息更新对象", required = true, dataType = "BdcDjxxUpdateQO", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public int updateBatchBdcQl(@RequestBody BdcDjxxUpdateQO bdcDjxxUpdateQO) throws Exception{
        return bdcQllxService.updateBatchBdcQl(bdcDjxxUpdateQO);

    }

    @ApiOperation(value = "根据bdcXmQO获取不动产项目集合数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcXmQO", value = "不动产项目查询对象", required = true, dataType = "bdcXmQO", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcXmDO> getBdcXmByQllx(@RequestBody BdcXmQO bdcXmQO){
        Example example = new Example(BdcXmDO.class);
        example.createCriteria().andEqualTo("bdcdyh", bdcXmQO.getBdcdyh()).andEqualTo("qszt",bdcXmQO.getQszt()).andIn("qllx",bdcXmQO.getQllxs());
        return entityMapper.selectByExampleNotNull(example);
    }

    /**
     * @author  <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @param   xmidList 项目ID
     * @return  {List} 权利信息
     * @description 根据项目ID获取生成的所有权利信息
     */
    @ApiOperation(value = "根据项目ID获取生成的所有权利信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmidList", value = "项目IDList", dataType = "String", paramType = "body")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcQl> listQlxxByXmids(@RequestBody List<String> xmidList){
        return bdcQllxService.listQlxxDOByXmids(xmidList);
    }

    /**
     * @param bdcQl 权利信息
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新同一个证书的权利附记
     */
    @ApiOperation(value = "更新同一个证书的权利附记")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcQl", value = "不动产权利接口对象", dataType = "BdcQl", paramType = "body")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public int updateBdcZsQlFj(@RequestBody BdcQl bdcQl) {
        return bdcQllxService.updateBdcZsQlFj(bdcQl);
    }

    /**
     * @param xmid 项目id
     * @return 规划用途
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 根据xmid获取规划用途
     */
    @ApiOperation(value = "根据xmid获取规划用途")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目id", dataType = "String", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public String getGhytByXmid(@PathVariable("xmid") String xmid){
        return bdcQllxService.getGhytByXmid(xmid);
    }

    /**
     * @param processInsId
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 同步抵押权的bdbzzqse到zgzqse字段
     */
    @ApiOperation(value = "同步抵押权的bdbzzqse到zgzqse字段")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void updateBdcDyaqZgzqse(@RequestParam("processInsId") String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        bdcQllxService.updateBdcDyaqZgzqse(processInsId);
    }

    /**
     * @author  <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param xmids
     * @return  {List} 限制信息
     * @description 根据不动产单元号获取限制信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    public List<BdcQl> listXzxxByXmids(@RequestBody List<String> xmids) {
        return bdcQllxService.listXzxxByXmids(xmids);
    }

    /**
     * @author  <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param   bdcdyhs 项目ID
     * @return  {List} 限制信息
     * @description 根据不动产单元号获取限制信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    public List<BdcQl> listXzxxByBdcdyhs(@RequestBody List<String> bdcdyhs) {
        return bdcQllxService.listXzxxByBdcdyhs(bdcdyhs);
    }

    /**
     * 更新在建建筑物抵押范围
     * 土地证坐落+所有不动产单元的房间号（顿号分隔）+房屋及对应的建设用地使用权
     *
     * @param xmidList xmidList
     * @param gzldyid  gzldyid
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    @ApiOperation(value = "更新在建建筑物抵押范围")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmidList", value = "xmidList", required = true, dataType = "List", paramType = "body"),
            @ApiImplicitParam(name = "gzldyid", value = "gzldyid", required = true, dataType = "String", paramType = "query")})
    public int updateZjjwdyfw(@RequestBody List<String> xmidList, @RequestParam String gzldyid) {
        if (CollectionUtils.isEmpty(xmidList)) {
            throw new MissingArgumentException("xmid");
        }
        if (StringUtils.isBlank(gzldyid)) {
            throw new MissingArgumentException("gzldyid");
        }

        BdcXmQO bdcXmQO = new BdcXmQO(xmidList.get(0));
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        List<BdcQl> bdcQls = bdcQllxService.listQlxxDOByXmids(xmidList);
        if (CollectionUtils.isEmpty(bdcQls) && !CommonConstantUtils.DJLX_ZXDJ_DM.equals(bdcXmDOList.get(0).getDjlx())) {
            throw new MissingArgumentException("权利信息");
        }
        String qjgldm ="";
        if(StringUtils.isNotBlank(xmidList.get(0))) {
            BdcXmFbDO bdcXmFbDO = entityMapper.selectByPrimaryKey(BdcXmFbDO.class, xmidList.get(0));
            if(bdcXmFbDO != null){
                qjgldm =bdcXmFbDO.getQjgldm();
            }
        }
        return bdcQllxService.updateZjjwdyfw(bdcQls, gzldyid,qjgldm);
    }

    @Override
    public Map queryFdcqByZl(@RequestParam(value = "zl", required = false)String zl) {
        return bdcQllxService.queryFdcqByZl(zl);
    }

    /**
     * 查询权利人权利信息
     * @param bdcQlxx 查询参数
     * @return List<BdcQl> 权利信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询权利人权利信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcQlxx", value = "第三方接口入参dto", paramType = "query")})
    public List<BdcQl> getBdcQlxxByQlr(@RequestBody BdcQlxxRequestDTO bdcQlxx){
        return bdcQllxService.getBdcQlxxByQlr(bdcQlxx);
    }



}
