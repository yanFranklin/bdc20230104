package cn.gtmap.realestate.common.core.service.rest.exchange;

import cn.gtmap.realestate.common.core.dto.exchange.BdcAccessLogDTO;
import cn.gtmap.realestate.common.core.dto.exchange.access.SbxzDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2020/3/27
 * @description 登簿日志上报接口
 */
public interface AccessLogRestService {

    /**
     * @param ywh
     * @param logType
     * @return java.lang.String
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 查看响应报文
     */
    @PostMapping(value = "/queryXybw")
    String queryXybw(@RequestParam(name = "ywh") String ywh, @RequestParam(name = "logType", required = false) String logType);

    /**
     * @param ywh
     * @param logType
     * @return java.lang.String
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 查看响应报文
     */
    @PostMapping(value = "/queryBdcAccessLog")
    BdcAccessLogDTO queryBdcAccessLog(@RequestParam(name = "ywh") String ywh, @RequestParam(name = "logType", required = false) String logType);

    /**
     * @param ywh
     * @param logType
     * @return java.lang.String
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 查看业务报文
     */
    @PostMapping(value = "/queryJrbw")
    String queryJrbw(@RequestParam(name = "ywh") String ywh, @RequestParam(name = "logType", required = false) String logType);

    /**
     * @param xmid
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 上报日志台账数据校验
     */
    @GetMapping(value = "/checkDataByXmid")
    Boolean checkDataByXmid(@RequestParam(name = "xmid") String xmid);

    /**
     * @param xmidList 包含xmid和type（国家上报或省级上报）
     * @param logType
     * @return
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 获取响应报文
     */
    @GetMapping(value = "/getAccessResponse")
    String getAccessResponse(@RequestParam(name = "xmidList") List<String> xmidList,
                             @RequestParam(name = "logType") String logType);


    /**
     * @return
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 获取定时任务配置时间
     */
    @PostMapping(value = "/pz")
    String getPz();

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 例外上报
     * @date : 2022/10/6 11:03
     */
    @PostMapping("/lwsb")
    Object lwsb(@RequestBody SbxzDTO sbxzDTO);

}
