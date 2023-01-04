package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlLzrService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlLzrDO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlLzrRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/1/15
 * @description 不动产受理领证人rest服务
 */
@RestController
@Api(tags = "不动产受理领证人服务")
public class BdcSlLzrRestController extends BaseController implements BdcSlLzrRestService {

    @Autowired
    private BdcSlLzrService bdcSlLzrService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据领证人ID获取不动产受理领证人", notes = "根据领证人ID获取不动产受理领证人服务")
    @ApiImplicitParam(name = "lzrid", value = "领证人ID", required = true, dataType = "String", paramType = "path")
    public BdcSlLzrDO queryBdcSlLzrByLzrid(@PathVariable(value = "lzrid") String lzrid) {
        return bdcSlLzrService.queryBdcSlLzrByLzrid(lzrid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据项目ID获取不动产受理领证人", notes = "根据项目ID获取不动产受理领证人服务")
    @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")
    public List<BdcSlLzrDO> listBdcSlLzrByXmid(@PathVariable(value = "xmid") String xmid) {
        return bdcSlLzrService.listBdcSlLzrByXmid(xmid);
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "新增不动产受理领证人", notes = "新增不动产受理领证人服务服务")
    @ApiImplicitParam(name = "bdcSlLzrDO", value = "新增不动产受理领证人", required = true, dataType = "BdcSlLzrDO")
    public BdcSlLzrDO insertBdcSlLzr(@RequestBody BdcSlLzrDO bdcSlLzrDO) {
        return bdcSlLzrService.insertBdcSlLzr(bdcSlLzrDO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新不动产受理领证人", notes = "更新不动产受理领证人服务")
    @ApiImplicitParam(name = "bdcSlLzrDO", value = "新增不动产受理领证人", required = true, dataType = "BdcSlLzrDO")
    public Integer updateBdcSlLzr(@RequestBody BdcSlLzrDO bdcSlLzrDO) {
        return bdcSlLzrService.updateBdcSlLzr(bdcSlLzrDO);
    }


    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据领证人ID删除不动产受理领证人", notes = "根据领证人ID删除不动产受理领证人服务")
    @ApiImplicitParam(name = "lzrid", value = "领证人ID", required = true, dataType = "String", paramType = "path")
    public Integer deleteBdcSlLzrByLzrid(@PathVariable(value = "lzrid") String lzrid) {
        return bdcSlLzrService.deleteBdcSlLzrByLzrid(lzrid);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据项目ID删除不动产受理领证人", notes = "根据项目ID删除不动产受理领证人服务")
    @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")
    public Integer deleteBdcSlLzrByXmid(@PathVariable(value = "xmid") String xmid) {
        return bdcSlLzrService.deleteBdcSlLzrByXmid(xmid);
    }

    /**
     * 批量修改领证人（用于批量页面）
     *
     * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @param: json 领证人JSON
     * @param: processInsId 流程实例ID
     * @param: xmid 项目ID
     * @return: Integer 修改数量
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "批量修改权利人（用于批量页面）", notes = "批量修改权利人（用于批量页面）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "领证人JSON", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "processInsId", value = "流程实例ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "query")
    })
    public Integer updatePlBdcLzr(@RequestBody String json, @RequestParam("processInsId") String processInsId,
                                  @RequestParam("djxl") String djxl) throws Exception {
        return bdcSlLzrService.updatePlBdcLzr(json, processInsId, djxl);
    }

}
