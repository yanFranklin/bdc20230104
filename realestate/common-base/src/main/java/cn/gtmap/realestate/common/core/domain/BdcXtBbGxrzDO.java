package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 系统版本更新日志
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/5/21 10:44
 */
@Table(name = "BDC_XT_BB_GXRZ")
public class BdcXtBbGxrzDO {
    @Id
    @ApiModelProperty(value = "更新日志 ID")
    private String gxrzid;
    @ApiModelProperty(value = "版本 ID")
    private String bbid;
    @ApiModelProperty(value = "版本编号")
    private String bbbh;
    @ApiModelProperty(value = "更新类型")
    private String gxlx;
    @ApiModelProperty(value = "更新说明")
    private String gxsm;
    @ApiModelProperty(value = "提需求人 ID")
    private String txqrid;
    @ApiModelProperty(value = "提需求人")
    private String txqr;
    @ApiModelProperty(value = "开发人")
    private String kfr;
    @ApiModelProperty(value = "测试人")
    private String csr;
    @ApiModelProperty(value = "完成时间")
    private Date wcsj;

    public String getBbid() {
        return bbid;
    }

    public void setBbid(String bbid) {
        this.bbid = bbid;
    }

    public String getGxrzid() {
        return gxrzid;
    }

    public void setGxrzid(String gxrzid) {
        this.gxrzid = gxrzid;
    }

    public String getBbbh() {
        return bbbh;
    }

    public void setBbbh(String bbbh) {
        this.bbbh = bbbh;
    }

    public String getGxlx() {
        return gxlx;
    }

    public void setGxlx(String gxlx) {
        this.gxlx = gxlx;
    }

    public String getGxsm() {
        return gxsm;
    }

    public void setGxsm(String gxsm) {
        this.gxsm = gxsm;
    }

    public String getTxqrid() {
        return txqrid;
    }

    public void setTxqrid(String txqrid) {
        this.txqrid = txqrid;
    }

    public String getTxqr() {
        return txqr;
    }

    public void setTxqr(String txqr) {
        this.txqr = txqr;
    }

    public String getKfr() {
        return kfr;
    }

    public void setKfr(String kfr) {
        this.kfr = kfr;
    }

    public String getCsr() {
        return csr;
    }

    public void setCsr(String csr) {
        this.csr = csr;
    }

    public Date getWcsj() {
        return wcsj;
    }

    public void setWcsj(Date wcsj) {
        this.wcsj = wcsj;
    }

    @Override
    public String toString() {
        return "BdcXtBbGxrzDO{" +
                "gxrzid='" + gxrzid + '\'' +
                ", bbid='" + bbid + '\'' +
                ", bbbh='" + bbbh + '\'' +
                ", gxlx='" + gxlx + '\'' +
                ", gxsm='" + gxsm + '\'' +
                ", txqrid='" + txqrid + '\'' +
                ", txqr='" + txqr + '\'' +
                ", kfr='" + kfr + '\'' +
                ", csr='" + csr + '\'' +
                ", wcsj=" + wcsj +
                '}';
    }
}
