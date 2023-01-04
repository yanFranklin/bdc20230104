package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlTdcrjService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlTdcrjDO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlTdcrjRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/8
 * @description
 */
@RestController
@Api(tags = "不动产受理土地出让金服务")
public class BdcSlTdcrjRestController implements BdcSlTdcrjRestService {

    @Autowired
    private BdcSlTdcrjService bdcSlTdcrjService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据项目ID获取出让金信息", notes = "根据项目ID获取出让金信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")
    })
    public List<BdcSlTdcrjDO> listBdcSlTdcrjByXmid(@PathVariable(value = "xmid")String xmid){
        return bdcSlTdcrjService.listBdcSlTdcrjByXmid(xmid);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "新增土地出让金信息", notes = "新增土地出让金信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlTdcrjDO", value = "土地出让金信息", required = true, dataType = "BdcSlTdcrjDO", paramType = "body")
    })
    public void insertBdcSlTdcrj(@RequestBody BdcSlTdcrjDO bdcSlTdcrjDO){
        bdcSlTdcrjService.insertBdcSlTdcrj(bdcSlTdcrjDO);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "删除土地出让金信息", notes = "删除土地出让金信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tdcrjid", value = "土地出让金ID", required = true, dataType = "String")
    })
    public void deleteBdcSlTdcrj(@RequestParam(name = "tdcrjid") String tdcrjid){
        bdcSlTdcrjService.deleteBdcSlTdcrj(tdcrjid);
    }
}
