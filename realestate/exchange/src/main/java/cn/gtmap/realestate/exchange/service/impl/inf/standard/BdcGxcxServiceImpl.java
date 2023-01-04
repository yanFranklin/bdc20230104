package cn.gtmap.realestate.exchange.service.impl.inf.standard;

import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.bzfwhhxx.request.BzfwhhxxCxywcsDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.bzfwhhxx.request.BzfwhhxxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.csyxzm.request.CsyszmCxywcsDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.csyxzm.request.CsyxzmRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.hyxxhygr.request.HyxxhygrCxywcsDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.hyxxhygr.request.HyxxhygrRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.hyxxhysf.request.HyxxhysfCxywcsDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.hyxxhysf.request.HyxxhysfRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.shttfrdjzs.request.ShttfrdjzsCxywcsDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.shttfrdjzs.request.ShttfrdjzsdjzsrRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.swyxzm.request.SwyxzmCxywcsDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.swyxzm.request.SwyxzmRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.sydjxxcx.request.SydjxxcxCxywcsDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.sydjxxcx.request.SydjxxcxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.xuancheng.gwsdk.*;
import cn.gtmap.realestate.common.core.dto.exchange.xuancheng.gxcx.GxcxCsyxzmDTO;
import cn.gtmap.realestate.common.core.dto.exchange.xuancheng.gxcx.GxcxCsyxzmResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.xuancheng.gxcx.GxcxSwyxzmDTO;
import cn.gtmap.realestate.common.core.dto.exchange.xuancheng.gxcx.GxcxSwyxzmResponseDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.exchange.service.inf.standard.BdcGxcxService;
import cn.gtmap.realestate.exchange.util.HttpClientUtils;
import cn.gtmap.realestate.exchange.util.XmlEntityConvertUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iflytek.fsp.shield.java.sdk.model.ApiResponse;
import org.apache.axis.client.Call;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.encoding.XMLType;
import javax.xml.soap.SOAPElement;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class BdcGxcxServiceImpl implements BdcGxcxService {
    private final Logger LOGGER = LoggerFactory.getLogger(BdcGxcxServiceImpl.class);

    @Autowired
    BdcGxcxTokenServiceImpl bdcGxcxTokenService;

    @Autowired
    HttpClientUtils httpClientUtils;

    /**
     *
     */
    @Value("${gxcx.token.rest_url:http://59.255.22.70:8443/gateway/httpproxy}")
    private String restUrl;

    /**
     * 获取token的url
     */
    @Value("${gxcx.token.appSecretUrl:http://59.255.22.70:8443/gateway/httpproxy}")
    private String appSecretUrl;

    @Value("${gxcx.csyxzm.url:}")
    private String csyxzmUrl;

    /**
     * 宣城-sdk查询常住人口
     *
     * @param request
     * @return
     */
    @Override
    public JSONArray SkdCzrkcx(JSONObject request) {
        //return testResponse();
        String identityNumber = request.getString("identityNumber");
        ShieldSyncAppCzrkcx op = new ShieldSyncAppCzrkcx();
        JSONObject json = new JSONObject();
        json.put("SenderId", "C10-10000006");//请求方编号
        json.put("ServiceId", "S10-10000007");//服务方编号
        json.put("AuthorizeInfo", "E0AA-7B24-0AED-CD8B");//请求方授权码
        JSONObject endUser = new JSONObject();
        endUser.put("UserCardId", "123456789987654321");
        endUser.put("UserName", "admin");
        endUser.put("UserDept", "010000");
        endUser.put("UserIp", "172.16.105.21");
        endUser.put("CallReason", "测试");
        json.put("EndUser", endUser);//访问者信息
        json.put("Method", "Query");//调用方法名
        JSONObject params = new JSONObject();
        params.put("Condition", "GMSFHM='" + identityNumber + "'");
        params.put("RequiredItems", "GMSFHM,XM,XP,XB,MZ,CSRQ,CSDGJDQ,CSDSSXQ,RYZT,HB,HZXM,HJQFSJ,HJDZ,YHZGX,HJQFJG");
        params.put("OrderItems", "{'GMSFHM':'desc'}");
        params.put("InfoCodeMode", "1");
        json.put("Params", params);//参数信息
        ApiResponse apiResponse = op.Czrkcx(json.toString().getBytes());
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            LOGGER.info("宣城-sdk查询常住人口 response code = " + apiResponse.getStatusCode());
            LOGGER.info("宣城-sdk查询常住人口 response content = " + new String(apiResponse.getBody(), "UTF-8"));
            JSONObject jsonObject = JSON.parseObject(new String(apiResponse.getBody()));
            return JSONArray.parseArray(jsonObject
                    .getJSONObject("PayLoad")
                    .getJSONObject("Data")
                    .getString("Result"));
        } catch (Exception e) {
            return null;
        }
    }


    @NotNull
    private Map<String, Object> getHeardMap(JSONObject config) {
        config.put("rtime", System.currentTimeMillis());
        String sign = bdcGxcxTokenService.sign(config);
        Map<String, Object> headerParamMap = new HashMap<>();
        headerParamMap.put("gjzwfwpt_sid", config.getString("sid"));
        headerParamMap.put("gjzwfwpt_rid", config.getString("rid"));
        headerParamMap.put("gjzwfwpt_rtime", config.getString("rtime"));
        headerParamMap.put("gjzwfwpt_sign", sign);
        headerParamMap.put("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        headerParamMap.put("connection", "Keep-Alive");
        return headerParamMap;
    }

    @NotNull
    private static Map<String, Object> getCommonParamMap(JSONObject config, Map<String, Object> headerParamMap) {
        Map<String, Object> requestParamMap = new HashMap<>();
        requestParamMap.put("access_key", config.getString("appkey"));
        requestParamMap.put("format", "json");
        requestParamMap.put("request_id", UUID.randomUUID());
        requestParamMap.put("sign", headerParamMap.get("gjzwfwpt_sign"));
        requestParamMap.put("timestamp", config.getString("gjzwfwpt_rtime"));
        requestParamMap.put("version", "1.0");
        return requestParamMap;
    }


    /**
     * 宣城-出生医学证明查询
     *
     * @param info
     * @return
     */
    public GxcxCsyxzmResponseDTO csyxzmCx(CsyxzmRequestDTO info) {
        JSONObject config = JSON.parseObject("" +
                "{\n" +
                "    \"rid\": \"TE3400003400000001@7c15767bdfd643ed9484e2a8110a30db\",\n" +
                "    \"sid\": \"s_1200002500000_2922\",\n" +
                "    \"appkey\": \"2c355286659ebba1653341f51f2e800c\",\n" +
                "    \"orgName\": \"国家卫计委\",\n" +
                "    \"orgCode\": \"000000000\",\n" +
                "    \"username\": \"GJCSZM\",\n" +
                "    \"password\": \"888888\"\n" +
                "  }");
        LOGGER.info("宣城出生医学证明查询,参数:{}", JSON.toJSONString(info));
        Map<String, Object> requestParamMap = new HashMap<>();
        requestParamMap.put("username", config.getString("username"));
        requestParamMap.put("password", config.getString("password"));

        //处理请求参数
        GxcxCsyxzmDTO csyxzmDTO = new GxcxCsyxzmDTO();
        GxcxCsyxzmDTO.RequestInfo requestInfo = new GxcxCsyxzmDTO.RequestInfo();
        requestInfo.setRequestDate(DateUtils.getCurrentTimeStr());
        requestInfo.setRequestOrgCode(config.getString("orgCode"));
        requestInfo.setRequestOrgName(config.getString("orgName"));
        csyxzmDTO.setRequestinfo(requestInfo);
        GxcxCsyxzmDTO.Body body = new GxcxCsyxzmDTO.Body();
        GxcxCsyxzmDTO.Data data = new GxcxCsyxzmDTO.Data();
        CsyszmCxywcsDTO csyszmCxywcsDTO = info.getCxywcs().get(0);
        data.setBirthCode(csyszmCxywcsDTO.getCszmbh());
        data.setMomIdCode(csyszmCxywcsDTO.getMqzjbm());
        data.setMomName(csyszmCxywcsDTO.getMqxm());
        body.setData(data);
        csyxzmDTO.setBody(body);
        //转换为xml
        requestParamMap.put("xmlInput", Base64Utils.encodeByteToBase64Str(
                XmlEntityConvertUtil.convertObjectToXml(csyxzmDTO).getBytes(StandardCharsets.UTF_8)
        ));

        //通用请求头
        Map<String, Object> headerParamMap = getHeardMap(config);

        String response = sendWebServiceReq(csyxzmUrl, "getBirthInfo", requestParamMap, headerParamMap);
        //String response = testResponse("getBirthInfo");
        LOGGER.info("宣城出生医学证明查询,返回:{}", response);
        //转成对象
        GxcxCsyxzmResponseDTO csyxzmResponseDTO = XmlEntityConvertUtil.toBean(response, GxcxCsyxzmResponseDTO.class);
        LOGGER.info("宣城出生医学证明查询,xml转换后的对象为:{}", JSON.toJSONString(csyxzmResponseDTO));
        return csyxzmResponseDTO;
    }


    /**
     * 宣城-死亡医学证明查询接口
     *
     * @param info
     * @return
     */
    public GxcxSwyxzmResponseDTO swyxzmCx(SwyxzmRequestDTO info) {
        JSONObject config = JSON.parseObject("" +
                "{\n" +
                "    \"rid\": \"TE3400003400000001@7c15767bdfd643ed9484e2a8110a30db\",\n" +
                "    \"sid\": \"s_1200002500000_2922\",\n" +
                "    \"appkey\": \"2c355286659ebba1653341f51f2e800c\",\n" +
                "    \"orgName\": \"国家卫计委\",\n" +
                "    \"orgCode\": \"000000000\",\n" +
                "    \"username\": \"GJCSZM\",\n" +
                "    \"password\": \"888888\"\n" +
                "  }");
        LOGGER.info("死亡医学证明查询接口,参数:{}", JSON.toJSONString(info));
        Map<String, Object> requestParamMap = new HashMap<>();
        requestParamMap.put("username", config.getString("username"));
        requestParamMap.put("password", config.getString("password"));

        //处理请求参数
        GxcxSwyxzmDTO swyxzmDTO = new GxcxSwyxzmDTO();
        GxcxSwyxzmDTO.RequestInfo requestInfo = new GxcxSwyxzmDTO.RequestInfo();
        requestInfo.setRequestDate(DateUtils.getCurrentTimeStr());
        requestInfo.setRequestOrgCode(config.getString("orgCode"));
        requestInfo.setRequestOrgName(config.getString("orgName"));
        swyxzmDTO.setRequestinfo(requestInfo);

        GxcxSwyxzmDTO.Body body = new GxcxSwyxzmDTO.Body();
        GxcxSwyxzmDTO.Data data = new GxcxSwyxzmDTO.Data();
        SwyxzmCxywcsDTO swyxzmCxywcsDTO = info.getCxywcs().get(0);
        data.setGMSFZH(swyxzmCxywcsDTO.getSfzh());
        data.setXM(swyxzmCxywcsDTO.getXm());
        body.setData(data);
        swyxzmDTO.setBody(body);
        //转换为xml
        requestParamMap.put("xmlInput", Base64Utils.encodeByteToBase64Str(
                XmlEntityConvertUtil.convertObjectToXml(swyxzmDTO).getBytes(StandardCharsets.UTF_8)
        ));

        //通用请求头
        Map<String, Object> headerParamMap = getHeardMap(config);

        String response = sendWebServiceReq(csyxzmUrl, "getSwyxzmInfo", requestParamMap, headerParamMap);
        //String response = testResponse("getSwyxzmInfo");
        LOGGER.info("死亡医学证明查询接口,返回:{}", response);
        //转成对象
        GxcxSwyxzmResponseDTO swyxzmResponseDTO = XmlEntityConvertUtil.toBean(response, GxcxSwyxzmResponseDTO.class);
        LOGGER.info("死亡医学证明查询接口,xml转换后的对象为:{}", JSON.toJSONString(swyxzmResponseDTO));
        return swyxzmResponseDTO;
    }

    /**
     * 民政部-婚姻登记信息核验（个人）接口
     *
     * @param info
     * @return
     */
    public JSONArray grhyhy(HyxxhygrRequestDTO info) {
        JSONObject config = JSON.parseObject("" +
                "{\n" +
                "    \"rid\": \"TE3400003400000001@e6ab7e08912e48a58b7d3af7f7907aa5\",\n" +
                "    \"sid\": \"751cde33ed5d46e7ba7f47f18d2b23c3\",\n" +
                "    \"appkey\": \"122a965b58f8a98303430ce863b43c03\",\n" +
                "    \"orgName\": \"国家卫计委\",\n" +
                "    \"orgCode\": \"000000000\",\n" +
                "    \"username\": \"GJCSZM\",\n" +
                "    \"password\": \"888888\"\n" +
                "  }");
        LOGGER.info("婚姻登记信息核验（个人）,参数:{}", JSON.toJSONString(info));

        //通用请求头
        Map<String, Object> headerParamMap = getHeardMap(config);
        LOGGER.info("婚姻登记信息核验（个人）,通用请求头:{}", JSON.toJSONString(headerParamMap));

        Map<String, Object> requestParamMap = getCommonParamMap(config, headerParamMap);
        List<HyxxhygrCxywcsDTO> cxywcs = info.getCxywcs();
        JSONObject param = new JSONObject();
        param.put("name_man", cxywcs.get(0).getName_man());
        param.put("cert_num_man", cxywcs.get(0).getCert_num_man());
        requestParamMap.put("biz_content", JSON.toJSONString(param));
        LOGGER.info("婚姻登记信息核验（个人）,请求参数:{}", JSON.toJSONString(requestParamMap));
        String response = "";

        try {
            response = httpClientUtils.sendFormRequest(restUrl, requestParamMap, headerParamMap);
        } catch (Exception e) {
            e.printStackTrace();
            response = testResponse("grhyhy");
        }
        LOGGER.info("婚姻登记信息核验（个人）,返回:{}", response);

        JSONObject jsonObject = JSON.parseObject(response);
        LOGGER.info("---民政部-婚姻登记信息核验（个人）接口:{}", jsonObject);

        if (!"1".equals(jsonObject.getString("code"))) {
            throw new AppException(jsonObject.getString("msg"));
        }
        JSONObject result = jsonObject.getJSONObject("biz_data");
        return result.getJSONArray("result");
    }

    /**
     * 民政部-婚姻登记信息核验（双方）接口
     *
     * @param info
     * @return
     */
    public JSONArray sfhyhy(HyxxhysfRequestDTO info) {
        JSONObject config = JSON.parseObject("" +
                "{\n" +
                "    \"rid\": \"TE3400003400000001@e6ab7e08912e48a58b7d3af7f7907aa5\",\n" +
                "    \"sid\": \"ab7584434a0e4873bda98bc029f79391\",\n" +
                "    \"appkey\": \"d0761144d1e826cce297a359bb2a5c7a\",\n" +
                "    \"orgName\": \"国家卫计委\",\n" +
                "    \"orgCode\": \"000000000\",\n" +
                "    \"username\": \"GJCSZM\",\n" +
                "    \"password\": \"888888\"\n" +
                "  }");
        LOGGER.info("婚姻登记信息核验（双方）,参数:{}", JSON.toJSONString(info));

        //通用请求头
        Map<String, Object> headerParamMap = getHeardMap(config);
        LOGGER.info("婚姻登记信息核验（双方）,通用请求头:{}", JSON.toJSONString(headerParamMap));

        Map<String, Object> requestParamMap = getCommonParamMap(config, headerParamMap);
        List<HyxxhysfCxywcsDTO> cxywcs = info.getCxywcs();
        JSONObject param = new JSONObject();
        param.put("name_man", cxywcs.get(0).getName_man());
        param.put("cert_num_man", cxywcs.get(0).getCert_num_man());
        param.put("name_woman", cxywcs.get(0).getName_woman());
        param.put("cert_num_woman", cxywcs.get(0).getCert_num_woman());
        requestParamMap.put("biz_content", JSON.toJSONString(param));
        LOGGER.info("婚姻登记信息核验（双方）,请求参数:{}", JSON.toJSONString(requestParamMap));

        String response = "";

        try {
            response = httpClientUtils.sendFormRequest(restUrl, requestParamMap, headerParamMap);
        } catch (Exception e) {
            e.printStackTrace();
            response = testResponse("sfhyhy");
        }

        LOGGER.info("婚姻登记信息核验（双方）,返回:{}", response);

        JSONObject jsonObject = JSON.parseObject(response);
        LOGGER.info("---民政部-婚姻登记信息核验（双方）接口:{}", jsonObject);

        if (!"1".equals(jsonObject.getString("code"))) {
            throw new AppException(jsonObject.getString("msg"));
        }
        JSONObject result = jsonObject.getJSONObject("biz_data");
        return result.getJSONArray("result");
    }

    /**
     * 民政部_社会团体法人登记证书查询
     *
     * @param info
     * @return
     */
    public JSONArray shttfrzs(ShttfrdjzsdjzsrRequestDTO info) {
        JSONObject config = JSON.parseObject("" +
                "{\n" +
                "    \"rid\": \"TE3400003400000001@e6ab7e08912e48a58b7d3af7f7907aa5\",\n" +
                "    \"sid\": \"aa6af3ad8a144547a1f90c2de6e12ed7\",\n" +
                "    \"appkey\": \"744e9640356defaffe459a308fa5d2bd\",\n" +
                "    \"orgName\": \"国家卫计委\",\n" +
                "    \"orgCode\": \"000000000\",\n" +
                "    \"username\": \"GJCSZM\",\n" +
                "    \"password\": \"888888\"\n" +
                "  }");
        LOGGER.info("社会团体法人登记证书查询,参数:{}", JSON.toJSONString(info));

        //通用请求头
        Map<String, Object> headerParamMap = getHeardMap(config);
        LOGGER.info("社会团体法人登记证书查询,通用请求头:{}", JSON.toJSONString(headerParamMap));

        Map<String, Object> requestParamMap = getCommonParamMap(config, headerParamMap);
        List<ShttfrdjzsCxywcsDTO> cxywcs = info.getCxywcs();
        JSONObject param = new JSONObject();
        param.put("usc_code", cxywcs.get(0).getUsc_code());
        param.put("org_name", cxywcs.get(0).getOrg_name());
        requestParamMap.put("biz_content", JSON.toJSONString(param));
        LOGGER.info("社会团体法人登记证书查询,请求参数:{}", JSON.toJSONString(requestParamMap));

        String response = "";

        try {
            response = httpClientUtils.sendFormRequest(restUrl, requestParamMap, headerParamMap);
        } catch (Exception e) {
            e.printStackTrace();
            response = testResponse("shttfrzs");
        }

        LOGGER.info("社会团体法人登记证书查询,返回:{}", response);

        JSONObject jsonObject = JSON.parseObject(response);

        if (!"1".equals(jsonObject.getString("code"))) {
            throw new AppException(jsonObject.getString("msg"));
        }
        JSONObject result = jsonObject.getJSONObject("biz_data");
        return result.getJSONArray("rows");
    }

    /**
     * 民政部_收养登记证信息（国内）
     *
     * @param info
     * @return
     */
    public JSONArray sydjxx(SydjxxcxRequestDTO info) {
        JSONObject config = JSON.parseObject("" +
                "{\n" +
                "    \"rid\": \"TE3400003400000001@e6ab7e08912e48a58b7d3af7f7907aa5\",\n" +
                "    \"sid\": \"s_111000000000131433_5629\",\n" +
                "    \"appkey\": \"9978b080b748b300bc006f706e1c7378\",\n" +
                "    \"orgName\": \"国家卫计委\",\n" +
                "    \"orgCode\": \"000000000\",\n" +
                "    \"username\": \"GJCSZM\",\n" +
                "    \"password\": \"888888\"\n" +
                "  }");
        LOGGER.info("收养登记证信息,参数:{}", JSON.toJSONString(info));

        //通用请求头
        Map<String, Object> headerParamMap = getHeardMap(config);
        LOGGER.info("收养登记证信息,通用请求头:{}", JSON.toJSONString(headerParamMap));

        Map<String, Object> requestParamMap = getCommonParamMap(config, headerParamMap);
        List<SydjxxcxCxywcsDTO> cxywcs = info.getCxywcs();
        JSONObject param = new JSONObject();
        param.put("querytype", cxywcs.get(0).getQuerytype());
        param.put("cert_num", cxywcs.get(0).getCert_num());
        param.put("name", cxywcs.get(0).getName());
        requestParamMap.put("biz_content", JSON.toJSONString(param));
        LOGGER.info("收养登记证信息,请求参数:{}", JSON.toJSONString(requestParamMap));

        String response = "";

        try {
            response = httpClientUtils.sendFormRequest(restUrl, requestParamMap, headerParamMap);
        } catch (Exception e) {
            e.printStackTrace();
            response = testResponse("sydjxx");
        }

        LOGGER.info("收养登记证信息,返回:{}", response);

        JSONObject jsonObject = JSON.parseObject(response);

        if (!"1".equals(jsonObject.getString("code"))) {
            throw new AppException(jsonObject.getString("msg"));
        }
        JSONObject result = jsonObject.getJSONObject("biz_data");
        return result.getJSONArray("result");
    }

    /**
     * 民政部_殡葬服务火化信息
     *
     * @param info
     * @return
     */
    public JSONArray bzhhxx(BzfwhhxxRequestDTO info) {
        JSONObject config = JSON.parseObject("" +
                "{\n" +
                "    \"rid\": \"TE3400003400000001@e6ab7e08912e48a58b7d3af7f7907aa5\",\n" +
                "    \"sid\": \"s_12100000000018032A_3939\",\n" +
                "    \"appkey\": \"fb0ee1e380fddb933cad318f8048a8ac\",\n" +
                "    \"orgName\": \"国家卫计委\",\n" +
                "    \"orgCode\": \"000000000\",\n" +
                "    \"username\": \"GJCSZM\",\n" +
                "    \"password\": \"888888\"\n" +
                "  }");
        LOGGER.info("殡葬服务火化信息,参数:{}", JSON.toJSONString(info));

        //通用请求头
        Map<String, Object> headerParamMap = getHeardMap(config);
        headerParamMap.put("gjgxjhpt_rid", config.getString("rid"));
        headerParamMap.put("gjgxjhpt_sid", config.getString("sid"));
        headerParamMap.put("gjgxjhpt_rtime", config.getString("rtime"));
        headerParamMap.put("gjgxjhpt_sign", headerParamMap.get("gjzwfwpt_sign"));
        LOGGER.info("殡葬服务火化信息,通用请求头:{}", JSON.toJSONString(headerParamMap));

        Map<String, Object> requestParamMap = getCommonParamMap(config, headerParamMap);
        List<BzfwhhxxCxywcsDTO> cxywcs = info.getCxywcs();
        requestParamMap.put("name", cxywcs.get(0).getName());
        requestParamMap.put("id_card", cxywcs.get(0).getId_card());
        requestParamMap.put("gjgxjhpt_rid", config.getString("rid"));
        requestParamMap.put("gjgxjhpt_sid", config.getString("sid"));
        requestParamMap.put("gjgxjhpt_rtime", config.getString("rtime"));
        requestParamMap.put("gjgxjhpt_sign", headerParamMap.get("gjzwfwpt_sign"));
        LOGGER.info("殡葬服务火化信息,请求参数:{}", JSON.toJSONString(requestParamMap));

        String response = "";

        try {
            response = httpClientUtils.sendFormRequest(restUrl, requestParamMap, headerParamMap);
        } catch (Exception e) {
            e.printStackTrace();
            response = testResponse("bzhhxx");
        }

        LOGGER.info("殡葬服务火化信息,返回:{}", response);

        JSONObject jsonObject = JSON.parseObject(response);

        if (!"1".equals(jsonObject.getString("code"))) {
            throw new AppException(jsonObject.getString("msg"));
        }
        JSONObject result = jsonObject.getJSONObject("biz_data");
        return result.getJSONArray("rows");
    }

    /**
     * 民政部_涉外婚姻登记信息查询
     *
     * @param param
     * @return
     */
    public JSONArray swhyxx(JSONObject param) {
        JSONObject config = JSON.parseObject("" +
                "{\n" +
                "    \"rid\": \"TE3400003400000001@e6ab7e08912e48a58b7d3af7f7907aa5\",\n" +
                "    \"sid\": \"s_111000000000131433_5630\",\n" +
                "    \"appkey\": \"840c9719b6cb9f9476c869cf2e98b8dd\",\n" +
                "    \"orgName\": \"国家卫计委\",\n" +
                "    \"orgCode\": \"000000000\",\n" +
                "    \"username\": \"GJCSZM\",\n" +
                "    \"password\": \"888888\"\n" +
                "  }");
        LOGGER.info("涉外婚姻登记信息查询,参数:{}", JSON.toJSONString(param));

        //通用请求头
        Map<String, Object> headerParamMap = getHeardMap(config);
        LOGGER.info("涉外婚姻登记信息查询,通用请求头:{}", JSON.toJSONString(headerParamMap));

        Map<String, Object> requestParamMap = getCommonParamMap(config, headerParamMap);
        JSONObject bizContent = new JSONObject();
        bizContent.put("name", param.getString("xm"));
        bizContent.put("cert_num", param.getString("zjh"));
        requestParamMap.put("biz_content", JSON.toJSONString(bizContent));
        LOGGER.info("涉外婚姻登记信息查询,请求参数:{}", JSON.toJSONString(requestParamMap));

        String response = "";

        try {
            response = httpClientUtils.sendFormRequest(restUrl, requestParamMap, headerParamMap);
        } catch (Exception e) {
            e.printStackTrace();
            response = testResponse("swhyxx");
        }

        LOGGER.info("涉外婚姻登记信息查询,返回:{}", response);

        JSONObject jsonObject = JSON.parseObject(response);

        if (!"1".equals(jsonObject.getString("code"))) {
            throw new AppException(jsonObject.getString("msg"));
        }
        JSONObject result = jsonObject.getJSONObject("biz_data");
        return result.getJSONArray("result");
    }


    /**
     * @param
     * @param url             请求地址
     * @param method          方法
     * @param requestParamMap 请求参数
     * @return java.lang.String
     * @author
     * @date 2022/8/9 8:54
     * @description 发送webservice请求
     **/
    private String sendWebServiceReq(String url, String method,
                                     Map<String, Object> requestParamMap,
                                     Map<String, Object> headerParamMap) {
        LOGGER.info("方法{}请求{}", method, JSON.toJSONString(requestParamMap));
        List<String> paramNameList = new ArrayList<>();
        List<Object> paramValList = new ArrayList<>();
        if (MapUtils.isNotEmpty(requestParamMap)) {
            for (Map.Entry<String, Object> str : requestParamMap.entrySet()) {
                paramNameList.add(str.getKey());
                paramValList.add(str.getValue());
            }
        }
        String[] paramNameArray = paramNameList.toArray(new String[0]);
        Object[] paramValArray = paramValList.toArray();
        //String invoke = testResponse(method);
        String invoke = "";
        try {
            org.apache.axis.client.Service service = new org.apache.axis.client.Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(url);//设置请求wsdl
            call.setOperationName(new QName("", method)); //设置请求命名空s间及具体方法
            // 设置头
            for (Map.Entry<String, Object> headEntry : headerParamMap.entrySet()) {
                QName qName = XMLType.XSD_STRING;
                SOAPHeaderElement element = new SOAPHeaderElement(qName);

                SOAPElement se = element.addChildElement(headEntry.getKey());
                se.addTextNode((String) headEntry.getValue());
                call.addHeader(element);
            }
            // 设置入参
            for (int i = 0; i < paramNameArray.length; i++) {
                QName qName = XMLType.XSD_STRING;
                if (i < paramValArray.length) {
                    if (paramValArray[i] instanceof Integer) {
                        qName = XMLType.XSD_INTEGER;
                    }
                }
                call.addParameter(paramNameArray[i], qName, ParameterMode.IN);
            }
            call.setUseSOAPAction(true);
            //设置使用soap操作
//            call.setTimeout(soTimeout); // 设置超时时间
            call.setReturnType(XMLType.XSD_STRING);// 设置返回类型
            invoke = (String) call.invoke(paramValArray);
        } catch (Exception e) {
            LOGGER.error(method + "发送webservice请求远程调用失败", e);
        }
        // 解密并编码
        String result = StringUtils.isNotBlank(invoke) ? new String(invoke.getBytes(StandardCharsets.UTF_8)) : null;
        LOGGER.info("方法{}请求返回{}", method, result);
        return result;
    }


    /**
     * 宣城-sdk查询个体工商户
     *
     * @param request
     * @return
     */
    public JSONObject SkdGtgshcx(JSONObject request) {
        LOGGER.info("宣城-sdk查询个体工商户 请求{} " ,JSON.toJSONString(request));
        //return JSON.parseObject(testResponse("SkdGtgshcx"));
        String epbno = request.getString("epbno");
        String name = request.getString("name");
        String traname = request.getString("traname");
        String authcode = request.getString("authcode");
        ShieldSyncAppGtgshjbxxcx op = new ShieldSyncAppGtgshjbxxcx();

        ApiResponse apiResponse = op.Gtgsjbxxcx(epbno, name, traname, authcode);
        try {
            LOGGER.info("宣城-sdk查询个体工商户 response code = " + apiResponse.getStatusCode());
            LOGGER.info("宣城-sdk查询个体工商户 response content = " + new String(apiResponse.getBody(), "UTF-8"));
            return JSON.parseObject(new String(apiResponse.getBody()));
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 宣城-sdk验证个体工商户
     *
     * @param request
     * @return
     */
    public JSONObject SkdGtgshyz(JSONObject request) {
        LOGGER.info("宣城-sdk验证个体工商户 请求{} " ,JSON.toJSONString(request));
        //return JSON.parseObject(testResponse("SkdGtgshyz"));
        String epbno = request.getString("epbno");
        String name = request.getString("name");
        String traname = request.getString("traname");
        String authcode = request.getString("authcode");
        ShieldSyncAppGtgshxxyz op = new ShieldSyncAppGtgshxxyz();

        ApiResponse apiResponse = op.Gtgsjbxxyz(epbno, name, traname, authcode);
        try {
            LOGGER.info("宣城-sdk验证个体工商户 response code = " + apiResponse.getStatusCode());
            LOGGER.info("宣城-sdk验证个体工商户 response content = " + new String(apiResponse.getBody(), "UTF-8"));
            return JSON.parseObject(new String(apiResponse.getBody()));
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 宣城-sdk查询企业基本信息
     *
     * @param request
     * @return
     */
    public JSONObject SkdQyxxcx(JSONObject request) {
        LOGGER.info("宣城-sdk查询企业基本信息 请求{} " ,JSON.toJSONString(request));
        //return JSON.parseObject(testResponse("SkdQyxxcx"));
        String entname = request.getString("entname");
        String uniscid = request.getString("uniscid");
        String authcode = request.getString("authcode");
        ShieldSyncAppQyxxcx op = new ShieldSyncAppQyxxcx();

        ApiResponse apiResponse = op.Qyxxcx(entname, uniscid, authcode);
        try {
            LOGGER.info("宣城-sdk查询企业基本信息 response code = " + apiResponse.getStatusCode());
            LOGGER.info("宣城-sdk查询企业基本信息 response content = " + new String(apiResponse.getBody(), "UTF-8"));
            return JSON.parseObject(new String(apiResponse.getBody()));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 宣城-sdk验证企业基本信息
     *
     * @param request
     * @return
     */
    public JSONObject SkdQyxxyz(JSONObject request) {
        LOGGER.info("宣城-sdk验证企业基本信息 请求{} " ,JSON.toJSONString(request));
        //return JSON.parseObject(testResponse("SkdQyxxyz"));
        String entname = request.getString("entname");
        String uniscid = request.getString("uniscid");
        String authcode = request.getString("authcode");
        ShieldSyncAppQyxxyz op = new ShieldSyncAppQyxxyz();

        ApiResponse apiResponse = op.Qyxxyz(entname, uniscid, authcode);
        try {
            LOGGER.info("宣城-sdk验证企业基本信息 response code = " + apiResponse.getStatusCode());
            LOGGER.info("宣城-sdk验证企业基本信息 response content = " + new String(apiResponse.getBody(), "UTF-8"));
            return JSON.parseObject(new String(apiResponse.getBody()));
        } catch (Exception e) {
            return null;
        }
    }


    private String testResponse(String method) {
        if (method.equals("SkdGtgshyz")) {
            return "{\n" +
                    "    \"ent_idx\":{\n" +
                    "        \"name\":\"xxxxxx公司\",\n" +
                    "        \"epbno\":\"Gxxxxxxo\"\n" +
                    "    },\n" +
                    "    \"entchk_checkrcs_msg\":\"吊销.未注销\",\n" +
                    "    \"diff_list\":[\n" +
                    "        {\n" +
                    "            \"chk_key\":\"regno\",\n" +
                    "            \"in_val\":\"xxxxxx\",\n" +
                    "            \"out_val\":\"652xxxxxx\"\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";
        }

        if (method.equals("SkdGtgshcx")) {
            return "{\n" +
                    "    \"code\":\"2000\",\n" +
                    "    \"msg\":\"查询成功\",\n" +
                    "    \"data\":[\n" +
                    "        {\n" +
                    "            \"uniscid\":\"92xxxxxxxxxxxxxxxl\",\n" +
                    "            \"regno\":\"n0121xxxxxxxxl\",\n" +
                    "            \"traname\":\"超好吃蛋糕店\",\n" +
                    "            \"enttype\":\"个体工商户\",\n" +
                    "            \"oploc\":\"西城区XX街道XX号\",\n" +
                    "            \"name\":\"李四\",\n" +
                    "            \"compformcn\":\"个人经营\",\n" +
                    "            \"estdate\":938620800000,\n" +
                    "            \"opscope\":\"蛋糕等\",\n" +
                    "            \"regorgcn\":\"xx 登记机关\",\n" +
                    "            \"apprdate\":938620800000,\n" +
                    "            \"regstatecn\":\"开业\",\n" +
                    "            \"updatetime\":9368000\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";
        }

        if (method.equals("SkdQyxxcx")) {
            return "{\n" +
                    "    \"code\":\"2000\",\n" +
                    "    \"msg\":\"查询成功\",\n" +
                    "    \"data\":[\n" +
                    "        {\n" +
                    "            \"uniscid\":\"92xxxxxxxxxxxxxxxl\",\n" +
                    "            \"regno\":\"110121xxxxxxxxl\",\n" +
                    "            \"entname\":\"超好吃蛋糕店\",\n" +
                    "            \"enttypeCn\":\"股份有限公司\",\n" +
                    "            \"dom\":\"西城区XX街道XX号\",\n" +
                    "            \"name\":\"李四\",\n" +
                    "            \"opfrom\":20081011000000,\n" +
                    "            \"estdate\":938620800000,\n" +
                    "            \"opscope\":\"蛋糕等\",\n" +
                    "            \"opto\":20081011000000,\n" +
                    "            \"regcap\":200,\n" +
                    "            \"regcapcurCn\":\"null\",\n" +
                    "            \"regorgcn\":\"xx登记机关\",\n" +
                    "            \"enttypCn\":\"股份有限公司\",\n" +
                    "            \"apprdate\":938620800000,\n" +
                    "            \"regstateCn\":\"正常\"\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";
        }

        if (method.equals("SkdQyxxyz")) {
            return "{\n" +
                    "    \"ent_idx\":{\n" +
                    "        \"entname\":\"xxxxxx公司\",\n" +
                    "        \"umscid\":\"Gxxxxxxo\"\n" +
                    "    },\n" +
                    "    \"regstateCn\":\"吊销.未注销\",\n" +
                    "    \"entchk_checkrcs_msg\":\"吊销.未注销\",\n" +
                    "    \"diff_list\":[\n" +
                    "        {\n" +
                    "            \"chk_key\":\"regno\",\n" +
                    "            \"in_val\":\"xxxxxx\",\n" +
                    "            \"out_val\":\"652xxxxxx\"\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";
        }
//       if(method.equals("getBirthInfo")){
//           return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
//                   "<MDEML templateVersion=\"1.0\">\n" +
//                   "    <resultinfo >\n" +
//                   "        <success>0</success>\n" +
//                   "        <message>成功</message>\n" +
//                   "    </resultinfo>\n" +
//                   "    <body>\n" +
//                   "        <data>\n" +
//                   "            <babyName>张三1</babyName>\n" +
//                   "            <babySexCode>张三2</babySexCode>\n" +
//                   "            <babySex>张三3</babySex>\n" +
//                   "            <birthArea>张三4</birthArea>\n" +
//                   "            <birthTime>张三5</birthTime>\n" +
//                   "            <birthCode>张三6</birthCode>\n" +
//                   "            <momName>张三7</momName>\n" +
//                   "            <momIdCode>张三8</momIdCode>\n" +
//                   "            <dadName>张三9</dadName>\n" +
//                   "            <dadIdCode>张三10</dadIdCode>  \n" +
//                   "        </data>\n" +
//                   "        <data>\n" +
//                   "            <babyName>1张三1</babyName>\n" +
//                   "            <babySexCode>2张三2</babySexCode>\n" +
//                   "            <babySex>3张三3</babySex>\n" +
//                   "            <birthArea>4张三4</birthArea>\n" +
//                   "            <birthTime>5张三5</birthTime>\n" +
//                   "            <birthCode>6张三6</birthCode>\n" +
//                   "            <momName>7张三7</momName>\n" +
//                   "            <momIdCode>8张三8</momIdCode>\n" +
//                   "            <dadName>9张三9</dadName>\n" +
//                   "            <dadIdCode>10张三10</dadIdCode>  \n" +
//                   "        </data>\n" +
//                   "    </body>\n" +
//                   "</MDEML>";
//       }
//       else if(method.equals("getSwyxzmInfo")){
//            return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
//                    "<MDEML templateVersion=\"1.0\">\n" +
//                    "    <resultinfo>\n" +
//                    "        <success>0</success>\n" +
//                    "        <message>成功</message>\n" +
//                    "    </resultinfo>\n" +
//                    "    <body>\n" +
//                    "        <data>\n" +
//                    "            <GMSFHM>*******</GMSFHM>\n" +
//                    "            <XM>某某</XM>\n" +
//                    "            <XBDM>1</XBDM>\n" +
//                    "            <MZDM>01</MZDM>\n" +
//                    "            <SWYY>支气管或肺，未特指</SWYY>\n" +
//                    "        </data>\n" +
//                    "        <data>\n" +
//                    "            <GMSFHM>*******</GMSFHM>\n" +
//                    "            <XM>某某</XM>\n" +
//                    "            <XBDM>1</XBDM>\n" +
//                    "            <MZDM>01</MZDM>\n" +
//                    "            <SWYY>扩张性心肌病</SWYY>\n" +
//                    "        </data >\n" +
//                    "    </body>\n" +
//                    "</MDEML>";
//       }
//       else if(method.equals("grhyhy")){
//           return "{\n" +
//                   "    \"msg\": \"成功\",\n" +
//                   "    \"code\": \"1\",\n" +
//                   "    \"sign\": \"xxx\",\n" +
//                   "    \"biz_data\": {\n" +
//                   "        \"result\": [\n" +
//                   "            {\n" +
//                   "              \"op_type\":\"xxx\",\n" +
//                   "              \"op_type_desc\":\"匹配不成功\",\n" +
//                   "\t       \"op_date\":\"xxx\"\n" +
//                   "            }\n" +
//                   "        ],\n" +
//                   "        \"code\": \"0\",\n" +
//                   "        \"status\": \"success\"\n" +
//                   "    }\n" +
//                   " }";
//       }
//       else if(method.equals("sfhyhy")){
//           return "{\n" +
//                   "    \"msg\": \"成功\",\n" +
//                   "    \"code\": \"1\",\n" +
//                   "    \"sign\": \"xxx\",\n" +
//                   "    \"biz_data\": {\n" +
//                   "        \"result\": [\n" +
//                   "            {\n" +
//                   "              \"op_type\":\"xxx\",\n" +
//                   "              \"op_type_desc\":\"xxx\",\n" +
//                   "\t       \"op_date\":\"xxx\"\n" +
//                   "            }\n" +
//                   "        ],\n" +
//                   "        \"code\": \"0\",\n" +
//                   "        \"status\": \"success\"\n" +
//                   "    }\n" +
//                   " }";
//       }
//       else if(method.equals("shttfrzs")){
//           return "{\n" +
//                   "    \"msg\":\"成功\",\n" +
//                   "    \"code\":\"1\",\n" +
//                   "    \"sign\":\"xxx\",\n" +
//                   "    \"biz_data\":{\n" +
//                   "        \"returncount\":1,\n" +
//                   "        \"recordcount\":1,\n" +
//                   "        \"rows\":[\n" +
//                   "            {\n" +
//                   "                \"business_scope\":\"xxx\",\n" +
//                   "                \"issue_certificate_dept\":\"xxx\",\n" +
//                   "                \"registration_date\":\"xxx\",\n" +
//                   "                \"valid_to\":\"xxx\",\n" +
//                   "                \"usc_code\":\"xxx\",\n" +
//                   "                \"valid_from\":\"xxx\",\n" +
//                   "                \"legal_name\":\"xxx\",\n" +
//                   "                \"domicile_addres\":\"xxx\",\n" +
//                   "                \"org_name\":\"xxx\",\n" +
//                   "                \"registered_capital\":\"registered_capital\",\n" +
//                   "                \"ifcharity\":\"\",\n" +
//                   "                \"manager_dept\":\"xxx\"\n" +
//                   "            }\n" +
//                   "        ]\n" +
//                   "    }\n" +
//                   "}";
//       }
//       else if(method.equals("sydjxx")){
//           return "{\n" +
//                   "    \"msg\":\"成功\",\n" +
//                   "    \"code\":\"1\",\n" +
//                   "    \"sign\":\"xxx\",\n" +
//                   "    \"biz_data\":{\n" +
//                   "        \"result\":[\n" +
//                   "            {\n" +
//                   "                \"dept_code\":\"xxx\",\n" +
//                   "                \"adopt_cert_type_man_cn\":\"xxx\",\n" +
//                   "                \"adopt_cert_num_man\":\"xxx\",\n" +
//                   "                \"adopt_cert_type_woman\":\"xxx\",\n" +
//                   "                \"baby_nation_cn\":\"xxx\",\n" +
//                   "                \"op_type_cn\":\"xxx\",\n" +
//                   "                \"op_date\":\"xxx\",\n" +
//                   "                \"adopt_birth_woman\":\"xxx\",\n" +
//                   "                \"baby_cert_num\":\"xxx\",\n" +
//                   "                \"baby_birth\":\"xxx\",\n" +
//                   "                \"adopt_birth_man\":\"xxx\",\n" +
//                   "                \"baby_sex_cn\":\"xxx\",\n" +
//                   "                \"adopt_name_woman\":\"xxx\",\n" +
//                   "                \"adopt_cert_type_woman_cn\":\"xxx\",\n" +
//                   "                \"adopt_nation_man_cn\":\"xxx\",\n" +
//                   "                \"baby_name\":\"xxx\",\n" +
//                   "                \"adopt_nation_woman_cn\":\"xxx\",\n" +
//                   "                \"baby_nation\":\"xxx\",\n" +
//                   "                \"adopt_name_man\":\"xxx\",\n" +
//                   "                \"dept_code_cn\":\"xxx\",\n" +
//                   "                \"adopt_cert_type_man\":\"xxx\",\n" +
//                   "                \"adopt_nation_woman\":\"xxx\",\n" +
//                   "                \"op_type\":\"xxx\",\n" +
//                   "                \"adopt_nation_man\":\"xxx\",\n" +
//                   "                \"adopt_cert_num_woman\":\"xxx\",\n" +
//                   "                \"baby_sex\":\"xxx\"\n" +
//                   "            }\n" +
//                   "        ],\n" +
//                   "        \"code\":\"0\",\n" +
//                   "        \"status\":\"success\"\n" +
//                   "    }\n" +
//                   "}";
//       }
//       else if(method.equals("bzhhxx")){
//           return "{\n" +
//                   "    \"msg\":\"成功\",\n" +
//                   "    \"code\":\"1\",\n" +
//                   "    \"sign\":\"xxx\",\n" +
//                   "    \"biz_data\":{\n" +
//                   "        \"returncount\":1,\n" +
//                   "        \"recordcount\":1,\n" +
//                   "        \"rows\":[\n" +
//                   "            {\n" +
//                   "                \"DEATH_TIME\":\"xxx\",\n" +
//                   "                \"SEX\":\"xxx\",\n" +
//                   "                \"ADDRESS\":\"xxx\",\n" +
//                   "                \"CREMATION_TIME\":\"xxx\",\n" +
//                   "                \"ID_CARD\":\"xxx\",\n" +
//                   "                \"NAME\":\"xxx\"\n" +
//                   "            }\n" +
//                   "        ]\n" +
//                   "    }\n" +
//                   "}";
//       }
//       else if(method.equals("swhyxx")){
//           return "{\n" +
//                   "    \"msg\":\"成功\",\n" +
//                   "    \"code\":\"1\",\n" +
//                   "    \"sign\":\"xxx\",\n" +
//                   "    \"biz_data\":{\n" +
//                   "        \"result\":[\n" +
//                   "            {\n" +
//                   "                \"op_date\":\"xxx\",\n" +
//                   "                \"name_woman\":\"xxx\",\n" +
//                   "                \"cert_num_man\":\"xxx\",\n" +
//                   "                \"cert_num_woman\":\"xxx\",\n" +
//                   "                \"id_type_woman\":\"2\",\n" +
//                   "                \"op_type\":\"INR\",\n" +
//                   "                \"name_man\":\"xxx\",\n" +
//                   "                \"id_type_man\":\"1\"\n" +
//                   "            }\n" +
//                   "        ],\n" +
//                   "        \"code\":\"0\",\n" +
//                   "        \"status\":\"success\"\n" +
//                   "    }\n" +
//                   "}";
//       }
        return "";
    }

    private JSONArray testResponse() {
        return JSONArray.parseArray("[\n" +
                "    {\n" +
                "        \"GMSFHM\":\"GMSFHM\",\n" +
                "        \"XM\":\"XM\",\n" +
                "        \"XP\":\"/9j/4AAQSkZJRgABAQEBXgFeAAD/2wBDAAQDAwQDAwQEAwQFBAQFBgoHBgYGBg0JCggKDw0QEA8N\\nDw4RExgUERIXEg4PFRwVFxkZGxsbEBQdHx0aHxgaGxr/2wBDAQQFBQYFBgwHBwwaEQ8RGhoaGhoa\\nGhoaGhoaGhoaGhoaGhoaGhoaGhoaGhoaGhoaGhoaGhoaGhoaGhoaGhoaGhr/wAARCAG5AWYDASIA\\nAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQA\\nAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3\\nODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWm\\np6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEA\\nAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSEx\\nBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElK\\nU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3\\nuLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD7+ooo\\noAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAopksqQIXmdUUd2OK5rUfiF4e0kn+0r9\\nbYA7SXU4z9aN9BNpK7OoorzDU/j94H05io1m3uSP+eMgbH1HBFYtl+1D4CnmEd1fNbAnAcqWX8eB\\nV+zm+hHtYdz2miuQ0b4o+EvECh9H1y0ukxklX+79QeRXS2upWl8zLZ3MU5XqEcGpaa3LUk9mWqKT\\nB3Z3HGMY/rSj3pDCiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKK\\nKKACiiigAooooAKKKKACiiigAooooAKKbJIkMbSSusaKMszHAA9zXzT8bf2n9H8NwT6R4VuhdXxy\\n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\",\n" +
                "        \"XB\":\"XB\",\n" +
                "        \"MZ\":\"MZ\",\n" +
                "        \"CSRQ\":\"CSRQ\",\n" +
                "        \"CSDGJDQ\":\"CSDGJDQ\",\n" +
                "        \"CSDSSXQ\":\"CSDSSXQ\",\n" +
                "        \"RYZT\":\"RYZT\",\n" +
                "        \"HB\":\"HB\",\n" +
                "        \"HZXM\":\"HZXM\",\n" +
                "        \"HJQFSJ\":\"HJQFSJ\",\n" +
                "        \"HJDZ\":\"HJDZ\",\n" +
                "        \"YHZGX\":\"YHZGX\",\n" +
                "        \"HJQFJG\":\"HJQFJG\"\n" +
                "    }\n" +
                "]");
    }
}
