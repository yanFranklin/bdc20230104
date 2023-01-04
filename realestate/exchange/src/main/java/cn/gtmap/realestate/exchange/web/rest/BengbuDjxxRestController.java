package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.exchange.core.annotation.OpenApi;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.core.dto.bengbu.djxx.DjxxCommonResponse;
import cn.gtmap.realestate.exchange.core.dto.bengbu.djxx.DjxxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.yctb.YctbSginpriKeyInfo;
import cn.gtmap.realestate.exchange.service.impl.inf.request.HfYctbInterfaceServiceImpl;
import cn.gtmap.realestate.exchange.service.inf.bengbu.BengbuDjxxService;
import cn.gtmap.realestate.exchange.util.SM2Util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @version 1.0  2022-12-22
 * @description 蚌埠交易部门获取登记信息相关服务
 */
@OpenController(consumer = "蚌埠交易部门获取登记信息相关服务")
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0/djxx")
@Api(tags = "蚌埠交易部门获取登记信息相关服务")
public class BengbuDjxxRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcYctbRestController.class);

    /**
     * 省平台调我们接口，请求体是否解密,true是不加解密，false是加解密
     */
    @Value("${yctb.interface.test.flag:true}")
    private String jmkg;

    @Autowired
    BengbuDjxxService bengbuDjxxService;

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @Resource(name = "hfyctbPostService")
    private HfYctbInterfaceServiceImpl hfYctbInterfaceService;

    @Value("#{${yctb.qxdm.appid:{'340101': 'hefei'}}}")
    private Map<String, String> appidMap;


    /**
     * 获取不动产权属登记信息
     * @param jsonString
     * @param response
     * @return
     */
    @OpenApi(apiDescription = "蚌埠_获取权属登记信息", apiName = "", openLog = false)
    @PostMapping("/getqsdjxx")
    @DsfLog(logEventName = "蚌埠_获取权属登记信息", dsfFlag = "DJXX", requester = "DJXX", responser = "BDC")
    public DjxxCommonResponse getQsdjxx(@RequestBody String jsonString , HttpServletResponse response) {
        LOGGER.info("蚌埠_获取权属登记信息入参:{}", jsonString);
        DjxxRequestDTO djxxRequestDTO;
        String qxdm = "";
        // 需要解密
        if ("false".equals(jmkg)) {
            LOGGER.info("蚌埠_获取权属登记信息进行解密");
            YctbSginpriKeyInfo syInfo =  hfYctbInterfaceService.getsyInfo();
            String syStr = syInfo.getKey();
            String jmStr = SM2Util.decryptInputParameters(jsonString, syStr);
            LOGGER.info("蚌埠_获取权属登记信息解密后：{}", jmStr);
            djxxRequestDTO = JSONObject.parseObject(jmStr, DjxxRequestDTO.class);
            qxdm = bengbuDjxxService.getQxdm(djxxRequestDTO);
            if (StringUtils.isBlank(qxdm)) {
                throw new AppException("蚌埠_获取权属登记信息未查询到区县代码");
            }
            LOGGER.info("header中的appid：{}", appidMap.get(qxdm));
            response.setHeader("appid",appidMap.get(qxdm));
        } else {
            djxxRequestDTO = JSONObject.parseObject(jsonString, DjxxRequestDTO.class);
        }
        DjxxCommonResponse djxxCommonResponse = bengbuDjxxService.getQsdjxx(djxxRequestDTO);
        if ("false".equals(jmkg) && DjxxCommonResponse.SUCCESS_STATUS.equals(djxxCommonResponse.getStatus()) && Objects.nonNull(djxxCommonResponse.getResult())){
            String jmStr = getJmStr(qxdm, djxxCommonResponse);
            djxxCommonResponse.setResult(jmStr);
        }
        return djxxCommonResponse;
    }

    /**
     * 获取不动产业务详情信息
     * @param jsonString
     * @param response
     * @return
     */
    @OpenApi(apiDescription = "蚌埠_获取业务详情信息", apiName = "", openLog = false)
    @PostMapping("/getywxqxx")
    @DsfLog(logEventName = "蚌埠_获取业务详情信息", dsfFlag = "DJXX", requester = "DJXX", responser = "BDC")
    public DjxxCommonResponse getYwxqxx(@RequestBody String jsonString , HttpServletResponse response) {
        LOGGER.info("蚌埠_获取业务详情信息入参:{}", jsonString);
        DjxxRequestDTO djxxRequestDTO;
        String qxdm = "";
        // 需要解密
        if ("false".equals(jmkg)) {
            LOGGER.info("蚌埠_获取业务详情信息进行解密");
            YctbSginpriKeyInfo syInfo =  hfYctbInterfaceService.getsyInfo();
            String syStr = syInfo.getKey();
            String jmStr = SM2Util.decryptInputParameters(jsonString, syStr);
            LOGGER.info("蚌埠_获取业务详情信息解密后：{}", jmStr);
            djxxRequestDTO = JSONObject.parseObject(jmStr, DjxxRequestDTO.class);
            qxdm = bengbuDjxxService.getQxdm(djxxRequestDTO);
            if (StringUtils.isBlank(qxdm)) {
                throw new AppException("蚌埠_获取业务详情信息未查询到区县代码");
            }
            LOGGER.info("header中的appid：{}", appidMap.get(qxdm));
            response.setHeader("appid",appidMap.get(qxdm));
        } else {
            djxxRequestDTO = JSONObject.parseObject(jsonString, DjxxRequestDTO.class);
        }
        DjxxCommonResponse djxxCommonResponse = bengbuDjxxService.getYwxqxx(djxxRequestDTO);
        if ("false".equals(jmkg) && DjxxCommonResponse.SUCCESS_STATUS.equals(djxxCommonResponse.getStatus()) && Objects.nonNull(djxxCommonResponse.getResult())){
            String jmStr = getJmStr(qxdm, djxxCommonResponse);
            djxxCommonResponse.setResult(jmStr);
        }
        return djxxCommonResponse;
    }

    /**
     * 省平台交互接口返回参数加密
     * @param qxdm 区县代码
     * @param djxxCommonResponse 返回
     * @return
     */
    private String getJmStr(String qxdm, DjxxCommonResponse djxxCommonResponse) {
        // 返回参数加密
        LOGGER.info("蚌埠_获取登记信息返回值进行加密");
        // 获取公钥
        YctbSginpriKeyInfo gyInfo =  hfYctbInterfaceService.getSginpriKeyInfo(qxdm,2);
        String gyStr = gyInfo.getPubkey();
        JSONObject json = JSONObject.parseObject(JSON.toJSONString(djxxCommonResponse.getResult()));
        String data = json.toJSONString();
        LOGGER.info("加密内容：{}",data);
        String jmStr = SM2Util.encryptedMealReturn(data, gyStr);
        LOGGER.info("加密后：{}", jmStr);
        LOGGER.info("最后返回结果：{}", JSON.toJSONString(DjxxCommonResponse.ok(jmStr)));
        return jmStr;
    }
}
