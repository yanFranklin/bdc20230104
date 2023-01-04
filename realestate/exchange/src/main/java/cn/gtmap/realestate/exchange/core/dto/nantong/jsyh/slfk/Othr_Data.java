package cn.gtmap.realestate.exchange.core.dto.nantong.jsyh.slfk;

import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/3/8
 * @description 建设银行推送受理信息-其他数据
 */
@XmlRootElement(name = "Othr_Data")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Othr_Data {

    @ApiModelProperty("不动产业务号")
    private String RealEst_Bsn_No;

    @ApiModelProperty("不动产单元号")
    private String RealEst_Unit_No;

    @ApiModelProperty("不动产证明号")
    private String RealEst_Ctfn_No;

    @ApiModelProperty("不动产登记证明号")
    private String RealEst_Ecb_No;

    @ApiModelProperty("不动产所属省份代码")
    private String Province_Cd;

    @ApiModelProperty("不动产所属城市代码")
    private String City_Cd;

    @ApiModelProperty("不动产所属区县代码")
    private String District_Cd;

    @ApiModelProperty("登记日期")
    private String Rg_Dt;

    @ApiModelProperty("登记到期日")
    private String Rgs_ExDy;

    @ApiModelProperty("不动产权证号")
    private String RealEst_Wrnt_No;

    @ApiModelProperty("记载日期时间")
    private String InPut_Dt_Tm;

    @ApiModelProperty("创建时间")
    private String Crt_Tm;

    @ApiModelProperty("附件ID")
    private String Atch_Id;

    @ApiModelProperty("附件名称")
    private String Atch_Nm;

    @ApiModelProperty("附件大小")
    private String Atch_Sz;

    @ApiModelProperty("附件类型")
    private String Atch_Tp;

    @ApiModelProperty("附件MD5值")
    private String Atch_MD5_Val;

    @ApiModelProperty("附件信息串")
    private String Atch_Inf_Str;

    @XmlElement(name = "RealEst_Bsn_No", nillable = true)
    public String getRealEst_Bsn_No() {
        return RealEst_Bsn_No;
    }

    public void setRealEst_Bsn_No(String realEst_Bsn_No) {
        RealEst_Bsn_No = realEst_Bsn_No;
    }

    @XmlElement(name = "RealEst_Unit_No", nillable = true)
    public String getRealEst_Unit_No() {
        return RealEst_Unit_No;
    }

    public void setRealEst_Unit_No(String realEst_Unit_No) {
        RealEst_Unit_No = realEst_Unit_No;
    }

    @XmlElement(name = "RealEst_Ctfn_No", nillable = true)
    public String getRealEst_Ctfn_No() {
        return RealEst_Ctfn_No;
    }

    public void setRealEst_Ctfn_No(String realEst_Ctfn_No) {
        RealEst_Ctfn_No = realEst_Ctfn_No;
    }

    @XmlElement(name = "RealEst_Ecb_No", nillable = true)
    public String getRealEst_Ecb_No() {
        return RealEst_Ecb_No;
    }

    public void setRealEst_Ecb_No(String realEst_Ecb_No) {
        RealEst_Ecb_No = realEst_Ecb_No;
    }

    @XmlElement(name = "Province_Cd", nillable = true)
    public String getProvince_Cd() {
        return Province_Cd;
    }

    public void setProvince_Cd(String province_Cd) {
        Province_Cd = province_Cd;
    }

    @XmlElement(name = "City_Cd", nillable = true)
    public String getCity_Cd() {
        return City_Cd;
    }

    public void setCity_Cd(String city_Cd) {
        City_Cd = city_Cd;
    }

    @XmlElement(name = "District_Cd", nillable = true)
    public String getDistrict_Cd() {
        return District_Cd;
    }

    public void setDistrict_Cd(String district_Cd) {
        District_Cd = district_Cd;
    }

    @XmlElement(name = "Rg_Dt", nillable = true)
    public String getRg_Dt() {
        return Rg_Dt;
    }

    public void setRg_Dt(String rg_Dt) {
        Rg_Dt = rg_Dt;
    }

    @XmlElement(name = "Rgs_ExDy", nillable = true)
    public String getRgs_ExDy() {
        return Rgs_ExDy;
    }

    public void setRgs_ExDy(String rgs_ExDy) {
        Rgs_ExDy = rgs_ExDy;
    }

    @XmlElement(name = "RealEst_Wrnt_No", nillable = true)
    public String getRealEst_Wrnt_No() {
        return RealEst_Wrnt_No;
    }

    public void setRealEst_Wrnt_No(String realEst_Wrnt_No) {
        RealEst_Wrnt_No = realEst_Wrnt_No;
    }

    @XmlElement(name = "InPut_Dt_Tm", nillable = true)
    public String getInPut_Dt_Tm() {
        return InPut_Dt_Tm;
    }

    public void setInPut_Dt_Tm(String inPut_Dt_Tm) {
        InPut_Dt_Tm = inPut_Dt_Tm;
    }

    @XmlElement(name = "Crt_Tm", nillable = true)
    public String getCrt_Tm() {
        return Crt_Tm;
    }

    public void setCrt_Tm(String crt_Tm) {
        Crt_Tm = crt_Tm;
    }

    @XmlElement(name = "Atch_Id", nillable = true)
    public String getAtch_Id() {
        return Atch_Id;
    }

    public void setAtch_Id(String atch_Id) {
        Atch_Id = atch_Id;
    }

    @XmlElement(name = "Atch_Nm", nillable = true)
    public String getAtch_Nm() {
        return Atch_Nm;
    }

    public void setAtch_Nm(String atch_Nm) {
        Atch_Nm = atch_Nm;
    }

    @XmlElement(name = "Atch_Sz", nillable = true)
    public String getAtch_Sz() {
        return Atch_Sz;
    }

    public void setAtch_Sz(String atch_Sz) {
        Atch_Sz = atch_Sz;
    }

    @XmlElement(name = "Atch_Tp", nillable = true)
    public String getAtch_Tp() {
        return Atch_Tp;
    }

    public void setAtch_Tp(String atch_Tp) {
        Atch_Tp = atch_Tp;
    }

    @XmlElement(name = "Atch_MD5_Val", nillable = true)
    public String getAtch_MD5_Val() {
        return Atch_MD5_Val;
    }

    public void setAtch_MD5_Val(String atch_MD5_Val) {
        Atch_MD5_Val = atch_MD5_Val;
    }

    @XmlElement(name = "Atch_Inf_Str", nillable = true)
    public String getAtch_Inf_Str() {
        return Atch_Inf_Str;
    }

    public void setAtch_Inf_Str(String atch_Inf_Str) {
        Atch_Inf_Str = atch_Inf_Str;
    }

    @Override
    public String toString() {
        return "Othr_Data{" +
                "RealEst_Bsn_No='" + RealEst_Bsn_No + '\'' +
                ", RealEst_Unit_No='" + RealEst_Unit_No + '\'' +
                ", RealEst_Ctfn_No='" + RealEst_Ctfn_No + '\'' +
                ", RealEst_Ecb_No='" + RealEst_Ecb_No + '\'' +
                ", Province_Cd='" + Province_Cd + '\'' +
                ", City_Cd='" + City_Cd + '\'' +
                ", District_Cd='" + District_Cd + '\'' +
                ", Rg_Dt='" + Rg_Dt + '\'' +
                ", Rgs_ExDy='" + Rgs_ExDy + '\'' +
                ", RealEst_Wrnt_No='" + RealEst_Wrnt_No + '\'' +
                ", InPut_Dt_Tm='" + InPut_Dt_Tm + '\'' +
                ", Crt_Tm='" + Crt_Tm + '\'' +
                ", Atch_Id='" + Atch_Id + '\'' +
                ", Atch_Nm='" + Atch_Nm + '\'' +
                ", Atch_Sz='" + Atch_Sz + '\'' +
                ", Atch_Tp='" + Atch_Tp + '\'' +
                ", Atch_MD5_Val='" + Atch_MD5_Val + '\'' +
                ", Atch_Inf_Str='" + Atch_Inf_Str + '\'' +
                '}';
    }
}
