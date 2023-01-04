package cn.gtmap.realestate.init.web.rest.changzhou;

import cn.gtmap.realestate.common.core.domain.BdcYwblBahdDO;
import cn.gtmap.realestate.common.core.service.rest.init.changzhou.BdcYwblBahdRestService;
import cn.gtmap.realestate.init.core.service.BdcYwblBahdService;
import cn.gtmap.realestate.init.web.BaseController;
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
 * @description: 不动产业务办理备案核定RestController
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-07-21 10:20
 **/
@RestController
@Api(tags = "业务办理备案核定rest服务")
public class BdcYwblBahdRestController extends BaseController implements BdcYwblBahdRestService {

    @Autowired
    BdcYwblBahdService bdcYwblBahdService;

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询业务办理备案核定数据
     * @date : 2021/7/21 10:11
     */
    @ApiOperation(value = "查询业务办理备案核定数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例id", dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcYwblBahdDO> listYwblBahd(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcYwblBahdService.listYwblBahd(gzlslid);
    }

    /**
     * @param bdcYwblBahdDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增业务办理备案核定数据
     * @date : 2021/7/21 10:18
     */
    @ApiOperation(value = "新增业务办理备案核定数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcYwblBahdDO", value = "不动产业务办理核证信息对象", dataType = "BdcYwblBahdDO", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public BdcYwblBahdDO insertYwblBahd(@RequestBody BdcYwblBahdDO bdcYwblBahdDO) {
        return bdcYwblBahdService.insertYwblBahd(bdcYwblBahdDO);
    }
}
