package cn.gtmap.realestate.exchange.web.rest.yancheng;

import cn.gtmap.realestate.exchange.core.annotation.OpenApi;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.core.dto.yancheng.AccessXmlDTO;
import cn.gtmap.realestate.exchange.service.impl.national.access.AccessLogProvinceAnhuiImpl;
import cn.gtmap.realestate.exchange.service.impl.national.access.AccessLogProvinceJiangsuImpl;
import cn.gtmap.realestate.exchange.service.impl.national.access.AccessLogProvinceStdImpl;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @version 1.0  2021-03-30
 * @description (盐城) 登簿日志补偿上报
 */
@OpenController(consumer = "(盐城) 上报补偿服务")
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0/access")
@Api(tags = "(盐城) 一体化相关服务")
public class BdcAccessRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcAccessRestController.class);

    @Autowired
    private AccessLogProvinceJiangsuImpl accessLogProvinceJiangsu;

    @Autowired
    private AccessLogProvinceStdImpl accessLogProvinceStd;

    @Autowired
    private AccessLogProvinceAnhuiImpl accessLogProvinceAnhui;


    /**
     *江苏上报
     */
    @OpenApi(apiDescription = "江苏上报", apiName = "", openLog = false)
    @PostMapping("/js")
    @DsfLog(logEventName = "江苏上报", dsfFlag = "", requester = "", responser = "BDC")
    public void js(@RequestBody AccessXmlDTO accessXmlDTO) {
        LOGGER.info(accessXmlDTO.getXml());
        accessLogProvinceJiangsu.provinceAccessLog(accessXmlDTO.getXml());
    }

    /**
     */
    @OpenApi(apiDescription = "安徽上报", apiName = "", openLog = false)
    @PostMapping("/anhui")
    @DsfLog(logEventName = "安徽上报", dsfFlag = "", requester = "", responser = "BDC")
    public void anhui(@RequestBody AccessXmlDTO accessXmlDTO) {
        accessLogProvinceAnhui.provinceAccessLog(accessXmlDTO.getXml());
    }

    /**
     */
    @OpenApi(apiDescription = "标准上报", apiName = "", openLog = false)
    @PostMapping("/st")
    @DsfLog(logEventName = "标准上报", dsfFlag = "", requester = "", responser = "BDC")
    public void st(@RequestBody AccessXmlDTO accessXmlDTO) {
        accessLogProvinceStd.provinceAccessLog(accessXmlDTO.getXml());
    }


}
