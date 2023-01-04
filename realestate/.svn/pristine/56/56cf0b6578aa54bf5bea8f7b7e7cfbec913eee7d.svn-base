package cn.gtmap.realestate.common.core.dto.building;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/12/15
 * @description 分摊土地面积计算
 */
@ApiModel(value = "FttdmjRequestDTO", description = "分摊土地面积计算")
public class FttdmjRequestDTO extends LjzJzmjRequestDTO{
    /**
     * 计算公式序号
     */
    @ApiModelProperty(value = "计算公式序号")
    private String jsgsxh;

    /**
     * 分摊系数
     */
    @ApiModelProperty(value = "分摊系数")
    private double ftxs;

    /**
     * 楼层数
     */
    @ApiModelProperty(value = "楼层数")
    private Integer lcs;

    /**
     * 宗地内楼幢总建筑面积（ljz或者是zrz）
     */
    @ApiModelProperty(value = "宗地内楼幢总建筑面积")
    private String zdnlzzjzmj;

    /**
     * 权籍管理代码
     */
    @ApiModelProperty(value = "权籍管理代码")
    private String qjgldm;

    public String getZdnlzzjzmj() {
        return zdnlzzjzmj;
    }

    public void setZdnlzzjzmj(String zdnlzzjzmj) {
        this.zdnlzzjzmj = zdnlzzjzmj;
    }

    public String getJsgsxh() {
        return jsgsxh;
    }

    public void setJsgsxh(String jsgsxh) {
        this.jsgsxh = jsgsxh;
    }

    public double getFtxs() {
        return ftxs;
    }

    public void setFtxs(double ftxs) {
        this.ftxs = ftxs;
    }

    public Integer getLcs() {
        return lcs;
    }

    public void setLcs(Integer lcs) {
        this.lcs = lcs;
    }

    @Override
    public String getQjgldm() {
        return qjgldm;
    }

    @Override
    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    @Override
    public String toString() {
        return "FttdmjRequestDTO{" +
                "jsgsxh='" + jsgsxh + '\'' +
                ", ftxs=" + ftxs +
                ", lcs=" + lcs +
                ", zdnlzzjzmj='" + zdnlzzjzmj + '\'' +
                ", qjgldm='" + qjgldm + '\'' +
                '}';
    }
}