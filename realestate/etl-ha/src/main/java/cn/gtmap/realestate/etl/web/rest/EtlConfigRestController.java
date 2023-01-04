package cn.gtmap.realestate.etl.web.rest;

import cn.gtmap.realestate.common.core.service.rest.etl.EtlConfigRestService;
import cn.gtmap.realestate.etl.service.EtlConfigService;
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
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/7/27
 * @description 配置服务
 */
@RestController
@Api(tags = "配置服务")
public class EtlConfigRestController implements EtlConfigRestService {

    @Autowired
    private EtlConfigService etlConfigService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "执行配置sql", notes = "执行配置sql")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "SQL以及参数", required = true, dataType = "map", paramType = "body")
    })
    public List<Map> executeConfigSql(@RequestBody Map param){
        return etlConfigService.executeConfigSql(param);
    }
}
