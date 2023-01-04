package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlFpxxService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlFpxxDO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlFpxxRestService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/5/11
 * @description 不动产受理发票信息RestController服务
 */
@RestController
@Api(tags = "不动产审核信息服务接口")
public class BdcSlFpxxRestController extends BaseController implements BdcSlFpxxRestService {

    @Autowired
    BdcSlFpxxService bdcSlFpxxService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据收费信息ID获取不动产受理发票信息", notes = "根据收费信息ID获取不动产受理发票信息")
    @ApiImplicitParam(name = "sfxxid", value = "收费信息ID", required = true, dataType = "String", paramType = "path")
    public List<BdcSlFpxxDO> queryBdcSlFpxxBySfxxid(@PathVariable(value = "sfxxid") String sfxxid) {
        return bdcSlFpxxService.queryBdcSlFpxxBySfxxid(sfxxid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取发票信息，并上传发票信息", notes = "获取发票信息，并上传发票信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sfxxid", value = "收费信息ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")
    })
    public void getFpxxAndUploadFpxx(@RequestParam(value = "sfxxid") String sfxxid, @RequestParam(value="gzlslid") String gzlslid) {
        bdcSlFpxxService.getFpxxAndUploadFpxx(sfxxid, gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "开具发票", notes = "开具发票")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sfxxid", value = "收费信息ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qlrlb", value = "权利人类别", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "query"),
    })
    public void inovice(@RequestParam(value = "sfxxid") String sfxxid, @RequestParam(value = "qlrlb") String qlrlb, @RequestParam(value="gzlslid") String gzlslid) {
        bdcSlFpxxService.inovice(sfxxid, qlrlb, gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "开具发票", notes = "开具发票")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sfxxid", value = "收费信息ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userCode", value = "用户code", required = false, dataType = "String", paramType = "query"),
    })
    public String queryDzph(@RequestParam(value = "sfxxid") String sfxxid, @RequestParam(value = "userCode") String userCode) {
        return this.bdcSlFpxxService.queryDzph(sfxxid, userCode);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询缴款情况成功，并自动执行获取发票号、开具发票信息功能", notes = "查询缴款情况成功，并自动执行获取发票号、开具发票信息功能")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")
    })
    public void queryJkqkAndAutoExec(@RequestParam(value= "processInsId") String processInsId, @RequestParam(name = "currentUserName") String currentUserName) {
        this.bdcSlFpxxService.queryJkqkAndAutoExec(processInsId, currentUserName);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "保存不动产受理发票信息", notes = "保存不动产受理发票信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlFpxxDO", value = "不动产受理发票信息", required = true, dataType = "BdcSlFpxxDO", paramType = "body")
    })
    public void saveBdcSlFpxx(@RequestBody BdcSlFpxxDO bdcSlFpxxDO) {
        this.bdcSlFpxxService.saveOrUpdateBdcSlFpxx(bdcSlFpxxDO);
    }
}
