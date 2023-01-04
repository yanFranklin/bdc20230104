package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.service.AcceptWorkFlowService;
import cn.gtmap.realestate.common.core.service.rest.accept.AcceptWorkFlowRestService;
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
 * @version 1.0, 2022/05/23.
 * @description Accept受理工作流Rest接口服务
 */
@RestController
@Api(tags = "Accept受理工作流Rest接口服务")
public class AcceptWorkFlowRestController implements AcceptWorkFlowRestService {

    @Autowired
    AcceptWorkFlowService acceptWorkFlowService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新需要缴费的月结收费信息发票号为‘月结’", notes = "更新需要缴费的月结收费信息发票号为‘月结’")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")
    })
    public void updateYjjffph(@RequestParam(name="processInsId")  String processInsId) {
        acceptWorkFlowService.updateYjjffph(processInsId);
    }
}
