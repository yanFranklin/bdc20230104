package cn.gtmap.realestate.accept.ui.web.rest;

import cn.gtmap.gtc.workflow.clients.manage.ProcessDefinitionClient;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/02/25
 * @description 流程相关服务
 */
@RestController
@RequestMapping("/rest/v1.0/task")
@Api(tags = "流程相关服务接口")
public class BdcProcessController {

    @Autowired
    private ProcessDefinitionClient processDefinitionClient;

    /**
     * 获取所有已经发布的最新版本的流程定义
     *
     * @return List<ProcessDefData>
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/process/all")
    @ApiOperation(value = "获取所有已经发布的最新版本的流程定义")
    @ResponseStatus(HttpStatus.OK)
    public List<ProcessDefData> getAllProcessDefData() {
        return processDefinitionClient.getAllProcessDefData();
    }
}
