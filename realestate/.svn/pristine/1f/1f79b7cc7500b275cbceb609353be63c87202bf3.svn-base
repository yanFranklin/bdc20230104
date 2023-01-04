package cn.gtmap.realestate.common.core.dto.exchange.nantong.fssr;


import javax.xml.bind.annotation.*;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @Date 2021/11/30
 * @description 单位票据信息同步Voucher
 */
@XmlType(propOrder = {"admDivCode", "setYear", "payNo", "payCode", "billDate", "chgAgenCode", "chgAgenName",
        "payerName", "payerAcct", "payerOpBk", "amt", "oriAmt", "delayAmt", "payStats", "recAcctType", "recName", "recAcct", "recAcctReal",
        "recOpBk", "recBankNo", "isInterBank", "payInfo", "memo", "crcCode", "mobilePhone", "payerCreditCode", "recCreditCode", "isAutoMake","hold1", "hold2"})
@XmlRootElement(name = "Voucher")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class DwpjxxtbVoucher {

    /**
     * 标识符	   数据项名称	类型	    长度	    数据项描述
     * AdmDivCode	行政区划	String	[1, 6]
     * SetYear	    年度	    String	4
     * PayNo	    非税缴款书编号	String	[1,20]	这里的非税缴款书编号为单位系统产生的标志缴款信息的唯一编号
     * PayCode	    非税缴款书编号	String	[1,20]	这里的非税缴款书编号为收入平台产生的标志缴款信息的唯一编号
     * BillDate  	填制日期	  Date	8
     * ChgAgenCode	执收单位编码	String	[1,30]	这里的执收单位编码为单位在财政非税收入系统中的单位代码
     * ChgAgenName	执收单位名称	GBString	[1,100]	这里的执收单位名称为单位在财政非税收入系统中的单位名称
     * PayerName	缴（付）款人全称	GBString	[1,100]	财政返回的缴款书上的缴款人名称
     * PayerAcct	缴（付）款人账号	String	[0,50]
     * PayerOpBk	缴（付）款人开户行	GBString	[0,100]
     * Amt	缴款金额合计	Currency
     * OriAmt	缴款书金额	Currency
     * DelayAmt	滞纳金	Currency
     * PayStats	缴款状态	NString	1	0：未缴款，2：作废
     * RecAcctType	收款人账户类型	NString	2	默认为1
     * RecName	收款人全称	GBString	[0,100]
     * RecAcct	票据上打印的收款人账号	String	[0,50]
     * RecAcctReal	真实的收款人账号	String	[0,50]	如果票据上的收款人账号是伪账号，这里填写真实账号
     * RecOpBk	收款人开户行	GBString	[0,100]
     * RecBankNo	收款人银行代码	NString	[0,20]
     * IsInterBank	是否跨行	NString	[1,1]	0 本行，1 跨行
     * PayInfo	摘要	GBString	[0,500]	全国统一项目识别码+项目名称+金额+计量单位+数量+标准类型+收缴标准，信息以分隔符进行分割，如：全国统一项目识别码1|项目名称1|2.0|次|2|无限制|0.0-0.0#全国统一项目识别码2|项目名称2|3.0|个|3|金额上下限|1.0-10.0#,也可为财政定义的其他格式
     * Memo	备注	GBString	[0,200]
     * CrcCode	校验码	String	4
     * MobilePhone	缴款人手机号	String	[1,30]	可用于接收缴款通知、和电子票通知短信
     * PayerCreditCode	缴款人统一社会信用代码	String	[0,100]	可用于查询电子票
     * RecCreditCode	收款人统一社会信用代码	String	[0,100]
     * IsAutoMake	是否自动换开电子票	String	[0,1]	当此字段值为N时，在缴费成功之后，收入平台不会自动发起换开电子票；否则默认换开电子票
     * Hold1	预留字段	GBString	[0,100]
     * Hold2	预留字段	GBString	[0,100]
     */

    private String AdmDivCode;
    private String SetYear;
    private String PayNo;
    private String PayCode;
    private String BillDate;
    private String ChgAgenCode;
    private String ChgAgenName;
    private String PayerName;
    private String PayerAcct;
    private String PayerOpBk;
    private String Amt;
    private String OriAmt;
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
    private String IsAutoMake;
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

    @XmlElement(name = "PayNo")
    public String getPayNo() {
        return PayNo;
    }

    public void setPayNo(String payNo) {
        PayNo = payNo;
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
    public String getAmt() {
        return Amt;
    }

    public void setAmt(String amt) {
        Amt = amt;
    }

    @XmlElement(name = "OriAmt")
    public String getOriAmt() {
        return OriAmt;
    }

    public void setOriAmt(String oriAmt) {
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

    @XmlElement(name = "IsAutoMake")
    public String getIsAutoMake() {
        return IsAutoMake;
    }

    public void setIsAutoMake(String isAutoMake) {
        IsAutoMake = isAutoMake;
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
        return "DwpjxxtbVoucher{" +
                "AdmDivCode='" + AdmDivCode + '\'' +
                ", SetYear='" + SetYear + '\'' +
                ", PayNo='" + PayNo + '\'' +
                ", PayCode='" + PayCode + '\'' +
                ", BillDate='" + BillDate + '\'' +
                ", ChgAgenCode='" + ChgAgenCode + '\'' +
                ", ChgAgenName='" + ChgAgenName + '\'' +
                ", PayerName='" + PayerName + '\'' +
                ", PayerAcct='" + PayerAcct + '\'' +
                ", PayerOpBk='" + PayerOpBk + '\'' +
                ", Amt='" + Amt + '\'' +
                ", OriAmt='" + OriAmt + '\'' +
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
                ", IsAutoMake='" + IsAutoMake + '\'' +
                ", Hold1='" + Hold1 + '\'' +
                ", Hold2='" + Hold2 + '\'' +
                '}';
    }
}
