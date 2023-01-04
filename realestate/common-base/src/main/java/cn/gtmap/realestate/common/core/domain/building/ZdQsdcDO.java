package cn.gtmap.realestate.common.core.domain.building;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-19
 * @description
 */
@ApiModel(value = "ZdQsdcDO", description = "权属调查信息表")
@Table(name = "zd_qsdc")
public class ZdQsdcDO {

    @ApiModelProperty(value = "地籍号")
    private String djh;

    @ApiModelProperty(value = "地籍调查表主键")
    private String djdcbIndex;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "建立日期")
    private Date jlrq;

    @ApiModelProperty(value = "更新日期")
    private Date gxrq;

    @ApiModelProperty(value = "指界委托书")
    private String zjwts;

    @ApiModelProperty(value = "说明")
    private String sm;

    @ApiModelProperty(value = "权属调查记事")
    private String qsdcjs;

    @ApiModelProperty(value = "调查员")
    private String dcy;

    @ApiModelProperty(value = "调查日期")
    private Date dcrq;

    @ApiModelProperty(value = "宗地草图")
    private String zdct;

    @ApiModelProperty(value = "地籍勘丈记事")
    private String djkzjs;

    @ApiModelProperty(value = "勘丈员")
    private String kzy;

    @ApiModelProperty(value = "勘丈日期")
    private Date kzrq;

    @ApiModelProperty(value = "调查审核意见")
    private String dcshyj;

    @ApiModelProperty(value = "调查审核人")
    private String dcshr;

    @ApiModelProperty(value = "调查审核日期")
    private Date dcshrq;

    @ApiModelProperty(value = "界址点位说明")
    private String zygdwzsm;

    @ApiModelProperty(value = "主要权属界线走向说明")
    private String zyjxzxsm;

    @Id
    @ApiModelProperty(value = "宗地权属调查主键")
    private String zdQsdcIndex;

    @ApiModelProperty(value = "预编地籍号")
    private String ybdjh;

    @ApiModelProperty(value = "调查审核人签名")
    private String dcshrqm;


    public String getDjh() {
        return djh;
    }

    public void setDjh(String djh) {
        this.djh = djh;
    }

    public String getDjdcbIndex() {
        return djdcbIndex;
    }

    public void setDjdcbIndex(String djdcbIndex) {
        this.djdcbIndex = djdcbIndex;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Date getJlrq() {
        return jlrq;
    }

    public void setJlrq(Date jlrq) {
        this.jlrq = jlrq;
    }

    public Date getGxrq() {
        return gxrq;
    }

    public void setGxrq(Date gxrq) {
        this.gxrq = gxrq;
    }

    public String getZjwts() {
        return zjwts;
    }

    public void setZjwts(String zjwts) {
        this.zjwts = zjwts;
    }

    public String getSm() {
        return sm;
    }

    public void setSm(String sm) {
        this.sm = sm;
    }

    public String getQsdcjs() {
        return qsdcjs;
    }

    public void setQsdcjs(String qsdcjs) {
        this.qsdcjs = qsdcjs;
    }

    public String getDcy() {
        return dcy;
    }

    public void setDcy(String dcy) {
        this.dcy = dcy;
    }

    public Date getDcrq() {
        return dcrq;
    }

    public void setDcrq(Date dcrq) {
        this.dcrq = dcrq;
    }

    public String getZdct() {
        return zdct;
    }

    public void setZdct(String zdct) {
        this.zdct = zdct;
    }

    public String getDjkzjs() {
        return djkzjs;
    }

    public void setDjkzjs(String djkzjs) {
        this.djkzjs = djkzjs;
    }

    public String getKzy() {
        return kzy;
    }

    public void setKzy(String kzy) {
        this.kzy = kzy;
    }

    public Date getKzrq() {
        return kzrq;
    }

    public void setKzrq(Date kzrq) {
        this.kzrq = kzrq;
    }

    public String getDcshyj() {
        return dcshyj;
    }

    public void setDcshyj(String dcshyj) {
        this.dcshyj = dcshyj;
    }

    public String getDcshr() {
        return dcshr;
    }

    public void setDcshr(String dcshr) {
        this.dcshr = dcshr;
    }

    public Date getDcshrq() {
        return dcshrq;
    }

    public void setDcshrq(Date dcshrq) {
        this.dcshrq = dcshrq;
    }

    public String getZygdwzsm() {
        return zygdwzsm;
    }

    public void setZygdwzsm(String zygdwzsm) {
        this.zygdwzsm = zygdwzsm;
    }

    public String getZyjxzxsm() {
        return zyjxzxsm;
    }

    public void setZyjxzxsm(String zyjxzxsm) {
        this.zyjxzxsm = zyjxzxsm;
    }

    public String getZdQsdcIndex() {
        return zdQsdcIndex;
    }

    public void setZdQsdcIndex(String zdQsdcIndex) {
        this.zdQsdcIndex = zdQsdcIndex;
    }

    public String getYbdjh() {
        return ybdjh;
    }

    public void setYbdjh(String ybdjh) {
        this.ybdjh = ybdjh;
    }

    public String getDcshrqm() {
        return dcshrqm;
    }

    public void setDcshrqm(String dcshrqm) {
        this.dcshrqm = dcshrqm;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ZdQsdcDO{");
        sb.append("djh='").append(djh).append('\'');
        sb.append(", djdcbIndex='").append(djdcbIndex).append('\'');
        sb.append(", bz='").append(bz).append('\'');
        sb.append(", jlrq=").append(jlrq);
        sb.append(", gxrq=").append(gxrq);
        sb.append(", zjwts='").append(zjwts).append('\'');
        sb.append(", sm='").append(sm).append('\'');
        sb.append(", qsdcjs='").append(qsdcjs).append('\'');
        sb.append(", dcy='").append(dcy).append('\'');
        sb.append(", dcrq=").append(dcrq);
        sb.append(", zdct='").append(zdct).append('\'');
        sb.append(", djkzjs='").append(djkzjs).append('\'');
        sb.append(", kzy='").append(kzy).append('\'');
        sb.append(", kzrq=").append(kzrq);
        sb.append(", dcshyj='").append(dcshyj).append('\'');
        sb.append(", dcshr='").append(dcshr).append('\'');
        sb.append(", dcshrq=").append(dcshrq);
        sb.append(", zygdwzsm='").append(zygdwzsm).append('\'');
        sb.append(", zyjxzxsm='").append(zyjxzxsm).append('\'');
        sb.append(", zdQsdcIndex='").append(zdQsdcIndex).append('\'');
        sb.append(", ybdjh='").append(ybdjh).append('\'');
        sb.append(", dcshrqm='").append(dcshrqm).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
