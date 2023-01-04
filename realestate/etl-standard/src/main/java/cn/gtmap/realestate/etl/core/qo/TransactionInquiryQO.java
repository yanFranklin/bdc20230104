package cn.gtmap.realestate.etl.core.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
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

    @Override
    public String toString() {
        return "TransactionInquiryQO{" +
                "xm='" + xm + '\'' +
                ", zjhm='" + zjhm + '\'' +
                ", cqzh='" + cqzh + '\'' +
                ", htbh='" + htbh + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionInquiryQO)) {
            return false;
        }
        TransactionInquiryQO that = (TransactionInquiryQO) o;
        return Objects.equals(getXm(), that.getXm()) &&
                Objects.equals(getZjhm(), that.getZjhm()) &&
                Objects.equals(getCqzh(), that.getCqzh()) &&
                Objects.equals(getHtbh(), that.getHtbh());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getXm(), getZjhm(), getCqzh(), getHtbh());
    }
}
