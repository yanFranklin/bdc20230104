package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlXmLsgxService;
import cn.gtmap.realestate.accept.service.BdcWlzsService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlXmLsgxRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/5
 * @description 不动产受理项目历史关系rest服务
 */
@RestController
@Api(tags = "不动产受理项目历史关系服务")
public class BdcSlXmLsgxRestController extends BaseController implements BdcSlXmLsgxRestService {
    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 不动产受理项目历史关系数据服务
     */
    @Autowired
    private BdcSlXmLsgxService bdcSlXmLsgxService;
    @Autowired
    BdcWlzsService bdcWlzsService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据关系ID获取不动产受理项目历史关系", notes = "根据项目ID获取不动产受理项目历史关系服务")
    @ApiImplicitParam(name = "gxid", value = "关系ID", required = true, dataType = "String", paramType = "path")
    public BdcSlXmLsgxDO queryBdcSlXmLsgxByGxid(@PathVariable(value = "gxid") String gxid) {
        return bdcSlXmLsgxService.queryBdcSlXmLsgxByGxid(gxid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据项目ID获取不动产受理项目历史关系", notes = "根据项目ID获取不动产受理项目历史关系服务")
    @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")
    public List<BdcSlXmLsgxDO> listBdcSlXmLsgxByXmid(@PathVariable(value = "xmid") String xmid) {
        return bdcSlXmLsgxService.listBdcSlXmLsgxByXmid(xmid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据项目ID或者原项目id获取不动产受理项目历史关系", notes = "根据项目ID或者原项目id获取不动产受理项目历史关系")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID",  dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "yxmid", value = "原项目ID", dataType = "String", paramType = "path")
    })
    public List<BdcSlXmLsgxDO> listBdcSlXmLsgx(@RequestParam(value = "xmid",required = false) String xmid, @RequestParam(value = "yxmid",required = false) String yxmid,@RequestParam(value = "sfwlzs",required = false) Integer sfwlzs) {
        return bdcSlXmLsgxService.listBdcSlXmLsgx(xmid,yxmid,sfwlzs);
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "新增不动产受理项目历史关系", notes = "新增不动产受理项目历史关系服务服务")
    @ApiImplicitParam(name = "bdcSlXmLsgxDO", value = "新增不动产受理项目历史关系", required = true, dataType = "BdcSlXmLsgxDO")
    public BdcSlXmLsgxDO insertBdcSlXmLsgx(@RequestBody BdcSlXmLsgxDO bdcSlXmLsgxDO) {
        return bdcSlXmLsgxService.insertBdcSlXmLsgx(bdcSlXmLsgxDO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新不动产受理项目历史关系", notes = "更新不动产受理项目历史关系服务")
    @ApiImplicitParam(name = "bdcSlXmLsgxDO", value = "新增不动产受理项目历史关系", required = true, dataType = "BdcSlXmLsgxDO")
    public Integer updateBdcSlXmLsgx(@RequestBody BdcSlXmLsgxDO bdcSlXmLsgxDO) {
        return bdcSlXmLsgxService.updateBdcSlXmLsgx(bdcSlXmLsgxDO);
    }


    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据项目ID删除不动产受理项目历史关系", notes = "根据项目ID删除不动产受理项目历史关系服务")
    @ApiImplicitParam(name = "gxid", value = "关系ID", required = true, dataType = "String", paramType = "path")
    public Integer deleteBdcSlXmLsgxByGxid(@PathVariable(value = "gxid") String gxid) {
        return bdcSlXmLsgxService.deleteBdcSlXmLsgxByGxid(gxid);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据项目ID删除不动产受理项目历史关系", notes = "根据项目ID删除不动产受理项目历史关系服务")
    @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")
    public Integer deleteBdcSlXmLsgxByXmid(@PathVariable(value = "xmid") String xmid) {
        return bdcSlXmLsgxService.deleteBdcSlXmLsgxByXmid(xmid);
    }

    /**
     * @param jbxxid 基本信息ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据基本信息ID删除不动产受理项目历史关系
     */
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据基本信息ID删除不动产受理项目历史关系", notes = "根据基本信息ID删除不动产受理项目历史关系服务")
    @ApiImplicitParam(name = "jbxxid", value = "基本信息ID", required = true, dataType = "String", paramType = "path")
    public void deleteBdcSlXmLsgxByJbxxid(@PathVariable(value = "jbxxid") String jbxxid) {
        bdcSlXmLsgxService.deleteBdcSlXmLsgx(jbxxid,null);
    }

    /**
     * @param fcxmid    项目ID
     * @param tdxmid
     * @param bdcSlXmDO @return 不动产受理项目历史关系
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据项目ID新增不动产受理项目历史关系
     */
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "根据项目ID新增不动产受理项目历史关系", notes = "根据项目ID新增不动产受理项目历史关系服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fcxmid", value = "房产xmid", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tdxmid", value = "土地xmid", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bdcSlXmDO", value = "不动产受理项目", required = true, dataType = "BdcSlXmDO")
    })
    public BdcSlXmLsgxDO insertBdcSlXmLsgxByTdz(@RequestParam String fcxmid, @RequestParam String tdxmid, @RequestBody BdcSlXmDO bdcSlXmDO) {
        return bdcWlzsService.wltdz(fcxmid,bdcSlXmDO.getYbdcqz(),bdcSlXmDO);
    }

    /**
     * @param gzlslid
     * @return 不动产受理项目上下手关系
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description  根据gzlslid获取不动产受理上下手关系数据
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据gzlslid获取不动产受理上下手关系数据", notes = "根据gzlslid获取不动产受理上下手关系数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "gzlslid", required = true, dataType = "String", paramType = "query"),
    })
    public List<Map> sxxBdcXx(@RequestParam(value = "gzlslid",required = true) String gzlslid){
        return bdcSlXmLsgxService.sxxBdcXx(gzlslid);
    }

}
