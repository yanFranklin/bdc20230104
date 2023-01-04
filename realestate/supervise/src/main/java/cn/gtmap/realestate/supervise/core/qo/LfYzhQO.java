package cn.gtmap.realestate.supervise.core.qo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/08/27
 * @description 印制号查询QO
 */
public class LfYzhQO {
    @ApiModelProperty(value = "年份")
    private String nf;

    @ApiModelProperty(value = "区县代码")
    private String qxdm;

    @ApiModelProperty(value = "证书类型")
    private Integer zslx;

    @ApiModelProperty(value = "权证印刷序列号")
    private String qzysxlh;

    @ApiModelProperty(value = "使用情况")
    private Integer syqk;

    @ApiModelProperty(value = "领取人")
    private String lqr;

    @ApiModelProperty(value = "领取人ID")
    private String lqrid;

    @ApiModelProperty(value = "领取部门")
    private String lqbm;

    @ApiModelProperty(value = "领取部门ID")
    private String lqbmid;

    @ApiModelProperty(value = "创建时间开始时间")
    private String cjsjq;

    @ApiModelProperty(value = "创建时间结束时间")
    private String cjsjz;

    @ApiModelProperty(value = "印制号起")
    private String yzhq;

    @ApiModelProperty(value = "印制号止")
    private String yzhz;

    @ApiModelProperty(value = "使用人")
    private String syr;

    @ApiModelProperty(value = "使用人ID")
    private String syrid;

    @ApiModelProperty(value = "使用时间开始时间")
    private String sysjq;

    @ApiModelProperty(value = "使用时间结束时间")
    private String sysjz;

    @ApiModelProperty(value = "作废原因")
    private String zfyy;

    @ApiModelProperty(value = "排序字段")
    private String field;

    @ApiModelProperty(value = "排序类型")
    private String order;


    public String getNf() {
        return nf;
    }

    public void setNf(String nf) {
        this.nf = nf;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public Integer getZslx() {
        return zslx;
    }

    public void setZslx(Integer zslx) {
        this.zslx = zslx;
    }

    public String getQzysxlh() {
        return qzysxlh;
    }

    public void setQzysxlh(String qzysxlh) {
        this.qzysxlh = qzysxlh;
    }

    public Integer getSyqk() {
        return syqk;
    }

    public void setSyqk(Integer syqk) {
        this.syqk = syqk;
    }

    public String getLqr() {
        return lqr;
    }

    public void setLqr(String lqr) {
        this.lqr = lqr;
    }

    public String getLqrid() {
        return lqrid;
    }

    public void setLqrid(String lqrid) {
        this.lqrid = lqrid;
    }

    public String getLqbm() {
        return lqbm;
    }

    public void setLqbm(String lqbm) {
        this.lqbm = lqbm;
    }

    public String getLqbmid() {
        return lqbmid;
    }

    public void setLqbmid(String lqbmid) {
        this.lqbmid = lqbmid;
    }

    public String getCjsjq() {
        return cjsjq;
    }

    public void setCjsjq(String cjsjq) {
        this.cjsjq = cjsjq;
    }

    public String getCjsjz() {
        return cjsjz;
    }

    public void setCjsjz(String cjsjz) {
        this.cjsjz = cjsjz;
    }

    public String getYzhq() {
        return yzhq;
    }

    public void setYzhq(String yzhq) {
        this.yzhq = yzhq;
    }

    public String getYzhz() {
        return yzhz;
    }

    public void setYzhz(String yzhz) {
        this.yzhz = yzhz;
    }

    public String getSyr() {
        return syr;
    }

    public void setSyr(String syr) {
        this.syr = syr;
    }

    public String getSyrid() {
        return syrid;
    }

    public void setSyrid(String syrid) {
        this.syrid = syrid;
    }

    public String getSysjq() {
        return sysjq;
    }

    public void setSysjq(String sysjq) {
        this.sysjq = sysjq;
    }

    public String getSysjz() {
        return sysjz;
    }

    public void setSysjz(String sysjz) {
        this.sysjz = sysjz;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getZfyy() {
        return zfyy;
    }

    public void setZfyy(String zfyy) {
        this.zfyy = zfyy;
    }

    @Override
    public String toString() {
        return "LfYzhQO{" +
                "nf='" + nf + '\'' +
                ", qxdm='" + qxdm + '\'' +
                ", zslx=" + zslx +
                ", qzysxlh='" + qzysxlh + '\'' +
                ", syqk=" + syqk +
                ", lqr='" + lqr + '\'' +
                ", lqrid='" + lqrid + '\'' +
                ", lqbm='" + lqbm + '\'' +
                ", lqbmid='" + lqbmid + '\'' +
                ", cjsjq='" + cjsjq + '\'' +
                ", cjsjz='" + cjsjz + '\'' +
                ", yzhq='" + yzhq + '\'' +
                ", yzhz='" + yzhz + '\'' +
                ", syr='" + syr + '\'' +
                ", syrid='" + syrid + '\'' +
                ", sysjq='" + sysjq + '\'' +
                ", sysjz='" + sysjz + '\'' +
                ", zfyy='" + zfyy + '\'' +
                ", field='" + field + '\'' +
                ", order='" + order + '\'' +
                '}';
    }
}
