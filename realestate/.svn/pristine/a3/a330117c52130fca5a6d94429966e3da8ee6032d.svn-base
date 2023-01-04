package cn.gtmap.realestate.natural.ui.web.rest;

import cn.gtmap.gtc.clients.LogMessageClient;
import cn.gtmap.gtc.sso.domain.dto.AuditLogDto;
import cn.gtmap.gtc.sso.domain.dto.DataValue;
import cn.gtmap.gtc.sso.domain.dto.QueryLogCondition;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.starter.gcas.config.GTAutoConfiguration;
import cn.gtmap.realestate.common.config.logaop.LogParamsTslConstants;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyXtCxLogDO;
import cn.gtmap.realestate.common.core.dto.natural.FR3DTO;
import cn.gtmap.realestate.common.core.dto.natural.FR3ListDTO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyPrintLogDTO;
import cn.gtmap.realestate.common.core.dto.natural.ZyTzDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.natural.ZrzyLogQO;
import cn.gtmap.realestate.common.core.qo.natural.ZrzyZhcxLogQO;
import cn.gtmap.realestate.common.core.service.feign.natural.ZrzyLogFeignService;
import cn.gtmap.realestate.common.core.vo.natural.ResultDataVO;
import cn.gtmap.realestate.common.core.vo.natural.ZrzyLogVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.RSAEncryptUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.common.util.office.ExcelUtil;
import cn.gtmap.realestate.natural.ui.util.ExportUtils;
import cn.gtmap.realestate.natural.ui.util.LogCheckEnum;
import cn.gtmap.realestate.natural.ui.web.main.BaseController;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2019/1/11
 * @description
 */
@RestController
@RequestMapping(value = "/log")
public class ZrzyLogController extends BaseController {
    @Autowired
    private LogMessageClient logMessageClient;
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private ZrzyLogFeignService zrzyLogFeignService;

    public static final String FRURL = "frURL";
    public static final String DATAURL = "dataURL";
    public static final String EPRT = "eprt:";

    @GetMapping(value = "/list")
    @ResponseStatus(HttpStatus.OK)
    public Object logListByPage(@LayuiPageable Pageable pageable, ZrzyLogQO zrzyLogQO) {
        int pageSize = pageable.getPageSize();
        List<ZrzyLogVO> logList = Lists.newArrayList();
        ZrzyLogVO log = null;
        Long begin = null;
        Long end = null;
        if (zrzyLogQO.getBeginTime() != null) {
            begin = zrzyLogQO.getBeginTime().getTime();
        }
        if (zrzyLogQO.getEndTime() != null) {
            end = zrzyLogQO.getEndTime().getTime();
        }
        // 组织日志查询参数
        List<QueryLogCondition> conditionList = combineConditionList(zrzyLogQO);
        // 平台日志服务 默认查询 查询日志
        Page<AuditLogDto> auditLogDtoPage = logMessageClient.listLogs(pageable.getPageNumber(), pageSize,
                zrzyLogQO.getEventName(),
                zrzyLogQO.getPrincipal(), null, begin, end, conditionList);
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
     * @param zrzyLogQO 查询条件
     * @description 组织日志查询参数条件
     * @date 2019/3/15
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    private List<QueryLogCondition> combineConditionList(ZrzyLogQO zrzyLogQO) {
        List<QueryLogCondition> conditionList = Lists.newArrayList();
        QueryLogCondition queryLogCondition = null;
        Object value = null;
        if (zrzyLogQO != null) {
            Field[] fields = zrzyLogQO.getClass().getDeclaredFields();
            // 默認模糊查詢
            String type = StringUtils.isNotBlank(zrzyLogQO.getCxfs()) ? zrzyLogQO.getCxfs() : CommonConstantUtils.TYPE_LIKE;
            //对查询项：查询条件，查询结果进行转义
            if (StringUtils.isNotBlank(zrzyLogQO.getCxtj())) {
                zrzyLogQO.setCxtj(stringTransferMeaning(zrzyLogQO.getCxtj()));
            }
            if (StringUtils.isNotBlank(zrzyLogQO.getCxjg())) {
                zrzyLogQO.setCxjg(stringTransferMeaning(zrzyLogQO.getCxjg()));
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
                        value = fields[i].get(zrzyLogQO);
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
                            } else if (StringUtils.equalsIgnoreCase(CommonConstantUtils.QLR, fields[i].getName())) {
                                queryLogCondition.setKey(CommonConstantUtils.QLR);
                            } else if (StringUtils.equalsIgnoreCase(CommonConstantUtils.CXTJ, fields[i].getName())) {
                                queryLogCondition.setKey(CommonConstantUtils.CXTJ);
                            } else if (StringUtils.equalsIgnoreCase(CommonConstantUtils.CXJG, fields[i].getName())) {
                                queryLogCondition.setKey(CommonConstantUtils.CXJG);
                            } else {
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
        param = param.replace("\\", "\\\\\\\\");
        param = param.replace("\"", "\\\"");
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
    private ZrzyLogVO analysisAuditLogDto(AuditLogDto auditLog) {
        ZrzyLogVO log = null;
        Map<String, String> paramConfigs = LogParamsTslConstants.PARAM;
        Map<String, Map<String, String>> paramConditionConfig = LogParamsTslConstants.PARAMCONDITION;
        List<ZyTzDTO> listZyTzDTO = new ArrayList<>();
        if (auditLog != null) {
            JSONObject defineParam = new JSONObject();
            String eventType = auditLog.getEvent();
            log = new ZrzyLogVO();
            log.setId(auditLog.getId());
            log.setTime(auditLog.getTimestamp_millis());
            log.setEventName(eventType);
            log.setRequestUrl(auditLog.getName());
            if (StringUtils.isNotEmpty(auditLog.getPrincipal())) {
                log.setPrincipal(auditLog.getPrincipal());
            } else {
                log.setPrincipal("未记录");
            }
            // 用户真实姓名，兼容改版前的日志和改版后的日志
            if (StringUtils.isNotEmpty(auditLog.getPrincipal())) {
                UserDto userDto = userManagerUtils.getUserByName(auditLog.getPrincipal());
                if (userDto != null) {
                    log.setAlias(userDto.getAlias());
                } else {
                    log.setAlias("该用户不存在");
                }
            } else {
                log.setAlias("未记录");
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
    public Object queryLog(@PathVariable("logid") String logid) {
        AuditLogDto auditLogDto = logMessageClient.getAuditLogDetail(logid);
        if (auditLogDto != null) {
            ZrzyLogVO log = analysisAuditLogDto(auditLogDto);
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
    public Object checkLog(@PathVariable("logid") String logid) {
        // 获取日志信息
        AuditLogDto auditLogDto = logMessageClient.getAuditLogDetail(logid);
        if (auditLogDto == null) {
            return new ResultDataVO(LogCheckEnum.LOG_NOT_FOUND.getMc());
        }
        // 解析平台日志
        ZrzyLogVO log = analysisAuditLogDto(auditLogDto);
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
    public void savePrintInfo(ZrzyPrintLogDTO bplDto) {
        zrzyLogFeignService.savaPrintInfo(JSON.toJSONString(bplDto));
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
    public String exportLog(ZrzyZhcxLogQO zhcxLogQO, HttpServletResponse response) throws IOException, WriteException {

        String fileName = "不动产登记综合查询日志信息导出Excel表" + System.currentTimeMillis() + ".xls";
        if (null != zhcxLogQO) {
            LinkedHashMap exportColMap = new LinkedHashMap<>(16);

            if (null != zhcxLogQO.getExportCol()) {
                exportColMap = JSONObject.parseObject(URLDecoder.decode(zhcxLogQO.getExportCol(), "utf-8"), LinkedHashMap.class);
            } else {
                throw new MissingArgumentException("缺少参数");
            }

            //获取页码
            int pageSize = zhcxLogQO.getWpage();
            //获取一页条数
            int pageNumber = zhcxLogQO.getCurrcount();

            List<ZrzyLogVO> logList = Lists.newArrayList();
            ZrzyLogVO log = null;
            Long begin = null;
            Long end = null;
            ZrzyLogQO zrzyLogQO = createLogQO(zhcxLogQO);

            if (zrzyLogQO.getBeginTime() != null) {
                begin = zrzyLogQO.getBeginTime().getTime();
            }
            if (zrzyLogQO.getEndTime() != null) {
                end = zrzyLogQO.getEndTime().getTime();
            }

            // 组织日志查询参数
            List<QueryLogCondition> conditionList = combineConditionList(zrzyLogQO);
            // 平台日志服务 默认查询 查询日志
            Page<AuditLogDto> auditLogDtoPage = logMessageClient.listLogs(pageSize - 1, pageNumber,
                    zrzyLogQO.getEventName(),
                    zrzyLogQO.getPrincipal(), null, begin, end, conditionList);
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
            LOGGER.info("fileName:{}导出成功！", fileName);
        } else {
            throw new MissingArgumentException("缺少参数！");
        }
        return "导出成功！";
    }

    /**
     * 赋值
     *
     * @param zhcxLogQO
     * @return ZrzyLogQO
     * @Date 2020/5/25
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public ZrzyLogQO createLogQO(ZrzyZhcxLogQO zhcxLogQO) {
        ZrzyLogQO zrzyLogQO = new ZrzyLogQO();
        if (null != zhcxLogQO) {
            zrzyLogQO.setEventName(zhcxLogQO.getEventName());
            zrzyLogQO.setBeginTime(zhcxLogQO.getBeginTime());
            zrzyLogQO.setEndTime(zhcxLogQO.getEndTime());
            zrzyLogQO.setPrincipal(zhcxLogQO.getPrincipal());
            zrzyLogQO.setAlias(zhcxLogQO.getAlias());
            zrzyLogQO.setViewTypeName(zhcxLogQO.getViewTypeName());
            zrzyLogQO.setQlr(zhcxLogQO.getQlr());
            zrzyLogQO.setSlbh(zhcxLogQO.getSlbh());
            zrzyLogQO.setZjh(zhcxLogQO.getZjh());
            zrzyLogQO.setCqzh(zhcxLogQO.getCqzh());
            zrzyLogQO.setBdcdyh(zhcxLogQO.getBdcdyh());
            zrzyLogQO.setResponse(zhcxLogQO.getResponse());
            zrzyLogQO.setCxbh(zhcxLogQO.getCxbh());
            zrzyLogQO.setZl(zhcxLogQO.getZl());
            zrzyLogQO.setCxfs(zhcxLogQO.getCxfs());
            zrzyLogQO.setType(zhcxLogQO.getType());
            zrzyLogQO.setZmbh(zhcxLogQO.getZmbh());
            zrzyLogQO.setPrincipal(zhcxLogQO.getPrincipal());
            zrzyLogQO.setIpaddress(zhcxLogQO.getIpaddress());
            zrzyLogQO.setOrganization(zhcxLogQO.getOrganization());

        }
        return zrzyLogQO;
    }

    /**
     * 保存查询台账查询日志
     *
     * @param zrzyXtCxLogDO 日志信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping("/xtcx")
    public void saveZrzyCxLog(@RequestBody ZrzyXtCxLogDO zrzyXtCxLogDO) {
        zrzyLogFeignService.saveZrzyCxLog(zrzyXtCxLogDO);
    }

    /**
     * 查询本地保存的综合查询日志
     *
     * @param pageable       pageable
     * @param zrzyXtCxLogCxtj 查询条件
     * @return return
     */
    @GetMapping(value = "/xtcx/list")
    @ResponseStatus(HttpStatus.OK)
    public Object xtcxLogListByPage(@LayuiPageable Pageable pageable, @RequestParam("zrzyXtCxLogCxtj") String zrzyXtCxLogCxtj) {
        Page<ZrzyXtCxLogDO> pageResponseDTOS = zrzyLogFeignService.zrzyCxZhcxLogByPage(pageable, zrzyXtCxLogCxtj);
        return super.addLayUiCode(pageResponseDTOS);
    }

    /**
     * 综合查询页面日志导出
     *
     * @param zrzyZhcxLogQO   分页数据和字段名
     * @param response       response
     * @param title          文件名称
     * @param zrzyXtCxLogCxtj 查询条件
     */
    @PostMapping("/xtxclog/export")
    public void exportExcel(ZrzyZhcxLogQO zrzyZhcxLogQO, HttpServletResponse response, @RequestParam("title") String title, @RequestParam("zrzyXtCxLogCxtj") String zrzyXtCxLogCxtj) {
        if (null == zrzyZhcxLogQO) {
            throw new AppException("综合查询日志导出数据为空!");
        }
        // 数据库查询是从第0页开始的 所以页面的数值需要减1
        Pageable pageable = new PageRequest(zrzyZhcxLogQO.getWpage() - 1, zrzyZhcxLogQO.getCurrcount());
        List<List<String>> excelList = JSONObject.parseObject(zrzyZhcxLogQO.getExportCol(), new TypeReference<List<List<String>>>() {
        });
        Page<ZrzyXtCxLogDO> pageResponseDTOS = zrzyLogFeignService.zrzyCxZhcxLogByPage(pageable, zrzyXtCxLogCxtj);
        List<ZrzyXtCxLogDO> excelDateList = pageResponseDTOS.getContent();
        List<List<String>> dataList = new ArrayList<>();
        // 添加第一列
        dataList.add(excelList.get(0));
        // 取出excel第一行的名称
        List<String> firstRow = excelList.get(1);
        SimpleDateFormat sdf = new SimpleDateFormat("");
        for (int i = 0; i < excelDateList.size(); i++) {
            List<String> temp = new ArrayList<>();
            Map<String, String> map = JSONObject.parseObject(JSONObject.toJSONString(excelDateList.get(i)), Map.class);
            for (int j = 0; j < firstRow.size(); j++) {
                if ("czsj".equals(firstRow.get(j))) {
                    temp.add(sdf.format(excelDateList.get(i).getCzsj()));
                } else {
                    temp.add(StringUtils.isEmpty(map.get(firstRow.get(j))) ? "" : map.get(firstRow.get(j)));
                }
            }
            dataList.add(temp);
        }
        try {
            ExcelUtil.simpleExport(response, dataList, title);
        } catch (Exception e) {
            throw new AppException("导出数据错误!");
        }
    }

    /**
     * 根据Id获取本地综合日志查询结果
     *
     * @param id      key
     * @param keyname 查询结果
     * @return return
     */
    @GetMapping("/xtcxdata/{id}/{keyname}")
    public String getXtcxLogData(@PathVariable("id") String id, @PathVariable("keyname") String keyname) {
        if (StringUtils.isBlank(id) || StringUtils.isBlank(keyname)) {
            throw new AppException("获取日志数据异常：未指定要获取的数据类型！");
        }

        ZrzyXtCxLogDO zrzyXtCxLogDO = zrzyLogFeignService.zrzyCxZhcxLogById(id);
        Map<String, String> map = JSONObject.parseObject(JSONObject.toJSONString(zrzyXtCxLogDO), Map.class);
        if (StringUtils.isNotEmpty(map.get(keyname))) {
            return map.get(keyname);
        }
        return null;
    }
}
