package cn.gtmap.realestate.common.core.dto.register;

import io.swagger.annotations.ApiModelProperty;

/**
 * @program: realestate
 * @description: 抵押权信息DTO
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-09-15 14:22
 **/
public class BdcDyaqQlrDTO {

    @ApiModelProperty("权利id主键")
    private String qlid;

    @ApiModelProperty("项目id")
    private String xmid;

    @ApiModelProperty("权利人类别")
    private String qlrlb;

    @ApiModelProperty("受理编号")
    private String slbh;

    public String getQlid() {
        return qlid;
    }

    public void setQlid(String qlid) {
        this.qlid = qlid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getQlrlb() {
        return qlrlb;
    }

    public void setQlrlb(String qlrlb) {
        this.qlrlb = qlrlb;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    @Override
    public String toString() {
        return "BdcDyaqQlrDTO{" +
                "qlid='" + qlid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", qlrlb='" + qlrlb + '\'' +
                ", slbh='" + slbh + '\'' +
                '}';
    }
}
