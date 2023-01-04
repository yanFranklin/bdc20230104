package cn.gtmap.realestate.exchange.core.dto.wwsq.sczmd.request;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-27
 * @description 分页查询 的 查询条件
 */
public class SczmdRequestDTO {

    private String qlrmc;

    private String zh;

    private String zl;

    private String fjh;

    private String badh;

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getFjh() {
        return fjh;
    }

    public void setFjh(String fjh) {
        this.fjh = fjh;
    }

    public String getBadh() {
        return badh;
    }

    public void setBadh(String badh) {
        this.badh = badh;
    }
}
