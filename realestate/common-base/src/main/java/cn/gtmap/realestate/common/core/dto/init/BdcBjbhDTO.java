package cn.gtmap.realestate.common.core.dto.init;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0, 2020/12/23
 * @description 合肥办件编号
 */
public class BdcBjbhDTO {

    private String xmid;

    private String slbh;

    private String qlrmc;

    private String qlrzjh;

    private String zfxzspbh;

    private String lcmc;

    private String gzldyid;

    private String gzlslid;

    private String unid;

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getZfxzspbh() {
        return zfxzspbh;
    }

    public void setZfxzspbh(String zfxzspbh) {
        this.zfxzspbh = zfxzspbh;
    }

    public String getLcmc() {
        return lcmc;
    }

    public void setLcmc(String lcmc) {
        this.lcmc = lcmc;
    }

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    @Override
    public String toString() {
        return "BdcBjbhDTO{" +
                "xmid='" + xmid + '\'' +
                ", slbh='" + slbh + '\'' +
                ", qlrmc='" + qlrmc + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", zfxzspbh='" + zfxzspbh + '\'' +
                ", lcmc='" + lcmc + '\'' +
                ", gzldyid='" + gzldyid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", unid='" + unid + '\'' +
                '}';
    }
}
