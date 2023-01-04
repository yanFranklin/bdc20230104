package cn.gtmap.realestate.exchange.web.main;

import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-25
 * @description 外网系统前台 业务系统受理页面
 */
@Controller
@RequestMapping("/realestate-exchange/wwsq")
public class WwsqSlController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WwsqSlController.class);

    @Autowired
    private CommonService commonService;

    @Autowired
    private ProcessTaskClient processTaskClient;

    @Value("${app.oauth}")
    private String accountUrl;

    @Value("${app.portal-ui:}")
    private String portalUrl;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param spxtywh
     * @return cn.gtmap.gtc.workflow.domain.manage.TaskData
     * @description 根据审批系统业务号 获取 任务实体
     */
    @RequestMapping("/sl")
    @ResponseBody
    public TaskData queryTaskBySpxtywh(HttpServletResponse response,String spxtywh,String slr) throws IOException {
        LOGGER.info("请求跳转 spxtywh:{},slr:{}",spxtywh,slr);
        TaskData taskData = null;
        if(StringUtils.isNotBlank(spxtywh)){
            List<BdcXmDO> bdcXmDOList = commonService.listBdcXmBySpxtywh(spxtywh);
            if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                String gzlslid = bdcXmDOList.get(0).getGzlslid();
                List<TaskData> taskList = processTaskClient.processLastTasks(gzlslid);
                if(CollectionUtils.isNotEmpty(taskList)){
                    taskData = taskList.get(0);
                    LOGGER.info("redirect跳转 spxtywh:{},slr:{}",spxtywh,slr);
                    String url = portalUrl + "/view/wwsqslredirect.html?taskId="+ taskData.getTaskId();
                    String redirectUrl = accountUrl +
                            "/user-redirect?authUrl="+accountUrl+"/login&username="+slr+"&redirect=" +url;
                    response.sendRedirect(redirectUrl);
                }
            }
        }
        LOGGER.info("执行跳转结束 spxtywh:{},slr:{}",spxtywh,slr);
        return taskData;
    }

}
