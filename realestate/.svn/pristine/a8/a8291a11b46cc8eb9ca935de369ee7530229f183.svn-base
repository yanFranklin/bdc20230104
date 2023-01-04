package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcLcTsjfGxService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcLcTsjfGxDO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcLcTsjfRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: realestate
 * @description: 流程与推送缴费关系restController
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-09-16 13:54
 **/
@RestController
@Api(tags = "流程与推送缴费关系服务")
public class BdcLcTsjfGxRestController extends BaseController implements BdcLcTsjfRestService {
    @Autowired
    BdcLcTsjfGxService bdcLcTsjfGxService;

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据工作流实例id查询推送缴费流程数据
     * @date : 2021/9/16 13:50
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = " 根据工作流实例id查询推送缴费流程数据", notes = " 根据工作流实例id查询推送缴费流程数据服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "gzlslid", required = true, dataType = "string", paramType = "path")})
    public List<BdcLcTsjfGxDO> listLcTsjfGx(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcLcTsjfGxService.listLcTsjfGx(gzlslid);
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据工作流实例id删除推送缴费流程数据
     * @date : 2021/9/16 13:52
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = " 根据工作流实例id删除推送缴费流程数据", notes = " 根据工作流实例id删除推送缴费流程数据服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "gzlslid", required = true, dataType = "string", paramType = "path")})
    public int deleteLcTsJfxx(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcLcTsjfGxService.deleteLcTsjfGx(gzlslid);
    }

    /**
     * @param bdcLcTsjfGxDOList@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量新增流程推送缴费关系
     * @date : 2021/9/16 14:05
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = " 批量新增流程推送缴费关系", notes = "批量新增流程推送缴费关系服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcLcTsjfGxDOList", value = "bdcLcTsjfGxDOList", dataType = "BdcLcTsjfGxDO", paramType = "body")})
    public int batchInsertLcTsjfGx(@RequestBody List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList) {
        return bdcLcTsjfGxService.batchInsertLcTsjfGx(bdcLcTsjfGxDOList);
    }

    /**
     * @param sfxxidList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据收费信息id批量删除
     * @date : 2021/10/26 16:19
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = " 根据收费信息id批量删除", notes = "根据收费信息id批量删除服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sfxxidList", value = "sfxxidList", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "gzlslid", value = "gzlslid", dataType = "String", paramType = "path")
    })
    public int deleteLcTsJfxxBySfxxid(@RequestBody List<String> sfxxidList, @PathVariable(value="gzlslid") String gzlslid) {
        return bdcLcTsjfGxService.deleteLcTsGxBySfxxid(sfxxidList, gzlslid);
    }

    /**
     * @param bdcLcTsjfGxDOList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量更新流程推送缴费关系的信息
     * @date : 2021/11/3 14:31
     */
    @Override
    public void batchUpdateLcTsjfGx(@RequestBody List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList) {
        bdcLcTsjfGxService.batchUpdateLcTsjfGx(bdcLcTsjfGxDOList);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = " 清除推送ID", notes = "清除推送ID")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "gzlslid", dataType = "String", paramType = "path")
    })
    public void clearTsid(@PathVariable(value="gzlslid") String gzlslid) {
        bdcLcTsjfGxService.clearTsid(gzlslid);
    }
}
