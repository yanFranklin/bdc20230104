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
        // ????????????????????????
        List<QueryLogCondition> conditionList = combineConditionList(zrzyLogQO);
        // ?????????????????? ???????????? ????????????
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
     * @param zrzyLogQO ????????????
     * @description ??????????????????????????????
     * @date 2019/3/15
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    private List<QueryLogCondition> combineConditionList(ZrzyLogQO zrzyLogQO) {
        List<QueryLogCondition> conditionList = Lists.newArrayList();
        QueryLogCondition queryLogCondition = null;
        Object value = null;
        if (zrzyLogQO != null) {
            Field[] fields = zrzyLogQO.getClass().getDeclaredFields();
            // ??????????????????
            String type = StringUtils.isNotBlank(zrzyLogQO.getCxfs()) ? zrzyLogQO.getCxfs() : CommonConstantUtils.TYPE_LIKE;
            //??????????????????????????????????????????????????????
            if (StringUtils.isNotBlank(zrzyLogQO.getCxtj())) {
                zrzyLogQO.setCxtj(stringTransferMeaning(zrzyLogQO.getCxtj()));
            }
            if (StringUtils.isNotBlank(zrzyLogQO.getCxjg())) {
                zrzyLogQO.setCxjg(stringTransferMeaning(zrzyLogQO.getCxjg()));
            }
            try {
                for (int i = 0; i < fields.length; i++) {
                    // ???????????????????????????????????? ???????????????????????????
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
                                 * ?????????????????? ???????????????????????????RESPONSE??????
                                 *  ?????????????????????????????????????????????????????????RESPONSE_PARAM_KEY???
                                 *  ???????????????RESPONSE_PARAM_KEY?????????
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
                                // ?????????????????????????????????????????? ????????????
                                queryLogCondition.setKey(CommonConstantUtils.PARAM_SUB + fields[i].getName());
                            }
                            queryLogCondition.setValue(value.toString());
                            queryLogCondition.setType(type);
                            conditionList.add(queryLogCondition);
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("??????????????????", e);
            }
        }
        return conditionList;
    }

    /**
     * version 1.0
     *
     * @return
     * @description ??????????????????\???"??????????????????
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
     * @description ??????????????????????????????
     * @date 2019/3/15
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    private String formatEngToCha(Map<String, String> paramConfig, Map<String, Map<String, String>> paramConditionConfig, JSONObject param) {
        StringBuilder result = new StringBuilder();
        if (param != null && MapUtils.isNotEmpty(paramConfig) && MapUtils.isNotEmpty(paramConditionConfig)) {
            for (Map.Entry<String, Object> entry : param.entrySet()) {
                String key = entry.getKey();
                if (paramConfig.containsKey(key.toUpperCase())) {
                    // ????????????
                    if (paramConditionConfig.containsKey(key.toUpperCase()) &&
                            paramConditionConfig.get(key.toUpperCase()).containsKey(param.get(key))) {
                        result.append(paramConfig.get(key.toUpperCase())).append("???").
                                append(paramConditionConfig.get(key.toUpperCase()).get(param.get(key))).append(CommonConstantUtils.separate);
                    } else {
                        result.append(paramConfig.get(key.toUpperCase())).append("???").append(param.get(key)).append(CommonConstantUtils.separate);
                    }
                }
            }

            /**
             * ???????????????????????????
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
     * @param auditLog ??????????????????
     * @return
     * @description ??????????????????????????????
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
                log.setPrincipal("?????????");
            }
            // ??????????????????????????????????????????????????????????????????
            if (StringUtils.isNotEmpty(auditLog.getPrincipal())) {
                UserDto userDto = userManagerUtils.getUserByName(auditLog.getPrincipal());
                if (userDto != null) {
                    log.setAlias(userDto.getAlias());
                } else {
                    log.setAlias("??????????????????");
                }
            } else {
                log.setAlias("?????????");
            }
            List<DataValue> dataValueList = auditLog.getBinaryAnnotations();
            boolean activityNameBoolean = false;
            boolean processDefinitionNameBoolean = false;
            boolean processInstanceIdBoolean = false;
            boolean taskIdBoolean = false;
            String reason = "";
            String opnion = "";
            String taskId = "";//??????id
            String activityName = "";//????????????
            String processDefinitionName = "";//????????????
            String processInstanceId = "";//??????id
            if (CollectionUtils.isNotEmpty(dataValueList)) {
                for (DataValue dataValue : dataValueList) {
                    String dataValueStr = dataValue.getValue();
                    if (dataValueStr == null) {
                        dataValueStr = "";
                    }
                    dataValueStr = dataValueStr.equalsIgnoreCase("unknown") ? "" : dataValueStr;
                    // ??????????????????
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.MODELURS)) {
                        log.setModelUrl(dataValueStr);
                        continue;
                    }
                    // ?????????????????????????????????????????????
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.DATAURL)) {
                        log.setDataUrl(dataValueStr);
                        continue;
                    }
                    // ???????????????xml????????????????????????
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.XMLSTR)) {
                        log.setXmlStr(dataValueStr);
                        continue;
                    }

                    // ????????????????????????????????????
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.PRINTTYPE)) {
                        log.setPrintType(dataValueStr);
                        continue;
                    }

                    // ????????????????????????????????????
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.ZMBH)) {
                        log.setZmbh(dataValue.getValue());
                        continue;
                    }

                    // ????????????
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.SLBH)) {
                        log.setSlbh(dataValueStr);
                        continue;
                    }

                    // ??????????????????
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.BDCDYH)) {
                        log.setBdcdyh(dataValueStr);
                        continue;
                    }
                    // ?????????
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.YWR)) {
                        log.setYwr(dataValueStr);
                        continue;
                    }
                    // ??????
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.ZL)) {
                        log.setZl(dataValueStr);
                        continue;
                    }
                    // ?????????
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.QLRMC) || StringUtils
                            .equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.QLR)) {
                        log.setQlr(dataValueStr);
                        continue;
                    }
                    // ????????????
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.PROCESSDEFINITIONNAME)) {
                        processDefinitionNameBoolean = true;
                        processDefinitionName = dataValueStr;
                        continue;
                    }

                    // ????????????
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.DELETEREASON)) {
                        reason = dataValueStr;
                        continue;
                    }

                    // ????????????
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.ZFOPNION)) {
                        opnion = dataValueStr;
                        continue;
                    }
                    // ??????id
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
                    // ????????????
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.ACTIVITYNAME)) {
                        activityNameBoolean = true;
                        activityName = dataValueStr;
                        continue;
                    }

                    // ???????????? ?????????
//                    if(StringUtils.equalsIgnoreCase(dataValue.getKey(),CommonConstantUtils.CONTROLLER_METHOD_NAME)) {
//                        String methodName = dataValue.getValue();
//                        log.setZyTzName("????????????");
//                        for (int i=0;i<listZyTzDTO.size();i++) {
//                            if(listZyTzDTO.get(i).getTzType().equals(methodName)){
//                                log.setZyTzName(listZyTzDTO.get(i).getTzName());
//                                break;
//                            }
//                        }
//                        continue;
//                    }

                    // ??????????????????????????????????????????????????????????????????
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.ALIAS)) {
                        if (StringUtils.isNotEmpty(dataValueStr)) {
                            log.setAlias(dataValueStr);
                        } else {
                            log.setAlias("?????????");
                        }
                        continue;
                    }

                    // ????????????????????????
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
                    // ????????????????????????
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.DCNR)) {
                        log.setParamCha(dataValueStr);
                        continue;
                    }
                    // ????????????
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.VIEW_TYPE_NAME)) {
                        if (!eventType.equals("GATEWAY_EVENT")) {
                            log.setViewTypeName(dataValueStr);
                            continue;
                        }
                    }

                    // ????????????????????????
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

                    // ????????????
                    if (StringUtils.equals(dataValue.getKey(), CommonConstantUtils.RESPONSE)) {
                        // ??????xml??????
                        if (StringUtils.equals(CommonConstantUtils.LOG_EVENT_PRINT, auditLog.getEvent())) {
                            log.setResponse(dataValue.getValue().replaceAll("<", "&lt")
                                    .replaceAll(">", "&gt"));
                        } else {
                            log.setResponse(dataValue.getValue());
                        }
                    }
                    // ??????????????????
                    if (StringUtils.equals(dataValue.getKey(), CommonConstantUtils.REQUEST)) {
                        log.setRequest(dataValueStr);
                    }

                    // ?????????????????? added by zhuyong 20191126
                    // ????????????????????????
                    if (StringUtils.equals(dataValue.getKey(), CommonConstantUtils.CXTJ)) {
                        log.setCxtj(dataValueStr);
                    }
                    // ????????????????????????
                    if (StringUtils.equals(dataValue.getKey(), CommonConstantUtils.CXJG)) {
                        log.setCxjg(dataValueStr);
                    }
                    // ????????????IP??????
                    if (StringUtils.equals(dataValue.getKey(), CommonConstantUtils.IPADDRESS)) {
                        log.setIpaddress(dataValueStr);
                    }

                    // ???????????? added by zhuyong 20191127
                    if (StringUtils.equals(dataValue.getKey(), CommonConstantUtils.ORGANIZATION)) {
                        log.setOrganization(dataValueStr);
                    }
                }
            }
            // ?????????????????????
            if (activityNameBoolean && processDefinitionNameBoolean
                    && processInstanceIdBoolean && taskIdBoolean) {// ??????
                String cznr = "???????????????" + processDefinitionName + "??????????????????" + activityName +
                        "??????????????????id???" + processInstanceId + "?????????id???" + taskId;
                // if(StringUtils.isNotBlank(reason)){
                cznr += "??????????????????" + reason;
                // }
                if (StringUtils.isNotBlank(opnion)) {
                    cznr += "??????????????????" + opnion;
                } else {
                    cznr += "?????????????????????";
                }

                cznr = cznr.replaceAll("unknown", "???");
                log.setParamCha(cznr);
            } else {// ????????????
                log.setParam(defineParam.toJSONString());// JSON?????????????????????
                if (StringUtils.isEmpty(log.getParamCha())) {
                    log.setParamCha(formatEngToCha(paramConfigs, paramConditionConfig, defineParam));
                }
            }
        }
        return log;
    }

    /* version 1.0
     * @description ??????????????????
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
                return "??????????????????????????????";
            }
        }
        return null;
    }

    @GetMapping(value = "/check/{logid}")
    @ResponseStatus(HttpStatus.OK)
    public Object checkLog(@PathVariable("logid") String logid) {
        // ??????????????????
        AuditLogDto auditLogDto = logMessageClient.getAuditLogDetail(logid);
        if (auditLogDto == null) {
            return new ResultDataVO(LogCheckEnum.LOG_NOT_FOUND.getMc());
        }
        // ??????????????????
        ZrzyLogVO log = analysisAuditLogDto(auditLogDto);
        // ?????????????????????
        if (StringUtils.isBlank(log.getResponse())) {
            return new ResultDataVO(LogCheckEnum.RESPONSE_NOT_FOUND.getMc());
        }

        if (StringUtils.equalsIgnoreCase(CommonConstantUtils.LOG_EVENT_DOWNLOAD, log.getEventName())
                || StringUtils.equalsIgnoreCase(CommonConstantUtils.LOG_EVENT_EXPORT, log.getEventName())) {
            JSONObject fileObj = JSONObject.parseObject(log.getResponse());
            if (fileObj != null && fileObj.containsKey(CommonConstantUtils.FILE_NAME)
                    && fileObj.containsKey(CommonConstantUtils.FILE_PATH)) {
                // ?????????
                String fileName = fileObj.get(CommonConstantUtils.FILE_NAME).toString();
                // ????????????
                String filePath = fileObj.get(CommonConstantUtils.FILE_PATH).toString();
                // ???????????????
                if (!new File(filePath, fileName).exists()) {
                    return new ResultDataVO(LogCheckEnum.FILE_NOT_FOUND.getMc());
                }
            } else {
                return new ResultDataVO(LogCheckEnum.FILE_NOT_FOUND.getMc());
            }
        } else {
            // ??????????????????
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
                // ????????????
                if (StringUtils.isNotBlank(log.getParam())) {
                    JSONObject param = JSONObject.parseObject(log.getParam());
                    // ?????????????????????
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
     * ?????????????????????
     *
     * @return
     */
    @PostMapping(value = "/savePrintInfo")
    public void savePrintInfo(ZrzyPrintLogDTO bplDto) {
        zrzyLogFeignService.savaPrintInfo(JSON.toJSONString(bplDto));
    }

    /**
     * ??????fr3
     *
     * @return
     */
    @GetMapping(value = "/openFr3/{logid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String openFr3(@PathVariable("logid") String logid) {
        // ????????????O
        AuditLogDto auditLogDto = logMessageClient.getAuditLogDetail(logid);
        if (auditLogDto != null) {
            return getXmlBylog(auditLogDto);
        } else {
            return "";
        }
    }

    /**
     * ??????xml
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
                    // ???????????????xml
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), CommonConstantUtils.XMLSTR)) {
                        return dataValue.getValue();
                    }
                }
            }
        }
        return "";
    }

    /**
     * @param logid   ??????ID
     * @param keyname ?????????????????????????????????KEY
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description ?????????????????????????????????
     */
    @GetMapping("/data/{logid}/{keyname}")
    public String getLogData(@PathVariable("logid") String logid, @PathVariable("keyname") String keyname) {
        if (StringUtils.isBlank(logid) || StringUtils.isBlank(keyname)) {
            throw new AppException("???????????????????????????????????????????????????????????????");
        }

        // ???????????????ID?????????????????????
        AuditLogDto auditLogDto = logMessageClient.getAuditLogDetail(logid);
        if (null == auditLogDto || CollectionUtils.isEmpty(auditLogDto.getBinaryAnnotations())) {
            return null;
        }

        // ???????????????KEY???
        for (DataValue dataValue : auditLogDto.getBinaryAnnotations()) {
            if (StringUtils.equalsIgnoreCase(dataValue.getKey(), keyname)) {
                return dataValue.getValue();
            }
        }
        return null;
    }

    /**
     * ?????????????????????????????????????????????
     *
     * @param zhcxLogQO
     * @return string
     * @Date 2020/5/23
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @ResponseBody
    @GetMapping("/export")
    public String exportLog(ZrzyZhcxLogQO zhcxLogQO, HttpServletResponse response) throws IOException, WriteException {

        String fileName = "?????????????????????????????????????????????Excel???" + System.currentTimeMillis() + ".xls";
        if (null != zhcxLogQO) {
            LinkedHashMap exportColMap = new LinkedHashMap<>(16);

            if (null != zhcxLogQO.getExportCol()) {
                exportColMap = JSONObject.parseObject(URLDecoder.decode(zhcxLogQO.getExportCol(), "utf-8"), LinkedHashMap.class);
            } else {
                throw new MissingArgumentException("????????????");
            }

            //????????????
            int pageSize = zhcxLogQO.getWpage();
            //??????????????????
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

            // ????????????????????????
            List<QueryLogCondition> conditionList = combineConditionList(zrzyLogQO);
            // ?????????????????? ???????????? ????????????
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
            LOGGER.info("fileName:{}???????????????", fileName);
        } else {
            throw new MissingArgumentException("???????????????");
        }
        return "???????????????";
    }

    /**
     * ??????
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
     * ??????????????????????????????
     *
     * @param zrzyXtCxLogDO ????????????
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping("/xtcx")
    public void saveZrzyCxLog(@RequestBody ZrzyXtCxLogDO zrzyXtCxLogDO) {
        zrzyLogFeignService.saveZrzyCxLog(zrzyXtCxLogDO);
    }

    /**
     * ???????????????????????????????????????
     *
     * @param pageable       pageable
     * @param zrzyXtCxLogCxtj ????????????
     * @return return
     */
    @GetMapping(value = "/xtcx/list")
    @ResponseStatus(HttpStatus.OK)
    public Object xtcxLogListByPage(@LayuiPageable Pageable pageable, @RequestParam("zrzyXtCxLogCxtj") String zrzyXtCxLogCxtj) {
        Page<ZrzyXtCxLogDO> pageResponseDTOS = zrzyLogFeignService.zrzyCxZhcxLogByPage(pageable, zrzyXtCxLogCxtj);
        return super.addLayUiCode(pageResponseDTOS);
    }

    /**
     * ??????????????????????????????
     *
     * @param zrzyZhcxLogQO   ????????????????????????
     * @param response       response
     * @param title          ????????????
     * @param zrzyXtCxLogCxtj ????????????
     */
    @PostMapping("/xtxclog/export")
    public void exportExcel(ZrzyZhcxLogQO zrzyZhcxLogQO, HttpServletResponse response, @RequestParam("title") String title, @RequestParam("zrzyXtCxLogCxtj") String zrzyXtCxLogCxtj) {
        if (null == zrzyZhcxLogQO) {
            throw new AppException("????????????????????????????????????!");
        }
        // ????????????????????????0???????????? ??????????????????????????????1
        Pageable pageable = new PageRequest(zrzyZhcxLogQO.getWpage() - 1, zrzyZhcxLogQO.getCurrcount());
        List<List<String>> excelList = JSONObject.parseObject(zrzyZhcxLogQO.getExportCol(), new TypeReference<List<List<String>>>() {
        });
        Page<ZrzyXtCxLogDO> pageResponseDTOS = zrzyLogFeignService.zrzyCxZhcxLogByPage(pageable, zrzyXtCxLogCxtj);
        List<ZrzyXtCxLogDO> excelDateList = pageResponseDTOS.getContent();
        List<List<String>> dataList = new ArrayList<>();
        // ???????????????
        dataList.add(excelList.get(0));
        // ??????excel??????????????????
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
            throw new AppException("??????????????????!");
        }
    }

    /**
     * ??????Id????????????????????????????????????
     *
     * @param id      key
     * @param keyname ????????????
     * @return return
     */
    @GetMapping("/xtcxdata/{id}/{keyname}")
    public String getXtcxLogData(@PathVariable("id") String id, @PathVariable("keyname") String keyname) {
        if (StringUtils.isBlank(id) || StringUtils.isBlank(keyname)) {
            throw new AppException("???????????????????????????????????????????????????????????????");
        }

        ZrzyXtCxLogDO zrzyXtCxLogDO = zrzyLogFeignService.zrzyCxZhcxLogById(id);
        Map<String, String> map = JSONObject.parseObject(JSONObject.toJSONString(zrzyXtCxLogDO), Map.class);
        if (StringUtils.isNotEmpty(map.get(keyname))) {
            return map.get(keyname);
        }
        return null;
    }
}
