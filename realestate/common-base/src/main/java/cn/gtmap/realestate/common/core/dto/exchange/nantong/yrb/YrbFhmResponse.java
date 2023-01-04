package cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/8
 * @description 返回实体
 */
public class YrbFhmResponse {


    /**
     * fhm : 返回码
     * fhxx : 返回信息
     * sjbh : 收件编号
     * htbh : 合同编号
     * jyuuid : 交易编号
     * fwuuid : 房屋编号
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
}
