package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.gtc.clients.LogMessageClient;
import cn.gtmap.gtc.sso.domain.dto.AuditLogDto;
import cn.gtmap.gtc.sso.domain.dto.DataValue;
import cn.gtmap.gtc.sso.domain.dto.QueryLogCondition;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.starter.gcas.config.GTAutoConfiguration;
import cn.gtmap.realestate.common.config.logaop.LogParamsTslConstants;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcXtCxLogDO;
import cn.gtmap.realestate.common.core.domain.BdcZhcxLogDO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzLogDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcPrintLogDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.FR3DTO;
import cn.gtmap.realestate.common.core.dto.inquiry.FR3ListDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.ZyTzDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzLogQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcLogQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZhcxLogQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcZhcxFeignService;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzYzLogFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcLogFeignService;
import cn.gtmap.realestate.common.core.vo.inquiry.BdcLogVO;
import cn.gtmap.realestate.common.core.vo.inquiry.ResultDataVO;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.common.util.office.ExcelUtil;
import cn.gtmap.realestate.inquiry.ui.util.ExportUtils;
import cn.gtmap.realestate.inquiry.ui.util.LogCheckEnum;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import jxl.write.WriteException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2019/1/11
 * @description
 */
@RestController
@RequestMapping(value = "/log")
public class BdcLogController extends BaseController {

    /**
     * @description Excel全部导出的导出条数
     */
    @Value("${excel.qxdcts:1000}")
    private Integer dcts;

    @Autowired
    private LogMessageClient logMessageClient;
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private BdcLogFeignService bdcLogFeignService;
    @Autowired
    BdcZhcxFeignService bdcZhcxFeignService;
    @Autowired
    BdcGzYzLogFeignService bdcGzYzLogFeignService;

    /**
     * 排除ES查询中默认的查询参数名称
     */
    private final static String[] ES_EXCLUDE_CONDITION = {"eventName", "principal", "beginTime", "endTime", "esSearchKey", "esSearchVal"};

    public static final String FRURL = "frURL";
    public static final String DATAURL = "dataURL";
    public static final String EPRT = "eprt:";

    @GetMapping(value = "/list")
    @ResponseStatus(HttpStatus.OK)
    public Object logListByPage(@LayuiPageable Pageable pageable, BdcLogQO bdcLogQO) throws ParseException {
        //打印留档查询Excel导出全部台账 导出条数配置
        if (Boolean.TRUE.equals(bdcLogQO.getDctspz())) {
            pageable = new PageRequest(0,dcts);
            bdcLogQO.setDctspz(null);//置为null，否则查不到数据
        }
        int pageSize = pageable.getPageSize();
        List<BdcLogVO> logList = Lists.newArrayList();
        BdcLogVO log = null;
        Long begin = null;
        Long end = null;
        if (bdcLogQO.getBeginTime() != null) {
            begin = bdcLogQO.getBeginTime().getTime();
        }
        if (bdcLogQO.getEndTime() != null) {
            end = bdcLogQO.getEndTime().getTime();
        }
        // 组织日志查询参数
        List<QueryLogCondition> conditionList = combineConditionList(bdcLogQO);
        // 平台日志服务 默认查询 查询日志
        Page<AuditLogDto> auditLogDtoPage = logMessageClient.listLogs(pageable.getPageNumber(), pageSize,
                bdcLogQO.getEventName(),
                bdcLogQO.getPrincipal(), null, begin, end, conditionList);
        if (auditLogDtoPage.hasContent()) {
            Iterator<AuditLogDto> auditLogIterator = auditLogDtoPage.iterator();
            while (auditLogIterator.hasNext()) {
                log = analysisAuditLogDto(auditLogIterator.next());
                if (log != null) {
                    logList.add(log);
                }
            }
        }
        return super.addLayUiCode(new GTAutoConfiguration.DefaultPageImpl<>(logList, pageable.getPageNumber(),
                pageable.getPageSize(), auditLogDtoPage.getTotalElements()));
    }

    /**
     * version 1.0
     *
     * @param bdcLogQO 查询条件
     * @description 组织日志查询参数条件
     * @date 2019/3/15
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    private List<QueryLogCondition> combineConditionList(BdcLogQO bdcLogQO) {
        List<QueryLogCondition> conditionList = Lists.newArrayList();
        QueryLogCondition queryLogCondition = null;
        Object value = null;
        if (bdcLogQO != null) {
            Field[] fields = bdcLogQO.getClass().getDeclaredFields();
            // 默認模糊查詢
            String type = StringUtils.isNotBlank(bdcLogQO.getCxfs()) ? bdcLogQO.getCxfs() : CommonConstantUtils.TYPE_LIKE;
            //对查询项：查询条件，查询结果进行转义
            if(StringUtils.isNotBlank(bdcLogQO.getCxtj())){
                bdcLogQO.setCxtj(stringTransferMeaning(bdcLogQO.getCxtj()));
            }
            if(StringUtils.isNotBlank(bdcLogQO.getCxjg())){
                bdcLogQO.setCxjg(stringTransferMeaning(bdcLogQO.getCxjg()));
            }
            try {
                for (int i = 0; i < fields.length; i++) {
                    // 这些属性不放在本过滤条件 直接放在接口参数中
                    if (!StringUtils.equalsIgnoreCase("cxfs", fields[i].getName())
                            && !StringUtils.equalsIgnoreCase("beginTime", fields[i].getName())
                            && !StringUtils.equalsIgnoreCase("endTime", fields[i].getName())
                            && !StringUtils.equalsIgnoreCase("eventName", fields[i].getName())
                            && !StringUtils.equalsIgnoreCase("type", fields[i].getName())) {
                        fields[i].setAccessible(true);
                        value = fields[i].get(bdcLogQO);
                        if (value != null && StringUtils.isNotBlank(value.toString())) {
                            queryLogCondition = new QueryLogCondition();

                            if (StringUtils.equalsIgnoreCase(CommonConstantUtils.RESPONSE, fields[i].getName())) {
                                /**
                                 * 根据结果匹配 目前不支持直接根据RESPONSE匹配
                                 *  保存日志时结果中部分字段值被抽出来存在RESPONSE_PARAM_KEY中
                                 *  查询时根据RESPONSE_PARAM_KEY来匹配
                                 */
                                queryLogCondition.setKey(CommonConstantUtils.RESPONSE_PARAM_KEY);
                            } else if (StringUtils.equalsIgnoreCase(CommonConstantUtils.VIEW_TYPE_NAME, fields[i].getName())) {
                                queryLogCondition.setKey(CommonConstantUtils.VIEW_TYPE_NAME);
                            } else if (StringUtils.equalsIgnoreCase(CommonConstantUtils.ALIAS, fields[i].getName())) {
                                queryLogCondition.setKey(CommonConstantUtils.ALIAS);
                            } else if (StringUtils.equalsIgnoreCase(CommonConstantUtils.PRINCIPAL, fields[i].getName())) {
                                queryLogCondition.setKey(CommonConstantUtils.PRINCIPAL);
                            } else if (StringUtils.equalsIgnoreCase(CommonConstantUtils.ZMBH, fields[i].getName())) {
                                queryLogCondition.setKey(CommonConstantUtils.ZMBH);
                            } else if (StringUtils.equalsIgnoreCase(CommonConstantUtils.SLBH, fields[i].getName())) {
                                queryLogCondition.setKey(CommonConstantUtils.SLBH);
                            } else if (StringUtils.equalsIgnoreCase(CommonConstantUtils.PRINTTYPE, fields[i].getName())) {
                                queryLogCondition.setKey(CommonConstantUtils.PRINTTYPE);
                            } else if (StringUtils.equalsIgnoreCase(CommonConstantUtils.IPADDRESS, fields[i].getName())) {
                                queryLogCondition.setKey(CommonConstantUtils.IPADDRESS);
                            } else if (StringUtils.equalsIgnoreCase(CommonConstantUtils.ZL, fields[i].getName())) {
                                queryLogCondition.setKey(CommonConstantUtils.ZL);
                            }else if (StringUtils.equalsIgnoreCase(CommonConstantUtils.QLR, fields[i].getName())) {
                                queryLogCondition.setKey(CommonConstantUtils.QLR);
                            }else if (StringUtils.equalsIgnoreCase(CommonConstantUtils.CXTJ, fields[i].getName())) {
                                queryLogCondition.setKey(CommonConstantUtils.CXTJ);
                            }else if (StringUtils.equalsIgnoreCase(CommonConstantUtils.CXJG, fields[i].getName())) {
                                queryLogCondition.setKey(CommonConstantUtils.CXJG);
                            }else if (StringUtils.equalsIgnoreCase(CommonConstantUtils.ORGANIZATION, fields[i].getName())) {
                                queryLogCondition.setKey(CommonConstantUtils.ORGANIZATION);
                            }else {
                                // 根据保存日志记录时拼接的前缀 匹配参数
                                queryLogCondition.setKey(CommonConstantUtils.PARAM_SUB + fields[i].getName());
                            }
                            queryLogCondition.setValue(value.toString());
                            queryLogCondition.setType(type);
                            conditionList.add(queryLogCondition);
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("获取值失败！", e);
            }
        }
        return conditionList;
    }

    /**
     * version 1.0
     *
     * @return
     * @description 对字符串中的\和"进行转义（）
     * @date 2021/9/9
     * @author <a href ="mailto:wutao2@gtmap.cn">wutao2</a>
     */
    private String stringTransferMeaning(String param) {
        param=param.replace("\\","\\\\\\\\");
        param=param.replace("\"","\\\"");
        return param;
    }

    /**
     * version 1.0
     *
     * @return
     * @description 参数名转换成中文（）
     * @date 2019/3/15
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    private String formatEngToCha(Map<String, String> paramConfig, Map<String, Map<String, String>> paramConditionConfig, JSONObject param) {
        StringBuilder result = new StringBuilder();
        if (param != null && MapUtils.isNotEmpty(paramConfig) && MapUtils.isNotEmpty(paramConditionConfig)) {
            for (Map.Entry<String, Object> entry : param.entrySet()) {
                String key = entry.getKey();
                if (paramConfig.containsKey(key.toUpperCase())) {
                    // 字典参数
                    if (paramConditionConfig.containsKey(key.toUpperCase()) &&
                            paramConditionConfig.get(key.toUpperCase()).containsKey(param.get(key))) {
                        result.append(paramConfig.get(key.toUpperCase())).append("：").
                                append(paramConditionConfig.get(key.toUpperCase()).get(param.get(key))).append(CommonConstantUtils.separate);
                    } else {
                        result.append(paramConfig.get(key.toUpperCase())).append("：").append(param.get(key)).append(CommonConstantUtils.separate);
                    }
                }
            }

            /**
             * 删除最后一个分隔符
             */
            int index = result.lastIndexOf(CommonConstantUtils.separate);
            if (index > -1 && index == result.length() - CommonConstantUtils.separate.length()) {
                result.delete(index, index + CommonConstantUtils.separate.length());
            }
        }
        return result.toString();
    }

    /**
     * version 1.0
     *
     * @param auditLog 接口返回数据
     * @return
     * @description 解析平台接口返回数据
     * @date 2019/3/21
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    private BdcLogVO analysisAuditLogDto(AuditLogDto auditLog) throws ParseException {
        BdcLogVO log = null;
        Map<String, String> paramConfigs = LogParamsTslConstants.PARAM;
        Map<String, Map<String, String>> paramConditionConfig = LogParamsTslConstants.PARAMCONDITION;
        List<ZyTzDTO> listZyTzDTO = LogParamsTslConstants.LISTZYTZDTO;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (auditLog != null) {
            JSONObject defineParam = new JSONObject();
            String eventType = auditLog.getEvent();
            log = new BdcLogVO();
            log.setId(auditLog.getId());
            log.setTime(auditLog.getTimestamp_millis());
            log.setEventName(eventType);
            log.setRequestUrl(auditLog.getName());
            if (StringUtils.isNotEmpty(auditLog.getPrincipal())) {
                log.setPrincipal(auditLog.getPrincipal());
            }
            // 用户真实姓名，兼容改版前的日志和改版后的日志
            if (StringUtils.isNotEmpty(auditLog.getPrincipal()) && !"unknown".equals(auditLog.getPrincipal())) {
                UserDto userDto = userManagerUtils.getUserByName(auditLog.getPrincipal());
                if (userDto != null) {
                    log.setAlias(userDto.getAlias());
                }
            }


            List<DataValue> dataValueList = auditLog.getBinaryAnnotations();
            boolean activityNameBoolean = false;
            boolean processDefinitionNameBoolean = false;
            boolean processInstanceIdBoolean = false;
            boolean taskIdBoolean = false;
            String reason = "";
            String opnion = "";
            String taskId = "";//任务id
            String activityName = "";//办理节点
            String processDefinitionName = "";//流程名称
            String processInstanceId = "";//流程id
            if (CollectionUtils.isNotEmpty(dataValueList)) {
                for (DataValue dataValue : dataValueList) {
                    String dataValueStr = dataValue.getValue();
                    if (dataValueStr == null) {
                        dataValueStr = "";
                    }
                    dataValueStr = dataValueStr.equalsIgnoreCase("unknown") ? "" : dataValueStr;
                    // 打印模板路径
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.MODELURS)) {
                        log.setModelUrl(dataValueStr);
                        continue;
                    }
                    // 打印数据源请求（打印留档特有）
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.DATAURL)) {
                        log.setDataUrl(dataValueStr);
                        continue;
                    }
                    // 打印数据源xml（打印留档特有）
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.XMLSTR)) {
                        log.setXmlStr(dataValueStr);
                        continue;
                    }

                    // 打印类型（打印留档特有）
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.PRINTTYPE)) {
                        log.setPrintType(dataValueStr);
                        continue;
                    }

                    // 证明编号（打印留档特有）
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.ZMBH)) {
                        log.setZmbh(dataValue.getValue());
                        continue;
                    }

                    // 打印留档真正操作时间；南通打印留档特有）
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), "dyldTime")) {
                        log.setDyldTime(dataValue.getValue());
                        Date date = simpleDateFormat.parse(log.getDyldTime());
                        log.setTime(date);
                        continue;
                    }
                    // 受理编号
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.SLBH)) {
                        log.setSlbh(dataValueStr);
                        continue;
                    }

                    // 不动产单元号
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.BDCDYH)) {
                        log.setBdcdyh(dataValueStr);
                        continue;
                    }
                    // 义务人
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.YWR)) {
                        log.setYwr(dataValueStr);
                        continue;
                    }
                    // 坐落
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.ZL)) {
                        log.setZl(dataValueStr);
                        continue;
                    }
                    // 权利人
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.QLRMC) || StringUtils
                            .equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.QLR)) {
                        log.setQlr(dataValueStr);
                        continue;
                    }
                    // 流程名称
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.PROCESSDEFINITIONNAME)) {
                        processDefinitionNameBoolean = true;
                        processDefinitionName = dataValueStr;
                        continue;
                    }

                    // 删除原因
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.DELETEREASON)) {
                        reason = dataValueStr;
                        continue;
                    }

                    // 转发意见
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.ZFOPNION)) {
                        opnion = dataValueStr;
                        continue;
                    }
                    // 流程id
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.PROCESSINSTANCEID)) {
                        processInstanceIdBoolean = true;
                        processInstanceId = dataValueStr;
                        continue;
                    }
                    // taskid
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.TASKID)) {
                        taskIdBoolean = true;
                        taskId = dataValueStr;
                        continue;
                    }
                    // 办理节点
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.ACTIVITYNAME)) {
                        activityNameBoolean = true;
                        activityName = dataValueStr;
                        continue;
                    }

                    // 资源台账 方案一
//                    if(StringUtils.equalsIgnoreCase(dataValue.getKey(),CommonConstantUtils.CONTROLLER_METHOD_NAME)) {
//                        String methodName = dataValue.getValue();
//                        log.setZyTzName("内部操作");
//                        for (int i=0;i<listZyTzDTO.size();i++) {
//                            if(listZyTzDTO.get(i).getTzType().equals(methodName)){
//                                log.setZyTzName(listZyTzDTO.get(i).getTzName());
//                                break;
//                            }
//                        }
//                        continue;
//                    }

                    // 用户真实姓名，兼容改版前的日志和改版后的日志
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.ALIAS)) {
                        if (StringUtils.isNotEmpty(dataValueStr)) {
                            log.setAlias(dataValueStr);
                        } else {
                            log.setAlias("未记录");
                        }
                        continue;
                    }

                    // 操作内容（参数）
                    if (paramConfigs.containsKey(dataValue.getKey().toUpperCase())) {
                        if (StringUtils.equalsIgnoreCase(dataValue.getKey(), "before")) {
                            defineParam.put(dataValue.getKey(), RSAEncryptUtils.decrypt(dataValueStr));
                        } else if (StringUtils.equalsIgnoreCase(dataValue.getKey(), "after")) {
                            defineParam.put(dataValue.getKey(), RSAEncryptUtils.decrypt(dataValueStr));
                        } else {
                            defineParam.put(dataValue.getKey(), dataValueStr);
                        }
                        continue;
                    }
                    // 导出内容（参数）
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.DCNR)) {
                        log.setParamCha(dataValueStr);
                        continue;
                    }
                    // 功能名称
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.VIEW_TYPE_NAME)) {
                        if (!eventType.equals("GATEWAY_EVENT")) {
                            log.setViewTypeName(dataValueStr);
                            continue;
                        }
                    }

                    // 功能名称全量日志
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.ACTION)) {
                        log.setViewTypeName(dataValueStr);
                        continue;
                    }

                    // ip
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.IP)) {
                        log.setIp(dataValueStr);
                        continue;
                    }

                    // remoteAddr
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.IP)) {
                        log.setRemoteAddr(dataValueStr);
                        continue;
                    }

                    // clientip
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.CLIENT_IP)) {
                        log.setClientIp(dataValueStr);
                        continue;
                    }

                    // 响应结果
                    if (StringUtils.equals(dataValue.getKey(), CommonConstantUtils.RESPONSE)) {
                        // 转义xml标签
                        if (StringUtils.equals(CommonConstantUtils.LOG_EVENT_PRINT, auditLog.getEvent())) {
                            log.setResponse(dataValue.getValue().replaceAll("<", "&lt")
                                    .replaceAll(">", "&gt"));
                        } else {
                            log.setResponse(dataValue.getValue());
                        }
                    }
                    // 接口请求参数
                    if (StringUtils.equals(dataValue.getKey(), CommonConstantUtils.REQUEST)) {
                        log.setRequest(dataValueStr);
                    }

                    // 综合查询日志 added by zhuyong 20191126
                    // 综合查询查询条件
                    if (StringUtils.equals(dataValue.getKey(), CommonConstantUtils.CXTJ)) {
                        log.setCxtj(dataValueStr);
                    }
                    // 综合查询查询结果
                    if (StringUtils.equals(dataValue.getKey(), CommonConstantUtils.CXJG)) {
                        log.setCxjg(dataValueStr);
                    }
                    // 综合查询IP地址
                    if (StringUtils.equals(dataValue.getKey(), CommonConstantUtils.IPADDRESS)) {
                        log.setIpaddress(dataValueStr);
                    }

                    // 机构部门 added by zhuyong 20191127
                    if (StringUtils.equals(dataValue.getKey(), CommonConstantUtils.ORGANIZATION)) {
                        log.setOrganization(dataValueStr);
                    }
                }
            }
            // 参数都有的时候
            if (activityNameBoolean && processDefinitionNameBoolean
                    && processInstanceIdBoolean && taskIdBoolean) {// 大云
                String cznr = "流程名称：" + processDefinitionName + "；节点名称：" + activityName +
                        "；工作流实例id：" + processInstanceId + "；任务id：" + taskId;
                // if(StringUtils.isNotBlank(reason)){
                cznr += "；删除原因：" + reason;
                // }
                if (StringUtils.isNotBlank(opnion)) {
                    cznr += "；转发意见：" + opnion;
                } else {
                    cznr += "；转发意见：无";
                }

                cznr = cznr.replaceAll("unknown", "无");
                log.setParamCha(cznr);
            } else {// 业务系统
                log.setParam(defineParam.toJSONString());// JSON字符串格式参数
                if (StringUtils.isEmpty(log.getParamCha())) {
                    log.setParamCha(formatEngToCha(paramConfigs, paramConditionConfig, defineParam));
                }
            }
        }
        return log;
    }

    /* version 1.0
     * @description 查询日志重现
     * @date 2019/3/21
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @GetMapping(value = "/query/review/{logid}")
    @ResponseStatus(HttpStatus.OK)
    public Object queryLog(@PathVariable("logid") String logid) throws ParseException {
        AuditLogDto auditLogDto = logMessageClient.getAuditLogDetail(logid);
        if (auditLogDto != null) {
            BdcLogVO log = analysisAuditLogDto(auditLogDto);
            if (log != null && StringUtils.isNotBlank(log.getResponse())) {
                String resStr = log.getResponse();
                if (StringUtils.isNotBlank(resStr) && resStr.indexOf(FRURL) != -1 && resStr.indexOf(DATAURL) != -1) {
                    FR3ListDTO result = new FR3ListDTO();
                    FR3DTO fr = new FR3DTO();
                    if (resStr.indexOf(EPRT) != -1) {
                        fr.setUrl(resStr);
                    } else {
                        fr.setUrl(EPRT + resStr);
                    }
                    result.getFr().add(fr);
                    return result;
                } else {
                    return log.getResponse();
                }
            } else {
                return "该日志未记录结果集！";
            }
        }
        return null;
    }

    @GetMapping(value = "/check/{logid}")
    @ResponseStatus(HttpStatus.OK)
    public Object checkLog(@PathVariable("logid") String logid) throws ParseException {
        // 获取日志信息
        AuditLogDto auditLogDto = logMessageClient.getAuditLogDetail(logid);
        if (auditLogDto == null) {
            return new ResultDataVO(LogCheckEnum.LOG_NOT_FOUND.getMc());
        }
        // 解析平台日志
        BdcLogVO log = analysisAuditLogDto(auditLogDto);
        // 查询结果不存在
        if (StringUtils.isBlank(log.getResponse())) {
            return new ResultDataVO(LogCheckEnum.RESPONSE_NOT_FOUND.getMc());
        }

        if (StringUtils.equalsIgnoreCase(CommonConstantUtils.LOG_EVENT_DOWNLOAD, log.getEventName())
                || StringUtils.equalsIgnoreCase(CommonConstantUtils.LOG_EVENT_EXPORT, log.getEventName())) {
            JSONObject fileObj = JSONObject.parseObject(log.getResponse());
            if (fileObj != null && fileObj.containsKey(CommonConstantUtils.FILE_NAME)
                    && fileObj.containsKey(CommonConstantUtils.FILE_PATH)) {
                // 文件名
                String fileName = fileObj.get(CommonConstantUtils.FILE_NAME).toString();
                // 文件路径
                String filePath = fileObj.get(CommonConstantUtils.FILE_PATH).toString();
                // 文件不存在
                if (!new File(filePath, fileName).exists()) {
                    return new ResultDataVO(LogCheckEnum.FILE_NOT_FOUND.getMc());
                }
            } else {
                return new ResultDataVO(LogCheckEnum.FILE_NOT_FOUND.getMc());
            }
        } else {
            // 台账查询日志
            if (StringUtils.equalsIgnoreCase(CommonConstantUtils.LOG_EVENT_QUERY, log.getEventName())) {
                if (StringUtils.isBlank(log.getViewAddress())) {
                    return new ResultDataVO(LogCheckEnum.ADDRESS_NOT_FOUND.getMc());
                }
                try {
                    JSONObject.parseObject(log.getResponse(), GTAutoConfiguration.DefaultPageImpl.class);
                } catch (Exception e) {
                    return new ResultDataVO(LogCheckEnum.RESPONSE_ERROR.getMc());
                }
            } else if (StringUtils.equalsIgnoreCase(CommonConstantUtils.LOG_EVENT_PRINT, log.getEventName())) {
                // 打印日志
                if (StringUtils.isNotBlank(log.getParam())) {
                    JSONObject param = JSONObject.parseObject(log.getParam());
                    // 打印模板不存在
                    if (!param.containsKey(CommonConstantUtils.FR3_TEMPLAT)) {
                        return new ResultDataVO(LogCheckEnum.TEMPLAT_NOT_FOUND.getMc());
                    }
                }
                try {
                    DocumentHelper.parseText(log.getResponse().replaceAll("&lt", "<")
                            .replaceAll("&gt", ">"));
                } catch (Exception e) {
                    return new ResultDataVO(LogCheckEnum.RESPONSE_ERROR.getMc());
                }

            }
        }
        return new ResultDataVO(true, LogCheckEnum.SUCCESS.getMc());
    }

    /**
     * 保存打印数据源
     *
     * @return
     */
    @PostMapping(value = "/savePrintInfo")
    public void savePrintInfo(BdcPrintLogDTO bplDto) {
        if(bplDto == null) {
            throw new AppException("获取打印日志数据为空！");
        }
        bdcLogFeignService.savaPrintInfo(bplDto);
    }

    /**
     * 打开fr3
     *
     * @return
     */
    @GetMapping(value = "/openFr3/{logid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String openFr3(@PathVariable("logid") String logid) {
        // 平台日志O
        AuditLogDto auditLogDto = logMessageClient.getAuditLogDetail(logid);
        if (auditLogDto != null) {
            return getXmlBylog(auditLogDto);
        } else {
            return "";
        }
    }

    /**
     * 获取xml
     *
     * @param auditLog
     * @return
     */
    private String getXmlBylog(AuditLogDto auditLog) {
        if (auditLog != null) {
            auditLog.getAnnotations();
            List<DataValue> dataValueList = auditLog.getBinaryAnnotations();
            if (CollectionUtils.isNotEmpty(dataValueList)) {
                for (DataValue dataValue : dataValueList) {
                    // 打印数据源xml
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.XMLSTR)) {
                        return dataValue.getValue();
                    }
                }
            }
        }
        return "";
    }

    /**
     * @param logid   日志ID
     * @param keyname 日志数据中指定要获取的KEY
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取指定类型的日志数据
     */
    @GetMapping("/data/{logid}/{keyname}")
    public String getLogData(@PathVariable("logid") String logid, @PathVariable("keyname") String keyname) {
        if (StringUtils.isBlank(logid) || StringUtils.isBlank(keyname)) {
            throw new AppException("获取日志数据异常：未指定要获取的数据类型！");
        }

        // 先获取指定ID的所有日志信息
        AuditLogDto auditLogDto = logMessageClient.getAuditLogDetail(logid);
        if (null == auditLogDto || CollectionUtils.isEmpty(auditLogDto.getBinaryAnnotations())) {
            return null;
        }

        // 过滤出指定KEY值
        for (DataValue dataValue : auditLogDto.getBinaryAnnotations()) {
            if (StringUtils.equalsIgnoreCase(dataValue.getKey(), keyname)) {
                return dataValue.getValue();
            }
        }
        return null;
    }

    /**
     * 根据查询参数查询日志，进行导出
     *
     * @param zhcxLogQO
     * @return string
     * @Date 2020/5/23
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @ResponseBody
    @GetMapping("/export")
    public String exportLog(BdcZhcxLogQO zhcxLogQO, HttpServletResponse response) throws IOException, WriteException, ParseException {

        String fileName = "不动产登记综合查询日志信息导出Excel表" + System.currentTimeMillis() + ".xls";
        if (null != zhcxLogQO) {
            LinkedHashMap exportColMap = new LinkedHashMap<>(16);

            if(null != zhcxLogQO.getExportCol()){
                exportColMap = JSONObject.parseObject(URLDecoder.decode(zhcxLogQO.getExportCol(), "utf-8"), LinkedHashMap.class);
            } else {
                throw new MissingArgumentException("缺少参数");
            }

            //获取页码
            int pageSize = zhcxLogQO.getWpage();
            //获取一页条数
            int pageNumber = zhcxLogQO.getCurrcount();

            List<BdcLogVO> logList = Lists.newArrayList();
            BdcLogVO log = null;
            Long begin = null;
            Long end = null;
            BdcLogQO bdcLogQO = createLogQO(zhcxLogQO);

            if (bdcLogQO.getBeginTime() != null) {
                begin = bdcLogQO.getBeginTime().getTime();
            }
            if (bdcLogQO.getEndTime() != null) {
                end = bdcLogQO.getEndTime().getTime();
            }

            // 组织日志查询参数
            List<QueryLogCondition> conditionList = combineConditionList(bdcLogQO);
            // 平台日志服务 默认查询 查询日志
            Page<AuditLogDto> auditLogDtoPage = logMessageClient.listLogs(pageSize - 1, pageNumber,
                    bdcLogQO.getEventName(),
                    bdcLogQO.getPrincipal(), null, begin, end, conditionList);
            if (auditLogDtoPage.hasContent()) {
                Iterator<AuditLogDto> auditLogIterator = auditLogDtoPage.iterator();
                while (auditLogIterator.hasNext()) {
                    log = analysisAuditLogDto(auditLogIterator.next());
                    if (log != null) {
                        logList.add(log);
                    }
                }
            }
            ExportUtils.exportExcel(fileName, exportColMap, JSON.parseArray(JSON.toJSONString(logList), Map.class), response);
            LOGGER.info("fileName:{}导出成功！",fileName);
        } else {
            throw new MissingArgumentException("缺少参数！");
        }
        return "导出成功！";
    }

    /**
     * 赋值
     *
     * @param zhcxLogQO
     * @return BdcLogQO
     * @Date 2020/5/25
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcLogQO createLogQO(BdcZhcxLogQO zhcxLogQO) {
        BdcLogQO bdcLogQO = new BdcLogQO();
        if (null != zhcxLogQO) {
            bdcLogQO.setEventName(zhcxLogQO.getEventName());
            bdcLogQO.setBeginTime(zhcxLogQO.getBeginTime());
            bdcLogQO.setEndTime(zhcxLogQO.getEndTime());
            bdcLogQO.setPrincipal(zhcxLogQO.getPrincipal());
            bdcLogQO.setAlias(zhcxLogQO.getAlias());
            bdcLogQO.setViewTypeName(zhcxLogQO.getViewTypeName());
            bdcLogQO.setQlr(zhcxLogQO.getQlr());
            bdcLogQO.setSlbh(zhcxLogQO.getSlbh());
            bdcLogQO.setZjh(zhcxLogQO.getZjh());
            bdcLogQO.setCqzh(zhcxLogQO.getCqzh());
            bdcLogQO.setBdcdyh(zhcxLogQO.getBdcdyh());
            bdcLogQO.setResponse(zhcxLogQO.getResponse());
            bdcLogQO.setCxbh(zhcxLogQO.getCxbh());
            bdcLogQO.setZl(zhcxLogQO.getZl());
            bdcLogQO.setCxfs(zhcxLogQO.getCxfs());
            bdcLogQO.setType(zhcxLogQO.getType());
            bdcLogQO.setZmbh(zhcxLogQO.getZmbh());
            bdcLogQO.setPrincipal(zhcxLogQO.getPrincipal());
            bdcLogQO.setIpaddress(zhcxLogQO.getIpaddress());
            bdcLogQO.setOrganization(zhcxLogQO.getOrganization());

        }
        return bdcLogQO;
    }

    /**
     * 保存查询台账查询日志
     * @param bdcXtCxLogDO 日志信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping("/xtcx")
    public void saveBdcCxLog(@RequestBody BdcXtCxLogDO bdcXtCxLogDO, HttpServletRequest request) {
        if(StringUtils.isBlank(bdcXtCxLogDO.getDlip())){
            bdcXtCxLogDO.setDlip(IPUtils.getRequestClientRealIP(request));
        }
        bdcLogFeignService.saveBdcCxLog(bdcXtCxLogDO);
    }

    /**
     * 保存综合查询台账查询和打印操作日志
     * @param bdcZhcxLogDO 日志信息
     */
    @PostMapping("/zhcx")
    public void saveBdcZhcxLog(@RequestBody BdcZhcxLogDO bdcZhcxLogDO) {
        bdcZhcxFeignService.insertZhcxLog(bdcZhcxLogDO);
    }

    /**
     * 查询本地保存的综合查询日志
     * @param pageable pageable
     * @param bdcXtCxLogCxtj 查询条件
     * @return return
     */
    @GetMapping(value = "/xtcx/list")
    @ResponseStatus(HttpStatus.OK)
    public Object xtcxLogListByPage(@LayuiPageable Pageable pageable,@RequestParam("bdcXtCxLogCxtj") String bdcXtCxLogCxtj) {
        Page<BdcXtCxLogDO> pageResponseDTOS = bdcLogFeignService.bdcCxZhcxLogByPage(pageable, bdcXtCxLogCxtj);
        return super.addLayUiCode(pageResponseDTOS);
    }

    /**
     * 综合查询页面日志导出
     *
     * @param bdcZhcxLogQO 分页数据和字段名
     * @param response response
     * @param title 文件名称
     * @param bdcXtCxLogCxtj 查询条件
     */
    @PostMapping("/xtxclog/export")
    public void exportExcel(BdcZhcxLogQO bdcZhcxLogQO,HttpServletResponse response,@RequestParam("title")String title,@RequestParam("bdcXtCxLogCxtj") String bdcXtCxLogCxtj) {
        if(null == bdcZhcxLogQO){
            throw new AppException("综合查询日志导出数据为空!");
        }
        // 数据库查询是从第0页开始的 所以页面的数值需要减1
        Pageable pageable = new PageRequest(bdcZhcxLogQO.getWpage()-1,bdcZhcxLogQO.getCurrcount());
        List<List<String>> excelList = JSONObject.parseObject(bdcZhcxLogQO.getExportCol(),new TypeReference<List<List<String>>>(){});
        Page<BdcXtCxLogDO> pageResponseDTOS = bdcLogFeignService.bdcCxZhcxLogByPage(pageable, bdcXtCxLogCxtj);
        List<BdcXtCxLogDO> excelDateList = pageResponseDTOS.getContent();
        List<List<String>> dataList = new ArrayList<>();
        // 添加第一列
        dataList.add(excelList.get(0));
        // 取出excel第一行的名称
        List<String> firstRow = excelList.get(1);
        SimpleDateFormat sdf = new SimpleDateFormat("");
        for (int i = 0; i < excelDateList.size(); i++){
            List<String> temp = new ArrayList<>();
            Map<String,String> map = JSONObject.parseObject(JSONObject.toJSONString(excelDateList.get(i)), Map.class);
            for (int j = 0; j < firstRow.size(); j++){
                if("czsj".equals(firstRow.get(j))){
                    temp.add(sdf.format(excelDateList.get(i).getCzsj()));
                }else{
                    temp.add(StringUtils.isEmpty(map.get(firstRow.get(j)))?"":map.get(firstRow.get(j)));
                }
            }
            dataList.add(temp);
        }
        try{
            ExcelUtil.simpleExport(response,dataList,title);
        }catch (Exception e){
            throw new AppException("导出数据错误!");
        }
    }

    /**
     * 根据Id获取本地综合日志查询结果
     * @param id key
     * @param keyname 查询结果
     * @return return
     */
    @GetMapping("/xtcxdata/{id}/{keyname}")
    public String getXtcxLogData(@PathVariable("id") String id, @PathVariable("keyname") String keyname) {
        if (StringUtils.isBlank(id) || StringUtils.isBlank(keyname)) {
            throw new AppException("获取日志数据异常：未指定要获取的数据类型！");
        }

        BdcXtCxLogDO bdcXtCxLogDO = bdcLogFeignService.bdcCxZhcxLogById(id);
        Map<String,String> map = JSONObject.parseObject(JSONObject.toJSONString(bdcXtCxLogDO), Map.class);
        if(StringUtils.isNotEmpty(map.get(keyname))){
            return map.get(keyname);
        }
        return null;
    }

    /**
     * 日志分页查询
     * @param pageable 分页参数
     * @param bdcLogQO 查询参数
     * @return 分页数据
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/v2/list")
    @ResponseStatus(HttpStatus.OK)
    public Object logListByPageV2(@LayuiPageable Pageable pageable, BdcLogQO bdcLogQO) {
        if(Objects.nonNull(bdcLogQO) && StringUtils.isNotBlank(bdcLogQO.getCondition())){
            // 对查询条件进行解密处理
            bdcLogQO.setCondition(StringToolUtils.replaceBracket(new String(Base64Utils.decodeBase64StrToByte(bdcLogQO.getCondition()))));
        }
        if (Boolean.TRUE.equals(bdcLogQO.getDctspz())) {
            pageable = new PageRequest(0, dcts);
            bdcLogQO.setDctspz(null);
        }
        // 组织日志查询参数
        List<QueryLogCondition> conditionList = this.handlerQueryParam(bdcLogQO.getCondition());

        Page<AuditLogDto> auditLogDtoPage = logMessageClient.listLogs(pageable.getPageNumber(), pageable.getPageSize(),
                bdcLogQO.getEventName(), bdcLogQO.getPrincipal(), null,
                this.resloveTime(bdcLogQO.getBeginTime()), this.resloveTime(bdcLogQO.getEndTime()), conditionList);

        List<Map<String,Object>> logPageList = new ArrayList<>(auditLogDtoPage.getSize());
        if (auditLogDtoPage.hasContent()) {
            Iterator<AuditLogDto> auditLogIterator = auditLogDtoPage.iterator();
            while (auditLogIterator.hasNext()) {
                logPageList.add(this.handlerPageContent(auditLogIterator.next()));
            }
        }
        return super.addLayUiCode(new GTAutoConfiguration.DefaultPageImpl<>(logPageList, pageable.getPageNumber(),
                pageable.getPageSize(), auditLogDtoPage.getTotalElements()));
    }

    /**
     * 根据 LogEsCondition 中配置当前类型查询
     */
    private List<QueryLogCondition> handlerQueryParam(String condition){
        if(StringUtils.isNotBlank(condition)){
            List<QueryLogCondition> list = JSON.parseArray(condition, QueryLogCondition.class);
            list = list.stream().filter(t-> !(Arrays.asList(ES_EXCLUDE_CONDITION).contains(t.getKey())))
                    .collect(Collectors.toList());
            return list;
        }
        return new ArrayList<>();
    }

    /**
     * 将记录在 es 中的日志参数，转换为Map数据进行返回
     */
    private Map handlerPageContent(AuditLogDto auditLog){
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", auditLog.getId());
        resultMap.put("event", auditLog.getEvent());
        resultMap.put("principal", auditLog.getPrincipal());
        resultMap.put("name", auditLog.getName());
        resultMap.put("time", auditLog.getTimestamp_millis());

        if(CollectionUtils.isNotEmpty(auditLog.getBinaryAnnotations())){
            for(DataValue dataValue : auditLog.getBinaryAnnotations()){
                resultMap.put(dataValue.getKey(), dataValue.getValue());
            }
        }
        // 信息补录日志内容解密
        if(StringUtils.isNotBlank(auditLog.getEvent()) && "XXBL".equals(auditLog.getEvent())){
            if(resultMap.containsKey("before")){
                resultMap.put("before", RSAEncryptUtils.decrypt((String) resultMap.get("before")));
            }
            if(resultMap.containsKey("after")){
                resultMap.put("after", RSAEncryptUtils.decrypt((String) resultMap.get("after")));
            }
            if(resultMap.containsKey("change")){
                resultMap.put("change", RSAEncryptUtils.decrypt((String) resultMap.get("change")));
            }
        }
        return resultMap;
    }

    // 处理时间
    private Long resloveTime(Date date){
        Long time = null;
        if (null != date) {
            time = date.getTime();
        }
        return time;
    }

    /**
     * 规则验证日志分组分页查询
     * <p>规则日志按照 验证标识 分组查询</p>
     * @param pageable  分页参数
     * @param bdcGzYzLogQO  规则验证日志QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 规则日志分组分页数据
     */
    @GetMapping(value = "/list/group/gzyz")
    @ResponseStatus(HttpStatus.OK)
    public Object logListGzyzGroup(@LayuiPageable Pageable pageable, BdcGzYzLogQO bdcGzYzLogQO) {
        if (Boolean.TRUE.equals(bdcGzYzLogQO.getDctspz())) {
            pageable = new PageRequest(0, dcts);
            bdcGzYzLogQO.setDctspz(null);
        }
        Page<BdcGzYzLogDTO> bdcGzyzLogDOPage = bdcGzYzLogFeignService.listBdcGzYzLogGroupPage(pageable, JSON.toJSONString(bdcGzYzLogQO));
        return super.addLayUiCode(bdcGzyzLogDOPage);
    }

    /**
     * 规则验证日志分页查询
     * @param pageable  分页参数
     * @param bdcGzYzLogQO  规则验证日志QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 规则日志分页数据
     */
    @GetMapping(value = "/list/gzyz")
    @ResponseStatus(HttpStatus.OK)
    public Object logListGzyz(@LayuiPageable Pageable pageable, BdcGzYzLogQO bdcGzYzLogQO) {
        if (Boolean.TRUE.equals(bdcGzYzLogQO.getDctspz())) {
            pageable = new PageRequest(0, dcts);
            bdcGzYzLogQO.setDctspz(null);
        }
        Page<BdcGzYzLogDTO> bdcGzyzLogDOPage = bdcGzYzLogFeignService.listBdcGzYzLogPage(pageable, JSON.toJSONString(bdcGzYzLogQO));
        return super.addLayUiCode(bdcGzyzLogDOPage);
    }

    /**
     * 对加密的内容进行解密
     * @param encryptMsg 加密内容
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 解密信息
     */
    @PostMapping(value = "/list/decrypt")
    @ResponseStatus(HttpStatus.OK)
    public String decryptMsg(@RequestBody String encryptMsg) {
        return RSAEncryptUtils.decrypt(encryptMsg);
    }

    /**
     * @description 获取综合查询日志配置项,excel导出全部的导出条数
     * @return
     */
    @GetMapping("/zhcxLog/pz")
    public Integer getZhcxLogPz(){
        return dcts;
    }

}
