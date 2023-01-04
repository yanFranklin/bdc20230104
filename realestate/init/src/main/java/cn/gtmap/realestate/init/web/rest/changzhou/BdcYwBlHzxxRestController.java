package cn.gtmap.realestate.init.web.rest.changzhou;

import cn.gtmap.realestate.common.core.domain.BdcYwblhzxxDO;
import cn.gtmap.realestate.common.core.service.rest.init.changzhou.BdcYwblHzxxRestService;
import cn.gtmap.realestate.init.core.service.BdcYwblHzxxService;
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
 * @description: 业务办理核证信息restcontroller
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-07-21 09:45
 **/
@RestController
@Api(tags = "业务办理核证信息rest服务")
public class BdcYwBlHzxxRestController extends BaseController implements BdcYwblHzxxRestService {

    @Autowired
    BdcYwblHzxxService bdcYwblHzxxService;

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询业务办理核证信息
     * @date : 2021/7/21 9:52
     */
    @ApiOperation(value = "查询业务办理核证信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例id", dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcYwblhzxxDO> listYwblHzxx(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcYwblHzxxService.listYwblHzxx(gzlslid);
    }

    /**
     * @param bdcYwblhzxxDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增业务办理核证信息
     * @date : 2021/7/21 9:54
     */
    @ApiOperation(value = "新增业务办理核证信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcYwblhzxxDO", value = "不动产业务办理核证信息对象", dataType = "BdcYwblhzxxDO", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public BdcYwblhzxxDO insertYwblHzxx(@RequestBody BdcYwblhzxxDO bdcYwblhzxxDO) {
        return bdcYwblHzxxService.insertYwblHzxx(bdcYwblhzxxDO);
    }
}
