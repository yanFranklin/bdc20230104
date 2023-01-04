package cn.gtmap.realestate.common.core.vo.analysis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2019/3/5
 * @description
 */
@ApiModel(value = "BdcLogVO", description = "日志信息")
public class BdcLogVO {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("操作IP")
    private String ip;

    @ApiModelProperty("请求服务地址")
    private String requestUrl;

    @ApiModelProperty("事件名")
    private String eventName;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("查询参数")
    private String param;

    @ApiModelProperty("查询参数 中文")
    private String paramCha;

    @ApiModelProperty("响应结果")
    private String response;

    @ApiModelProperty("操作时间")
    private Date time;

    @ApiModelProperty("台账类型")
    private String viewType;

    @ApiModelProperty("台账类型名称")
    private String viewTypeName;

    @ApiModelProperty("台账地址 用于日志重现")
    private String viewAddress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public String getViewTypeName() {
        return viewTypeName;
    }

    public void setViewTypeName(String viewTypeName) {
        this.viewTypeName = viewTypeName;
    }

    public String getViewAddress() {
        return viewAddress;
    }

    public void setViewAddress(String viewAddress) {
        this.viewAddress = viewAddress;
    }

    public String getParamCha() {
        return paramCha;
    }

    public void setParamCha(String paramCha) {
        this.paramCha = paramCha;
    }
}
