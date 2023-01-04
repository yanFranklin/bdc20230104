package cn.gtmap.realestate.common.core.dto.exchange.wwsq;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-05
 * @description
 */
public class FjclmxDTO {

    private String fjmc;

    private String fjurl;

    private String fjid;

    // 附件内容 base64位code
    private String fjnr;

    // 传输方式 ftp
    private String csfs;

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

    public String getFjid() {
        return fjid;
    }

    public void setFjid(String fjid) {
        this.fjid = fjid;
    }

    public String getFjnr() {
        return fjnr;
    }

    public void setFjnr(String fjnr) {
        this.fjnr = fjnr;
    }

    public String getCsfs() {
        return csfs;
    }

    public void setCsfs(String csfs) {
        this.csfs = csfs;
    }

    @Override
    public String toString() {
        return "FjclmxDTO{" +
                "fjmc='" + fjmc + '\'' +
                ", fjurl='" + fjurl + '\'' +
                ", fjid='" + fjid + '\'' +
                ", fjnr='" + fjnr + '\'' +
                ", csfs='" + csfs + '\'' +
                '}';
    }
}
