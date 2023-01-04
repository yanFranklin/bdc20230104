package cn.gtmap.realestate.init.web.rest.changzhou;

import cn.gtmap.realestate.common.core.domain.BdcFwjsztckDO;
import cn.gtmap.realestate.common.core.service.rest.init.changzhou.BdcFwJsztCkRestService;
import cn.gtmap.realestate.init.core.service.BdcFwjsztCkService;
import cn.gtmap.realestate.init.web.BaseController;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: realestate
 * @description: 房屋建设状态查看restcontroller
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-07-20 13:51
 **/
@RestController
@Api(tags = "房屋建设状态查看rest服务")
public class BdcFwJsztCkRestController extends BaseController implements BdcFwJsztCkRestService {
    @Autowired
    BdcFwjsztCkService bdcFwjsztCkService;

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据工作流实例id查询房屋建设状态
     * @date : 2021/7/20 13:55
     */
    @ApiOperation(value = "根据工作流实例id查询房屋建设状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例id", dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcFwjsztckDO> queryFwjszt(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcFwjsztCkService.queryBdcFwjszt(gzlslid);
    }

    /**
     * @param bdcFwjsztckDO@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增房屋建设状态查看信息
     * @date : 2021/7/20 14:07
     */
    @ApiOperation(value = "新增房屋建设状态查看信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcFwjsztckDO", value = "不动产房屋建设状态查看对象", dataType = "BdcFwjsztckDO", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public BdcFwjsztckDO insertFwjsztCk(@RequestBody BdcFwjsztckDO bdcFwjsztckDO) {
        return bdcFwjsztCkService.insertFwjsztCk(bdcFwjsztckDO);
    }
}
