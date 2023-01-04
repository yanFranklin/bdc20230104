package cn.gtmap.realestate.exchange.quartz;

import cn.gtmap.realestate.exchange.core.national.BdcAccessLog;
import cn.gtmap.realestate.exchange.core.national.NationalAccess;
import cn.gtmap.realestate.exchange.core.national.ProvinceAccess;
import cn.gtmap.realestate.exchange.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.exchange.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.exchange.service.impl.national.AccessLogImpl;
import cn.gtmap.realestate.exchange.service.impl.national.upload.NationalAccessUploadFtpImpl;
import cn.gtmap.realestate.exchange.service.impl.national.upload.NationalAccessUploadSftpImpl;
import cn.gtmap.realestate.exchange.service.national.NationalAccessUpload;
import cn.gtmap.realestate.exchange.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.util.DateUtil;
import cn.gtmap.realestate.exchange.util.UploadServiceUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
 * @version 1.0  2018-12-17
 * @description
 */
@Service
@EnableScheduling
public class AccessLogQuartzService {
    @Autowired
    AccessLogImpl accessLog;
    @Value("${access.response.enable:}")
    private String quartz;
    @Autowired
    private EntityMapper entityMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessLogQuartzService.class);

    /**
     * @param
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 定时接入日志
     */
    public void accessLogs() {
        LOGGER.info("定时接入日志 开始===========>");
        Date accessDate = DateUtil.getCurDate();
        accessLog.accessLog(accessDate, null);
        LOGGER.info("定时接入日志 结束===========>");
    }

    /**
     * @param
     * @return
     * @author <a herf="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 定时批量获取响应报文
     */
//    @Scheduled(cron = "${access.response.quartztime:0 30 21 * * ?}")
    public void quartzGetAccessResponse() {
        Map map = new HashMap();
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
                example.createCriteria().andEqualNvlTo("cgbs", "0", "-1");
                example.setOrderByClause("updatetime asc");
                List<BdcAccessLog> listLog = entityMapper.selectByExample(example);
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

}
