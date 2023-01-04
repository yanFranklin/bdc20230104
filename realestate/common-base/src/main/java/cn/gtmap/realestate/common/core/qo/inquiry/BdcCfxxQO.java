package cn.gtmap.realestate.common.core.qo.inquiry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2019/07/10
 * @description
 */
@Api(value = "BdcCfxxQO", description = "查封信息查询对象")
public class BdcCfxxQO {

    @ApiModelProperty("xmid")
    private String xmid;

    @ApiModelProperty("查封类型")
    private String cflx;

    @ApiModelProperty("查封起始时间")
    private String cfqssj;

    @ApiModelProperty("查封文号")
    private String cfwh;

    @ApiModelProperty("查封结束时间")
    private String cfjssj;

    @ApiModelProperty("登簿人")
    private String dbr;

    @ApiModelProperty("权属状态")
    private String qszt;

    @ApiModelProperty("权利人")
    private String qlr;

    @ApiModelProperty("被执行人")
    private String bzxr;

    @ApiModelProperty("坐落")
    private String zl;

    @ApiModelProperty("不动产单元号")
    private String bdcdyh;

    @ApiModelProperty("不动产权证号")
    private String bdcqzh;

    @ApiModelProperty("是否超期")
    private String isBeyondDeadLine;

    @ApiModelProperty("登记原因")
    private String djyy;

    @ApiModelProperty("受理编号")
    private String slbh;

    @ApiModelProperty("权利类型")
    private Integer qllx;

    @ApiModelProperty("登记小类")
    private String djxl;
    @ApiModelProperty("是否分页,0:不分页，1:分页，默认不分页")
    private int queryType;

    @ApiModelProperty("不分页情况下返回到期查封数据的条数，不传则返回全部，默认10条")
    private int rowNum = 10;

    @ApiModelProperty("上一手查封类型")
    private String ycflx;

    @ApiModelProperty("上一手xmid")
    private String yxmid;


    @ApiModelProperty("工作流实例id")
    private String gzlslid;

    @ApiModelProperty("单元号集合")
    private List<String> bdcdyhList;

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public List<String> getBdcdyhList() {
        return bdcdyhList;
    }

    public void setBdcdyhList(List<String> bdcdyhList) {
        this.bdcdyhList = bdcdyhList;
    }

    public String getBzxr() {
        return bzxr;
    }

    public void setBzxr(String bzxr) {
        this.bzxr = bzxr;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getIsBeyondDeadLine() {
        return isBeyondDeadLine;
    }

    public void setIsBeyondDeadLine(String isBeyondDeadLine) {
        this.isBeyondDeadLine = isBeyondDeadLine;
    }

    public String getCfwh() {
        return cfwh;
    }

    public void setCfwh(String cfwh) {
        this.cfwh = cfwh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getCflx() {
        return cflx;
    }

    public void setCflx(String cflx) {
        this.cflx = cflx;
    }

    public String getCfqssj() {
        return cfqssj;
    }

    public void setCfqssj(String cfqssj) {
        this.cfqssj = cfqssj;
    }

    public String getCfjssj() {
        return cfjssj;
    }

    public void setCfjssj(String cfjssj) {
        this.cfjssj = cfjssj;
    }

    public String getDbr() {
        return dbr;
    }

    public void setDbr(String dbr) {
        this.dbr = dbr;
    }

    public String getQszt() {
        return qszt;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public int getQueryType() {
        return queryType;
    }

    public void setQueryType(int queryType) {
        this.queryType = queryType;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public String getYcflx() {
        return ycflx;
    }

    public void setYcflx(String ycflx) {
        this.ycflx = ycflx;
    }

    public String getYxmid() {
        return yxmid;
    }

    public void setYxmid(String yxmid) {
        this.yxmid = yxmid;
    }
}
