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
 * @description 日志上报 service 实现
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
     * @description 读取配置文件中的 外市县列表
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
        LOGGER.info("开始登簿日志上报，上报日期{}、区划{}", accessDate, xzdm);
        try {
            List<Map<String, String>> qxdmlist = bdcdjMapper.queryBdcZdQx();
            LOGGER.info("需要上报的区县有{}", qxdmlist);
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
                        LOGGER.error("当前区县{}登簿日志上报异常", map.get("DM"), e);
                    }
                }
            } else {
                LOGGER.error("接入登簿日志失败，请检查BDC_ZD_QX表数据");
            }
        } catch (Exception e) {
            LOGGER.error("接入登簿日志失败", e);
        }
    }

    /**
     * @param accessDate
     * @param qxdm
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 市级手动上报
     * @date : 2023/1/6 15:24
     */
    @Override
    public void cityAccessLog(Date accessDate, String qxdm) {
        try {
            List<Map<String, String>> qxdmlist = bdcdjMapper.queryBdcZdQx();
            LOGGER.info("开始市级登簿日志上报，上报日期{}、区划{},所有区划信息{}", accessDate, qxdm, qxdmlist);
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
                        LOGGER.error("当前区县{}登簿日志上报异常", map.get("DM"), e);
                    }
                }
            } else {
                LOGGER.error("接入登簿日志失败，请检查BDC_ZD_QX表数据");
            }
        } catch (Exception e) {
            LOGGER.error("接入登簿日志失败", e);
        }
    }

    /**
     * @param accessDate
     * @param qxdm
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 登簿日志明细预览
     * @date : 2022/10/13 9:19
     */
    @Override
    public Map<String, Object> dbrzmxyl(Date accessDate, String qxdm, String type) {
        String sbType = configInit.getConditionTime();
        String conditionTime = StringUtils.isNotBlank(sbType) ? sbType : "dbsj";
        List<Map<String, String>> qxdmlist = bdcdjMapper.queryBdcZdQx();
        //获取上报的登薄日志
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
                    LOGGER.error("未发现登簿日志上报服务");
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
        LOGGER.warn("当前登簿日志上报时间{}，上报区县{}", accessDate, qxdm);
        return dbrzMx;
    }

    /**
     * @param pageable
     * @param map
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询上报日志
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
     * @description 分页查询外市县
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
     * @description 查询外市县权利人列表
     */
    private List<ZttGyQlrDO> listWsxQlrByYwh(Map map) {
        return accessLogMapper.listZttGyQlrByYwh(map);
    }

    /**
     * @param accessLogDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 给日志结果填充 QLR 和 BDCQZH
     */
    private void fillContent(AccessLogDTO accessLogDTO, Map map) {
        if (StringUtils.isNotBlank(accessLogDTO.getYwh())) {
            // 处理权利人 和BDCQZH  拼接
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
     * @description 获取响应报文
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
                    msg = "获取汇交结果结束,请查看!";
                } catch (Exception e) {
                    logger.error("errorMsg:", e);
                    msg = "获取汇交结果失败！";
                }
            } else {
                msg = "业务报文主键为空，无法获取汇交结果";
            }
        }
        return msg;
    }

    /**
     * @param ywh
     * @param logType
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 读取响应报文XML
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
        return "没有响应报文";
    }

    /**
     * @param ywh
     * @param logType
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 读取响应报文XML
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
        return "没有响应报文";
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
     * @param accessDate    上报时间
     * @param qxdm          区县代码
     * @param qxmc          区县名称
     * @param conditionTime 限制时间
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 分区县日志上报
     */
    public boolean accessLogsByQxdm(String qxdm, String qxmc, String conditionTime, Date accessDate, boolean insertJrdbrzFlag) {
        //获取上报的登薄日志
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
        //日志对象
        BdcJrDbrzjlDO bdcJrDbrzjlDO = new BdcJrDbrzjlDO();
        //报文xml
        String xml = "";
        AccessNewLogs accessNewLogs = new AccessNewLogs();
        LOGGER.warn("当前登簿日志上报时间{}，上报区县{}", accessDate, qxdm);
        try {
            AccessLogDataService accessService = this.getAccessLogService();
            if (Objects.isNull(accessService)) {
                LOGGER.error("未发现登簿日志上报服务");
                return false;
            }
            //获取报文
            accessNewLogs = accessService.getAccessNewLogs(param, accessDate, false);
            if (null != accessNewLogs) {
                xml = xmlEntityConvertUtil.entityToXml(accessNewLogs);
            }

            xml = xml.replaceAll("\\s*xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", "");
            LOGGER.info("当前登簿日志上报已成功生成报文，准备上报省厅，上报区县{}，报文内容：{}", qxdm, xml);

            //部级webservice上报
            if (configInit.isNationalEnble()) {
                accessLogSftpService.nationalAccessLogWeb(xml, bdcJrDbrzjlDO);
            }

            //省级webservice上报
            if (configInit.isProvinceEnble()) {
                accessLogSftpService.provinceAccessLogWeb(xml, bdcJrDbrzjlDO);
            }

            //市级上报
            if (configInit.getCityEnable()) {
                LOGGER.warn("配置了市级上报,开始查询bdc_jr_shijjl");
                param.put("logTable", "bdc_jr_shijjl");
                accessNewLogs = accessService.getAccessNewLogs(param, accessDate, false);
                if (null != accessNewLogs) {
                    xml = xmlEntityConvertUtil.entityToXml(accessNewLogs);
                }

                xml = xml.replaceAll("\\s*xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", "");
                LOGGER.info("当前登簿日志上报已成功生成报文，准备上报市级，上报区县{}，报文内容：{}", qxdm, xml);
                accessLogSftpService.cityAccessLogWeb(xml, bdcJrDbrzjlDO);
            }

            //本地保存xml
            if (StringUtils.isNotBlank(configInit.getXmlPath())) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(qxdm).append("-").append(DateUtil.formateTimeYmdhms(accessDate));
                accessLogSftpService.generateXmlAndFtp(xml, stringBuilder.toString());
            }

        } catch (Exception e) {
            LOGGER.error("区县{}接入登簿日志失败",qxdm, e);
            //发送通知
            MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
            msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_7.getYjlx());
            msgNoticeDTO.setSbsj(DateUtil.formateTime(accessDate));
            accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
        } finally {
            LOGGER.info("区县:{}执行登簿日志数据插入开始: {}", qxmc, JSONObject.toJSONString(bdcJrDbrzjlDO));
            if (insertJrdbrzFlag) {
                //更改为sql语句更新，不修改do对象,特殊字段不用添加在do对象中
                bdcJrDbrzjlDO = getBdcJrDbrzjlDO(bdcJrDbrzjlDO, xml, accessDate, qxdm);
                //南通登簿日志上报特殊处理: 部分自定义的区县需要通过xm表的qxdm去统计展示登簿日志量，非bdcdyh前六位
                if (StringUtils.isNotBlank(dataVersion) && CommonConstantUtils.NANTONG.equals(dataVersion)) {
                    JSONObject bdcJrDbrzjlJsonObject = JSON.parseObject(JSON.toJSONString(bdcJrDbrzjlDO));
                    //获取报文
                    //报文xml
                    String xnxml = "";
                    AccessLogDataService accessService = this.getAccessLogService();
                    if (Objects.isNull(accessService)) {
                        LOGGER.error("未发现登簿日志上报服务");
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

                //保存登簿日志详情数据
                if (Objects.nonNull(accessNewLogs) && CollectionUtils.isNotEmpty(accessNewLogs.getAccessNewLogList()) && Objects.nonNull(accessNewLogs.getAccessNewLogList().get(0).getRegisterList())) {
                    if (CollectionUtils.isNotEmpty(accessNewLogs.getAccessNewLogList().get(0).getRegisterList().getRegister())) {
                        /*
                         * 改为异步处理
                         * */
                        asyncDealUtils.plbcDbrzXq(accessNewLogs.getAccessNewLogList().get(0).getRegisterList().getRegister(), bdcJrDbrzjlDO.getId());
                    }
                }
            }
            if (!CommonConstantUtils.SF_S_DM.equals(bdcJrDbrzjlDO.getSjcgbs())) {
                //判断当前时间的当前区县的登簿日志是否有记录
                List<BdcJrDbrzjlDO> list = accessLogMapper.listBdcJrDbrzjlList(qxdm, accessDate);
                //做多处理三次  20分钟后再次上报
                if (CollectionUtils.isEmpty(list)) {
                    LOGGER.info("登簿日志上报失败二十分钟后重新上报: {}", JSONObject.toJSONString(bdcJrDbrzjlDO));
                    //增加定时任务处理  先判定当天上报次数
                    new Timer("登簿日志重新上报").schedule(new TimerTask() {
                        @Override
                        public void run() {
                            accessLog(accessDate, qxdm);
                        }
                    }, new Date(System.currentTimeMillis() + 20 * 60 * 1000));

                    //发送通知
                    MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
                    msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_14.getYjlx());
                    msgNoticeDTO.setSbsj(DateUtil.formateTime(accessDate));
                    accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
                }
                return false;
            }
            LOGGER.info("区县{}执行登簿日志数据插入结束", qxmc);
            return true;
        }
    }


    public boolean cityAccess(String qxdm, String qxmc, Date accessDate) {
        //获取上报的登薄日志
        boolean result = true;
        Map param = new HashMap();
        param.put("qxdm", qxdm);
        param.put("qxmc", qxmc);
        param.put("accessDate", accessDate);
        param.put("logTable", "bdc_jr_shijjl");
        //日志对象
        BdcJrDbrzjlDO bdcJrDbrzjlDO = new BdcJrDbrzjlDO();
        //报文xml
        String xml = "";
        AccessNewLogs accessNewLogs = new AccessNewLogs();
        LOGGER.warn("当前登簿日志市级上报时间{}，上报区县{}", accessDate, qxdm);
        try {
            AccessLogDataService accessService = this.getAccessLogService();
            if (Objects.isNull(accessService)) {
                LOGGER.error("未发现登簿日志上报服务");
                return false;
            }
            //获取报文
            accessNewLogs = accessService.getAccessNewLogs(param, accessDate, false);
            if (null != accessNewLogs) {
                xml = xmlEntityConvertUtil.entityToXml(accessNewLogs);
            }

            xml = xml.replaceAll("\\s*xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", "");
            LOGGER.info("当前登簿日志上报已成功生成报文，准备上报市级，上报区县{}，报文内容：{}", qxdm, xml);
            accessLogSftpService.cityAccessLogWeb(xml, bdcJrDbrzjlDO);
            //本地保存xml
            if (StringUtils.isNotBlank(configInit.getXmlPath())) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(qxdm).append("-").append(DateUtil.formateTimeYmdhms(accessDate));
                accessLogSftpService.generateXmlAndFtp(xml, stringBuilder.toString());
            }
        } catch (Exception e) {
            result = false;
            LOGGER.error("区县{}接入市级登簿日志失败", qxdm, e);
            //发送通知
            MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
            msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_7.getYjlx());
            msgNoticeDTO.setSbsj(DateUtil.formateTime(accessDate));
            accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
        } finally {
            LOGGER.info("区县:{}执行市级登簿日志数据插入开始: {}", qxmc, JSONObject.toJSONString(bdcJrDbrzjlDO));
            //更改为sql语句更新，不修改do对象,特殊字段不用添加在do对象中
            bdcJrDbrzjlDO = getBdcJrDbrzjlDO(bdcJrDbrzjlDO, xml, accessDate, qxdm);
            entityMapper.insertSelective(bdcJrDbrzjlDO);
            //保存登簿日志详情数据
            if (Objects.nonNull(accessNewLogs) && CollectionUtils.isNotEmpty(accessNewLogs.getAccessNewLogList()) && Objects.nonNull(accessNewLogs.getAccessNewLogList().get(0).getRegisterList())) {
                if (CollectionUtils.isNotEmpty(accessNewLogs.getAccessNewLogList().get(0).getRegisterList().getRegister())) {
                    /*
                     * 改为异步处理
                     * */
                    asyncDealUtils.plbcDbrzXq(accessNewLogs.getAccessNewLogList().get(0).getRegisterList().getRegister(), bdcJrDbrzjlDO.getId());
                }
            }
            LOGGER.info("区县{}执行市级登簿日志数据插入结束", qxmc);
        }
        return result;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 组织存库的实体类
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
     * @description 定时任务获取省级接入日志 并更新处理标志
     */
    @Override
    @RedissonLock(lockKey = "EXCHANGE_QUARZ_SJJR", description = "定时任务处理省级日志")
    public List<BdcAccessLog> queryAndUpdateSjjr() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("num", Constants.ACCESSRESPONSE_QUARTZ_PERUPDATE_TIMES);
        paramMap.put("logType", Constants.ACCESS_TYPE_PROVINCE);
        paramMap.put("afterDate", DateUtil.dealDate(DateUtil.getPastDate(Constants.ACCESSRESPONSE_QUARTZ_BEFOREDATE), "00:00:00"));
        List<BdcAccessLog> jrrzList = accessLogMapper.listAccessLogWithNoXybm(paramMap);
        // 更新接入日志的FLAG
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
     * @description 更新 处理标志
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
     * 校验上报响应数据并发送短信
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
                    //发送短信
                    if (StringUtils.isNotBlank(msgType) && StringUtils.isNotBlank(dhs) && dhs.contains(",")) {
                        for (String dh : dhs.split(",")) {
                            sendMsgService.smsMsg(collect, dh, msgType);
                            sendMsgService.saveMsgLog(null, String.valueOf(collect), dh, "0", "发送成功");
                        }
                    } else if (StringUtils.isNotBlank(msgType) && StringUtils.isNotBlank(dhs) && !dhs.contains(",")) {
                        sendMsgService.smsMsg(collect, dhs, msgType);
                        sendMsgService.saveMsgLog(null, String.valueOf(collect), dhs, "0", "发送成功");
                    }
                }
            } catch (Exception e) {
                sendMsgService.saveMsgLog(null, null, dhs, "1", "未知异常:" + e.getMessage());
            }
        }
    }

    /*
     * 查询登簿数据量
     * */
    @Override
    public int queryDbSjl(BdcDbJrXqQO bdcDbJrXqQO) {
        return accessLogMapper.queryDbsjl(bdcDbJrXqQO);
    }

    /*
     * 查询接入数据量
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
     * 查询登簿日志明细数据量
     * */
    @Override
    public int queryDbrzXqSjl(BdcDbJrXqQO bdcDbJrXqQO) {
        return accessLogMapper.queryDbrzxqSjl(bdcDbJrXqQO);
    }

    /**
     * @param xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询接入操作日志
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
            //查当天的接入量
            List<BdcAccessLog> bdcAccessLogList = accessLogMapper.listBdcJrSjjl(dbsj);
            //查当天的登簿量
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setCurrentDate(dbsj);
            bdcXmQO.setGzldymc(bdcDbJrXqQO.getLcmc());
            bdcXmQO.setSlbh(bdcDbJrXqQO.getSlbh());
            bdcXmQO.setBdcdyh(bdcDbJrXqQO.getBdcdyh());
            List<BdcXmDO> bdcXmDOList = bdcXmMapper.listBdcXm(bdcXmQO);
            Map<String, BdcAccessLog> bdcAccessLogMap = bdcAccessLogList.stream().collect(Collectors.toMap(BdcAccessLog::getYwlsh, bdcAccessLog -> bdcAccessLog, (k1, k2) -> k2));
            //2. 查询项目表当天的登簿数据
            Map<String, BdcXmDO> bdcXmDOMap = bdcXmDOList.stream().collect(Collectors.toMap(BdcXmDO::getXmid, bdcXmDO -> bdcXmDO));
            if (CollectionUtils.size(bdcAccessLogList) > CollectionUtils.size(bdcXmDOList)) {
                //如果接入量大于登簿量，返回异常数据
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
                            dbJrDbMxDTO.setTsyy("异常数据，当天接入日志存在信息，未找到登簿信息");
                        } else {
                            dbJrDbMxDTO.setTsyy("业务流水号=" + bdcAccessLog.getYwlsh() + "未找到相关项目");
                        }
                        dbJrDbMxDTOList.add(dbJrDbMxDTO);
                    }
                }
            } else if (CollectionUtils.size(bdcAccessLogList) < CollectionUtils.size(bdcXmDOList)) {
                //接入量小于登簿量 用xmid 查接入操作日志表根据时间倒叙第一条的操作内容
                for (BdcXmDO bdcXmDO : bdcXmDOList) {
                    if (!bdcAccessLogMap.containsKey(bdcXmDO.getXmid())) {
                        //登簿未接入
                        DbJrDbMxDTO dbJrDbMxDTO = new DbJrDbMxDTO();
                        dbJrDbMxDTO.setSlbh(bdcXmDO.getSlbh());
                        dbJrDbMxDTO.setBdcdyh(bdcXmDO.getBdcdyh());
                        dbJrDbMxDTO.setBdcqzh(bdcXmDO.getBdcqzh());
                        dbJrDbMxDTO.setZl(bdcXmDO.getZl());
                        dbJrDbMxDTO.setLcmc(bdcXmDO.getGzldymc());
                        //查询接入操作日志时间倒叙第一条
                        List<BdcJrCzrzDO> bdcJrCzrzDOList = accessLogMapper.listBdcJrczrzDesc(bdcXmDO.getXmid());
                        if (CollectionUtils.isNotEmpty(bdcJrCzrzDOList)) {
                            dbJrDbMxDTO.setTsyy(bdcJrCzrzDOList.get(0).getCznr());
                        } else {
                            dbJrDbMxDTO.setTsyy("未找到相关接入操作日志");
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
     * @description 例外上报
     * @date : 2022/10/6 11:05
     */
    @Override
    public Object lwsb(SbxzDTO sbxzDTO) {
        //1.调用接口例外上报
        if (Objects.nonNull(sbxzDTO) && CollectionUtils.isNotEmpty(sbxzDTO.getXmidList()) && CollectionUtils.isNotEmpty(sbxzDTO.getIdList())) {
            //循环上报
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
                LOGGER.warn("上报销账台账例外上报接口入参{}", JSON.toJSONString(tsqxxzDTO));
                Object response = exchangeInterfaceFeignService.requestInterface("sjpt_tsqxxz", tsqxxzDTO);
                LOGGER.warn("上报销账台账例外上报接口返回值{}", JSON.toJSONString(response));
                //2.成功后存入里外表数据
                if (Objects.nonNull(response)) {
                    Map resultMap = (Map) response;
                    Map headMap = MapUtils.getMap(resultMap, "head", null);
                    if (MapUtils.isNotEmpty(headMap) && StringUtils.equals(CommonConstantUtils.SUCCESS_CODE_0000, MapUtils.getString(headMap, "code", "")) && CollectionUtils.isNotEmpty(bdcSlJrLwDOList)) {
                        //更新销账状态
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
     * @description 获取登簿日志上报的实现类
     * @date : 2022/12/14 11:23
     */
    @Override
    public AccessLogDataService getAccessLogService() {
        String cheat = EnvironmentConfig.getEnvironment().getProperty("accessLog.turn-on-cheating", "false");
        String service = "accessLogDataServiceImpl";
        if (StringUtils.isNotBlank(cheat) && StringUtils.equals(CommonConstantUtils.BOOL_TRUE, cheat)) {
            service = "accessLogCheatServiceImpl";
        }
        LOGGER.warn("当前系统配置的登簿日志实现服务为{}", service);
        Object accessLogDataService = Container.getBean(service);
        return (AccessLogDataService) accessLogDataService;
    }
}
