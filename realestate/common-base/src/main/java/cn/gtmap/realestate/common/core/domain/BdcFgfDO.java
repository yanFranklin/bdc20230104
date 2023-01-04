package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @description 不动产项目房改房DO
 */
@Table(
        name = "BDC_FGF"
)
@ApiModel(value = "BdcFgfDO",description = "不动产房改房推送信息")
public class BdcFgfDO {
    @Id
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @ApiModelProperty(value = "xmid")
    private String xmid;
    @ApiModelProperty(value = "办理状态")
    private String blzt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getBlzt() {
        return blzt;
    }

    public void setBlzt(String blzt) {
        this.blzt = blzt;
    }

    @Override
    public String toString() {
        return "BdcFgfDO{" +
                "id='" + id + '\'' +
                ", slbh='" + slbh + '\'' +
                ", xmid='" + xmid + '\'' +
                ", blzt='" + blzt + '\'' +
                '}';
    }
}
