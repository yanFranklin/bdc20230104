package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "BdcSlCshFwkgDataDTO", description = "受理初始化房屋开关信息DTO")
public class BdcSlCshFwkgDataDTO {

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "是否生成证书  0：否  1：是")
    private Integer sfsczs;
    @ApiModelProperty(value = "证书种类   1：证书  2：证明")
    private Integer zszl;
    @ApiModelProperty(value = "登记小类")
    private String djxl;

    public Integer getSfsczs() {
        return sfsczs;
    }

    public void setSfsczs(Integer sfsczs) {
        this.sfsczs = sfsczs;
    }

    public Integer getZszl() {
        return zszl;
    }

    public void setZszl(Integer zszl) {
        this.zszl = zszl;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }


    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public BdcSlCshFwkgDataDTO() {
    }

    public BdcSlCshFwkgDataDTO(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcSlCshFwkgDataDTO{");
        sb.append("bdcdyh='").append(bdcdyh).append('\'');
        sb.append(", sfsczs=").append(sfsczs);
        sb.append(", zszl=").append(zszl);
        sb.append(", djxl='").append(djxl).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
