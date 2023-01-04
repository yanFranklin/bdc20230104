package cn.gtmap.realestate.natural.web.rest;

import cn.gtmap.realestate.common.core.service.rest.natural.ZrzyWorkflowRestService;
import cn.gtmap.realestate.natural.service.ZrzyDbxxService;
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
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/12/13
 * @description 自然资源工作流事件
 */
@RestController
@Api(tags = "自然资源证书服务接口")
public class ZrzyWorkflowRestController implements ZrzyWorkflowRestService {

    @Autowired
    ZrzyDbxxService zrzyDbxxService;

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "转发登簿事件")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID")})
    @Override
    public void zrzyDbEvent(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "currentUserName") String currentUserName){
        //生成证书证号
        //更新zrzy_xm qszt，dbr,djsj 参考：”/realestate-register/rest/v1.0/dbxx/dbxxAndQszt"
        zrzyDbxxService.updateZrzyDbxx(processInsId,currentUserName);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "转发办结事件")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID")})
    @Override
    public void endEvent(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "currentUserName") String currentUserName){
        //更新zrzy_xm ajzt,jssj，参考：/realestate-register/rest/v1.0/workflow/dbxx/ajzt
        zrzyDbxxService.changeAjzt(processInsId);
    }
}
