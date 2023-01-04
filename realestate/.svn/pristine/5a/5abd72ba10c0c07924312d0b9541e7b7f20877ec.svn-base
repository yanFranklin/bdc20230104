package cn.gtmap.realestate.common.core.dto.building;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-07
 * @description 分页查询BDCDY 的 返回对象
 */
@ApiModel(value = "BdcdyPageResponseDTO", description = "分页查询BDCDY的返回对象")
public class BdcdyPageResponseDTO extends AcceptPageResponseDTO {

    /**
     * 户室主键
     */
    @ApiModelProperty(value = "户室主键")
    private String fwHsIndex;

    /**
     * 逻辑幢主键
     */
    @ApiModelProperty(value = "逻辑幢主键")
    private String fwDcbIndex;

    /**
     * 权利人
     */
    @ApiModelProperty(value = "权利人")
    private String qlr;

    /**
     * 坐落
     */
    @ApiModelProperty(value = "坐落")
    private String zl;

    /**
     * 不动产单元号
     */
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    /**
     * 建筑面积
     */
    @ApiModelProperty(value = "建筑面积")
    private Double jzmj;

    public String getFwHsIndex() {
        return fwHsIndex;
    }

    public void setFwHsIndex(String fwHsIndex) {
        this.fwHsIndex = fwHsIndex;
    }

    public String getFwDcbIndex() {
        return fwDcbIndex;
    }

    public void setFwDcbIndex(String fwDcbIndex) {
        this.fwDcbIndex = fwDcbIndex;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public Double getJzmj() {
        return jzmj;
    }

    public void setJzmj(Double jzmj) {
        this.jzmj = jzmj;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BdcdyPageResponseDTO{");
        sb.append("fwHsIndex='").append(fwHsIndex).append('\'');
        sb.append(", fwDcbIndex='").append(fwDcbIndex).append('\'');
        sb.append(", qlr='").append(qlr).append('\'');
        sb.append(", zl='").append(zl).append('\'');
        sb.append(", bdcdyh='").append(bdcdyh).append('\'');
        sb.append(", jzmj=").append(jzmj);
        sb.append('}');
        return sb.toString();
    }
}
