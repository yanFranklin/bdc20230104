package cn.gtmap.realestate.common.core.dto.register;

import java.util.Date;

public class BdcDkxxDTO {

    /**
     * 坐落
     */
    private String zl;

    /**
     * 不动产单元号
     */
    private String bdcdyh;

    /**
     * 宗地代码
     */
    private String dkbm;

    /**
     * 承包面积
     */
    private Double cbmj;

    /**
     * 实测面积
     */
    private Double scmj;

    /**
     * 承包起始时间
     */
    private Date cbqssj;

    /**
     * 承包结束时间
     */
    private Date cbjssj;

    /**
     * 是否基本农田
     */
    private String sfjbnt;

    /**
     * 宗地四至
     */
    private String zdszd;

    /**
     * 宗地四至
     */
    private String zdszn;

    /**
     * 宗地四至
     */
    private String zdszx;

    /**
     * 宗地四至
     */
    private String zdszb;

    /**
     * 确权情况
     */
    private String qqqk;

    /**
     * 备注
     */
    private String bz;

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getDkbm() {
        return dkbm;
    }

    public void setDkbm(String dkbm) {
        this.dkbm = dkbm;
    }

    public Double getCbmj() {
        return cbmj;
    }

    public void setCbmj(Double cbmj) {
        this.cbmj = cbmj;
    }

    public Double getScmj() {
        return scmj;
    }

    public void setScmj(Double scmj) {
        this.scmj = scmj;
    }

    public Date getCbqssj() {
        return cbqssj;
    }

    public void setCbqssj(Date cbqssj) {
        this.cbqssj = cbqssj;
    }

    public Date getCbjssj() {
        return cbjssj;
    }

    public void setCbjssj(Date cbjssj) {
        this.cbjssj = cbjssj;
    }

    public String getSfjbnt() {
        return sfjbnt;
    }

    public void setSfjbnt(String sfjbnt) {
        this.sfjbnt = sfjbnt;
    }

    public String getZdszd() {
        return zdszd;
    }

    public void setZdszd(String zdszd) {
        this.zdszd = zdszd;
    }

    public String getZdszn() {
        return zdszn;
    }

    public void setZdszn(String zdszn) {
        this.zdszn = zdszn;
    }

    public String getZdszx() {
        return zdszx;
    }

    public void setZdszx(String zdszx) {
        this.zdszx = zdszx;
    }

    public String getZdszb() {
        return zdszb;
    }

    public void setZdszb(String zdszb) {
        this.zdszb = zdszb;
    }

    public String getQqqk() {
        return qqqk;
    }

    public void setQqqk(String qqqk) {
        this.qqqk = qqqk;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @Override
    public String toString() {
        return "BdcDkxxDTO{" +
                "zl='" + zl + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", dkbm='" + dkbm + '\'' +
                ", cbmj=" + cbmj +
                ", scmj=" + scmj +
                ", cbqssj=" + cbqssj +
                ", cbjssj=" + cbjssj +
                ", sfjbnt='" + sfjbnt + '\'' +
                ", zdszd='" + zdszd + '\'' +
                ", zdszn='" + zdszn + '\'' +
                ", zdszx='" + zdszx + '\'' +
                ", zdszb='" + zdszb + '\'' +
                ", qqqk='" + qqqk + '\'' +
                ", bz='" + bz + '\'' +
                '}';
    }
}
