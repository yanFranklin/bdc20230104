package cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.tsqxxz;

import io.swagger.annotations.ApiModel;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/9/30
 * @description 推送省厅区县销账
 */
@ApiModel(value = "TsqxxzRequest", description = "推送省厅区县销账")
public class TsqxxzRequest {


    /**
     * qxdm : 320104
     * ywh : 202208040001298
     * bdcdyh : 320104006014GB00088F00010002
     * bdcqzh : 苏（2019）宁秦不动产证明第 0007430 号
     * bdlx : 1
     * zbdzt : 0
     * sjsm : 历史遗留问题
     */

    private String qxdm;
    private String ywh;
    private String bdcdyh;
    private String bdcqzh;
    private String bdlx;
    private String zbdzt;
    private String sjsm;

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getBdlx() {
        return bdlx;
    }

    public void setBdlx(String bdlx) {
        this.bdlx = bdlx;
    }

    public String getZbdzt() {
        return zbdzt;
    }

    public void setZbdzt(String zbdzt) {
        this.zbdzt = zbdzt;
    }

    public String getSjsm() {
        return sjsm;
    }

    public void setSjsm(String sjsm) {
        this.sjsm = sjsm;
    }

    @Override
    public String toString() {
        return "TsqxxzRequest{" +
                "qxdm='" + qxdm + '\'' +
                ", ywh='" + ywh + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", bdlx='" + bdlx + '\'' +
                ", zbdzt='" + zbdzt + '\'' +
                ", sjsm='" + sjsm + '\'' +
                '}';
    }
}
