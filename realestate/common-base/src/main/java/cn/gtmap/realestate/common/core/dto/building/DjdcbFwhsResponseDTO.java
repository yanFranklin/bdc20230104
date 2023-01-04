package cn.gtmap.realestate.common.core.dto.building;

import cn.gtmap.realestate.common.core.annotations.Zd;
import cn.gtmap.realestate.common.core.domain.building.SZdFwytDO;
import cn.gtmap.realestate.common.core.domain.building.SZdHxjgDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2019/1/28
 * @description
 */
@ApiModel(value = "DjdcbFwhsResponseDTO", description = "房屋信息DTO")
public class DjdcbFwhsResponseDTO {
    /**
     * 不动产单元号
     */
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    /**
     * 幢号
     */
    @ApiModelProperty(value = "幢号")
    private String ljzh;

    /**
     * 单元号
     */
    @ApiModelProperty(value = "单元号")
    private String dyh;

    /**
     * 房间号
     */
    @ApiModelProperty(value = "房间号")
    private String fjh;


    /**
     * 物理层数
     */
    @ApiModelProperty(value = "物理层数")
    private Integer wlcs;

    /**
     * 定义层数
     */
    @ApiModelProperty(value = "定义层数")
    private String dycs;

    /**
     * 室序号
     */
    @ApiModelProperty(value = "室序号")
    private Integer sxh;

    /**
     * 隶属宗地
     */
    @ApiModelProperty(value = "隶属宗地")
    private String lszd;

    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称")
    private String xmmc;

    /**
     * 层高
     */
    @ApiModelProperty(value = "层高")
    private Double cg;

    /**
     * 土地使用权人
     */
    @ApiModelProperty(value = "土地使用权人")
    private String qlrmc;

    /**
     * 实测建筑面积
     */
    @ApiModelProperty(value = "实测建筑面积")
    private Double scjzmj;

    /**
     * 户型结构
     */
    @Zd(tableClass = SZdHxjgDO.class)
    @ApiModelProperty(value = "户型结构")
    private String hxjg;

    /**
     * 实测套内建筑面积
     */
    @ApiModelProperty(value = "实测套内建筑面积")
    private Double sctnjzmj;

    /**
     * 规划用途
     */
    @Zd(tableClass = SZdFwytDO.class)
    @ApiModelProperty(value = "规划用途")
    private String ghyt;

    /**
     * 实测分摊建筑面积
     */
    @ApiModelProperty(value = "实测分摊建筑面积")
    private Double scftjzmj;

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getLjzh() {
        return ljzh;
    }

    public void setLjzh(String ljzh) {
        this.ljzh = ljzh;
    }

    public String getDyh() {
        return dyh;
    }

    public void setDyh(String dyh) {
        this.dyh = dyh;
    }

    public String getFjh() {
        return fjh;
    }

    public void setFjh(String fjh) {
        this.fjh = fjh;
    }

    public Integer getWlcs() {
        return wlcs;
    }

    public void setWlcs(Integer wlcs) {
        this.wlcs = wlcs;
    }

    public String getDycs() {
        return dycs;
    }

    public void setDycs(String dycs) {
        this.dycs = dycs;
    }

    public Integer getSxh() {
        return sxh;
    }

    public void setSxh(Integer sxh) {
        this.sxh = sxh;
    }

    public String getLszd() {
        return lszd;
    }

    public void setLszd(String lszd) {
        this.lszd = lszd;
    }

    public String getXmmc() {
        return xmmc;
    }

    public void setXmmc(String xmmc) {
        this.xmmc = xmmc;
    }

    public Double getCg() {
        return cg;
    }

    public void setCg(Double cg) {
        this.cg = cg;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public Double getScjzmj() {
        return scjzmj;
    }

    public void setScjzmj(Double scjzmj) {
        this.scjzmj = scjzmj;
    }

    public String getHxjg() {
        return hxjg;
    }

    public void setHxjg(String hxjg) {
        this.hxjg = hxjg;
    }

    public Double getSctnjzmj() {
        return sctnjzmj;
    }

    public void setSctnjzmj(Double sctnjzmj) {
        this.sctnjzmj = sctnjzmj;
    }

    public String getGhyt() {
        return ghyt;
    }

    public void setGhyt(String ghyt) {
        this.ghyt = ghyt;
    }

    public Double getScftjzmj() {
        return scftjzmj;
    }

    public void setScftjzmj(Double scftjzmj) {
        this.scftjzmj = scftjzmj;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DjdcbFwhsResponseDTO{");
        sb.append("bdcdyh='").append(bdcdyh).append('\'');
        sb.append(", ljzh='").append(ljzh).append('\'');
        sb.append(", dyh='").append(dyh).append('\'');
        sb.append(", fjh='").append(fjh).append('\'');
        sb.append(", wlcs=").append(wlcs);
        sb.append(", dycs='").append(dycs).append('\'');
        sb.append(", sxh=").append(sxh);
        sb.append(", lszd='").append(lszd).append('\'');
        sb.append(", xmmc='").append(xmmc).append('\'');
        sb.append(", cg=").append(cg);
        sb.append(", qlrmc='").append(qlrmc).append('\'');
        sb.append(", scjzmj=").append(scjzmj);
        sb.append(", hxjg='").append(hxjg).append('\'');
        sb.append(", sctnjzmj=").append(sctnjzmj);
        sb.append(", ghyt='").append(ghyt).append('\'');
        sb.append(", scftjzmj=").append(scftjzmj);
        sb.append('}');
        return sb.toString();
    }
}