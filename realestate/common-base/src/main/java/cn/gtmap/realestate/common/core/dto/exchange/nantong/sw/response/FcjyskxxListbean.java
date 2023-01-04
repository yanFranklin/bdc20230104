package cn.gtmap.realestate.common.core.dto.exchange.nantong.sw.response;

public class FcjyskxxListbean {

    /**
     * FHM : 返回码
     * FHXX : 返回信息
     * SJBH : 收件编号
     * HTBH : 合同编号
     * JYUUID : 交易编号
     * FWUUID : 房屋编号
     */

    private String fhm;
    private String fhxx;
    private String sjbh;
    private String htbh;
    private String jyuuid;
    private String fwuuid;

    public String getFhm() {
        return fhm;
    }

    public void setFhm(String fhm) {
        this.fhm = fhm;
    }

    public String getFhxx() {
        return fhxx;
    }

    public void setFhxx(String fhxx) {
        this.fhxx = fhxx;
    }

    public String getSjbh() {
        return sjbh;
    }

    public void setSjbh(String sjbh) {
        this.sjbh = sjbh;
    }

    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    public String getJyuuid() {
        return jyuuid;
    }

    public void setJyuuid(String jyuuid) {
        this.jyuuid = jyuuid;
    }

    public String getFwuuid() {
        return fwuuid;
    }

    public void setFwuuid(String fwuuid) {
        this.fwuuid = fwuuid;
    }

    @Override
    public String toString() {
        return "FcjyskxxListbean{" +
                "fhm='" + fhm + '\'' +
                ", fhxx='" + fhxx + '\'' +
                ", sjbh='" + sjbh + '\'' +
                ", htbh='" + htbh + '\'' +
                ", jyuuid='" + jyuuid + '\'' +
                ", fwuuid='" + fwuuid + '\'' +
                '}';
    }
}
