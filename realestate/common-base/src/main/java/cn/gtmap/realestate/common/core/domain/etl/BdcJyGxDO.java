package cn.gtmap.realestate.common.core.domain.etl;

import javax.persistence.Table;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/23 11:31
 */
@Table(name = "BDC_JY_GX")
public class BdcJyGxDO {

    private String bdcdyh;

    private String fwbm;

    private String xmid;

    private String djbh;

    private String sjly;

    private String gxlx;

    private String zjsm;

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getFwbm() {
        return fwbm;
    }

    public void setFwbm(String fwbm) {
        this.fwbm = fwbm;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getDjbh() {
        return djbh;
    }

    public void setDjbh(String djbh) {
        this.djbh = djbh;
    }

    public String getSjly() {
        return sjly;
    }

    public void setSjly(String sjly) {
        this.sjly = sjly;
    }

    public String getGxlx() {
        return gxlx;
    }

    public void setGxlx(String gxlx) {
        this.gxlx = gxlx;
    }

    public String getZjsm() {
        return zjsm;
    }

    public void setZjsm(String zjsm) {
        this.zjsm = zjsm;
    }

    @Override
    public String toString() {
        return "BdcJyGxDO{" +
                "bdcdyh='" + bdcdyh + '\'' +
                ", fwbm='" + fwbm + '\'' +
                ", xmid='" + xmid + '\'' +
                ", djbh='" + djbh + '\'' +
                ", sjly='" + sjly + '\'' +
                ", gxlx='" + gxlx + '\'' +
                ", zjsm='" + zjsm + '\'' +
                '}';
    }
}
