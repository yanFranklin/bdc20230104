package cn.gtmap.realestate.exchange.quartz;

import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.exchange.core.mapper.server.accessLog.AccessLogMapper;
import cn.gtmap.realestate.exchange.core.national.BdcAccessLog;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessUpload;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-07-04
 * @description 获取响应报文的 定时任务
 */
@Component
@EnableScheduling
public class AccessResponseQuartzService {

    private static Logger LOGGER = LoggerFactory.getLogger(AccessResponseQuartzService.class);

    /**
     * 每次定时任务 处理的固定条数
     */
    private static final int PER_TIMES = 2000;

    @Autowired
    private AccessLogMapper accessLogMapper;

    @Resource(name = "provinceAccessUploadFtpImpl")
    private NationalAccessUpload provinceAccessUploadFtpImpl;

    @Autowired
    private AccessLogService accessLogService;


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return void
     * @description 定时任务获取 省级接入的响应报文
     */
    @Scheduled(cron = "${accessResponse.sjjr.cron}")
    public void getSjjrResponseTask(){
        if(StringUtils.equals(EnvironmentConfig.getEnvironment()
                .getProperty("accessResponse.sjjr.switch"),"true")){
            getSjjrResponse(PER_TIMES);
        }
    }
    public void getSjjrResponse(Integer num){
        int total = num;
        while (total > 0){
            boolean endflag = dealProvinceResponse();
            if(endflag){
                break;
            }else{
                total = total - Constants.ACCESSRESPONSE_QUARTZ_PERUPDATE_TIMES;
            }
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return void
     * @description 处理 省级 响应日志
     */
    private boolean dealProvinceResponse(){
        List<BdcAccessLog> logList = accessLogService.queryAndUpdateSjjr();
        try {
            if(CollectionUtils.isNotEmpty(logList)){
                StringBuilder sb = new StringBuilder();
                for(BdcAccessLog temp : logList){
                    if(temp != null && StringUtils.isNotBlank(temp.getYwbwid())){
                        sb.append(temp.getYwbwid()+",");
                    }
                }
                String ywbwids = sb.toString();
                if(StringUtils.isNotBlank(ywbwids)){
                    ywbwids = ywbwids.substring(0,ywbwids.length()-1);
                    provinceAccessUploadFtpImpl.getReponse(ywbwids);
                }
            }else{
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("定时获取响应上报日志异常:", e);
        } finally {
            rollbackDealingFlag(logList,Constants.ACCESS_TYPE_PROVINCE);
        }
        return false;
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param jrrzList
     * @return void
     * @description 还原 处理标志
     */
    private void rollbackDealingFlag(List<BdcAccessLog> jrrzList,String logType){
        List ywbwidList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(jrrzList)){
            for(BdcAccessLog accessLog : jrrzList){
                if(StringUtils.isNotBlank(accessLog.getYwbwid())){
                    ywbwidList.add(accessLog.getYwbwid());
                }
            }
        }
        if(CollectionUtils.isNotEmpty(ywbwidList)){
            Map paramMap = new HashMap();
            paramMap.put("ywbwidList",ywbwidList);
            paramMap.put("logType",logType);
            paramMap.put("flag",Constants.ACCESSRESPONSE_QUARTZ_DEALINGFLAG);
            accessLogMapper.rollbackDealingXybm(paramMap);
        }
    }

    /**
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @param
     * @return void
     * @description 定时任务获取 省级接入的响应报文
     */
    @Scheduled(cron = "${check.access.response.cron:0 0 * * * ?}")
    public void checkAccessResponse(){
        if(StringUtils.equals(EnvironmentConfig.getEnvironment()
                .getProperty("check.access.response.switch"),"true")){
            accessLogService.checkAccessResponseAndSendMsg(Constants.ACCESS_TYPE_PROVINCE);
        }
    }

}
