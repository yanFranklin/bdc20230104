package cn.gtmap.realestate.etl.core.qo.ggxypt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/30
 * @description 江苏公共信用信息平台 公用请求体
 */
@ApiModel(description = "江苏公共信用信息平台 公用请求体")
public class GgxyptCommonQO {


    /**
     * publicKey : 040469D4B903EF4985E932A9ABE5D33A35FA38CDC4DFE83B94B2D1B4BF6F0B1115CBAF4CA9BE42E6444DE8F4AC637B3BFBFE695D6E9634DFD5D5E900916C4AAD6C
     * key : 0481833F9ADB55D363637FC2A086D83F7561790032F53EDCD23AB13CC514E23DFD03387776CDBC50AA183CECC352365410874D2FA4552E74795267726BBDE4EA6E7BE5C8AB67A1F85C4485FC9B392181B6731934B3DCBE4E3125273046ABA3953AFF98EDE2FE66F50B53DAC1724E8153CF
     * signatureData : 30460221009E8FE9176F11BD1C17378670AB0EC3FE3CCFFED5B2D2C298E2E4D88F2076410E022100F5CAF9157763C350B3D2B5EEAB0AE1B4A4818EB6155FDD45F2ECB096F2BD776D
     * requestData : KZzNz0tmAQYTK7fV69rmUY0g2sVTiFEJyPNttEjWsCUnN2/YcNEowQupla86VwNYNBLSveVOMWyUUJ4Lz+lJrgRebNftP//O00+ABBmdU8ZzDZxYxrsZH6Qi26js/bP8PlUW8G9OZD1x0rjbWWkN6w==
     */

    @ApiModelProperty(value = "公钥")
    private String publicKey;
    @ApiModelProperty(value = "请求的key")
    private String key;
    @ApiModelProperty(value = "签名数据")
    private String signatureData;
    @ApiModelProperty(value = "请求数据")
    private String requestData;

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSignatureData() {
        return signatureData;
    }

    public void setSignatureData(String signatureData) {
        this.signatureData = signatureData;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    @Override
    public String toString() {
        return "GgxyptCommonQO{" +
                "publicKey='" + publicKey + '\'' +
                ", key='" + key + '\'' +
                ", signatureData='" + signatureData + '\'' +
                ", requestData='" + requestData + '\'' +
                '}';
    }
}
