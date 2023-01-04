package cn.gtmap.realestate.common.core.dto.building;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/12/15
 * @description 楼盘表构建
 */
@ApiModel(value = "LpbGJRequestDTO", description = "楼盘表构建")
public class LpbGJRequestDTO {

    /**
     * 逻辑幢主键
     */
    @ApiModelProperty(value = "逻辑幢主键")
    @NotBlank(message = "逻辑幢主键不能为空")
    private String fwDcbIndex;
    /**
     * 层数
     */
    @ApiModelProperty(value = "层数")
    @NotNull(message = "层数不能为空")
    private Integer cs;

    /**
     * 构建方式
     */
    @ApiModelProperty(value = "构建方式")
    @NotBlank(message = "构建方式不能为空")
    private String gjfs;

    /**
     * 每层户数
     */
    @ApiModelProperty(value = "每层户数")
    private Integer mchs;

    /**
     * 单元数
     */
    @ApiModelProperty(value = "单元数")
    private Integer dys;

    /**
     * 单元每层户数
     */
    @ApiModelProperty(value = "单元每层户数")
    private Integer dymshs;

    /**
     * 按单元户数动态构建
     */
    @ApiModelProperty(value = "按单元户数动态构建(单元号+“，”+户数)")
    private List<String> hsdtgj;

    /**
     * 权籍管理代码
     */
    @ApiModelProperty(value = "权籍管理代码")
    private String qjgldm;

    public String getFwDcbIndex() {
        return fwDcbIndex;
    }

    public void setFwDcbIndex(String fwDcbIndex) {
        this.fwDcbIndex = fwDcbIndex;
    }

    public Integer getCs() {
        return cs;
    }

    public void setCs(Integer cs) {
        this.cs = cs;
    }

    public String getGjfs() {
        return gjfs;
    }

    public void setGjfs(String gjfs) {
        this.gjfs = gjfs;
    }

    public Integer getMchs() {
        return mchs;
    }

    public void setMchs(Integer mchs) {
        this.mchs = mchs;
    }

    public Integer getDys() {
        return dys;
    }

    public void setDys(Integer dys) {
        this.dys = dys;
    }

    public Integer getDymshs() {
        return dymshs;
    }

    public void setDymshs(Integer dymshs) {
        this.dymshs = dymshs;
    }

    public List<String> getHsdtgj() {
        return hsdtgj;
    }

    public void setHsdtgj(List<String> hsdtgj) {
        this.hsdtgj = hsdtgj;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    @Override
    public String toString() {
        return "LpbGJRequestDTO{" +
                "fwDcbIndex='" + fwDcbIndex + '\'' +
                ", cs=" + cs +
                ", gjfs='" + gjfs + '\'' +
                ", mchs=" + mchs +
                ", dys=" + dys +
                ", dymshs=" + dymshs +
                ", hsdtgj=" + hsdtgj +
                ", qjgldm='" + qjgldm + '\'' +
                '}';
    }
}