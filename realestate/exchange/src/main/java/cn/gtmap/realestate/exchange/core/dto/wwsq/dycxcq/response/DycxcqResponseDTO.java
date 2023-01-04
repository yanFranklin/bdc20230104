package cn.gtmap.realestate.exchange.core.dto.wwsq.dycxcq.response;

/**
 * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
 * @version 1.0  2022-05-31
 * @description
 */
public class DycxcqResponseDTO {


    /**
     * 受理编号
     */
    private String slbh;

    /**
     * 抵押人
     */
    private String dyr;

    /**
     * 不动产单元号
     */
    private String bdcdyh;

    /**
     * 坐落
     */
    private String zl;

    /**
     * 宗地宗海面积
     */
    private String zdzhmj;

    /**
     * 宗地宗海用途
     */
    private String zdzhyt;

    /**
     * 权利性质
     */
    private String qlxz;

    /**
     * 规划用途
     */
    private String ghyt;

    /**
     * 建筑面积
     */
    private String jzmj;

    /**
     * 不动产权证号
     */
    private String bdcqzh;

    /**
     * 幢号
     */
    private String zh;

    /**
     * 房间号
     */
    private String fjh;

    /**
     * 登记时间
     */
    private String djsj;

    /**
     * 房屋结构
     */
    private String fwjg;

    /**
     * 房屋性质
     */
    private String fwxz;

    /**
     * 专有建筑面积
     */
    private String zyjzmj;

    /**
     * 分摊建筑面积
     */
    private String ftjzmj;

    /**
     * 附记
     */
    private String fj;

    /**
     * 查封状态
     */
    private String cfzt;

    /**
     * 抵押权人
     */
    private String dyqr;

    public String getDyqr() {
        return dyqr;
    }

    public void setDyqr(String dyqr) {
        this.dyqr = dyqr;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getDyr() {
        return dyr;
    }

    public void setDyr(String dyr) {
        this.dyr = dyr;
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

    public void setZl(String zl) { this.zl = zl; }

    public String getZdzhmj() { return zdzhmj; }

    public void setZdzhmj(String zdzhmj) { this.zdzhmj = zdzhmj; }

    public String getZdzhyt() { return zdzhyt; }

    public void setZdzhyt(String zdzhyt) { this.zdzhyt = zdzhyt; }

    public String getQlxz() { return qlxz; }

    public void setQlxz(String qlxz) { this.qlxz = qlxz; }

    public String getGhyt() { return ghyt; }

    public void setGhyt(String ghyt) { this.ghyt = ghyt; }

    public String getJzmj() { return jzmj; }

    public void setJzmj(String jzmj) { this.jzmj = jzmj; }

    public String getBdcqzh() { return bdcqzh; }

    public void setBdcqzh(String bdcqzh) { this.bdcqzh = bdcqzh; }

    public String getZh() { return zh; }

    public void setZh(String zh) { this.zh = zh; }

    public String getFjh() { return fjh; }

    public void setFjh(String fjh) { this.fjh = fjh; }

    public String getDjsj() { return djsj; }

    public void setDjsj(String djsj) { this.djsj = djsj; }

    public String getFwjg() { return fwjg; }

    public void setFwjg(String fwjg) { this.fwjg = fwjg; }

    public String getFwxz() { return fwxz; }

    public void setFwxz(String fwxz) { this.fwxz = fwxz; }

    public String getZyjzmj() { return zyjzmj; }

    public void setZyjzmj(String zyjzmj) { this.zyjzmj = zyjzmj; }

    public String getFtjzmj() { return ftjzmj; }

    public void setFtjzmj(String ftjzmj) { this.ftjzmj = ftjzmj; }

    public String getFj() { return fj; }

    public void setFj(String fj) { this.fj = fj; }

    public String getCfzt() { return cfzt; }

    public void setCfzt(String cfzt) { this.cfzt = cfzt; }

    @Override
    public String toString() {
        return "DycxcqResponseDTO{" +
                "dyr='" + dyr + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", zl='" + zl + '\'' +
                ", zdzhmj='" + zdzhmj + '\'' +
                ", zdzhyt='" + zdzhyt + '\'' +
                ", qlxz='" + qlxz + '\'' +
                ", ghyt='" + ghyt + '\'' +
                ", jzmj='" + jzmj + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", zh='" + zh + '\'' +
                ", fjh='" + fjh + '\'' +
                ", djsj='" + djsj + '\'' +
                ", fwjg='" + fwjg + '\'' +
                ", fwxz='" + fwxz + '\'' +
                ", zyjzmj='" + zyjzmj + '\'' +
                ", ftjzmj='" + ftjzmj + '\'' +
                ", fj='" + fj + '\'' +
                ", cfzt='" + cfzt + '\'' +
                '}';
    }
}
