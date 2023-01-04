package cn.gtmap.realestate.common.core.dto.register;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu/a>"
 * @version 3.0, 2022/3/14
 * @description 常州自助查询机查询登记簿结果-自然状况-房屋信息
 */
public class BdcDjbFwxxDTO {
    /**
     * 幢号
     */
    private String zh;

    /**
     * 房号
     */
    private String fh;

    /**
     * 所在层
     */
    private String szc;

    /**
     * 总层数
     */
    private String zcs;

    /**
     * 房屋结构
     */
    private String fwjg;

    /**
     * 建筑面积
     */
    private String jzmj;

    /**
     * 套内建筑面积
     */
    private String tnmj;

    /**
     * 规划用途
     */
    private String ghyt;

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }

    public String getFh() {
        return fh;
    }

    public void setFh(String fh) {
        this.fh = fh;
    }

    public String getSzc() {
        return szc;
    }

    public void setSzc(String szc) {
        this.szc = szc;
    }

    public String getZcs() {
        return zcs;
    }

    public void setZcs(String zcs) {
        this.zcs = zcs;
    }

    public String getFwjg() {
        return fwjg;
    }

    public void setFwjg(String fwjg) {
        this.fwjg = fwjg;
    }

    public String getJzmj() {
        return jzmj;
    }

    public void setJzmj(String jzmj) {
        this.jzmj = jzmj;
    }

    public String getTnmj() {
        return tnmj;
    }

    public void setTnmj(String tnmj) {
        this.tnmj = tnmj;
    }

    public String getGhyt() {
        return ghyt;
    }

    public void setGhyt(String ghyt) {
        this.ghyt = ghyt;
    }

    @Override
    public String toString() {
        return "BdcFwmxDTO{" +
                "zh='" + zh + '\'' +
                ", fh='" + fh + '\'' +
                ", szc='" + szc + '\'' +
                ", zcs='" + zcs + '\'' +
                ", fwjg='" + fwjg + '\'' +
                ", jzmj='" + jzmj + '\'' +
                ", tnmj='" + tnmj + '\'' +
                ", ghyt='" + ghyt + '\'' +
                '}';
    }
}
