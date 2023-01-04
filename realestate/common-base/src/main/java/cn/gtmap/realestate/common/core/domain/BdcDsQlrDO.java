package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2019/09/18
 * @description 不动产第三权利人
 */
@Table(
        name = "BDC_DSQLR"
)
@ApiModel(value = "BdcDsQlrDO",description = "不动第三产权利人")
public class BdcDsQlrDO {
    @Id
    @ApiModelProperty(value = "权利人id")
    private String qlrid;
    @ApiModelProperty(value = "项目id")
    private String xmid;
    @ApiModelProperty(value = "权利人名称")
    private String qlrmc;
    @ApiModelProperty(value = "证件种类")
    private Integer zjzl;
    @ApiModelProperty(value = "证件号")
    private String zjh;
    @ApiModelProperty(value = "通讯地址")
    private String txdz;
    @ApiModelProperty(value = "邮编")
    private String yb;
    @ApiModelProperty(value = "性别")
    private Integer xb;
    @ApiModelProperty(value = "法人名称")
    private String frmc;
    @ApiModelProperty(value = "法人电话")
    private String frdh;
    @ApiModelProperty(value = "法人证件种类")
    private String frzjzl;
    @ApiModelProperty(value = "法人证件号")
    private String frzjh;
    @ApiModelProperty(value = "代理人名称")
    private String dlrmc;
    @ApiModelProperty(value = "代理人电话")
    private String dlrdh;
    @ApiModelProperty(value = "代理人证件种类")
    private String dlrzjzl;
    @ApiModelProperty(value = "代理人证件号")
    private String dlrzjh;
    @ApiModelProperty(value = "代理机构")
    private String dljg;
    @ApiModelProperty(value = "权利人类型")
    private Integer qlrlx;
    @ApiModelProperty(value = "权利人类别")
    private String qlrlb;
    @ApiModelProperty(value = "权利比例")
    private String qlbl;
    @ApiModelProperty(value = "共有方式")
    private Integer gyfs;
    @ApiModelProperty(value = "共有情况")
    private String gyqk;
    @ApiModelProperty(value = "电话")
    private String dh;
    @ApiModelProperty(value = "备注")
    private String bz;
    @ApiModelProperty(value = "顺序号")
    private Integer sxh;

    public String getQlrid() {
        return qlrid;
    }

    public void setQlrid(String qlrid) {
        this.qlrid = qlrid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public Integer getZjzl() {
        return zjzl;
    }

    public void setZjzl(Integer zjzl) {
        this.zjzl = zjzl;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    public String getTxdz() {
        return txdz;
    }

    public void setTxdz(String txdz) {
        this.txdz = txdz;
    }

    public String getYb() {
        return yb;
    }

    public void setYb(String yb) {
        this.yb = yb;
    }

    public Integer getXb() {
        return xb;
    }

    public void setXb(Integer xb) {
        this.xb = xb;
    }

    public String getFrmc() {
        return frmc;
    }

    public void setFrmc(String frmc) {
        this.frmc = frmc;
    }

    public String getFrdh() {
        return frdh;
    }

    public void setFrdh(String frdh) {
        this.frdh = frdh;
    }

    public String getFrzjzl() {
        return frzjzl;
    }

    public void setFrzjzl(String frzjzl) {
        this.frzjzl = frzjzl;
    }

    public String getFrzjh() {
        return frzjh;
    }

    public void setFrzjh(String frzjh) {
        this.frzjh = frzjh;
    }

    public String getDlrmc() {
        return dlrmc;
    }

    public void setDlrmc(String dlrmc) {
        this.dlrmc = dlrmc;
    }

    public String getDlrdh() {
        return dlrdh;
    }

    public void setDlrdh(String dlrdh) {
        this.dlrdh = dlrdh;
    }

    public String getDlrzjzl() {
        return dlrzjzl;
    }

    public void setDlrzjzl(String dlrzjzl) {
        this.dlrzjzl = dlrzjzl;
    }

    public String getDlrzjh() {
        return dlrzjh;
    }

    public void setDlrzjh(String dlrzjh) {
        this.dlrzjh = dlrzjh;
    }

    public String getDljg() {
        return dljg;
    }

    public void setDljg(String dljg) {
        this.dljg = dljg;
    }

    public Integer getQlrlx() {
        return qlrlx;
    }

    public void setQlrlx(Integer qlrlx) {
        this.qlrlx = qlrlx;
    }

    public String getQlrlb() {
        return qlrlb;
    }

    public void setQlrlb(String qlrlb) {
        this.qlrlb = qlrlb;
    }

    public String getQlbl() {
        return qlbl;
    }

    public void setQlbl(String qlbl) {
        this.qlbl = qlbl;
    }

    public Integer getGyfs() {
        return gyfs;
    }

    public void setGyfs(Integer gyfs) {
        this.gyfs = gyfs;
    }

    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    public String getDh() {
        return dh;
    }

    public void setDh(String dh) {
        this.dh = dh;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Integer getSxh() {
        return sxh;
    }

    public void setSxh(Integer sxh) {
        this.sxh = sxh;
    }

    @Override
    public String toString() {
        return "BdcDsQlrDO{" +
                "qlrid='" + qlrid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", qlrmc='" + qlrmc + '\'' +
                ", zjzl=" + zjzl +
                ", zjh='" + zjh + '\'' +
                ", txdz='" + txdz + '\'' +
                ", yb='" + yb + '\'' +
                ", xb=" + xb +
                ", frmc='" + frmc + '\'' +
                ", frdh='" + frdh + '\'' +
                ", frzjzl='" + frzjzl + '\'' +
                ", frzjh='" + frzjh + '\'' +
                ", dlrmc='" + dlrmc + '\'' +
                ", dlrdh='" + dlrdh + '\'' +
                ", dlrzjzl='" + dlrzjzl + '\'' +
                ", dlrzjh='" + dlrzjh + '\'' +
                ", dljg='" + dljg + '\'' +
                ", qlrlx=" + qlrlx +
                ", qlrlb='" + qlrlb + '\'' +
                ", qlbl='" + qlbl + '\'' +
                ", gyfs=" + gyfs +
                ", gyqk='" + gyqk + '\'' +
                ", dh='" + dh + '\'' +
                ", bz='" + bz + '\'' +
                ", sxh=" + sxh +
                '}';
    }
}
