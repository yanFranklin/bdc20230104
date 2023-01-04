package cn.gtmap.realestate.common.core.qo.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/24
 * @description 受理核税信息查询QO对象
 */
@ApiModel(value = "BdcSlHsxxQO", description = "受理核税信息查询QO对象")
public class BdcSlHsxxQO {

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "申请人类别")
    private String sqrlb;

    @ApiModelProperty(value = "核税信息ID")
    private String hsxxid;

    @ApiModelProperty(value = "核税信息类型")
    private String hsxxlx;

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getSqrlb() {
        return sqrlb;
    }

    public void setSqrlb(String sqrlb) {
        this.sqrlb = sqrlb;
    }

    public String getHsxxid() {
        return hsxxid;
    }

    public void setHsxxid(String hsxxid) {
        this.hsxxid = hsxxid;
    }

    public String getHsxxlx() {
        return hsxxlx;
    }

    public void setHsxxlx(String hsxxlx) {
        this.hsxxlx = hsxxlx;
    }

    @Override
    public String toString() {
        return "BdcSlHsxxQO{" +
                "xmid='" + xmid + '\'' +
                ", sqrlb='" + sqrlb + '\'' +
                ", hsxxid='" + hsxxid + '\'' +
                ", hsxxlx='" + hsxxlx + '\'' +
                '}';
    }
}
