package cn.gtmap.realestate.exchange.core.dto.sjrpt;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0  2022-09-28
 * @description 省金融平台 请求体body参数
 */
public class OrgReqData {

    /**
     * 加密数据
     */
    private String encData;

    /**
     * 加密后密钥
     */
    private String encKey;

    /**
     * 偏移量
     */
    private String iv;

    /**
     * 签名
     */
    private String signature;


    /**
     * 客户端公钥
     */
    private String qPubKey;

    /**
     * 解密数据
     */
    private String decData;

    /**
     * 解密后密钥
     */
    private String decKey;


    public String getEncData() {
        return encData;
    }

    public void setEncData(String encData) {
        this.encData = encData;
    }

    public String getEncKey() {
        return encKey;
    }

    public void setEncKey(String encKey) {
        this.encKey = encKey;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getqPubKey() {
        return qPubKey;
    }

    public void setqPubKey(String qPubKey) {
        this.qPubKey = qPubKey;
    }

    public String getDecData() {
        return decData;
    }

    public void setDecData(String decData) {
        this.decData = decData;
    }

    public String getDecKey() {
        return decKey;
    }

    public void setDecKey(String decKey) {
        this.decKey = decKey;
    }

    @Override
    public String toString() {
        return "OrgReqData{" +
                "encData='" + encData + '\'' +
                ", encKey='" + encKey + '\'' +
                ", iv='" + iv + '\'' +
                ", signature='" + signature + '\'' +
                ", qPubKey='" + qPubKey + '\'' +
                ", decData='" + decData + '\'' +
                ", decKey='" + decKey + '\'' +
                '}';
    }
}
