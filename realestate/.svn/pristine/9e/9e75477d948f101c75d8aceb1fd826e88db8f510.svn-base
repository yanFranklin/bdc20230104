package cn.gtmap.realestate.config.web.rest;

import cn.gtmap.realestate.common.core.dto.BdcXxbdDTO;
import cn.gtmap.realestate.common.core.qo.config.BdcXxbdQO;
import cn.gtmap.realestate.common.core.service.rest.config.BdcXxbdRestService;
import cn.gtmap.realestate.config.service.BdcXxbdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/7/28
 * @description 信息比对接口
 */
@RestController
@Api(tags = "信息比对接口")
public class BdcXxbdRestController implements BdcXxbdRestService {

    @Autowired
    private BdcXxbdService bdcXxbdService;

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "生成信息比对结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcXxbdQO", value = "信息比对查询参数", required = true, dataType = "BdcXxbdQO", paramType = "body")})
    public BdcXxbdDTO generateXxbdDTO(@RequestBody BdcXxbdQO bdcXxbdQO){
        return bdcXxbdService.generateXxbdDTO(bdcXxbdQO);
    }
}
