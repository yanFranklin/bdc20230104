package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2019-05-22
 * @description 查询子系统：不动产办件时长DTO
 */
@Api(value = "BdcBjscDTO", description = "不动产办件时长DTO")
public class BdcBjscDTO {

    @ApiModelProperty("办件大厅")
    private String bmmc;

    @ApiModelProperty("办结业务量")
    private Integer bjywl;

    @ApiModelProperty("办件总时长")
    private float bjsc;

    @ApiModelProperty("平均办件时长")
    private float avgBjsc;

    @ApiModelProperty("时间段统计")
    private Map periodCount;

    public String getBmmc() {
        return bmmc;
    }

    public void setBmmc(String bmmc) {
        this.bmmc = bmmc;
    }

    public Integer getBjywl() {
        return bjywl;
    }

    public void setBjywl(Integer bjywl) {
        this.bjywl = bjywl;
    }

    public float getBjsc() {
        return bjsc;
    }

    public void setBjsc(float bjsc) {
        this.bjsc = bjsc;
    }

    public float getAvgBjsc() {
        return avgBjsc;
    }

    public void setAvgBjsc(float avgBjsc) {
        this.avgBjsc = avgBjsc;
    }

    public Map getPeriodCount() {
        return periodCount;
    }

    public void setPeriodCount(Map periodCount) {
        this.periodCount = periodCount;
    }
}
