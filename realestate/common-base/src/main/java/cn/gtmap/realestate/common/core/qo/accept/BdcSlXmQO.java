package cn.gtmap.realestate.common.core.qo.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/3/26
 * @description 受理项目查询对象
 */
@ApiModel(value = "BdcSlXmQO", description = "受理项目查询QO对象")
public class BdcSlXmQO {

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "基本信息ID")
    private String jbxxid;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "权利人")
    private String qlr;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "原不动产权证")
    private String ybdcqz;

    @ApiModelProperty(value = "登记小类")
    private String djxl;

    @ApiModelProperty(value = "是否增量初始化业务  0：否  1：是")
    private Integer sfzlcsh;

    @ApiModelProperty(value = "项目ID集合")
    private List<String> xmidList;

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "工作流实例id")
    private String gzlslid;

    @ApiModelProperty(value = "权利类型")
    private List qllxs;

    @ApiModelProperty(value = "排序字段")
    private String sort;

    @ApiModelProperty(value = "审批系统业务号")
    private String spxtywh;

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getJbxxid() {
        return jbxxid;
    }

    public void setJbxxid(String jbxxid) {
        this.jbxxid = jbxxid;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getYbdcqz() {
        return ybdcqz;
    }

    public void setYbdcqz(String ybdcqz) {
        this.ybdcqz = ybdcqz;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public Integer getSfzlcsh() {
        return sfzlcsh;
    }

    public void setSfzlcsh(Integer sfzlcsh) {
        this.sfzlcsh = sfzlcsh;
    }

    public List<String> getXmidList() {
        return xmidList;
    }

    public void setXmidList(List<String> xmidList) {
        this.xmidList = xmidList;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public List getQllxs() {
        return qllxs;
    }

    public void setQllxs(List qllxs) {
        this.qllxs = qllxs;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSpxtywh() {
        return spxtywh;
    }

    public void setSpxtywh(String spxtywh) {
        this.spxtywh = spxtywh;
    }

    @Override
    public String toString() {
        return "BdcSlXmQO{" +
                "xmid='" + xmid + '\'' +
                ", jbxxid='" + jbxxid + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", qlr='" + qlr + '\'' +
                ", zl='" + zl + '\'' +
                ", ybdcqz='" + ybdcqz + '\'' +
                ", djxl='" + djxl + '\'' +
                ", sfzlcsh=" + sfzlcsh +
                ", xmidList=" + xmidList +
                ", slbh='" + slbh + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", qllxs=" + qllxs +
                ", sort='" + sort + '\'' +
                ", spxtywh='" + spxtywh + '\'' +
                '}';
    }
}
