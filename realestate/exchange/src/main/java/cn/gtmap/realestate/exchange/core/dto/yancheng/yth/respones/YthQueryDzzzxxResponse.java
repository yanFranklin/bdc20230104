package cn.gtmap.realestate.exchange.core.dto.yancheng.yth.respones;

import java.io.Serializable;

/**
 * 盐城_一体化查询电子证照信息接口请求参数
 */
public class YthQueryDzzzxxResponse implements Serializable {

    private static final long serialVersionUID = -4744678578318492555L;
    /**
     * 不动产登记单元代码
     */
    private String bdcdjdydm;
    /**
     * 照片转码参数
     */
    private String base64;
    /**
     * 文件下载地址
     */
    private String fileUrl;
    /**
     * 证照文件格式
     */
    private String fileFormat;
    /**
     * 证照类型名称
     */
    private String certificateType;
    /**
     * 房屋坐落
     */
    private String fwzl;
    /**
     * 证照编码
     */
    private String zzbm;

    public String getBdcdjdydm() {
        return bdcdjdydm;
    }

    public void setBdcdjdydm(String bdcdjdydm) {
        this.bdcdjdydm = bdcdjdydm;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getFwzl() {
        return fwzl;
    }

    public void setFwzl(String fwzl) {
        this.fwzl = fwzl;
    }

    public String getZzbm() {
        return zzbm;
    }

    public void setZzbm(String zzbm) {
        this.zzbm = zzbm;
    }

    public static final class YthQueryDzzzxxResponseBuilder {
        private String bdcdjdydm;
        private String base64;
        private String fileUrl;
        private String fileFormat;
        private String certificateType;
        private String fwzl;
        private String zzbm;

        private YthQueryDzzzxxResponseBuilder() {
        }

        public static YthQueryDzzzxxResponseBuilder anYthQueryDzzzxxResponse() {
            return new YthQueryDzzzxxResponseBuilder();
        }

        public YthQueryDzzzxxResponseBuilder withBdcdjdydm(String bdcdjdydm) {
            this.bdcdjdydm = bdcdjdydm;
            return this;
        }

        public YthQueryDzzzxxResponseBuilder withBase64(String base64) {
            this.base64 = base64;
            return this;
        }

        public YthQueryDzzzxxResponseBuilder withFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
            return this;
        }

        public YthQueryDzzzxxResponseBuilder withFileFormat(String fileFormat) {
            this.fileFormat = fileFormat;
            return this;
        }

        public YthQueryDzzzxxResponseBuilder withCertificateType(String certificateType) {
            this.certificateType = certificateType;
            return this;
        }

        public YthQueryDzzzxxResponseBuilder withFwzl(String fwzl) {
            this.fwzl = fwzl;
            return this;
        }

        public YthQueryDzzzxxResponseBuilder withZzbm(String zzbm) {
            this.zzbm = zzbm;
            return this;
        }

        public YthQueryDzzzxxResponse build() {
            YthQueryDzzzxxResponse ythQueryDzzzxxResponse = new YthQueryDzzzxxResponse();
            ythQueryDzzzxxResponse.setBdcdjdydm(bdcdjdydm);
            ythQueryDzzzxxResponse.setBase64(base64);
            ythQueryDzzzxxResponse.setFileUrl(fileUrl);
            ythQueryDzzzxxResponse.setFileFormat(fileFormat);
            ythQueryDzzzxxResponse.setCertificateType(certificateType);
            ythQueryDzzzxxResponse.setFwzl(fwzl);
            ythQueryDzzzxxResponse.setZzbm(zzbm);
            return ythQueryDzzzxxResponse;
        }
    }
}
