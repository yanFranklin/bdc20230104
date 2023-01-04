package cn.gtmap.realestate.common.core.dto.exchange.sfxx;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
 * @version 2022/05/25
 * @description
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class JfqrResponseDataDTO {

    // -业务唯一码
    @XmlElement(name = "BUSINESS_ID")
    private String businessId;

    // 缴款码
    @XmlElement(name = "BUSINESSNUMBER")
    private String businessNumber;

    // 缴款日期
    @XmlElement(name = "PAY_DATE")
    private String payDate;

    // 缴费成功标志 0-表示未缴费，1-表示缴费成功
    @XmlElement(name = "IS_SUCCESS")
    private String isSuccess;

    // 作废说明
    @XmlElement(name = "STATUS_MC")
    private String statusMc;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getStatusMc() {
        return statusMc;
    }

    public void setStatusMc(String statusMc) {
        this.statusMc = statusMc;
    }

    @Override
    public String toString() {
        return "JfqrResponseDataDTo{" +
                "businessId='" + businessId + '\'' +
                ", businessNumber='" + businessNumber + '\'' +
                ", payDate='" + payDate + '\'' +
                ", isSuccess='" + isSuccess + '\'' +
                ", statusMc='" + statusMc + '\'' +
                '}';
    }
}
