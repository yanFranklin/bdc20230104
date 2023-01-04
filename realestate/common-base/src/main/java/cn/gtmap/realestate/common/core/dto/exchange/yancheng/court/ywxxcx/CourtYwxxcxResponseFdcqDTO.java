package cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.ywxxcx;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/12/07 15:13
 */
public class CourtYwxxcxResponseFdcqDTO {

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "房地坐落")
    private String fdzl;

    @ApiModelProperty(value = "建筑面积")
    private String jzmj;

    @ApiModelProperty(value = "专有建筑面积")
    private String zyjzmj;

    @ApiModelProperty(value = "分摊建筑面积")
    private String ftjzmj;

    @ApiModelProperty(value = "规划用途")
    private String ghyt;

    @ApiModelProperty(value = "房屋性质")
    private String fwxz;

    @ApiModelProperty(value = "竣工时间")
    private String jgsj;

    @ApiModelProperty(value = "土地使用起始时间 格式 yyyy-MM-dd")
    private String tdsyqssj;

    @ApiModelProperty(value = "土地使用结束时间 格式 yyyy-MM-dd")
    private String tdsyjssj;

    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;

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

    public String getFdzl() {
        return fdzl;
    }

    public void setFdzl(String fdzl) {
        this.fdzl = fdzl;
    }

    public String getJzmj() {
        return jzmj;
    }

    public void setJzmj(String jzmj) {
        this.jzmj = jzmj;
    }

    public String getZyjzmj() {
        return zyjzmj;
    }

    public void setZyjzmj(String zyjzmj) {
        this.zyjzmj = zyjzmj;
    }

    public String getFtjzmj() {
        return ftjzmj;
    }

    public void setFtjzmj(String ftjzmj) {
        this.ftjzmj = ftjzmj;
    }

    public String getGhyt() {
        return ghyt;
    }

    public void setGhyt(String ghyt) {
        this.ghyt = ghyt;
    }

    public String getFwxz() {
        return fwxz;
    }

    public void setFwxz(String fwxz) {
        this.fwxz = fwxz;
    }

    public String getJgsj() {
        return jgsj;
    }

    public void setJgsj(String jgsj) {
        this.jgsj = jgsj;
    }

    public String getTdsyqssj() {
        return tdsyqssj;
    }

    public void setTdsyqssj(String tdsyqssj) {
        this.tdsyqssj = tdsyqssj;
    }

    public String getTdsyjssj() {
        return tdsyjssj;
    }

    public void setTdsyjssj(String tdsyjssj) {
        this.tdsyjssj = tdsyjssj;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
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
}
