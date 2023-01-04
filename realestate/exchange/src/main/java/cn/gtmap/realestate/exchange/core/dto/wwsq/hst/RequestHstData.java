package cn.gtmap.realestate.exchange.core.dto.wwsq.hst;

public class RequestHstData {

    private String bdcdyh;


    private String fwbm;

    private String qjgldm;

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

    @Override
    public String toString() {
        return "RequestHstData{" +
                "bdcdyh='" + bdcdyh + '\'' +
                ", fwbm='" + fwbm + '\'' +
                ", qjgldm='" + qjgldm + '\'' +
                '}';
    }
}
