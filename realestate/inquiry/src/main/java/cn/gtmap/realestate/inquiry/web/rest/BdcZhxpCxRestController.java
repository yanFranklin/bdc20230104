package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcZhxpCxRestService;
import cn.gtmap.realestate.inquiry.service.BdcZhxpCxService;
import cn.gtmap.realestate.inquiry.web.main.BaseController;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/7/4
 * @description 综合小屏-中心不同业务的排号情况
 */
@RestController
@Api(tags = "综合小屏-中心不同业务的排号情况查询")
public class BdcZhxpCxRestController extends BaseController implements BdcZhxpCxRestService{
    @Autowired
    private BdcZhxpCxService bdcZhxpCxService;

    /**
     * @return List 当前中心呼叫信息查询
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 综合小屏当前呼叫信息查询
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "综合小屏当前呼叫信息查询", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "zxmc", value = "当前中心名称", required = true, dataType = "String")})
    public List<Map> listBdcZhxpDqhj(@PathVariable("zxmc")String zxmc) {
        return bdcZhxpCxService.listBdcZhxpDqhj(zxmc);
    }

    /**
     * @return List 当前中心等待呼叫信息
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 综合小屏等待呼叫信息查询
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "综合小屏等待呼叫信息查询", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "zxmc", value = "当前中心名称", required = true, dataType = "String")})
    public List<Map> listBdcZhxpDdhj(@PathVariable("zxmc")String zxmc) {
        return bdcZhxpCxService.listBdcZhxpDdhj(zxmc);
    }
}
