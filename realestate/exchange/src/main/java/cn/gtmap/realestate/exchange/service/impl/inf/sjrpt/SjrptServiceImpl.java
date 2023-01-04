package cn.gtmap.realestate.exchange.service.impl.inf.sjrpt;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcHyzfDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.CommonUtil;
import cn.gtmap.realestate.exchange.core.dto.sjrpt.OrgReqData;
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
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @Date 2022/09/28
 * @description 安徽省金融平台服务-加密请求参数
 */
@Service(value = "sjrptServiceImpl")
public class SjrptServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(SjrptServiceImpl.class);

    @Autowired
    private HttpClientUtils httpClientUtils;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private BdcSfxxFeignService bdcSfxxFeignService;

    @Autowired
    private BdcSlSfxxFeignService bdcSlSfxxFeignService;


    /**
     * 获取公钥地址
     */
    public static final String GY_URL = "/api/v1/pubkey";

    /**
     * 注册公钥地址
     */
    public static final String REG_URL = "/api/v1/pubkey-reg";

    /**
     * 驳回申请地址
     */
    public static final String BHSQ_URL = "/api/v1/reject";

    /**
     * 推送ems地址
     */
    public static final String TS_EMS_URL = "/api/v1/tracking";

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


    /**
     * 省金融平台请求地址
     */
    @Value("${sjrpt.url:}")
    private String sjrpUrl;


    @Value("${sjrpt.appid:}")
    private String appid;

    /**
     * 不动产登记私钥
     */
    @Value("${sjrpt.nonce:}")
    private String nonce;


    /**
     * @param jsonObject 请求参数
     * @author <a href = "mailto:caolu@gtmap.cn">caolu</a>
     * @description 驳回互联网申请
     */
    public void bhhlwsq(JSONObject jsonObject) {
        String jsonStr = jsonObject.toString();
        LOGGER.info("驳回互联网申请请求参数：{}", jsonStr);
        String timestamp = String.valueOf(System.currentTimeMillis());
        JSONObject jsonObjectReq = getEncJsonObject(jsonStr, timestamp);
        LOGGER.info("开始调用省金融平台平台的驳回互联网申请接口,加密后参数为:{}", jsonObjectReq.toString());
        Map<String, Object> headMap = Maps.newHashMap();
        headMap.put("timestamp", timestamp);
        headMap.put("nonce", nonce);
        headMap.put("appId", appid);
        String response = httpClientUtils.sendPostJsonHeaderRequest(sjrpUrl + BHSQ_URL, jsonObjectReq.toString(), "驳回互联网申请", headMap);
        JSONObject res = JSONObject.parseObject(response);
        JSONObject head = JSONObject.parseObject(res.getString("head"));
        if (head != null && "200".equals(head.getString("code"))) {
            LOGGER.info("驳回互联网申请成功");
        } else {
            throw new AppException("驳回互联网申请失败");
        }
    }

    /**
     * @param jsonObject
     * @author <a href = "mailto:caolu@gtmap.cn">caolu</a>
     * @description 推送ems信息
     */
    public void tsEmsxx(JSONObject jsonObject) {
        String jsonStr = jsonObject.toString();
        LOGGER.info("推送ems信息请求参数：{}", jsonStr);
        String timestamp = String.valueOf(System.currentTimeMillis());
        JSONObject jsonObjectReq = getEncJsonObject(jsonStr, timestamp);
        LOGGER.info("开始调用省金融平台的推送ems信息接口,加密后参数为:{}", jsonObjectReq.toString());
        Map<String, Object> headMap = Maps.newHashMap();
        headMap.put("timestamp", timestamp);
        headMap.put("nonce", nonce);
        headMap.put("appId", appid);
        String response = httpClientUtils.sendPostJsonHeaderRequest(sjrpUrl + TS_EMS_URL, jsonObjectReq.toString(), "推送ems信息", headMap);
        JSONObject res = JSONObject.parseObject(response);
        JSONObject head = JSONObject.parseObject(res.getString("head"));
        if (head != null && "200".equals(head.getString("code"))) {
            LOGGER.info("推送ems信息成功");
        } else {
            throw new AppException("推送ems信息失败");
        }
    }


    /**
     * 加密请求参数
     *
     * @param jsonStr
     * @return 返回加密后参数
     */
    private JSONObject getEncJsonObject(String jsonStr, String timestamp) {
        regBdcGy();
        byte[] symmetricKey = Sm4Util.initKey();
        byte[] iv = Sm4Util.initIv();

        String encData = Sm4Util.encrypt(jsonStr, symmetricKey, iv);

        // b的公钥加密
        String sjrptGy = getSjrptGyUrl();
        LOGGER.info("省金融平台公钥为：{}", sjrptGy);
        if (StringUtils.isBlank(sjrptGy)) {
            throw new AppException("获取省金融平台公钥失败");
        }
        String s1 = Base64.encode(symmetricKey);
        String encKey = Sm2Util.encrypt(sjrptGy, s1);

        String reqIv = Base64.encode(iv);
        String digest = Sm3Util.digest(encData + timestamp + nonce);

        // 签名用a的私钥
        String signature = Sm2Util.sign(bdcsy, digest);

        JSONObject jsonObjectReq = new JSONObject();
        jsonObjectReq.put("encData", encData);
        jsonObjectReq.put("encKey", encKey);
        jsonObjectReq.put("iv", reqIv);
        jsonObjectReq.put("signature", signature);
        // a的公钥
        jsonObjectReq.put("qPubkey", bdcgy);
        return jsonObjectReq;
    }

    /**
     * 获取公钥，注册公钥
     *
     * @return
     */
    public String getSjrptGyUrl() {
        String pubKey = "";
        Map<String, Object> headMap = Maps.newHashMap();
        headMap.put("timestamp", System.currentTimeMillis());
        headMap.put("nonce", nonce);
        headMap.put("appId", appid);
        String response = httpClientUtils.sendGetHeadRequest(sjrpUrl + GY_URL, null, headMap);
        LOGGER.info("获取省金融平台公钥，返回参数:{}", response);
        if (StringUtils.isNotBlank(response)) {
            JSONObject object = JSONObject.parseObject(response);
            String data = object.getString("data");
            JSONObject jsonData = JSONObject.parseObject(data);
            pubKey = (String) jsonData.get("pubKey");
        }
        return pubKey;
    }


    /**
     * 获取公钥，注册公钥
     *
     * @return
     */
    public void regBdcGy() {
        Map<String, Object> headMap = Maps.newHashMap();
        headMap.put("timestamp", System.currentTimeMillis());
        headMap.put("nonce", nonce);
        headMap.put("appId", appid);
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("effect_timestamp", System.currentTimeMillis());
        paramMap.put("invalid_timestamp", System.currentTimeMillis() + 1000000000);
        paramMap.put("pubKey", bdcgy);
        String response = httpClientUtils.sendPostJsonHeaderRequest(sjrpUrl + REG_URL, JSONObject.toJSONString(paramMap), "注册公钥", headMap);
        LOGGER.info("注册公钥，返回参数:{}", response);
    }

    /**
     * @description: 舒城生成支付订单
     * @author: <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @date: 2022/11/8 10:36
     * @param jsonObject
     * @return: java.util.Map
     **/
    public JSONArray scZfdd(JSONObject jsonObject){
        JSONArray dataArr = new JSONArray();
        String slbh = jsonObject.getString("slbh");
        String xmid = jsonObject.getString("xmid");
        String ze = jsonObject.getString("ze");
        String jffs = jsonObject.getString("jffs");
        if (StringUtils.isBlank(slbh) || StringUtils.isBlank(xmid) || StringUtils.isBlank(ze) || StringUtils.isBlank(jffs)) {
            throw new AppException("缺少必填参数");
        } else {
            //项目信息
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setSlbh(slbh);
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isEmpty(bdcXmDOList)) {
                throw new AppException("项目不存在");
            } else {
                Map<String, String> map = bdcSfxxFeignService.queryHyzfEwm(bdcXmDOList.get(0).getGzlslid(), xmid);
                BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
                bdcSlSfxxDO.setSlbh(slbh);
                bdcSlSfxxDO.setXmid(xmid);
                if (StringUtils.isNotBlank(map.get("qlrHyzfUrl")) || StringUtils.isNotBlank(map.get("hyzfUrl"))) {
                    bdcSlSfxxDO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                    List<BdcSlSfxxDO> qlrSlSfxxDOS = bdcSlSfxxFeignService.queryBdcSlSfxx(bdcSlSfxxDO);
                    BdcSlSfxxDO qlrSlSfxxDO = qlrSlSfxxDOS.get(0);
                    JSONObject dataJson = new JSONObject();
                    dataJson.put("ddbh", qlrSlSfxxDO.getHyzfddid());
                    dataJson.put("jfurl", qlrSlSfxxDO.getHyzfurl());
                    dataJson.put("pincode", qlrSlSfxxDO.getJfsewmurl());
                    dataJson.put("id", qlrSlSfxxDO.getSfxxid());
                    dataArr.add(dataJson);
                }
                if (StringUtils.isNotBlank(map.get("ywrHyzfUrl"))) {
                    bdcSlSfxxDO.setQlrlb(CommonConstantUtils.QLRLB_YWR);
                    List<BdcSlSfxxDO> ywrrSlSfxxDOS = bdcSlSfxxFeignService.queryBdcSlSfxx(bdcSlSfxxDO);
                    BdcSlSfxxDO ywrSlSfxxDO = ywrrSlSfxxDOS.get(0);
                    JSONObject dataJson = new JSONObject();
                    dataJson.put("ddbh", ywrSlSfxxDO.getHyzfddid());
                    dataJson.put("jfurl", ywrSlSfxxDO.getHyzfurl());
                    dataJson.put("pincode", ywrSlSfxxDO.getJfsewmurl());
                    dataJson.put("id", ywrSlSfxxDO.getSfxxid());
                    dataArr.add(dataJson);
                }
            }
        }
        return dataArr;
    }

    /**
     * @description: 舒城查询缴费状态
     * @author: <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @date: 2022/11/8 16:45
     * @param jsonObject
     * @return: com.alibaba.fastjson.JSONObject
     **/
    public JSONArray getSwZfzt(JSONObject jsonObject){
        JSONArray dataArr = new JSONArray();
        JSONObject paramJson = jsonObject.getJSONObject("data");
        String slbh = paramJson.getString("slbh");
        String ddbh = paramJson.getString("ddbh");
        if (StringUtils.isBlank(slbh) &&  StringUtils.isBlank(ddbh)) {
            throw new AppException("缺少必填参数");
        } else {
            BdcSlSfxxDO bdcSlSfxxQO = new BdcSlSfxxDO();
            if (StringUtils.isNotBlank(slbh)){
                bdcSlSfxxQO.setSlbh(slbh);
            }
            if (StringUtils.isNotBlank(ddbh)){
                bdcSlSfxxQO.setHyzfddid(ddbh);
            }
            List<BdcSlSfxxDO> slSfxxDOS = bdcSlSfxxFeignService.queryBdcSlSfxx(bdcSlSfxxQO);
            if (CollectionUtils.isEmpty(slSfxxDOS)){
                throw new AppException("无收费信息");
            }else{
                bdcSfxxFeignService.queryHyzfJfzt(slSfxxDOS.get(0).getGzlslid(),slSfxxDOS.get(0).getXmid());
                // 再次查询
                slSfxxDOS = bdcSlSfxxFeignService.queryBdcSlSfxx(bdcSlSfxxQO);
                for (BdcSlSfxxDO bdcSlSfxxDO: slSfxxDOS){
                    JSONObject dataJson = new JSONObject();
                    // 缴费状态需要第三方对照
                    dataJson.put("jfzt",bdcSlSfxxDO.getSfzt());
                    dataJson.put("ddje",bdcSlSfxxDO.getHj());
                    dataJson.put("ze",bdcSlSfxxDO.getHj());
                    dataJson.put("url",bdcSlSfxxDO.getHyzfurl());
                    dataJson.put("qlrlb",bdcSlSfxxDO.getQlrlb());
                    dataJson.put("ddbh",bdcSlSfxxDO.getHyzfddid());
                    dataArr.add(dataJson);
                }
            }
        }
        return dataArr;
    }




}
