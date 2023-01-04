package cn.gtmap.realestate.common.core.qo.accept;

import io.swagger.annotations.ApiModelProperty;

/**
 * @program: realestate
 * @description: 长三角证照信息查询条件
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-05-11 09:53
 **/
public class CsjZzxxQO {

    @ApiModelProperty("证件号")
    private String zjh;

    @ApiModelProperty("业务编码")
    private String ywbm;

    @ApiModelProperty("查询类型")
    private String cxlx;

    @ApiModelProperty()
    private String sign;

    @ApiModelProperty("选择的证照类型，逗号隔开")
    private String zzlx;

    @ApiModelProperty("流程实例id")
    private String gzlslid;

    @ApiModelProperty("流程定义id")
    private String gzldyid;

    @ApiModelProperty("苏服码内容")
    private String sfmnr;

    @ApiModelProperty("项目id")
    private String xmid;

    @ApiModelProperty("登记小类")
    private String djxl;

    @ApiModelProperty("流程类型")
    private Integer lclx;

    @ApiModelProperty("电子证照唯一标识码")
    private String license_code;

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    public String getYwbm() {
        return ywbm;
    }

    public void setYwbm(String ywbm) {
        this.ywbm = ywbm;
    }

    public String getCxlx() {
        return cxlx;
    }

    public void setCxlx(String cxlx) {
        this.cxlx = cxlx;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getZzlx() {
        return zzlx;
    }

    public void setZzlx(String zzlx) {
        this.zzlx = zzlx;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }


    public String getSfmnr() {
        return sfmnr;
    }

    public void setSfmnr(String sfmnr) {
        this.sfmnr = sfmnr;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public Integer getLclx() {
        return lclx;
    }

    public void setLclx(Integer lclx) {
        this.lclx = lclx;
    }

    public String getLicense_code() {
        return license_code;
    }

    public void setLicense_code(String license_code) {
        this.license_code = license_code;
    }

    @Override
    public String toString() {
        return "CsjZzxxQO{" +
                "zjh='" + zjh + '\'' +
                ", ywbm='" + ywbm + '\'' +
                ", cxlx='" + cxlx + '\'' +
                ", sign='" + sign + '\'' +
                ", zzlx='" + zzlx + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", gzldyid='" + gzldyid + '\'' +
                ", sfmnr='" + sfmnr + '\'' +
                ", xmid='" + xmid + '\'' +
                ", djxl='" + djxl + '\'' +
                ", lclx=" + lclx +
                '}';
    }
}
