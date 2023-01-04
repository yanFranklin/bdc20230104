package cn.gtmap.realestate.certificate.web.maintain;

import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzSignatureMapper;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.dzzzgx.sign.DzzzSignRequestModel;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.model.encrypt.SM2KeyPair;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzDigitalService;
import cn.gtmap.realestate.certificate.core.support.annotations.CheckToken;
import cn.gtmap.realestate.certificate.util.*;
import cn.gtmap.realestate.certificate.web.main.DzzzController;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/18
 */
@Controller
@Api("电子证照数字签名")
@RequestMapping(value = "/realestate-e-certificate/rest/v1.0/sign")
public class BdcDzzzSigntureController extends DzzzController {

    @Autowired
    private BdcDzzzDigitalService bdcDzzzDigitalService;
    @Autowired
    private BdcDzzzSignatureMapper bdcDzzzSignatureMapper;

    /*@ResponseBody
    @RequestMapping(value = "/keyExchangeTwoStep", method = RequestMethod.POST)
    @ApiOperation(value = "电子证照秘钥协商第二步", notes = "电子证照秘钥协商第二步")
    public DzzzResponseModel keyExchangeTwoStep(@RequestBody String jsonString) {
        Map<String, Object> data = new HashMap<>(4);
        String raHexStr = null;
        String zaHexStr = null;
        String publicKeyHexStr = null;
        JSONObject jSONObject = JSON.parseObject(jsonString);
        if (null != jSONObject) {
            *//*raHexStr = jSONObject.getString("raHexStr");
            zaHexStr = jSONObject.getZaHexStr();
            publicKeyHexStr = jSONObject.getPublicKeyHexStr();*//*
        }

        if (StringUtils.isNotBlank(raHexStr) && StringUtils.isNotBlank(zaHexStr)&& StringUtils.isNotBlank(publicKeyHexStr)) {
            SM2.KeyExchange bKeyExchange = new SM2.KeyExchange(SM2Util.SM2_KEY_ID,SM2Util.KEYPAIR);
            byte[] raArr = DataUtil.hexStringToBytes(raHexStr);
            byte[] zaArr = DataUtil.hexStringToBytes(zaHexStr);
            byte[] publicKeyStrArr = DataUtil.hexStringToBytes(publicKeyHexStr);
            if (null != raArr) {
                ECPoint RA = SM2.curve.decodePoint(raArr);
                ECPoint publicKey = SM2.curve.decodePoint(publicKeyStrArr);
                if (null != RA) {
                    SM2.TransportEntity entity2 = bKeyExchange.keyExchange_2(new SM2.TransportEntity(RA.getEncoded(false), null, zaArr, publicKey));
                    if (null != entity2) {
                        data.put("RB", DataUtil.encodeHexString(entity2.R));
                        data.put("SB", DataUtil.encodeHexString(entity2.S));
                        data.put("ZB", DataUtil.encodeHexString(entity2.Z));
                        data.put("PB", DataUtil.encodeHexString(entity2.K));
                        return bdcDzzzService.dzzzResponseSuccess(data);
                    }
                }
            }
        }
        data.put("info", "TwoStep 协商失败!");
        return bdcDzzzService.dzzzResponseFalse(ResponseEnum.FALSE.getMsg(), data);
    }

    @ResponseBody
    @RequestMapping(value = "/keyExchangeFourStep", method = RequestMethod.POST)
    @ApiOperation(value = "电子证照秘钥协商第四步", notes = "电子证照秘钥协商第四步")
    public DzzzResponseModel keyExchangeFourStep(@RequestBody String jsonString) {
        Map<String, Object> data = new HashMap<>(1);
        String aIdStr = null;
        String raHexStr = null;
        String zaHexStr = null;
        String publicKeyHexStr = null;
        JSONObject jSONObject = JSON.parseObject(jsonString);
        if (null != jSONObject) {
            aIdStr = jSONObject.getString("aIdStr");
            raHexStr = jSONObject.getString("raHexStr");
            zaHexStr = jSONObject.getString("zaHexStr");
            publicKeyHexStr = jSONObject.getString("publicKeyHexStr");
        }
        if (StringUtils.isNotBlank(aIdStr) && StringUtils.isNotBlank(raHexStr) && StringUtils.isNotBlank(zaHexStr)&& StringUtils.isNotBlank(publicKeyHexStr)) {
            SM2.KeyExchange bKeyExchange = new SM2.KeyExchange(SM2Util.SM2_KEY_ID,SM2Util.KEYPAIR);
            byte[] raArr = DataUtil.hexStringToBytes(raHexStr);
            byte[] zaArr = DataUtil.hexStringToBytes(zaHexStr);
            byte[] publicKeyStrArr = DataUtil.hexStringToBytes(publicKeyHexStr);
            if (null != raArr) {
                ECPoint RA = SM2.curve.decodePoint(raArr);
                ECPoint publicKey = SM2.curve.decodePoint(publicKeyStrArr);
                if (null != RA) {
                    boolean exBool = bKeyExchange.keyExchange_4(new SM2.TransportEntity(RA.getEncoded(false), null,zaArr,publicKey));
                    if (exBool) {
                        //signatureService.insertOrUpdateSignature(aIdStr, publicKeyHexStr);
                        data.put("info", "协商成功！");
                        return bdcDzzzService.dzzzResponseSuccess(data);
                    }
                }
            }
        }
        data.put("info", "协商失败！");
        return bdcDzzzService.dzzzResponseFalse(ResponseEnum.FALSE.getMsg(), data);
    }*/

    @ResponseBody
    @RequestMapping(value = "/sign", method = RequestMethod.POST)
    @ApiOperation(value = "电子证照数字签名", notes = "电子证照数字签名")
    @CheckToken
    public DzzzResponseModel sign(HttpServletRequest request, @RequestBody String jsonString) {
        String sign = null;
        Map<String, Object> data = new HashMap<>(1);
        if (StringUtils.isNotBlank(jsonString)) {
            sign = bdcDzzzDigitalService.sign((String)request.getAttribute(Constants.YYMC), jsonString);
            if (StringUtils.isNotBlank(sign)) {
                data.put("sign", sign);
                return bdcDzzzService.dzzzResponseSuccess(data);
            }
        }
        data.put("info", "数字签名生成失败！");
        return bdcDzzzService.dzzzResponseFalse(ResponseEnum.FALSE.getMsg(), data);
    }

    @ResponseBody
    @RequestMapping(value = "/getPublicKey", method = RequestMethod.POST)
    @ApiOperation(value = "电子证照分配公钥", notes = "电子证照分配公钥")
    @CheckToken
    public DzzzResponseModel getPublicKey(HttpServletRequest request) {
        Map<String, Object> data = new HashMap<>(1);
        SM2KeyPair keyPair = SM2Util.getKeyPair();
        if (null != keyPair) {
            String publicKeyStr = Base64Util.encodeByteToBase64Str(keyPair.getPublicKey().getEncoded(false));
            String privateKeyStr = Base64Util.encodeByteToBase64Str(keyPair.getPrivateKey().toByteArray());
            bdcDzzzDigitalService.insertOrUpdateSignature((String)request.getAttribute(Constants.YYMC), publicKeyStr,privateKeyStr);
            data.put("publicKeyHexStr", publicKeyStr);
            return bdcDzzzService.dzzzResponseSuccess(data);
        }
        data.put("info", "分配公钥失败！");
        return bdcDzzzService.dzzzResponseFalse(ResponseEnum.FALSE.getMsg(), data);
    }

    @ResponseBody
    @RequestMapping(value = "/verifySign", method = RequestMethod.POST)
    @ApiOperation(value = "电子证照数字签名验签", notes = "电子证照数字签名验签")
    @CheckToken
    public DzzzResponseModel verifySign(HttpServletRequest request, @RequestBody String jsonString) {
        DzzzSignRequestModel dzzzRequestModel = JSON.parseObject(jsonString, DzzzSignRequestModel.class);
        if (null == dzzzRequestModel) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), null);
        }
        boolean signBool = bdcDzzzDigitalService.verifySign((String)request.getAttribute(Constants.YYMC), dzzzRequestModel.getHead().getSign(), jsonString);
        if (signBool) {
            return bdcDzzzService.dzzzResponseSuccess(null);
        }
        return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_SIGNTURE_ERROR.getCode(), null);
    }
}
