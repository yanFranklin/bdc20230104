package cn.gtmap.realestate.supervise.core.dto;

import cn.gtmap.realestate.supervise.core.domain.BdcLfYcbjyjCkbjDO;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:zhuyong@gmail.com">zhuyong</a>
 * @version V1.0, 2021/10/25
 * @description 异常办件预警-超快办件信息
 */
public class BdcLfYcbjyjCkbjDTO extends BdcLfYcbjyjCkbjDO {
    @ApiModelProperty(value = "异常办件原因")
    private String ycbjyy;

    @ApiModelProperty(value = "异常办件ID")
    private String ycbjid;

    @ApiModelProperty(value = "异常详情描述")
    private String yysm;

    @ApiModelProperty(value = "部门名称")
    private String bmmc;


    public String getYcbjid() {
        return ycbjid;
    }

    public void setYcbjid(String ycbjid) {
        this.ycbjid = ycbjid;
    }

    public String getYcbjyy() {
        return ycbjyy;
    }

    public void setYcbjyy(String ycbjyy) {
        this.ycbjyy = ycbjyy;
    }

    public String getYysm() {
        return yysm;
    }

    public void setYysm(String yysm) {
        this.yysm = yysm;
    }

    public String getBmmc() {
        return bmmc;
    }

    public void setBmmc(String bmmc) {
        this.bmmc = bmmc;
    }

    @Override
    public String toString() {
        return "BdcLfYcbjyjCkbjDTO{" +
                "ycbjyy='" + ycbjyy + '\'' +
                ", ycbjid='" + ycbjid + '\'' +
                ", yysm='" + yysm + '\'' +
                ", bmmc='" + bmmc + '\'' +
                '}';
    }
}
