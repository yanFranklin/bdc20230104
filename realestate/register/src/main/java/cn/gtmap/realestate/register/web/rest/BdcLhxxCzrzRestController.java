package cn.gtmap.realestate.register.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcLhxxCzrzDO;
import cn.gtmap.realestate.common.core.service.rest.register.BdcLhxxCzrzRestService;
import cn.gtmap.realestate.register.service.BdcLhxxCzrzService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/1/10
 * @description 量化信息操作日志rest服务
 */
@RestController
@Api(tags = "量化信息操作日志rest服务")
public class BdcLhxxCzrzRestController implements BdcLhxxCzrzRestService {

    @Autowired
    private BdcLhxxCzrzService bdcLhxxCzrzService;

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询量化信息操作日志信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcLhxxCzrzDO", value = "量化信息操作日志", required = true, dataType = "BdcLhxxCzrzDO", paramType = "body")})
    public List<BdcLhxxCzrzDO> queryBdcLhxxCrzz(@RequestBody BdcLhxxCzrzDO bdcLhxxCzrzDO) {
        return this.bdcLhxxCzrzService.queryLhxxCzrz(bdcLhxxCzrzDO);
    }
}
