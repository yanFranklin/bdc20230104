package cn.gtmap.realestate.common.core.qo.accept;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 2021/11/02
 * @description 不动产受理收费信息常州QO
 */
public class BdcSfxxCzQO {

    @ApiModelProperty(value = "收费信息ID")
    private String sfxxid;
    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;
    @ApiModelProperty(value = "项目ID")
    private String xmid;
    @ApiModelProperty(value = "是否土地收益金")
    private boolean sftdsyj;
    @ApiModelProperty(value = "票据代码")
    private String pjdm;
    @ApiModelProperty(value = "推送来源")
    private String tsly;
    @ApiModelProperty(value = "收费状态")
    private Integer sfzt;
    @ApiModelProperty(value = "发票号")
    private String fph;
    @ApiModelProperty(value= "缴款方式")
    private String jkfs;
    @ApiModelProperty(value = "推送财政ID")
    private String tsid;
    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @ApiModelProperty(value = "收费信息Id集合")
    private List<String> sfxxidList;
    @ApiModelProperty(value = "减免原因")
    private String jmyy;
    @ApiModelProperty(value = "区县代码")
    private String qxdm;

    public String getSfxxid() {
        return sfxxid;
    }

    public void setSfxxid(String sfxxid) {
        this.sfxxid = sfxxid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public boolean isSftdsyj() {
        return sftdsyj;
    }

    public void setSftdsyj(boolean sftdsyj) {
        this.sftdsyj = sftdsyj;
    }

    public String getPjdm() {
        return pjdm;
    }

    public void setPjdm(String pjdm) {
        this.pjdm = pjdm;
    }

    public String getTsly() {
        return tsly;
    }

    public void setTsly(String tsly) {
        this.tsly = tsly;
    }

    public Integer getSfzt() {
        return sfzt;
    }

    public void setSfzt(Integer sfzt) {
        this.sfzt = sfzt;
    }

    public String getFph() {
        return fph;
    }

    public void setFph(String fph) {
        this.fph = fph;
    }

    public String getJkfs() {
        return jkfs;
    }

    public void setJkfs(String jkfs) {
        this.jkfs = jkfs;
    }

    public String getTsid() {
        return tsid;
    }

    public void setTsid(String tsid) {
        this.tsid = tsid;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public List<String> getSfxxidList() {
        return sfxxidList;
    }

    public void setSfxxidList(List<String> sfxxidList) {
        this.sfxxidList = sfxxidList;
    }

    public String getJmyy() {
        return jmyy;
    }

    public void setJmyy(String jmyy) {
        this.jmyy = jmyy;
    }

    public String getQxdm() { return qxdm; }

    public void setQxdm(String qxdm) { this.qxdm = qxdm; }
}
