package cn.gtmap.realestate.common.core.qo.analysis;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2019/3/15
 * @description
 */
@Api(value = "BdcLogQO", description = "日志查询对象")
public class BdcLogQO {

    @ApiModelProperty("事件")
    private String eventName;

    @ApiModelProperty(value = "查询开始时间")
    private Date beginTime;

    @ApiModelProperty(value = "查询结束时间")
    private Date endTime;

    @ApiModelProperty("操作用户")
    private String userName;

    @ApiModelProperty("台账名称")
    private String viewTypeName;

    @ApiModelProperty("权利人")
    private String qlr;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
