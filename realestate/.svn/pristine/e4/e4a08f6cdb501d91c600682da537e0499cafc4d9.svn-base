package cn.gtmap.realestate.building.quartz;

import cn.gtmap.gtc.clients.LogMessageClient;
import cn.gtmap.gtc.sso.domain.dto.AuditLogDto;
import cn.gtmap.gtc.sso.domain.dto.DataValue;
import cn.gtmap.realestate.building.core.service.BdcdyZtService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-03
 * @description 同步不动产单元号现势状态的 定时任务
 */
@Component
@EnableScheduling
public class BdcdyztSyncQuartzService {

    @Autowired
    private BdcdyZtService bdcdyZtService;

    @Autowired
    private LogMessageClient logMessageClient;

    // 查询ES日志的 页码
    private static final int CXLOG_PAGESIZE = 1000;

    // 是否触发同步任务
    @Value("${synczt.task:false}")
    private boolean syncztTask;

    // 同步状态定时任务 是否使用ES作为扫描依据
    @Value("${synczt.task.withes:true}")
    private boolean syncztWithEs;


    @Scheduled(cron = "${synczt.cron:0 0 22 * * ?}")
    public void syncTask(){
        if(syncztTask){
            if(syncztWithEs){
                syncWithEslog();
            }else{
                Date curDate = new Date();
                bdcdyZtService.syncByDate(curDate);
            }
        }
    }
    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return void
     * @description 用查询大云ES 的方式 做 补偿机制
     */
    public void syncWithEslog(){
        Date currentDate = new Date();
        // 获取当前日期的 00:00:00
        Date beginDate = DateUtils.dealDate(currentDate,"00:00:00");
        // 获取当前日期的 23:59:59
        Date endDate = DateUtils.dealDate(currentDate,"23:59:59");
        int totalPage = 1;
        Page<AuditLogDto> auditLogDtos;
        for(int i = 0 ; i <= totalPage ; i++){
            auditLogDtos = logMessageClient.listLogs(i,CXLOG_PAGESIZE, CommonConstantUtils.LOG_EVENT_QJBDCDYZT+"1",null,null,beginDate.getTime(),endDate.getTime(),new ArrayList<>(0));
            // 总页数可能会一直上升
            totalPage = auditLogDtos.getTotalPages();
            if(!auditLogDtos.hasContent()){
                break;
            }else{
                List<AuditLogDto> tempList = auditLogDtos.getContent();
                Set<String> bdcdyhSet = new HashSet<>();
                log:for(AuditLogDto dto:tempList){
                    if(CollectionUtils.isNotEmpty(dto.getBinaryAnnotations())){
                        logData:for(DataValue dataValue:dto.getBinaryAnnotations()){
                            if(StringUtils.equalsIgnoreCase(dataValue.getKey(),CommonConstantUtils.BDCDYH)) {
                                // 获取不动产单元号 JSON
                                String tempJson = dataValue.getValue();
                                List<String> bdcdyhList = JSONObject.parseObject(tempJson,List.class);
                                if(CollectionUtils.isNotEmpty(bdcdyhList)){
                                    bdcdyhSet.addAll(bdcdyhList);
                                }
                                break logData;
                            }
                        }
                    }
                }

                // 批量  同步
                if(CollectionUtils.isNotEmpty(bdcdyhSet)){
                    bdcdyZtService.syncByBdcdyhByPage(bdcdyhSet);
                }
            }
        }
    }

}
