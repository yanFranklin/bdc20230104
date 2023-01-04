package cn.gtmap.realestate.supervise.core.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/08/31
 * @description 查询异常统计信息DTO
 */
public class LfCxycDTO {
    @ApiModelProperty(value = "用户名")
    private String yhm;

    @ApiModelProperty(value = "用户ID")
    private String yhid;

    @ApiModelProperty(value = "用户账号")
    private String yhzh;

    @ApiModelProperty(value = "所在部门")
    private String szbm;

    @ApiModelProperty(value = "职位")
    private String zw;

    @ApiModelProperty(value = "人员角色")
    private String ryjs;

    @ApiModelProperty(value = "查询次数")
    private Double cxcs;

    @ApiModelProperty(value = "重复查询字段")
    private String cfcxzd;

    @ApiModelProperty(value = "重复查询字段值")
    private String cfcxzdz;

    @ApiModelProperty(value = "权利人名称")
    private String qlrmc;

    @ApiModelProperty(value = "权利人证件号")
    private String qlrzjh;

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getYhid() {
        return yhid;
    }

    public void setYhid(String yhid) {
        this.yhid = yhid;
    }

    public String getYhm() {
        return yhm;
    }

    public void setYhm(String yhm) {
        this.yhm = yhm;
    }

    public String getYhzh() {
        return yhzh;
    }

    public void setYhzh(String yhzh) {
        this.yhzh = yhzh;
    }

    public String getSzbm() {
        return szbm;
    }

    public void setSzbm(String szbm) {
        this.szbm = szbm;
    }

    public String getRyjs() {
        return ryjs;
    }

    public void setRyjs(String ryjs) {
        this.ryjs = ryjs;
    }

    public Double getCxcs() {
        return cxcs;
    }

    public void setCxcs(Double cxcs) {
        this.cxcs = cxcs;
    }

    public String getCfcxzd() {
        return cfcxzd;
    }

    public void setCfcxzd(String cfcxzd) {
        this.cfcxzd = cfcxzd;
    }

    public String getCfcxzdz() {
        return cfcxzdz;
    }

    public void setCfcxzdz(String cfcxzdz) {
        this.cfcxzdz = cfcxzdz;
    }

    public String getZw() {
        return zw;
    }

    public void setZw(String zw) {
        this.zw = zw;
    }

    @Override
    public String toString() {
        return "LfCxycDTO{" +
                "yhm='" + yhm + '\'' +
                ", yhid='" + yhid + '\'' +
                ", yhzh='" + yhzh + '\'' +
                ", szbm='" + szbm + '\'' +
                ", zw='" + zw + '\'' +
                ", ryjs='" + ryjs + '\'' +
                ", cxcs=" + cxcs +
                ", cfcxzd='" + cfcxzd + '\'' +
                ", cfcxzdz='" + cfcxzdz + '\'' +
                ", qlrmc='" + qlrmc + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                '}';
    }
}
