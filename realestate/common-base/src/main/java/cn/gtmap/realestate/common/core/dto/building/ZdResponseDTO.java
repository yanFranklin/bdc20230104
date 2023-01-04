package cn.gtmap.realestate.common.core.dto.building;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/2
 * @description 宗地分页
 */
@ApiModel(value = "ZdResponseDTO", description = "地籍信息分页查询")
public class ZdResponseDTO extends AcceptPageResponseDTO{
    /**
     * 不动产单元号
     */
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    /**
     * 地籍号
     */
    @ApiModelProperty(value = "地籍号")
    private String djh;
    /**
     * 土地座落
     */
    @ApiModelProperty(value = "土地座落")
    private String tdzl;
    /**
     * 实测面积
     */
    @ApiModelProperty(value = "实测面积")
    private Double scmj;
    /**
     * 发证面积
     */
    @ApiModelProperty(value = "发证面积")
    private Double fzmj;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String dcbIndex;
    /**
     * 权利人
     */
    @ApiModelProperty(value = "权利人")
    private String qlr;
    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")

    private String lx;

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getDjh() {
        return djh;
    }

    public void setDjh(String djh) {
        this.djh = djh;
    }

    public String getTdzl() {
        return tdzl;
    }

    public void setTdzl(String tdzl) {
        this.tdzl = tdzl;
    }

    public Double getScmj() {
        return scmj;
    }

    public void setScmj(Double scmj) {
        this.scmj = scmj;
    }

    public Double getFzmj() {
        return fzmj;
    }

    public void setFzmj(Double fzmj) {
        this.fzmj = fzmj;
    }

    public String getDcbIndex() {
        return dcbIndex;
    }

    public void setDcbIndex(String dcbIndex) {
        this.dcbIndex = dcbIndex;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getLx() {
        return lx;
    }

    public void setLx(String lx) {
        this.lx = lx;
    }

    @Override
    public String toString() {
        return "ZdResponseDTO{" +
                "bdcdyh='" + bdcdyh + '\'' +
                ", djh='" + djh + '\'' +
                ", tdzl='" + tdzl + '\'' +
                ", scmj=" + scmj +
                ", fzmj=" + fzmj +
                ", dcbIndex='" + dcbIndex + '\'' +
                ", qlr='" + qlr + '\'' +
                ", lx='" + lx + '\'' +
                '}';
    }
}