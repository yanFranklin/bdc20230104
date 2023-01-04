package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.service.BdcSlQlrService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.dto.accept.SfmLiscenseInfoDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.csjzzxx.sfm.SfmResponse;
import cn.gtmap.realestate.common.core.qo.accept.CsjZzxxQO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlQlrRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/1/13.
 * @description 不动产权利人RESR接口服务，提取accept中处理权利人信息通用接口服务供外部接口调用
 */
@RestController
@Api(tags = "不动产权利人RESR接口服务")
public class BdcSlQlrRestController extends BaseController implements BdcSlQlrRestService {

    @Autowired
    BdcSlQlrService bdcQlrService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "批量修改权利人（用于批量页面）", notes = "批量修改权利人（用于批量页面）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "权利人集合JSON", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "processInsId", value = "流程实例ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "query")
    })
    public Integer updatePlBdcQlr(@RequestBody String json,
                                  @RequestParam("processInsId") String processInsId,
                                  String xmid) throws Exception{
      return  this.bdcQlrService.updatePlBdcQlr(json, processInsId, xmid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "批量修改权利人(用于批量组合页面)", notes = "批量修改权利人(用于批量组合页面)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "权利人集合JSON", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "processInsId", value = "流程实例ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "djxl", value = "登记小类", required = true, dataType = "String", paramType = "query")
    })
    public List<BdcQlrDO> updatePlzhBdcQlr(@RequestBody String json,
                                           @RequestParam("processInsId") String processInsId,
                                           @RequestParam("xmid") String xmid,
                                           @RequestParam("djxl") String djxl) throws Exception{
        return this.bdcQlrService.updatePlzhBdcQlr(json, processInsId, xmid, djxl);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "修改权利人共有方式接口", notes = "修改权利人共有方式接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcQlrList", value = "权利人集合", required = true, dataType = "BdcQlrDO", paramType = "query"),
            @ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "lclx", value = "流程类型", required = true, dataType = "String", paramType = "query")
    })
    public List<BdcQlrDO> updateBdcQlrGyfs(@RequestBody List<BdcQlrDO> bdcQlrList,
                                             @RequestParam("processInsId") String processInsId,
                                             @RequestParam("xmid") String xmid,
                                             @RequestParam("lclx") String lclx) throws Exception {
        return this.bdcQlrService.modifyBdcQlrGyfs(bdcQlrList, processInsId, xmid, lclx);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "批量新增权利人(用于批量组合页面)", notes = "批量新增权利人(用于批量组合页面)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "权利人集合JSON", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "processInsId", value = "流程实例ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "djxl", value = "登记小类", required = true, dataType = "String", paramType = "query")
    })
    public BdcQlrDO insertPlzhBdcQlr(@RequestBody String json,
                              @RequestParam("processInsId") String processInsId,
                              @RequestParam("djxl") String djxl,@RequestParam("syncTdsyqr") boolean syncTdsyqr){
        return bdcQlrService.insertPlzhBdcQlr(json,processInsId,djxl,syncTdsyqr);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "批量删除权利人(批量组合)", notes = "批量删除权利人(批量组合)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInsId", value = "流程实例ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qlrid", value = "权利人ID", required = true, dataType = "String", paramType = "query")
    })
    public void deletePlzhBdcQlr(@RequestParam("qlrid") String qlrid,
                          @RequestParam("processInsId") String processInsId) throws Exception{
        bdcQlrService.deletePlzhBdcQlr(qlrid,processInsId);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "新增、修改、删除权利人时，同步土地使用权人", notes = "新增、修改、删除权利人时，同步土地使用权人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "权利人集合JSON", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "processInsId", value = "流程实例ID", required = true, dataType = "String", paramType = "query"),
    })
    public void syncTdsyqr(@RequestBody String json, @RequestParam(value = "processInsId") String processInsId) {
        this.bdcQlrService.syncTdsyqr(json, processInsId);
    }

    /**
     * @param yyzzm 营业执照码
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 南通页面输入营业执照调用接口查询权利人信息和附件信息
     * @date : 2021/5/11 10:53
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "南通页面输入营业执照调用接口查询权利人信息和附件信息", notes = "南通页面输入营业执照调用接口查询权利人信息和附件信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "yyzzm", value = "营业执照码", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "gzlslid", value = "营业执照码", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "djxl", value = "营业执照码", required = false, dataType = "String", paramType = "query")
    })
    public void importYyzz(@PathVariable(value = "yyzzm") String yyzzm, @PathVariable(name = "gzlslid") String gzlslid, @RequestParam(name = "djxl") String djxl) throws IOException {
        bdcQlrService.importYyzz(yyzzm, gzlslid, djxl);
    }

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询长三角证照信息
     * @date : 2022/5/11 9:45
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    public String querCsjZzxx(@RequestBody CsjZzxxQO csjZzxxQO) {
        return bdcQlrService.querCsjZzxx(csjZzxxQO);
    }

    /**
     * @param csjZzxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 扫描苏服码并查询相关信息
     * @date : 2022/5/12 8:34
     */
    @Override
    public String querCsjZzxxBySfm(@RequestBody CsjZzxxQO csjZzxxQO) {
        return bdcQlrService.queryCsjZzxxBySfm(csjZzxxQO);
    }

    /**
     * @param csjZzxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 扫描苏服码并查询相关信息
     * @date : 2022/5/12 8:34
     */
    @Override
    public List<SfmLiscenseInfoDTO> querCsjZzxxBySfmCx(@RequestBody CsjZzxxQO csjZzxxQO) {
        return bdcQlrService.queryCsjZzxxBySfmCx(csjZzxxQO);
    }

    /**
     * @param csjZzxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 扫描苏服码添加权利人
     * @date : 2022/5/12 8:34
     */
    @Override
    public void querCsjZzxxBySfmAdd(@RequestBody CsjZzxxQO csjZzxxQO) {
        bdcQlrService.queryCsjZzxxBySfmAdd(csjZzxxQO);
    }

    /**
     * @param csjZzxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 扫描苏服码添加附件
     * @date : 2022/5/12 8:34
     */
    @Override
    public void querCsjZzxxBySfmAddFj(@RequestBody CsjZzxxQO csjZzxxQO) {
        bdcQlrService.queryCsjZzxxBySfmAddFj(csjZzxxQO);
    }
}
