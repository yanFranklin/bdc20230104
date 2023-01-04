package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlSqrService;
import cn.gtmap.realestate.accept.service.BdcGenerateQlrService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlJtcyGroupDTO;
import cn.gtmap.realestate.common.core.dto.accept.YcslYmxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSqrQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlSqrRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/5
 * @description 不动产受理申请人rest服务
 */
@RestController
@Api(tags = "不动产受理申请人服务")
public class BdcSlSqrRestController extends BaseController implements BdcSlSqrRestService {
    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 不动产受理申请人数据服务
     */
    @Autowired
    private BdcSlSqrService bdcSlSqrDOService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    BdcGenerateQlrService bdcGenerateQlrService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据申请人ID获取不动产受理申请人", notes = "根据申请人ID获取不动产受理申请人服务")
    @ApiImplicitParam(name = "sqrid", value = "申请人ID", required = true, dataType = "String", paramType = "path")
    public BdcSlSqrDO queryBdcSlSqrBySqrid(@PathVariable(value = "sqrid") String sqrid) {
        return bdcSlSqrDOService.queryBdcSlSqrBySqrid(sqrid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据项目ID获取不动产受理申请人", notes = "根据项目ID获取不动产受理申请人服务")
    @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")
    public List<BdcSlSqrDO> listBdcSlSqrByXmid(@PathVariable(value = "xmid") String xmid,@RequestParam("sqrlb") String sqrlb) {
        return bdcSlSqrDOService.listBdcSlSqrByXmid(xmid,sqrlb);
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "新增不动产受理申请人", notes = "新增不动产受理申请人服务服务")
    @ApiImplicitParam(name = "bdcSlSqrDO", value = "新增不动产受理申请人", required = true, dataType = "BdcSlSqrDO")
    public BdcSlSqrDO insertBdcSlSqr(@RequestBody BdcSlSqrDO bdcSlSqrDO) {
        return bdcSlSqrDOService.insertBdcSlSqr(bdcSlSqrDO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新不动产受理申请人", notes = "更新不动产受理申请人服务")
    @ApiImplicitParam(name = "bdcSlSqrDO", value = "新增不动产受理申请人", required = true, dataType = "BdcSlSqrDO")
    public Integer updateBdcSlSqr(@RequestBody BdcSlSqrDO bdcSlSqrDO) {
        return bdcSlSqrDOService.updateBdcSlSqr(bdcSlSqrDO);
    }


    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据申请人ID删除不动产受理申请人", notes = "根据申请人ID删除不动产受理申请人服务")
    @ApiImplicitParam(name = "sqrid", value = "申请人ID", required = true, dataType = "String", paramType = "path")
    public Integer deleteBdcSlSqrBySqrid(@PathVariable(value = "sqrid") String sqrid) {
        return bdcSlSqrDOService.deleteBdcSlSqrBySqrid(sqrid);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据项目ID删除不动产受理申请人", notes = "根据项目ID删除不动产受理申请人服务")
    @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")
    public Integer deleteBdcSlSqrByXmid(@PathVariable(value = "xmid") String xmid) {
        return bdcSlSqrDOService.deleteBdcSlSqrByXmid(xmid,"");
    }

    /**
     * @param jbxxid 基本信息ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据基本信息ID删除不动产受理申请人
     */
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据基本信息ID删除不动产受理申请人", notes = "根据基本信息ID删除不动产受理申请人服务")
    @ApiImplicitParam(name = "jbxxid", value = "基本信息ID", required = true, dataType = "String", paramType = "path")
    public void deleteBdcSlSqrByJbxxid(@PathVariable(value = "jbxxid") String jbxxid){
        bdcSlSqrDOService.batchDeleteSlSqr(jbxxid);


    }

    /**
     * @param gzlslid 工作流实例id
     * @param qlrlb   权利人类别
     * @param djxl    登记小类
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2019/11/30 13:57
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "从登记获取到所有的义务人进行去重封装", notes = "从登记获取到所有的义务人进行去重封装服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "qlrlb", value = "权利人类别", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "djxl", value = "登记小类", required = false, dataType = "String", paramType = "query"),
    })
    public BdcQlrDO generateYwr(@PathVariable(name = "gzlslid") String gzlslid,@PathVariable(name = "qlrlb") String qlrlb,@RequestParam(value = "djxl", required = false) String djxl) {
        if(StringUtils.isBlank(gzlslid) || StringUtils.isBlank(qlrlb)) {
            throw new AppException("获取权利人数据缺失查询参数");
        }
        List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listAllBdcQlr(gzlslid,qlrlb,djxl);
        if(CollectionUtils.isNotEmpty(bdcQlrDOList)) {
            return bdcGenerateQlrService.getYwrData(bdcQlrDOList);
        } else {
            return new BdcQlrDO();
        }
    }

    /**
     * 根据查询参数查询受理申请人信息
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [bdcSlSqrQO] 受理申请人查询QO
     * @return: List<BdcSlSqrDO> 受理申请人DO集合
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "通过不动产受理申请人QO查询申请人信息", notes = "通过不动产受理申请人QO查询申请人信息")
    public List<BdcSlSqrDO> listBdcSlSqrByBdcSlSqrQO(@RequestBody BdcSlSqrQO bdcSlSqrQO) {
        if(null == bdcSlSqrQO){
            throw new MissingArgumentException("BdcSlSqrQO");
        }
        return bdcSlSqrDOService.queryBdcSlSqrBySqrQO(bdcSlSqrQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据家庭分组获取申请人信息", notes = "根据家庭分组获取申请人信息")
    @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")
    public YcslYmxxDTO getSqrxxGroupByJt(@PathVariable(value = "xmid")String xmid){
        return bdcSlSqrDOService.getSqrxxGroupByJt(xmid);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据家庭分组获取申请人信息", notes = "根据家庭分组获取申请人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "sqrlb", value = "申请人类别", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sfhb", value = "是否合并", required = false, dataType = "String", paramType = "query"),
    })
    public List<List<BdcSlJtcyGroupDTO>> getSqrxxGroupByJtWithSqrlb(@PathVariable(value = "xmid")String xmid, @RequestParam(name = "sqrlb", required = false)String sqrlb, @RequestParam(name = "sfhb", required = false)Boolean sfhb){
        return bdcSlSqrDOService.getSqrxxGroupByJtWithSqrlb(xmid, sqrlb, sfhb);
    }

    @ApiOperation(value = "批量更新申请人信息(组合流程同步）")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bdcSlSqrDO", value = "申请人对象", dataType = "BdcQlrDO", paramType = "body"),
            @ApiImplicitParam(name = "qllx", value = "权利类型", dataType = "String", paramType = "query")})
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public List<BdcSlSqrDO> saveBatchBdcSlSqrWithZh(@RequestBody String json, @RequestParam("gzlslid") String gzlslid, @RequestParam(value = "qllx", required = false) String qllx, @RequestParam(value = "djxl", required = false) String djxl) {
        return bdcSlSqrDOService.saveBatchBdcSlSqrWithZh(json, gzlslid, qllx, djxl);
    }

    @ApiOperation(value = "批量删除申请人(组合流程同步）")
    @ApiImplicitParams({@ApiImplicitParam(name = "sqrid", value = "申请人ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qllx", value = "权利类型", dataType = "String", paramType = "query")})
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Override
    public void deleteBdcSqrsBySqrxxWithZh(@RequestParam("sqrid") String sqrid, @RequestParam("gzlslid") String gzlslid, @RequestParam(value = "qllx", required = false) String qllx, @RequestParam(value = "djxl") String djxl) {
        bdcSlSqrDOService.deleteBdcSqrsBySqrxxWithZh(sqrid, gzlslid, qllx, djxl);

    }

    @ApiOperation(value = "同步一窗申请人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcQlrDOList", value = "权利人信息集合", dataType = "list", paramType = "body"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "操作类型", dataType = "String", paramType = "query")})
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Override
    public void syncSqrxx(@RequestBody List<BdcQlrDO> bdcQlrDOList, @RequestParam("gzlslid") String gzlslid, @RequestParam("type") String type) {
        bdcSlSqrDOService.syncSqrxx(bdcQlrDOList, gzlslid, type);
    }
}
