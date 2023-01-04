package cn.gtmap.realestate.exchange.core.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/4
 * @description
 */
public class YzwYzsbQsgcxxVO {

    @ApiModelProperty(value = "受理人")
    private String slr;

    @ApiModelProperty(value = "受理部门")
    private String slbm;

    @ApiModelProperty(value = "受理时间")
    private Date slsj;

    @ApiModelProperty(value = "过程办理人")
    private String gcblr;

    @ApiModelProperty(value = "过程开始时间")
    private Date gckssj;

    @ApiModelProperty(value = "过程结束时间")
    private Date gcjssj;

    public String getSlr() {
        return slr;
    }

    public void setSlr(String slr) {
        this.slr = slr;
    }

    public String getSlbm() {
        return slbm;
    }

    public void setSlbm(String slbm) {
        this.slbm = slbm;
    }

    public Date getSlsj() {
        return slsj;
    }

    public void setSlsj(Date slsj) {
        this.slsj = slsj;
    }

    public String getGcblr() {
        return gcblr;
    }

    public void setGcblr(String gcblr) {
        this.gcblr = gcblr;
    }

    public Date getGckssj() {
        return gckssj;
    }

    public void setGckssj(Date gckssj) {
        this.gckssj = gckssj;
    }

    public Date getGcjssj() {
        return gcjssj;
    }

    public void setGcjssj(Date gcjssj) {
        this.gcjssj = gcjssj;
    }
}
