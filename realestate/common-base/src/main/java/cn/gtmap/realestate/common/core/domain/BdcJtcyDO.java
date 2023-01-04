package cn.gtmap.realestate.common.core.domain;

import cn.gtmap.realestate.common.core.annotations.Zd;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/8/3
 * @description 不动产家庭成员
 */
@Table(
        name = "BDC_JTCY"
)
@ApiModel(value = "BdcJtcyDO",description = "不动产家庭成员")
public class BdcJtcyDO {

    @Id
    @ApiModelProperty(value = "家庭成员id")
    private String jtcyid;

    @ApiModelProperty(value = "户口簿编码")
    private String hkbbm;

    @ApiModelProperty(value = "家庭成员名称")
    private String jtcymc;

    @ApiModelProperty(value = "家庭成员证件种类")
    private Integer jtcyzjzl;

    @ApiModelProperty(value = "家庭成员证件号")
    private String jtcyzjh;

    @Zd(tableClass = BdcZdYhzgxDO.class)
    @ApiModelProperty(value = "家庭成员与户主关系")
    private String yhzgx;

    @ApiModelProperty(value = "家庭成员性别")
    private String xb;

    @ApiModelProperty(value = "成员序号")
    private Integer xh;

    @ApiModelProperty(value = "户口所在地")
    private String hkszd;

    @ApiModelProperty(value = "户口簿版本号")
    private Integer hkbbbh;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "电话")
    private String dh;

    @ApiModelProperty(value = "地址")
    private String dz;

    @ApiModelProperty(value = "邮编")
    private String yb;

    @ApiModelProperty(value = "工作单位")
    private String gzdw;

    @ApiModelProperty(value = "电子邮件")
    private String dzyj;

    @ApiModelProperty(value = "是否共有人")
    private Integer sfgyr;

    @ApiModelProperty(value = "发证机关")
    private String fzjg;

    @ApiModelProperty(value = "所属行业")
    private String sshy;

    @ApiModelProperty(value = "国家地区")
    private String gj;

    @ApiModelProperty(value = "年龄")
    private Integer nl;

    @ApiModelProperty(value = "是否集体经济组织成员")
    private String sfjtjjzzcy;

    public String getJtcyid() {
        return jtcyid;
    }

    public void setJtcyid(String jtcyid) {
        this.jtcyid = jtcyid;
    }

    public String getHkbbm() {
        return hkbbm;
    }

    public void setHkbbm(String hkbbm) {
        this.hkbbm = hkbbm;
    }

    public String getJtcymc() {
        return jtcymc;
    }

    public void setJtcymc(String jtcymc) {
        this.jtcymc = jtcymc;
    }

    public String getJtcyzjh() {
        return jtcyzjh;
    }

    public void setJtcyzjh(String jtcyzjh) {
        this.jtcyzjh = jtcyzjh;
    }

    public String getYhzgx() {
        return yhzgx;
    }

    public void setYhzgx(String yhzgx) {
        this.yhzgx = yhzgx;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }

    public String getHkszd() {
        return hkszd;
    }

    public void setHkszd(String hkszd) {
        this.hkszd = hkszd;
    }

    public Integer getHkbbbh() {
        return hkbbbh;
    }

    public void setHkbbbh(Integer hkbbbh) {
        this.hkbbbh = hkbbbh;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getDh() {
        return dh;
    }

    public void setDh(String dh) {
        this.dh = dh;
    }

    public String getDz() {
        return dz;
    }

    public void setDz(String dz) {
        this.dz = dz;
    }

    public String getYb() {
        return yb;
    }

    public void setYb(String yb) {
        this.yb = yb;
    }

    public String getGzdw() {
        return gzdw;
    }

    public void setGzdw(String gzdw) {
        this.gzdw = gzdw;
    }

    public String getDzyj() {
        return dzyj;
    }

    public void setDzyj(String dzyj) {
        this.dzyj = dzyj;
    }

    public Integer getSfgyr() {
        return sfgyr;
    }

    public void setSfgyr(Integer sfgyr) {
        this.sfgyr = sfgyr;
    }

    public String getFzjg() {
        return fzjg;
    }

    public void setFzjg(String fzjg) {
        this.fzjg = fzjg;
    }

    public String getSshy() {
        return sshy;
    }

    public void setSshy(String sshy) {
        this.sshy = sshy;
    }

    public String getGj() {
        return gj;
    }

    public void setGj(String gj) {
        this.gj = gj;
    }

    public Integer getJtcyzjzl() {
        return jtcyzjzl;
    }

    public void setJtcyzjzl(Integer jtcyzjzl) {
        this.jtcyzjzl = jtcyzjzl;
    }

    public Integer getNl() {
        return nl;
    }

    public void setNl(Integer nl) {
        this.nl = nl;
    }

    public String getSfjtjjzzcy() {
        return sfjtjjzzcy;
    }

    public void setSfjtjjzzcy(String sfjtjjzzcy) {
        this.sfjtjjzzcy = sfjtjjzzcy;
    }

    @Override
    public String toString() {
        return "BdcJtcyDO{" +
                "jtcyid='" + jtcyid + '\'' +
                ", hkbbm='" + hkbbm + '\'' +
                ", jtcymc='" + jtcymc + '\'' +
                ", jtcyzjzl=" + jtcyzjzl +
                ", jtcyzjh='" + jtcyzjh + '\'' +
                ", yhzgx='" + yhzgx + '\'' +
                ", xb='" + xb + '\'' +
                ", xh=" + xh +
                ", hkszd='" + hkszd + '\'' +
                ", hkbbbh=" + hkbbbh +
                ", bz='" + bz + '\'' +
                ", dh='" + dh + '\'' +
                ", dz='" + dz + '\'' +
                ", yb='" + yb + '\'' +
                ", gzdw='" + gzdw + '\'' +
                ", dzyj='" + dzyj + '\'' +
                ", sfgyr=" + sfgyr +
                ", fzjg='" + fzjg + '\'' +
                ", sshy='" + sshy + '\'' +
                ", gj='" + gj + '\'' +
                ", nl=" + nl +
                '}';
    }
}
