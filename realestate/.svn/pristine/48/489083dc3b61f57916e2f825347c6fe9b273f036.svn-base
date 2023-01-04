package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/11/18
 * @description 不动产受理退费信息DO
 */
@Table(name = "BDC_SL_TFXX")
@ApiModel(value = "BdcSlTfxxDO", description = "不动产受理退费信息DO")
public class BdcSlTfxxDO implements Serializable {

    @Id
    @ApiModelProperty(value = "退费信息ID")
    private String tfxxid;
    @ApiModelProperty(value = "收费信息ID")
    private String sfxxid;
    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @ApiModelProperty(value = "缴费书编码")
    private String jfsbm;
    @ApiModelProperty(value = "缴款人姓名")
    private String jkrxm;
    @ApiModelProperty(value = "收费时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sfsj;
    @ApiModelProperty(value = "不动产登记费")
    private Double djf;
    @ApiModelProperty(value = "土地使用权交易服务费")
    private Double tdsyqjyfwf;
    @ApiModelProperty(value = "实际收费总金额")
    private Double sjsfzje;
    @ApiModelProperty(value = "实际退费总金额")
    private Double sjtfzje;
    @ApiModelProperty(value = "票据类型")
    private String pjlx;
    @ApiModelProperty(value = "申请退费时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sqtfsj;
    @ApiModelProperty(value = "申请退费原因")
    private String sqtfyy;
    @ApiModelProperty(value = "部门名称")
    private String bmmc;

    public String getTfxxid() {
        return tfxxid;
    }

    public void setTfxxid(String tfxxid) {
        this.tfxxid = tfxxid;
    }

    public String getSfxxid() {
        return sfxxid;
    }

    public void setSfxxid(String sfxxid) {
        this.sfxxid = sfxxid;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getJfsbm() {
        return jfsbm;
    }

    public void setJfsbm(String jfsbm) {
        this.jfsbm = jfsbm;
    }

    public String getJkrxm() {
        return jkrxm;
    }

    public void setJkrxm(String jkrxm) {
        this.jkrxm = jkrxm;
    }

    public Date getSfsj() {
        return sfsj;
    }

    public void setSfsj(Date sfsj) {
        this.sfsj = sfsj;
    }

    public Double getDjf() {
        return djf;
    }

    public void setDjf(Double djf) {
        this.djf = djf;
    }

    public Double getTdsyqjyfwf() {
        return tdsyqjyfwf;
    }

    public void setTdsyqjyfwf(Double tdsyqjyfwf) {
        this.tdsyqjyfwf = tdsyqjyfwf;
    }

    public Double getSjsfzje() {
        return sjsfzje;
    }

    public void setSjsfzje(Double sjsfzje) {
        this.sjsfzje = sjsfzje;
    }

    public Double getSjtfzje() {
        return sjtfzje;
    }

    public void setSjtfzje(Double sjtfzje) {
        this.sjtfzje = sjtfzje;
    }

    public String getPjlx() {
        return pjlx;
    }

    public void setPjlx(String pjlx) {
        this.pjlx = pjlx;
    }

    public Date getSqtfsj() {
        return sqtfsj;
    }

    public void setSqtfsj(Date sqtfsj) {
        this.sqtfsj = sqtfsj;
    }

    public String getSqtfyy() {
        return sqtfyy;
    }

    public void setSqtfyy(String sqtfyy) {
        this.sqtfyy = sqtfyy;
    }

    public String getBmmc() {
        return bmmc;
    }

    public void setBmmc(String bmmc) {
        this.bmmc = bmmc;
    }

}
