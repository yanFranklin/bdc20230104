package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.dto.exchange.BdcAccessLogDTO;
import cn.gtmap.realestate.common.core.dto.exchange.access.SbxzDTO;
import cn.gtmap.realestate.common.core.service.rest.exchange.AccessLogRestService;
import cn.gtmap.realestate.exchange.core.national.BdcAccessLog;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogService;
import cn.gtmap.realestate.exchange.web.main.AccessLogController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2020/3/27
 * @description 上报日志台账数据校验
 */
@RestController
public class AccessLogRestController implements AccessLogRestService {
    @Autowired
    private AccessLogController accessLogController;
    @Autowired
    private AccessLogService accessLogService;

    @Value("${access.sbxz.cron:0 0 23 * * ?}")
    private String sbsj;

    @Override
    public String queryXybw(String ywh, String logType) {
        return accessLogController.queryXybw(ywh, logType);
    }

    @Override
    public BdcAccessLogDTO queryBdcAccessLog(String ywh, String logType) {
        BdcAccessLogDTO bdcAccessLogDTO = new BdcAccessLogDTO();
        BdcAccessLog logDO = accessLogService.queryBdcAccessLog(ywh, logType);
        if (logDO != null) {
            BeanUtils.copyProperties(logDO, bdcAccessLogDTO);
        }
        return bdcAccessLogDTO;
    }

    @Override
    public String queryJrbw(String ywh, String logType) {
        return accessLogController.queryJrbw(ywh, logType);
    }

    @Override
    public Boolean checkDataByXmid(String xmid) {
        return accessLogController.checkDataByXmid(xmid);
    }

    @Override
    public String getAccessResponse(@RequestParam(name = "xmidList") List<String> xmidList,
                                    @RequestParam(name = "logType") String logType) {
        return accessLogController.getAccessResponse(xmidList, logType);
    }

    @Override
    public String getPz() {
        return sbsj;
    }

    /**
     * @param sbxzDTO@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 例外上报
     * @date : 2022/10/6 11:03
     */
    @Override
    public Object lwsb(@RequestBody SbxzDTO sbxzDTO) {
        return accessLogService.lwsb(sbxzDTO);
    }
}
