package cn.gtmap.realestate.common.core.dto.certificate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng/a>"
 * @version 1.0, 2019/8/8
 * @description 项目移交单核验信息dto
 */
@Api(value = "BdcYjdGdxxGxDTO", description = "移交单归档信息dto")
@Table(name = "bdc_xm_yjd_gx")
public class BdcYjdGdxxGxDTO{

    @ApiModelProperty(value = "移交单id")
    private String yjdid;

    @ApiModelProperty(value = "xmid")
    private String xmid;
    @Id
    @ApiModelProperty(value = "gxid")
    private String gxid;

    public String getYjdid() {
        return yjdid;
    }

    public void setYjdid(String yjdid) {
        this.yjdid = yjdid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getGxid() {
        return gxid;
    }

    public void setGxid(String gxid) {
        this.gxid = gxid;
    }
}
