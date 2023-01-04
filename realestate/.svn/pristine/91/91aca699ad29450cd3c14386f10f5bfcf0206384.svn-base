package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.dto.inquiry.*;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcYbtjQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcBzbZmRestService;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcYbtjRestService;
import cn.gtmap.realestate.inquiry.service.BdcBzbZhcxZmService;
import cn.gtmap.realestate.inquiry.service.BdcYbtjService;
import cn.gtmap.realestate.inquiry.service.BdcZszmCxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @version 1.0, 2022/12/28
 * @description 月报统计查询服务
 */
@RestController
@Api(tags = "月报统计服务接口")
public class BdcYbtjRestController implements BdcYbtjRestService {
    @Autowired
    private BdcYbtjService bdcYbtjService;

    /**
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @version 1.0, 2022/12/28
     * @description 月报统计查询
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询不动产业务统计信息")
    @ApiImplicitParam(name = "bdcYbtjQO", value = "参数信息", required = true, paramType = "body")
    public List bdcywtj(@RequestBody BdcYbtjQO bdcYbtjQO) {
        return bdcYbtjService.bdcywtj(bdcYbtjQO);
    }

    /**
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @version 1.0, 2022/12/28
     * @description 月报统计查询
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询不动产业务统计信息")
    @ApiImplicitParam(name = "bdcYbtjQO", value = "参数信息", required = true, paramType = "body")
    public List bdcyzbwtj(@RequestBody BdcYbtjQO bdcYbtjQO) {
        return bdcYbtjService.bdcyzbwtj(bdcYbtjQO);
    }

}
