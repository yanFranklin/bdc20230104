package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlQl;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/7/5
 * @description 不动产受理林权信息
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz", defaultImpl = BdcSlLqDTO.class)
@ApiModel(value = "BdcSlLqDTO", description = "不动产受理林权信息")
public class BdcSlLqDTO implements BdcSlQl {

    @Id
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "林地使用（承包）起始时间", example = "2018-10-01 12:18:48")
    private Date ldsyqssj;

    @ApiModelProperty(value = "林地使用（承包）结束时间", example = "2018-10-01 12:18:48")
    private Date ldsyjssj;

    @ApiModelProperty(value = "主要树种")
    private String zysz;

    @ApiModelProperty(value = "株数")
    private Integer zs;

    @ApiModelProperty(value = "林班")
    private String lb;

    @ApiModelProperty(value = "小班")
    private String xb;

    @ApiModelProperty(value = "造林年度")
    private Integer zlnd;

    @ApiModelProperty(value = "小地名")
    private String xdm;

    @ApiModelProperty(value = "起源")
    private Integer qy;

    @ApiModelProperty(value = "森林类别")
    private String sllb;

    @ApiModelProperty(value = "附记")
    private String fj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getXmid() {
        return xmid;
    }

    @Override
    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public Date getLdsyqssj() {
        return ldsyqssj;
    }

    public void setLdsyqssj(Date ldsyqssj) {
        this.ldsyqssj = ldsyqssj;
    }

    public Date getLdsyjssj() {
        return ldsyjssj;
    }

    public void setLdsyjssj(Date ldsyjssj) {
        this.ldsyjssj = ldsyjssj;
    }

    public String getZysz() {
        return zysz;
    }

    public void setZysz(String zysz) {
        this.zysz = zysz;
    }

    public Integer getZs() {
        return zs;
    }

    public void setZs(Integer zs) {
        this.zs = zs;
    }

    public String getLb() {
        return lb;
    }

    public void setLb(String lb) {
        this.lb = lb;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public Integer getZlnd() {
        return zlnd;
    }

    public void setZlnd(Integer zlnd) {
        this.zlnd = zlnd;
    }

    public String getXdm() {
        return xdm;
    }

    public void setXdm(String xdm) {
        this.xdm = xdm;
    }

    public Integer getQy() {
        return qy;
    }

    public void setQy(Integer qy) {
        this.qy = qy;
    }

    public String getSllb() {
        return sllb;
    }

    public void setSllb(String sllb) {
        this.sllb = sllb;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    @Override
    public String toString() {
        return "BdcSlLqDTO{" +
                "id='" + id + '\'' +
                ", xmid='" + xmid + '\'' +
                ", ldsyqssj=" + ldsyqssj +
                ", ldsyjssj=" + ldsyjssj +
                ", zysz='" + zysz + '\'' +
                ", zs=" + zs +
                ", lb='" + lb + '\'' +
                ", xb='" + xb + '\'' +
                ", zlnd=" + zlnd +
                ", xdm='" + xdm + '\'' +
                ", qy=" + qy +
                ", sllb='" + sllb + '\'' +
                ", fj='" + fj + '\'' +
                '}';
    }
}
