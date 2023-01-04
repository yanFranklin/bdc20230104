package cn.gtmap.realestate.common.core.dto.exchange.nantong.fscz;

import javax.xml.bind.annotation.*;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2021/11/30
 * @description 电子缴款码Voucher
 */
@XmlType(propOrder = {"payCode", "billNo", "admDivCode", "billTypeCode", "billTypeName", "billVerCode", "billVerName", "version",
        "invoice_Data", "seal_Pic", "attach_Info", "hold1", "sign_Info", "recipient_Addr"})
@XmlRootElement(name = "Voucher")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class DzpjkpVoucher {

    /**
     * 标识符	数据项名称	类型	长度	数据项描述	强制/可选
     * PayCode	缴款码	String	[1,20]	唯一码	M
     * BillNo	电子票据票号	String	[1,20]	电子票据票号	M
     * AdmDivCode	行政区划	String	[1, 6]		M
     * BillTypeCode	票据类型代码	String	[1,30]		M
     * BillTypeName	票据类型名称	GBString	[1,100]		M
     * BillVerCode	票据版本代码	String	[1,30]		M
     * BillVerName	票据版本名称	GBString	[1,100]		M
     * Version	接口版本号	String	[1,4]		M
     * Invoice_Data	票据内容	String		电子票据头部、票面信息进行base64编码后内容。
     * 电子票据头部、票面信息数据规范见附录3，Header （包含 Header 标签）部分和EInvoiceData部分（包含EInvoiceData标签），Xml中内容编码字符集为UTF8。报文格式详见附录11：财政电子票据数据编码规范
     * M
     * Seal_Pic	单位印章图片数据	String		单位印章图片数据（透明PNG），base64编码
     * O
     * Attach_Info	附加信息	String			O
     * Hold1	预留字段	GBString	[0,100]	单位非税操作员账号	O
     * Sign_Info	签名信息			详见Sign_Info节点描述	M
     * Recipient_Addr	通知信息			详见Recipient_Addr节点描述	O
     * <p>
     * 报文体内部XML节点TAG：Voucher->Sign_Info
     * 标识符	数据项名称	类型	长度	数据项描述	强制/可选
     * Value	签名值	String		对电子票据头部、票面信息的签名值（签名原文为电子票据数据 Header和EInvoiceData内容），base64格式。	M
     * Format	签名格式类型	String		签名格式类型，
     * 目前固定 DETACH	M
     * Algorithm	摘要算法	String		摘要算法，默认SM2	M
     * Time	签名时间	UTCDateTime			M
     * Key_Info	证书信息			详见Key_Info节点描述	O
     * <p>
     * 报文体内部XML节点TAG：Voucher->Sign_Info->Key_Info
     * 标识符	数据项名称	类型	长度	数据项描述	强制/可选
     * Number	证书序号	String	[1,60]		O
     * Issuer	证书证书颁发者名称	GBString	[1,100]		O
     * <p>
     * 报文体内部XML节点TAG：Voucher->Recipient_Addr
     * 标识符	数据项名称	类型	长度	数据项描述	强制/可选
     * Email	通知邮件地址	String	[1,50]		O
     * Telephone	通知人电话	GBString	[1,50]		O
     */

    private String PayCode;

    private String BillNo;

    private String AdmDivCode;

    private String BillTypeCode;

    private String BillTypeName;

    private String BillVerCode;

    private String BillVerName;

    private String Version;

    private String Invoice_Data;

    private String Seal_Pic;

    private String Attach_Info;

    private String Hold1;

    private SignInfo Sign_Info;

    private RecipientAddr Recipient_Addr;

    @XmlElement(name = "PayCode")
    public String getPayCode() {
        return PayCode;
    }

    public void setPayCode(String payCode) {
        PayCode = payCode;
    }

    @XmlElement(name = "BillNo")
    public String getBillNo() {
        return BillNo;
    }

    public void setBillNo(String billNo) {
        BillNo = billNo;
    }

    @XmlElement(name = "AdmDivCode")
    public String getAdmDivCode() {
        return AdmDivCode;
    }

    public void setAdmDivCode(String admDivCode) {
        AdmDivCode = admDivCode;
    }

    @XmlElement(name = "BillTypeCode")
    public String getBillTypeCode() {
        return BillTypeCode;
    }

    public void setBillTypeCode(String billTypeCode) {
        BillTypeCode = billTypeCode;
    }

    @XmlElement(name = "BillTypeName")
    public String getBillTypeName() {
        return BillTypeName;
    }

    public void setBillTypeName(String billTypeName) {
        BillTypeName = billTypeName;
    }

    @XmlElement(name = "BillVerCode")
    public String getBillVerCode() {
        return BillVerCode;
    }

    public void setBillVerCode(String billVerCode) {
        BillVerCode = billVerCode;
    }

    @XmlElement(name = "BillVerName")
    public String getBillVerName() {
        return BillVerName;
    }

    public void setBillVerName(String billVerName) {
        BillVerName = billVerName;
    }

    @XmlElement(name = "Version")
    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    @XmlElement(name = "Invoice_Data")
    public String getInvoice_Data() {
        return Invoice_Data;
    }

    public void setInvoice_Data(String invoice_Data) {
        Invoice_Data = invoice_Data;
    }

    @XmlElement(name = "Seal_Pic")
    public String getSeal_Pic() {
        return Seal_Pic;
    }

    public void setSeal_Pic(String seal_Pic) {
        Seal_Pic = seal_Pic;
    }

    @XmlElement(name = "Attach_Info")
    public String getAttach_Info() {
        return Attach_Info;
    }

    public void setAttach_Info(String attach_Info) {
        Attach_Info = attach_Info;
    }

    @XmlElement(name = "Hold1")
    public String getHold1() {
        return Hold1;
    }

    public void setHold1(String hold1) {
        Hold1 = hold1;
    }

    @XmlElement(name = "Sign_Info")
    public SignInfo getSign_Info() {
        return Sign_Info;
    }

    public void setSign_Info(SignInfo sign_Info) {
        Sign_Info = sign_Info;
    }

    @XmlElement(name = "Recipient_Addr")
    public RecipientAddr getRecipient_Addr() {
        return Recipient_Addr;
    }

    public void setRecipient_Addr(RecipientAddr recipient_Addr) {
        Recipient_Addr = recipient_Addr;
    }

    @Override
    public String toString() {
        return "DzpjkpVoucher{" +
                "PayCode='" + PayCode + '\'' +
                ", BillNo='" + BillNo + '\'' +
                ", AdmDivCode=" + AdmDivCode +
                ", BillTypeCode='" + BillTypeCode + '\'' +
                ", BillTypeName='" + BillTypeName + '\'' +
                ", BillVerCode='" + BillVerCode + '\'' +
                ", BillVerName='" + BillVerName + '\'' +
                ", Version='" + Version + '\'' +
                ", Invoice_Data='" + Invoice_Data + '\'' +
                ", Seal_Pic='" + Seal_Pic + '\'' +
                ", Attach_Info='" + Attach_Info + '\'' +
                ", Hold1='" + Hold1 + '\'' +
                ", Sign_Info=" + Sign_Info +
                ", Recipient_Addr=" + Recipient_Addr +
                '}';
    }
}
