package cn.gtmap.realestate.exchange.service.impl.inf;

import cn.gtmap.gtc.clients.LogMessageClient;
import cn.gtmap.gtc.sso.domain.dto.AuditLogDto;
import cn.gtmap.gtc.sso.domain.dto.DataValue;
import cn.gtmap.gtc.sso.domain.dto.QueryLogCondition;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.BaseResultDto;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.exchange.BdcDaCxLog;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.exchange.service.inf.BdcDaCxService;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import cn.gtmap.realestate.common.core.enums.DacxInterfaceTypeEnum;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Service
public class BdcDaCxServiceImpl implements BdcDaCxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcDaCxService.class);

    private final static String ES_QUERY_TYPE_EQUAL = "equal";

    private final static String RUSULT_PDF_KEY = "pdfBase64Str";

    private final static String RUSULT_JSON_KEY = "respJsonStr";

    private final static String REQUEST_JSON_KEY = "reqJsonStr";

    private final static String RUSULT_INTERFACE_URL_KEY = "interfaceUrl";

    private final static String RUSULT_JKLY_KEY = "jkly";

    private final static String JKLY_PARAM_ZDBS = "DACX_JKLY_MAPPING";

    private final static String JKLY_PARAM_DSFXTBS = "THREE_PARTY_SYSTEM";

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private StorageClientMatcher storageClient;

    @Autowired
    private LogMessageClient logMessageClient;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    private final static Map<String,String> interfacTypeMap = new HashMap<>();

    static {
        interfacTypeMap.put(DacxInterfaceTypeEnum.FY_XXCX.code,DacxInterfaceTypeEnum.FY_XXCX.interfaceType);
        interfacTypeMap.put(DacxInterfaceTypeEnum.GRDACX.code,DacxInterfaceTypeEnum.GRDACX.interfaceType);
        interfacTypeMap.put(DacxInterfaceTypeEnum.WWSQCQZXX.code,DacxInterfaceTypeEnum.WWSQCQZXX.interfaceType);
        interfacTypeMap.put(DacxInterfaceTypeEnum.BDCZSCX.code,DacxInterfaceTypeEnum.BDCZSCX.interfaceType);
        interfacTypeMap.put(DacxInterfaceTypeEnum.ISEXITHUSE.code,DacxInterfaceTypeEnum.ISEXITHUSE.interfaceType);
        interfacTypeMap.put(DacxInterfaceTypeEnum.FWQSXX.code,DacxInterfaceTypeEnum.FWQSXX.interfaceType);
        interfacTypeMap.put(DacxInterfaceTypeEnum.YFWFZM.code,DacxInterfaceTypeEnum.YFWFZM.interfaceType);
        interfacTypeMap.put(DacxInterfaceTypeEnum.YFWFZMLS.code,DacxInterfaceTypeEnum.YFWFZMLS.interfaceType);
        interfacTypeMap.put(DacxInterfaceTypeEnum.QSZM.code,DacxInterfaceTypeEnum.QSZM.interfaceType);
        interfacTypeMap.put(DacxInterfaceTypeEnum.YWTDXXZM.code,DacxInterfaceTypeEnum.YWTDXXZM.interfaceType);
    }

    /**
     * @param bdcDaCxLog
     * @author <a href="mailto:zedeqinag@gtmap.cn">zedq</a>
     */
    @Override
    public int saveBdcDaCxLog(BdcDaCxLog bdcDaCxLog) {
        return entityMapper.insert(bdcDaCxLog);
    }

    /**
     * @param bdcDaCxLog
     * @author <a href="mailto:zedeqinag@gtmap.cn">zedq</a>
     */
    @Override
    public int updateBdcDaCxLog(BdcDaCxLog bdcDaCxLog) {
        return entityMapper.updateByPrimaryKey(bdcDaCxLog);
    }

    /**
     * @param queryId
     * @param response
     * @author <a href="mailto:zedeqinag@gtmap.cn">zedq</a>
     */
    @Override
    public CommonResponse queryBdcDaCxLogInfo(String queryId, HttpServletResponse response) {
        if (StringUtils.isBlank(queryId)) {
            return CommonResponse.fail("??????????????????queryId");
        }
        LOGGER.info("????????????????????????queryId:{}", queryId);
        BdcDaCxLog bdcDaCxLog = entityMapper.selectByPrimaryKey(BdcDaCxLog.class, queryId);
        if (checkBdcDaCxLogInfoForQueryById(bdcDaCxLog)) {
            Map<String, Object> resultMap = new HashMap<>(2);
            if (!"9".equals(bdcDaCxLog.getDacxlogzt())) {
                //????????????????????????storage??????
                if ("2".equals(bdcDaCxLog.getDacxloglx())) {
                    BaseResultDto baseResultDto = storageClient.downloadBase64(bdcDaCxLog.getForeign());
                    if (baseResultDto == null || baseResultDto.getCode() != 0) {
                        return CommonResponse.fail("?????????????????????pdf??????");
                    }
                    resultMap.put(RUSULT_PDF_KEY, baseResultDto.getMsg());
                    resultMap.put(RUSULT_JKLY_KEY,"0");
                    return CommonResponse.ok(resultMap);
                } else {
                    // ??????????????????
                    List<QueryLogCondition> conditions = new ArrayList<>();
                    conditions.add(instCondi("rzid", bdcDaCxLog.getForeign(), ES_QUERY_TYPE_EQUAL));
                    LOGGER.info("??????es????????????");
                    Page<AuditLogDto> logPageResult = logMessageClient.listLogs(0, 10,
                            Constants.EXCHANGE_ES_DACX_RZLX, null, null, null, null, conditions);
                    LOGGER.info("??????es????????????:{}", JSON.toJSONString(logPageResult));
                    if (logPageResult.getContent() != null && CollectionUtils.isNotEmpty(logPageResult.getContent())) {
                        if ("BDCDJ".equals(bdcDaCxLog.getDags())){
                            List<DataValue> esResultDetail = logPageResult.getContent().get(0).getBinaryAnnotations();
                            Optional<DataValue> optional = esResultDetail.stream().filter(dataValue -> dataValue.getKey().equals("xyjg")).findAny();
                            if (optional.isPresent()) {
                                DataValue dataValue = optional.get();
                                if (StringUtils.isNotBlank(dataValue.getValue())) {
                                    resultMap.put(RUSULT_JSON_KEY, dataValue.getValue());
                                    resultMap.put(RUSULT_JKLY_KEY,"0");
                                    if (!interfacTypeMap.containsKey(queryId.substring(0,2))){
                                        return CommonResponse.fail("queryId????????????????????????");
                                    }
                                    resultMap.put(RUSULT_INTERFACE_URL_KEY,interfacTypeMap.get(queryId.substring(0,2)));
                                    return CommonResponse.ok(resultMap);
                                }
                            }
                        }else {
                            List<DataValue> esResultDetail = logPageResult.getContent().get(0).getBinaryAnnotations();
                            Optional<DataValue> optional = esResultDetail.stream().filter(dataValue -> dataValue.getKey().equals("xyjg")).findAny();
                            Optional<DataValue> request = esResultDetail.stream().filter(dataValue1 -> dataValue1.getKey().equals("qqcs")).findAny();
                            if (optional.isPresent()) {
                                DataValue dataValue = optional.get();
                                DataValue requestData = request.isPresent()?request.get():null;
                                if (StringUtils.isNotBlank(dataValue.getValue())) {
                                    resultMap.put(RUSULT_JSON_KEY, dataValue.getValue());
                                    resultMap.put(REQUEST_JSON_KEY, requestData != null ? requestData.getValue() : null);
                                    if (!interfacTypeMap.containsKey(queryId.substring(0,2))){
                                        return CommonResponse.fail("queryId????????????????????????");
                                    }
                                    resultMap.put(RUSULT_INTERFACE_URL_KEY,interfacTypeMap.get(queryId.substring(0,2)));
                                    //??????????????????
                                    BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
                                    bdcZdDsfzdgxDO.setZdbs(JKLY_PARAM_ZDBS);
                                    bdcZdDsfzdgxDO.setBdczdz(bdcDaCxLog.getDags());
                                    bdcZdDsfzdgxDO.setDsfxtbs(JKLY_PARAM_DSFXTBS);
                                    BdcZdDsfzdgxDO jklyConfigration = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
                                    if(jklyConfigration == null || StringUtils.isBlank(jklyConfigration.getDsfzdz())){
                                        return CommonResponse.fail("????????????????????????????????????????????????????????????");
                                    }
                                    resultMap.put(RUSULT_JKLY_KEY,jklyConfigration.getDsfzdz());
                                    return CommonResponse.ok(resultMap);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            return CommonResponse.fail("queryId:" + queryId + "?????????bdcDaCxLog???????????????!");
        }
        return CommonResponse.fail("????????????????????????");
    }

    /**
     * @param fileId
     * @param response
     * @author <a href="mailto:zedeqinag@gtmap.cn">zedq</a>
     */
    @Override
    public void downloadBdcDaCxLogPdf(String fileId, HttpServletResponse response) {
        if (StringUtils.isBlank(fileId)) {
            throw new RuntimeException("??????fileId????????????");
        }
        MultipartDto download = storageClient.download(fileId);
        if (download == null) {
            throw new RuntimeException("?????????????????????pdf??????");
        }
        OutputStream outputStream = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/pdf"); // word??????
            response.setHeader("Content-Disposition", "attachment; filename=" + download.getOriginalFilename());
            outputStream = response.getOutputStream();
            outputStream.write(download.getData());
            outputStream.flush();
        } catch (IOException e) {
            LOGGER.error("??????pdf???????????????:" + e.getMessage());
            throw new RuntimeException("??????pdf???????????????:" + e.getMessage());
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

    private boolean checkBdcDaCxLogInfoForQueryById(BdcDaCxLog bdcDaCxLog) {
        if (bdcDaCxLog == null) {
            return false;
        }
        if (StringUtils.isBlank(bdcDaCxLog.getDacxloglx()) || StringUtils.isBlank(bdcDaCxLog.getDacxlogzt()) || StringUtils.isBlank(bdcDaCxLog.getForeign()) || StringUtils.isBlank(bdcDaCxLog.getDags())) {
            return false;
        }
        if ("9".equals(bdcDaCxLog.getDacxlogzt())) {
            return false;
        }
        return true;
    }

    private QueryLogCondition instCondi(String key, String value, String type) {
        QueryLogCondition condition = new QueryLogCondition();
        condition.setType(type);
        condition.setKey(key);
        condition.setValue(value);
        return condition;
    }

}
