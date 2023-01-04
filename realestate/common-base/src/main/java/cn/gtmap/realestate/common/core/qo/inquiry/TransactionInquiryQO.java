package cn.gtmap.realestate.common.core.qo.inquiry;

import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 2020/06/23,1.0
 * @description
 */
public class TransactionInquiryQO {

    @ApiModelProperty(value = "姓名")
    private String xm;

    @ApiModelProperty(value = "证件号码")
    private String zjhm;

    @ApiModelProperty(value = "产权证号")
    private String cqzh;

    @ApiModelProperty(value = "合同编号")
    private String htbh;

    @ApiModelProperty(value = "坐落")
    private String zl;

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }

    public String getCqzh() {
        return cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
    }

    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    @Override
    public String toString() {
        return "TransactionInquiryQO{" +
                "xm='" + xm + '\'' +
                ", zjhm='" + zjhm + '\'' +
                ", cqzh='" + cqzh + '\'' +
                ", htbh='" + htbh + '\'' +
                ", zl='" + zl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof TransactionInquiryQO)){
            return false;
        }
        TransactionInquiryQO that = (TransactionInquiryQO) o;
        return Objects.equals(xm, that.xm) &&
                Objects.equals(zjhm, that.zjhm) &&
                Objects.equals(cqzh, that.cqzh) &&
                Objects.equals(htbh, that.htbh) &&
                Objects.equals(zl, that.zl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xm, zjhm, cqzh, htbh, zl);
    }
}
