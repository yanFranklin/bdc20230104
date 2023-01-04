package cn.gtmap.realestate.supervise.core.qo;

import cn.gtmap.realestate.supervise.core.domain.BdcLfYcbjyjFgzsjbjDO;
import io.swagger.annotations.ApiModelProperty;

/**
 * 非工作时间办件查询QO
 */
public class LfFgzsjbjQO extends BdcLfYcbjyjFgzsjbjDO {
    @ApiModelProperty(value = "记录时间起")
    private String jlsjq;

    @ApiModelProperty(value = "记录时间止")
    private String jlsjz;

    @ApiModelProperty(value = "审核时间起")
    private String shsjq;

    @ApiModelProperty(value = "记录时间止")
    private String shsjz;

    @ApiModelProperty(value = "异常办件原因")
    private String ycbjyy;

    @ApiModelProperty(value = "部门id")
    private String bmid;

    @ApiModelProperty(value = "人员名称")
    private String rymc;


    public String getJlsjq() {
        return jlsjq;
    }

    public void setJlsjq(String jlsjq) {
        this.jlsjq = jlsjq;
    }

    public String getJlsjz() {
        return jlsjz;
    }

    public void setJlsjz(String jlsjz) {
        this.jlsjz = jlsjz;
    }

    public String getShsjq() {
        return shsjq;
    }

    public void setShsjq(String shsjq) {
        this.shsjq = shsjq;
    }

    public String getShsjz() {
        return shsjz;
    }

    public void setShsjz(String shsjz) {
        this.shsjz = shsjz;
    }

    public String getYcbjyy() {
        return ycbjyy;
    }

    public void setYcbjyy(String ycbjyy) {
        this.ycbjyy = ycbjyy;
    }

    public String getBmid() {
        return bmid;
    }

    public void setBmid(String bmid) {
        this.bmid = bmid;
    }

    public String getRymc() {
        return rymc;
    }

    public void setRymc(String rymc) {
        this.rymc = rymc;
    }
}
