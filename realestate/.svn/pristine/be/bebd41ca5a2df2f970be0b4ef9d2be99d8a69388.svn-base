package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlHsxxMxService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxMxDO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlHsxxMxRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/27
 * @description 不动产受理核税信息明细rest服务
 */
@RestController
@Api(tags = "不动产受理核税信息明细服务")
public class BdcSlHsxxMxRestController extends BaseController implements BdcSlHsxxMxRestService {

    @Autowired
    BdcSlHsxxMxService bdcSlHsxxMxService;

    /**
     * @param json 集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  批量更新受理核税信息明细
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "批量更新受理核税信息明细", notes = "批量更新受理核税信息明细服务")
    @ApiImplicitParam(name = "json", value = "批量更新受理核税信息明细", required = true, dataType = "String")
    public int batchUpdateBdcSlHsxxMx(@RequestBody String json){
        return bdcSlHsxxMxService.batchUpdateBdcSlHsxxMx(json);

    }


    /**
     * @param hsxxid 核税信息ID
     * @return 受理核税信息明细
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据核税信息ID查询受理核税信息明细
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据核税信息ID查询受理核税信息明细", notes = "根据核税信息ID查询受理核税信息明细服务")
    @ApiImplicitParam(name = "核税信息ID", value = "hsxxid", required = true, dataType = "String")
    public List<BdcSlHsxxMxDO> bdcSlHsxxMx(@PathVariable("hsxxid") String hsxxid){
        return bdcSlHsxxMxService.listBdcSlHsxxMxByHsxxid(hsxxid);

    }

}
