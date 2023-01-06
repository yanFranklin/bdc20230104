package cn.gtmap.realestate.exchange.core.dto.wwsq.hst;

public class RequestHstData {

    private String bdcdyh;


    private String fwbm;

    private String qjgldm;

    private String slbh;

    private String bdcqzh;

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

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

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

    @Override
    public String toString() {
        return "RequestHstData{" +
                "bdcdyh='" + bdcdyh + '\'' +
                ", fwbm='" + fwbm + '\'' +
                ", qjgldm='" + qjgldm + '\'' +
                ", slbh='" + slbh + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +

                '}';
    }
}
