package cn.gtmap.realestate.common.core.dto.accept;

/**
 * @program: realestate
 * @description: 一个业务多次创建修正流程
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-07-13 19:07
 **/
public class BdcSlXzxxPlDTO {
    private String gzlslid;

    private String slbh;

    private String lcmc;

    private String djyy;

    private String slr;

    private String qlr;

    private String zl;

    private String jdmc;


    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getLcmc() {
        return lcmc;
    }

    public void setLcmc(String lcmc) {
        this.lcmc = lcmc;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public String getSlr() {
        return slr;
    }

    public void setSlr(String slr) {
        this.slr = slr;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getJdmc() {
        return jdmc;
    }

    public void setJdmc(String jdmc) {
        this.jdmc = jdmc;
    }

    @Override
    public String toString() {
        return "BdcSlXzxxPlDTO{" +
                "gzlslid='" + gzlslid + '\'' +
                ", slbh='" + slbh + '\'' +
                ", lcmc='" + lcmc + '\'' +
                ", djyy='" + djyy + '\'' +
                ", slr='" + slr + '\'' +
                ", qlr='" + qlr + '\'' +
                ", zl='" + zl + '\'' +
                ", jdmc='" + jdmc + '\'' +
                '}';
    }
}
