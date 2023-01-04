package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzSignatureMapper;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzSignatureDo;
import cn.gtmap.realestate.certificate.core.service.*;
import cn.gtmap.realestate.certificate.util.DateUtil;
import cn.gtmap.realestate.certificate.util.SM2Util;
import cn.gtmap.realestate.certificate.util.StringUtil;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/28
 */
@Service
public class BdcDzzzDigitalServiceImpl implements BdcDzzzDigitalService {
    private final Logger logger = LoggerFactory.getLogger(BdcDzzzDigitalServiceImpl.class);
    @Autowired
    private BdcDzzzSignatureMapper bdcDzzzSignatureMapper;

    @Override
    public String sign(String sealName, String jsonString) {
        String signStr = null;
        BdcDzzzSignatureDo bdcDzzzSignatureDo = bdcDzzzSignatureMapper.querySignatureBySealName(sealName);
        if (null != bdcDzzzSignatureDo) {
            String sealPublicKey = bdcDzzzSignatureDo.getSealPublicKey();
            if (StringUtils.isNotBlank(sealPublicKey) && StringUtils.isNotBlank(jsonString)) {
                signStr = SM2Util.encrypt(jsonString, sealPublicKey);
            }
        }
        return signStr;
    }

    @Override
    public boolean verifySign(String sealName, String signData, String jsonString) {
        BdcDzzzSignatureDo bdcDzzzSignatureDo = bdcDzzzSignatureMapper.querySignatureBySealName(sealName);
        if (null != bdcDzzzSignatureDo) {
            String sealPrivateKey = bdcDzzzSignatureDo.getSealPrivateKey();
            if (StringUtils.isNoneBlank(sealPrivateKey, signData, jsonString)) {
                try {
                    String signStr = SM2Util.decrypt(signData, sealPrivateKey);
                    String str2 = StringUtil.substringZzpdfJson(jsonString);
                    if (StringUtils.equals(signStr, str2)) {
                        return true;
                    }
                } catch (Exception e) {
                    logger.info("验签异常，签名为非法值：{}，请求时间：{}", signData, DateUtil.now());
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public void insertOrUpdateSignature(String sealName, String publicKeyStr, String privateKeyStr) {
        BdcDzzzSignatureDo bdcDzzzSignatureDo = bdcDzzzSignatureMapper.querySignatureBySealName(sealName);
        if (null != bdcDzzzSignatureDo) {
            bdcDzzzSignatureDo.setSealPublicKey(publicKeyStr);
            bdcDzzzSignatureDo.setSealPrivateKey(privateKeyStr);
            bdcDzzzSignatureMapper.updateSignatureBySealName(bdcDzzzSignatureDo);
        } else {
            BdcDzzzSignatureDo bdcDzzzSignature = new BdcDzzzSignatureDo();
            bdcDzzzSignature.setSealId(UUIDGenerator.generate());
            bdcDzzzSignature.setSealName(sealName);
            bdcDzzzSignature.setSealPublicKey(publicKeyStr);
            bdcDzzzSignature.setSealCjsj(DateUtil.now());
            bdcDzzzSignature.setSealPrivateKey(privateKeyStr);
            bdcDzzzSignatureMapper.insertSignature(bdcDzzzSignature);
        }
    }
}
