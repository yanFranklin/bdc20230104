package cn.gtmap.realestate.certificate.core.service;



/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/5/20 数字签名
 */
public interface BdcDzzzDigitalService {

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param sealName
     * @param jsonString
     * @return
     * @description SM2加密
     */
    String sign(String sealName, String jsonString);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param sealName
     * @param signData
     * @param jsonString
     * @return
     * @description SM2解密
     */
    boolean verifySign(String sealName, String signData, String jsonString);

    /**
     *
     * @param sealName
     * @param publicKeyStr
     * @description 保存或更新数字签名
     */
    void insertOrUpdateSignature(String sealName, String publicKeyStr, String privateKeyStr);

}
