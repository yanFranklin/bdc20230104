package cn.gtmap.realestate.common.core.dto.exchange.nantong.fssr;

import javax.xml.bind.annotation.*;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @Date 2022/07/11
 * @description 换开电子发票Voucher
 */
@XmlType(propOrder = {"admDivCode", "setYear", "coCode", "payCode", "memo", "mobilePhone", "payerCreditCode","hold1","hold2"})
@XmlRootElement(name = "Voucher")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class HkDzfpVoucher {

    /**
     * AdmDivCode	行政区划	String	[1, 6]		M
     * SetYear	年度	String	4		M
     * CoCode	执收单位编码	String	[1,30]	这里的执收单位编码为单位在财政非税收入系统中的单位代码	M
     * PayCode	非税缴款书编号	String	[1,20]	这里的非税缴款书编号为收入平台产生的标志缴款信息的唯一编号	M
     * Memo	备注	GBString	[0,200]	电子票上显示的备注信息	O
     * MobilePhone	缴款人手机号	String	[1,30]	可用于接收电子票通知短信	0
     * PayerCreditCode	缴款人统一社会信用代码	String	[0,100]	可用于查询电子票	0
     * Hold1	预留字段	GBString	[0,100]		0
     * Hold2	预留字段	GBString	[0,100]		O
     */
    private String AdmDivCode;
    private String SetYear;
    private String CoCode;
    private String PayCode;
    private String Memo;
    private String MobilePhone;
    private String PayerCreditCode;
    private String Hold1;
    private String Hold2;

    @XmlElement(name = "AdmDivCode")
    public String getAdmDivCode() {
        return AdmDivCode;
    }

    public void setAdmDivCode(String admDivCode) {
        AdmDivCode = admDivCode;
    }

    @XmlElement(name = "SetYear")
    public String getSetYear() {
        return SetYear;
    }

    public void setSetYear(String setYear) {
        SetYear = setYear;
    }

    @XmlElement(name = "CoCode")
    public String getCoCode() {
        return CoCode;
    }

    public void setCoCode(String coCode) {
        CoCode = coCode;
    }

    @XmlElement(name = "PayCode")
    public String getPayCode() {
        return PayCode;
    }

    public void setPayCode(String payCode) {
        PayCode = payCode;
    }

    @XmlElement(name = "Memo")
    public String getMemo() {
        return Memo;
    }

    public void setMemo(String memo) {
        Memo = memo;
    }

    @XmlElement(name = "MobilePhone")
    public String getMobilePhone() {
        return MobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        MobilePhone = mobilePhone;
    }

    @XmlElement(name = "PayerCreditCode")
    public String getPayerCreditCode() {
        return PayerCreditCode;
    }

    public void setPayerCreditCode(String payerCreditCode) {
        PayerCreditCode = payerCreditCode;
    }

    @XmlElement(name = "Hold1")
    public String getHold1() {
        return Hold1;
    }

    public void setHold1(String hold1) {
        Hold1 = hold1;
    }

    @XmlElement(name = "Hold2")
    public String getHold2() {
        return Hold2;
    }

    public void setHold2(String hold2) {
        Hold2 = hold2;
    }

    @Override
    public String toString() {
        return "HkDzfpVoucher{" +
                "AdmDivCode='" + AdmDivCode + '\'' +
                ", SetYear='" + SetYear + '\'' +
                ", CoCode='" + CoCode + '\'' +
                ", PayCode='" + PayCode + '\'' +
                ", Memo='" + Memo + '\'' +
                ", MobilePhone='" + MobilePhone + '\'' +
                ", PayerCreditCode='" + PayerCreditCode + '\'' +
                ", Hold1='" + Hold1 + '\'' +
                ", Hold2='" + Hold2 + '\'' +
                '}';
    }
}
