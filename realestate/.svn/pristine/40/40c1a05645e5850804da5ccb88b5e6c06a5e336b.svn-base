package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.service.rest.exchange.CityAccessRestService;
import cn.gtmap.realestate.exchange.service.national.CityAccesssModelHandlerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 2022/10/31
 * @description 市级不动产数据汇交
 */
@RestController
@Api(tags = "不动产数据汇交")
public class CityAccessRestController implements CityAccessRestService {

    @Autowired
    CityAccesssModelHandlerService cityAccesssModelHandlerService;

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "市级上报（根据工作流实例ID进行上报）")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流主键", required = true, dataType = "String", paramType = "query")})
    public void cityAccessByProcessInsId(@RequestParam(name = "processInsId") String processInsId) {
        cityAccesssModelHandlerService.cityAccessByProcessInsId(processInsId);
    }
}
