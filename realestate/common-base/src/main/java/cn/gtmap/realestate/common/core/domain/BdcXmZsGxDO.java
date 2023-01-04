package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href ="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.3, 2019/02/14
 * @description 项目证书关系
 */
@Table(
        name = "BDC_XM_ZS_GX"
)
@ApiModel(value = "BdcXmZsGxDO", description = "不动产项目证书关系")
public class BdcXmZsGxDO {
    @Id
    /**主键*/
    @ApiModelProperty(value = "主键ID")
    private String gxid;
    /**项目id*/
    @ApiModelProperty(value = "项目ID")
    private String xmid;
    /**证书id*/
    @ApiModelProperty(value = "证书ID")
    private String zsid;

    public String getGxid() {
        return gxid;
    }

    public void setGxid(String gxid) {
        this.gxid = gxid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getZsid() {
        return zsid;
    }

    public void setZsid(String zsid) {
        this.zsid = zsid;
    }

    @Override
    public String toString() {
        return "BdcXmZsGxDO{" +
                "gxid='" + gxid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", zsid='" + zsid + '\'' +
                '}';
    }
}
