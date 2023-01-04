package cn.gtmap.realestate.common.core.dto.analysis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2018/12/20
 * @description
 */
@ApiModel(value = "BdcCfXxDTO", description = "查封信息")
public class BdcCfXxDTO {

    @ApiModelProperty("不动产单元号")
    private String bdcdyh;

    @ApiModelProperty("不动产单元唯一编号")
    private String bdcdywybh;

    @ApiModelProperty("坐落")
    private String zl;

    @ApiModelProperty("查封文号")
    private String cfwh;

    @ApiModelProperty("查封机关")
    private String cfjg;

    @ApiModelProperty(value = "查封起始时间", example = "2018-12-20 18:00:00")
    private String cfqssj;

    @ApiModelProperty(value = "查封结束时间", example = "2018-12-20 18:00:00")
    private String cfjssj;

    @ApiModelProperty(value = "登记时间", example = "2018-12-20 18:00:00")
    private String djsj;

    @ApiModelProperty("查封范围")
    private String cffw;

    @ApiModelProperty("查封类型名称")
    private String cflx;

    @ApiModelProperty("查封类型代码")
    private Integer cflxdm;

    @ApiModelProperty("查封备注")
    private String bz;

    @ApiModelProperty("附记")
    private String fj;

    @ApiModelProperty("是否注销")
    private String sfzx;

    @ApiModelProperty("权属状态代码")
    private Integer qsztdm;

    @ApiModelProperty("权属状态名称")
    private String qszt;

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getBdcdywybh() {
        return bdcdywybh;
    }

    public void setBdcdywybh(String bdcdywybh) {
        this.bdcdywybh = bdcdywybh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getCfwh() {
        return cfwh;
    }

    public void setCfwh(String cfwh) {
        this.cfwh = cfwh;
    }

    public String getCfjg() {
        return cfjg;
    }

    public void setCfjg(String cfjg) {
        this.cfjg = cfjg;
    }

    public String getCfqssj() {
        return cfqssj;
    }

    public void setCfqssj(String cfqssj) {
        this.cfqssj = cfqssj;
    }

    public String getCfjssj() {
        return cfjssj;
    }

    public void setCfjssj(String cfjssj) {
        this.cfjssj = cfjssj;
    }

    public String getDjsj() {
        return djsj;
    }

    public void setDjsj(String djsj) {
        this.djsj = djsj;
    }

    public String getCffw() {
        return cffw;
    }

    public void setCffw(String cffw) {
        this.cffw = cffw;
    }

    public String getCflx() {
        return cflx;
    }

    public void setCflx(String cflx) {
        this.cflx = cflx;
    }

    public Integer getCflxdm() {
        return cflxdm;
    }

    public void setCflxdm(Integer cflxdm) {
        this.cflxdm = cflxdm;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public String getSfzx() {
        return sfzx;
    }

    public void setSfzx(String sfzx) {
        this.sfzx = sfzx;
    }

    public Integer getQsztdm() {
        return qsztdm;
    }

    public void setQsztdm(Integer qsztdm) {
        this.qsztdm = qsztdm;
    }

    public String getQszt() {
        return qszt;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
    }
}
