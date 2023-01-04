package cn.gtmap.realestate.common.core.domain.building;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/11/4
 * @description 经营权地块调查表
 */
@Table(name = "jyqdk_dcb")
public class JyqDkDcbDO {

    @Id
    @ApiModelProperty(value = "主键")
    private String jyqdkdcbIndex;

    @ApiModelProperty(value = "流出方主键")
    private String jyqdklcfIndex;

    @ApiModelProperty(value = "地块编号")
    private String dkbm;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "经营权起始日期")
    private Date jyqqsrq;

    @ApiModelProperty(value = "经营权终止日期")
    private Date jyqzzrq;

    @ApiModelProperty(value = "地块实测面积")
    private Double dkscmj;

    @ApiModelProperty(value = "地块实测面积亩")
    private Double dkscmjm;

    @ApiModelProperty(value = "地块合同面积")
    private Double dkhtmj;

    @ApiModelProperty(value = "地块合同面积亩")
    private Double dkhtmjm;

    @ApiModelProperty(value = "地块四至东")
    private String dkszd;

    @ApiModelProperty(value = "地块四至南")
    private String dkszn;

    @ApiModelProperty(value = "地块四至西")
    private String dkszx;

    @ApiModelProperty(value = "地块四至北")
    private String dkszb;

    @ApiModelProperty(value = "变更日期")
    private Date bgrq;

    @ApiModelProperty(value = "变更编号")
    private String bgbh;

    @ApiModelProperty(value = "更新时间")
    private Date gxsj;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "权属性质")
    private String qsxz;

    @ApiModelProperty(value = "土地用途")
    private String tdyt;

    @ApiModelProperty(value = "权利性质")
    private String qlxz;

    @ApiModelProperty(value = "经营用途")
    private String jyyt;

    public String getJyqdkdcbIndex() {
        return jyqdkdcbIndex;
    }

    public void setJyqdkdcbIndex(String jyqdkdcbIndex) {
        this.jyqdkdcbIndex = jyqdkdcbIndex;
    }

    public String getJyqdklcfIndex() {
        return jyqdklcfIndex;
    }

    public void setJyqdklcfIndex(String jyqdklcfIndex) {
        this.jyqdklcfIndex = jyqdklcfIndex;
    }

    public String getDkbm() {
        return dkbm;
    }

    public void setDkbm(String dkbm) {
        this.dkbm = dkbm;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public Date getJyqqsrq() {
        return jyqqsrq;
    }

    public void setJyqqsrq(Date jyqqsrq) {
        this.jyqqsrq = jyqqsrq;
    }

    public Date getJyqzzrq() {
        return jyqzzrq;
    }

    public void setJyqzzrq(Date jyqzzrq) {
        this.jyqzzrq = jyqzzrq;
    }

    public Double getDkscmj() {
        return dkscmj;
    }

    public void setDkscmj(Double dkscmj) {
        this.dkscmj = dkscmj;
    }

    public Double getDkscmjm() {
        return dkscmjm;
    }

    public void setDkscmjm(Double dkscmjm) {
        this.dkscmjm = dkscmjm;
    }

    public Double getDkhtmj() {
        return dkhtmj;
    }

    public void setDkhtmj(Double dkhtmj) {
        this.dkhtmj = dkhtmj;
    }

    public Double getDkhtmjm() {
        return dkhtmjm;
    }

    public void setDkhtmjm(Double dkhtmjm) {
        this.dkhtmjm = dkhtmjm;
    }

    public String getDkszd() {
        return dkszd;
    }

    public void setDkszd(String dkszd) {
        this.dkszd = dkszd;
    }

    public String getDkszn() {
        return dkszn;
    }

    public void setDkszn(String dkszn) {
        this.dkszn = dkszn;
    }

    public String getDkszx() {
        return dkszx;
    }

    public void setDkszx(String dkszx) {
        this.dkszx = dkszx;
    }

    public String getDkszb() {
        return dkszb;
    }

    public void setDkszb(String dkszb) {
        this.dkszb = dkszb;
    }

    public Date getBgrq() {
        return bgrq;
    }

    public void setBgrq(Date bgrq) {
        this.bgrq = bgrq;
    }

    public String getBgbh() {
        return bgbh;
    }

    public void setBgbh(String bgbh) {
        this.bgbh = bgbh;
    }

    public Date getGxsj() {
        return gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getQsxz() {
        return qsxz;
    }

    public void setQsxz(String qsxz) {
        this.qsxz = qsxz;
    }

    public String getTdyt() {
        return tdyt;
    }

    public void setTdyt(String tdyt) {
        this.tdyt = tdyt;
    }

    public String getQlxz() {
        return qlxz;
    }

    public void setQlxz(String qlxz) {
        this.qlxz = qlxz;
    }

    public String getJyyt() {
        return jyyt;
    }

    public void setJyyt(String jyyt) {
        this.jyyt = jyyt;
    }
}
