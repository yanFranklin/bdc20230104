package cn.gtmap.realestate.common.core.dto.exchange.nantong.fscz;

import javax.xml.bind.annotation.*;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2021/11/30
 * @description 电子号码Voucher
 */
@XmlType(propOrder = {"billTypeCode", "billTypeName", "billVerCode", "billVerName", "chgAgenCode", "hold1", "hold2"})
@XmlRootElement(name = "Voucher")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class DzhmVoucher {

    /**
     * 标识符	数据项名称	类型	长度	数据项描述	强制/可选
     * BillTypeCode	票据类型代码	String	[1,30]		M
     * BillTypeName	票据类型名称	GBString	[1,100]		M
     * BillVerCode	票据版本代码	String	[1,30]		M
     * BillVerName	票据版本名称	GBString	[1,100]		M
     * ChgAgenCode	换开电子票单位编码	String	[1,30]		M
     * Hold1	预留字段	GBString	[0,100]	单位非税操作员账号	O
     * Hold2	预留字段	GBString	[0,100]		O
     */

    private String BillTypeCode;

    private String BillTypeName;

    private String BillVerCode;

    private String BillVerName;

    private String ChgAgenCode;

    private String Hold1;

    private String Hold2;

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

    @XmlElement(name = "ChgAgenCode")
    public String getChgAgenCode() {
        return ChgAgenCode;
    }

    public void setChgAgenCode(String chgAgenCode) {
        ChgAgenCode = chgAgenCode;
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
        return "DzhmVoucher{" +
                "BillTypeCode='" + BillTypeCode + '\'' +
                ", BillTypeName='" + BillTypeName + '\'' +
                ", BillVerCode='" + BillVerCode + '\'' +
                ", BillVerName='" + BillVerName + '\'' +
                ", ChgAgenCode='" + ChgAgenCode + '\'' +
                ", Hold1='" + Hold1 + '\'' +
                ", Hold2='" + Hold2 + '\'' +
                '}';
    }
}
