package cn.gtmap.realestate.exchange.service.impl.inf.xuancheng;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlJtcyDO;
import cn.gtmap.realestate.common.core.dto.exchange.sjxxgx.HydjcxResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjxxgx.JtcyResponseDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.GetJtcyxxQO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.wwsq.jtcy.response.WwsqQueryJtcyResponseData;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.XmlUtils;
import cn.gtmap.realestate.exchange.util.enums.YcslJtcygxEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @date 2022/12/15  16:21
 * @description 宣城市级接口服务
 */
@Service(value = "sjjkServiceImpl")
public class SjjkServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(SjjkServiceImpl.class);

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;


    /**
     * @param jtcyxxQO
     * @description: 为受理提供查询婚姻，过滤婚姻信息
     * @author: <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @date: 2022/12/15 17:06
     * @return: com.alibaba.fastjson.JSONObject
     **/
    public JSONObject hyxxFilter(GetJtcyxxQO jtcyxxQO) {
        if (StringUtils.isBlank(jtcyxxQO.getQlrmc()) || StringUtils.isBlank(jtcyxxQO.getQlrzjh())) {
            throw new MissingArgumentException("宣城婚姻接口，查询参数缺失名称或证件号！");
        }
        JSONObject jsonObject = new JSONObject();
        Map<String, String> param = new HashMap<String, String>();
        param.put("XM", jtcyxxQO.getQlrmc());
        param.put("GMSFHM", jtcyxxQO.getQlrzjh());
        Map paramMap = initRequest(XmlUtils.setXmlData(CommonConstantUtils.HYDJCX_REQUEST_CONDITION, param), CommonConstantUtils.HYDJCX_REQUEST_REQUIREDITEMS, CommonConstantUtils.REQUEST_CLIENTINFO);
        LOGGER.info("婚姻登记查询请求参数：{}", paramMap);
        JSONObject paramJson = JSONObject.parseObject(JSONObject.toJSONString(paramMap));
        Object response = exchangeBeanRequestService.request("hydjcx", paramJson);
//        String response = "{\"response\":{\"errorCode\":200,\"value\":{\"type\":\"GsbString\",\"value\":\"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?><response><head><condition><item><GMSFHM>342522198909073319</GMSFHM><XM>王峰</XM></item></condition><requiredItems><item><DJJG>登记机构</DJJG><DJRQ>登记日期</DJRQ><DJZH>登记证号</DJZH><GMSFHM>公民身份号码</GMSFHM><HYDJYWLX>婚姻登记业务类型</HYDJYWLX><LFCSRQ>女方出生日期</LFCSRQ><LFGJ>女方国籍</LFGJ><LFGMSFHM>女方公民身份号码</LFGMSFHM><LFXM>女方姓名</LFXM><NFCSRQ>男方出生日期</NFCSRQ><NFGJ>男方国籍</NFGJ><NFGMSFHM>男方公民身份号码</NFGMSFHM><NFXM>男方姓名</NFXM><XM>姓名</XM></item></requiredItems><clientInfo><loginName>sadmin</loginName></clientInfo></head><body><message>请求成功</message><resultCode>00</resultCode><resultList><result><NFXM>王峰</NFXM><DJRQ>20150512000000</DJRQ><DJZH>J341821-2015-1121</DJZH><NFGMSFHM>342522198909073319</NFGMSFHM><LFCSRQ>19900808000000</LFCSRQ><LFGJ>中国</LFGJ><XM>王峰</XM><LFGMSFHM>342522199008083629</LFGMSFHM><NFGJ>中国</NFGJ><DJJG>宣城市郎溪县民政局婚姻登记处</DJJG><NFCSRQ>19890907000000</NFCSRQ><GMSFHM>342522198909073319</GMSFHM><LFXM>黄莹</LFXM><HYDJYWLX>结婚</HYDJYWLX></result></resultList></body></response>\"}}}";
        LOGGER.info("接口返回参数：：{}", response);
        List result = new ArrayList<>();
        if (response != null) {
            JSONObject responseJson = new JSONObject((Map<String, Object>) response);
            String resultXml = responseJson.getJSONObject("response").getJSONObject("value").getString("value");
            LOGGER.info("resultXml：{}", resultXml);
            String resultCode = XmlUtils.getXmlElementValue(resultXml, "resultCode");
            if (resultCode.equals("00")) {
                result = readXMLToList(resultXml, HydjcxResponseDTO.class);
            }
            LOGGER.info("返回的result：{}", result);
            if (CollectionUtils.isNotEmpty(result)) {
                // 根据 登记日期排序，取最近的一条数据
                Collections.sort(result);
                HydjcxResponseDTO responseDTO = (HydjcxResponseDTO) result.get(0);
                // 结婚
                if (CommonConstantUtils.HYDJYWLX_JH.equals(responseDTO.getHYDJYWLX())) {
                    jsonObject.put("hyzt", "已婚");
                    BdcSlJtcyDO jtcy = new BdcSlJtcyDO();
                    // 入参如果是男方，返回女方信息
                    if (jtcyxxQO.getQlrmc().equals(responseDTO.getNFXM())) {
                        jtcy.setJtcymc(responseDTO.getLFXM());
                        jtcy.setZjh(responseDTO.getLFGMSFHM());
                    } else {
                        jtcy.setJtcymc(responseDTO.getNFXM());
                        jtcy.setZjh(responseDTO.getNFGMSFHM());
                    }
                    jsonObject.put("jtcy", jtcy);
                } else {
                    jsonObject.put("hyzt", "离婚");
                }
            } else {
                jsonObject.put("hyzt", "未婚");
            }
        }
        return jsonObject;
    }


    /**
     * @param jtcyxxQO
     * @description: 宣城查询家庭成员信息，未成年子女信息
     * @author: <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @date: 2022/12/15 19:44
     * @return: cn.gtmap.realestate.common.core.domain.accept.BdcSlJtcyDO
     **/
    public List<BdcSlJtcyDO> jtcyFilter(GetJtcyxxQO jtcyxxQO) {
        if (StringUtils.isBlank(jtcyxxQO.getQlrzjh())) {
            throw new MissingArgumentException("宣城家庭成员接口，查询参数证件号！");
        }
        List<BdcSlJtcyDO> jtcyDOList = new ArrayList<>();
        Map<String, String> param = new HashMap<String, String>();
        param.put("ZJH", jtcyxxQO.getQlrzjh());
        Map paramMap = initRequest(XmlUtils.setXmlData(CommonConstantUtils.JTCYCX_REQUEST_CONDITION, param), CommonConstantUtils.JTCYCX_REQUEST_REQUIREDITEMS, CommonConstantUtils.REQUEST_CLIENTINFO);
        LOGGER.info("家庭成员查询请求参数：{}", paramMap);
        JSONObject paramJson = JSONObject.parseObject(JSONObject.toJSONString(paramMap));
        Object response = exchangeBeanRequestService.request("jtcycx", paramJson);
//        String response = "{\n" +
//                "    \"response\":{\"errorCode\":200,\"value\":{\"type\":\"GsbString\",\"value\":\"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?><response><head><condition><item><ZJH>342522198909073319</ZJH></item></condition><requiredItems><item><CSRQ>出生日期</CSRQ><GJ>国籍</GJ><HJDZ>户籍地址</HJDZ><HSLBZ>何时来本址</HSLBZ><HXZLBZ>何详址来本址</HXZLBZ><HYZK>婚姻状况</HYZK><JHRQ>结婚日期</JHRQ><MZ>民族</MZ><NVXM>婚姻女方姓名</NVXM><NVZJH>婚姻女方证件号</NVZJH><NXM>婚姻男方姓名</NXM><NZJH>婚姻男方证件号</NZJH><QFJG>签发机构</QFJG><SG>身高</SG><SSXQ>省市县区</SSXQ><WHCD>文化程度</WHCD><XB>性别</XB><XM>姓名</XM><YHZGX>与户主关系</YHZGX><YXQXQSRQ>登记日期</YXQXQSRQ><ZJH>身份证</ZJH><ZJXY>宗教信仰</ZJXY></item></requiredItems><clientInfo><loginName>sadmin</loginName></clientInfo></head><body><message>请求成功</message><resultCode>00</resultCode><resultList><result><HJDZ>安徽省郎溪县十字镇施吴村施村９号</HJDZ><YXQXQSRQ>20060608</YXQXQSRQ><HXZLBZ>十字镇施吴村施村９号</HXZLBZ><MZ>汉族</MZ><NZJH></NZJH><SG>168</SG><HSLBZ>20130916</HSLBZ><NVZJH></NVZJH><QFJG>郎溪县公安局</QFJG><ZJH>342522196507033323</ZJH><ZJXY></ZJXY><CSRQ>19650703</CSRQ><HYZK>离婚</HYZK><XM>肖玉凤</XM><XB>女</XB><NXM></NXM><YHZGX>户主</YHZGX><SSXQ>安徽省郎溪县</SSXQ><JHRQ></JHRQ><GJ></GJ><NVXM></NVXM><WHCD>小学教育</WHCD></result><result><HJDZ>安徽省郎溪县十字镇施吴村施村９号</HJDZ><YXQXQSRQ>20140630</YXQXQSRQ><HXZLBZ>十字镇施吴村施村９号</HXZLBZ><MZ>汉族</MZ><NZJH></NZJH><SG>170</SG><HSLBZ>20130916</HSLBZ><NVZJH>342522199008083629</NVZJH><QFJG>郎溪县公安局</QFJG><ZJH>342522198909073319</ZJH><ZJXY></ZJXY><CSRQ>19890907</CSRQ><HYZK>已婚</HYZK><XM>王峰</XM><XB>男</XB><NXM></NXM><YHZGX>子</YHZGX><SSXQ>安徽省郎溪县</SSXQ><JHRQ>2015-05-12</JHRQ><GJ></GJ><NVXM>黄莹</NVXM><WHCD>大学本科教育</WHCD></result><result><HJDZ>安徽省郎溪县十字镇施吴村施村９号</HJDZ><YXQXQSRQ></YXQXQSRQ><HXZLBZ></HXZLBZ><MZ>汉族</MZ><NZJH></NZJH><SG></SG><HSLBZ></HSLBZ><NVZJH></NVZJH><QFJG></QFJG><ZJH>341821202003061213</ZJH><ZJXY></ZJXY><CSRQ>20200306</CSRQ><HYZK></HYZK><XM>王敏博</XM><XB>男</XB><NXM></NXM><YHZGX>孙子</YHZGX><SSXQ>安徽省郎溪县</SSXQ><JHRQ></JHRQ><GJ></GJ><NVXM></NVXM><WHCD></WHCD></result><result><HJDZ>安徽省郎溪县十字镇施吴村施村９号</HJDZ><YXQXQSRQ></YXQXQSRQ><HXZLBZ></HXZLBZ><MZ>汉族</MZ><NZJH></NZJH><SG></SG><HSLBZ></HSLBZ><NVZJH></NVZJH><QFJG></QFJG><ZJH>341821201511171211</ZJH><ZJXY></ZJXY><CSRQ>20151117</CSRQ><HYZK></HYZK><XM>王悠然</XM><XB>男</XB><NXM></NXM><YHZGX>孙子</YHZGX><SSXQ>安徽省郎溪县</SSXQ><JHRQ></JHRQ><GJ></GJ><NVXM></NVXM><WHCD></WHCD></result></resultList></body></response>\"}}}\n";
        LOGGER.info("接口返回参数：：{}", response);
        List result = new ArrayList<>();
        if (response != null) {
            JSONObject responseJson = new JSONObject((Map<String, Object>) response);
            String resultXml = responseJson.getJSONObject("response").getJSONObject("value").getString("value");
            LOGGER.info("resultXml：{}", resultXml);
            String resultCode = XmlUtils.getXmlElementValue(resultXml, "resultCode");
            if (resultCode.equals("00")) {
                result = readXMLToList(resultXml, JtcyResponseDTO.class);
            }
            LOGGER.info("返回的result：{}", result);
            if (CollectionUtils.isNotEmpty(result)) {
                // 查询人与户主关系
                String cxrYhzgx = "";
                for (Object object : result) {
                    JtcyResponseDTO jtcyResponseDTO = (JtcyResponseDTO) object;
                    // 确认查询人与户主的关系
                    if (jtcyxxQO.getQlrzjh().equals(jtcyResponseDTO.getZJH())) {
                        cxrYhzgx = jtcyResponseDTO.getYHZGX();
                    }
                }
                // 需要查找查询人的未成年子女,各种情况需要对应的与户主关系
                // 查询人子女与户主关系
                String cxrznYhzgx = "";
                if (StringUtils.equals("户主", cxrYhzgx) || StringUtils.equals("夫妻", cxrYhzgx)) {
                    //查询人为户主或者为户主的配偶,当前查询人的子女即为户主的子女
                    cxrznYhzgx = "子";
                } else if (StringUtils.equals("子", cxrYhzgx)) {
                    //查询人为户主的子女,当前查询人的子女即为户主以及户主的孙子女
                    cxrznYhzgx = "孙子,孙女";
                } else if (StringUtils.equals("父母", cxrYhzgx)) {
                    //查询人为户主的父母,当前查询人的子女即为户主以及户主的兄弟姐妹
                    cxrznYhzgx = "户主,兄弟,姐妹";
                }
                // 查找未成年子女
                for (Object object : result) {
                    JtcyResponseDTO jtcyResponseDTO = (JtcyResponseDTO) object;
                    if ((StringUtils.isBlank(cxrznYhzgx) || cn.gtmap.realestate.common.util.CommonUtil.indexOfStrs(cxrznYhzgx.split(CommonConstantUtils.ZF_YW_DH), jtcyResponseDTO.getYHZGX())) && !CommonUtil.checkSfcn(jtcyResponseDTO.getZJH())) {
                        BdcSlJtcyDO slJtcyDO = new BdcSlJtcyDO();
                        slJtcyDO.setJtcymc(jtcyResponseDTO.getXM());
                        slJtcyDO.setZjh(jtcyResponseDTO.getZJH());
                        slJtcyDO.setYsqrgx(CommonConstantUtils.YSQRGX_WCNZN_MC);
                        jtcyDOList.add(slJtcyDO);
                    }
                }
            }
        }
        return jtcyDOList;

    }


    /**
     * @description: 为互联网+做家庭成员过滤,宣城市级接口
     * @author: <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @date: 2023/1/3 16:09
     * @param jsonParam
     * @return: java.util.List<cn.gtmap.realestate.exchange.core.dto.wwsq.jtcy.response.WwsqQueryJtcyResponseData>
     **/
    public List<WwsqQueryJtcyResponseData> filterJtCy(JSONObject jsonParam) {
        List<WwsqQueryJtcyResponseData> filteredJtcyList = new ArrayList<>();
        String qlrzjh ="";
        JSONObject data  = jsonParam.getJSONObject("data");
        if (Objects.nonNull(data)){
            qlrzjh = data.getString("qlrzjh");
        }
        if (StringUtils.isBlank(qlrzjh)){
            return Collections.emptyList();
        }
        Map<String, String> param = new HashMap<String, String>();
        param.put("ZJH", qlrzjh);
        Map paramMap = initRequest(XmlUtils.setXmlData(CommonConstantUtils.JTCYCX_REQUEST_CONDITION, param), CommonConstantUtils.JTCYCX_REQUEST_REQUIREDITEMS, CommonConstantUtils.REQUEST_CLIENTINFO);
        LOGGER.info("家庭成员查询请求参数：{}", paramMap);
        JSONObject paramJson = JSONObject.parseObject(JSONObject.toJSONString(paramMap));
        Object response = exchangeBeanRequestService.request("jtcycx", paramJson);
//        String response = "{\n" +
//                "    \"response\":{\"errorCode\":200,\"value\":{\"type\":\"GsbString\",\"value\":\"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?><response><head><condition><item><ZJH>342522198909073319</ZJH></item></condition><requiredItems><item><CSRQ>出生日期</CSRQ><GJ>国籍</GJ><HJDZ>户籍地址</HJDZ><HSLBZ>何时来本址</HSLBZ><HXZLBZ>何详址来本址</HXZLBZ><HYZK>婚姻状况</HYZK><JHRQ>结婚日期</JHRQ><MZ>民族</MZ><NVXM>婚姻女方姓名</NVXM><NVZJH>婚姻女方证件号</NVZJH><NXM>婚姻男方姓名</NXM><NZJH>婚姻男方证件号</NZJH><QFJG>签发机构</QFJG><SG>身高</SG><SSXQ>省市县区</SSXQ><WHCD>文化程度</WHCD><XB>性别</XB><XM>姓名</XM><YHZGX>与户主关系</YHZGX><YXQXQSRQ>登记日期</YXQXQSRQ><ZJH>身份证</ZJH><ZJXY>宗教信仰</ZJXY></item></requiredItems><clientInfo><loginName>sadmin</loginName></clientInfo></head><body><message>请求成功</message><resultCode>00</resultCode><resultList><result><HJDZ>安徽省郎溪县十字镇施吴村施村９号</HJDZ><YXQXQSRQ>20060608</YXQXQSRQ><HXZLBZ>十字镇施吴村施村９号</HXZLBZ><MZ>汉族</MZ><NZJH></NZJH><SG>168</SG><HSLBZ>20130916</HSLBZ><NVZJH></NVZJH><QFJG>郎溪县公安局</QFJG><ZJH>342522196507033323</ZJH><ZJXY></ZJXY><CSRQ>19650703</CSRQ><HYZK>离婚</HYZK><XM>肖玉凤</XM><XB>女</XB><NXM></NXM><YHZGX>母亲</YHZGX><SSXQ>安徽省郎溪县</SSXQ><JHRQ></JHRQ><GJ></GJ><NVXM></NVXM><WHCD>小学教育</WHCD></result><result><HJDZ>安徽省郎溪县十字镇施吴村施村９号</HJDZ><YXQXQSRQ>20140630</YXQXQSRQ><HXZLBZ>十字镇施吴村施村９号</HXZLBZ><MZ>汉族</MZ><NZJH></NZJH><SG>170</SG><HSLBZ>20130916</HSLBZ><NVZJH>342522199008083629</NVZJH><QFJG>郎溪县公安局</QFJG><ZJH>342522198909073319</ZJH><ZJXY></ZJXY><CSRQ>19890907</CSRQ><HYZK>已婚</HYZK><XM>王峰</XM><XB>男</XB><NXM></NXM><YHZGX>户主</YHZGX><SSXQ>安徽省郎溪县</SSXQ><JHRQ>2015-05-12</JHRQ><GJ></GJ><NVXM>黄莹</NVXM><WHCD>大学本科教育</WHCD></result><result><HJDZ>安徽省郎溪县十字镇施吴村施村９号</HJDZ><YXQXQSRQ></YXQXQSRQ><HXZLBZ></HXZLBZ><MZ>汉族</MZ><NZJH></NZJH><SG></SG><HSLBZ></HSLBZ><NVZJH></NVZJH><QFJG></QFJG><ZJH>341821202003061213</ZJH><ZJXY></ZJXY><CSRQ>20200306</CSRQ><HYZK></HYZK><XM>王敏博</XM><XB>男</XB><NXM></NXM><YHZGX>子</YHZGX><SSXQ>安徽省郎溪县</SSXQ><JHRQ></JHRQ><GJ></GJ><NVXM></NVXM><WHCD></WHCD></result><result><HJDZ>安徽省郎溪县十字镇施吴村施村９号</HJDZ><YXQXQSRQ></YXQXQSRQ><HXZLBZ></HXZLBZ><MZ>汉族</MZ><NZJH></NZJH><SG></SG><HSLBZ></HSLBZ><NVZJH></NVZJH><QFJG></QFJG><ZJH>341821201511171211</ZJH><ZJXY></ZJXY><CSRQ>20151117</CSRQ><HYZK></HYZK><XM>王悠然</XM><XB>男</XB><NXM></NXM><YHZGX>子</YHZGX><SSXQ>安徽省郎溪县</SSXQ><JHRQ></JHRQ><GJ></GJ><NVXM></NVXM><WHCD></WHCD></result></resultList></body></response>\"}}}\n";
        LOGGER.info("接口返回参数：：{}", response);
        List result = new ArrayList<>();
        if (response != null) {
            JSONObject responseJson = new JSONObject((Map<String, Object>) response);
            String resultXml = responseJson.getJSONObject("response").getJSONObject("value").getString("value");
            LOGGER.info("resultXml：{}", resultXml);
            String resultCode = XmlUtils.getXmlElementValue(resultXml, "resultCode");
            if (resultCode.equals("00")) {
                result = readXMLToList(resultXml, JtcyResponseDTO.class);
            }
            LOGGER.info("返回的result：{}", result);

            if (CollectionUtils.isNotEmpty(result)) {
                for (Object object : result) {
                    JtcyResponseDTO jtcyResponseDTO = (JtcyResponseDTO) object;
                    // 与户主关系，未成年子女，未成年兄妹，父母
                    Boolean adult = CommonUtil.checkSfcn(jtcyResponseDTO.getZJH());
                    String yhzgx = jtcyResponseDTO.getYHZGX();
                    WwsqQueryJtcyResponseData wwsqQueryJtcyResponseData = new WwsqQueryJtcyResponseData();
                    if (adult) {
                        if (StringUtils.equals("父亲", yhzgx) || StringUtils.equals("母亲", yhzgx)) {
                            wwsqQueryJtcyResponseData.setQlrmc(jtcyResponseDTO.getXM());
                            wwsqQueryJtcyResponseData.setQlrzjh(jtcyResponseDTO.getZJH());
                            wwsqQueryJtcyResponseData.setYhzgx(YcslJtcygxEnum.YCSL_JTCY_FM.getDm());
                            filteredJtcyList.add(wwsqQueryJtcyResponseData);
                        }
                    } else {
                        if (StringUtils.equals("子", yhzgx)) {
                            wwsqQueryJtcyResponseData.setQlrmc(jtcyResponseDTO.getXM());
                            wwsqQueryJtcyResponseData.setQlrzjh(jtcyResponseDTO.getZJH());
                            wwsqQueryJtcyResponseData.setYhzgx(YcslJtcygxEnum.YCSL_JTCY_WCNZNV.getDm());
                            filteredJtcyList.add(wwsqQueryJtcyResponseData);
                        } else if (StringUtils.equals("兄弟姐妹", yhzgx)) {
                            wwsqQueryJtcyResponseData.setQlrmc(jtcyResponseDTO.getXM());
                            wwsqQueryJtcyResponseData.setQlrzjh(jtcyResponseDTO.getZJH());
                            wwsqQueryJtcyResponseData.setYhzgx(YcslJtcygxEnum.YCSL_JTCY_WCNXM.getDm());
                            filteredJtcyList.add(wwsqQueryJtcyResponseData);
                        }
                    }
                }
            }
        }
        return filteredJtcyList;
    }


    public Map initRequest(String condition, String requiredItems, String clientInfo) {
        Map paramMap = new HashMap();
        List<Map> data = new ArrayList<>();
        Map conditionParam = new HashMap();
        conditionParam.put("name", "condition");
        conditionParam.put("type", "GsbString");
        conditionParam.put("value", condition);
        data.add(conditionParam);
        Map requiredItemsParam = new HashMap();
        requiredItemsParam.put("name", "requiredItems");
        requiredItemsParam.put("type", "GsbString");
        requiredItemsParam.put("value", requiredItems);
        data.add(requiredItemsParam);
        Map clientInfoParam = new HashMap();
        clientInfoParam.put("name", "clientInfo");
        clientInfoParam.put("type", "GsbString");
        clientInfoParam.put("value", clientInfo);
        data.add(clientInfoParam);
        paramMap.put("data", JSONArray.parseArray(JSON.toJSONString(data)));
        paramMap.put("method", "");
        paramMap.put("serviceCode", "");
        paramMap.put("version", "");
        paramMap.put("authCode", "");
        paramMap.put("senderID", "");
        return paramMap;
    }

    public static List<Object> readXMLToList(String s, Class<?> clazz) {

        List<Object> list = new ArrayList();//创建list集合
        try {
            //1.创建一个SAXBuilder的对象
            SAXReader reader = new SAXReader();
            // 指定编码格式为UTF-8,可根据具体情况修改
            org.dom4j.Document doc = reader.read(new InputSource(new ByteArrayInputStream(s.getBytes("UTF-8"))));//dom4j读取
            // 4.通过document对象获取xml文件的根节点
            org.dom4j.Element root = doc.getRootElement();//获得根节点
            // 5. 获取根节点下的子节点items,即要解析XML多节点的父节点，可根据具体情况修改
            org.dom4j.Element body1 = root.element("body");
            org.dom4j.Element body = body1.element("resultList");
            // 6. 获取根节点下的二级节点
            org.dom4j.Element foo;
            for (Iterator i = body.elementIterator("result"); i.hasNext(); ) {//遍历t.getClass().getSimpleName()节点
                foo = (org.dom4j.Element) i.next();//下一个二级节点
                //实例化Object对象
                Object obj = clazz.newInstance();
                Field[] properties = obj.getClass().getDeclaredFields();//获得实例的属性
                //实例的get方法
                Method getmeth;
                //实例的set方法
                Method setmeth;
                //遍历实体类的属性
                for (int j = 0; j < properties.length; j++) {
                    //实例的set方法
                    if (properties[j].getName().equals("serialVersionUID")) {
                        continue;
                    } else {
                        setmeth = obj.getClass().getMethod(
                                "set"
                                        + properties[j].getName().substring(0, 1).toUpperCase()
                                        + properties[j].getName().substring(1), properties[j].getType());
                        Object setStr = foo.elementText(properties[j].getName());

                        if (foo.elementText(properties[j].getName()) != null && !"".equals(foo.elementText(properties[j].getName()))) {
                            if (properties[j].getType() == java.util.Date.class) {
                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Date sj = df.parse(foo.elementText(properties[j].getName()));
                                setStr = sj;
                            } else if (properties[j].getType() == java.lang.Integer.class) {
                                setStr = Integer.parseInt(foo.elementText(properties[j].getName()));
                            } else if (properties[j].getType() == java.lang.Character.class) {
                                setStr = foo.elementText(properties[j].getName()).charAt(0);
                            } else if (properties[j].getType() == java.lang.Double.class) {
                                setStr = Double.parseDouble(foo.elementText(properties[j].getName()));
                            }
                        } else {
                            setStr = null;
                        }
                        //将对应节点的值存入
                        setmeth.invoke(obj, setStr);
                    }
                }
                list.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
