package cn.gtmap.realestate.common.core.service.rest.exchange;

import cn.gtmap.realestate.common.core.dto.exchange.WorkFlowRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-05
 * @description 与第三方接口交互服务
 */
public interface ExchangeInterfaceRestService {


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param beanName, requestObject
     * @return java.lang.Object
     * @description post方式向EXCHANGE请求发送第三方接口
     */
    @PostMapping("/realestate-exchange/rest/v1.0/interface/{beanName}")
    Object requestInterface(@PathVariable(name = "beanName") String beanName, @RequestBody Object requestObject);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param beanName
     * @param requestObject
     * @return java.lang.Object
     * @description 外网申请
     */
    @PostMapping("/realestate-exchange/rest/v1.0/wwsqinterface/{beanName}")
    Object wwsqRequestInterface(@PathVariable(name = "beanName") String beanName,
                                @RequestBody Object requestObject);



    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return java.lang.Object
     * @description 与省级平台相关的接口
     */
    @PostMapping("/realestate-exchange/rest/v1.0/sjptinterface/{beanName}")
    Object sjptRequestInterface(@PathVariable(name = "beanName") String beanName,
                                @RequestBody Object requestObject);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param beanName
     * @param processInsId
     * @param taskId
     * @return void
     * @description  内部使用工作流请求(异步)
     */
    @GetMapping("/realestate-exchange/rest/v1.0/workflowinterface/param/{beanName}")
    void workflowSyncRequestInterface(@PathVariable(name = "beanName") String beanName,
                                  @RequestParam(name = "processInsId") String processInsId,
                                  @RequestParam(name = "taskId",required = false) String taskId,
                                  @RequestParam(name = "opinion" ,required = false)String opinion,
                                  @RequestParam(name = "isDelete" ,required = false)String isDelete);

    /**
     * 内部使用工作流请求(异步)
     * @param beanName 请求beanName
     * @param workFlowRequest 工作流请求参数
     */
    @PostMapping("/realestate-exchange/rest/v1.0/workflowinterface/param/{beanName}")
    void workflowSyncRequestInterface(@PathVariable(name = "beanName")String beanName, @RequestBody WorkFlowRequest workFlowRequest);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param beanName
     * @param request
     * @return void
     * @description  工作流请求(异步)
     */
    @GetMapping("/realestate-exchange/rest/v1.0/workflowinterface/{beanName}")
    void workflowRequestInterface(@PathVariable(name = "beanName") String beanName,HttpServletRequest request);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param beanName
     * @param request
     * @return void
     * @description  工作流请求(同步)
     */
    @GetMapping("/realestate-exchange/rest/v1.0/workflowinterface/sync/{beanName}")
    void workflowSyncRequestInterface(@PathVariable(name = "beanName") String beanName,HttpServletRequest request);

    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param beanName
     * @return java.lang.Object
     * @description get方式向EXCHANGE请求发送第三方接口
     */
    @GetMapping("/realestate-exchange/rest/v1.0/interface/{beanName}")
    Object requestInterface(@PathVariable(name = "beanName") String beanName,HttpServletRequest request);

    /**
     * @param beanName, requestObject
     * @return java.lang.Object
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description post方式向EXCHANGE请求发送第三方接口 降级兼容部分
     */
    @PostMapping("/realestate-exchange/rest/v.bak/interface/{beanName}")
    Object requestBakInterface(@PathVariable(name = "beanName") String beanName, @RequestBody Object requestObject);


    /**
     * @param beanName
     * @param requestObject
     * @return java.lang.Object
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 外网申请 降级兼容部分
     */
    @PostMapping("/realestate-exchange/rest/v.bak/wwsqinterface/{beanName}")
    Object wwsqRequestBakInterface(@PathVariable(name = "beanName") String beanName,
                                   @RequestBody Object requestObject);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param beanName, requestObject
     * @return java.lang.Object
     * @description post方式向EXCHANGE请求第三方接口
     */
    @PostMapping("/realestate-exchange/rest/v1.0/swinterface/{beanName}")
    Object swRequestInterface(@PathVariable(name = "beanName") String beanName, @RequestBody Object requestObject);

}
