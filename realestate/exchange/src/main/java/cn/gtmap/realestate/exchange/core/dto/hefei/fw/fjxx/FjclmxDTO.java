package cn.gtmap.realestate.exchange.core.dto.hefei.fw.fjxx;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0  2022-10-19
 * @description 材料信息
 */
public class FjclmxDTO {

    private String fjmc;

    private String fjurl;

    private String fjid;


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


    @Override
    public String toString() {
        return "FjclmxDTO{" +
                "fjmc='" + fjmc + '\'' +
                ", fjurl='" + fjurl + '\'' +
                ", fjid='" + fjid + '\'' +
                '}';
    }
}
