package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:wutao2@gtmap.cn">wutao</a>
 * @version 1.0  2021/4/19
 * @description 不动产公告关系信息
 */
@Table(name = "BDC_GG_GX")
public class BdcGggxDO {

    @Id
    @ApiModelProperty(value = "主键")
    private String gxid;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "公告ID")
    private String ggid;

    @ApiModelProperty(value = "业务数据ID")
    private String ywsjid;

    public String getYwsjid() {
        return ywsjid;
    }

    public void setYwsjid(String ywsjid) {
        this.ywsjid = ywsjid;
    }

    public String getGxid() { return gxid; }

    public void setGxid(String gxid) { this.gxid = gxid; }

    public String getXmid() { return xmid; }

    public void setXmid(String xmid) { this.xmid = xmid; }

    public String getGgid() { return ggid; }

    public void setGgid(String ggid) { this.ggid = ggid; }

    @Override
    public String toString() {
        return "BdcGggxDO{" +
                "gxid='" + gxid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", ggid=" + ggid +
                '}';
    }
}
