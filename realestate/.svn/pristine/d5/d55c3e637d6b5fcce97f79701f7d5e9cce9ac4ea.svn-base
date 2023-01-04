package cn.gtmap.realestate.inquiry.ui.web.rest.yancheng;

import cn.gtmap.gtc.workflow.clients.manage.ProcessDefinitionClient;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskCustomExtendClient;
import cn.gtmap.gtc.workflow.domain.common.RequestCondition;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.gtc.workflow.enums.manage.QueryJudge;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.service.feign.exchange.YanchengYthFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.YthxxTsFeignService;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/2/2
 * @description 盐城一体化手动推送业务信息
 */
@RestController
@RequestMapping("/rest/v1.0/yth")
public class BdcYthController extends BaseController {

    /**
     * 网上业务显示流程名称
     */
    @Value("#{'${config.wsyw.gzldyids:}'.split(',')}")
    private List<String> wsywProcessKey;

    @Autowired
    ProcessDefinitionClient processDefinitionClient;

    @Autowired
    private ProcessTaskCustomExtendClient processTaskCustomExtendClient;

    @Autowired
    YanchengYthFeignService yanchengYthFeignService;

    @Autowired
    YthxxTsFeignService ythxxTsFeignService;

    @GetMapping("/wsyw/process")
    @ApiOperation(value = "获取所有已经发布的最新版本的流程定义")
    @ResponseStatus(HttpStatus.OK)
    public List<ProcessDefData> getWsywProcessDefData() {
        List<ProcessDefData> allProcessDefData = processDefinitionClient.getAllProcessDefData();
        return allProcessDefData.stream().filter(processDefData -> wsywProcessKey.contains(processDefData.getProcessDefKey())).collect(Collectors.toList());
    }

    @PostMapping("/task/complete")
    @ApiOperation(value = "分页查询一体化业务数据")
    @ResponseStatus(HttpStatus.OK)
    public Object queryCompleteTask(@LayuiPageable Pageable pageable, HttpServletRequest httpServletRequest) {
        List<RequestCondition> requestConditions = querySearchParams(httpServletRequest.getParameter("param"));
        // 设置审批来源为一体化数据
        RequestCondition splyEq = new RequestCondition();
        splyEq.setRequestKey("sply");
        splyEq.setRequestValue("1");
        splyEq.setRequestJudge(QueryJudge.EQUALS.value());
        requestConditions.add(splyEq);
        Page<Map<String, Object>> completeTaskPage = processTaskCustomExtendClient.completeTaskExtendList(requestConditions, pageable.getPageSize(), pageable.getPageNumber());
        return addLayUiCode(completeTaskPage);
    }

    /**
     * 处理前端返回的查询参数转为<code>RequestCondition</code>对象，过滤掉value为空的查询条件
     */
    private List<RequestCondition> querySearchParams(String param) {
        if (StringUtils.isBlank(param)) {
            return new ArrayList<>();
        }
        final List<RequestCondition> conditionList = JSONArray.parseArray(param, RequestCondition.class);
        // in 查询方式统一传 list
        return conditionList.stream()
                .filter(t -> StringUtils.isNotBlank((CharSequence) t.getRequestValue()))
                .peek(requestCondition -> {
                    if (StringUtils.equalsIgnoreCase(requestCondition.getRequestJudge(), "in")) {
                        String inparams = requestCondition.getRequestValue().toString();
                        if (StringUtils.isNotBlank(inparams)) {
                            String[] paramList = StringUtils.split(inparams, ",");
                            requestCondition.setRequestValue(Arrays.asList(paramList));
                        }
                    }
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/tsslxx")
    @ApiOperation(value = "推送一体化受理信息")
    @ResponseStatus(HttpStatus.OK)
    public Object ythTsSlxx(String gzlslid) {
        return yanchengYthFeignService.ythTsSlxx(gzlslid);
    }

    @GetMapping("/tsshxx")
    @ApiOperation(value = "推送一体化审核信息")
    @ResponseStatus(HttpStatus.OK)
    public Object ythTsShxx(String gzlslid) {
        return yanchengYthFeignService.ythTsShxx(gzlslid);
    }

    @GetMapping("/tsfzxx")
    @ApiOperation(value = "一体化推送发证信息")
    @ResponseStatus(HttpStatus.OK)
    public Object ythTsFzxx(String gzlslid) {
        return yanchengYthFeignService.ythTsFzxx(gzlslid);
    }

    @GetMapping("/tscjfxx")
    @ApiOperation(value = "一体化推送查解封信息")
    @ResponseStatus(HttpStatus.OK)
    public void ythTsCjfxx(@RequestParam(name = "processInsId") String processInsId) {
        ythxxTsFeignService.ythxxsdts(processInsId);
    }
}