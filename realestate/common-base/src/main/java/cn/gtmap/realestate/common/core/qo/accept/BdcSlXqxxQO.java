package cn.gtmap.realestate.common.core.qo.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2021/2/23
 * @description
 */
public class BdcSlXqxxQO {

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "工作流实例id")
    private String gzlslid;

    @ApiModelProperty(value = "需求主键id")
    private String xqxxid;

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getXqxxid() {
        return xqxxid;
    }

    public void setXqxxid(String xqxxid) {
        this.xqxxid = xqxxid;
    }

    @Override
    public String toString() {
        return "BdcSlXqxxQO{" +
                "xmid='" + xmid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", xqxxid='" + xqxxid + '\'' +
                '}';
    }
}
