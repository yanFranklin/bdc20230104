package cn.gtmap.realestate.common.core.dto.exchange.nantong.fscz;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2021/12/1
 * @description 8.2.2财政交易确认报文（5996）
 */
@XmlRootElement(name = "Voucher")
@XmlAccessorType(XmlAccessType.FIELD)
public class CzjyQrbwResponse {
    @XmlElement(name = "CfmDate")
    private String CfmDate;
    @XmlElement(name = "PayCode")
    private String PayCode;
    @XmlElement(name = "CfmTime")
    private String CfmTime;
    @XmlElement(name = "Amt")
    private Double Amt;
    @XmlElement(name = "BankOutlet")
    private String BankOutlet;
    @XmlElement(name = "Operator")
    private String Operator;
    @XmlElement(name = "PayMode")
    private String PayMode;
    @XmlElement(name = "TraNoBus")
    private String TraNoBus;
    @XmlElement(name = "PayerName")
    private String PayerName;
    @XmlElement(name = "PayerAcct")
    private String PayerAcct;
    @XmlElement(name = "PayerOpBk")
    private String PayerOpBk;
    @XmlElement(name = "PayerOpBkNo")
    private String PayerOpBkNo;
    @XmlElement(name = "RecAcctType")
    private String RecAcctType;
    @XmlElement(name = "RecAcct")
    private String RecAcct;
    @XmlElement(name = "Hold1")
    private String Hold1;
    @XmlElement(name = "Hold2")
    private String Hold2;

    public String getCfmDate() {
        return CfmDate;
    }

    public void setCfmDate(String cfmDate) {
        CfmDate = cfmDate;
    }

    public String getPayCode() {
        return PayCode;
    }

    public void setPayCode(String payCode) {
        PayCode = payCode;
    }

    public String getCfmTime() {
        return CfmTime;
    }

    public void setCfmTime(String cfmTime) {
        CfmTime = cfmTime;
    }

    public Double getAmt() {
        return Amt;
    }

    public void setAmt(Double amt) {
        Amt = amt;
    }

    public String getBankOutlet() {
        return BankOutlet;
    }

    public void setBankOutlet(String bankOutlet) {
        BankOutlet = bankOutlet;
    }

    public String getOperator() {
        return Operator;
    }

    public void setOperator(String operator) {
        Operator = operator;
    }

    public String getPayMode() {
        return PayMode;
    }

    public void setPayMode(String payMode) {
        PayMode = payMode;
    }

    public String getTraNoBus() {
        return TraNoBus;
    }

    public void setTraNoBus(String traNoBus) {
        TraNoBus = traNoBus;
    }

    public String getPayerName() {
        return PayerName;
    }

    public void setPayerName(String payerName) {
        PayerName = payerName;
    }

    public String getPayerAcct() {
        return PayerAcct;
    }

    public void setPayerAcct(String payerAcct) {
        PayerAcct = payerAcct;
    }

    public String getPayerOpBk() {
        return PayerOpBk;
    }

    public void setPayerOpBk(String payerOpBk) {
        PayerOpBk = payerOpBk;
    }

    public String getPayerOpBkNo() {
        return PayerOpBkNo;
    }

    public void setPayerOpBkNo(String payerOpBkNo) {
        PayerOpBkNo = payerOpBkNo;
    }

    public String getRecAcctType() {
        return RecAcctType;
    }

    public void setRecAcctType(String recAcctType) {
        RecAcctType = recAcctType;
    }

    public String getRecAcct() {
        return RecAcct;
    }

    public void setRecAcct(String recAcct) {
        RecAcct = recAcct;
    }

    public String getHold1() {
        return Hold1;
    }

    public void setHold1(String hold1) {
        Hold1 = hold1;
    }

    public String getHold2() {
        return Hold2;
    }

    public void setHold2(String hold2) {
        Hold2 = hold2;
    }

    @Override
    public String toString() {
        return "CzjyQrbwResponse{" +
                "CfmDate=" + CfmDate +
                ", PayCode='" + PayCode + '\'' +
                ", CfmTime='" + CfmTime + '\'' +
                ", Amt=" + Amt +
                ", BankOutlet='" + BankOutlet + '\'' +
                ", Operator='" + Operator + '\'' +
                ", PayMode='" + PayMode + '\'' +
                ", TraNoBus='" + TraNoBus + '\'' +
                ", PayerName='" + PayerName + '\'' +
                ", PayerAcct='" + PayerAcct + '\'' +
                ", PayerOpBk='" + PayerOpBk + '\'' +
                ", PayerOpBkNo='" + PayerOpBkNo + '\'' +
                ", RecAcctType='" + RecAcctType + '\'' +
                ", RecAcct='" + RecAcct + '\'' +
                ", Hold1='" + Hold1 + '\'' +
                ", Hold2='" + Hold2 + '\'' +
                '}';
    }
}
