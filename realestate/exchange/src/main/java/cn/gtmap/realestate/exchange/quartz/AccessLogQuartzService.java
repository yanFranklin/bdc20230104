package cn.gtmap.realestate.exchange.quartz;

import cn.gtmap.realestate.common.core.annotations.RedissonLock;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.RestRpcUtils;
import cn.gtmap.realestate.exchange.core.mapper.server.accessLog.AccessLogMapper;
import cn.gtmap.realestate.exchange.core.national.BdcAccessLog;
import cn.gtmap.realestate.exchange.core.national.CityAccess;
import cn.gtmap.realestate.exchange.core.national.NationalAccess;
import cn.gtmap.realestate.exchange.core.national.ProvinceAccess;
import cn.gtmap.realestate.exchange.core.qo.BdcJrrzQO;
import cn.gtmap.realestate.exchange.service.impl.national.access.AccessLogImpl;
import cn.gtmap.realestate.exchange.service.impl.national.upload.NationalAccessUploadFtpImpl;
import cn.gtmap.realestate.exchange.service.impl.national.upload.NationalAccessUploadSftpImpl;
import cn.gtmap.realestate.exchange.service.national.AccesssModelHandlerService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessUpload;
import cn.gtmap.realestate.exchange.util.AsyncDealUtils;
import cn.gtmap.realestate.exchange.util.DateUtil;
import cn.gtmap.realestate.exchange.util.UploadServiceUtil;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
 * @version 1.0  2018-12-17
 * @description
 */
@Service
public class AccessLogQuartzService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessLogQuartzService.class);

    /**
     * 登簿日志上报接口
     */
    private static final String DBRZJK = "/realestate-exchange/rest/v1.0/access/dbrz?date=#{date}";

    @Value("${access.response.enable:}")
    private String quartz;

    /**
     * ftp状态检测是否开启
     */
    @Value("${access.check.sbfwq-status:false}")
    private boolean sbfwqStatusCheck;

    /*
     * 接入失败补偿上报
     * */
    @Value("${access.bcsb.enable:false}")
    private boolean jrsbbcsb;

    /**
     * 登簿日志上报固定IP应用上报（配置值例如 192.168.2.111:8080）
     */
    @Value("${accessLog.gdyyip:}")
    private String accessLogGdyyip;

    /**
     * 定时获取上报响应报文默认查询几天内数据
     */
    @Value("${access.response.days:2}")
    private Integer accessResponseDays;

    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private RestRpcUtils restRpcUtils;
    @Autowired
    private AccessLogImpl accessLog;

    @Autowired
    AccessLogMapper accessLogMapper;

    @Autowired
    AccesssModelHandlerService accesssModelHandlerService;

    @Autowired
    AsyncDealUtils asyncDealUtils;


    /**
     * @param
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 定时接入日志
     */
    @RedissonLock(lockKey = Constants.ACCESS_LOG_TASK_JOB_NAME, description = "定时任务登簿日志上报", waitTime = 1L, leaseTime = 300L)
    public void accessLogs() {
        LOGGER.info("定时接入日志 开始===========>");
        Date accessDate = DateUtil.getCurDate();

        if(StringUtils.isNotBlank(accessLogGdyyip)) {
            fixedAppAccessLog(accessDate);
        } else {
            accessLog.accessLog(accessDate, null);
        }
        LOGGER.info("定时接入日志 结束===========>");
    }

    /**
     * 采用指定IP应用进行登簿日志上报
     * 原因：登簿日志台账上报和程序自动上报不是同一个ip，省厅统计登簿日志数量时，按照部里制定的规则，不同ip会默认为两个地区上传的登簿日志，数量会累加。
     * @param accessDate 当前日期
     */
    private void fixedAppAccessLog(Date accessDate) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("date", DateFormatUtils.format(accessDate, DateUtils.sdf));

        try {
            LOGGER.info("定时登簿日志上报采用固定IP应用模式，调用应用：{}，服务地址：{}", accessLogGdyyip, DBRZJK);
            restRpcUtils.getRpcRequest(accessLogGdyyip, DBRZJK, params);
        } catch (Exception e) {
            LOGGER.error("定时登簿日志上报采用固定IP应用模式异常，继续采用调用当前应用上报逻辑，固定IP模式调用应用：{}、服务地址：{}", accessLogGdyyip, DBRZJK, e);
            accessLog.accessLog(accessDate, null);
        }
    }

    /**
     * @param
     * @return
     * @author <a herf="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 定时批量获取响应报文
     */
    @RedissonLock(lockKey = Constants.ACCESS_XYBW_TASK_JOB_NAME, description = "定时任务响应报文获取", waitTime = 1L, leaseTime = 300L)
    public void quartzGetAccessResponse() {
        if (StringUtils.equals(quartz, "true")) {
            List<NationalAccessUpload> list = UploadServiceUtil.listNationalAccessUpload();
            //获取响应
            for (NationalAccessUpload nationalAccessUpload : list) {
                Example example = null;
                if (nationalAccessUpload instanceof NationalAccessUploadFtpImpl || nationalAccessUpload instanceof NationalAccessUploadSftpImpl) {
                    example = new Example(NationalAccess.class);
                } else {
                    example = new Example(ProvinceAccess.class);
                }
                Example.Criteria criteria = example.createCriteria();
                criteria.andGreaterThan("jrrq", getAccessResponseStartDate());
                criteria.andEqualNvlTo("cgbs", "0", "-1");
                example.setOrderByClause("updatetime asc");
                List<BdcAccessLog> listLog = entityMapper.selectByExample(example);
                LOGGER.info("定时批量获取响应报文{}条", CollectionUtils.size(listLog));
                if (CollectionUtils.isNotEmpty(listLog)) {
                    List<String> listId = new ArrayList<>();
                    for (BdcAccessLog bdcAccessLog : listLog) {
                        if (StringUtils.isNotBlank(bdcAccessLog.getYwbwid())) {
                            listId.add(bdcAccessLog.getYwbwid());
                        }
                    }
                    nationalAccessUpload.getReponse(StringUtils.join(listId, CommonConstantUtils.ZF_YW_DH));
                }
            }
        }
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 定时任务获取市级接入响应消息
     * @date : 2023/1/5 17:41
     */
    @Scheduled(cron = "${access.response.quartztime:0 0 20 * * ?}")
    @RedissonLock(lockKey = Constants.ACCESS_SJXYBW_TASK_JOB_NAME, description = "获取市级接入响应消息", waitTime = 1L, leaseTime = 300L)
    public void getCityAccessResponse() {
        if (StringUtils.equals(quartz, "true")) {
            List<NationalAccessUpload> list = UploadServiceUtil.listNationalAccessUploadByType(UploadServiceUtil.CITY_ACCESS);
            //获取响应
            for (NationalAccessUpload nationalAccessUpload : list) {
                Example example = new Example(CityAccess.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andGreaterThan("jrrq", getAccessResponseStartDate());
                criteria.andEqualNvlTo("cgbs", "0", "-1");
                example.setOrderByClause("updatetime asc");
                List<BdcAccessLog> listLog = entityMapper.selectByExample(example);
                LOGGER.info("定时批量获取响应报文{}条", CollectionUtils.size(listLog));
                if (CollectionUtils.isNotEmpty(listLog)) {
                    List<String> listId = new ArrayList<>();
                    for (BdcAccessLog bdcAccessLog : listLog) {
                        if (StringUtils.isNotBlank(bdcAccessLog.getYwbwid())) {
                            listId.add(bdcAccessLog.getYwbwid());
                        }
                    }
                    nationalAccessUpload.getReponse(StringUtils.join(listId, CommonConstantUtils.ZF_YW_DH));
                }
            }
        }
    }

    /**
     * 获取上报返回报文查询开始日期
     * @return
     */
    private Date getAccessResponseStartDate() {
        if(null == accessResponseDays || accessResponseDays < 1 || accessResponseDays > 7) {
            // 避免查询范围过大导致应用OOM
            accessResponseDays = 1;
        }

        // 天数往前算
        Date preDay = org.apache.commons.lang3.time.DateUtils.addDays(new Date(), - accessResponseDays);
        return DateUtils.getStartDate(preDay);
    }

    /**
     * 每天定时检查ftp服务器和sftp服务器状态，异常发短信提醒 默认每天八点
     *
     * @param
     * @return
     * @Date 2022/6/22
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Scheduled(cron = "${access.check.quartztime:0 0 8 * * ?}")
    @RedissonLock(lockKey = Constants.ACCESS_SBFWQ_JOB, description = "定时任务检测服务器状态", waitTime = 1L, leaseTime = 300L)
    public void checkFtpStatus() {
        if (sbfwqStatusCheck) {
            List<NationalAccessUpload> list = UploadServiceUtil.listNationalAccessUpload();
            for (NationalAccessUpload nationalAccessUpload : list) {
                nationalAccessUpload.checkStatus();
            }
        }


    }


    @Scheduled(cron = "${access.bcsb.cron:0 0 21 * * ?}")
    @RedissonLock(lockKey = Constants.ACCESS_BCSB_JOB, description = "定时补偿当天登簿未成功接入的数据和连接前置机失败的数据", waitTime = 1L, leaseTime = 300L)
    public void bcsb() {
        if (jrsbbcsb) {
            BdcJrrzQO bdcJrrzQO = new BdcJrrzQO();
            bdcJrrzQO.setJrrq(new Date());
            bdcJrrzQO.setCgbsList(Arrays.asList(4, 7));
            LOGGER.warn("开启接入补偿服务，开始查询当天登簿后未成功接入的数据和连接前置机失败的数据，cgbs为4和7,查询入参{}", JSON.toJSONString(bdcJrrzQO));
            List<BdcAccessLog> bdcAccessLogList = accessLogMapper.listBdcJrrz(bdcJrrzQO);
            LOGGER.warn("查询到cgbs为4和7 的数据有{}条", CollectionUtils.size(bdcAccessLogList));
            if (CollectionUtils.isNotEmpty(bdcAccessLogList)) {
                for (BdcAccessLog bdcAccessLog : bdcAccessLogList) {
                    asyncDealUtils.saveJrCzrz(bdcAccessLog.getYwlsh(), 0, "开启补偿服务，开始汇交", "", new Date(), "1");
                    accesssModelHandlerService.autoAccessByXmid(bdcAccessLog.getYwlsh(), false);
                }
            }
        }
    }

}
