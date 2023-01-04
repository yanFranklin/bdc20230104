package cn.gtmap.realestate.exchange.core.dto.wwsq.init.zydy;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/7/1
 * @description 更正信息
 */
public class InitZyDyRequestGzxx {


    /**
     * gzdjlxdm :
     * gzdjlxmc :
     * gzzt :
     * gzyj :
     * gznr :
     */

    private String gzdjlxdm;
    private String gzdjlxmc;
    private String gzzt;
    private String gzyj;
    private String gznr;

    public String getGzdjlxdm() {
        return gzdjlxdm;
    }

    public void setGzdjlxdm(String gzdjlxdm) {
        this.gzdjlxdm = gzdjlxdm;
    }

    public String getGzdjlxmc() {
        return gzdjlxmc;
    }

    public void setGzdjlxmc(String gzdjlxmc) {
        this.gzdjlxmc = gzdjlxmc;
    }

    public String getGzzt() {
        return gzzt;
    }

    public void setGzzt(String gzzt) {
        this.gzzt = gzzt;
    }

    public String getGzyj() {
        return gzyj;
    }

    public void setGzyj(String gzyj) {
        this.gzyj = gzyj;
    }

    public String getGznr() {
        return gznr;
    }

    public void setGznr(String gznr) {
        this.gznr = gznr;
    }

    @Override
    public String toString() {
        return "InitZyDyRequestGzxx{" +
                "gzdjlxdm='" + gzdjlxdm + '\'' +
                ", gzdjlxmc='" + gzdjlxmc + '\'' +
                ", gzzt='" + gzzt + '\'' +
                ", gzyj='" + gzyj + '\'' +
                ", gznr='" + gznr + '\'' +
                '}';
    }
}
