package cn.gtmap.realestate.exchange.core.dto.wwsq.tdqsxxcj.resp;


public class TdqsxxcjResponseResultBodyDto {

    private String fwuuid;
    private String bdcdyh;
    private String htbh;

    public String getFwuuid() {
        return fwuuid;
    }

    public void setFwuuid(String fwuuid) {
        this.fwuuid = fwuuid;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    @Override
    public String toString() {
        return "TdqsxxcjResponseResultBodyDto{" +
                "fwuuid='" + fwuuid + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", htbh='" + htbh + '\'' +
                '}';
    }
}
