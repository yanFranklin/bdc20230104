package cn.gtmap.realestate.common.core.qo.certificate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 交接单流程信息查询对象
 *
 * @author <a href="mailto:lixin1@gtmap.cn>lixin</a>"
 * @version 1.0, 2019/11/20
 */
@ApiModel(value = "BdcJjdXmQO", description = "交接单查询QO")
public class BdcJjdXmQO {
    @ApiModelProperty(value = "案件状态")
    private Integer ajzt;
    @ApiModelProperty(value = "开始时间")
    private String begin;
    @ApiModelProperty(value = "结束时间")
    private String end;
    @ApiModelProperty(value = "受理人 ID")
    private String slrid;
    @ApiModelProperty(value = "是否移交")
    private boolean isYj;
    @ApiModelProperty(value = "登记类型")
    private Integer djlx;
    @ApiModelProperty(value = "工作流ID集合")
    private List<String> glzsldList;

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public Integer getDjlx() {
        return djlx;
    }

    public void setDjlx(Integer djlx) {
        this.djlx = djlx;
    }

    public Integer getAjzt() {
        return ajzt;
    }

    public void setAjzt(Integer ajzt) {
        this.ajzt = ajzt;
    }

    public String getSlrid() {
        return slrid;
    }

    public void setSlrid(String slrid) {
        this.slrid = slrid;
    }

    public boolean isYj() {
        return isYj;
    }

    public void setYj(boolean yj) {
        isYj = yj;
    }

    public List<String> getGlzsldList() {
        return glzsldList;
    }

    public void setGlzsldList(List<String> glzsldList) {
        this.glzsldList = glzsldList;
    }


}
