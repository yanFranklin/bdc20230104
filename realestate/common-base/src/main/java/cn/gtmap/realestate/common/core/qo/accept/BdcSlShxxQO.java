package cn.gtmap.realestate.common.core.qo.accept;

import io.swagger.annotations.ApiModelProperty;

/**
 * *
 *
 * @author <a href="mailto:chenyucheng@gtmap.cn>chenyucheng</a>"
 * @version 1.0, 2021/02/23
 * @description 审核信息查询对象类
 */
public class BdcSlShxxQO {
    @ApiModelProperty(value = "审核信息ID")
    String shxxid;
    /**
     * 项目ID
     */
    @ApiModelProperty(value = "项目ID")
    String xmid;
    /**
     * 工作流实例ID
     */
    @ApiModelProperty(value = "工作流实例ID")
    String gzlslid;
    /**
     * 节点名称
     */
    @ApiModelProperty(value = "节点名称")
    String jdmc;
    /**
     * 审核人员姓名
     */
    @ApiModelProperty(value = "审核人员姓名")
    String shryxm;
    /**
     * 是否只是当前节点
     */
    @ApiModelProperty(value = "用于标识是否只是当前节点")
    Boolean onlyCurrentJd;
    /**
     * 签名图片的地址
     */
    @ApiModelProperty(value = "签名图片的地址")
    String signImageUrl;

    @ApiModelProperty(value = "修正信息id")
    private String xzxxid;

    public Boolean getOnlyCurrentJd() {
        return onlyCurrentJd;
    }

    public void setOnlyCurrentJd(Boolean onlyCurrentJd) {
        this.onlyCurrentJd = onlyCurrentJd;
    }

    public String getSignImageUrl() {
        return signImageUrl;
    }

    public void setSignImageUrl(String signImageUrl) {
        this.signImageUrl = signImageUrl;
    }

    public String getShxxid() {
        return shxxid;
    }

    public void setShxxid(String shxxid) {
        this.shxxid = shxxid;
    }
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

    public String getJdmc() {
        return jdmc;
    }

    public void setJdmc(String jdmc) {
        this.jdmc = jdmc;
    }

    public String getShryxm() {
        return shryxm;
    }

    public void setShryxm(String shryxm) {
        this.shryxm = shryxm;
    }

    public String getXzxxid() {
        return xzxxid;
    }

    public void setXzxxid(String xzxxid) {
        this.xzxxid = xzxxid;
    }

    @Override
    public String toString() {
        return "BdcSlShxxQO{" +
                "shxxid='" + shxxid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", jdmc='" + jdmc + '\'' +
                ", shryxm='" + shryxm + '\'' +
                ", onlyCurrentJd=" + onlyCurrentJd +
                ", signImageUrl='" + signImageUrl + '\'' +
                ", xzxxid='" + xzxxid + '\'' +
                '}';
    }

}
