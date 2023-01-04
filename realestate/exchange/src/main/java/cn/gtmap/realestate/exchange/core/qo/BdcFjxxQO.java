package cn.gtmap.realestate.exchange.core.qo;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0  2022/10/18
 * @description 查询附件
 */
public class BdcFjxxQO {
    private String xmid;
    private String wjjmc;
    private String slbh;

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getWjjmc() {
        return wjjmc;
    }

    public void setWjjmc(String wjjmc) {
        this.wjjmc = wjjmc;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    @Override
    public String toString() {
        return "BdcFjxxQO{" +
                "xmid='" + xmid + '\'' +
                ", wjjmc='" + wjjmc + '\'' +
                ", slbh='" + slbh + '\'' +
                '}';
    }
}
