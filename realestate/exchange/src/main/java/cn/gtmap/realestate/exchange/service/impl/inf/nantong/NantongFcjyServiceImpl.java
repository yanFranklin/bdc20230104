package cn.gtmap.realestate.exchange.service.impl.inf.nantong;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.accept.FcjyClfHtxxDTO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.util.HttpClientUtils;
import cn.gtmap.realestate.exchange.util.Md5Util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/5/11
 * @description 房产交易服务
 */
@Service
public class NantongFcjyServiceImpl {

    @Autowired
    HttpClientUtils httpClientUtils;

    /**
     * 日志处理
     */
    private static final Logger logger = LoggerFactory.getLogger(NantongFcjyServiceImpl.class);

    private static final String QLRMC = "qlrmc";
    private static final String QLRZJH = "qlrzjh";
    private static final String HTBH = "htbh";

    /**
     * 如东交易唯一凭证
     */
    @Value("${rdjy.appKey:}")
    private String appKey;

    /**
     * 如东交易唯一凭证密钥
     */
    @Value("${rdjy.appSecret:}")
    private String appSecret;


    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    @Value("${rd.clfhtxx.url:}")
    private String clfhtxxUrl;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 交易签名md5编码格式
     */
    @Value("${rdjy.sign.encode:UTF-8}")
    private String jyEncode;

    @Resource(name = "exchangeDozerMapper")
    private DozerBeanMapper dozerBeanMapper;

    /**
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取如东交易接口sign
     */
    private String getRdjySign(Map<String, String> params) {
        String strSginTemp = String.format("%s%s%s", appSecret, combineOrderParam(params).toLowerCase(), appSecret);
        return Md5Util.getMd5To32(strSginTemp, jyEncode).toUpperCase();

    }

    //根据key排序并且拼接参数名与参数值
    private String combineOrderParam(Map<String, String> params){
        TreeSet<String> keySet = new TreeSet<String>();
        for (String objectKey : params.keySet()) {
            keySet.add(objectKey);
        }

        Iterator<String> keyIterator = keySet.iterator();
        String keyTmp = "";
        StringBuilder checkStrBuilder = new StringBuilder();
        while (keyIterator.hasNext()) {
            keyTmp = keyIterator.next();
            String valTmp = params.get(keyTmp);
            if (StringUtils.isEmpty(valTmp)) {
                continue;
            }
            checkStrBuilder.append(keyTmp).append(params.get(keyTmp));
        }

        return checkStrBuilder.toString();
    }

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 获取存量房请求参数
      */
    public Map<String,String> getClfHtxxParam(Object param) {
        return  getFcjyParam(param,"clf");

    }

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 获取商品房请求参数
      */
    public Map<String,String> getSpfHtxxParam(Object param) {
        return  getFcjyParam(param,"spf");

    }

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 获取交易参数
      */
    private Map<String, String> getFcjyParam(Object param,String fwfclx)  {
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(param));
        Map<String, String> paramMap = new HashMap<>();
        if(jsonObject != null) {
            paramMap.put("appkey", appKey);
            paramMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
            paramMap.put("format", "json");
            if(StringUtils.isNotBlank(jsonObject.getString(QLRMC)) ||StringUtils.isNotBlank(jsonObject.getString(QLRZJH)) ||StringUtils.isNotBlank(jsonObject.getString(HTBH))) {
                if(StringUtils.isNotBlank(jsonObject.getString(QLRMC))) {
                    paramMap.put(QLRMC, jsonObject.getString(QLRMC));
                }
                if(StringUtils.isNotBlank(jsonObject.getString(QLRZJH))) {
                    paramMap.put(QLRZJH, jsonObject.getString(QLRZJH));
                }
                if(StringUtils.isNotBlank(jsonObject.getString(HTBH))) {
                    paramMap.put(HTBH, jsonObject.getString(HTBH));
                }
            }
            if(StringUtils.equals("spf",fwfclx)){
                paramMap.put("serviceid", "v_spfba");
            }else if(StringUtils.equals("clf",fwfclx)){
                paramMap.put("serviceid", "v_espba");
            }
            paramMap.put("sign", getRdjySign(paramMap));
        }
        return paramMap;

    }

    //交易信息异常时,result 数据为空数据对象,需要转换成JSONArray对象,保持统一
    public JSONObject dealResult(JSONObject jsonObject) {
        if(jsonObject != null){
            if(jsonObject.getObject("res_result",Object.class) instanceof JSONObject) {
                JSONArray jsonArray =new JSONArray();
                jsonArray.add(jsonObject.getObject("res_result",JSONObject.class));
                jsonObject.put("res_result",jsonArray);
            }
        }
        return jsonObject;

    }

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  根据字符分割数据转换成list
      */
    public static List<BdcSlSqrDO> splitQlrsByZf(Object object) {
        List<BdcSlSqrDO> bdcSlSqrDOList =new ArrayList<>();
        if (object != null) {
            try {
                JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
                logger.error("交易信息转换，jsonObject：{}",JSONObject.toJSONString(jsonObject));
                if (jsonObject != null) {
                    if (StringUtils.isNotBlank(jsonObject.getString("qlrmc"))) {
                        String[] qlrList = StringUtils.split(jsonObject.getString("qlrmc"), CommonConstantUtils.ZF_YW_FH);
                        String[] qlrzjhs = StringUtils.split(jsonObject.getString("qlrzjh"), CommonConstantUtils.ZF_YW_FH);
                        for (int i = 0; i < qlrList.length; i++) {
                            BdcSlSqrDO sqrDO = new BdcSlSqrDO();
                            sqrDO.setSqrlb(CommonConstantUtils.QLRLB_QLR);
                            sqrDO.setSqrmc(qlrList[i]);
                            sqrDO.setZjh(getIndexStrOrFristStr(qlrzjhs, i));
                            bdcSlSqrDOList.add(sqrDO);
                        }
                    }
                    if (StringUtils.isNotBlank(jsonObject.getString("ywrmc"))) {
                        String[] ywrList = StringUtils.split(jsonObject.getString("ywrmc"), CommonConstantUtils.ZF_YW_FH);
                        String[] ywrzjhs = StringUtils.split(jsonObject.getString("ywrzjh"), CommonConstantUtils.ZF_YW_FH);
                        for (int i = 0; i < ywrList.length; i++) {
                            BdcSlSqrDO ywrDO = new BdcSlSqrDO();
                            ywrDO.setSqrlb(CommonConstantUtils.QLRLB_YWR);
                            ywrDO.setSqrmc(ywrList[i]);
                            ywrDO.setZjh(getIndexStrOrFristStr(ywrzjhs, i));
                            bdcSlSqrDOList.add(ywrDO);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("交易权利人转换出错", e);
            }

        }
        return bdcSlSqrDOList;

    }

    private static String getIndexStrOrFristStr(String[] strArray, int i) {
        String str = "";
        if (strArray != null && i < strArray.length) {
            str = strArray[i];
        } else if (ArrayUtils.isNotEmpty(strArray)) {
            str = strArray[0];
        }
        return str;
    }

    public List<FcjyClfHtxxDTO> zjxxZh(JSONObject jsonObject) {
        logger.info(jsonObject.toJSONString());
        List<FcjyClfHtxxDTO> fcjyClfHtxxDTOList = new ArrayList<>();
        if (null != jsonObject && null != jsonObject.get("mmqy")) {
            JSONArray array = JSONObject.parseArray(jsonObject.getString("mmqy"));
            for (int i = 0; i < array.size(); i++) {
                JSONObject object = array.getJSONObject(i);
                FcjyClfHtxxDTO fcjyClfHtxxDTO = new FcjyClfHtxxDTO();
                dozerBeanMapper.map(object, fcjyClfHtxxDTO, "zjxxmmqy");
                fcjyClfHtxxDTOList.add(fcjyClfHtxxDTO);
            }
        }
        return fcjyClfHtxxDTOList;
    }


}
