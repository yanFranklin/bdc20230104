package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2021/2/23
 * @description 受理需求流转信息DO
 */
@Table(name = "BDC_SL_XQXX")
@ApiModel(value = "BdcSlXqxxDO", description = "受理需求流转信息")
public class BdcSlXqxxDO {
    @Id
    @ApiModelProperty(value = "主键")
    private String xqxxid;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "需求名称")
    private String xqmc;

    @ApiModelProperty(value = "需求内容")
    private String xqnr;

    @ApiModelProperty(value = "修改状态")
    private Integer xgzt;

    @ApiModelProperty(value = "修改完成时间")
    private Date xgwcsj;

    @ApiModelProperty(value = "确认状态")
    private Integer qrzt;

    @ApiModelProperty(value = "确认完成时间")
    private Date qrwcsj;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    public String getXqxxid() {
        return xqxxid;
    }

    public void setXqxxid(String xqxxid) {
        this.xqxxid = xqxxid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getXqmc() {
        return xqmc;
    }

    public void setXqmc(String xqmc) {
        this.xqmc = xqmc;
    }

    public String getXqnr() {
        return xqnr;
    }

    public void setXqnr(String xqnr) {
        this.xqnr = xqnr;
    }

    public Integer getXgzt() {
        return xgzt;
    }

    public void setXgzt(Integer xgzt) {
        this.xgzt = xgzt;
    }

    public Date getXgwcsj() {
        return xgwcsj;
    }

    public void setXgwcsj(Date xgwcsj) {
        this.xgwcsj = xgwcsj;
    }

    public Integer getQrzt() {
        return qrzt;
    }

    public void setQrzt(Integer qrzt) {
        this.qrzt = qrzt;
    }

    public Date getQrwcsj() {
        return qrwcsj;
    }

    public void setQrwcsj(Date qrwcsj) {
        this.qrwcsj = qrwcsj;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcSlXqxxDO{");
        sb.append("xqxxid='").append(xqxxid).append('\'');
        sb.append(", xmid='").append(xmid).append('\'');
        sb.append(", xqmc='").append(xqmc).append('\'');
        sb.append(", xqnr='").append(xqnr).append('\'');
        sb.append(", xgzt=").append(xgzt);
        sb.append(", xgwcsj=").append(xgwcsj);
        sb.append(", qrzt=").append(qrzt);
        sb.append(", qrwcsj=").append(qrwcsj);
        sb.append(", bz='").append(bz).append('\'');
        sb.append(", gzlslid='").append(gzlslid).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
