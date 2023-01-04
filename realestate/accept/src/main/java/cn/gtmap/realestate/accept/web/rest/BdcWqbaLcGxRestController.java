package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcWqbaLcGxService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcWqbaLcGxDO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcWqbaLcGxRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: realestate
 * @description: 网签备案流程关系服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-03-16 09:31
 **/
@RestController
@Api(tags = "不动产税务信息服务")
public class BdcWqbaLcGxRestController extends BaseController implements BdcWqbaLcGxRestService {
    @Autowired
    BdcWqbaLcGxService bdcWqbaLcGxService;

    /**
     * @param bdcWqbaLcGxDO 查询条件
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description //查询网签备案
     * @date : 2021/3/16 9:53
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "通过查询条件查询网签备案河流程关系信息", notes = "通过查询条件查询网签备案河流程关系信息")
    public BdcWqbaLcGxDO queryWqbaLcGx(@RequestBody BdcWqbaLcGxDO bdcWqbaLcGxDO) {
        return bdcWqbaLcGxService.queryWqbaLcGxDO(bdcWqbaLcGxDO);
    }

    /**
     * @param bdcWqbaLcGxDO 查询条件
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description //新增网签备案信息
     * @date : 2021/3/16 9:53
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "通过查询条件查询网签备案河流程关系信息", notes = "通过查询条件查询网签备案河流程关系信息")
    public BdcWqbaLcGxDO insertWqbaLcGx(@RequestBody BdcWqbaLcGxDO bdcWqbaLcGxDO) {
        return bdcWqbaLcGxService.insertWqbaLcGxDO(bdcWqbaLcGxDO);
    }

    /**
     * @param bdcWqbaLcGxDO 查询条件
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description //更新网签备案关系
     * @date : 2021/3/16 9:53
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "通过查询条件查询网签备案河流程关系信息", notes = "通过查询条件查询网签备案河流程关系信息")
    public BdcWqbaLcGxDO updateWqbaLcGx(@RequestBody BdcWqbaLcGxDO bdcWqbaLcGxDO) {
        return bdcWqbaLcGxService.updateWqbaLcGxDO(bdcWqbaLcGxDO);
    }
}
