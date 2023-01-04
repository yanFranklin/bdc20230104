package cn.gtmap.realestate.inquiry.core.dto;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/04/20
 * @description 权利附记信息
 */
public class BdcQlfjDTO {
    private String xmid;

    private String fj;

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    @Override
    public String toString() {
        return "BdcQlfjDTO{" +
                "xmid='" + xmid + '\'' +
                ", fj='" + fj + '\'' +
                '}';
    }
}
