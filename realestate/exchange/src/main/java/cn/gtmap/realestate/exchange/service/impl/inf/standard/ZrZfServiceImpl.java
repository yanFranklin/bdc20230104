package cn.gtmap.realestate.exchange.service.impl.inf.standard;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.dto.exchange.swxx.SftjDzdResponseDTO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.exchange.core.dto.zrpay.tk.ZrTkjgcxRequest;
import cn.gtmap.realestate.exchange.core.dto.zrpay.tk.ZrTkjgcxResponse;
import cn.gtmap.realestate.exchange.core.dto.zrpay.tk.ZrTksqRequest;
import cn.gtmap.realestate.exchange.core.dto.zrpay.tk.ZrTksqResponse;
import cn.gtmap.realestate.exchange.util.zrpay.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.gtmap.realestate.exchange.core.dto.zrpay.BaseVo;
import cn.gtmap.realestate.exchange.core.dto.zrpay.dzwj.FileDownloadVo;
import cn.gtmap.realestate.exchange.core.dto.zrpay.dzwj.VerifyFileNoticeQo;
import cn.gtmap.realestate.exchange.core.dto.zrpay.jfqr.PayResultDTO;
import cn.gtmap.realestate.exchange.core.dto.zrpay.jfqr.PayConfirmVo;
import cn.gtmap.realestate.exchange.core.dto.zrpay.jfqr.PayResultNoticeDTO;
import cn.gtmap.realestate.exchange.core.dto.zrpay.jfqr.PayResultNoticeVo;
import cn.gtmap.realestate.exchange.core.dto.zrpay.jfqr.PosPaySuccessQO;
import cn.gtmap.realestate.exchange.core.dto.zrpay.jfqr.PosPaySuccessVO;
import cn.gtmap.realestate.exchange.core.dto.zrpay.scdd.PayMentInVo;
import cn.gtmap.realestate.exchange.core.dto.zrpay.scdd.PayOrderVO;
import cn.gtmap.realestate.exchange.util.DateUtil;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuhzou</a>
 * @version 1.0  2022/08/10
 * @description 政融支付平台接口
 */
@Service("zrZfServiceImpl")
public class ZrZfServiceImpl {

    /**
     * 政融支付公钥
     */
    @Value("${zrzf.public.key:MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkxJVDKgDhDGS/OCBDmqD1F5j8yzeimmEmaFjz3w+oO6vUDpJ+bGesONsK0Ue1ttOyFBm0x1IRRmJJWTTL9NSNm+f8P1nttZiRSdWIgOjXwxZvasndDN9cUArilIGPNhpLxkHaZkzvym/GKPPbr/p5ys/iFFjZsxGTJ5k84KnK83yC503TGqLweOp24/ghJZO80lPH3ZNTQUqmV4JodTdRvirJbPAZBoc2lUkizYX4NitAqCDnXXFN8JT9C2tJONy6s8JRsuXC7Y6+kffXTxUne2UAvuwkyBJtMsiH3a38yj9V9PLbggOST9gpUG7ISwe2PGWamYsx0/tJJR8avUnwwIDAQAB}")
    private String publicKey;

    /**
     * 政融支付私钥
     */
    @Value("${zrzf.anther.key:MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCDcj2lNGdpEwFY196A/pcT9s9/+XgfI6rYb5GeBoOattnEL7JAe3Zbob0XtZDOnuYP+3yaorqVmDvwFAsACt9ac8jXXpghvLHIW8rdv25jbjlajOcwQy/H42+tBKgZV7oO3r8zj58PvjXjj7wwFEHiacRMI3Y6YCYi60omKQeCk9bY7L5FK7Mym4GrNT4+tv5A/zyBS7NuaCO8reup5BVtg2MjVuizoMDOPNr7sT1oqFj21KqbIrL+TzQe/hiddOrRVY8llAtXmq0lvMcjfnfgCtbs+rhYijIXjfi1VEh+cq9Gl4RY/3Vh5KE58jgVk7ggFuyvDmr57sDtb62V8RgZAgMBAAECggEAKTNfuy6n0zmPzIctSQWD2VAdTiGl0yzc2iZuYDfufEogC+xfVPLd8G7L1Gim0PzhY9USA25KSaWD9AZ0RDkkNTfhlhAiY0DAAOs0tTjRovzjoUxVEr2cdm0FjhfNkNP8j7il+cVkuAyI26Er/W38ELnSO4NKBZYpnfxeK4PkxLqZW8/NKUXJni7FaPni2S7tWhgkt7Zqa/gdLnCQMLaf9xePhLzqrM2gT10kkZjBDQ4WqvfsgLRMUJ8B40e3QezYxTtYlzaZCSibBMI+gMnBKJOzXf01wUybNpBWYmDFMDoMColILB2GeSTzO92V0DceewaOamBpKaXTj/DeMFZuuQKBgQDbiHF+/GOIirzdWe0SiJkKmv4MRvIcTGrwGOepdEimbDvFaq+M4JxS0n5drufxq1E8kmDAcLfatvf7NZ0+FZBMMMfl3giLcpZvgCxKxABefdZdkOsTvTgG0ckKwf9qO2Yq+A6tlk52Hv80tecXsIDGdvQnU/52xryTUBcbdWjfCwKBgQCZR/FaPUFDjkRwU/uHq21azGUNCUjKH6O1ULRBWJdQBv5OLvCxqvwXSJGAYaG1JdwP15jI4jNzJfmmM8IT3Jw65zAlhXe01l2LEf7PohAVN5LDqd5Lq2rwCYhAV5aspk7Wc2gHhTsyRgTZWeLHF7i1Xc0JlmW5mgW4/t7QExCr6wKBgCs+z4zCTyEgo1+/TTIvcmZibdUhTKRCcXZmkYwR+hW+kG+tOnO381NlX7s4rzwuEUyrUR/XlIAjNupnf1gxi0FXAqnHeUtvAS9pwk/gGGqEw2ufFo/G4HiHbuENojDdDp08TDfpuf8O0BskEifafyOZXzM4GpJvR8qFJmgkUspNAoGAaWGl1FWixhBMizGiD59TOoalvrWwXo4sHh8THo4K0ZFNS3FIN84HLPbOWgZFh+Y0iou+VfX2S2dDYPnap48XtgN1/YXqS+DJRTClEBkql3uyomTqGPoMNmVHUH0ncSGRuCx1zB3UGfc7pDcBC8IKUl7f9YR6AYWcA5julP1Wi+sCgYEAyysEFyNAlTaDrtVMqYcRZ2Gufqoi/2qUzFFQnT6HTuhiB+S67p0VYaTPI4/XllrftkfiVYmt/gI2YuGhzrgO3vF6ucsqrNfnFdeA2HUomcO4XkFygt/die1pgAZ+XRL232F1kIWSbmIoerVBubc+vAZpEtVjVVJNgMMPSz29Nrs=}")
    private String antherKey;

    /**
     * 政融支付下单url
     */
    @Value("${zrzf.payment.url:http://snhlwga.gp.mytunnel.site:8029/online/gzf0001}")
    private String paymentUrl;

    /**
     * 政融支付支付结果url
     */
    @Value("${zrzf.payConfirm.url:}")
    private String payConfirmUrl;

    /**
     * 政融支付pos支付成功url
     */
    @Value("${zrzf.posPaySuccess.url:}")
    private String posPaySuccessUrl;

    /**
     * 政融对账文件下载url
     */
    @Value("${zrzf.dzdxz.url:http://snhlwga.gp.mytunnel.site:8029/online/gzf0005}")
    private String dzdxzUrl;

    /**
     * 政融支付线上退款申请url
     */
    @Value("${zrzf.xstksq.url:}")
    private String xstksqUrl;

    /**
     * 政融支付线上退款申请结果查询url
     */
    @Value("${zrzf.xstkjgcx.url:}")
    private String xstkjgcxUrl;

    /**
     * 政融支付pos支付接口，商户代码
     */
    @Value("${zrzf.zfcg.shdm:}")
    private String shdm;

    /**
     * 政融支付pos支付接口，终端号
     */
    @Value("${zrzf.zfcg.zdh:}")
    private String zdh;

    /**
     * 政融支付公共字段： 发起方系统编号
     */
    @Value("${zrzf.payorder.ittpartystmid:}")
    private String fqfxtbh;

    /**
     * 政融支付公共字段： pos支付渠道代码
     */
    @Value("${zrzf.payorder.posPychnlcd:}")
    private String posPychnlcd;

    @Autowired
    BdcZdFeignService zdFeignService;

    protected static final Logger LOGGER = LoggerFactory.getLogger(ZrZfServiceImpl.class);

    /**
     * @param
     * @param payMentInVo
     * @return void
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/10 16:42
     * @description 生成支付订单
     **/
    public PayOrderVO payOrder(PayMentInVo payMentInVo) {
        LOGGER.info("政融生成支付订单参数{}", payMentInVo);
        if (payMentInVo == null) {
            return null;
        }
        // 为线下支付（POS、现金）时，设置支付渠道代码配置为zrzf.payorder.posPychnlcd
        String onlnOflnIndCd = payMentInVo.getOnlnOflnIndCd();
        if ("2".equals(onlnOflnIndCd) && StringUtils.isNotBlank(posPychnlcd)) {
            payMentInVo.setPyChnlCd(posPychnlcd);
        }
        // 请求参数参与签名
        Set<String> requestSet = new HashSet<>();
        requestSet.add("FEEGRP");
        requestSet.add("TAXGRP");
        // 返回参数签名
        Set<String> responseSet = new HashSet<>();
        String payOrderStr = sendRequestToZr(payMentInVo, requestSet, responseSet, paymentUrl);
        if (StringUtils.isNotBlank(payOrderStr)) {
            return JSONObject.parseObject(payOrderStr, PayOrderVO.class);
        }
        return null;
    }

    /**
     * @param
     * @param jsonObject
     * @return void
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/11 17:55
     * @description 接收政融支付结果通知
     **/
    public PayResultNoticeDTO payResultNotice(JSONObject jsonObject) {
        Set<String> setRe = new HashSet<>();
        setRe.add("LIST1");
        String result =EncryUtil.decryption(jsonObject.getString("Tprt_Parm"), setRe, jsonObject.getString("Bsn_Data"), antherKey, publicKey);
        PayResultNoticeDTO payResultNoticeDTO = new PayResultNoticeDTO();
        payResultNoticeDTO.setRcvStCd("01");
        if (StringUtils.isNotBlank(result)) {
            PayResultNoticeVo payResultNoticeVo = JSONObject.parseObject(result, PayResultNoticeVo.class);
            LOGGER.info("接收政融支付结果通知入参{}", payResultNoticeVo);
            payResultNoticeDTO.setRcvStCd("00");
            payResultNoticeDTO.setRecTm(DateUtil.formatDate("yyyyMMddhhmmss"));
        }
        return payResultNoticeDTO;
    }

    /**
     * @param
     * @param payConfirmVo
     * @return void
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/11 16:34
     * @description 支付结果确认
     **/
    public PayResultDTO payResult(PayConfirmVo payConfirmVo) {
        LOGGER.info("接收到政融支付确认参数{}", payConfirmVo);
        Set<String> requestSet = new HashSet<>();
        payConfirmVo.setIttPartyTms(DateUtils.formateTime(new Date(), DateUtils.DATE_FORMATYMDHMSF));

        Set<String> responseSet = new HashSet<>();
        responseSet.add("LIST1");
        String result = sendRequestToZr(payConfirmVo, requestSet, responseSet, payConfirmUrl);
        if (StringUtils.isNotBlank(result)) {
            PayResultDTO payResultDTO = JSONObject.parseObject(result, PayResultDTO.class);
            // 进行字典值对照，对照支付状态、订单状态
            if (StringUtils.isNotBlank(payResultDTO.getPyStCd())) {
                String pystcd_zdz = getDsfZdDzxx("BDC_ZRZF_ZFZT","pyStCd", payResultDTO.getPyStCd());
                payResultDTO.setPyStCd(pystcd_zdz);
            }
            if(StringUtils.isNotBlank(payResultDTO.getOrderStCd())){
                String orderstcd_zdz = getDsfZdDzxx("BDC_ZRZF_DDZT","orderStCd", payResultDTO.getOrderStCd());
                payResultDTO.setOrderStCd(orderstcd_zdz);
            }
            return payResultDTO;
        }
        return null;
    }

    // 获取第三方对照信息
    public String getDsfZdDzxx(String zdbs, String dsfzdbs, String bdczdz) {
        //数据归属地区进行对照
        BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
        bdcZdDsfzdgxDO.setZdbs(zdbs);
        bdcZdDsfzdgxDO.setBdczdz(bdczdz);
        bdcZdDsfzdgxDO.setDsfxtbs(dsfzdbs);
        BdcZdDsfzdgxDO result = zdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
        LOGGER.info("---第三方字典返回值:{},查询参数:{}", result, bdcZdDsfzdgxDO);
        if (null != result && StringUtils.isNotBlank(result.getDsfzdz())) {
            return result.getDsfzdz();
        }
        return bdczdz;
    }

    /**
     * @param fileDownloadVo 政融支付平台 对账文件下载入参
     * @return SftjDzdResponseDTO 税费统缴对账单返回值
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @date 2022/10/17
     * @description 对账文件下载
     **/
    public SftjDzdResponseDTO dzwjxz(FileDownloadVo fileDownloadVo) {
        fileDownloadVo.setIttPartyTms(DateUtils.formateTime(new Date(), DateUtils.DATE_FORMATYMDHMSF));
        fileDownloadVo.setIttPartyJrnlNo(UUIDGenerator.generate16());
        LOGGER.info("接收到对账文件下载参数{}", fileDownloadVo);

        Set<String> set = new HashSet<>();
        String dzdResponseStr = sendRequestToZr(fileDownloadVo, set, set, dzdxzUrl);
        if (StringUtils.isNotBlank(dzdResponseStr)) {
            return JSONObject.parseObject(dzdResponseStr, SftjDzdResponseDTO.class);
        }
        return null;

    }

    /**
     * @param
     * @param jsonObject
     * @return cn.gtmap.realestate.exchange.core.dto.zrpay.jfqr.PayResultNoticeDTO
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/11 20:14
     * @description 对账文件推送
     **/
    public PayResultNoticeDTO dzwjts(JSONObject jsonObject) {
        Set<String> setRe = new HashSet<>();
        String result =  EncryUtil.decryption(jsonObject.getString("Tprt_Parm"), setRe, jsonObject.getString("Bsn_Data"), antherKey, publicKey);
        PayResultNoticeDTO payResultNoticeDTO = new PayResultNoticeDTO();
        payResultNoticeDTO.setRcvStCd("01");
        if (StringUtils.isNotBlank(result)) {
            VerifyFileNoticeQo verifyFileNoticeQo = JSONObject.parseObject(result, VerifyFileNoticeQo.class);
            LOGGER.info("接收政融对账文件通知入参{}", verifyFileNoticeQo);
            payResultNoticeDTO.setRcvStCd("00");
            payResultNoticeDTO.setRecTm(DateUtil.formatDate("yyyyMMddhhmmss"));
        }
        return payResultNoticeDTO;
    }

    /**
     * @param
     * @param posPaySuccessQo
     * @return void
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/11 21:31
     * @description pos支付成功推送政融
     **/
    public PosPaySuccessVO posPaySuccess(PosPaySuccessQO posPaySuccessQo) {
        if(StringUtils.isNotBlank(shdm)){
            posPaySuccessQo.setMrchCd(shdm);
        }
        if(StringUtils.isNotBlank(zdh)){
            posPaySuccessQo.setTmnlNo(zdh);
        }
        LOGGER.info("pos支付成功推送政融参数{}", posPaySuccessQo);
        // 参与签名
        Set<String> set = new HashSet<>();
        String result = sendRequestToZr(posPaySuccessQo, set, set, posPaySuccessUrl);
        if (StringUtils.isNotBlank(result)) {
            return JSONObject.parseObject(result, PosPaySuccessVO.class);
        }
        return null;
    }

    /**
     * 线上退款申请接口
     * @param zrTksqRequest 政融支付平台 退款申请请求参数
     * @return 退款申请请求返回值
     */
    public ZrTksqResponse xstksq(ZrTksqRequest zrTksqRequest){
        LOGGER.info("线上退款申请接口参数{}", zrTksqRequest);
        // 参与签名
        Set<String> requestSet = new HashSet<>();
        requestSet.add("LIST1");
        Set<String> responseSet = new HashSet<>();
        requestSet.add("LIST");

        String result = sendRequestToZr(zrTksqRequest, requestSet, responseSet, xstksqUrl);
        if (StringUtils.isNotBlank(result)) {

            ZrTksqResponse zrTksqResponse = JSONObject.parseObject(result, ZrTksqResponse.class);
            // 进行字典值对照，对照支付状态、订单状态
            if (StringUtils.isNotBlank(zrTksqResponse.getRfndStCd())) {
                String tkzt_zdz = getDsfZdDzxx("BDC_ZRZF_TKZT","rfndStCd", zrTksqResponse.getRfndStCd());
                zrTksqResponse.setRfndStCd(tkzt_zdz);
            }
            return JSONObject.parseObject(result, ZrTksqResponse.class);
        }
        return null;
    }

    /**
     * 线上退款结果查询接口
     * @param zrTkjgcxRequest 政融支付平台 退款结果查询请求值
     * @return 退款结果查询返回值
     */
    public ZrTkjgcxResponse xstkjgcx(ZrTkjgcxRequest zrTkjgcxRequest){
        LOGGER.info("线上退款结果查询接口参数{}", zrTkjgcxRequest);
        // 参与签名
        Set<String> set = new HashSet<>();
        Set<String> responseSet = new HashSet<>();
        responseSet.add("LIST1");
        String result = sendRequestToZr(zrTkjgcxRequest, set, responseSet, xstkjgcxUrl);
        if (StringUtils.isNotBlank(result)) {
            return JSONObject.parseObject(result, ZrTkjgcxResponse.class);
        }
        return null;
    }


    /**
     * @param
     * @param t 参数对象
     * @param requestSet 请求值签名参数
     * @param responseSet 返回值签名参数
     * @param urlPath 请求地址
     * @return java.lang.String
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/11 16:17
     * @description 请求政融支付
     **/
    private <T> String sendRequestToZr(T t, Set<String> requestSet, Set<String> responseSet,String urlPath) {
        // 加密签名
        String[] ss = EncryUtil.newRequestZrzfDncryptParam(JSON.toJSONString(t), requestSet, antherKey, publicKey);
        // 封装发送实体
        BaseVo baseVo = new BaseVo();
        // 发起方渠道编号  即政融分配的多实体
        baseVo.setIttPartySysId(fqfxtbh);
        baseVo.setBsnData(ss[0]);
        baseVo.setTprtParm(ss[1]);

        LOGGER.info("政融支付加密后请求参数：{}", JSON.toJSONString(baseVo));
        // 发送请求下单
        String responseStr = HttpUtil.doJsonPost(urlPath, JSON.toJSONString(baseVo));
        LOGGER.info("政融支付请求地址：{}，返回结果为：{}", urlPath, responseStr);

        // 解密验签
        JSONObject jsonObject = JSON.parseObject(responseStr);
        return EncryUtil.decryption(jsonObject.getString("Tprt_Parm"), responseSet, jsonObject.getString("Bsn_Data"), antherKey, publicKey);
    }

}
