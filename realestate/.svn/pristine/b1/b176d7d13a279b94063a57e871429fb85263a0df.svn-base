package cn.gtmap.realestate.register.ui.web.rest;

import cn.gtmap.realestate.common.matcher.TaskHandleClientMatcher;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.register.RegisterWorkflowFeignService;
import cn.gtmap.realestate.register.ui.web.main.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/11/15
 * @description
 */
@RestController
@RequestMapping("/rest/v1.0/workflow")
public class BdcWorkFlowController extends BaseController {
    @Autowired
    private TaskHandleClientMatcher taskHandleClient;
    @Autowired
    private RegisterWorkflowFeignService registerWorkflowFeignService;

    /**
     * @param gzlslid 工作流实例ID
     * @return boolean
     * @author <a href ="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 流程办结
     */
    @PostMapping("/end")
    @ApiOperation(value = "流程办结")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    public void processEnd(@RequestParam(value = "gzlslid") String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("办结流程的gzlslid不能为空！");
        }
        LOGGER.error("办结：{}", gzlslid);
        registerWorkflowFeignService.changeAjzt(gzlslid);
    }

}
