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
 * @description ???????????????????????????-??????????????????
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
     * ??????????????????
     */
    public static final String GY_URL = "/api/v1/pubkey";

    /**
     * ??????????????????
     */
    public static final String REG_URL = "/api/v1/pubkey-reg";

    /**
     * ??????????????????
     */
    public static final String BHSQ_URL = "/api/v1/reject";

    /**
     * ??????ems??????
     */
    public static final String TS_EMS_URL = "/api/v1/tracking";

    /**
     * ?????????????????????
     */
    @Value("${sjrpt.bdcgy:}")
    private String bdcgy;

    /**
     * ?????????????????????
     */
    @Value("${sjrpt.bdcsy:}")
    private String bdcsy;


    /**
     * ???????????????????????????
     */
    @Value("${sjrpt.url:}")
    private String sjrpUrl;


    @Value("${sjrpt.appid:}")
    private String appid;

    /**
     * ?????????????????????
     */
    @Value("${sjrpt.nonce:}")
    private String nonce;


    /**
     * @param jsonObject ????????????
     * @author <a href = "mailto:caolu@gtmap.cn">caolu</a>
     * @description ?????????????????????
     */
    public void bhhlwsq(JSONObject jsonObject) {
        String jsonStr = jsonObject.toString();
        LOGGER.info("????????????????????????????????????{}", jsonStr);
        String timestamp = String.valueOf(System.currentTimeMillis());
        JSONObject jsonObjectReq = getEncJsonObject(jsonStr, timestamp);
        LOGGER.info("???????????????????????????????????????????????????????????????,??????????????????:{}", jsonObjectReq.toString());
        Map<String, Object> headMap = Maps.newHashMap();
        headMap.put("timestamp", timestamp);
        headMap.put("nonce", nonce);
        headMap.put("appId", appid);
        String response = httpClientUtils.sendPostJsonHeaderRequest(sjrpUrl + BHSQ_URL, jsonObjectReq.toString(), "?????????????????????", headMap);
        JSONObject res = JSONObject.parseObject(response);
        JSONObject head = JSONObject.parseObject(res.getString("head"));
        if (head != null && "200".equals(head.getString("code"))) {
            LOGGER.info("???????????????????????????");
        } else {
            throw new AppException("???????????????????????????");
        }
    }

    /**
     * @param jsonObject
     * @author <a href = "mailto:caolu@gtmap.cn">caolu</a>
     * @description ??????ems??????
     */
    public void tsEmsxx(JSONObject jsonObject) {
        String jsonStr = jsonObject.toString();
        LOGGER.info("??????ems?????????????????????{}", jsonStr);
        String timestamp = String.valueOf(System.currentTimeMillis());
        JSONObject jsonObjectReq = getEncJsonObject(jsonStr, timestamp);
        LOGGER.info("????????????????????????????????????ems????????????,??????????????????:{}", jsonObjectReq.toString());
        Map<String, Object> headMap = Maps.newHashMap();
        headMap.put("timestamp", timestamp);
        headMap.put("nonce", nonce);
        headMap.put("appId", appid);
        String response = httpClientUtils.sendPostJsonHeaderRequest(sjrpUrl + TS_EMS_URL, jsonObjectReq.toString(), "??????ems??????", headMap);
        JSONObject res = JSONObject.parseObject(response);
        JSONObject head = JSONObject.parseObject(res.getString("head"));
        if (head != null && "200".equals(head.getString("code"))) {
            LOGGER.info("??????ems????????????");
        } else {
            throw new AppException("??????ems????????????");
        }
    }


    /**
     * ??????????????????
     *
     * @param jsonStr
     * @return ?????????????????????
     */
    private JSONObject getEncJsonObject(String jsonStr, String timestamp) {
        regBdcGy();
        byte[] symmetricKey = Sm4Util.initKey();
        byte[] iv = Sm4Util.initIv();

        String encData = Sm4Util.encrypt(jsonStr, symmetricKey, iv);

        // b???????????????
        String sjrptGy = getSjrptGyUrl();
        LOGGER.info("???????????????????????????{}", sjrptGy);
        if (StringUtils.isBlank(sjrptGy)) {
            throw new AppException("?????????????????????????????????");
        }
        String s1 = Base64.encode(symmetricKey);
        String encKey = Sm2Util.encrypt(sjrptGy, s1);

        String reqIv = Base64.encode(iv);
        String digest = Sm3Util.digest(encData + timestamp + nonce);

        // ?????????a?????????
        String signature = Sm2Util.sign(bdcsy, digest);

        JSONObject jsonObjectReq = new JSONObject();
        jsonObjectReq.put("encData", encData);
        jsonObjectReq.put("encKey", encKey);
        jsonObjectReq.put("iv", reqIv);
        jsonObjectReq.put("signature", signature);
        // a?????????
        jsonObjectReq.put("qPubkey", bdcgy);
        return jsonObjectReq;
    }

    /**
     * ???????????????????????????
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
        LOGGER.info("??????????????????????????????????????????:{}", response);
        if (StringUtils.isNotBlank(response)) {
            JSONObject object = JSONObject.parseObject(response);
            String data = object.getString("data");
            JSONObject jsonData = JSONObject.parseObject(data);
            pubKey = (String) jsonData.get("pubKey");
        }
        return pubKey;
    }


    /**
     * ???????????????????????????
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
        String response = httpClientUtils.sendPostJsonHeaderRequest(sjrpUrl + REG_URL, JSONObject.toJSONString(paramMap), "????????????", headMap);
        LOGGER.info("???????????????????????????:{}", response);
    }

    /**
     * @description: ????????????????????????
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
            throw new AppException("??????????????????");
        } else {
            //????????????
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setSlbh(slbh);
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isEmpty(bdcXmDOList)) {
                throw new AppException("???????????????");
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
     * @description: ????????????????????????
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
            throw new AppException("??????????????????");
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
                throw new AppException("???????????????");
            }else{
                bdcSfxxFeignService.queryHyzfJfzt(slSfxxDOS.get(0).getGzlslid(),slSfxxDOS.get(0).getXmid());
                // ????????????
                slSfxxDOS = bdcSlSfxxFeignService.queryBdcSlSfxx(bdcSlSfxxQO);
                for (BdcSlSfxxDO bdcSlSfxxDO: slSfxxDOS){
                    JSONObject dataJson = new JSONObject();
                    // ?????????????????????????????????
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
