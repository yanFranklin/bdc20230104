package cn.gtmap.realestate.common.core.dto.rules;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2019/3/4
 * @description 规则限制验证例外查询结果DTO
 */
public class BdcGzXzYzLwResponseDTO implements Serializable {

    @ApiModelProperty(value = "操作人员ID")
    private String czryid;

    @ApiModelProperty(value = "操作时间")
    private String czsj;

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    @ApiModelProperty(value = "操作机器Ip")
    private String czjqip;

    @ApiModelProperty(value = "例外ID")
    private String lwid;

    @ApiModelProperty(value = "例外类型")
    private String lwlx;

    @ApiModelProperty(value = "例外原因")
    private String lwyy;

    @ApiModelProperty(value = "例外文号")
    private String lwwh;

    @ApiModelProperty(value = "操作人员")
    private String czry;

    public String getCzryid() {
        return czryid;
    }

    public void setCzryid(String czryid) {
        this.czryid = czryid;
    }

    public String getCzsj() {
        return czsj;
    }

    public void setCzsj(String czsj) {
        this.czsj = czsj;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getCzjqip() {
        return czjqip;
    }

    public void setCzjqip(String czjqip) {
        this.czjqip = czjqip;
    }

    public String getLwid() {
        return lwid;
    }

    public void setLwid(String lwid) {
        this.lwid = lwid;
    }

    public String getLwlx() {
        return lwlx;
    }

    public void setLwlx(String lwlx) {
        this.lwlx = lwlx;
    }

    public String getLwyy() {
        return lwyy;
    }

    public void setLwyy(String lwyy) {
        this.lwyy = lwyy;
    }

    public String getLwwh() {
        return lwwh;
    }

    public void setLwwh(String lwwh) {
        this.lwwh = lwwh;
    }

    public String getCzry() {
        return czry;
    }

    public void setCzry(String czry) {
        this.czry = czry;
    }

    @Override
    public String toString() {
        return "BdcGzXzYzLwResponseDTO{" +
                "czryid='" + czryid + '\'' +
                ", czsj='" + czsj + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", czjqip='" + czjqip + '\'' +
                ", lwid='" + lwid + '\'' +
                ", lwlx='" + lwlx + '\'' +
                ", lwyy='" + lwyy + '\'' +
                ", lwwh='" + lwwh + '\'' +
                ", czry='" + czry + '\'' +
                '}';
    }
}
