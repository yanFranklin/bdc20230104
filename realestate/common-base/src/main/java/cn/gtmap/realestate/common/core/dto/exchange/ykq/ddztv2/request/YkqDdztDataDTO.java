package cn.gtmap.realestate.common.core.dto.exchange.ykq.ddztv2.request;

import io.swagger.annotations.ApiModel;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/11/29
 * @description 一卡清 查询订单信息Data
 */
@ApiModel(value = "YkqDdztDataDTO", description = "一卡清查询订单信息DATA")
public class YkqDdztDataDTO {


    /**
     * ddbh : 111
     * ddje : 21
     * jfzt : 1
     * ddzt : 1
     * nwslbh : 1121
     * url : 1
     * sfglid : 1
     * sfglidlx : 1
     * ze : 1
     * dsfddbh : 1
     * jfrxm : 1
     * qlrlb : 1
     */

    private String ddbh;
    private String ddje;
    private String jfzt;
    private String ddzt;
    private String nwslbh;
    private String url;
    private String sfglid;
    private String sfglidlx;
    private String ze;
    private String dsfddbh;
    private String jfrxm;
    private String qlrlb;

    public String getDdbh() {
        return ddbh;
    }

    public void setDdbh(String ddbh) {
        this.ddbh = ddbh;
    }

    public String getDdje() {
        return ddje;
    }

    public void setDdje(String ddje) {
        this.ddje = ddje;
    }

    public String getJfzt() {
        return jfzt;
    }

    public void setJfzt(String jfzt) {
        this.jfzt = jfzt;
    }

    public String getDdzt() {
        return ddzt;
    }

    public void setDdzt(String ddzt) {
        this.ddzt = ddzt;
    }

    public String getNwslbh() {
        return nwslbh;
    }

    public void setNwslbh(String nwslbh) {
        this.nwslbh = nwslbh;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSfglid() {
        return sfglid;
    }

    public void setSfglid(String sfglid) {
        this.sfglid = sfglid;
    }

    public String getSfglidlx() {
        return sfglidlx;
    }

    public void setSfglidlx(String sfglidlx) {
        this.sfglidlx = sfglidlx;
    }

    public String getZe() {
        return ze;
    }

    public void setZe(String ze) {
        this.ze = ze;
    }

    public String getDsfddbh() {
        return dsfddbh;
    }

    public void setDsfddbh(String dsfddbh) {
        this.dsfddbh = dsfddbh;
    }

    public String getJfrxm() {
        return jfrxm;
    }

    public void setJfrxm(String jfrxm) {
        this.jfrxm = jfrxm;
    }

    public String getQlrlb() {
        return qlrlb;
    }

    public void setQlrlb(String qlrlb) {
        this.qlrlb = qlrlb;
    }

    @Override
    public String toString() {
        return "YkqDdztDataDTO{" +
                "ddbh='" + ddbh + '\'' +
                ", ddje='" + ddje + '\'' +
                ", jfzt='" + jfzt + '\'' +
                ", ddzt='" + ddzt + '\'' +
                ", nwslbh='" + nwslbh + '\'' +
                ", url='" + url + '\'' +
                ", sfglid='" + sfglid + '\'' +
                ", sfglidlx='" + sfglidlx + '\'' +
                ", ze='" + ze + '\'' +
                ", dsfddbh='" + dsfddbh + '\'' +
                ", jfrxm='" + jfrxm + '\'' +
                ", qlrlb='" + qlrlb + '\'' +
                '}';
    }
}
