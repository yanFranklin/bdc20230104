package cn.gtmap.realestate.register.web.rest.yancheng;

import cn.gtmap.realestate.common.core.service.rest.register.BdcPjdxRestService;
import cn.gtmap.realestate.register.service.BdcPjdxService;
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
 * @author <a href="mailto:qianguoyi@gtmap.cn">qianguoyi</a>
 * @version 1.0  2021-6-25
 * @description (盐城) 评价短信发送
 */
@RestController
@Api(tags = "不动产短信下发服务")
public class BdcPjdxRestController implements BdcPjdxRestService{
    @Autowired
    private BdcPjdxService bdcPjdxService;

    /**
     * 盐城评价短信
     *
     * @param processInsId 工作流实例ID
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("盐城短信接口下发")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例iD", required = true, dataType = "string", paramType = "query")})
    public void queryPjdxMsg(@RequestParam(value = "processInsId") String processInsId,@RequestParam(value = "taskId") String taskId){
        bdcPjdxService.queryPjdxMsg(processInsId,taskId);
    }
}
