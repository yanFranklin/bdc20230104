package cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.ywxxcx;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/12/07 15:13
 */
public class CourtYwxxcxResponseCfDTO {

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "查封机关")
    private String cfjg;

    @ApiModelProperty(value = "查封类型")
    private String cflx;

    @ApiModelProperty(value = "查封文号")
    private String cfwh;

    @ApiModelProperty(value = "查封期限起")
    private String cfqssj;

    @ApiModelProperty(value = "查封期限止")
    private String cfjssj;

    @ApiModelProperty(value = "查封业务号")
    private String cfywh;

    @ApiModelProperty(value = "登记机构")
    private String djjg;

    @ApiModelProperty(value = "地区代码")
    private String dqdm;

    @ApiModelProperty(value = "业务号 项目ID")
    private String ywh;

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getCfjg() {
        return cfjg;
    }

    public void setCfjg(String cfjg) {
        this.cfjg = cfjg;
    }

    public String getCflx() {
        return cflx;
    }

    public void setCflx(String cflx) {
        this.cflx = cflx;
    }

    public String getCfwh() {
        return cfwh;
    }

    public void setCfwh(String cfwh) {
        this.cfwh = cfwh;
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

    public String getCfywh() {
        return cfywh;
    }

    public void setCfywh(String cfywh) {
        this.cfywh = cfywh;
    }

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    public String getDqdm() {
        return dqdm;
    }

    public void setDqdm(String dqdm) {
        this.dqdm = dqdm;
    }

    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    @Override
    public String toString() {
        return "CourtYwxxcxResponseCfDTO{" +
                "bdcdyh='" + bdcdyh + '\'' +
                ", zl='" + zl + '\'' +
                ", cfjg='" + cfjg + '\'' +
                ", cflx='" + cflx + '\'' +
                ", cfwh='" + cfwh + '\'' +
                ", cfqssj='" + cfqssj + '\'' +
                ", cfjssj='" + cfjssj + '\'' +
                ", cfywh='" + cfywh + '\'' +
                ", djjg='" + djjg + '\'' +
                ", dqdm='" + dqdm + '\'' +
                ", ywh='" + ywh + '\'' +
                '}';
    }
}
