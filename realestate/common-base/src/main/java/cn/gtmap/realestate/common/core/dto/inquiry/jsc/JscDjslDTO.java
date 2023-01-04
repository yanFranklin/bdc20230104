package cn.gtmap.realestate.common.core.dto.inquiry.jsc;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 *
 * @author wangyinghao
 */
public class JscDjslDTO {
    @ApiModelProperty("数量")
    private Integer num;

    @ApiModelProperty("xm")
    private String xmid;

    @ApiModelProperty("时间")
    private Date djsj;

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("汇总时间")
    private String tendencyDate;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTendencyDate() {
        return tendencyDate;
    }

    public void setTendencyDate(String tendencyDate) {
        this.tendencyDate = tendencyDate;
    }
}
