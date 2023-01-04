package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.config.mq.enums.RabbitMqEnum;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.exchange.BdcDaCxLog;
import cn.gtmap.realestate.common.core.dto.exchange.BdcDaCxLogDTO;
import cn.gtmap.realestate.common.core.service.rest.exchange.BdcDaCxRestService;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.xsd.LogBO;
import cn.gtmap.realestate.exchange.core.convert.BdcDacxConvert;
import cn.gtmap.realestate.exchange.service.inf.BdcDaCxService;
import cn.gtmap.realestate.exchange.service.rabbitmq.InsertAuditLogMQSender;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @author <a href="mailto:zedeqiang@gmail.com">zedq</a>
 * @version 1.0
 * @date 2021/04/28 15:30
 */
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0/dacx")
@Api(tags = "不动产档案查询相关服务")
public class BdcDaCxRestController implements BdcDaCxRestService {

    @Autowired
    private BdcDaCxService bdcDaCxService;

    @Autowired
    private InsertAuditLogMQSender insertAuditLogMQSender;

    @Autowired
    private BdcDacxConvert bdcDacxConvert;

    /**
     * 保存档案查询日志记录
     *
     * @param bdcDaCxLog
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @Override
    @ResponseBody
    @PostMapping(value = "/log/save")
    public CommonResponse saveBdcDaCxLog(@RequestBody BdcDaCxLog bdcDaCxLog) {
        if (bdcDaCxService.saveBdcDaCxLog(bdcDaCxLog) > 0) {
            return CommonResponse.ok();
        }
        return CommonResponse.fail("插入失败！");
    }

    /**
     * 更细档案查询日志记录
     *
     * @param bdcDaCxLog
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @Override
    @ResponseBody
    @PostMapping(value = "/log/update")
    public CommonResponse updateBdcDaCxLog(@RequestBody BdcDaCxLog bdcDaCxLog) {
        if (bdcDaCxService.updateBdcDaCxLog(bdcDaCxLog) > 0) {
            return CommonResponse.ok();
        }
        return CommonResponse.fail("更新失败！");
    }

//    /**
//     * 查询档案查询日志记录
//     *
//     * @param queryId
//     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
//     */
//    @Override
//    @GetMapping("/return/log/query/url")
//    public String returnLogQueryUrl(@RequestParam(name = "queryId") String queryId, HttpServletResponse response) {
//        return bdcDaCxService.queryBdcDaCxLogInfo(queryId,response);
//    }

    /**
     * 查询档案查询日志记录
     *
     * @param queryId
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @Override
    @GetMapping("/log/query")
    public CommonResponse queryBdcDaCxLogById(@RequestParam(name = "queryId") String queryId, HttpServletResponse response) {
       return bdcDaCxService.queryBdcDaCxLogInfo(queryId,response);
    }

    /**
     * 下载档案查询pdf
     *
     * @param fileId
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @Override
    @GetMapping("/download/pdf")
    public void downloadBdcDaCxLogPdf(@RequestParam(name = "fileId") String fileId, HttpServletResponse response) {
        bdcDaCxService.downloadBdcDaCxLogPdf(fileId,response);
    }

    /**
     * 保存档案日志信息
     *
     * @param bdcDaCxLogDTO
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @Override
    @ResponseBody
    @PostMapping(value = "/pdf/log/save")
    public CommonResponse saveBdcDacxLogWithPdfJsonStr(@RequestBody BdcDaCxLogDTO bdcDaCxLogDTO) {
        LogBO logBO = new LogBO();
        logBO.setResponser("BDCDJ");
        logBO.setResponser("BDCDJ");
        logBO.setDsfFlag("BDCDJ");
        logBO.setLogEventName("综合页面打印");
        AuditEventBO auditEventBO = new AuditEventBO(logBO);
        auditEventBO.setRzid(bdcDaCxLogDTO.getForeign());
        //指定新的es存储目录和日志类型
        auditEventBO.setRzlx(Constants.EXCHANGE_ES_DACX_RZLX);
        auditEventBO.setResponse(bdcDaCxLogDTO.getPdfJsonStr());
        auditEventBO.getLogBO().setBdcdz(bdcDaCxLogDTO.getUrl());
        auditEventBO.setBigDataFlag(true);
        auditEventBO.setUsername(bdcDaCxLogDTO.getDags());
        Message<AuditEventBO> message = MessageBuilder.createMessage(auditEventBO, new MessageHeaders(new HashMap<>()));
        //发送日志记录保存消息
        insertAuditLogMQSender.send(RabbitMqEnum.Exchange.CONTRACT_DIRECT.getCode(), RabbitMqEnum.QueueEnum.BDCDJINSERTAUDITLOGQUEUE.getCode(), message);
        return saveBdcDaCxLog(bdcDacxConvert.getBdcDaCxLogByBdcDaCxLogDTO(bdcDaCxLogDTO));
    }

}
