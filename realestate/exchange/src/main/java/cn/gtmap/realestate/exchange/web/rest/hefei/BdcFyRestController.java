package cn.gtmap.realestate.exchange.web.rest.hefei;

import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.exchange.core.annotation.OpenApi;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.core.dto.hefei.fw.FycdRequest;
import cn.gtmap.realestate.exchange.core.qo.BdcFjxxQO;
import cn.gtmap.realestate.exchange.service.inf.hefei.BdcFyService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0  2022-10-19
 * @description 合肥法院服务
 */
@OpenController(consumer = "合肥法院服务")
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0/fy")
@Api(tags = "合肥法院服务")
public class BdcFyRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcFyRestController.class);

    @Autowired
    private BdcFyService bdcFyService;

    @OpenApi(apiDescription = "附件查询接口", apiName = "", openLog = false)
    @PostMapping("/queryFjxx")
    @DsfLog(logEventName = "附件查询接口", dsfFlag = "", requester = "", responser = "BDC")
    public BdcCommonResponse queryFjxxByxmid(@RequestBody BdcFjxxQO bdcFjxxQO) {
        return bdcFyService.queryFjxx(bdcFjxxQO);

    }

    @OpenApi(apiDescription = "生成查封回执单PDF", apiName = "", openLog = false)
    @PostMapping("/cfhzdpdf")
    @DsfLog(logEventName = "生成查封回执单PDF", dsfFlag = "", requester = "", responser = "BDC")
    public void cfhzdPdf(String gzlslid) {
        bdcFyService.scCjfhzdPdf(gzlslid);
    }


    @OpenApi(apiDescription = "生成法院查档PDF", apiName = "", openLog = false)
    @PostMapping("/cdpdf")
    @DsfLog(logEventName = "生成法院查档PDF", dsfFlag = "", requester = "", responser = "BDC")
    public BdcCommonResponse fwcdPdf(@RequestBody FycdRequest fycdRequest) {
        return bdcFyService.scCdPdf(fycdRequest);
    }


}
