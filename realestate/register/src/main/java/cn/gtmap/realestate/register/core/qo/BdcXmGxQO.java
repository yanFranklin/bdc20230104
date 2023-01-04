package cn.gtmap.realestate.register.core.qo;

import io.swagger.annotations.ApiModelProperty;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2020/2/13
 * @description 项目查询QO
 */
public class BdcXmGxQO {
    @ApiModelProperty("项目ID")
    private String xmid;
    @ApiModelProperty("工作流实例ID")
    private String gzlslid;
    @ApiModelProperty("注销原权利")
    private Integer zxyql;
    @ApiModelProperty("外联项目")
    private Integer wlxm;

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

    public Integer getZxyql() {
        return zxyql;
    }

    public void setZxyql(Integer zxyql) {
        this.zxyql = zxyql;
    }

    public Integer getWlxm() {
        return wlxm;
    }

    public void setWlxm(Integer wlxm) {
        this.wlxm = wlxm;
    }

    @Override
    public String toString() {
        return "BdcXmGxQO{" +
                "xmid='" + xmid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", zxyql=" + zxyql +
                ", wlxm=" + wlxm +
                '}';
    }

}
