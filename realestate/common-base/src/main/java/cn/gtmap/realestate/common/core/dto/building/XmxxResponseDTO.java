package cn.gtmap.realestate.common.core.dto.building;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/2
 * @description 项目信息DTO
 */
@ApiModel(value = "XmxxResponseDTO", description = "项目信息DTO")
public class XmxxResponseDTO {
    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称")
    private String xmmc;
    /**
     * 不动产单元号
     */
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    /**
     * 坐落
     */
    @ApiModelProperty(value = "坐落")
    private String zl;
    /**
     * 隶属宗地
     */
    @ApiModelProperty(value = "隶属宗地")
    private String lszd;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String fwXmxxIndex;
    /**
     * 不动产状态
     */
    @ApiModelProperty(value = "不动产状态")
    private String bdczt;

    public String getXmmc() {
        return xmmc;
    }

    public void setXmmc(String xmmc) {
        this.xmmc = xmmc;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getLszd() {
        return lszd;
    }

    public void setLszd(String lszd) {
        this.lszd = lszd;
    }

    public String getFwXmxxIndex() {
        return fwXmxxIndex;
    }

    public void setFwXmxxIndex(String fwXmxxIndex) {
        this.fwXmxxIndex = fwXmxxIndex;
    }

    public String getBdczt() {
        return bdczt;
    }

    public void setBdczt(String bdczt) {
        this.bdczt = bdczt;
    }

    @Override
    public String toString() {
        return "XmxxResponseDTO{" +
                "xmmc='" + xmmc + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", zl='" + zl + '\'' +
                ", lszd='" + lszd + '\'' +
                ", fwXmxxIndex='" + fwXmxxIndex + '\'' +
                ", bdczt='" + bdczt + '\'' +
                '}';
    }
}