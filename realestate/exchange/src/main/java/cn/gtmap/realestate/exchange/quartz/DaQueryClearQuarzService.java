package cn.gtmap.realestate.exchange.quartz;

import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.core.domain.exchange.BdcDaCxLog;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0  2021-04-25
 * @description 档案查询 相关定时任务
 */
@Component
@EnableScheduling
public class DaQueryClearQuarzService {

    /**
     * 外部应用log超时配置
     */
    @Value("#{${dangan.cx.exceed.time:{'BDCDJ': 30}}}")
    private Map<String, Integer> logGsMap;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private StorageClientMatcher storageClient;

    private static final List<String> DACX_LOG_ZT_LIST;

    static {
        String[] s = {"1","0"};
        DACX_LOG_ZT_LIST = Arrays.asList(s);
    }

    /**
     * @param
     * @return void
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 定时任务清除查询日志
     */
    @Scheduled(cron = "${dangan.cx.clear.cron:0 0 0 1 * ?}")
//    @Scheduled(cron = "${dangan.cx.clear.cron:0 0/1 * * * ?}")
    public void clearDaCxLog() {
        List<BdcDaCxLog> logList2clear = new ArrayList<>();
        if (MapUtils.isNotEmpty(logGsMap)) {
            logGsMap.entrySet().forEach(entry -> {
                //清除对应的
                Example example = new Example(BdcDaCxLog.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("dags", entry.getKey());
                Date date = new Date();
                Date targetTime = getDateFewDayAgo(date, entry.getValue());
                if (targetTime == null) {
                    targetTime = getDateFewDayAgo(date, 30);
                }
                criteria.andLessThan("cjsj",targetTime);
                criteria.andInWithListString("dacxlogzt",DACX_LOG_ZT_LIST);
                criteria.andEqualTo("dacxlogzt",0);
                List<BdcDaCxLog> bdcDaCxLogs = entityMapper.selectByExample(example);
                if (CollectionUtils.isNotEmpty(bdcDaCxLogs)) {
                    logList2clear.addAll(bdcDaCxLogs);
                }
            });
        }
        //删除大云文件
        if (CollectionUtils.isNotEmpty(logList2clear)) {
            List<String> fileIds2Delete = new ArrayList<>(logList2clear.size());
            logList2clear.forEach(bdcDaCxLog -> {
                if ("2".equals(bdcDaCxLog.getDacxloglx()) && StringUtils.isNotBlank(bdcDaCxLog.getForeign()) && StringUtils.isNotBlank(bdcDaCxLog.getPdfSpaceId())) {
                    fileIds2Delete.add(bdcDaCxLog.getPdfSpaceId());
                }
                bdcDaCxLog.setDacxlogzt("9");
                entityMapper.updateByPrimaryKeyNull(bdcDaCxLog);
            });
            if (CollectionUtils.isNotEmpty(fileIds2Delete)) {
                storageClient.deleteStorages(fileIds2Delete);
            }
        }
    }

    private Date getDateFewDayAgo(Date date, int i) {
        if (i < 0) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -i);
        date = calendar.getTime();
        return date;
    }

}
