package cn.gtmap.realestate.exchange.core.dto.wwsq.init;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-06
 * @description
 */
public class InitFjxxClnr {

    private String fjurl;

    private String fjmc;

    private String fjid;

    private String csfs;

    private String fjnr;

    public String getFjnr() {
        return fjnr;
    }

    public void setFjnr(String fjnr) {
        this.fjnr = fjnr;
    }

    public String getFjurl() {
        return fjurl;
    }

    public void setFjurl(String fjurl) {
        this.fjurl = fjurl;
    }

    public String getFjmc() {
        return fjmc;
    }

    public void setFjmc(String fjmc) {
        this.fjmc = fjmc;
    }

    public String getFjid() {
        return fjid;
    }

    public void setFjid(String fjid) {
        this.fjid = fjid;
    }

    public String getCsfs() {
        return csfs;
    }

    public void setCsfs(String csfs) {
        this.csfs = csfs;
    }

    @Override
    public String toString() {
        return "InitFjxxClnr{" +
                "fjurl='" + fjurl + '\'' +
                ", fjmc='" + fjmc + '\'' +
                ", fjid='" + fjid + '\'' +
                ", csfs='" + csfs + '\'' +
                ", fjnr='" + fjnr + '\'' +
                '}';
    }
}
