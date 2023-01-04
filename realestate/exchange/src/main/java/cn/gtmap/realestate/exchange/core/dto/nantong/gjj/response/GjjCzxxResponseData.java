package cn.gtmap.realestate.exchange.core.dto.nantong.gjj.response;

public class GjjCzxxResponseData {

    /**
     * fwzl : 房屋坐落
     * fwyt : 房屋用途
     * djrq : 登记日期
     * bdcdjzmh : 不动产权证号
     */

    private String fwzl;
    private String fwyt;
    private String djrq;
    private String bdcdjzmh;

    public String getFwzl() {
        return fwzl;
    }

    public void setFwzl(String fwzl) {
        this.fwzl = fwzl;
    }

    public String getFwyt() {
        return fwyt;
    }

    public void setFwyt(String fwyt) {
        this.fwyt = fwyt;
    }

    public String getDjrq() {
        return djrq;
    }

    public void setDjrq(String djrq) {
        this.djrq = djrq;
    }

    public String getBdcdjzmh() {
        return bdcdjzmh;
    }

    public void setBdcdjzmh(String bdcdjzmh) {
        this.bdcdjzmh = bdcdjzmh;
    }
}
