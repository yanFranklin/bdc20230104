package cn.gtmap.realestate.common.core.qo.natural;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2019/3/15
 * @description
 */
@Api(value = "ZrzyLogQO", description = "日志查询对象")
public class ZrzyLogQO {

    @ApiModelProperty("事件")
    private String eventName;

    @ApiModelProperty(value = "查询开始时间")
    private Date beginTime;

    @ApiModelProperty(value = "查询结束时间")
    private Date endTime;

    @ApiModelProperty("用户名")
    private String principal;

    @ApiModelProperty("姓名")
    private String alias;

    @ApiModelProperty("台账名称")
    private String viewTypeName;

    @ApiModelProperty("权利人")
    private String qlr;

    @ApiModelProperty("受理编号")
    private String slbh;

    @ApiModelProperty("证件号")
    private String zjh;

    @ApiModelProperty("产权证号")
    private String cqzh;

    @ApiModelProperty("不动产单元号")
    private String bdcdyh;

    @ApiModelProperty("响应结果")
    private String response;

    @ApiModelProperty("查询编号")
    private String cxbh;

    @ApiModelProperty("坐落")
    private String zl;

    @ApiModelProperty(value = "精确查询:equal  模糊查询:like")
    private String cxfs;

    @ApiModelProperty(value = "查询类型")
    private String type;

    @ApiModelProperty(value = "证明编号")
    private String zmbh;

    @ApiModelProperty(value = "打印类型")
    private String printType;

    @ApiModelProperty("综合查询 IP地址")
    private String ipaddress;

    @ApiModelProperty("部门")
    private String organization;

    @ApiModelProperty("查询条件")
    private String cxtj;

    @ApiModelProperty("查询结果")
    private String cxjg;

    public String getCxtj() { return cxtj; }

    public void setCxtj(String cxtj) { this.cxtj = cxtj; }

    public String getCxjg() { return cxjg; }

    public void setCxjg(String cxjg) { this.cxjg = cxjg; }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    public String getCqzh() {
        return cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getCxfs() {
        return cxfs;
    }

    public void setCxfs(String cxfs) {
        this.cxfs = cxfs;
    }

    public String getViewTypeName() {
        return viewTypeName;
    }

    public void setViewTypeName(String viewTypeName) {
        this.viewTypeName = viewTypeName;
    }

    public String getCxbh() {
        return cxbh;
    }

    public void setCxbh(String cxbh) {
        this.cxbh = cxbh;
    }

    public String getZmbh() {
        return zmbh;
    }

    public void setZmbh(String zmbh) {
        this.zmbh = zmbh;
    }

    public String getPrintType() {
        return printType;
    }

    public void setPrintType(String printType) {
        this.printType = printType;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }


    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return "BdcLogQO{" +
                "eventName='" + eventName + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", principal='" + principal + '\'' +
                ", alias='" + alias + '\'' +
                ", viewTypeName='" + viewTypeName + '\'' +
                ", qlr='" + qlr + '\'' +
                ", slbh='" + slbh + '\'' +
                ", zjh='" + zjh + '\'' +
                ", cqzh='" + cqzh + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", response='" + response + '\'' +
                ", cxbh='" + cxbh + '\'' +
                ", zl='" + zl + '\'' +
                ", cxfs='" + cxfs + '\'' +
                ", type='" + type + '\'' +
                ", zmbh='" + zmbh + '\'' +
                ", printType='" + printType + '\'' +
                ", ipaddress='" + ipaddress + '\'' +
                ", organization='" + organization + '\'' +
                ", cxtj='" + cxtj + '\'' +
                ", cxjg='" + cxjg + '\'' +
                '}';
    }
}
