package cn.gtmap.realestate.common.core.qo.init;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @description 不动产项目查询QO
 */
@ApiModel(value = "BdcXmQO",description = "不动产项目查询参数封装对象")
public class BdcXmQO {
    @ApiModelProperty(value = "项目id")
    private String xmid;

    @ApiModelProperty(value = "权利人")
    private String qlr;

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "工作流实例id")
    private String gzlslid;

    @ApiModelProperty(value = "办结日期")
    private Date bjrq;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "交易合同号")
    private String jyhth;

    @ApiModelProperty(value = "审批系统业务号")
    private String spxtywh;

    @ApiModelProperty(value = "登记小类")
    private String djxl;

    @ApiModelProperty(value = "权属状态")
    private Integer qszt;

    @ApiModelProperty(value = "类型权利")
    private List qllxs;

    @ApiModelProperty(value = "案件状态")
    private Integer ajzt;

    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;

    @ApiModelProperty(value = "审批来源")
    private Integer sply;

    @ApiModelProperty(value = "不动产类型")
    private Integer bdclx;

    @ApiModelProperty(value = "不动产单元号集合")
    private List bdcdyhList;

    @ApiModelProperty(value = "原产权证号")
    private String ycqzh;

    @ApiModelProperty(value = "不动产单元唯一编号")
    private String bdcdywybh;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "坐落 精确查询")
    private String zljq;

    @ApiModelProperty(value = "项目id集合")
    private List<String> xmidList;

    @ApiModelProperty(value = "区县代码")
    private String qxdm;

    @ApiModelProperty(value = "权属状态集合")
    private List qszts;

    @ApiModelProperty(value = "工作流定义IDs")
    private String gzldyids;

    @ApiModelProperty(value = "审批来源集合")
    private List splys;

    @ApiModelProperty(value = "受理编号集合")
    private List<String> slbhList;

    @ApiModelProperty(value = "工作流定义名称")
    private String gzldymc;

    @ApiModelProperty(value = "查询数量")
    private Integer count;

    @ApiModelProperty(value = "当天时间")
    private Date currentDate;

    @ApiModelProperty(value = "政府行政审批编号")
    private String zfxzspbh;

    @ApiModelProperty(value = "受理时间")
    private Date slsjDate;

    @ApiModelProperty(value = "区县代码集合")
    private List<String> qxdmList;

    @ApiModelProperty(value = "义务人")
    private String ywr;

    public static BdcXmQO buildByProcessInsId(String processInsId) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        return bdcXmQO;
    }

    public List<String> getQxdmList() {
        return qxdmList;
    }

    public void setQxdmList(List<String> qxdmList) {
        this.qxdmList = qxdmList;
    }

    public Date getSlsjDate() {
        return slsjDate;
    }

    public void setSlsjDate(Date slsjDate) {
        this.slsjDate = slsjDate;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getZljq() {
        return zljq;
    }

    public void setZljq(String zljq) {
        this.zljq = zljq;
    }

    public List getSplys() {
        return splys;
    }

    public void setSplys(List splys) {
        this.splys = splys;
    }

    public List getBdcdyhList() {
        return bdcdyhList;
    }

    public void setBdcdyhList(List bdcdyhList) {
        this.bdcdyhList = bdcdyhList;
    }

    public Integer getAjzt() {
        return ajzt;
    }

    public void setAjzt(Integer ajzt) {
        this.ajzt = ajzt;
    }

    public List getQllxs() {
        return qllxs;
    }

    public void setQllxs(List qllxs) {
        this.qllxs = qllxs;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
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

    public Date getBjrq() {
        return bjrq;
    }

    public void setBjrq(Date bjrq) {
        this.bjrq = bjrq;
    }

    public String getJyhth() {
        return jyhth;
    }

    public void setJyhth(String jyhth) {
        this.jyhth = jyhth;
    }

    public String getSpxtywh() {
        return spxtywh;
    }

    public void setSpxtywh(String spxtywh) {
        this.spxtywh = spxtywh;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public Integer getQszt() {
        return qszt;
    }

    public void setQszt(Integer qszt) {
        this.qszt = qszt;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public Integer getSply() {
        return sply;
    }

    public void setSply(Integer sply) {
        this.sply = sply;
    }

    public Integer getBdclx() {
        return bdclx;
    }

    public void setBdclx(Integer bdclx) {
        this.bdclx = bdclx;
    }

    public String getYcqzh() {
        return ycqzh;
    }

    public void setYcqzh(String ycqzh) {
        this.ycqzh = ycqzh;
    }

    public String getBdcdywybh() {
        return bdcdywybh;
    }

    public void setBdcdywybh(String bdcdywybh) {
        this.bdcdywybh = bdcdywybh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public List<String> getXmidList() {
        return xmidList;
    }

    public void setXmidList(List<String> xmidList) {
        this.xmidList = xmidList;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public List getQszts() {
        return qszts;
    }

    public void setQszts(List qszts) {
        this.qszts = qszts;
    }

    public String getGzldyids() {
        return gzldyids;
    }

    public void setGzldyids(String gzldyids) {
        this.gzldyids = gzldyids;
    }

    public BdcXmQO() {
    }

    public BdcXmQO(String xmid) {
        this.xmid = xmid;
    }

    public BdcXmQO withGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
        return this;
    }

    public List<String> getSlbhList() {
        return slbhList;
    }

    public void setSlbhList(List<String> slbhList) {
        this.slbhList = slbhList;
    }

    public String getGzldymc() {
        return gzldymc;
    }

    public void setGzldymc(String gzldymc) {
        this.gzldymc = gzldymc;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public String getZfxzspbh() {
        return zfxzspbh;
    }

    public void setZfxzspbh(String zfxzspbh) {
        this.zfxzspbh = zfxzspbh;
    }

    public String getYwr() {
        return ywr;
    }

    public void setYwr(String ywr) {
        this.ywr = ywr;
    }

    @Override
    public String toString() {
        return "BdcXmQO{" +
                "xmid='" + xmid + '\'' +
                ", qlr='" + qlr + '\'' +
                ", slbh='" + slbh + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", bjrq=" + bjrq +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", jyhth='" + jyhth + '\'' +
                ", spxtywh='" + spxtywh + '\'' +
                ", djxl='" + djxl + '\'' +
                ", qszt=" + qszt +
                ", qllxs=" + qllxs +
                ", ajzt=" + ajzt +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", sply=" + sply +
                ", bdclx=" + bdclx +
                ", bdcdyhList=" + bdcdyhList +
                ", ycqzh='" + ycqzh + '\'' +
                ", bdcdywybh='" + bdcdywybh + '\'' +
                ", zl='" + zl + '\'' +
                ", zljq='" + zljq + '\'' +
                ", xmidList=" + xmidList +
                ", qxdm='" + qxdm + '\'' +
                ", qszts=" + qszts +
                ", gzldyids='" + gzldyids + '\'' +
                ", splys=" + splys +
                ", slbhList=" + slbhList +
                ", gzldymc='" + gzldymc + '\'' +
                ", count=" + count +
                ", currentDate=" + currentDate +
                ", zfxzspbh='" + zfxzspbh + '\'' +
                ", slsjDate=" + slsjDate +
                ", qxdmList=" + qxdmList +
                ", ywr='" + ywr + '\'' +
                '}';
    }
}
