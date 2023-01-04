package cn.gtmap.realestate.etl.core.qo;

import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 2021/07/27,1.0
 * @description
 */
public class TransactionInquiryDyxxQO {

    @ApiModelProperty(value = "产权登记编号")
    private String cqdjbh;

    @ApiModelProperty(value = "不动产权证明号")
    private String bdcqzmh;

    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;

    public String getCqdjbh() {
        return cqdjbh;
    }

    public void setCqdjbh(String cqdjbh) {
        this.cqdjbh = cqdjbh;
    }

    public String getBdcqzmh() {
        return bdcqzmh;
    }

    public void setBdcqzmh(String bdcqzmh) {
        this.bdcqzmh = bdcqzmh;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    @Override
    public String toString() {
        return "TransactionInquiryDyxxQO{" +
                "cqdjbh='" + cqdjbh + '\'' +
                ", bdcqzmh='" + bdcqzmh + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                '}';
    }
}
