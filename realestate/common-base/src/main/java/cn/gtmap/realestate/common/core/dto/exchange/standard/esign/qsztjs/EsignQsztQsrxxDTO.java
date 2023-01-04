package cn.gtmap.realestate.common.core.dto.exchange.standard.esign.qsztjs;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/10/21
 * @description 签署状态接受  签署人信息DTO
 */
public class EsignQsztQsrxxDTO {


    /**
     * xm :
     * zjlx :
     * zjh :
     * qsrlb :
     * lxdh :
     * qmsx :
     * qszt :
     * qssbyy :
     */

    private String xm;
    private String zjlx;
    private String zjh;
    private String qsrlb;
    private String lxdh;
    private String qmsx;
    private String qszt;
    private String qssbyy;

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getZjlx() {
        return zjlx;
    }

    public void setZjlx(String zjlx) {
        this.zjlx = zjlx;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    public String getQsrlb() {
        return qsrlb;
    }

    public void setQsrlb(String qsrlb) {
        this.qsrlb = qsrlb;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getQmsx() {
        return qmsx;
    }

    public void setQmsx(String qmsx) {
        this.qmsx = qmsx;
    }

    public String getQszt() {
        return qszt;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
    }

    public String getQssbyy() {
        return qssbyy;
    }

    public void setQssbyy(String qssbyy) {
        this.qssbyy = qssbyy;
    }

    @Override
    public String toString() {
        return "EsignQsztQsrxxDTO{" +
                "xm='" + xm + '\'' +
                ", zjlx='" + zjlx + '\'' +
                ", zjh='" + zjh + '\'' +
                ", qsrlb='" + qsrlb + '\'' +
                ", lxdh='" + lxdh + '\'' +
                ", qmsx='" + qmsx + '\'' +
                ", qszt='" + qszt + '\'' +
                ", qssbyy='" + qssbyy + '\'' +
                '}';
    }
}
