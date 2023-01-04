package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @program: realestate
 * @description: 一人办已缴款信息存储
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-08-11 17:36
 **/
@Table(name = "BDC_SL_YJKXX")
@ApiModel(value = "BdcSlYjkxxDO", description = "不动产受理基本信息")
public class BdcSlYjkxxDO {

    @Id
    @ApiModelProperty("缴款信息id")
    private String jkxxid;

    @ApiModelProperty("项目id")
    private String xmid;

    @ApiModelProperty("纳税人识别号")
    private String nsrsbh;

    @ApiModelProperty("纳税人名称")
    private String nsrmc;

    @ApiModelProperty("登记序号")
    private String djxh;

    @ApiModelProperty("主管税务科所分局代码")
    private String zgswskfjdm;

    @ApiModelProperty("凭证序号")
    private String pzxh;

    @ApiModelProperty("应征凭证序号明细序号")
    private String yzpzmxxh;

    @ApiModelProperty("应征凭证种类代码")
    private String yzpzzldm;

    @ApiModelProperty("税款所属期起")
    private String skssqq;

    @ApiModelProperty("税款所属期止")
    private String skssqz;

    @ApiModelProperty("应补（退）税额")
    private Double ybtse;

    @ApiModelProperty("缴款状态")
    private String jkzt;

    @ApiModelProperty("电子税票号码")
    private String dzsphm;

    public String getJkyh() {
        return jkyh;
    }

    public void setJkyh(String jkyh) {
        this.jkyh = jkyh;
    }



    @ApiModelProperty("权利人类别")
    private String qlrlb;

    @ApiModelProperty("缴款银行")
    private String jkyh;

    public Double getXjze() {
        return xjze;
    }

    public void setXjze(Double xjze) {
        this.xjze = xjze;
    }

    @ApiModelProperty("小计总额")
    private Double xjze;



    public String getJkxxid() {
        return jkxxid;
    }

    public void setJkxxid(String jkxxid) {
        this.jkxxid = jkxxid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getNsrsbh() {
        return nsrsbh;
    }

    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
    }

    public String getNsrmc() {
        return nsrmc;
    }

    public void setNsrmc(String nsrmc) {
        this.nsrmc = nsrmc;
    }

    public String getDjxh() {
        return djxh;
    }

    public void setDjxh(String djxh) {
        this.djxh = djxh;
    }

    public String getZgswskfjdm() {
        return zgswskfjdm;
    }

    public void setZgswskfjdm(String zgswskfjdm) {
        this.zgswskfjdm = zgswskfjdm;
    }

    public String getPzxh() {
        return pzxh;
    }

    public void setPzxh(String pzxh) {
        this.pzxh = pzxh;
    }

    public String getYzpzmxxh() {
        return yzpzmxxh;
    }

    public void setYzpzmxxh(String yzpzmxxh) {
        this.yzpzmxxh = yzpzmxxh;
    }

    public String getYzpzzldm() {
        return yzpzzldm;
    }

    public void setYzpzzldm(String yzpzzldm) {
        this.yzpzzldm = yzpzzldm;
    }

    public String getSkssqq() {
        return skssqq;
    }

    public void setSkssqq(String skssqq) {
        this.skssqq = skssqq;
    }

    public String getSkssqz() {
        return skssqz;
    }

    public void setSkssqz(String skssqz) {
        this.skssqz = skssqz;
    }

    public Double getYbtse() {
        return ybtse;
    }

    public void setYbtse(Double ybtse) {
        this.ybtse = ybtse;
    }

    public String getJkzt() {
        return jkzt;
    }

    public void setJkzt(String jkzt) {
        this.jkzt = jkzt;
    }

    public String getDzsphm() {
        return dzsphm;
    }

    public void setDzsphm(String dzsphm) {
        this.dzsphm = dzsphm;
    }

    public String getQlrlb() {
        return qlrlb;
    }

    public void setQlrlb(String qlrlb) {
        this.qlrlb = qlrlb;
    }
}
