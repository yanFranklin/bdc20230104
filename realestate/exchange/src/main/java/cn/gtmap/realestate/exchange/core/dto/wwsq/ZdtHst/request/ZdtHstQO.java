package cn.gtmap.realestate.exchange.core.dto.wwsq.ZdtHst.request;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date  2021/8/27
 * @description 宗地图户室图查询对象
 */
public class ZdtHstQO {

    private String bdcdyh;

    private String zl;

    private String qjgldm;

    private String slbh;

    private String bdcqzh;

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    @Override
    public String toString() {
        return "ZdtHstQO{" +
                "bdcdyh='" + bdcdyh + '\'' +
                ", zl='" + zl + '\'' +
                ", qjgldm='" + qjgldm + '\'' +
                ", slbh='" + slbh + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                '}';
    }
}
