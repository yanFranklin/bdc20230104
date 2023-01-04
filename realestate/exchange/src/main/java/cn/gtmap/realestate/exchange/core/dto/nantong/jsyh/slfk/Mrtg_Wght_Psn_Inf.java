package cn.gtmap.realestate.exchange.core.dto.nantong.jsyh.slfk;

import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/3/8
 * @description 推送建设银行受理信息-抵押权人信息
 */
@XmlRootElement(name = "Mrtg_Wght_Psn_Inf")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Mrtg_Wght_Psn_Inf {

    @ApiModelProperty("抵押权人名称")
    private String Mrtg_Wght_Psn_Nm;

    @ApiModelProperty("抵押权人证件类型")
    private String Mrtg_Wght_Psn_Crdt_Tp;

    @ApiModelProperty("抵押权人证件号码")
    private String Mrtg_Wght_Psn_Crdt_No;

    @ApiModelProperty("抵押权人联系电话")
    private String Mrtg_Wght_Psn_Ctc_Tel;

    @ApiModelProperty("抵押权人类型")
    private String Mrtg_Wght_Psn_Tp;

    @ApiModelProperty("抵押权人法定代表或负责人")
    private String Mrtg_Wght_Psn_Lgl_Tbl_Or_Pnp;

    @ApiModelProperty("抵押权人地址")
    private String Mrtg_Wght_Psn_Adr;

    @ApiModelProperty("抵押权人操作状态")
    private String Mrtg_Wght_Psn_Mnplt_St;

    @XmlElement(name = "Mrtg_Wght_Psn_Nm", nillable = true)
    public String getMrtg_Wght_Psn_Nm() {
        return Mrtg_Wght_Psn_Nm;
    }

    public void setMrtg_Wght_Psn_Nm(String mrtg_Wght_Psn_Nm) {
        Mrtg_Wght_Psn_Nm = mrtg_Wght_Psn_Nm;
    }

    @XmlElement(name = "Mrtg_Wght_Psn_Crdt_Tp", nillable = true)
    public String getMrtg_Wght_Psn_Crdt_Tp() {
        return Mrtg_Wght_Psn_Crdt_Tp;
    }

    public void setMrtg_Wght_Psn_Crdt_Tp(String mrtg_Wght_Psn_Crdt_Tp) {
        Mrtg_Wght_Psn_Crdt_Tp = mrtg_Wght_Psn_Crdt_Tp;
    }

    @XmlElement(name = "Mrtg_Wght_Psn_Crdt_No", nillable = true)
    public String getMrtg_Wght_Psn_Crdt_No() {
        return Mrtg_Wght_Psn_Crdt_No;
    }

    public void setMrtg_Wght_Psn_Crdt_No(String mrtg_Wght_Psn_Crdt_No) {
        Mrtg_Wght_Psn_Crdt_No = mrtg_Wght_Psn_Crdt_No;
    }

    @XmlElement(name = "Mrtg_Wght_Psn_Ctc_Tel", nillable = true)
    public String getMrtg_Wght_Psn_Ctc_Tel() {
        return Mrtg_Wght_Psn_Ctc_Tel;
    }

    public void setMrtg_Wght_Psn_Ctc_Tel(String mrtg_Wght_Psn_Ctc_Tel) {
        Mrtg_Wght_Psn_Ctc_Tel = mrtg_Wght_Psn_Ctc_Tel;
    }

    @XmlElement(name = "Mrtg_Wght_Psn_Tp", nillable = true)
    public String getMrtg_Wght_Psn_Tp() {
        return Mrtg_Wght_Psn_Tp;
    }

    public void setMrtg_Wght_Psn_Tp(String mrtg_Wght_Psn_Tp) {
        Mrtg_Wght_Psn_Tp = mrtg_Wght_Psn_Tp;
    }

    @XmlElement(name = "Mrtg_Wght_Psn_Lgl_Tbl_Or_Pnp", nillable = true)
    public String getMrtg_Wght_Psn_Lgl_Tbl_Or_Pnp() {
        return Mrtg_Wght_Psn_Lgl_Tbl_Or_Pnp;
    }

    public void setMrtg_Wght_Psn_Lgl_Tbl_Or_Pnp(String mrtg_Wght_Psn_Lgl_Tbl_Or_Pnp) {
        Mrtg_Wght_Psn_Lgl_Tbl_Or_Pnp = mrtg_Wght_Psn_Lgl_Tbl_Or_Pnp;
    }

    @XmlElement(name = "Mrtg_Wght_Psn_Adr", nillable = true)
    public String getMrtg_Wght_Psn_Adr() {
        return Mrtg_Wght_Psn_Adr;
    }

    public void setMrtg_Wght_Psn_Adr(String mrtg_Wght_Psn_Adr) {
        Mrtg_Wght_Psn_Adr = mrtg_Wght_Psn_Adr;
    }

    @XmlElement(name = "Mrtg_Wght_Psn_Mnplt_St", nillable = true)
    public String getMrtg_Wght_Psn_Mnplt_St() {
        return Mrtg_Wght_Psn_Mnplt_St;
    }

    public void setMrtg_Wght_Psn_Mnplt_St(String mrtg_Wght_Psn_Mnplt_St) {
        Mrtg_Wght_Psn_Mnplt_St = mrtg_Wght_Psn_Mnplt_St;
    }

    @Override
    public String toString() {
        return "Mrtg_Wght_Psn_Inf{" +
                "Mrtg_Wght_Psn_Nm='" + Mrtg_Wght_Psn_Nm + '\'' +
                ", Mrtg_Wght_Psn_Crdt_Tp='" + Mrtg_Wght_Psn_Crdt_Tp + '\'' +
                ", Mrtg_Wght_Psn_Crdt_No='" + Mrtg_Wght_Psn_Crdt_No + '\'' +
                ", Mrtg_Wght_Psn_Ctc_Tel='" + Mrtg_Wght_Psn_Ctc_Tel + '\'' +
                ", Mrtg_Wght_Psn_Tp='" + Mrtg_Wght_Psn_Tp + '\'' +
                ", Mrtg_Wght_Psn_Lgl_Tbl_Or_Pnp='" + Mrtg_Wght_Psn_Lgl_Tbl_Or_Pnp + '\'' +
                ", Mrtg_Wght_Psn_Adr='" + Mrtg_Wght_Psn_Adr + '\'' +
                ", Mrtg_Wght_Psn_Mnplt_St='" + Mrtg_Wght_Psn_Mnplt_St + '\'' +
                '}';
    }
}
