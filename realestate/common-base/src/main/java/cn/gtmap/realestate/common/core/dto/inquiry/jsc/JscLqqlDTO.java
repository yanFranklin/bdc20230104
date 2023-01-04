package cn.gtmap.realestate.common.core.dto.inquiry.jsc;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 *
 * @author wangyinghao
 */
public class JscLqqlDTO {
    @ApiModelProperty("数量")
    private Integer num;

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("区县")
    private String qxdm;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }
}
