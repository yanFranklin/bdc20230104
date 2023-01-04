package cn.gtmap.realestate.exchange.core.dto.bengbu.djxx.ywxqxx.response;

/**
 * @description 不动产查封信息
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @date 2022/12/22 18:04
 */
public class BdcCfxxDTO {

    /**
     * 不动产业务号
     */
    private String bdcywh;

    /**
     * 业务事项名称
     */
    private String ywsxmc;

    /**
     * 查封类型
     */
    private String cflx;

    /**
     * 查封起止时间
     */
    private String cfqzsj;

    /**
     * 申请执行人
     */
    private String sqzxr;

    /**
     * 被执行人
     */
    private String bzxr;

    /**
     * 执行单位
     */
    private String zxdw;

    /**
     * 文书号
     */
    private String wsh;

    public String getBdcywh() {
        return bdcywh;
    }

    public void setBdcywh(String bdcywh) {
        this.bdcywh = bdcywh;
    }

    public String getYwsxmc() {
        return ywsxmc;
    }

    public void setYwsxmc(String ywsxmc) {
        this.ywsxmc = ywsxmc;
    }

    public String getCflx() {
        return cflx;
    }

    public void setCflx(String cflx) {
        this.cflx = cflx;
    }

    public String getCfqzsj() {
        return cfqzsj;
    }

    public void setCfqzsj(String cfqzsj) {
        this.cfqzsj = cfqzsj;
    }

    public String getSqzxr() {
        return sqzxr;
    }

    public void setSqzxr(String sqzxr) {
        this.sqzxr = sqzxr;
    }

    public String getBzxr() {
        return bzxr;
    }

    public void setBzxr(String bzxr) {
        this.bzxr = bzxr;
    }

    public String getZxdw() {
        return zxdw;
    }

    public void setZxdw(String zxdw) {
        this.zxdw = zxdw;
    }

    public String getWsh() {
        return wsh;
    }

    public void setWsh(String wsh) {
        this.wsh = wsh;
    }

    @Override
    public String toString() {
        return "BdcCfxxDTO{" +
                "bdcywh='" + bdcywh + '\'' +
                ", ywsxmc='" + ywsxmc + '\'' +
                ", cflx='" + cflx + '\'' +
                ", cfqzsj='" + cfqzsj + '\'' +
                ", sqzxr='" + sqzxr + '\'' +
                ", bzxr='" + bzxr + '\'' +
                ", zxdw='" + zxdw + '\'' +
                ", wsh='" + wsh + '\'' +
                '}';
    }
}
