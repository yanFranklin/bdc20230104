package cn.gtmap.realestate.exchange.core.dto.nantong.sw.HtbhWithFjxx;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date  2021/4/29
 * @description 南通税务，推送核税状态和电子税票
 */
public class HtbhWithFjxx {


    /**
     * htbh : 035020160401012
     * jszt : 1
     * jssj : 2019-11-23 18:25:23
     * fjmc : xx税票.pdf
     * fjurl : http://192.168.0.10:9090/sss/ss/s
     * fjbase64 :
     */

    private String htbh;
    private String jszt;
    private String jssj;
    private String fjmc;
    private String fjurl;
    private String fjbase64;

    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    public String getJszt() {
        return jszt;
    }

    public void setJszt(String jszt) {
        this.jszt = jszt;
    }

    public String getJssj() {
        return jssj;
    }

    public void setJssj(String jssj) {
        this.jssj = jssj;
    }

    public String getFjmc() {
        return fjmc;
    }

    public void setFjmc(String fjmc) {
        this.fjmc = fjmc;
    }

    public String getFjurl() {
        return fjurl;
    }

    public void setFjurl(String fjurl) {
        this.fjurl = fjurl;
    }

    public String getFjbase64() {
        return fjbase64;
    }

    public void setFjbase64(String fjbase64) {
        this.fjbase64 = fjbase64;
    }

    @Override
    public String toString() {
        return "HtbhWithFjxx{" +
                "htbh='" + htbh + '\'' +
                ", jszt='" + jszt + '\'' +
                ", jssj='" + jssj + '\'' +
                ", fjmc='" + fjmc + '\'' +
                ", fjurl='" + fjurl + '\'' +
                ", fjbase64='" + fjbase64 + '\'' +
                '}';
    }
}
