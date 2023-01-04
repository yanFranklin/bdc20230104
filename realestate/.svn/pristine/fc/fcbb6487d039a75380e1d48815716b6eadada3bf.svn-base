package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.StringJoiner;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-09-06
 * @description 不动产发票号
 */
@Table(name = "BDC_FPH")
@ApiModel(value = "BdcFphDO", description = "不动产发票号")
public class BdcFphDO implements Serializable {
    private static final long serialVersionUID = -7738203957345589123L;
    @Id
    @ApiModelProperty(value = "发票号id")
    private String fphid;
    @ApiModelProperty(value = "年份")
    private String nf;
    @ApiModelProperty(value = "区县代码")
    private String qxdm;
    @ApiModelProperty(value = "发票号")
    private String fph;
    @ApiModelProperty(value = "使用情况")
    private Integer syqk;
    @ApiModelProperty(value = "领取人")
    private String lqr;
    @ApiModelProperty(value = "领取人ID")
    private String lqrid;
    @ApiModelProperty(value = "领取部门")
    private String lqbm;
    @ApiModelProperty(value = "领取部门ID")
    private String lqbmid;
    @ApiModelProperty(value = "创建人")
    private String cjr;
    @ApiModelProperty(value = "创建人ID")
    private String cjrid;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间", example = "2018-10-01 12:18:48")
    private Date cjsj;
    @ApiModelProperty(value = "收费信息id")
    private String sfxxid;
    @ApiModelProperty(value = "收费项目id")
    private String sfxmid;
    @ApiModelProperty(value = "备注")
    private String bz;
    @ApiModelProperty(value = "使用明细id")
    private String fphsymxid;
    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "自助机标识")
    private Integer zzjfbs;

    @ApiModelProperty(value = "fplx")
    private Integer fplx;

    public String getFphid() {
        return fphid;
    }

    public void setFphid(String fphid) {
        this.fphid = fphid;
    }

    public String getNf() {
        return nf;
    }

    public void setNf(String nf) {
        this.nf = nf;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getFph() {
        return fph;
    }

    public void setFph(String fph) {
        this.fph = fph;
    }

    public Integer getSyqk() {
        return syqk;
    }

    public void setSyqk(Integer syqk) {
        this.syqk = syqk;
    }

    public String getLqr() {
        return lqr;
    }

    public void setLqr(String lqr) {
        this.lqr = lqr;
    }

    public String getLqrid() {
        return lqrid;
    }

    public void setLqrid(String lqrid) {
        this.lqrid = lqrid;
    }

    public String getLqbm() {
        return lqbm;
    }

    public void setLqbm(String lqbm) {
        this.lqbm = lqbm;
    }

    public String getLqbmid() {
        return lqbmid;
    }

    public void setLqbmid(String lqbmid) {
        this.lqbmid = lqbmid;
    }

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    public String getCjrid() {
        return cjrid;
    }

    public void setCjrid(String cjrid) {
        this.cjrid = cjrid;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public String getSfxxid() {
        return sfxxid;
    }

    public void setSfxxid(String sfxxid) {
        this.sfxxid = sfxxid;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getFphsymxid() {
        return fphsymxid;
    }

    public void setFphsymxid(String fphsymxid) {
        this.fphsymxid = fphsymxid;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public Integer getZzjfbs() {
        return zzjfbs;
    }

    public void setZzjfbs(Integer zzjfbs) {
        this.zzjfbs = zzjfbs;
    }

    public Integer getFplx() {
        return fplx;
    }

    public void setFplx(Integer fplx) {
        this.fplx = fplx;
    }

    public String getSfxmid() {
        return sfxmid;
    }

    public void setSfxmid(String sfxmid) {
        this.sfxmid = sfxmid;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BdcFphDO.class.getSimpleName() + "[", "]")
                .add("fphid='" + fphid + "'")
                .add("nf='" + nf + "'")
                .add("qxdm='" + qxdm + "'")
                .add("fph='" + fph + "'")
                .add("syqk=" + syqk)
                .add("lqr='" + lqr + "'")
                .add("lqrid='" + lqrid + "'")
                .add("lqbm='" + lqbm + "'")
                .add("lqbmid='" + lqbmid + "'")
                .add("cjr='" + cjr + "'")
                .add("cjrid='" + cjrid + "'")
                .add("cjsj=" + cjsj)
                .add("sfxxid='" + sfxxid + "'")
                .add("sfxmid='" + sfxmid + "'")
                .add("bz='" + bz + "'")
                .add("fphsymxid='" + fphsymxid + "'")
                .add("slbh='" + slbh + "'")
                .add("zzjfbs=" + zzjfbs)
                .add("fplx=" + fplx)
                .toString();
    }
}
