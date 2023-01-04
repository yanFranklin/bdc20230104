package cn.gtmap.realestate.exchange.core.dto.nantong.jsyh.slfk.reject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Mrtg_Psn_Inf")
@XmlAccessorType(XmlAccessType.PROPERTY)
@ApiModel(value = "建行审核拒绝MrtgPsnInf")
public class MrtgPsnInf {
    @ApiModelProperty("抵押人名称")
    private String mrtgPsnNm;
    @ApiModelProperty("抵押人类型")
    private String mrtgPsnTp;
    @ApiModelProperty("抵押人证件类型")
    private String mrtgPsnCrdtTp;
    @ApiModelProperty("抵押人证件号码")
    private String mrtgPsnCrdtNo;
    @ApiModelProperty("抵押人联系电话")
    private String mrtgPsnCtcTel;
    @ApiModelProperty("抵押人法定代表或负责人")
    private String mrtgPsnLglTblOrPnp;
    @ApiModelProperty("抵押人地址")
    private String mrtgPsnAdr;
    @ApiModelProperty("抵押人操作状态")
    private String mrtgPsnMnpltSt;

    @XmlElement(name = "Mrtg_Psn_Nm")
    public String getMrtgPsnNm() {
        return mrtgPsnNm;
    }

    public void setMrtgPsnNm(String mrtgPsnNm) {
        this.mrtgPsnNm = mrtgPsnNm;
    }

    @XmlElement(name = "Mrtg_Psn_Tp")
    public String getMrtgPsnTp() {
        return mrtgPsnTp;
    }

    public void setMrtgPsnTp(String mrtgPsnTp) {
        this.mrtgPsnTp = mrtgPsnTp;
    }

    @XmlElement(name = "Mrtg_Psn_Crdt_Tp")
    public String getMrtgPsnCrdtTp() {
        return mrtgPsnCrdtTp;
    }

    public void setMrtgPsnCrdtTp(String mrtgPsnCrdtTp) {
        this.mrtgPsnCrdtTp = mrtgPsnCrdtTp;
    }

    @XmlElement(name = "Mrtg_Psn_Crdt_No")
    public String getMrtgPsnCrdtNo() {
        return mrtgPsnCrdtNo;
    }

    public void setMrtgPsnCrdtNo(String mrtgPsnCrdtNo) {
        this.mrtgPsnCrdtNo = mrtgPsnCrdtNo;
    }

    @XmlElement(name = "Mrtg_Psn_Ctc_Tel")
    public String getMrtgPsnCtcTel() {
        return mrtgPsnCtcTel;
    }

    public void setMrtgPsnCtcTel(String mrtgPsnCtcTel) {
        this.mrtgPsnCtcTel = mrtgPsnCtcTel;
    }

    @XmlElement(name = "Mrtg_Psn_Lgl_Tbl_Or_Pnp")
    public String getMrtgPsnLglTblOrPnp() {
        return mrtgPsnLglTblOrPnp;
    }

    public void setMrtgPsnLglTblOrPnp(String mrtgPsnLglTblOrPnp) {
        this.mrtgPsnLglTblOrPnp = mrtgPsnLglTblOrPnp;
    }

    @XmlElement(name = "Mrtg_Psn_Adr")
    public String getMrtgPsnAdr() {
        return mrtgPsnAdr;
    }

    public void setMrtgPsnAdr(String mrtgPsnAdr) {
        this.mrtgPsnAdr = mrtgPsnAdr;
    }

    @XmlElement(name = "Mrtg_Psn_Mnplt_St")
    public String getMrtgPsnMnpltSt() {
        return mrtgPsnMnpltSt;
    }

    public void setMrtgPsnMnpltSt(String mrtgPsnMnpltSt) {
        this.mrtgPsnMnpltSt = mrtgPsnMnpltSt;
    }
}