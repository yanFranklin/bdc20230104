package cn.gtmap.realestate.exchange.core.dto.nantong.gjj.request;

public class GjjRequestData {
    /**
     * cqzh : 产权证号
     * xm : 产权人姓名
     * zjhm : 证件号码
     */

    private String cqzh;
    private String xm;
    private String zjhm;

    public String getCqzh() {
        return cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }

    @Override
    public String toString() {
        return "GjjRequestData{" +
                "cqzh='" + cqzh + '\'' +
                ", xm='" + xm + '\'' +
                ", zjhm='" + zjhm + '\'' +
                '}';
    }
}
