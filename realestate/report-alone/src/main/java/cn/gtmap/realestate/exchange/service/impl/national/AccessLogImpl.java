package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.common.core.bo.accessnewlog.AccessNewLogs;
import cn.gtmap.realestate.exchange.config.ConfigInit;
import cn.gtmap.realestate.exchange.core.domain.BdcJrDbrzjlDO;
import cn.gtmap.realestate.exchange.core.domain.exchange.BdcJrCzrzDO;
import cn.gtmap.realestate.exchange.core.domain.exchange.ZttGyQlrDO;
import cn.gtmap.realestate.exchange.core.dto.AccessLogDTO;
import cn.gtmap.realestate.exchange.core.dto.accessLog.AccessLogs;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.accessLog.AccessLogMapper;
import cn.gtmap.realestate.exchange.core.national.BdcAccessLog;
import cn.gtmap.realestate.exchange.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.exchange.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.exchange.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.exchange.service.impl.national.upload.NationalAccessUploadFtpImpl;
import cn.gtmap.realestate.exchange.service.impl.national.upload.NationalAccessUploadSftpImpl;
import cn.gtmap.realestate.exchange.service.impl.national.upload.ProvinceAccessUploadFtpImpl;
import cn.gtmap.realestate.exchange.service.impl.national.upload.ProvinceAccessUploadSftpImpl;
import cn.gtmap.realestate.exchange.service.national.NationalAccessUpload;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogService;
import cn.gtmap.realestate.exchange.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
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
    AccessLogDataServiceImpl accessLogDataService;
    @Autowired
    ConfigInit configInit;

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
    /*  @Autowired
      private SendMsgService sendMsgService;*/
	/*@Autowired
	private BdcZdFeignService bdcZdFeignService;*/
    @Value("${access.filter.history.data.flag:false}")
    private boolean accessFilterHistoryDataFlag;
    @Value("${data.version:}")
    private String dataVersion;
    @Value("${check.access.response.error.dh:}")
    private String dhs;
    @Value("${check.access.response.error.msg.type:}")
    private String msgType;
    //是否登簿日志详情模式
    @Value("${accessLog.turn-on-details: false}")
    private boolean accessTurnOnDetails;

    @Autowired
    AsyncDealUtils asyncDealUtils;
    /**
     * 判断上次定时任务是够结束的标识状态
     *
     * @author lst
     * @description 判断上次定时任务是够结束的标识状态(true : 已结束, false : 未结束)，未结束不会开启第二次任务
     */
    private boolean isFinish = true;

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
        if (isFinish) {
            LOGGER.info("进入登簿日志上报！");
            //进入赋值false
            isFinish = false;
            try {
                List<Map<String, String>> qxdmlist = bdcdjMapper.queryBdcZdQx();
                String sbType = configInit.getConditionTime();
                String conditionTime = StringUtils.isNotBlank(sbType) ? sbType : "dbsj";
                if (CollectionUtils.isNotEmpty(qxdmlist)) {
                    for (Map<String, String> map : qxdmlist) {
                        if (StringUtils.isNotBlank(xzdm)) {
                            while (StringUtils.equals(MapUtils.getString(map, "DM"), xzdm)) {
                                accessLogsByQxdm(xzdm, MapUtils.getString(map, "MC"), conditionTime, accessDate, true);
                                break;
                            }
                        } else {
                            String qxdm = String.valueOf(map.get("DM"));
                            accessLogsByQxdm(qxdm, MapUtils.getString(map, "MC"), conditionTime, accessDate, true);
                        }
                    }
                } else {
                    LOGGER.info("接入登簿日志失败，请检查djsj_zd_xzdm表数据");
                }
            } catch (Exception e) {
                LOGGER.error("接入登簿日志失败", e);
            } finally {
                isFinish = true;
            }
        }
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
        return repository.selectPaging("listAccessLogByPage", map, pageable);
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
        try {
            //获取报文
            if (accessTurnOnDetails) {
                accessNewLogs = accessLogDataService.getAccessNewLogs(param, accessDate, false);
                if (null != accessNewLogs) {
                    xml = xmlEntityConvertUtil.entityToXml(accessNewLogs);
                    LOGGER.error("详情接入登簿日志:{}", xml);

                }
            } else {
                AccessLogs accessLogs = accessLogDataService.getAccessLogs(param, accessDate, false);
                if (null != accessLogs) {
                    xml = xmlEntityConvertUtil.entityToXml(accessLogs);
                    LOGGER.error("接入登簿日志:{}", xml);

                }
            }

            xml = xml.replaceAll("\\s*xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", "");

            //部级webservice上报
            if (configInit.isNationalEnble()) {
                accessLogSftpService.nationalAccessLogWeb(xml, bdcJrDbrzjlDO);
            }

            //省级webservice上报
            if (configInit.isProvinceEnble()) {
                LOGGER.info("进入省级webservice上报登簿日志上报！");
                accessLogSftpService.provinceAccessLogWeb(xml, bdcJrDbrzjlDO);
                LOGGER.info("省级webservice上报完成！");

            }

            //本地保存xml
            if (StringUtils.isNotBlank(configInit.getXmlPath())) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(qxdm).append("-").append(DateUtil.formateTimeYmdhms(accessDate));
                accessLogSftpService.generateXmlAndFtp(xml, stringBuilder.toString());
            }

        } catch (Exception e) {
            LOGGER.error("接入登簿日志失败", e);
        } finally {
            LOGGER.info("执行登簿日志数据插入开始: {}", JSONObject.toJSONString(bdcJrDbrzjlDO));
            if (insertJrdbrzFlag) {
                //更改为sql语句更新，不修改do对象,特殊字段不用添加在do对象中
                bdcJrDbrzjlDO = getBdcJrDbrzjlDO(bdcJrDbrzjlDO, xml, accessDate, qxdm);
                //南通登簿日志上报特殊处理: 部分自定义的区县需要通过xm表的qxdm去统计展示登簿日志量，非bdcdyh前六位
                if (StringUtils.isNotBlank(dataVersion) && CommonConstantUtils.NANTONG.equals(dataVersion)) {
                    JSONObject bdcJrDbrzjlJsonObject = JSON.parseObject(JSON.toJSONString(bdcJrDbrzjlDO));
                    //获取报文
                    AccessLogs accessLogs = accessLogDataService.getAccessLogs(param, accessDate, true);
                    String xnxml = xmlEntityConvertUtil.entityToXml(accessLogs);
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
/*            if (!CommonConstantUtils.SF_S_DM.equals(bdcJrDbrzjlDO.getSjcgbs())) {
                List<BdcJrDbrzjlDO> list = accessLogMapper.listBdcJrDbrzjl(DateUtil.getCurDate());
                //做多处理2次  20分钟后再次上报
                if (CollectionUtils.isEmpty(list) || list.size() < 2) {
                    //增加定时任务处理  先判定当天上报次数
                    new Timer("登簿日志重新上报").schedule(new TimerTask() {
                        @Override
                        public void run() {
                            accessLog(DateUtil.getCurDate(), null);
                        }
                    }, new Date(System.currentTimeMillis() + 20 * 60 * 1000));
                }
                LOGGER.info("执行登簿日志数据插入结束: {}", JSONObject.toJSONString(bdcJrDbrzjlDO));
                return false;
            }*/
            LOGGER.info("执行登簿日志数据插入结束: {}", JSONObject.toJSONString(bdcJrDbrzjlDO));
            return true;
        }
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
//    @RedissonLock(lockKey = "EXCHANGE_QUARZ_SJJR", description = "定时任务处理省级日志")
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
            AccessLogs accessLogs;
//    		if (StringUtils.isNotBlank(dataVersion) && CommonConstantUtils.NANTONG.equals(dataVersion)){
//                String xnjrbw = bdcdjMapper.queryBdcJrDbrzjlXnjrbwWithXnjrbwById(id);
//                accessLogs = (AccessLogs)xmlEntityConvertUtil.xmlToEntity(AccessLogs.class, new StringReader(xnjrbw));
//            }else {
//                accessLogs = (AccessLogs)xmlEntityConvertUtil.xmlToEntity(AccessLogs.class, new StringReader(bdcJrDbrzjlDO.getJrbw()));
//            }
            accessLogs = (AccessLogs) xmlEntityConvertUtil.xmlToEntity(AccessLogs.class, new StringReader(bdcJrDbrzjlDO.getJrbw()));

            if (accessLogs != null && CollectionUtils.isNotEmpty(accessLogs.getAccessLogList())) {
                dbrzMx.put("accessLog", accessLogs.getAccessLogList().get(0));
            }
        }
        List<Map<String, String>> qxMapList = bdcdjMapper.queryBdcZdQx();
        dbrzMx.put("qx", qxMapList);

        return dbrzMx;
    }

    /**
     * @param xmidList
     * @param xzzt
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新未销账报文信息
     * @date : 2022/7/25 15:43
     */
    @Override
    public void updateXzzt(List<String> xmidList, String xzzt) {
        if (CollectionUtils.isNotEmpty(xmidList) && StringUtils.isNotBlank(xzzt)) {
            accessLogMapper.updateXzzt(xmidList, xzzt);
        }
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
   /* @Override
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
                    if (StringUtils.isNotBlank(msgType) && StringUtils.isNotBlank(dhs) && dhs.contains(",")){
                        for (String dh : dhs.split(",")) {
                            sendMsgService.smsMsg(collect, dh, msgType);
                            sendMsgService.saveMsgLog(null, String.valueOf(collect), dh, "0", "发送成功");
                        }
                    }else if (StringUtils.isNotBlank(msgType) && StringUtils.isNotBlank(dhs) && !dhs.contains(",")){
                        sendMsgService.smsMsg(collect, dhs, msgType);
                        sendMsgService.saveMsgLog(null, String.valueOf(collect), dhs, "0", "发送成功");
                    }
                }
            }catch (Exception e){
                sendMsgService.saveMsgLog(null, null, dhs, "1", "未知异常:"+e.getMessage());
            }
        }
    }*/
}
