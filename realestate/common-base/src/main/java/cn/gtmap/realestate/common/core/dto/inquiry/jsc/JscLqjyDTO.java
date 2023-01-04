package cn.gtmap.realestate.common.core.dto.inquiry.jsc;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangyinghao
 */
public class JscLqjyDTO {
    @ApiModelProperty("区县代码")
    String qxdm;

    @ApiModelProperty("数量")
    Integer jysl;

    @ApiModelProperty("面积")
    Integer mj;

    @ApiModelProperty("宗地数")
    Integer zds;

    @ApiModelProperty("林地总面积")
    Integer ldmj;

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public Integer getJysl() {
        return jysl;
    }

    public void setJysl(Integer jysl) {
        this.jysl = jysl;
    }

    public Integer getMj() {
        return mj;
    }

    public void setMj(Integer mj) {
        this.mj = mj;
    }

    public Integer getZds() {
        return zds;
    }

    public void setZds(Integer zds) {
        this.zds = zds;
    }

    public Integer getLdmj() {
        return ldmj;
    }

    public void setLdmj(Integer ldmj) {
        this.ldmj = ldmj;
    }
}
