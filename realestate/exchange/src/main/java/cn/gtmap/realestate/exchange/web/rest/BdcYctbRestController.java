package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.dto.exchange.hefei.yctb.YctbCommonResponse;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.rest.exchange.BdcYctbRestService;
import cn.gtmap.realestate.exchange.core.annotation.OpenApi;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.core.dto.yctb.*;
import cn.gtmap.realestate.exchange.service.impl.inf.request.HfYctbInterfaceServiceImpl;
import cn.gtmap.realestate.exchange.service.inf.hefei.BdcYctbService;
import cn.gtmap.realestate.exchange.util.SM2Util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:zedeqiangg@gtmap.cn">zedq</a>
 * @version 1.0  2021-06-23
 * @description (合肥) 一窗通办相关服务
 */
@OpenController(consumer = "(合肥) 一窗通办相关服务")
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0/yctb")
@Api(tags = "(合肥) 一体化相关服务")
public class BdcYctbRestController implements BdcYctbRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcYctbRestController.class);

    @Autowired
    private BdcYctbService bdcYctbService;

    /**
     * 省平台调我们接口，请求体是否解密,true是不加解密，false是加解密
     */
    @Value("${yctb.interface.test.flag:true}")
    private String jmkg;

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @Resource(name = "hfyctbPostService")
    private HfYctbInterfaceServiceImpl hfYctbInterfaceService;

    @Value("#{${yctb.qxdm.appid:{'340101': 'hefei'}}}")
    private Map<String, String> appidMap;

    /**
     * 盐城_一体化撤件请求
     *
     * @return CommonResponse 返回参数
     * @author <a href="mailto:zedeqiangg@gtmap.cn">zedq</a>
     */
    @OpenApi(apiDescription = "合肥_接收附件材料", apiName = "", openLog = false)
    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping("/upload/fjxx")
    @DsfLog(logEventName = "合肥_接收附件材料", dsfFlag = "YCTB", requester = "YCTB", responser = "BDC")
    public YctbCommonResponse uploadFjxx(MultipartHttpServletRequest httpServletRequest) {
        return bdcYctbService.uploadFjxx(httpServletRequest);
    }

    /**
     * 合肥_办件效能监管
     *
     * @return ythCommonResponse 返回参数
     * @author <a href="mailto:zedeqiangg@gtmap.cn">zedq</a>
     */
    @OpenApi(apiDescription = "合肥_办件效能监管", apiName = "", openLog = false)
    @PostMapping("/bjxnjg/{gzlslid}/{nextNodeCode}")
    @DsfLog(logEventName = "合肥_办件效能监管", dsfFlag = "YCTB", requester = "YCTB", responser = "BDC")
    public YctbCommonResponse yctbBjxnjg(@PathVariable(value = "gzlslid") String gzlslid, @PathVariable(value = "nextNodeCode") String nextNodeCode) {
        LOGGER.info("合肥_办件效能监管入参:gzlslid:{},nextNodeCode:{}",gzlslid,nextNodeCode);
        if(StringUtils.isNotBlank(gzlslid) && StringUtils.isNotEmpty(nextNodeCode)){
            return bdcYctbService.yctbBjxnjg(gzlslid,nextNodeCode);
        }else {
            throw new RuntimeException("缺少必要参数");
        }
    }

    /**
     * 合肥_在线查档
     *
     * @return ythCommonResponse 返回参数
     * @author <a href="mailto:zedeqiangg@gtmap.cn">zedq</a>
     */
    @OpenApi(apiDescription = "合肥_在线查档", apiName = "", openLog = false)
    @PostMapping("/zxcd")
    @DsfLog(logEventName = "合肥_在线查档", dsfFlag = "YCTB", requester = "YCTB", responser = "BDC")
    public YctbCommonResponse yctbZxcd(@RequestBody String jsonString , HttpServletResponse response) {
        LOGGER.info("合肥_在线查档入参:{}", jsonString);
        YctbZxcdRequest yctbZxcdRequest = new YctbZxcdRequest();
        // 需要解密
        if ("false".equals(jmkg)) {
            LOGGER.info("合肥_在线查档进行解密");
            YctbSginpriKeyInfo syInfo =  hfYctbInterfaceService.getsyInfo();
            String syStr = syInfo.getKey();
            String jmStr = SM2Util.decryptInputParameters(jsonString, syStr);
            LOGGER.info("解密后：{}", jmStr);
            yctbZxcdRequest = JSONObject.parseObject(jmStr, YctbZxcdRequest.class);
            String qxdm = yctbZxcdRequest.getQxdm();
            LOGGER.info("header中的appid：{}", appidMap.get(qxdm));
            response.setHeader("appid",appidMap.get(qxdm));
        } else {
            yctbZxcdRequest = JSONObject.parseObject(jsonString, YctbZxcdRequest.class);
        }
        yctbZxcdRequest.checkParam();
        return bdcYctbService.yctbZxcd(yctbZxcdRequest);
    }

    /**
     * 合肥_登记派件分发
     *
     * @return ythCommonResponse 返回参数
     * @author <a href="mailto:zedeqiangg@gtmap.cn">zedq</a>
     */
    @OpenApi(apiDescription = "合肥_登记派件分发", apiName = "", openLog = false)
    @PostMapping("/djpjff")
    @DsfLog(logEventName = "合肥_登记派件分发", dsfFlag = "YCTB", requester = "YCTB", responser = "BDC")
    public YctbCommonResponse saveApplicationInfo(@RequestBody String jsonString , HttpServletResponse response) {
        LOGGER.info("合肥_登记派件分发入参:{}", jsonString);
        YctbWwcjRequest yctbWwcjRequest;
        // 需要解密
        if ("false".equals(jmkg)) {
            LOGGER.info("合肥_登记派件进行解密");
            YctbSginpriKeyInfo syInfo =  hfYctbInterfaceService.getsyInfo();
            String syStr = syInfo.getKey();
            String jmStr = SM2Util.decryptInputParameters(jsonString, syStr);
            LOGGER.info("解密后：{}", jmStr);
            yctbWwcjRequest = JSONObject.parseObject(jmStr, YctbWwcjRequest.class);
            String qxdm = yctbWwcjRequest.getQxdm();
            LOGGER.info("header中的appid：{}", appidMap.get(qxdm));
            response.setHeader("appid",appidMap.get(qxdm));
        } else {
            yctbWwcjRequest = JSONObject.parseObject(jsonString, YctbWwcjRequest.class);
        }
        yctbWwcjRequest.checkParam();
        YctbCommonResponse yctbCommonResponse = bdcYctbService.saveApplicationInfo(yctbWwcjRequest);
        if ("false".equals(jmkg) && yctbCommonResponse.getCode() == 200 && yctbCommonResponse.getData()!=null){
            String jmStr = getJmStr(yctbWwcjRequest.getQxdm(), yctbCommonResponse);
            yctbCommonResponse.setData(jmStr);
        }
        return yctbCommonResponse;
    }

    /**
     * 省平台交互接口返回参数加密
     * @param qxdm 区县代码
     * @param yctbCommonResponse 返回
     * @return
     */
    private String getJmStr(String qxdm, YctbCommonResponse yctbCommonResponse) {
        // 返回参数加密
        LOGGER.info("合肥_一窗同通办返回值进行加密");
        // 获取公钥
        YctbSginpriKeyInfo gyInfo =  hfYctbInterfaceService.getSginpriKeyInfo(qxdm,2);
        String gyStr = gyInfo.getPubkey();
        JSONObject json = JSON.parseObject(JSON.toJSONString(yctbCommonResponse.getData()));
        String data = json.toJSONString();
        LOGGER.info("加密内容：{}",data);
        String jmStr = SM2Util.encryptedMealReturn(data, gyStr);
        LOGGER.info("加密后：{}", jmStr);
        LOGGER.info("最后返回结果：{}", JSON.toJSONString(YctbCommonResponse.ok(jmStr)));
        return jmStr;
    }

    /**
     * 合肥_添加税务明细和缴费明细
     *
     * @return ythCommonResponse 返回参数
     * @author <a href="mailto:zedeqiangg@gtmap.cn">zedq</a>
     */
    @Override
    @OpenApi(apiDescription = "合肥_添加税务明细和缴费明细", apiName = "", openLog = false)
    @PostMapping("/add/tax/info/{gzlslid}")
    @DsfLog(logEventName = "合肥_添加税务明细和缴费明细", dsfFlag = "YCTB", requester = "YCTB", responser = "BDC")
    public YctbCommonResponse yctbAddTaxInfo(@PathVariable(value = "gzlslid") String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            return bdcYctbService.addTaxAndPayInfo(gzlslid);
        } else {
            return YctbCommonResponse.fail("gzlslid不能为空");
        }
    }

    /**
     * 合肥_获取不动产单元信息
     *
     * @return ythCommonResponse 返回参数
     * @author <a href="mailto:zedeqiangg@gtmap.cn">zedq</a>
     */
    @OpenApi(apiDescription = "合肥_获取不动产单元信息", apiName = "", openLog = false)
    @PostMapping("/hqbdcdyxx")
    @DsfLog(logEventName = "合肥_获取不动产单元信息", dsfFlag = "YCTB", requester = "YCTB", responser = "BDC")
    public YctbCommonResponse yctbGetHouseInfo(@RequestBody String jsonString , HttpServletResponse response) {
        LOGGER.info("合肥_获取不动产单元信息入参:{}", jsonString);
        YctbGetHouseInfoRequest yctbGetHouseInfoRequest;
        // 需要解密
        if ("false".equals(jmkg)) {
            LOGGER.info("合肥_获取不动产单元信息进行解密");
            YctbSginpriKeyInfo syInfo =  hfYctbInterfaceService.getsyInfo();
            String syStr = syInfo.getKey();
            String jmStr = SM2Util.decryptInputParameters(jsonString, syStr);
            LOGGER.info("解密后：{}", jmStr);
            yctbGetHouseInfoRequest = JSONObject.parseObject(jmStr, YctbGetHouseInfoRequest.class);
            String qxdm = yctbGetHouseInfoRequest.getQxdm();
            LOGGER.info("header中的appid：{}", appidMap.get(qxdm));
            response.setHeader("appid",appidMap.get(qxdm));
        } else {
            yctbGetHouseInfoRequest = JSONObject.parseObject(jsonString, YctbGetHouseInfoRequest.class);
        }
        yctbGetHouseInfoRequest.checkParam();
        YctbCommonResponse yctbCommonResponse = bdcYctbService.yctbGetHouseInfo(yctbGetHouseInfoRequest);
        // 返回值加密
        if ("false".equals(jmkg) && yctbCommonResponse.getCode() == 200 && yctbCommonResponse.getData()!=null){
            String jmStr = getJmStr(yctbGetHouseInfoRequest.getQxdm(), yctbCommonResponse);
            yctbCommonResponse.setData(jmStr);
        }
        return yctbCommonResponse;
    }

    /**
     * 合肥_获取不动产上手业务信息
     *
     * @return ythCommonResponse 返回参数
     * @author <a href="mailto:zedeqiangg@gtmap.cn">zedq</a>
     */
    @OpenApi(apiDescription = "合肥_获取不动产上手业务信息", apiName = "", openLog = false)
    @PostMapping("/hqbdcsysywxx")
    @DsfLog(logEventName = "合肥_获取不动产上手业务信息", dsfFlag = "YCTB", requester = "YCTB", responser = "BDC")
    public YctbCommonResponse oldBusiness(@RequestBody String jsonString , HttpServletResponse response) {
        LOGGER.info("合肥_获取不动产上手业务信息入参:{}", jsonString);
        YctbOldBusinessRequest yctbOldBusinessRequest;
        if ("false".equals(jmkg)) {
            LOGGER.info("合肥_获取不动产上手业务信息进行解密");
            YctbSginpriKeyInfo syInfo =  hfYctbInterfaceService.getsyInfo();
            String syStr = syInfo.getKey();
            String jmStr = SM2Util.decryptInputParameters(jsonString, syStr);
            LOGGER.info("解密后：{}", jmStr);
            yctbOldBusinessRequest = JSONObject.parseObject(jmStr, YctbOldBusinessRequest.class);
            String qxdm = yctbOldBusinessRequest.getQxdm();
            response.setHeader("appid",appidMap.get(qxdm));
        } else {
            yctbOldBusinessRequest = JSONObject.parseObject(jsonString, YctbOldBusinessRequest.class);
        }
        yctbOldBusinessRequest.checkParam();
        YctbCommonResponse yctbCommonResponse = bdcYctbService.oldBusiness(yctbOldBusinessRequest);
        // 返回值加密
        if ("false".equals(jmkg) && yctbCommonResponse.getCode() == 200 && yctbCommonResponse.getData()!=null){
            String jmStr = getJmStr(yctbOldBusinessRequest.getQxdm(), yctbCommonResponse);
            yctbCommonResponse.setData(jmStr);
        }
        return yctbCommonResponse;
    }


    /**
     * 合肥_获取纳税缴费支付地址
     *
     * @return ythCommonResponse 返回参数
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     */
    @OpenApi(apiDescription = "合肥_获取纳税缴费支付地址", apiName = "", openLog = false)
    @PostMapping("/getpaul")
    @DsfLog(logEventName = "合肥_获取纳税缴费支付地址", dsfFlag = "YCTB", requester = "YCTB", responser = "BDC")
    public YctbCommonResponse getPayUrl(@RequestBody String jsonString , HttpServletResponse response) {
        LOGGER.info("合肥_获取纳税缴费支付地址入参:{}", jsonString);
        YctbGetPayUrlRequest yctbGetPayUrlRequest;
        if ("false".equals(jmkg)) {
            LOGGER.info("合肥_获取纳税缴费支付地址进行解密");
            YctbSginpriKeyInfo syInfo =  hfYctbInterfaceService.getsyInfo();
            String syStr = syInfo.getKey();
            String jmStr = SM2Util.decryptInputParameters(jsonString, syStr);
            LOGGER.info("解密后：{}", jmStr);
            yctbGetPayUrlRequest = JSONObject.parseObject(jmStr,YctbGetPayUrlRequest.class);
            String qxdm = yctbGetPayUrlRequest.getQxdm();
            response.setHeader("appid",appidMap.get(qxdm));
        } else {
            yctbGetPayUrlRequest = JSONObject.parseObject(jsonString, YctbGetPayUrlRequest.class);
        }
        yctbGetPayUrlRequest.checkParam();
        YctbCommonResponse yctbCommonResponse = bdcYctbService.getPayUrl(yctbGetPayUrlRequest);
        // 返回值加密
        if ("false".equals(jmkg) && yctbCommonResponse.getCode() == 200 && yctbCommonResponse.getData()!=null){
            String jmStr = getJmStr(yctbGetPayUrlRequest.getQxdm(), yctbCommonResponse);
            yctbCommonResponse.setData(jmStr);
        }
        return yctbCommonResponse;
    }

    /**
     * 合肥_获取纳税缴费状态
     *
     * @return ythCommonResponse 返回参数
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     */
    @OpenApi(apiDescription = "合肥_获取纳税缴费状态", apiName = "", openLog = false)
    @PostMapping("/getaxpaymentstate")
    @DsfLog(logEventName = "合肥_获取纳税缴费状态", dsfFlag = "YCTB", requester = "YCTB", responser = "BDC")
    public YctbCommonResponse getTaxPaymentState(@RequestBody String jsonString , HttpServletResponse response) {
        LOGGER.info("合肥_获取纳税缴费状态入参:{}", jsonString);
        YctbGetTaxPayRequest yctbGetTaxPayRequest;
        if ("false".equals(jmkg)) {
            LOGGER.info("合肥_获取纳税缴费状态进行解密");
            YctbSginpriKeyInfo syInfo =  hfYctbInterfaceService.getsyInfo();
            String syStr = syInfo.getKey();
            String jmStr = SM2Util.decryptInputParameters(jsonString, syStr);
            LOGGER.info("解密后：{}", jmStr);
            yctbGetTaxPayRequest = JSONObject.parseObject(jmStr,YctbGetTaxPayRequest.class);
            String qxdm = yctbGetTaxPayRequest.getQxdm();
            response.setHeader("appid",appidMap.get(qxdm));
        } else {
            yctbGetTaxPayRequest = JSONObject.parseObject(jsonString, YctbGetTaxPayRequest.class);
        }
        yctbGetTaxPayRequest.checkParam();
        YctbCommonResponse yctbCommonResponse = bdcYctbService.getTaxPaymentState(yctbGetTaxPayRequest);
        // 返回值加密
        if ("false".equals(jmkg) && yctbCommonResponse.getCode() == 200 && yctbCommonResponse.getData()!=null){
            String jmStr = getJmStr(yctbGetTaxPayRequest.getQxdm(), yctbCommonResponse);
            yctbCommonResponse.setData(jmStr);
        }
        return yctbCommonResponse;
    }


}
