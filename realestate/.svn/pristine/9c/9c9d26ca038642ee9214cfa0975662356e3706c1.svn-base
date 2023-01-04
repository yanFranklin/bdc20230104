package cn.gtmap.realestate.common.core.dto.certificate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2020/2/27
 * @description 电子证照配置DTO
 */
@ApiModel(value = "BdcEcertificateConfigDTO", description = "BdcEcertificateConfigDTO")
public class BdcEcertificateConfigDTO {

    /**
     * 电子证照的请求地址
     */
    @ApiModelProperty(value = "电子证照的请求地址")
    private String eCerticifatePath;
    /**
     * 电子证照版本
     */
    @ApiModelProperty(value = "电子证照版本")
    private String version;

    /**
     * 获取token的应用名称
     */
    @ApiModelProperty(value = "获取token的应用名称")
    private String tokenYymc;

    /**
     * 公钥
     */
    @ApiModelProperty(value = "公钥")
    private String publicKey;

    /**
     * 私钥
     */
    @ApiModelProperty(value = "私钥")
    private String privateKey;

    /**
     * 港澳台证件种类代码
     */
    @ApiModelProperty(value = "港澳台证件种类代码")
    private Integer zjzldm_HMT;

    /**
     * 上传storage的根方式.默认为eCertificateId
     */
    @ApiModelProperty(value = "上传storage的根方式.默认为eCertificateId")
    private String uploadStorageRoot;

    public String geteCerticifatePath() {
        return eCerticifatePath;
    }

    public void seteCerticifatePath(String eCerticifatePath) {
        this.eCerticifatePath = eCerticifatePath;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTokenYymc() {
        return tokenYymc;
    }

    public void setTokenYymc(String tokenYymc) {
        this.tokenYymc = tokenYymc;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public Integer getZjzldm_HMT() {
        return zjzldm_HMT;
    }

    public void setZjzldm_HMT(Integer zjzldm_HMT) {
        this.zjzldm_HMT = zjzldm_HMT;
    }

    public String getUploadStorageRoot() {
        return uploadStorageRoot;
    }

    public void setUploadStorageRoot(String uploadStorageRoot) {
        this.uploadStorageRoot = uploadStorageRoot;
    }

    @Override
    public String toString() {
        return "BdcEcertificateConfigDTO{" +
                "eCerticifatePath='" + eCerticifatePath + '\'' +
                ", version='" + version + '\'' +
                ", tokenYymc='" + tokenYymc + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", privateKey='" + privateKey + '\'' +
                ", zjzldm_HMT=" + zjzldm_HMT +
                ", uploadStorageRoot='" + uploadStorageRoot + '\'' +
                '}';
    }
}
