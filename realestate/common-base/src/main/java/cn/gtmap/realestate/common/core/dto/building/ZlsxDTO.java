package cn.gtmap.realestate.common.core.dto.building;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
 * @version 1.0  2018-10-28
 * @description
 */
public class ZlsxDTO {
    /**
     * 刷新规则
     */
    @ApiModelProperty(value = "刷新规则")
    @NotBlank(message = "刷新规则不能为空")
    private String sxgz;
    /**
     * 宗地号
     */
    @ApiModelProperty(value = "宗地号")
    private String djh;
    /**
     * 逻辑幢号
     */
    @ApiModelProperty(value = "逻辑幢号")
    private String fwDcbIndex;
    /**
     * 是否只刷新空值
     */
    @ApiModelProperty(value = "是否只刷新空值")
    private boolean nullOnly;

    /**
     * 权籍管理代码
     */
    @ApiModelProperty(value = "权籍管理代码")
    private String qjgldm;

    public boolean isNullOnly() {
        return nullOnly;
    }

    public void setNullOnly(boolean nullOnly) {
        this.nullOnly = nullOnly;
    }

    public String getDjh() {
        return djh;
    }

    public void setDjh(String djh) {
        this.djh = djh;
    }

    public String getFwDcbIndex() {
        return fwDcbIndex;
    }

    public void setFwDcbIndex(String fwDcbIndex) {
        this.fwDcbIndex = fwDcbIndex;
    }

    public String getSxgz() {
        return sxgz;
    }

    public void setSxgz(String sxgz) {
        this.sxgz = sxgz;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    @Override
    public String toString() {
        return "ZlsxDTO{" +
                "sxgz='" + sxgz + '\'' +
                ", djh='" + djh + '\'' +
                ", fwDcbIndex='" + fwDcbIndex + '\'' +
                ", nullOnly=" + nullOnly +
                ", qjgldm='" + qjgldm + '\'' +
                '}';
    }
}
