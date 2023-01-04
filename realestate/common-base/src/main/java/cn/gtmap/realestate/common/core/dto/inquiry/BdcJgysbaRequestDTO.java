package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @version 1.0  2022-12-16
 * @description 竣工验收备案查询接口requestdto
 */
@Api(value = "BdcJgysbaRequestDTO", description = "竣工验收备案查询接口requestdto")
public class BdcJgysbaRequestDTO {

    @ApiModelProperty(value = "建设单位名称")
    private String buildUnit;
    @ApiModelProperty(value = "项目名称")
    private String projName;
    @ApiModelProperty(value = "行政区划代码")
    private String xzqhdm;
    @ApiModelProperty(value = "竣工验收日期起始时间，格式：YYYY-MM-dd")
    private String startTime;
    @ApiModelProperty(value = "竣工验收日期结束时间，格式：YYYY-MM-dd")
    private String endTime;

    public String getBuildUnit() {return buildUnit;}

    public void setBuildUnit(String buildUnit) {this.buildUnit = buildUnit;}

    public String getProjName() {return projName;}

    public void setProjName(String projName) {this.projName = projName;}

    public String getXzqhdm() {return xzqhdm;}

    public void setXzqhdm(String xzqhdm) {this.xzqhdm = xzqhdm;}

    public String getStartTime() {return startTime;}

    public void setStartTime(String startTime) {this.startTime = startTime;}

    public String getEndTime() {return endTime;}

    public void setEndTime(String endTime) {this.endTime = endTime;}

    @Override
    public String toString() {
        return "BdcJgysbaRequestDTO{" +
                "buildUnit='" + buildUnit + '\'' +
                ", projName='" + projName + '\'' +
                ", xzqhdm='" + xzqhdm + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
