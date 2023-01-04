package cn.gtmap.realestate.common.core.qo.inquiry.count;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author: <a href="@mailto:hynkoala@163.com">hanyaning</a>
 * @version: V1.0, 2019.03.04
 * @description: 工作量统计qo对象
 */
public class GzltjQO {

    @ApiModelProperty("大云案件状态")
    private String statisticsType;

    @ApiModelProperty("案件状态")
    private Integer ajzt;

    @ApiModelProperty("登记机构")
    private String djjg;
    @ApiModelProperty("登记机构集合")
    private List djjglist;

    @ApiModelProperty("人员id")
    private String ryid;

    @ApiModelProperty("人员名称")
    private String slrmc;
    @ApiModelProperty("人员名称集合")
    private List slrmclist;

    @ApiModelProperty("部门id")
    private String bmid;

    @ApiModelProperty("节点id")
    private String jdid;

    @ApiModelProperty("节点名称")
    private String jdmc;
    @ApiModelProperty("节点名称集合")
    private List jdmclist;

    @ApiModelProperty("流程代码")
    private String process;

    @ApiModelProperty("流程名称")
    private String processname;
    @ApiModelProperty("流程名称集合")
    private List processnamelist;

    @ApiModelProperty("统计维度")
    private String dimension;

    @ApiModelProperty("查询起始日期")
    private String kssj;

    @ApiModelProperty("查询截止日期")
    private String jzsj;

    @ApiModelProperty("业务类型")
    private String ywlx;

    @ApiModelProperty("查询类型")
    private String type;

    @ApiModelProperty("打印勾选过滤json")
    private String printFilterJson;

    @ApiModelProperty("用于海门工作量统计，关联查询证书的字段")
    private String printTypeList;

    @ApiModelProperty("受理编号")
    private String slbh;

    @ApiModelProperty(value = "分页当前页码")
    private int page;
    @ApiModelProperty(value = "分页每页数据量")
    private int size;
    @ApiModelProperty(value = "统计类型： 1:按项目 2:按流程")
    private String tjlx;
    @ApiModelProperty(value = "人员id集合")
    private List<String> ryidList;
    @ApiModelProperty(value = "打印类型")
    private String dylx;
    @ApiModelProperty(value = "证书类型: 1:证书， 2：证明")
    private Integer zslx;
    @ApiModelProperty(value = "证书样式：1：纸质证， 2：电子证， 3：纸质与电子证")
    private Integer zsys;
    @ApiModelProperty(value = "权利类型集合")
    private List<Integer> qllxList;

    public String getProcessname() {
        return processname;
    }

    public void setProcessname(String processname) {
        this.processname = processname;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getSlrmc() {
        return slrmc;
    }

    public void setSlrmc(String slrmc) {
        this.slrmc = slrmc;
    }

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getRyid() {
        return ryid;
    }

    public void setRyid(String ryid) {
        this.ryid = ryid;
    }

    public String getBmid() {
        return bmid;
    }

    public void setBmid(String bmid) {
        this.bmid = bmid;
    }

    public String getJdid() {
        return jdid;
    }

    public void setJdid(String jdid) {
        this.jdid = jdid;
    }

    public String getJdmc() {
        return jdmc;
    }

    public void setJdmc(String jdmc) {
        this.jdmc = jdmc;
    }

    public String getKssj() {
        return kssj;
    }

    public void setKssj(String kssj) {
        this.kssj = kssj;
    }

    public String getJzsj() {
        return jzsj;
    }

    public void setJzsj(String jzsj) {
        this.jzsj = jzsj;
    }

    public String getYwlx() {
        return ywlx;
    }

    public void setYwlx(String ywlx) {
        this.ywlx = ywlx;
    }

    public String getPrintFilterJson() {
        return printFilterJson;
    }

    public void setPrintFilterJson(String printFilterJson) {
        this.printFilterJson = printFilterJson;
    }

    public Integer getAjzt() {
        return ajzt;
    }

    public void setAjzt(Integer ajzt) {
        this.ajzt = ajzt;
    }

    public String getStatisticsType() {
        return statisticsType;
    }

    public void setStatisticsType(String statisticsType) {
        this.statisticsType = statisticsType;
    }

    public List getDjjglist() {
        return djjglist;
    }

    public void setDjjglist(List djjglist) {
        this.djjglist = djjglist;
    }

    public List getSlrmclist() {
        return slrmclist;
    }

    public void setSlrmclist(List slrmclist) {
        this.slrmclist = slrmclist;
    }

    public List getJdmclist() {
        return jdmclist;
    }

    public void setJdmclist(List jdmclist) {
        this.jdmclist = jdmclist;
    }

    public List getProcessnamelist() {
        return processnamelist;
    }

    public void setProcessnamelist(List processnamelist) {
        this.processnamelist = processnamelist;
    }

    public String getPrintTypeList() {
        return printTypeList;
    }

    public void setPrintTypeList(String printTypeList) {
        this.printTypeList = printTypeList;
    }

    public String getSlbh() { return slbh; }

    public void setSlbh(String slbh) { this.slbh = slbh; }

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

    public String getTjlx() {
        return tjlx;
    }

    public void setTjlx(String tjlx) {
        this.tjlx = tjlx;
    }

    public List<String> getRyidList() {
        return ryidList;
    }

    public void setRyidList(List<String> ryidList) {
        this.ryidList = ryidList;
    }

    public String getDylx() {
        return dylx;
    }

    public void setDylx(String dylx) {
        this.dylx = dylx;
    }

    public Integer getZslx() {
        return zslx;
    }

    public void setZslx(Integer zslx) {
        this.zslx = zslx;
    }

    public Integer getZsys() {
        return zsys;
    }

    public void setZsys(Integer zsys) {
        this.zsys = zsys;
    }

    public List<Integer> getQllxList() {
        return qllxList;
    }

    public void setQllxList(List<Integer> qllxList) {
        this.qllxList = qllxList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GzltjQO{");
        sb.append("statisticsType='").append(statisticsType).append('\'');
        sb.append(", ajzt=").append(ajzt);
        sb.append(", djjg='").append(djjg).append('\'');
        sb.append(", djjglist=").append(djjglist);
        sb.append(", ryid='").append(ryid).append('\'');
        sb.append(", slrmc='").append(slrmc).append('\'');
        sb.append(", slrmclist=").append(slrmclist);
        sb.append(", bmid='").append(bmid).append('\'');
        sb.append(", jdid='").append(jdid).append('\'');
        sb.append(", jdmc='").append(jdmc).append('\'');
        sb.append(", jdmclist=").append(jdmclist);
        sb.append(", process='").append(process).append('\'');
        sb.append(", processname='").append(processname).append('\'');
        sb.append(", processnamelist=").append(processnamelist);
        sb.append(", dimension='").append(dimension).append('\'');
        sb.append(", kssj='").append(kssj).append('\'');
        sb.append(", jzsj='").append(jzsj).append('\'');
        sb.append(", ywlx='").append(ywlx).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", printFilterJson='").append(printFilterJson).append('\'');
        sb.append(", printTypeList='").append(printTypeList).append('\'');
        sb.append(", slbh='").append(slbh).append('\'');
        sb.append(", page=").append(page);
        sb.append(", size=").append(size);
        sb.append(", tjlx='").append(tjlx).append('\'');
        sb.append(", ryidList=").append(ryidList);
        sb.append(", dylx='").append(dylx).append('\'');
        sb.append(", zslx=").append(zslx);
        sb.append(", zsys=").append(zsys);
        sb.append(", qllxList=").append(qllxList);
        sb.append('}');
        return sb.toString();
    }
}