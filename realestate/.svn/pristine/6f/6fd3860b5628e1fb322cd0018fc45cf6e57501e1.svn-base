package cn.gtmap.realestate.common.core.dto.exchange.nantong.fscz;

import javax.xml.bind.annotation.*;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2021/11/30
 * @description 常州电子缴款码Voucher
 */
@XmlType(name = "VoucherCz", propOrder = {"admDivCode", "payCode", "billDate", "chgAgenCode", "chgAgenName", "payerName", "payerAcct",
        "payerOpBk", "amt", "oriAmt", "delayAmt", "payStats", "recAcctType", "recName", "recAcct", "recAcctReal", "recOpBk", "recBankNo",
        "isInterBank", "payInfo", "memo", "crcCode", "mobilePhone", "payerCreditCode", "recCreditCode", "hold1", "hold2"})
@XmlRootElement(name = "Voucher")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class DzjkdVoucherCz {

    /**
     * 标识符	数据项名称	类型	长度	数据项描述	强制/可选
     * AdmDivCode	行政区划	String	[1, 6]		M
     * PayCode	缴款码	String	[1,20]	缴款码固定20位，根据编码规则设置，需要保证唯一	M
     * BillDate	填制日期	Date	8		M
     * ChgAgenCode	执收单位编码	String	[1,30]		M
     * ChgAgenName	执收单位名称	GBString	[1,100]		M
     * PayerName	缴（付）款人全称	GBString	[1,100]		M
     * PayerAcct	缴（付）款人账号	String	[0,50]		O
     * PayerOpBk	缴（付）款人开户行	GBString	[0,100]		O
     * Amt	缴款金额合计	Currency			M
     * OriAmt	缴款书金额	Currency			M
     * DelayAmt	滞纳金	Currency			M
     * PayStats	缴款状态	NString	1	0：未缴款，2：作废	M
     * RecAcctType	收款人账户类型	NString	2	见附录5	M
     * RecName	收款人全称	GBString	[0,100]		M
     * RecAcct	票据上打印的收款人账号	String	[0,50]		M
     * RecAcctReal	真实的收款人账号	String	[0,50]	如果票据上的收款人账号是伪账号，这里填写真实账号	*M
     * RecOpBk	收款人开户行	GBString	[0,100]		M
     * RecBankNo	收款人开户行行号	NString	[0,20]
     * M
     * IsInterBank	是否跨行	NString	[1,1]	0 本行，1 跨行	M
     * PayInfo	摘要	GBString	[0,500]	全国统一项目识别码+项目名称+金额+计量单位+数量+标准类型+收缴标准，信息以分隔符进行分割，如：全国统一项目识别码1|项目名称1|2.0|次|2|无限制|0.0-0.0#全国统一项目识别码2|项目名称2|3.0|个|3|金额上下限|1.0-10.0#,也可为财政定义的其他格式	O
     * Memo	备注	GBString	[0,200]		O
     * CrcCode	校验码	String	[0,4]
     * O
     * MobilePhone	手机号	String	[1,20]	缴款人的手机号码	M
     * PayerCreditCode	缴（付）款人统一社会信用代码	String	[0,30]		O
     * RecCreditCode	收款人统一社会信用代码	String	[0,30]		O
     * <p>
     * OperatorCode	操作人编码	String	[0,30]		O
     * OperatorName	操作人名称	String	[0,30]		O
     * Hold1	预留字段	GBString	[0,100]	如果需要缴款成功通知填写：Y；其他情况非税不发缴款成功通知
     * O
     * Hold2	预留字段	GBString	[0,100]		O
     */
    private String AdmDivCode;
    private String PayCode;
    private String BillDate;

    private String ChgAgenCode;

    private String ChgAgenName;
    private String PayerName;
    private String PayerAcct;

    private String PayerOpBk;

    private Double Amt;

    private Double OriAmt;

    private String DelayAmt;

    private String PayStats;

    private String RecAcctType;

    private String RecName;

    private String RecAcct;

    private String RecAcctReal;

    private String RecOpBk;

    private String RecBankNo;

    private String IsInterBank;

    private String PayInfo;

    private String Memo;

    private String CrcCode;

    private String MobilePhone;

    private String PayerCreditCode;

    private String RecCreditCode;

    private String Hold1;

    private String Hold2;

    @XmlElement(name = "AdmDivCode")
    public String getAdmDivCode() {
        return AdmDivCode;
    }

    public void setAdmDivCode(String admDivCode) {
        AdmDivCode = admDivCode;
    }

    @XmlElement(name = "PayCode")
    public String getPayCode() {
        return PayCode;
    }

    public void setPayCode(String payCode) {
        PayCode = payCode;
    }

    @XmlElement(name = "BillDate")
    public String getBillDate() {
        return BillDate;
    }

    public void setBillDate(String billDate) {
        BillDate = billDate;
    }

    @XmlElement(name = "ChgAgenCode")
    public String getChgAgenCode() {
        return ChgAgenCode;
    }

    public void setChgAgenCode(String chgAgenCode) {
        ChgAgenCode = chgAgenCode;
    }

    @XmlElement(name = "ChgAgenName")
    public String getChgAgenName() {
        return ChgAgenName;
    }

    public void setChgAgenName(String chgAgenName) {
        ChgAgenName = chgAgenName;
    }

    @XmlElement(name = "PayerName")
    public String getPayerName() {
        return PayerName;
    }

    public void setPayerName(String payerName) {
        PayerName = payerName;
    }

    @XmlElement(name = "PayerAcct")
    public String getPayerAcct() {
        return PayerAcct;
    }

    public void setPayerAcct(String payerAcct) {
        PayerAcct = payerAcct;
    }

    @XmlElement(name = "PayerOpBk")
    public String getPayerOpBk() {
        return PayerOpBk;
    }

    public void setPayerOpBk(String payerOpBk) {
        PayerOpBk = payerOpBk;
    }

    @XmlElement(name = "Amt")
    public Double getAmt() {
        return Amt;
    }

    public void setAmt(Double amt) {
        Amt = amt;
    }

    @XmlElement(name = "OriAmt")
    public Double getOriAmt() {
        return OriAmt;
    }

    public void setOriAmt(Double oriAmt) {
        OriAmt = oriAmt;
    }

    @XmlElement(name = "DelayAmt")
    public String getDelayAmt() {
        return DelayAmt;
    }

    public void setDelayAmt(String delayAmt) {
        DelayAmt = delayAmt;
    }

    @XmlElement(name = "PayStats")
    public String getPayStats() {
        return PayStats;
    }

    public void setPayStats(String payStats) {
        PayStats = payStats;
    }

    @XmlElement(name = "RecAcctType")
    public String getRecAcctType() {
        return RecAcctType;
    }

    public void setRecAcctType(String recAcctType) {
        RecAcctType = recAcctType;
    }

    @XmlElement(name = "RecName")
    public String getRecName() {
        return RecName;
    }

    public void setRecName(String recName) {
        RecName = recName;
    }

    @XmlElement(name = "RecAcct")
    public String getRecAcct() {
        return RecAcct;
    }

    public void setRecAcct(String recAcct) {
        RecAcct = recAcct;
    }

    @XmlElement(name = "RecAcctReal")
    public String getRecAcctReal() {
        return RecAcctReal;
    }

    public void setRecAcctReal(String recAcctReal) {
        RecAcctReal = recAcctReal;
    }

    @XmlElement(name = "RecOpBk")
    public String getRecOpBk() {
        return RecOpBk;
    }

    public void setRecOpBk(String recOpBk) {
        RecOpBk = recOpBk;
    }

    @XmlElement(name = "RecBankNo")
    public String getRecBankNo() {
        return RecBankNo;
    }

    public void setRecBankNo(String recBankNo) {
        RecBankNo = recBankNo;
    }

    @XmlElement(name = "IsInterBank")
    public String getIsInterBank() {
        return IsInterBank;
    }

    public void setIsInterBank(String isInterBank) {
        IsInterBank = isInterBank;
    }

    @XmlElement(name = "PayInfo")
    public String getPayInfo() {
        return PayInfo;
    }

    public void setPayInfo(String payInfo) {
        PayInfo = payInfo;
    }

    @XmlElement(name = "Memo")
    public String getMemo() {
        return Memo;
    }

    public void setMemo(String memo) {
        Memo = memo;
    }

    @XmlElement(name = "CrcCode")
    public String getCrcCode() {
        return CrcCode;
    }

    public void setCrcCode(String crcCode) {
        CrcCode = crcCode;
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

    @XmlElement(name = "RecCreditCode")
    public String getRecCreditCode() {
        return RecCreditCode;
    }

    public void setRecCreditCode(String recCreditCode) {
        RecCreditCode = recCreditCode;
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
        return "DzjkdVoucher{" +
                "AdmDivCode='" + AdmDivCode + '\'' +
                ", PayCode='" + PayCode + '\'' +
                ", BillDate=" + BillDate +
                ", ChgAgenCode='" + ChgAgenCode + '\'' +
                ", ChgAgenName='" + ChgAgenName + '\'' +
                ", ChgAgenName='" + ChgAgenName + '\'' +
                ", PayerName='" + PayerName + '\'' +
                ", PayerAcct='" + PayerAcct + '\'' +
                ", PayerOpBk='" + PayerOpBk + '\'' +
                ", Amt=" + Amt +
                ", OriAmt=" + OriAmt +
                ", DelayAmt='" + DelayAmt + '\'' +
                ", PayStats='" + PayStats + '\'' +
                ", RecAcctType='" + RecAcctType + '\'' +
                ", RecName='" + RecName + '\'' +
                ", RecAcct='" + RecAcct + '\'' +
                ", RecAcctReal='" + RecAcctReal + '\'' +
                ", RecOpBk='" + RecOpBk + '\'' +
                ", RecBankNo='" + RecBankNo + '\'' +
                ", IsInterBank='" + IsInterBank + '\'' +
                ", PayInfo='" + PayInfo + '\'' +
                ", Memo='" + Memo + '\'' +
                ", CrcCode='" + CrcCode + '\'' +
                ", MobilePhone='" + MobilePhone + '\'' +
                ", PayerCreditCode='" + PayerCreditCode + '\'' +
                ", RecCreditCode='" + RecCreditCode + '\'' +
                ", Hold1='" + Hold1 + '\'' +
                ", Hold2='" + Hold2 + '\'' +
                '}';
    }
}
