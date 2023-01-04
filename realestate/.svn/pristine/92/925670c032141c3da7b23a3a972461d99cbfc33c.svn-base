package cn.gtmap.realestate.common.core.dto.exchange.nantong.fssr;

import javax.xml.bind.annotation.*;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @Date 2022/07/11
 * @description 电子发票下载Voucher
 */
@XmlType(propOrder = {"admDivCode", "setYear", "coCode", "payCode","hold1","hold2"})
@XmlRootElement(name = "Voucher")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class DzfpXzVoucher {

    /**
     * AdmDivCode	行政区划	String	[1, 6]		M
     * SetYear	年度	String	4		M
     * CoCode	执收单位编码	String	[1,30]	这里的执收单位编码为单位在财政非税收入系统中的单位代码	M
     * PayCode	非税缴款书编号	String	[1,20]	这里的非税缴款书编号为收入平台产生的标志缴款信息的唯一编号	M
     * Hold1	预留字段	GBString	[0,100]		0
     * Hold2	预留字段	GBString	[0,100]		O
     */
    private String AdmDivCode;
    private String SetYear;
    private String CoCode;
    private String PayCode;
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
                ", Hold1='" + Hold1 + '\'' +
                ", Hold2='" + Hold2 + '\'' +
                '}';
    }
}
