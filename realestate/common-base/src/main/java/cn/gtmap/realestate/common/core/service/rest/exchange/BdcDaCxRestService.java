package cn.gtmap.realestate.common.core.service.rest.exchange;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.exchange.BdcDaCxLog;
import cn.gtmap.realestate.common.core.dto.exchange.BdcDaCxLogDTO;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="mailto:zedeqiang.com">zedq</a>
 * @version 1.0
 * @date 2021/04/26 14:24
 */
public interface BdcDaCxRestService {

    /**
     * 保存档案日志信息
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @PostMapping(value = "/realestate-exchange/rest/v1.0/dacx/log/save")
    CommonResponse saveBdcDaCxLog(@RequestBody BdcDaCxLog bdcDaCxLog);

    /**
     * 更新档案日志信息
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @PostMapping(value = "/realestate-exchange/rest/v1.0/dacx/log/update")
    CommonResponse updateBdcDaCxLog(@RequestBody BdcDaCxLog bdcDaCxLog);

//    @GetMapping(value = "/realestate-exchange/rest/v1.0/dacx/return/log/query/url")
//    String returnLogQueryUrl(@RequestParam(name = "queryId") String queryId, HttpServletResponse response);

    /**
     * 查询档案日志信息
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @GetMapping(value = "/realestate-exchange/rest/v1.0/dacx/log/query")
    CommonResponse queryBdcDaCxLogById(@RequestParam(name = "queryId") String queryId, HttpServletResponse response);

    /**
     * 下载档案查询pdf
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @GetMapping(value = "/realestate-exchange/rest/v1.0/dacx/download/pdf")
    void downloadBdcDaCxLogPdf(@RequestParam(name = "fileId") String fileId, HttpServletResponse response);

    /**
     * 保存档案日志信息
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @PostMapping(value = "/realestate-exchange/rest/v1.0/dacx/pdf/log/save")
    CommonResponse saveBdcDacxLogWithPdfJsonStr(@RequestBody BdcDaCxLogDTO bdcDaCxLogDTO);

}
