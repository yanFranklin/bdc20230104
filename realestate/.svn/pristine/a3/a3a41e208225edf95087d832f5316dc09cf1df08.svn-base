package cn.gtmap.realestate.exchange.service.impl.inf.ahst;

import cn.gtmap.realestate.common.core.enums.BdcZdEnum;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcBhFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.exchange.core.dto.ahst.request.AhstSfhcRequestCondition;
import cn.gtmap.realestate.exchange.core.dto.ahst.request.AhstSfhcRequestRequestParam;
import cn.gtmap.realestate.exchange.core.dto.bjjk.sfpjcxfk.response.SfpjcxfkResponseResource;
import cn.gtmap.realestate.exchange.util.DateUtil;
import cn.gtmap.realestate.exchange.util.XmlEntityConvertUtil;
import cn.gtmap.realestate.exchange.util.rsa.HfBjjkRSAUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2020/11/23 18:33
 * @description
 */
@Service("ahstServiceImpl")
public class AhstServiceImpl {
    private static Logger LOGGER = LoggerFactory.getLogger(AhstServiceImpl.class);
    private static final String BHSERVICE_YWLX = "ahstcxqqdh";
    private static final String BHSERVICE_SFHC_YWLX = "ahstsfhc";

    // 用于加密的公钥 默认值
    private static final String TOKEN = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDQdg7PXA7iBtqJvGghcKa3Xki2Ya0tcXqpHaAV8JJ6NhO8JawVOGhhr2xOvhLS2WUNWP4JnZ1ILfRxINrm5_ivebgds7VahUyrTrJNgzB3J9NPTP4Gb_A11L8nyP_FRjCd9eXiHDysSTk91KePSb792EHkXFD1tz8QDneWSnoZQwIDAQAB-6fKxkVD0gkrMaFV5XDfbrhz4emgo8fmwWSnZlUyrtvPpZ6wg6MVlC98lIIH-6t1gyBKQlnlF8jIUEqvIgnvrP9dxWqy12elbsLAKHjGrckz6Ul9lK6CXNs7hMm2QbxZsdBWLJloxC_3aQIDAQAB";

    @Autowired
    private BdcBhFeignService bdcBhFeignService;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    // 自增时间范围
    private static final String BHSERVICE_ZZSJFW = "DAY";

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params []
     * @return java.lang.String
     * @description 身份核查服务消息流水号
     */
    public String getSfhcMessageSeq() {
        // 消息流水号 14 位时间串+4 位自定义索引码，0001 开始，步长为 1 顺序累加
        String dateStr = DateUtil.formateTimeYmdhms(new Date());
        String xlhStr = "";
        Integer xlh = bdcBhFeignService.queryLsh(BHSERVICE_SFHC_YWLX, BHSERVICE_ZZSJFW);
        if (xlh != null) {
            // 0补齐四位流水号
            xlhStr = String.format("%04d", xlh);
        }
        return dateStr + xlhStr;
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [paramMap]
     * @return cn.gtmap.realestate.exchange.core.dto.ahst.request.AhstSfhcRequestRequestParam
     * @description 身份核查服务参数解析
     */
    public AhstSfhcRequestRequestParam getRequestParam(JSONObject requestObject) {
        if(requestObject == null){
            return null;
        }

        List requestList  = requestObject.getJSONArray("param");
        if(CollectionUtils.isEmpty(requestList)){
            return null;
        }

        List<AhstSfhcRequestCondition> Conditions = new ArrayList();
        for(Object param : requestList){
            Map<String,String> paramMap = (Map<String, String>) param;
            Map checkCondition = new HashMap();
            checkCondition.put("xm", paramMap.get("xm"));

            AhstSfhcRequestCondition condition = new AhstSfhcRequestCondition();
            condition.setCheckCondition(checkCondition);
            condition.setQueryCondition("gmsfhm='" + paramMap.get("zjh") + "'");
            Conditions.add(condition);
        }

        AhstSfhcRequestRequestParam requestParam = new AhstSfhcRequestRequestParam();
        requestParam.setConditions(Conditions);
        return requestParam;
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [json]
     * @return java.lang.String
     * @description 参数加密
     */
    public static String encode(String json) {
        String publicKey = EnvironmentConfig.getEnvironment().getProperty("ahst.token", TOKEN);
        try {
            LOGGER.info("加密前数据：" + json);
            return HfBjjkRSAUtils.encodeByPublic(json, publicKey);
        } catch (Exception e) {
            LOGGER.error("安徽省厅接口参数加密失败", e);
        }
        return "";
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params []
     * @return java.lang.String
     * @description 生成查询请求单号
     */
    public String getCxqqdh() {
        // 本次调用的流水号，例如（20190823110102000001）
        // 八位日期+六位行政区代码+六位流水号；共20位数;
        String dateStr = DateUtil.formateTimeYmd(new Date());
        String xzqh = EnvironmentConfig.getEnvironment().getProperty("ahst.xzqh");
        String xlhStr = "";
        Integer xlh = bdcBhFeignService.queryLsh(BHSERVICE_YWLX, BHSERVICE_ZZSJFW);
        if (xlh != null) {
            // 0补齐六位流水号
            xlhStr = String.format("%06d", xlh);
        }
        return dateStr + xzqh + xlhStr;
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [responseObject]
     * @return java.lang.String
     * @description 参数解密Object
     */
    public static Object decode(JSONObject responseObject) {
        String publicKey = EnvironmentConfig.getEnvironment().getProperty("ahst.token", TOKEN);
        JSONObject content = responseObject.getJSONObject("data");
        if(content == null){
            return null;
        }
        String encryptData = content.getString("data");
        if(StringUtils.isBlank(encryptData)){
            return null;
        }
        String data = null;
        try {
            data = HfBjjkRSAUtils.decodeByPublic(encryptData, publicKey);
            // 身份核查格式
//            String data = "{\n" +
//                    "    \"ResourceInfos\": [\n" +
//                    "        {\n" +
//                    "            \"DataItems\": [],\n" +
//                    "            \"ResourceName\": \"\",\n" +
//                    "            \"ReturnInfos\": [\n" +
//                    "                {\n" +
//                    "                    \"DataInfo\": [],\n" +
//                    "                    \"CheckSourceInfos\": {\n" +
//                    "                        \"xm\": \"王雅青\"\n" +
//                    "                    },\n" +
//                    "                    \"CheckCode\": \"000\",\n" +
//                    "                    \"CheckCodeDesc\": \"核查一致\"\n" +
//                    "                }\n" +
//                    "            ]\n" +
//                    "        }\n" +
//                    "    ]\n" +
//                    "}";
            //地名信息 婚姻信息 社会组织
//            String data = "{\n" +
//                    "    \"count\": 1,\n" +
//                    "    \"rows\": [\n" +
//                    "        {\n" +
//                    "            \"place_type\": \"河流\",\n" +
//                    "            \"place_meaning\": \"”黑“，汉语，意为黑色，“河”指天然的或人工的大水道。\",\n" +
//                    "            \"place_origin\": \"因该河河水主要来自喀拉昆仑山冰川融化，所以将其命名为黑河。\",\n" +
//                    "            \"standard_name\": \"黑河\",\n" +
//                    "            \"place_code\": \"65322121012100900048\",\n" +
//                    "            \"roman_alphabet_spelling\": \"Hēi Hé\",\n" +
//                    "            \"place_history\": \"1983年第一次地名普查使用此地名，沿用至今。\"\n" +
//                    "        }\n" +
//                    "    ]\n" +
//                    "}";
            //金融许可证
//            String data = "{\n" +
//                    "    \"count\": 1,\n" +
//                    "    \"rows\": [\n" +
//                    "        {\n" +
//                    "            \"place_type\": \"河流\",\n" +
//                    "            \"place_meaning\": \"”黑“，汉语，意为黑色，“河”指天然的或人工的大水道。\",\n" +
//                    "            \"place_origin\": \"因该河河水主要来自喀拉昆仑山冰川融化，所以将其命名为黑河。\",\n" +
//                    "            \"standard_name\": \"黑河\",\n" +
//                    "            \"place_code\": \"65322121012100900048\",\n" +
//                    "            \"roman_alphabet_spelling\": \"Hēi Hé\",\n" +
//                    "            \"place_history\": \"1983年第一次地名普查使用此地名，沿用至今。\"\n" +
//                    "        }\n" +
//                    "    ]\n" +
//                    "}";
            //事业单位
            //data = "{\"count\":0,\"rows\":[]}";
            LOGGER.info("解码后的数据:{}", data);
            if(StringUtils.isNotBlank(data)){
                if(data.startsWith("{")){
                    return JSONObject.parseObject(data);
                }else if(data.startsWith("[")){
                    return JSONObject.parseArray(data);
                }
            }
        } catch (Exception e) {
            LOGGER.error("部级接口参数解密失败", e);
        }
        return null;
    }

    public Object dealSfhcResponse(JSONObject response){
        if(response == null){
            return null;
        }
        JSONArray ResourceInfos = response.getJSONArray("ResourceInfos");
        if(CollectionUtils.isNotEmpty(ResourceInfos)){
            JSONObject content = (JSONObject) ResourceInfos.get(0);
            if(content != null){
                JSONObject data = new JSONObject();
                data.put("data", content.getJSONArray("ReturnInfos"));
                return data;
            }
        }
        return null;
    }
}
