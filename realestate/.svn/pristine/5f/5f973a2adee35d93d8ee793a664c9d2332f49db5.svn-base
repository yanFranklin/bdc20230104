package cn.gtmap.realestate.common.core.dto.exchange.nantong.fscz;

import javax.xml.bind.annotation.*;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2021/11/30
 * @description 电子票据下载Voucher
 */
@XmlType(propOrder = {"billNo", "admDivCode", "billTypeCode", "billTypeName", "billVerCode", "billVerName", "version", "crcCode", "hold1"})
@XmlRootElement(name = "Voucher")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class DzpjxzVoucher {

    /**
     * 标识符	数据项名称	类型	长度	数据项描述	强制/可选
     * BillNo	电子票据票号	String	[1,20]	电子票据票号	M
     * AdmDivCode	行政区划	String	[1, 6]		M
     * BillTypeCode	票据类型代码	String	[1,30]		M
     * BillTypeName	票据类型名称	GBString	[1,100]		M
     * BillVerCode	票据版本代码	String	[1,30]		M
     * BillVerName	票据版本名称	GBString	[1,100]		M
     * Version	接口版本号	String	[1,4]		M
     * CrcCode	电子票校验码	String	[0,4]		O
     * Hold1	预留字段	GBString	[0,100]		O
     */

    private String BillNo;

    private String AdmDivCode;

    private String BillTypeCode;

    private String BillTypeName;

    private String BillVerCode;

    private String BillVerName;

    private String Version;

    private String CrcCode;

    private String Hold1;

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

    @XmlElement(name = "CrcCode")
    public String getCrcCode() {
        return CrcCode;
    }

    public void setCrcCode(String crcCode) {
        CrcCode = crcCode;
    }

    @XmlElement(name = "Hold1")
    public String getHold1() {
        return Hold1;
    }

    public void setHold1(String hold1) {
        Hold1 = hold1;
    }

    @Override
    public String toString() {
        return "DzpjxzVoucher{" +
                "BillNo='" + BillNo + '\'' +
                ", AdmDivCode='" + AdmDivCode + '\'' +
                ", BillTypeCode=" + BillTypeCode +
                ", BillTypeName='" + BillTypeName + '\'' +
                ", BillVerCode='" + BillVerCode + '\'' +
                ", BillVerName='" + BillVerName + '\'' +
                ", Version='" + Version + '\'' +
                ", CrcCode='" + CrcCode + '\'' +
                ", Hold1='" + Hold1 + '\'' +
                '}';
    }
}
