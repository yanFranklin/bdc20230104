package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.dto.exchange.WorkFlowRequest;
import cn.gtmap.realestate.common.core.service.feign.building.BdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.DjxxFeignService;
import cn.gtmap.realestate.common.core.service.rest.exchange.ExchangeInterfaceRestService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.wwsq.ResponseHead;
import cn.gtmap.realestate.exchange.core.dto.wwsq.WwsqResponse;
import cn.gtmap.realestate.exchange.core.ex.ValidException;
import cn.gtmap.realestate.exchange.service.impl.shucheng.ShuchengWwsqServiceImpl;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.service.inf.sjpt.GxRzService;
import cn.gtmap.realestate.exchange.service.inf.wwsq.GxWwSqxxService;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import cn.gtmap.realestate.exchange.util.enums.MsgEnum;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Extension;
import io.swagger.annotations.ExtensionProperty;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-05
 * @description
 */
@RestController
public class ExchangeInterfaceRestController implements ExchangeInterfaceRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeInterfaceRestController.class);

    @Autowired
    private GxRzService gxRzService;

    @Autowired
    private BdcdyFeignService bdcdyFeignService;

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    @Autowired
    private GxWwSqxxService gxWwSqxxService;

    @Autowired
    private DjxxFeignService djxxFeignService;

    @Autowired
    ShuchengWwsqServiceImpl shuchengWwsqService;

    @Value("${data.version:}")
    private String version;

    /**
     * @param beanName
     * @param requestObject
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description post方式向EXCHANGE请求发送第三方接口
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "post方式向EXCHANGE请求发送第三方接口")
    public Object requestInterface(@PathVariable(name = "beanName") String beanName, @RequestBody Object requestObject) {
        return exchangeBeanRequestService.request(beanName, requestObject);
    }


    /**
     * @param beanName
     * @param requestObject
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 外网申请
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "外网申请post方式向EXCHANGE请求发送第三方接口")
    public Object wwsqRequestInterface(@PathVariable(name = "beanName") String beanName, @RequestBody Object requestObject) {
        Object responseObject = null;
        //returncode  给予默认值
        String returncode = Constants.CODE_SEARCH_ERROR;
        String msg = MsgEnum.PARAM_ERROR.getMsg();
        //抵押登记 银行和互联完入参不一致处理
        if (StringUtils.isNotBlank(beanName) && "dydj".equals(beanName) && requestObject instanceof JSONObject) {
            Object data = ((JSONObject) requestObject).get("data");
            if (data instanceof JSONArray) {
                JSONArray dataJsonArray = (JSONArray) data;
                Object qlr = dataJsonArray.getJSONObject(0).get("dyqlr");
                if (qlr instanceof JSONObject) {
                    for (Object temp : dataJsonArray) {
                        JSONObject tempJSONObject = (JSONObject) temp;
                        JSONObject tempQlr = tempJSONObject.getJSONObject("dyqlr");
                        tempJSONObject.remove("dyqlr");
                        JSONArray tempJsonArray = new JSONArray();
                        tempJsonArray.add(tempQlr);
                        tempJSONObject.put("dyqlr", tempJsonArray);
                    }
                }
            }
        }
        // 将wlxx放入sqxx节点中，用于对照领证方式lzfs
        if (StringUtils.isNotBlank(beanName) && "initWwsqxx".equals(beanName)
                && requestObject instanceof JSONObject) {
            JSONObject data = ((JSONObject) requestObject).getJSONObject("data");
            if (data != null && CollectionUtils.isNotEmpty(data.getJSONArray("wlxx"))) {
                JSONArray wlxxJsonArray = data.getJSONArray("wlxx");
                JSONArray sqxxJsonArray = data.getJSONArray("sqxx");
                if (CollectionUtils.isNotEmpty(sqxxJsonArray)) {
                    for (Object sq : sqxxJsonArray) {
                        JSONObject sqxx = (JSONObject) sq;
                        sqxx.put("wlxx", wlxxJsonArray);
                    }
                    data.put("sqxx", sqxxJsonArray);
                    ((JSONObject) requestObject).put("data", data);
                }
            }
        }

        try {
            responseObject = exchangeBeanRequestService.request(beanName, requestObject);
            returncode = Constants.CODE_SUCCESS;
            msg = MsgEnum.SUCCESS.getMsg();
        } catch (ValidException e) {
            if (StringUtils.isNotBlank(e.getMessage())) {
                msg = e.getMessage();
            }
        } catch (Exception e) {
            if (StringUtils.isNotBlank(e.getMessage())) {
                msg = e.getMessage();
            }
            LOGGER.error("外网申请请求服务异常,beanName:{}", beanName, e);
        }
        WwsqResponse wwsqResponse = new WwsqResponse();
        ResponseHead responseHead = new ResponseHead();
        responseHead.setStatusCode(returncode);
        responseHead.setReturncode(returncode);
        responseHead.setMsg(msg);
        wwsqResponse.setHead(responseHead);
        wwsqResponse.setData(responseObject);
        return wwsqResponse;
    }

    /**
     * @param beanName
     * @param requestObject
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 与省级平台相关的接口 记录日志
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "与省级平台相关的接口")
    public Object sjptRequestInterface(@PathVariable(name = "beanName") String beanName,
                                       @RequestBody Object requestObject) {
        return exchangeBeanRequestService.request(beanName, requestObject);
    }

    /**
     * @param beanName
     * @param processInsId
     * @param taskId
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 内部使用工作流请求(异步)
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "工作流请求(异步)", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    public void workflowSyncRequestInterface(@PathVariable(name = "beanName") String beanName,
                                             @RequestParam(name = "processInsId") String processInsId,
                                             @RequestParam(name = "taskId", required = false) String taskId,
                                             @RequestParam(name = "opinion", required = false) String opinion,
                                             @RequestParam(name = "isDelete", required = false) String isDelete) {

        // TODO 工作流相关请求 由于国图大云不支持异步 先自己做成异步
        new Thread() {
            @Override
            public void run() {
                LOGGER.info("异步推送互联网状态开始");
                Map<String, String> requestObject = new HashMap<>();
                requestObject.put("processInsId", processInsId);
                requestObject.put("taskId", taskId);
                requestObject.put("opinion", opinion);
                requestObject.put("isDelete", isDelete);
                exchangeBeanRequestService.request(beanName, requestObject);
                LOGGER.info("异步推送互联网状态结束");
            }
        }.start();
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "工作流请求(异步)", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    public void workflowSyncRequestInterface(@PathVariable(name = "beanName") String beanName, @RequestBody WorkFlowRequest workFlowRequest) {
        new Thread() {
            @Override
            public void run() {
                LOGGER.info("异步推送互联网状态开始");
                Map<String, String> requestObject = new HashMap<>();
                requestObject.put("processInsId", workFlowRequest.getProcessInsId());
                requestObject.put("taskId", workFlowRequest.getTaskId());
                requestObject.put("opinion", workFlowRequest.getOpinion());
                requestObject.put("isDelete", workFlowRequest.getIsDelete());
                exchangeBeanRequestService.request(beanName, requestObject);
                LOGGER.info("异步推送互联网状态结束");
            }
        }.start();
    }

    /**
     * @param beanName
     * @param request
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 工作流请求(异步)
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "工作流请求(异步)", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    public void workflowRequestInterface(@PathVariable(name = "beanName") String beanName, HttpServletRequest request) {
        // TODO 工作流相关请求 由于国图大云不支持异步 先自己做成异步
        new Thread() {
            @Override
            public void run() {
                workflowSyncRequestInterface(beanName, request);
            }
        }.start();
    }

    /**
     * @param beanName
     * @param request
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 工作流请求(同步)
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "工作流请求(同步)", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    public void workflowSyncRequestInterface(@PathVariable(name = "beanName") String beanName, HttpServletRequest request) {
        try {
            Map<String, String[]> map = request.getParameterMap();
            Map<String, Object> paramMap = new HashMap<>();
            if (MapUtils.isNotEmpty(map)) {
                for (String key : map.keySet()) {
                    if (map.get(key) != null && map.get(key).length > 0) {
                        paramMap.put(key, map.get(key)[0]);
                    }
                }
            }
            exchangeBeanRequestService.request(beanName, paramMap);
        } catch (Exception e) {
            LOGGER.error("工作流事件请求异常:{}", e.getMessage());
            LOGGER.debug("工作流事件请求异常:", e);
        }
    }

    /**
     * @param beanName
     * @param request
     * @return java.lang.Object
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description get方式向EXCHANGE请求发送第三方接口
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "get方式向EXCHANGE请求发送第三方接口", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @Override
    public Object requestInterface(@PathVariable(name = "beanName") String beanName, HttpServletRequest request) {
        Map<String, String[]> map = request.getParameterMap();
        Map<String, Object> paramMap = new HashMap<>();
        if (MapUtils.isNotEmpty(map)) {
            for (String key : map.keySet()) {
                if (map.get(key) != null && map.get(key).length > 0) {
                    paramMap.put(key, map.get(key)[0]);
                }
            }
        }
        return exchangeBeanRequestService.request(beanName, paramMap);
    }

    @Override
    public Object requestBakInterface(@PathVariable(name = "beanName") String beanName, @RequestBody Object requestObject) {
        return requestInterface(beanName + "Bak", requestObject);
    }

    @Override
    public Object wwsqRequestBakInterface(@PathVariable(name = "beanName") String beanName, @RequestBody Object requestObject) {
        return wwsqRequestInterface(beanName + "Bak", requestObject);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "post方式向EXCHANGE请求发送第三方接口")
    public Object swRequestInterface(@PathVariable(name = "beanName") String beanName, @RequestBody Object requestObject) {
        return exchangeBeanRequestService.swRequest(beanName, requestObject);
    }

}
