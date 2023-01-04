package cn.gtmap.realestate.exchange.service.impl.inf.bjjk;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcBhFeignService;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.exchange.core.dto.bjjk.hydjxx.response.BjjkHydjxxResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.bjjk.hydjxx.response.BjjkHydjxxResponseRows;
import cn.gtmap.realestate.exchange.core.dto.bjjk.request.BjjkRequestHead;
import cn.gtmap.realestate.exchange.core.dto.bjjk.sfpjcx.request.SfpjcxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.bjjk.sfpjcx.request.SfpjcxRequestData;
import cn.gtmap.realestate.exchange.core.dto.bjjk.sfpjcxfk.response.SfpjcxfkResponseResource;
import cn.gtmap.realestate.exchange.util.DateUtil;
import cn.gtmap.realestate.exchange.util.XmlEntityConvertUtil;
import cn.gtmap.realestate.exchange.util.rsa.HfBjjkRSAUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-01
 * @description 合肥部级接口相关服务
 */
@Service
public class BjjkServiceImpl {

    @Autowired
    private UserManagerUtils userManagerUtils;

    private static Logger LOGGER = LoggerFactory.getLogger(BjjkServiceImpl.class);

    // 编号 类型
    private static final String BHSERVICE_YWLX = "bjjkcxqqdh";
    // 编号 类型
    private static final String BHSERVICE_GASFHC_YWLX = "bjjkgasfhc";

    // 自增时间范围
    private static final String BHSERVICE_ZZSJFW = "DAY";

    @Autowired
    private BdcBhFeignService bdcBhFeignService;

    // 用于加密的公钥 默认值
    private static final String TOKEN = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDBmeVa_KHCbYL1fRczTI3eExX5uVv0-6fKxkVD0gkrMaFV5XDfbrhz4emgo8fmwWSnZlUyrtvPpZ6wg6MVlC98lIIH-6t1gyBKQlnlF8jIUEqvIgnvrP9dxWqy12elbsLAKHjGrckz6Ul9lK6CXNs7hMm2QbxZsdBWLJloxC_3aQIDAQAB";

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @return java.lang.String
     * @description 获取查询请求编号
     */
    public String getCxqqdh(JSONObject jsonObject){
        UserDto user = userManagerUtils.getCurrentUser();
        String regionCode = "";
        if(user != null){
            regionCode = userManagerUtils.getRegionCodeByUserName(user.getUsername());
        }
        if(StringUtils.isBlank(regionCode) && jsonObject != null && StringUtils.isNotBlank(jsonObject.getString("regionCode"))){
            regionCode = jsonObject.getString("regionCode");
        }
        if(StringUtils.isNotBlank(regionCode)){
            // 查询请求单号（20190823110102000001）；
            // 八位日期+六位行政区代码+六位流水号；共20位数;
            String dateStr = DateUtil.formateTimeYmd(new Date());
            String xlhStr = "";
            Integer xlh = bdcBhFeignService.queryLsh(BHSERVICE_YWLX,BHSERVICE_ZZSJFW);
            if(xlh != null){
                // 0补齐六位流水号
                xlhStr = String.format("%06d", xlh);
            }
            return dateStr + regionCode + xlhStr;
        }
        return "";
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param qlrmc
     * @param hyxxResponse
     * @return JSONObject
     * @description 针对 国家返回的婚姻信息的结构 做 过滤 只保留最后一次婚姻信息
     * 如果是已婚 则返回配偶证件号和姓名，其他情况不返回
     */
    public JSONObject filterHyxx(String qlrmc,JSONObject hyxxResponse){
        JSONObject jsonObject = new JSONObject();
        if(hyxxResponse != null){
            BjjkHydjxxResponseDTO responseDTO = JSONObject.toJavaObject(hyxxResponse,BjjkHydjxxResponseDTO.class);
            if(responseDTO.getData() != null && CollectionUtils.isNotEmpty(responseDTO.getData().getRows())){
                List<BjjkHydjxxResponseRows> hyxxList = responseDTO.getData().getRows();
                // 用登记日期排序
                Collections.sort(hyxxList);
                BjjkHydjxxResponseRows responseRows = hyxxList.get(0);
                String opType = responseRows.getOp_type();
                jsonObject.put("opType",opType);
                // 33172【合肥】_新增合并婚姻接口20200927 不管结婚还是离婚都返回日期，用于与省级结婚/离婚接口比较取得有效日期
                jsonObject.put("opDate",responseRows.getOp_date());
                if(StringUtils.equals("IA",opType)
                        || StringUtils.equals("IC",opType)
                        || StringUtils.equals("ICA",opType)){
                    jsonObject.put("jhrq",responseRows.getOp_date());
                    // 如果入参是男方  返回女方信息
                    if(StringUtils.equals(responseRows.getName_man(),qlrmc)){
                        jsonObject.put("xm",responseRows.getName_woman());
                        jsonObject.put("zjh",responseRows.getCert_num_woman());

                        jsonObject.put("req_xm",responseRows.getName_man());
                        jsonObject.put("req_zjh",responseRows.getCert_num_man());
                    }else{
                        jsonObject.put("xm",responseRows.getName_man());
                        jsonObject.put("zjh",responseRows.getCert_num_man());

                        jsonObject.put("req_xm",responseRows.getName_woman());
                        jsonObject.put("req_zjh",responseRows.getCert_num_woman());
                    }
                }
            }
        }

        return jsonObject;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @return java.lang.String
     * @description 获取公安身份核查的查询请求编号
     */
    public String getGasfhcMessageSeq(){
        // 查询请求单号（201901071414120001）；
        // 消息流水号 14 位时间串+4 位自定义索引码，0001 开始，步长为 1 顺序累加
        String dateStr = DateUtil.formateTimeYmdhms(new Date());
        String xlhStr = "";
        Integer xlh = bdcBhFeignService.queryLsh(BHSERVICE_GASFHC_YWLX,BHSERVICE_ZZSJFW);
        if(xlh != null){
            // 0补齐六位流水号
            xlhStr = String.format("%04d", xlh);
        }
        return dateStr + xlhStr;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param json
     * @return java.lang.String
     * @description 给参数加密
     */
    public static String encode(String json){
        String publicKey = EnvironmentConfig.getEnvironment().getProperty("bjjk.token",TOKEN);
        try {
//            LOGGER.info("加密前:{}",json);
            return HfBjjkRSAUtils.encodeByPublic(json,publicKey);
        } catch (Exception e) {
            LOGGER.error("部级接口参数加密失败",e);
        }
        return "";
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param head
     * @param datasJson
     * @return cn.gtmap.realestate.exchange.core.dto.bjjk.sfpjcx.request.SfpjcxRequestDTO
     */
    public SfpjcxRequestDTO setCxqqdhToList(BjjkRequestHead head,JSONObject datasJson){
        SfpjcxRequestDTO dto = new SfpjcxRequestDTO();
        if(head != null && datasJson != null){
            List<SfpjcxRequestData> dataList = new ArrayList<>();
            if(head != null && StringUtils.isNotBlank(head.getCxqqdh())){
                String cxqqdh = head.getCxqqdh();
                JSONArray dataArr = datasJson.getJSONArray("data");
                for(int i = 0 ;i < dataArr.size() ; i++){
                    SfpjcxRequestData tempData = JSONObject.toJavaObject( dataArr.getJSONObject(i),SfpjcxRequestData.class);
                    if(tempData != null){
                        tempData.setCxqqdh(cxqqdh);
                    }
                    dataList.add(tempData);
                }
            }
            dto.setData(dataList);
        }
        return dto;
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param responseObject
     * @return java.lang.Object
     * @description  解密 并解析JSON结构 返回实体
     */
    public static Object decode(JSONObject responseObject){
        String publicKey = EnvironmentConfig.getEnvironment().getProperty("bjjk.token",TOKEN);
        String code = responseObject.getString("data");
        try {
            String data = HfBjjkRSAUtils.decodeByPublic(code,publicKey);
//            String data = "{\n" +
//                    "\t\t\"ResourceInfos\":[{\"ReturnInfos\":[{\"CheckCode\":\"000\"}]}]\n" +
//                    "\t}";
            //String data = "{\"count\":1,\"rows\":[{\"dept_code\":\"34120201\",\"name_woman\":\"岳梦梦\",\"cert_no\":\"J341202-2017-008280\",\"cert_num_woman\":\"341202199412102120\",\"nation_woman\":\"156\",\"dept_name\":\"阜阳市颍州区民政局婚姻登记处\",\"op_date\":\"2017-08-04 00:00:00\",\"birth_woman\":\"1994-12-10 00:00:00\",\"cert_num_man\":\"341227199102254417\",\"birth_man\":\"1991-02-25 00:00:00\",\"nation_man\":\"156\",\"op_type\":\"IA\",\"name_man\":\"巩志强\"}]}";
            LOGGER.info("解码后的数据:{}",data);
            if(StringUtils.isNotBlank(data)){
                if(data.startsWith("{")){
                    responseObject.put("data",JSONObject.parseObject(data));
                }else if(data.startsWith("[")){
                    responseObject.put("data",JSONObject.parseArray(data));
                }else if(data.indexOf("<?xml") > -1 ){
                    if(data.startsWith("\"")){
                        data = data.substring(1,data.length());
                    }
                    if(data.endsWith("\"")){
                        data = data.substring(0,data.length()-1);
                    }
                    data = data.replace("\\r","");
                    data = data.replace("\\n","").replace("\\","");
                    // 目前还有司法判决查询反馈会给XML结构
                    responseObject.put("data", XmlEntityConvertUtil.toBean(data, SfpjcxfkResponseResource.class));
                }else{
                    responseObject.put("data",data);
                }
            }
        } catch (Exception e) {
            LOGGER.error("部级接口参数解密失败",e);
        }
        return responseObject;
    }



    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param responseObject
     * @return java.lang.Object
     * @description  解密 并解析JSON结构 返回实体
     */
    public static Object xmldecode(JSONObject responseObject){
        String data = responseObject.getString("data");
        String xmlContent = "";
        if(StringUtils.isBlank(data)){
            return responseObject;
        }
        try {
            //html解码
            data = StringEscapeUtils.unescapeHtml(data);
            LOGGER.info("解码后的数据:{}",data);
            String gzsx = "";
            String gzcmc = "";
            String gzy = "";
            String gzsbh = "";
            String czsj = "";
            String yt = "";
            String ztm = "";
            String ztms = "";
            String file = "";
            if (data.indexOf("<ZTM>") > 0){
                ztm = data.substring(data.indexOf("<ZTM>") + 5,data.indexOf("</ZTM>"));
            }
            if (data.indexOf("<gzcmc>") > 0){
                gzcmc = data.substring(data.indexOf("<gzcmc>") + 7,data.indexOf("</gzcmc>"));
            }
            if (data.indexOf("<gzy>") > 0){
                gzy = data.substring(data.indexOf("<gzy>") + 5,data.indexOf("</gzy>"));
            }
            if (data.indexOf("<gzsbh>") > 0){
                gzsbh = data.substring(data.indexOf("<gzsbh>") + 7,data.indexOf("</gzsbh>"));
            }
            if (data.indexOf("<czsj>") > 0){
                czsj = data.substring(data.indexOf("<czsj>") + 6,data.indexOf("</czsj>"));
            }
            if (data.indexOf("<gzsx>") > 0){
                gzsx = data.substring(data.indexOf("<gzsx>") + 6,data.indexOf("</gzsx>"));
            }
            if (data.indexOf("<yt>") > 0){
                yt = data.substring(data.indexOf("<yt>") + 4,data.indexOf("</yt>"));
            }
            if (data.indexOf("<ZTMS>") > 0){
                ztms = data.substring(data.indexOf("<ZTMS>") + 6,data.indexOf("</ZTMS>"));
            }
            if (data.indexOf("<file>") > 0){
                file = data.substring(data.indexOf("<file>") + 6,data.indexOf("</file>"));
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ZTM",ztm);
            jsonObject.put("gzcmc",gzcmc);
            jsonObject.put("gzy",gzy);
            jsonObject.put("gzsbh",gzsbh);
            jsonObject.put("czsj",czsj);
            jsonObject.put("gzsx",gzsx);
            jsonObject.put("yt",yt);
            jsonObject.put("ZTMS",ztms);
            jsonObject.put("file",file);
            LOGGER.info("解码后的数据:{}", JSON.toJSONString(jsonObject));
            responseObject.put("data",jsonObject);
        } catch (Exception e) {
            LOGGER.error("解析xml返回值失败",e);
        }
        return responseObject;
    }

}
