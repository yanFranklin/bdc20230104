package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYjxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlYjxxFeginService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmfbFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.service.rest.exchange.SjrptRestService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.TokenUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.exchange.config.filter.RequestWrapper;
import cn.gtmap.realestate.exchange.core.annotation.OpenApi;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.core.dto.sjrpt.OrgReqData;
import cn.gtmap.realestate.exchange.service.impl.inf.sjrpt.SjrptServiceImpl;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.util.HttpClientUtils;
import cn.gtmap.realestate.exchange.util.sjrpt.Sm2Util;
import cn.gtmap.realestate.exchange.util.sjrpt.Sm3Util;
import cn.gtmap.realestate.exchange.util.sjrpt.Sm4Util;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SmUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 省金融平台服务，提供给省金融平台调用，有加解密
 *
 * @author <a href="mailto:lixin1@gtmap.cn">caolu</a>
 * @version v1.0, 2019/3/18 15:38
 */
@OpenController(consumer = "省金融平台服务")
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0/sjrpt")
@Api(tags = "省金融平台服务")
public class SjrptRestController implements SjrptRestService {

    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    /**
     * 不动产登记公钥
     */
    @Value("${sjrpt.bdcgy:}")
    private String bdcgy;

    /**
     * 不动产登记私钥
     */
    @Value("${sjrpt.bdcsy:}")
    private String bdcsy;

    @Value("${app.portal-ui:}")
    private String appUrl;

    @Value("${data.version}")
    private String dataversion;

    @Value("#{${sjrpt.gzyz.gzldyidmap:{'1':'DXKYRaGMj8bedSCy','2':'DXKYRaGMj8bedSCy'}}}")
    private Map<String, String> gzldyidMap;



    private static final Logger LOGGER = LoggerFactory.getLogger(SjrptRestController.class);

    @Autowired
    private HttpClientUtils httpClientUtils;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private BdcXmfbFeignService bdcXmfbFeignService;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private BdcSlYjxxFeginService bdcSlYjxxFeginService;
    @Autowired
    protected UserManagerUtils userManagerUtils;

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    @Autowired
    private SjrptServiceImpl sjrptService;

    private static Map<String, String> map = new HashMap<>(32);

    static {
        map.put("bdczscx", "/realestate-exchange/rest/v1.0/gxww/bdczscx");
        map.put("bdczmcx", "/realestate-exchange/rest/v1.0/gxww/bdczmcx");
        map.put("grdacx", "/realestate-exchange/rest/v1.0/gxww/grdacx");
        map.put("querycqzdyxx", "/realestate-exchange/rest/v1.0/gxww/querycqzdyxx");
        map.put("dydj", "/realestate-exchange/rest/v1.0/wwsqinterface/dydj");
        map.put("dyzx", "/realestate-exchange/rest/v1.0/wwsqinterface/dyzx");
        map.put("getWwsqzt", "/realestate-exchange/rest/v1.0/wwsqinterface/getWwsqztBySlbh");
        map.put("tsWwsqFjxx", "/realestate-exchange/rest/v1.0/wwsqinterface/tsWwsqFjxx");
        map.put("bdcdycjgzyz", "/realestate-exchange/rest/v1.0/interface/bdcdycjgzyz");
        map.put("wwsqdeltask", "/realestate-exchange/rest/v1.0/interface/wwsqdeltask");
        map.put("dzzz_cxbyzzbs", "/realestate-exchange/rest/v1.0/interface/dzzz_cxbyzzbs");
        map.put("dzzz_cxzs", "/realestate-exchange/rest/v1.0/interface/dzzz_cxzs");
        map.put("wwsqScddh", "/realestate-exchange/rest/v1.0/interface/wwsqScddh");
        map.put("wwsq_ykqjfztcx", "/realestate-exchange/rest/v1.0/interface/wwsq_ykqjfztcx");
        map.put("sjrpt_wwsqScddh", "/realestate-exchange/rest/v1.0/interface/sjrpt_wwsqScddh");
        map.put("sjrpt_wwsq_ykqjfztcx", "/realestate-exchange/rest/v1.0/interface/sjrpt_wwsq_ykqjfztcx");
        map.put("wwsq_sfssxx", "/realestate-exchange/rest/v1.0/interface/wwsq_sfssxx");


    }


    /**
     * 省金融平台请求不动产登记
     *
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     */
    @OpenApi(apiDescription = "省金融平台请求不动产登记", apiName = "", openLog = false)
    @PostMapping("/api/**")
    @DsfLog(logEventName = "省金融平台请求不动产登记", dsfFlag = "SJRPT", requester = "SJRPT", responser = "BDC")
    public Object sjptApiRequest(HttpServletRequest request) throws IOException {
        RequestWrapper requestWrapper = new RequestWrapper(request);
        String body = requestWrapper.getRequestBody();
        String requestUrl = requestWrapper.getRequestURI();
        LOGGER.info("省金融平台服务的调用方法：{}，加密请求参数：{}", requestUrl, body);
        LOGGER.info("省金融平台服务开始解密");
        OrgReqData orgReqData = JSONObject.parseObject(body, OrgReqData.class);
        String appId = request.getHeader("appId");
        String timestamp = request.getHeader("timestamp");
        String nonce = request.getHeader("nonce");
        LOGGER.info("省金融平台服务orgReqData:{},appid:{},timestamp:{},nonce:{}", orgReqData.toString(),appId, timestamp, nonce);
        //校验签名是否正确,平台公钥验签
        String qPubKey = sjrptService.getSjrptGyUrl();
        String encData = orgReqData.getEncData();
        String signature = orgReqData.getSignature();
        String encKey = orgReqData.getEncKey();
        boolean verify = Sm2Util.verify(qPubKey, HexUtil.encodeHexStr(SmUtil.sm3().digest(encData+timestamp+nonce)), signature);
        if (!verify) {
            throw new AppException("省金融平台验签失败");
        }
        // 不动产私钥解密
        String symmetricKey = Sm2Util.decrypt(bdcsy, encKey);
        //解密后请求报文
        String plainData = Sm4Util.decrypt(encData, cn.hutool.core.codec.Base64.decode(symmetricKey), Base64.decode(orgReqData.getIv()));
        LOGGER.info("省金融平台解密后：{}", plainData);
        String key = requestUrl.substring(requestUrl.lastIndexOf("/") + 1);
        if ("wwsqScddh".equals(key) && CommonConstantUtils.SYSTEM_VERSION_SC.equals(dataversion)){
            key = "sjrpt_wwsqScddh";
        }
        if ("wwsq_ykqjfztcx".equals(key) && CommonConstantUtils.SYSTEM_VERSION_SC.equals(dataversion)){
            key = "sjrpt_wwsq_ykqjfztcx";
        }
        // 请求参数包一层data
        if ("dydj".equals(key) || "dyzx".equals(key)) {
            // json数组
            JSONArray dataArr = JSONArray.parseArray(plainData);
            // 抵押创建和抵押注销的接口，申请登记类型需要对照
            dataArr.forEach(obj->{
                String sqdjlx = ((JSONObject) obj).getString("sqdjlx");
                sqdjlx = convertDsfZdToBdc("sjrpt", "SJRPT_ZD_SQDJLX", sqdjlx);
                ((JSONObject) obj).put("sqdjlx",sqdjlx);
            });
            JSONObject resJsonObj = new JSONObject();
            resJsonObj.put("data",dataArr);
            plainData = JSONObject.toJSONString(resJsonObj);
        }else if ("dzzz_cxzs".equals(key) || "wwsqdeltask".equals(key) || "dzzz_cxbyzzbs".equals(key) || "wwsq_sfssxx".equals(key) || "wwsqScddh".equals(key) || "sjrpt_wwsqScddh".equals(key)){
           // 不处理
        }else if ("bdcdycjgzyz".equals(key)){
            JSONObject  resJsonObj= JSONObject.parseObject(plainData);
            String gzldyid  = resJsonObj.getString("gzldyid");
            if (StringUtils.isNotBlank(gzldyid)){
                gzldyid = gzldyidMap.get(gzldyid);
            }
            resJsonObj.put("gzldyid",gzldyid);
            plainData = JSONObject.toJSONString(resJsonObj);

        }else{
            JSONObject data = JSONObject.parseObject(plainData);
            JSONObject resJsonObj = new JSONObject();
            resJsonObj.put("data",data);
            plainData = JSONObject.toJSONString(resJsonObj);
        }
        LOGGER.info("处理后请求参数：{}", plainData);
        // url替换
        String ipPort = appUrl.replace("/realestate-portal-ui", "");
        String httpUrl = map.get(key);
        httpUrl = httpUrl + "?access_token=" + tokenUtils.getAccessToken();
        String res = httpClientUtils.sendPostJsonRequest(ipPort + httpUrl, plainData, "省金融平台");
        LOGGER.info("省金融平台请求结果为：{}",res);
        // 特殊返回值处理
        if ("dzzz_cxzs".equals(key) || "wwsq_ykqjfztcx".equals(key) || "wwsq_sfssxx".equals(key) || "wwsqScddh".equals(key) || "sjrpt_wwsq_ykqjfztcx".equals(key)){
            // json数组
            JSONObject jsonObject = new JSONObject();
            JSONObject head = new JSONObject();
            JSONArray dataArr = JSONArray.parseArray(res);
            head.put("msg","查询成功");
            head.put("code","200");
            jsonObject.put("head",head);
            jsonObject.put("data",dataArr);
            res = JSONObject.toJSONString(jsonObject);
        } else if ("wwsqdeltask".equals(key)) {
            // code为0000改为200
            JSONObject jsonObject = new JSONObject();
            JSONObject head = JSONObject.parseObject(res);
            if (head != null && "0000".equals(head.getString("code"))) {
                head.put("code", "200");
            }
            jsonObject.put("head", head);
            res = JSONObject.toJSONString(jsonObject);
        } else if ("bdcdycjgzyz".equals(key)) {
            // jsonObject返回，没有head参数
            JSONObject jsonObject = new JSONObject();
            JSONObject head = new JSONObject();
            JSONObject data = JSONObject.parseObject(res);
            head.put("msg","成功");
            head.put("code","200");
            jsonObject.put("head",head);
            jsonObject.put("data",data);
            res = JSONObject.toJSONString(jsonObject);
        } else {
            // 返回参数里head的returncode改为code，code0000改为200
            JSONObject jsonObject = JSONObject.parseObject(res);
            JSONObject head = jsonObject.getJSONObject("head");
            if (head != null && StringUtils.isNotBlank(head.getString("returncode"))) {
                String code = head.getString("returncode");
                if ("0000".equals(code)) {
                    code = "200";
                }
                head.put("code", code);
                jsonObject.put("head", head);
                res = JSONObject.toJSONString(jsonObject);
            }
        }
        // 加密
        String encDataRet = Sm4Util.encrypt(res, Base64.decode(symmetricKey), Base64.decode(orgReqData.getIv()));
        String digest = Sm3Util.digest(encDataRet);
        // 不动产私钥签名
        String signatureRet = Sm2Util.sign(bdcsy, digest);
        JSONObject jsonObjectRet = new JSONObject();
        jsonObjectRet.put("encData", encDataRet);
        jsonObjectRet.put("signature", signatureRet);
        return jsonObjectRet;
    }

    @Override
    @PostMapping("/bhhlwsq")
    public void bhHlwSq(@RequestParam(name = "spxtywh") String spxtywh, @RequestParam(name = "spxtywh") String reason, @RequestParam(name = "spxtywh") String gzlslid) {
        if (StringUtils.isBlank(spxtywh)) {
            throw new AppException("驳回互联网申请缺少审批系统业务号！");
        }
        Map map = new HashMap();
        map.put("spxtywh", spxtywh);
        map.put("reason", reason);
        map.put("bhsj", DateUtils.getCurrentTimeStr());
        String userName = userManagerUtils.getCurrentUserName();
        map.put("bhr", userName);
        try {
            LOGGER.info("开始调用省金融平台驳回互联网申请接口，工作流实例id：{}，审批系统业务号：{}", gzlslid, spxtywh);
            Object response = exchangeBeanRequestService.request("bhhlwsq", map);
            LOGGER.info("驳回互联网申请接口返回结果：{}", response);
        } catch (Exception e) {
            LOGGER.error("---请求省金融平台驳回互联网申请异常:{},异常gzlslid:{}", e.getMessage(), gzlslid);
            throw new AppException("请求省金融平台驳回互联网申请异常");
        }
    }

    @Override
    @PostMapping("/tsems/{processInsId}")
    public void tsEmsxx(@PathVariable("processInsId") String processInsId) {
        BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
        bdcXmFbQO.setGzlslid(processInsId);
        List<BdcXmFbDO> bdcXmFbDOList = bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)){
            throw new AppException("不动产项目不存在，工作流实例id:"+processInsId);
        }
        BdcXmDO bdcXmDO = bdcXmDOList.get(0);
        if (CollectionUtils.isNotEmpty(bdcXmFbDOList) && CommonConstantUtils.LZFS_EMS.equals(bdcXmFbDOList.get(0).getLzfs())) {
            // 推送ems信息
            List<BdcSlYjxxDTO> bdcSlYjxxDTOS = bdcSlYjxxFeginService.queryBdcSlYjxxByGzlslid(processInsId);
            if (CollectionUtils.isNotEmpty(bdcSlYjxxDTOS)) {
                String wlddh = bdcSlYjxxDTOS.get(0).getWlddh();
                if (StringUtils.isNotBlank(wlddh)) {
                    Map emsMap = new HashMap();
                    emsMap.put("spxtywh", bdcXmDO.getSpxtywh());
                    emsMap.put("qxdm", bdcXmDO.getQxdm());
                    emsMap.put("kddh", wlddh);
                    try {
                        LOGGER.info("开始调用省金融平台接口推送ems信息，工作流实例id：{}，审批系统业务号：{}", processInsId, bdcXmDO.getSpxtywh());
                        Object tsResponse = exchangeBeanRequestService.request("tsemsxx", emsMap);
                        LOGGER.info("推送ems信息接口返回结果：{}", tsResponse);
                    } catch (Exception e) {
                        LOGGER.error("---请求省金融平台推送ems信息异常:{},异常gzlslid:{}", e.getMessage(), processInsId);
                        throw new AppException("请求省金融平台推送ems信息异常");
                    }
                }
            }
        }
    }


    /**
     * 字典项对照-第三方字典项转不动产
     *
     * @param value
     * @return
     */
    private String convertDsfZdToBdc(String dsfxtbs, String zdbs, String value) {
        // 查询第三方字典项对照关系
        BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
        bdcZdDsfzdgxDO.setZdbs(zdbs);
        bdcZdDsfzdgxDO.setDsfxtbs(dsfxtbs);
        bdcZdDsfzdgxDO.setDsfzdz(value);
        BdcZdDsfzdgxDO result = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
        if (result != null) {
            return result.getBdczdz();
        }
        return "";
    }
}
