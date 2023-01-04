package cn.gtmap.realestate.common.core.dto.building;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/2
 * @description
 */
@ApiModel(value = "LjzResponseDTO", description = "逻辑幢DTO")
public class LjzResponseDTO {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String fwDcbIndex;
    /**
     * 不动产单元号
     */
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    /**
     * 隶属宗地
     */
    @ApiModelProperty(value = "隶属宗地")
    private String lszd;
    /**
     * 自然幢号
     */
    @ApiModelProperty(value = "自然幢号")
    private String zrzh;
    /**
     * 房屋名称
     */
    @ApiModelProperty(value = "房屋名称")
    private String fwmc;
    /**
     * 逻辑幢号
     */
    @ApiModelProperty(value = "逻辑幢号")
    private String ljzh;
    /**
     * 坐落地址
     */
    @ApiModelProperty(value = "坐落地址")
    private String zldz;
    /**
     * 总套数
     */
    @ApiModelProperty(value = "总套数")
    private Integer zts;

    public String getFwDcbIndex() {
        return fwDcbIndex;
    }

    public void setFwDcbIndex(String fwDcbIndex) {
        this.fwDcbIndex = fwDcbIndex;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getLszd() {
        return lszd;
    }

    public void setLszd(String lszd) {
        this.lszd = lszd;
    }

    public String getZrzh() {
        return zrzh;
    }

    public void setZrzh(String zrzh) {
        this.zrzh = zrzh;
    }

    public String getFwmc() {
        return fwmc;
    }

    public void setFwmc(String fwmc) {
        this.fwmc = fwmc;
    }

    public String getLjzh() {
        return ljzh;
    }

    public void setLjzh(String ljzh) {
        this.ljzh = ljzh;
    }

    public String getZldz() {
        return zldz;
    }

    public void setZldz(String zldz) {
        this.zldz = zldz;
    }

    public Integer getZts() {
        return zts;
    }

    public void setZts(Integer zts) {
        this.zts = zts;
    }

    @Override
    public String toString() {
        return "LjzResponseDTO{" +
                "fwDcbIndex='" + fwDcbIndex + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", lszd='" + lszd + '\'' +
                ", zrzh='" + zrzh + '\'' +
                ", fwmc='" + fwmc + '\'' +
                ", ljzh='" + ljzh + '\'' +
                ", zldz='" + zldz + '\'' +
                ", zts=" + zts +
                '}';
    }
}