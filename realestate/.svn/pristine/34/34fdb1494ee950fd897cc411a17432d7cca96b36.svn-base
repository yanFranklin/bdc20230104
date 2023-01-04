package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.service.BdcBgxxDbService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcBgxxDbRestService;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcBgxxDbVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/10/21.
 * @description 变更信息对比Rest接口服务
 */
@RestController
@Api(tags = "变更信息对比Rest接口服务")
public class BdcBgxxDbRestController extends BaseController implements BdcBgxxDbRestService {

    @Autowired
    BdcBgxxDbService bdcBgxxDbService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "添加业务信息至es中", notes = "添加业务信息至es中")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")
    })
    public void addYwxxToEs(@RequestParam(name="gzlslid")String gzlslid) {
        bdcBgxxDbService.addBdcYwxxToEs(gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取不动产业务信息", notes = "获取不动产业务信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "xmid", value = "项目ID", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")
    })
    public List<BdcBgxxDbVO> getBdcYwxx(@RequestParam(name="xmid", required = false)String xmid,
                                        @RequestParam(name="gzlslid")String gzlslid) {
        return bdcBgxxDbService.getBdcYwxx(xmid, gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "对比变更前与变更后的业务信息", notes = "对比变更前与变更后的业务信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")
    })
    public Object compareYwxx(@RequestParam(name="gzlslid")String gzlslid) {
        return bdcBgxxDbService.compareYwxx(gzlslid);
    }

}
