package cn.gtmap.realestate.common.core.domain.building;

import io.swagger.annotations.ApiModel;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-22
 * @description 构筑物 调查信息
 */
@ApiModel(value = "GzwDcbDO", description = "构筑物 调查信息")
@Table(name = "gzw_dcb")
public class GzwDcbDO {

    /**
     * 主键
     */
    @Id
    private String gzwDcbIndex;

    /**
     * 不动产单元号
     */
    private String bdcdyh;

    /**
     * 宗地宗海代码
     */
    private String zdzhdm;

    /**
     * 构筑物名称
     */
    private String gzwmc;

    /**
     * 构筑物编号
     */
    private Long gzwbh;

    /**
     * 坐落
     */
    private String zl;

    /**
     * 构筑物类型
     */
    private String gzwlx;

    /**
     * 构筑物规划用途
     */
    private String gzwghyt;

    /**
     * 竣工时间
     */
    private Date jgsj;

    /**
     * 档案号
     */
    private String dah;

    /**
     * 面积
     */
    private Double mj;

    /**
     * 共有情况
     */
    private String gyqk;

    /**
     * 备注
     */
    private String bz;

    /**
     * 面积单位
     */
    private String mjdw;

    /**
     * 状态
     */
    private String zt;

    /**
     * 变更编号
     */
    private String bgbh;

    /**
     * 权属性质
     */
    private String qsxz;

    public String getGzwDcbIndex() {
        return gzwDcbIndex;
    }

    public void setGzwDcbIndex(String gzwDcbIndex) {
        this.gzwDcbIndex = gzwDcbIndex;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZdzhdm() {
        return zdzhdm;
    }

    public void setZdzhdm(String zdzhdm) {
        this.zdzhdm = zdzhdm;
    }

    public String getGzwmc() {
        return gzwmc;
    }

    public void setGzwmc(String gzwmc) {
        this.gzwmc = gzwmc;
    }

    public Long getGzwbh() {
        return gzwbh;
    }

    public void setGzwbh(Long gzwbh) {
        this.gzwbh = gzwbh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getGzwlx() {
        return gzwlx;
    }

    public void setGzwlx(String gzwlx) {
        this.gzwlx = gzwlx;
    }

    public String getGzwghyt() {
        return gzwghyt;
    }

    public void setGzwghyt(String gzwghyt) {
        this.gzwghyt = gzwghyt;
    }

    public Date getJgsj() {
        return jgsj;
    }

    public void setJgsj(Date jgsj) {
        this.jgsj = jgsj;
    }

    public String getDah() {
        return dah;
    }

    public void setDah(String dah) {
        this.dah = dah;
    }

    public Double getMj() {
        return mj;
    }

    public void setMj(Double mj) {
        this.mj = mj;
    }

    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getMjdw() {
        return mjdw;
    }

    public void setMjdw(String mjdw) {
        this.mjdw = mjdw;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public String getBgbh() {
        return bgbh;
    }

    public void setBgbh(String bgbh) {
        this.bgbh = bgbh;
    }

    public String getQsxz() {
        return qsxz;
    }

    public void setQsxz(String qsxz) {
        this.qsxz = qsxz;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GzwDcbDO{");
        sb.append("gzwDcbIndex='").append(gzwDcbIndex).append('\'');
        sb.append(", bdcdyh='").append(bdcdyh).append('\'');
        sb.append(", zdzhdm='").append(zdzhdm).append('\'');
        sb.append(", gzwmc='").append(gzwmc).append('\'');
        sb.append(", gzwbh=").append(gzwbh);
        sb.append(", zl='").append(zl).append('\'');
        sb.append(", gzwlx='").append(gzwlx).append('\'');
        sb.append(", gzwghyt='").append(gzwghyt).append('\'');
        sb.append(", jgsj=").append(jgsj);
        sb.append(", dah='").append(dah).append('\'');
        sb.append(", mj=").append(mj);
        sb.append(", gyqk='").append(gyqk).append('\'');
        sb.append(", bz='").append(bz).append('\'');
        sb.append(", mjdw='").append(mjdw).append('\'');
        sb.append(", zt='").append(zt).append('\'');
        sb.append(", bgbh='").append(bgbh).append('\'');
        sb.append(", qsxz='").append(qsxz).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
