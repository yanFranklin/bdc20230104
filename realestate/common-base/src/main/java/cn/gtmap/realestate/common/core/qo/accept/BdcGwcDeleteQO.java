package cn.gtmap.realestate.common.core.qo.accept;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/3/14
 * @description 购物车删除
 */
public class BdcGwcDeleteQO {

    @ApiModelProperty(value = "多个项目ID,用英文逗号隔开")
    private String xmids;

    @ApiModelProperty(value = "单个项目ID")
    private String onexmid;

    @ApiModelProperty(value = "基本信息ID")
    private String jbxxid;

    @ApiModelProperty(value = "是否增量初始化")
    private Integer sfzlcsh;

    @ApiModelProperty(value = "选择不动产单元页面受理编号")
    private String slbh;

    @ApiModelProperty(value = "页面版本")
    private String systemVersion;

    @ApiModelProperty(value = "清除购物车")
    private Integer clearGwc;

    public String getXmids() {
        return xmids;
    }

    public void setXmids(String xmids) {
        this.xmids = xmids;
    }

    public String getOnexmid() {
        return onexmid;
    }

    public void setOnexmid(String onexmid) {
        this.onexmid = onexmid;
    }

    public String getJbxxid() {
        return jbxxid;
    }

    public void setJbxxid(String jbxxid) {
        this.jbxxid = jbxxid;
    }

    public Integer getSfzlcsh() {
        return sfzlcsh;
    }

    public void setSfzlcsh(Integer sfzlcsh) {
        this.sfzlcsh = sfzlcsh;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public Integer getClearGwc() {
        return clearGwc;
    }

    public void setClearGwc(Integer clearGwc) {
        this.clearGwc = clearGwc;
    }

    @Override
    public String toString() {
        return "BdcGwcDeleteQO{" +
                "xmids='" + xmids + '\'' +
                ", onexmid='" + onexmid + '\'' +
                ", jbxxid='" + jbxxid + '\'' +
                ", sfzlcsh=" + sfzlcsh +
                ", slbh='" + slbh + '\'' +
                ", systemVersion='" + systemVersion + '\'' +
                ", clearGwc=" + clearGwc +
                '}';
    }
}
