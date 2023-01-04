package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlYwlzService;
import cn.gtmap.realestate.accept.service.BdcSjclService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlYwlzDO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlYwlzRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: realestate
 * @description: 受理业务流转restController层
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-09-22 11:15
 **/
@Api(tags = "受理业务流转restController")
@RestController
public class BdcSlYwlzRestController extends BaseController implements BdcSlYwlzRestService {

    @Autowired
    BdcSlYwlzService bdcSlYwlzService;
    @Autowired
    BdcSjclService bdcSjclService;

    /**
     * @param bdcSlYwlzDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询业务流转信息
     * @date : 2021/9/22 11:25
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询业务流转信息", notes = "查询业务流转信息服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSlYwlzDO", value = "bdcSlYwlzDO", dataType = "BdcSlYwlzDO", paramType = "body")})
    public List<BdcSlYwlzDO> listBdcSlYwlz(@RequestBody BdcSlYwlzDO bdcSlYwlzDO) {
        return bdcSlYwlzService.listBdcSlYwlz(bdcSlYwlzDO);
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除业务流转信息
     * @date : 2021/9/22 11:31
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = " 根据工作流实例id删除推送缴费流程数据", notes = " 根据工作流实例id删除推送缴费流程数据服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "gzlslid", required = true, dataType = "string", paramType = "path")})
    public int deleteYwlz(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcSlYwlzService.delteBdcSlYwlz(gzlslid);
    }

    /**
     * @param porcessInsId
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 继承收件材料
     * @date : 2021/9/22 14:56
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = " 继承收件材料", notes = "继承收件材料服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "porcessInsId", required = true, dataType = "string", paramType = "query")})
    public void extendSjcl(@RequestParam(value = "processInsId") String porcessInsId) {
        bdcSjclService.extendSjcl(porcessInsId);
    }
}
