package cn.gtmap.realestate.exchange.service.impl.national.access;

import cn.gtmap.realestate.common.core.annotations.RedissonLock;
import cn.gtmap.realestate.common.core.bo.accessnewlog.AccessNewLogs;
import cn.gtmap.realestate.common.core.cache.BdcZdCache;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJrLwDO;
import cn.gtmap.realestate.common.core.domain.exchange.ZttGyQlrDO;
import cn.gtmap.realestate.common.core.dto.exchange.access.MsgNoticeDTO;
import cn.gtmap.realestate.common.core.dto.exchange.access.SbxzDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.tsqxxz.TsqxxzDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.tsqxxz.TsqxxzRequest;
import cn.gtmap.realestate.common.core.enums.AccessWarningEnum;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJrlwFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.exchange.config.ConfigInit;
import cn.gtmap.realestate.exchange.core.domain.BdcJrCzrzDO;
import cn.gtmap.realestate.exchange.core.domain.BdcJrDbrzjlDO;
import cn.gtmap.realestate.exchange.core.domain.JgWxzbwxxDO;
import cn.gtmap.realestate.exchange.core.dto.AccessLogDTO;
import cn.gtmap.realestate.exchange.core.dto.DbJrDbMxDTO;
import cn.gtmap.realestate.exchange.core.dto.common.BdcAccessCountResultDTO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.accessLog.AccessLogMapper;
import cn.gtmap.realestate.exchange.core.national.BdcAccessLog;
import cn.gtmap.realestate.exchange.core.qo.BdcDbJrXqQO;
import cn.gtmap.realestate.exchange.core.qo.JgWxzBwxxQO;
import cn.gtmap.realestate.exchange.service.impl.national.upload.NationalAccessUploadFtpImpl;
import cn.gtmap.realestate.exchange.service.impl.national.upload.NationalAccessUploadSftpImpl;
import cn.gtmap.realestate.exchange.service.impl.national.upload.ProvinceAccessUploadFtpImpl;
import cn.gtmap.realestate.exchange.service.impl.national.upload.ProvinceAccessUploadSftpImpl;
import cn.gtmap.realestate.exchange.service.inf.wwsq.SendMsgService;
import cn.gtmap.realestate.exchange.service.national.AccesssModelHandlerService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessUpload;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogDataService;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogService;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogTypeService;
import cn.gtmap.realestate.exchange.util.AsyncDealUtils;
import cn.gtmap.realestate.exchange.util.DateUtil;
import cn.gtmap.realestate.exchange.util.UploadServiceUtil;
import cn.gtmap.realestate.exchange.util.XmlEntityConvertUtil;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import cn.gtmap.realestate.exchange.util.enums.JrRzCgbsEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
 * @version 1.0  2018-12-17
 * @description ???????????? service ??????
 */
@Service
public class AccessLogImpl implements AccessLogService {
    private static final Logger logger = LoggerFactory.getLogger(AccessLogImpl.class);
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcdjMapper bdcdjMapper;
    @Autowired
    private AccessLogMapper accessLogMapper;
    @Autowired
    XmlEntityConvertUtil xmlEntityConvertUtil;
    @Autowired
    ConfigInit configInit;
    @Autowired
    BdcZdCache bdcZdCache;
    @Autowired
    AccessLogTypeServiceImpl accessLogSftpService;
    @Autowired
    private Repo repository;
    @Autowired
    ProvinceAccessUploadFtpImpl provinceAccessUploadFtp;
    @Autowired
    ProvinceAccessUploadSftpImpl provinceAccessUploadSftp;
    @Autowired
    NationalAccessUploadFtpImpl nationalAccessUploadFtp;
    @Autowired
    NationalAccessUploadSftpImpl nationalAccessUploadSftp;
    @Autowired
    private SendMsgService sendMsgService;
    @Autowired
    private BdcZdFeignService bdcZdFeignService;
    @Value("${access.filter.history.data.flag:false}")
    private boolean accessFilterHistoryDataFlag;
    @Value("${data.version:}")
    private String dataVersion;
    @Value("${check.access.response.error.dh:}")
    private String dhs;
    @Value("${check.access.response.error.msg.type:}")
    private String msgType;

    @Autowired
    AsyncDealUtils asyncDealUtils;

    @Autowired
    BdcXmMapper bdcXmMapper;

    @Autowired
    AccessLogTypeService accessLogTypeService;

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    BdcSlJrlwFeignService bdcSlJrlwFeignService;

    @Autowired
    AccesssModelHandlerService accesssModelHandlerService;

    Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.String>>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ???????????????????????? ???????????????
     */
    @Override
    public List<Map<String, String>> queryAccessWsxList(String qxtype) {
        List<Map<String, String>> result = new ArrayList<>();
        String qxdms = EnvironmentConfig.getEnvironment().getProperty(qxtype + ".access.qxdm");
        String qxmcs = EnvironmentConfig.getEnvironment().getProperty(qxtype + ".access.qxmc");
        if (StringUtils.isNotBlank(qxdms) && StringUtils.isNotBlank(qxmcs)) {
            String[] qxdmArr = qxdms.split(",");
            String[] qxmcArr = qxmcs.split(",");
            if (qxdmArr.length == qxmcArr.length) {
                for (int i = 0; i < qxdmArr.length; i++) {
                    Map<String, String> qxMap = new HashMap<>(8);
                    qxMap.put("qxdm", qxdmArr[i]);
                    qxMap.put("qxmc", qxmcArr[i]);
                    result.add(qxMap);
                }
            }
        }
        return result;
    }

    @Override
    public void accessLog(Date accessDate, String xzdm) {
        LOGGER.info("???????????????????????????????????????{}?????????{}", accessDate, xzdm);
        try {
            List<Map<String, String>> qxdmlist = bdcdjMapper.queryBdcZdQx();
            LOGGER.info("????????????????????????{}", qxdmlist);
            String sbType = configInit.getConditionTime();
            String conditionTime = StringUtils.isNotBlank(sbType) ? sbType : "dbsj";
            if (CollectionUtils.isNotEmpty(qxdmlist)) {
                for (Map<String, String> map : qxdmlist) {
                    try {
                        if (StringUtils.isNotBlank(xzdm)) {
                            while (StringUtils.equals(MapUtils.getString(map, "DM"), xzdm)) {
                                accessLogsByQxdm(xzdm, MapUtils.getString(map, "MC"), conditionTime, accessDate, true);
                                break;
                            }
                        } else {
                            String qxdm = String.valueOf(map.get("DM"));
                            accessLogsByQxdm(qxdm, MapUtils.getString(map, "MC"), conditionTime, accessDate, true);
                        }
                    } catch (Exception e) {
                        LOGGER.error("????????????{}????????????????????????", map.get("DM"), e);
                    }
                }
            } else {
                LOGGER.error("????????????????????????????????????BDC_ZD_QX?????????");
            }
        } catch (Exception e) {
            LOGGER.error("????????????????????????", e);
        }
    }

    /**
     * @param accessDate
     * @param qxdm
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ??????????????????
     * @date : 2023/1/6 15:24
     */
    @Override
    public void cityAccessLog(Date accessDate, String qxdm) {
        try {
            List<Map<String, String>> qxdmlist = bdcdjMapper.queryBdcZdQx();
            LOGGER.info("?????????????????????????????????????????????{}?????????{},??????????????????{}", accessDate, qxdm, qxdmlist);
            if (CollectionUtils.isNotEmpty(qxdmlist)) {
                for (Map<String, String> map : qxdmlist) {
                    try {
                        if (StringUtils.isNotBlank(qxdm)) {
                            while (StringUtils.equals(MapUtils.getString(map, "DM"), qxdm)) {
                                cityAccess(qxdm, MapUtils.getString(map, "MC"), accessDate);
                                break;
                            }
                        } else {
                            qxdm = String.valueOf(map.get("DM"));
                            cityAccess(qxdm, MapUtils.getString(map, "MC"), accessDate);
                        }
                    } catch (Exception e) {
                        LOGGER.error("????????????{}????????????????????????", map.get("DM"), e);
                    }
                }
            } else {
                LOGGER.error("????????????????????????????????????BDC_ZD_QX?????????");
            }
        } catch (Exception e) {
            LOGGER.error("????????????????????????", e);
        }
    }

    /**
     * @param accessDate
     * @param qxdm
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ????????????????????????
     * @date : 2022/10/13 9:19
     */
    @Override
    public Map<String, Object> dbrzmxyl(Date accessDate, String qxdm, String type) {
        String sbType = configInit.getConditionTime();
        String conditionTime = StringUtils.isNotBlank(sbType) ? sbType : "dbsj";
        List<Map<String, String>> qxdmlist = bdcdjMapper.queryBdcZdQx();
        //???????????????????????????
        Map<String, Object> dbrzMx = new HashMap<>();
        for (Map<String, String> map : qxdmlist) {
            if (StringUtils.isNotBlank(qxdm) && StringUtils.equals(qxdm, MapUtils.getString(map, "DM", ""))) {
                Map param = new HashMap();
                param.put("qxdm", qxdm);
                param.put("qxmc", MapUtils.getString(map, "MC", ""));
                param.put("conditionTime", conditionTime);
                param.put("accessDate", accessDate);
                if (configInit.isNationalEnble()) {
                    param.put("logTable", "bdc_jr_gjjl");
                } else {
                    param.put("logTable", "bdc_jr_sjjl");
                }
                if (StringUtils.isNotBlank(type) && Constants.ACCESS_TYPE_CITY.equals(type)) {
                    param.put("logTable", "bdc_jr_shijjl");
                }
                String xml = "";
                AccessNewLogs accessNewLogs = new AccessNewLogs();
                AccessLogDataService accessService = this.getAccessLogService();
                if (Objects.isNull(accessService)) {
                    LOGGER.error("?????????????????????????????????");
                    break;
                }
                accessNewLogs = accessService.getAccessNewLogs(param, accessDate, false);
                if (null != accessNewLogs) {
                    xml = xmlEntityConvertUtil.entityToXml(accessNewLogs);
                }
                if (StringUtils.isNotBlank(xml)) {
                    AccessNewLogs accessLogs = (AccessNewLogs) xmlEntityConvertUtil.xmlToEntity(AccessNewLogs.class, new StringReader(xml));
                    if (accessLogs != null && CollectionUtils.isNotEmpty(accessLogs.getAccessNewLogList())) {
                        dbrzMx.put("accessLog", accessLogs.getAccessNewLogList().get(0));
                    }
                    dbrzMx.put("qx", qxdmlist);
                    dbrzMx.put("qxdm", qxdm);
                }
                break;
            }
        }
        LOGGER.warn("??????????????????????????????{}???????????????{}", accessDate, qxdm);
        return dbrzMx;
    }

    /**
     * @param pageable
     * @param map
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description ????????????????????????
     */
    @Override
    public Page<Object> listAccessLogByPages(Pageable pageable, Map map) {
        if (accessFilterHistoryDataFlag) {
            map.put("xmlyFlag", "true");
        }
        if (MapUtils.getBooleanValue(map, "wlxm", false)) {
            return repository.selectPaging("listWlxmAccessLogByPageOrder", map, pageable);
        }
        return repository.selectPaging("listAccessLogByPageOrder", map, pageable);
    }

    /**
     * @param pageable
     * @param map
     * @return org.springframework.data.domain.Page<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ?????????????????????
     */
    @Override
    public Page<AccessLogDTO> listWsxAccessLogByPage(Pageable pageable, Map map) {
        Page<AccessLogDTO> pageContent = repository.selectPaging("listWsxAccessLogByPageOrder", map, pageable);
        if (pageContent.hasContent()) {
            for (AccessLogDTO logDTO : pageContent.getContent()) {
                fillContent(logDTO, map);
            }
        }
        return pageContent;
    }

    /**
     * @param map
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.exchange.ZttGyQlrDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ??????????????????????????????
     */
    private List<ZttGyQlrDO> listWsxQlrByYwh(Map map) {
        return accessLogMapper.listZttGyQlrByYwh(map);
    }

    /**
     * @param accessLogDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ????????????????????? QLR ??? BDCQZH
     */
    private void fillContent(AccessLogDTO accessLogDTO, Map map) {
        if (StringUtils.isNotBlank(accessLogDTO.getYwh())) {
            // ??????????????? ???BDCQZH  ??????
            map.put("ywh", accessLogDTO.getYwh());
            List<ZttGyQlrDO> qlrList = listWsxQlrByYwh(map);
            if (CollectionUtils.isNotEmpty(qlrList)) {
                StringBuilder bdcqzhSb = new StringBuilder("");
                List<String> bdcqzhList = new ArrayList<>();
                StringBuilder qlrSb = new StringBuilder("");
                for (ZttGyQlrDO qlr : qlrList) {
                    if (StringUtils.isNotBlank(qlr.getQlrmc())) {
                        qlrSb.append(qlr.getQlrmc() + ",");
                    }
                    if (StringUtils.isNotBlank(qlr.getBdcqzh())
                            && !bdcqzhList.contains(qlr.getBdcqzh())) {
                        bdcqzhSb.append(qlr.getBdcqzh() + ",");
                    }
                }
                // set QLRMC
                String qlrmcStr = qlrSb.toString();
                if (qlrmcStr.endsWith(",")) {
                    qlrmcStr = qlrmcStr.substring(0, qlrmcStr.length() - 1);
                }
                accessLogDTO.setQlr(qlrmcStr);

                // set BDCQZH
                String bdcqzhStr = bdcqzhSb.toString();
                if (bdcqzhStr.endsWith(",")) {
                    bdcqzhStr = bdcqzhStr.substring(0, bdcqzhStr.length() - 1);
                }
                accessLogDTO.setBdcqzh(bdcqzhStr);
            }
        }
    }


    /**
     * @param xmidList
     * @param logType
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description ??????????????????
     */
    @Override
    public String getAccessResponse(List<String> xmidList, String logType) {
        List<NationalAccessUpload> list = UploadServiceUtil.listNationalAccessUpload();
        String msg = "";
        if (CollectionUtils.isNotEmpty(xmidList)) {
            StringBuilder sb = new StringBuilder();
            for (String xmid : xmidList) {
                String ywbwid = "";
                BdcAccessLog logDO = null;
                if (StringUtils.equals(Constants.ACCESS_TYPE_NATIONAL, logType)) {
                    logDO = accessLogMapper.getNationalAccessYwbwidByXmid(xmid);
                } else if (StringUtils.equals(Constants.ACCESS_TYPE_CITY, logType)) {
                    logDO = accessLogMapper.getCityAccessYwbwidByXmid(xmid);
                } else {
                    logDO = accessLogMapper.getProvinceAccessYwbwidByXmid(xmid);
                }
                if (logDO != null) {
                    ywbwid = logDO.getYwbwid();
                }
                if (StringUtils.isNotBlank(ywbwid)) {
                    sb.append(ywbwid).append(",");
                }
            }
            String ywbwids = sb.toString();
            if (StringUtils.isNotBlank(ywbwids)) {
                ywbwids = ywbwids.substring(0, ywbwids.length() - 1);
                try {
                    for (NationalAccessUpload upload : list) {
                        upload.getReponse(ywbwids);
                    }
                    msg = "????????????????????????,?????????!";
                } catch (Exception e) {
                    logger.error("errorMsg:", e);
                    msg = "???????????????????????????";
                }
            } else {
                msg = "???????????????????????????????????????????????????";
            }
        }
        return msg;
    }

    /**
     * @param ywh
     * @param logType
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ??????????????????XML
     */
    @Override
    public String queryXybw(String ywh, String logType) {
        BdcAccessLog logDO;
        if (StringUtils.equals(Constants.ACCESS_TYPE_NATIONAL, logType)) {
            logDO = accessLogMapper.getNationalAccessYwbwidByXmid(ywh);
        } else {
            logDO = accessLogMapper.getProvinceAccessYwbwidByXmid(ywh);
        }
        if (logDO != null && StringUtils.isNotBlank(logDO.getXybw())) {
            return logDO.getXybw();
        }
        return "??????????????????";
    }

    /**
     * @param ywh
     * @param logType
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ??????????????????XML
     */
    @Override
    public String queryJrbw(String ywh, String logType) {
        BdcAccessLog logDO;
        if (StringUtils.equals(Constants.ACCESS_TYPE_NATIONAL, logType)) {
            logDO = accessLogMapper.getNationalAccessYwbwidByXmid(ywh);
        } else {
            logDO = accessLogMapper.getProvinceAccessYwbwidByXmid(ywh);
        }
        if (logDO != null && StringUtils.isNotBlank(logDO.getJrbw())) {
            return logDO.getJrbw();
        }
        return "??????????????????";
    }

    @Override
    public BdcAccessLog queryBdcAccessLog(String ywh, String logType) {
        BdcAccessLog logDO = null;
        if (StringUtils.equals(Constants.ACCESS_TYPE_NATIONAL, logType)) {
            logDO = accessLogMapper.getNationalAccessYwbwidByXmid(ywh);
        } else {
            logDO = accessLogMapper.getProvinceAccessYwbwidByXmid(ywh);
        }
        return logDO;
    }


    /**
     * @param accessDate    ????????????
     * @param qxdm          ????????????
     * @param qxmc          ????????????
     * @param conditionTime ????????????
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description ?????????????????????
     */
    public boolean accessLogsByQxdm(String qxdm, String qxmc, String conditionTime, Date accessDate, boolean insertJrdbrzFlag) {
        //???????????????????????????
        Map param = new HashMap();
        param.put("qxdm", qxdm);
        param.put("qxmc", qxmc);
        param.put("conditionTime", conditionTime);
        param.put("accessDate", accessDate);
        if (configInit.isNationalEnble()) {
            param.put("logTable", "bdc_jr_gjjl");
        } else {
            param.put("logTable", "bdc_jr_sjjl");
        }
        //????????????
        BdcJrDbrzjlDO bdcJrDbrzjlDO = new BdcJrDbrzjlDO();
        //??????xml
        String xml = "";
        AccessNewLogs accessNewLogs = new AccessNewLogs();
        LOGGER.warn("??????????????????????????????{}???????????????{}", accessDate, qxdm);
        try {
            AccessLogDataService accessService = this.getAccessLogService();
            if (Objects.isNull(accessService)) {
                LOGGER.error("?????????????????????????????????");
                return false;
            }
            //????????????
            accessNewLogs = accessService.getAccessNewLogs(param, accessDate, false);
            if (null != accessNewLogs) {
                xml = xmlEntityConvertUtil.entityToXml(accessNewLogs);
            }

            xml = xml.replaceAll("\\s*xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", "");
            LOGGER.info("?????????????????????????????????????????????????????????????????????????????????{}??????????????????{}", qxdm, xml);

            //??????webservice??????
            if (configInit.isNationalEnble()) {
                accessLogSftpService.nationalAccessLogWeb(xml, bdcJrDbrzjlDO);
            }

            //??????webservice??????
            if (configInit.isProvinceEnble()) {
                accessLogSftpService.provinceAccessLogWeb(xml, bdcJrDbrzjlDO);
            }

            //????????????
            if (configInit.getCityEnable()) {
                LOGGER.warn("?????????????????????,????????????bdc_jr_shijjl");
                param.put("logTable", "bdc_jr_shijjl");
                accessNewLogs = accessService.getAccessNewLogs(param, accessDate, false);
                if (null != accessNewLogs) {
                    xml = xmlEntityConvertUtil.entityToXml(accessNewLogs);
                }

                xml = xml.replaceAll("\\s*xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", "");
                LOGGER.info("?????????????????????????????????????????????????????????????????????????????????{}??????????????????{}", qxdm, xml);
                accessLogSftpService.cityAccessLogWeb(xml, bdcJrDbrzjlDO);
            }

            //????????????xml
            if (StringUtils.isNotBlank(configInit.getXmlPath())) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(qxdm).append("-").append(DateUtil.formateTimeYmdhms(accessDate));
                accessLogSftpService.generateXmlAndFtp(xml, stringBuilder.toString());
            }

        } catch (Exception e) {
            LOGGER.error("??????{}????????????????????????",qxdm, e);
            //????????????
            MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
            msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_7.getYjlx());
            msgNoticeDTO.setSbsj(DateUtil.formateTime(accessDate));
            accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
        } finally {
            LOGGER.info("??????:{}????????????????????????????????????: {}", qxmc, JSONObject.toJSONString(bdcJrDbrzjlDO));
            if (insertJrdbrzFlag) {
                //?????????sql????????????????????????do??????,???????????????????????????do?????????
                bdcJrDbrzjlDO = getBdcJrDbrzjlDO(bdcJrDbrzjlDO, xml, accessDate, qxdm);
                //????????????????????????????????????: ????????????????????????????????????xm??????qxdm????????????????????????????????????bdcdyh?????????
                if (StringUtils.isNotBlank(dataVersion) && CommonConstantUtils.NANTONG.equals(dataVersion)) {
                    JSONObject bdcJrDbrzjlJsonObject = JSON.parseObject(JSON.toJSONString(bdcJrDbrzjlDO));
                    //????????????
                    //??????xml
                    String xnxml = "";
                    AccessLogDataService accessService = this.getAccessLogService();
                    if (Objects.isNull(accessService)) {
                        LOGGER.error("?????????????????????????????????");
                        return false;
                    }
                    accessNewLogs = accessService.getAccessNewLogs(param, accessDate, true);
                    if (null != accessNewLogs) {
                        xnxml = xmlEntityConvertUtil.entityToXml(accessNewLogs);
                    }
                    xnxml = xnxml.replaceAll("\\s*xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", "");
                    bdcJrDbrzjlJsonObject.put("xnjrbw", xnxml);
                    bdcdjMapper.insertBdcJrDbrzjlWithXnjrbw(bdcJrDbrzjlJsonObject);
                } else {
                    entityMapper.insertSelective(bdcJrDbrzjlDO);
                }

                //??????????????????????????????
                if (Objects.nonNull(accessNewLogs) && CollectionUtils.isNotEmpty(accessNewLogs.getAccessNewLogList()) && Objects.nonNull(accessNewLogs.getAccessNewLogList().get(0).getRegisterList())) {
                    if (CollectionUtils.isNotEmpty(accessNewLogs.getAccessNewLogList().get(0).getRegisterList().getRegister())) {
                        /*
                         * ??????????????????
                         * */
                        asyncDealUtils.plbcDbrzXq(accessNewLogs.getAccessNewLogList().get(0).getRegisterList().getRegister(), bdcJrDbrzjlDO.getId());
                    }
                }
            }
            if (!CommonConstantUtils.SF_S_DM.equals(bdcJrDbrzjlDO.getSjcgbs())) {
                //???????????????????????????????????????????????????????????????
                List<BdcJrDbrzjlDO> list = accessLogMapper.listBdcJrDbrzjlList(qxdm, accessDate);
                //??????????????????  20?????????????????????
                if (CollectionUtils.isEmpty(list)) {
                    LOGGER.info("???????????????????????????????????????????????????: {}", JSONObject.toJSONString(bdcJrDbrzjlDO));
                    //????????????????????????  ???????????????????????????
                    new Timer("????????????????????????").schedule(new TimerTask() {
                        @Override
                        public void run() {
                            accessLog(accessDate, qxdm);
                        }
                    }, new Date(System.currentTimeMillis() + 20 * 60 * 1000));

                    //????????????
                    MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
                    msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_14.getYjlx());
                    msgNoticeDTO.setSbsj(DateUtil.formateTime(accessDate));
                    accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
                }
                return false;
            }
            LOGGER.info("??????{}????????????????????????????????????", qxmc);
            return true;
        }
    }


    public boolean cityAccess(String qxdm, String qxmc, Date accessDate) {
        //???????????????????????????
        boolean result = true;
        Map param = new HashMap();
        param.put("qxdm", qxdm);
        param.put("qxmc", qxmc);
        param.put("accessDate", accessDate);
        param.put("logTable", "bdc_jr_shijjl");
        //????????????
        BdcJrDbrzjlDO bdcJrDbrzjlDO = new BdcJrDbrzjlDO();
        //??????xml
        String xml = "";
        AccessNewLogs accessNewLogs = new AccessNewLogs();
        LOGGER.warn("????????????????????????????????????{}???????????????{}", accessDate, qxdm);
        try {
            AccessLogDataService accessService = this.getAccessLogService();
            if (Objects.isNull(accessService)) {
                LOGGER.error("?????????????????????????????????");
                return false;
            }
            //????????????
            accessNewLogs = accessService.getAccessNewLogs(param, accessDate, false);
            if (null != accessNewLogs) {
                xml = xmlEntityConvertUtil.entityToXml(accessNewLogs);
            }

            xml = xml.replaceAll("\\s*xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", "");
            LOGGER.info("?????????????????????????????????????????????????????????????????????????????????{}??????????????????{}", qxdm, xml);
            accessLogSftpService.cityAccessLogWeb(xml, bdcJrDbrzjlDO);
            //????????????xml
            if (StringUtils.isNotBlank(configInit.getXmlPath())) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(qxdm).append("-").append(DateUtil.formateTimeYmdhms(accessDate));
                accessLogSftpService.generateXmlAndFtp(xml, stringBuilder.toString());
            }
        } catch (Exception e) {
            result = false;
            LOGGER.error("??????{}??????????????????????????????", qxdm, e);
            //????????????
            MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
            msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_7.getYjlx());
            msgNoticeDTO.setSbsj(DateUtil.formateTime(accessDate));
            accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
        } finally {
            LOGGER.info("??????:{}??????????????????????????????????????????: {}", qxmc, JSONObject.toJSONString(bdcJrDbrzjlDO));
            //?????????sql????????????????????????do??????,???????????????????????????do?????????
            bdcJrDbrzjlDO = getBdcJrDbrzjlDO(bdcJrDbrzjlDO, xml, accessDate, qxdm);
            entityMapper.insertSelective(bdcJrDbrzjlDO);
            //??????????????????????????????
            if (Objects.nonNull(accessNewLogs) && CollectionUtils.isNotEmpty(accessNewLogs.getAccessNewLogList()) && Objects.nonNull(accessNewLogs.getAccessNewLogList().get(0).getRegisterList())) {
                if (CollectionUtils.isNotEmpty(accessNewLogs.getAccessNewLogList().get(0).getRegisterList().getRegister())) {
                    /*
                     * ??????????????????
                     * */
                    asyncDealUtils.plbcDbrzXq(accessNewLogs.getAccessNewLogList().get(0).getRegisterList().getRegister(), bdcJrDbrzjlDO.getId());
                }
            }
            LOGGER.info("??????{}??????????????????????????????????????????", qxmc);
        }
        return result;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description ????????????????????????
     */
    private BdcJrDbrzjlDO getBdcJrDbrzjlDO(BdcJrDbrzjlDO bdcJrDbrzjlDO, String xml, Date accessDate, String qxdm) {
        bdcJrDbrzjlDO.setJrbw(xml);
        bdcJrDbrzjlDO.setJrrq(new Date());
        bdcJrDbrzjlDO.setAccessdate(accessDate);
        bdcJrDbrzjlDO.setXzqdm(qxdm);
        if (bdcJrDbrzjlDO.getCgbs() == null) {
            bdcJrDbrzjlDO.setCgbs(CommonConstantUtils.SF_F_DM);
        }
        if (bdcJrDbrzjlDO.getSjcgbs() == null) {
            bdcJrDbrzjlDO.setSjcgbs(CommonConstantUtils.SF_F_DM);
        }
        if (Objects.isNull(bdcJrDbrzjlDO.getShijcgbs())) {
            bdcJrDbrzjlDO.setShijcgbs(CommonConstantUtils.SF_S_DM);
        }
        bdcJrDbrzjlDO.setId(UUIDGenerator.generate());
        return bdcJrDbrzjlDO;
    }

    /**
     * @param
     * @return java.util.List<cn.gtmap.realestate.exchange.core.national.BdcAccessLog>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ???????????????????????????????????? ?????????????????????
     */
    @Override
    @RedissonLock(lockKey = "EXCHANGE_QUARZ_SJJR", description = "??????????????????????????????")
    public List<BdcAccessLog> queryAndUpdateSjjr() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("num", Constants.ACCESSRESPONSE_QUARTZ_PERUPDATE_TIMES);
        paramMap.put("logType", Constants.ACCESS_TYPE_PROVINCE);
        paramMap.put("afterDate", DateUtil.dealDate(DateUtil.getPastDate(Constants.ACCESSRESPONSE_QUARTZ_BEFOREDATE), "00:00:00"));
        List<BdcAccessLog> jrrzList = accessLogMapper.listAccessLogWithNoXybm(paramMap);
        // ?????????????????????FLAG
        updateDealingFlag(jrrzList, Constants.ACCESS_TYPE_PROVINCE);
        return jrrzList;
    }

    @Override
    public Map<String, Object> dbrzMx(String id) {
        Map<String, Object> dbrzMx = new HashMap<>();
        BdcJrDbrzjlDO bdcJrDbrzjlDO = accessLogMapper.queryBdcJrDbrzjl(id);
        if (bdcJrDbrzjlDO != null) {
            dbrzMx.put("bdcJrDbrzjlDO", bdcJrDbrzjlDO);
            AccessNewLogs accessLogs;
//    		if (StringUtils.isNotBlank(dataVersion) && CommonConstantUtils.NANTONG.equals(dataVersion)){
//                String xnjrbw = bdcdjMapper.queryBdcJrDbrzjlXnjrbwWithXnjrbwById(id);
//                accessLogs = (AccessLogs)xmlEntityConvertUtil.xmlToEntity(AccessLogs.class, new StringReader(xnjrbw));
//            }else {
//                accessLogs = (AccessLogs)xmlEntityConvertUtil.xmlToEntity(AccessLogs.class, new StringReader(bdcJrDbrzjlDO.getJrbw()));
//            }
            accessLogs = (AccessNewLogs) xmlEntityConvertUtil.xmlToEntity(AccessNewLogs.class, new StringReader(bdcJrDbrzjlDO.getJrbw()));

            if (accessLogs != null && CollectionUtils.isNotEmpty(accessLogs.getAccessNewLogList())) {
                dbrzMx.put("accessLog", accessLogs.getAccessNewLogList().get(0));
            }
        }
        List<Map> qxMapList = bdcZdFeignService.queryBdcZd("qx");
        dbrzMx.put("qx", qxMapList);

        return dbrzMx;
    }

    /**
     * @param jrrzList
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ?????? ????????????
     */
    private void updateDealingFlag(List<BdcAccessLog> jrrzList, String logType) {
        List ywbwidList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(jrrzList)) {
            for (BdcAccessLog accessLog : jrrzList) {
                if (StringUtils.isNotBlank(accessLog.getYwbwid())) {
                    ywbwidList.add(accessLog.getYwbwid());
                }
            }
        }
        if (CollectionUtils.isNotEmpty(ywbwidList)) {
            Map paramMap = new HashMap();
            paramMap.put("ywbwidList", ywbwidList);
            paramMap.put("logType", logType);
            paramMap.put("flag", Constants.ACCESSRESPONSE_QUARTZ_DEALINGFLAG);
            accessLogMapper.updateDealingXybm(paramMap);
        }
    }

    /**
     * ???????????????????????????????????????
     *
     * @param accessType
     */
    @Override
    public void checkAccessResponseAndSendMsg(String accessType) {
        Map countAccessParam = new HashMap(2);
        countAccessParam.put("accessDate", new Date());
        if (Constants.ACCESS_TYPE_PROVINCE.equals(accessType)) {
            countAccessParam.put("logTable", "bdc_jr_sjjl");
        } else {
            countAccessParam.put("logTable", "bdc_jr_gjjl");
        }
        List<BdcAccessCountResultDTO> countErrorJrxx = bdcdjMapper.countErrorJrxx(countAccessParam);
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(countErrorJrxx)) {
            try {
                Map<String, String> collect = countErrorJrxx.stream().collect(Collectors.toMap(bdcAccessCountResultDTO ->
                {
                    if (bdcAccessCountResultDTO.getCgbs() != null) {
                        return JrRzCgbsEnum.getCodeByBs(bdcAccessCountResultDTO.getCgbs());
                    } else {
                        return "wsb";
                    }
                }, bdcAccessCountResultDTO -> bdcAccessCountResultDTO.getNum().toString()));
                if (Integer.parseInt(collect.get(JrRzCgbsEnum.ACCESS_FAIL.getCode()) != null ? collect.get(JrRzCgbsEnum.ACCESS_FAIL.getCode()) : "0") > 0 || Integer.parseInt(collect.get(JrRzCgbsEnum.RESP_FAIL.getCode()) != null ? collect.get(JrRzCgbsEnum.RESP_FAIL.getCode()) : "0") > 0 || Integer.parseInt(collect.get("wsb") != null ? collect.get("wsb") : "0") > 0) {
                    //????????????
                    if (StringUtils.isNotBlank(msgType) && StringUtils.isNotBlank(dhs) && dhs.contains(",")) {
                        for (String dh : dhs.split(",")) {
                            sendMsgService.smsMsg(collect, dh, msgType);
                            sendMsgService.saveMsgLog(null, String.valueOf(collect), dh, "0", "????????????");
                        }
                    } else if (StringUtils.isNotBlank(msgType) && StringUtils.isNotBlank(dhs) && !dhs.contains(",")) {
                        sendMsgService.smsMsg(collect, dhs, msgType);
                        sendMsgService.saveMsgLog(null, String.valueOf(collect), dhs, "0", "????????????");
                    }
                }
            } catch (Exception e) {
                sendMsgService.saveMsgLog(null, null, dhs, "1", "????????????:" + e.getMessage());
            }
        }
    }

    /*
     * ?????????????????????
     * */
    @Override
    public int queryDbSjl(BdcDbJrXqQO bdcDbJrXqQO) {
        return accessLogMapper.queryDbsjl(bdcDbJrXqQO);
    }

    /*
     * ?????????????????????
     * */
    @Override
    public int queryJrSjl(BdcDbJrXqQO bdcDbJrXqQO) {
        if (configInit.isNationalEnble()) {
            bdcDbJrXqQO.setTableName("bdc_jr_gjjl");
        } else {
            bdcDbJrXqQO.setTableName("bdc_jr_sjjl");
        }
        return accessLogMapper.queryJrsjl(bdcDbJrXqQO);
    }

    /*
     * ?????????????????????????????????
     * */
    @Override
    public int queryDbrzXqSjl(BdcDbJrXqQO bdcDbJrXqQO) {
        return accessLogMapper.queryDbrzxqSjl(bdcDbJrXqQO);
    }

    /**
     * @param xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ????????????????????????
     * @date : 2022/7/11 16:47
     */
    @Override
    public List<BdcJrCzrzDO> queryJrCzrz(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            return accessLogMapper.listBdcJrczrz(xmid);
        }
        return null;
    }

    /**
     * @param bdcDbJrXqQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2022/7/28 10:00
     */
    @Override
    public List<DbJrDbMxDTO> listDbJrDbMxDTO(BdcDbJrXqQO bdcDbJrXqQO) {
        if (StringUtils.isNotBlank(bdcDbJrXqQO.getDjsj())) {
            List<DbJrDbMxDTO> dbJrDbMxDTOList = new ArrayList<>(1);
            Date dbsj = DateUtils.formatDate(bdcDbJrXqQO.getDjsj());
            //?????????????????????
            List<BdcAccessLog> bdcAccessLogList = accessLogMapper.listBdcJrSjjl(dbsj);
            //?????????????????????
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setCurrentDate(dbsj);
            bdcXmQO.setGzldymc(bdcDbJrXqQO.getLcmc());
            bdcXmQO.setSlbh(bdcDbJrXqQO.getSlbh());
            bdcXmQO.setBdcdyh(bdcDbJrXqQO.getBdcdyh());
            List<BdcXmDO> bdcXmDOList = bdcXmMapper.listBdcXm(bdcXmQO);
            Map<String, BdcAccessLog> bdcAccessLogMap = bdcAccessLogList.stream().collect(Collectors.toMap(BdcAccessLog::getYwlsh, bdcAccessLog -> bdcAccessLog, (k1, k2) -> k2));
            //2. ????????????????????????????????????
            Map<String, BdcXmDO> bdcXmDOMap = bdcXmDOList.stream().collect(Collectors.toMap(BdcXmDO::getXmid, bdcXmDO -> bdcXmDO));
            if (CollectionUtils.size(bdcAccessLogList) > CollectionUtils.size(bdcXmDOList)) {
                //???????????????????????????????????????????????????
                for (BdcAccessLog bdcAccessLog : bdcAccessLogList) {
                    if (!bdcXmDOMap.containsKey(bdcAccessLog.getYwlsh())) {
                        BdcXmDO bdcXmDO = bdcXmMapper.queryBdcXm(bdcAccessLog.getYwlsh());
                        DbJrDbMxDTO dbJrDbMxDTO = new DbJrDbMxDTO();
                        if (Objects.nonNull(bdcXmDO)) {
                            dbJrDbMxDTO.setSlbh(bdcXmDO.getSlbh());
                            dbJrDbMxDTO.setBdcdyh(bdcXmDO.getBdcdyh());
                            dbJrDbMxDTO.setBdcqzh(bdcXmDO.getBdcqzh());
                            dbJrDbMxDTO.setZl(bdcXmDO.getZl());
                            dbJrDbMxDTO.setLcmc(bdcXmDO.getGzldymc());
                            dbJrDbMxDTO.setTsyy("?????????????????????????????????????????????????????????????????????");
                        } else {
                            dbJrDbMxDTO.setTsyy("???????????????=" + bdcAccessLog.getYwlsh() + "?????????????????????");
                        }
                        dbJrDbMxDTOList.add(dbJrDbMxDTO);
                    }
                }
            } else if (CollectionUtils.size(bdcAccessLogList) < CollectionUtils.size(bdcXmDOList)) {
                //???????????????????????? ???xmid ??????????????????????????????????????????????????????????????????
                for (BdcXmDO bdcXmDO : bdcXmDOList) {
                    if (!bdcAccessLogMap.containsKey(bdcXmDO.getXmid())) {
                        //???????????????
                        DbJrDbMxDTO dbJrDbMxDTO = new DbJrDbMxDTO();
                        dbJrDbMxDTO.setSlbh(bdcXmDO.getSlbh());
                        dbJrDbMxDTO.setBdcdyh(bdcXmDO.getBdcdyh());
                        dbJrDbMxDTO.setBdcqzh(bdcXmDO.getBdcqzh());
                        dbJrDbMxDTO.setZl(bdcXmDO.getZl());
                        dbJrDbMxDTO.setLcmc(bdcXmDO.getGzldymc());
                        //?????????????????????????????????????????????
                        List<BdcJrCzrzDO> bdcJrCzrzDOList = accessLogMapper.listBdcJrczrzDesc(bdcXmDO.getXmid());
                        if (CollectionUtils.isNotEmpty(bdcJrCzrzDOList)) {
                            dbJrDbMxDTO.setTsyy(bdcJrCzrzDOList.get(0).getCznr());
                        } else {
                            dbJrDbMxDTO.setTsyy("?????????????????????????????????");
                        }
                        dbJrDbMxDTOList.add(dbJrDbMxDTO);
                    }
                }
            }
            return dbJrDbMxDTOList;
        }
        return Collections.emptyList();
    }

    /**
     * @param sbxzDTO@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ????????????
     * @date : 2022/10/6 11:05
     */
    @Override
    public Object lwsb(SbxzDTO sbxzDTO) {
        //1.????????????????????????
        if (Objects.nonNull(sbxzDTO) && CollectionUtils.isNotEmpty(sbxzDTO.getXmidList()) && CollectionUtils.isNotEmpty(sbxzDTO.getIdList())) {
            //????????????
            JgWxzBwxxQO jgWxzBwxxQO = new JgWxzBwxxQO();
            jgWxzBwxxQO.setIdList(sbxzDTO.getIdList());
            List<JgWxzbwxxDO> jgWxzbwxxDOList = accessLogMapper.queryWxzBwxx(jgWxzBwxxQO);
            if (CollectionUtils.isNotEmpty(jgWxzbwxxDOList)) {
                List<BdcSlJrLwDO> bdcSlJrLwDOList = new ArrayList<>(jgWxzbwxxDOList.size());
                TsqxxzDTO tsqxxzDTO = new TsqxxzDTO();
                List<TsqxxzRequest> tsqxxzRequestList = new ArrayList<>(jgWxzbwxxDOList.size());
                for (JgWxzbwxxDO jgWxzbwxxDO : jgWxzbwxxDOList) {
                    TsqxxzRequest tsqxxzRequest = new TsqxxzRequest();
                    tsqxxzRequest.setBdcdyh(jgWxzbwxxDO.getBdcdyh());
                    tsqxxzRequest.setBdcqzh(jgWxzbwxxDO.getZszmh());
                    tsqxxzRequest.setBdlx(jgWxzbwxxDO.getBdlx());
                    tsqxxzRequest.setSjsm(sbxzDTO.getLwyy());
                    tsqxxzRequest.setQxdm(jgWxzbwxxDO.getQxdm());
                    tsqxxzRequest.setYwh(jgWxzbwxxDO.getYwh());
                    tsqxxzRequest.setZbdzt("0");
                    tsqxxzRequestList.add(tsqxxzRequest);
                    //
                    BdcSlJrLwDO bdcSlJrLwDO = new BdcSlJrLwDO();
                    bdcSlJrLwDO.setWxzid(jgWxzbwxxDO.getId());
                    bdcSlJrLwDO.setLwry(userManagerUtils.getUserAlias());
                    bdcSlJrLwDO.setLwsj(new Date());
                    bdcSlJrLwDO.setLwyy(sbxzDTO.getLwyy());
                    bdcSlJrLwDOList.add(bdcSlJrLwDO);
                }
                tsqxxzDTO.setData(tsqxxzRequestList);
                LOGGER.warn("??????????????????????????????????????????{}", JSON.toJSONString(tsqxxzDTO));
                Object response = exchangeInterfaceFeignService.requestInterface("sjpt_tsqxxz", tsqxxzDTO);
                LOGGER.warn("?????????????????????????????????????????????{}", JSON.toJSONString(response));
                //2.??????????????????????????????
                if (Objects.nonNull(response)) {
                    Map resultMap = (Map) response;
                    Map headMap = MapUtils.getMap(resultMap, "head", null);
                    if (MapUtils.isNotEmpty(headMap) && StringUtils.equals(CommonConstantUtils.SUCCESS_CODE_0000, MapUtils.getString(headMap, "code", "")) && CollectionUtils.isNotEmpty(bdcSlJrLwDOList)) {
                        //??????????????????
                        accesssModelHandlerService.updateXzztByid(sbxzDTO.getIdList(), "3");
                        return bdcSlJrlwFeignService.saveOrUpdateJrlw(bdcSlJrLwDOList);
                    }
                }
            }
        }
        return 0;
    }

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ????????????????????????????????????
     * @date : 2022/12/14 11:23
     */
    @Override
    public AccessLogDataService getAccessLogService() {
        String cheat = EnvironmentConfig.getEnvironment().getProperty("accessLog.turn-on-cheating", "false");
        String service = "accessLogDataServiceImpl";
        if (StringUtils.isNotBlank(cheat) && StringUtils.equals(CommonConstantUtils.BOOL_TRUE, cheat)) {
            service = "accessLogCheatServiceImpl";
        }
        LOGGER.warn("????????????????????????????????????????????????{}", service);
        Object accessLogDataService = Container.getBean(service);
        return (AccessLogDataService) accessLogDataService;
    }
}
