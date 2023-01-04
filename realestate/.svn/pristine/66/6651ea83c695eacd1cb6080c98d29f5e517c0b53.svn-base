package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.core.service.ZhQlrService;
import cn.gtmap.realestate.building.core.service.ZhService;
import cn.gtmap.realestate.building.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.building.ZhDjdcbDO;
import cn.gtmap.realestate.common.core.domain.building.ZhQlrDO;
import cn.gtmap.realestate.common.core.service.rest.building.ZhRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/14
 * @description 宗海相关服务
 */
@RestController
@Api(tags = "宗海服务接口")
public class ZhRestController extends BaseController implements ZhRestService {
    @Autowired
    private ZhService zhService;

    @Autowired
    private ZhQlrService zhQlrService;

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.BdcDjsjDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询宗海地籍数据
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询宗海基本信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public ZhDjdcbDO queryZhByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return zhService.queryZhDjdcbByBdcdyh(bdcdyh);
    }
    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.ZhQlrDo>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据BDCDYH查询宗海权利人信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询宗海权利人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public List<ZhQlrDO> listZhQlrByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return zhQlrService.listZhQlrByBdcdyh(bdcdyh);
    }

}