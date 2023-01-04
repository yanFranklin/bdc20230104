package cn.gtmap.realestate.exchange.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/4
 * @description
 */
@ApiModel(value = "YzwYzsbSjcsVO", description = "项目超期未办结信息")
public class YzwYzsbXmcqwbjVO {

    @ApiModelProperty(value = "过程开始时间")
    private Date gckssj;

    @ApiModelProperty(value = "过程结束时间")
    private Date gcjssj;

    @ApiModelProperty(value = "过程办结人")
    private String gcbjr;

    @ApiModelProperty(value = "承诺时间")
    private Date jgclsj;

    @ApiModelProperty(value = "结果结束时间")
    private Date jgjssj;

    @ApiModelProperty(value = "结果办结人")
    private String jgbjr;

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

    public String getGcbjr() {
        return gcbjr;
    }

    public void setGcbjr(String gcbjr) {
        this.gcbjr = gcbjr;
    }

    public Date getJgclsj() {
        return jgclsj;
    }

    public void setJgclsj(Date jgclsj) {
        this.jgclsj = jgclsj;
    }

    public Date getJgjssj() {
        return jgjssj;
    }

    public void setJgjssj(Date jgjssj) {
        this.jgjssj = jgjssj;
    }

    public String getJgbjr() {
        return jgbjr;
    }

    public void setJgbjr(String jgbjr) {
        this.jgbjr = jgbjr;
    }
}
