package cn.gtmap.realestate.common.core.dto.exchange.hefei.dzzz.request;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-05-06
 * @description 合肥电子证照请求制证接口请求参数
 */
public class HefeiDzzzRequestDTO {

    private String slbh;
    // 证照目录编码
    @NotBlank
    private String contentCode;

    // 持证者id
    @NotBlank
    private String ownerId;

    // 持证者姓名
    @NotBlank
    private String ownerName;

    // 有效期开始
    private String infoValidityBegin;

    // 有效期结束
    private String infoValidityEnd;

    // 照面Json数据
    @NotBlank
    private String data;

    // 生成下载地址下载文件的格式(ofd、png)
    private String format;

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getContentCode() {
        return contentCode;
    }

    public void setContentCode(String contentCode) {
        this.contentCode = contentCode;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getInfoValidityBegin() {
        return infoValidityBegin;
    }

    public void setInfoValidityBegin(String infoValidityBegin) {
        this.infoValidityBegin = infoValidityBegin;
    }

    public String getInfoValidityEnd() {
        return infoValidityEnd;
    }

    public void setInfoValidityEnd(String infoValidityEnd) {
        this.infoValidityEnd = infoValidityEnd;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return "HefeiDzzzRequestDTO{" +
                "contentCode='" + contentCode + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", infoValidityBegin='" + infoValidityBegin + '\'' +
                ", infoValidityEnd='" + infoValidityEnd + '\'' +
                ", data='" + data + '\'' +
                ", format='" + format + '\'' +
                '}';
    }
}
