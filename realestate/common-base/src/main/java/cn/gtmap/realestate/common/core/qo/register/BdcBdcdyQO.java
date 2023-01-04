package cn.gtmap.realestate.common.core.qo.register;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/11/13
 * @description 单元信息查询对象类
 */
public class BdcBdcdyQO {

    /**
     * 项目ID
     */
    @ApiModelProperty(value = "项目ID")
    private String xmid;
    /**
     * 工作流实例ID
     */
    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;
    /**
     * 受理编号
     */
    @ApiModelProperty(value = "受理编号")
    private String slbh;

    /**
     * 不动产单元号
     */
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    /**
     * 坐落
     */
    @ApiModelProperty(value = "坐落")
    private String zl;

    /**
     * 登记小类
     */
    @ApiModelProperty(value = "登记小类")
    private String djxl;

    /**
     * 定着物用途
     */
    @ApiModelProperty(value = "定着物用途")
    private String dzwyt;

    /**
     * 房间号
     */
    @ApiModelProperty(value = "房间号")
    private String fjh;

    @ApiModelProperty(value = "权利类型")
    private String qllx;

    @ApiModelProperty(value = "项目ID集合")
    private List<String> xmidList;

    @ApiModelProperty(value = "是否原权利")
    private boolean sfyql;

    @ApiModelProperty(value = "是否关联宗地基本信息")
    private boolean glzdjbxx;

    @ApiModelProperty(value = "权属状态")
    private Integer qszt;

    @ApiModelProperty("权力类型集合")
    private List<String> qllxList;

    @ApiModelProperty("是否限制权利")
    private boolean sfxzql = true;

    @ApiModelProperty("")
    private int page;

    @ApiModelProperty("")
    private int size;

    @ApiModelProperty("")
    private String sort;

    @ApiModelProperty("")
    private String sortParameter;

    @ApiModelProperty("")
    private List<List> xmidPartList;

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

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public String getDzwyt() {
        return dzwyt;
    }

    public void setDzwyt(String dzwyt) {
        this.dzwyt = dzwyt;
    }

    public String getFjh() {
        return fjh;
    }

    public void setFjh(String fjh) {
        this.fjh = fjh;
    }

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public List<String> getXmidList() {
        return xmidList;
    }

    public void setXmidList(List<String> xmidList) {
        this.xmidList = xmidList;
    }

    public boolean isSfyql() {
        return sfyql;
    }

    public void setSfyql(boolean sfyql) {
        this.sfyql = sfyql;
    }

    public boolean isGlzdjbxx() {
        return glzdjbxx;
    }

    public void setGlzdjbxx(boolean glzdjbxx) {
        this.glzdjbxx = glzdjbxx;
    }

    public Integer getQszt() {
        return qszt;
    }

    public void setQszt(Integer qszt) {
        this.qszt = qszt;
    }

    public List<String> getQllxList() {
        return qllxList;
    }

    public void setQllxList(List<String> qllxList) {
        this.qllxList = qllxList;
    }

    public boolean getSfxzql() {
        return sfxzql;
    }

    public void setSfxzql(boolean sfxzql) {
        this.sfxzql = sfxzql;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSortParameter() {
        return sortParameter;
    }

    public void setSortParameter(String sortParameter) {
        this.sortParameter = sortParameter;
    }

    public List<List> getXmidPartList() {
        return xmidPartList;
    }

    public void setXmidPartList(List<List> xmidPartList) {
        this.xmidPartList = xmidPartList;
    }

    @Override
    public String toString() {
        return "BdcBdcdyQO{" +
                "xmid='" + xmid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", slbh='" + slbh + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", zl='" + zl + '\'' +
                ", djxl='" + djxl + '\'' +
                ", dzwyt='" + dzwyt + '\'' +
                ", fjh='" + fjh + '\'' +
                ", qllx='" + qllx + '\'' +
                ", xmidList=" + xmidList +
                ", sfyql=" + sfyql +
                ", glzdjbxx=" + glzdjbxx +
                ", qszt=" + qszt +
                ", qllxList=" + qllxList +
                ", sfxzql=" + sfxzql +
                ", page=" + page +
                ", size=" + size +
                ", sort='" + sort + '\'' +
                ", sortParameter='" + sortParameter + '\'' +
                ", xmidPartList=" + xmidPartList +
                '}';
    }
}
