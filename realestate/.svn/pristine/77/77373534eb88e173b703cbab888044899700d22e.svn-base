package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.service.BdcQllxService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcQllxRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/3/27
 * @description 不动产权利类型rest服务
 */
@RestController
@Api(tags = "不动产权利类型rest服务")
public class BdcQllxRestController extends BaseController implements BdcQllxRestService {
    @Autowired
    BdcQllxService bdcQllxService;

    /**
     * @param bdcdyh 不动产单元号
     * @param dyhcxlx 单元号查询类型,1-土地及其定着物,2-海域及其定着物,3-构筑物
     * @return 权利类型
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据不动产单元获取权利类型
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据不动产单元获取权利类型", notes = "根据不动产单元获取权利类型服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "dyhcxlx", value = "单元号查询类型", required = true, dataType = "String", paramType = "query")
    })
    public Integer getQllxByBdcdyh(@RequestParam(value = "bdcdyh") String bdcdyh,@RequestParam(value = "dyhcxlx") String dyhcxlx) {
        return bdcQllxService.getQllxByBdcdyh(bdcdyh, dyhcxlx);
    }
}
