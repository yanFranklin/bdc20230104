package cn.gtmap.realestate.common.core.dto.exchange.ykq.dzfpmx.response;

public class YkqFpFjxxDTO {

    /**
     * fjmc :
     * fjdx :
     * fjnr :
     * fjlx :
     */

    private String fjmc;
    private String fjdx;
    private String fjnr;
    private String fjlx;

    public String getFjmc() {
        return fjmc;
    }

    public void setFjmc(String fjmc) {
        this.fjmc = fjmc;
    }

    public String getFjdx() {
        return fjdx;
    }

    public void setFjdx(String fjdx) {
        this.fjdx = fjdx;
    }

    public String getFjnr() {
        return fjnr;
    }

    public void setFjnr(String fjnr) {
        this.fjnr = fjnr;
    }

    public String getFjlx() {
        return fjlx;
    }

    public void setFjlx(String fjlx) {
        this.fjlx = fjlx;
    }

    @Override
    public String toString() {
        return "YkqFpFjxxDTO{" +
                "fjmc='" + fjmc + '\'' +
                ", fjdx='" + fjdx + '\'' +
                ", fjnr='" + fjnr + '\'' +
                ", fjlx='" + fjlx + '\'' +
                '}';
    }
}
