package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskCustomExtendClient;
import cn.gtmap.gtc.workflow.clients.manage.StatisticsTaskClient;
import cn.gtmap.gtc.workflow.domain.common.RequestCondition;
import cn.gtmap.gtc.workflow.enums.manage.ProcQuerykey;
import cn.gtmap.gtc.workflow.enums.manage.QueryJudge;
import cn.gtmap.gtc.workflow.enums.manage.TaskQueryKey;
import cn.gtmap.gtc.workflow.enums.manage.TaskStatusQueryEqEnum;
import cn.gtmap.gtc.workflow.vo.StatisticsTaskVo;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.service.feign.inquiry.huaian.BdcGzltjXmxxFeignService;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcCstjxxVO;
import cn.gtmap.realestate.common.util.ComputeTaskTimeUtils;
import cn.gtmap.realestate.inquiry.ui.core.dto.BdcCountSzscDTO;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2019/12/22.
 * @description 不动产统计监管查询
 */
@RestController
@RequestMapping("/rest/v1.0/tjjg")
public class BdcTjjgController extends BaseController {

    @Autowired
    ProcessTaskCustomExtendClient processTaskCustomExtendClient;

    @Autowired
    BdcDtcxController bdcDtcxController;

    @Autowired
    ComputeTaskTimeUtils computeTaskTimeUtils;

    @Autowired
    StatisticsTaskClient statisticsTaskClient;

    @Autowired
    BdcGzltjXmxxFeignService bdcGzltjXmxxFeignService;

    @GetMapping("/rybm/page")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页查询统计监管人员部门退回节点数据")
    public Object queryTjjgRyBmByPage(@LayuiPageable Pageable pageable,
                                      @RequestParam(value = "paramJson",required = false) String paramJson) {
        List<RequestCondition> requestConditions = querySearchParams(paramJson);
        // 设置统计退回状态的节点信息
        RequestCondition backStatusEq = new RequestCondition();
        backStatusEq.setRequestKey(TaskQueryKey.TASK_STATUS.value());
        backStatusEq.setRequestValue(TaskStatusQueryEqEnum.BACK.getValue());
        backStatusEq.setRequestJudge(QueryJudge.SPECIAL.value());
        requestConditions.add(backStatusEq);

        RequestCondition backReasonAndPersonStatusEq = new RequestCondition();
        backReasonAndPersonStatusEq.setRequestKey(TaskQueryKey.BACK_INFO.value());
        requestConditions.add(backReasonAndPersonStatusEq);
        Page<Map<String, Object>> pagelist =  this.processTaskCustomExtendClient.allTaskExtendList(requestConditions, pageable.getPageSize(), pageable.getPageNumber());

        if(null != pagelist && CollectionUtils.isNotEmpty(pagelist.getContent())){
            for(Map<String, Object> map : pagelist.getContent()){
                if(map.containsKey("backInfo")){
                    Map backInfoMap = (Map)map.get("backInfo");
                    if(null != backInfoMap){
                        map.put("backReason",backInfoMap.get("next_back_opinion"));
                        map.put("backPerson",backInfoMap.get("backUserAlias"));
                        map.put("backTime", backInfoMap.get("next_back_opinion_time"));
                    }
                }
            }
        }

        return super.addLayUiCode(pagelist);
    }

    @GetMapping("/lc/page")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页查询统计监管流程超期数据")
    public Object queryTjjgLcByPage(@LayuiPageable Pageable pageable,
                                    @RequestParam(value = "paramJson",required = false) String paramJson) {
        List<RequestCondition> requestConditions = querySearchParams(paramJson);
        // 设置流程超期状态
        RequestCondition timeOutStatusEq = new RequestCondition();
        timeOutStatusEq.setRequestKey(ProcQuerykey.PROCTIMEOUTSTATUS.getValue());
        timeOutStatusEq.setRequestValue(1);
        timeOutStatusEq.setRequestJudge(QueryJudge.EQUALS.value());
        requestConditions.add(timeOutStatusEq);
        Page<Map<String, Object>> pagelist = this.processTaskCustomExtendClient.queryProcessInsWithProject(requestConditions, 0, pageable.getPageSize(), pageable.getPageNumber());
        return super.addLayUiCode(pagelist);
    }

    /**
     * 获取流程超期数据
     * @param gzlslid 工作流实例ID
     * @return 流程超期详情数据
     */
    @GetMapping("/lc/csxq")
    public Object queryCsxxByGzlslid(@RequestParam(value = "gzlslid") String gzlslid){
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID");
        }
        List<StatisticsTaskVo> statisticsTaskVoList = this.statisticsTaskClient.queryStatisticsTaskInfoByProcessInsId(gzlslid);
        return statisticsTaskVoList;
    }

    /**
     * 统计流程超期数据
     * @param requestJson 请求参数
     * @return 统计结果
     */
    @PostMapping(value = "/lc/cstj")
    public Object lccstj(@RequestBody String requestJson){
        if(StringUtils.isBlank(requestJson)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺少查询参数");
        }
        List<RequestCondition> requestConditions = querySearchParams(requestJson);
        // 设置流程超期状态
        RequestCondition timeOutStatusEq = new RequestCondition();
        timeOutStatusEq.setRequestKey(ProcQuerykey.PROCTIMEOUTSTATUS.getValue());
        timeOutStatusEq.setRequestValue(1);
        timeOutStatusEq.setRequestJudge(QueryJudge.EQUALS.value());
        requestConditions.add(timeOutStatusEq);
        List<BdcCstjxxVO> bdcCstjxxVOList =  this.bdcGzltjXmxxFeignService.listBdcCstjxx(JSON.toJSONString(requestConditions));
        bdcCstjxxVOList = bdcCstjxxVOList.stream().sorted(Comparator.comparing(BdcCstjxxVO::getCsjsl, Comparator.naturalOrder())).collect(Collectors.toList());
        return bdcCstjxxVOList;
    }

    @PostMapping("/count/szsc/list")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("批量计算缮证时长")
    public Map<String, Object> countSzscList(@RequestBody List<BdcCountSzscDTO> bdcCountSzscDTOList) {
        if(CollectionUtils.isNotEmpty(bdcCountSzscDTOList)){
            Map<String, Object> resultMap = new HashMap<>(bdcCountSzscDTOList.size());
            for(BdcCountSzscDTO bdcCountSzscDTO: bdcCountSzscDTOList){
                resultMap.put(bdcCountSzscDTO.getXmid(),
                        this.computeTaskTimeUtils.computeTaskTimeWithCache(bdcCountSzscDTO.getKssj(), bdcCountSzscDTO.getCjsj(), bdcCountSzscDTO.getSzsj()));
            }
            return resultMap;
        }
       return null;
    }

    /**
     * 处理前端返回的查询参数转为<code>RequestCondition</code>对象，过滤掉value为空的查询条件
     */
    private List<RequestCondition> querySearchParams(String param) {
        if (StringUtils.isBlank(param)) {
            return new ArrayList<>();
        }
        List<RequestCondition> conditionList = JSONArray.parseArray(param, RequestCondition.class);
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

    /**
     * 导出台账查询数据，并对数据进行处理导出
     */
    @PostMapping("/szsc/export")
    public String exportToExcel(HttpServletResponse response,String dataString, String data){
        Map<String,Object> dataMap = JSONObject.parseObject(dataString,Map.class);
        if(!dataMap.containsKey("kssj")){
            throw new AppException(ErrorCode.MISSING_ARG , "缺失查询参数开始时间");
        }
        String kssj = String.valueOf(dataMap.get("kssj"));
        return bdcDtcxController.exportToExcelWithZdyFilter(response, dataString, data, dataMapList -> {
            if(CollectionUtils.isNotEmpty(dataMapList)){
                for(Map map : dataMapList){
                    if(map.containsKey("CJSJ")  && map.containsKey("SZSJ")){
                        if(Objects.nonNull(map.get("CJSJ")) && Objects.nonNull(map.get("SZSJ"))){
                            Object szsc = this.computeTaskTimeUtils.computeTaskTimeWithCache(kssj, String.valueOf(map.get("CJSJ")), String.valueOf(map.get("SZSJ")));
                            map.put("XMID", szsc);
                        }else{
                            map.put("XMID", "");
                        }
                    }
                    if(map.containsKey("DYCJSJ")  && map.containsKey("DYSZSJ")){
                        if(Objects.nonNull(map.get("DYCJSJ")) && Objects.nonNull(map.get("DYSZSJ"))){
                            Object szsc = this.computeTaskTimeUtils.computeTaskTimeWithCache(kssj, String.valueOf(map.get("DYCJSJ")), String.valueOf(map.get("DYSZSJ")));
                            map.put("DYSZSC", szsc);
                        }else{
                            map.put("DYSZSC", "");
                        }
                    }
                }
            }
        });
    }
}
