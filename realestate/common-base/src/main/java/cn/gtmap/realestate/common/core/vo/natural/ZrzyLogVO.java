package cn.gtmap.realestate.common.core.vo.natural;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2019/3/5
 * @description
 */
@ApiModel(value = "ZrzyLogVO", description = "日志信息")
public class ZrzyLogVO {

    @ApiModelProperty("打印模板路径")
    private String modelUrl;

    @ApiModelProperty("打印数据源请求")
    private String dataUrl;

    @ApiModelProperty("打印数据源xml")
    private String xmlStr;

    @ApiModelProperty("打印类型")
    private String printType;

    @ApiModelProperty("证明编号")
    private String zmbh;

    @ApiModelProperty("受理编号")
    private String slbh;

    @ApiModelProperty("权利人")
    private String qlr;

    @ApiModelProperty("不动产单元号")
    private String bdcdyh;

    @ApiModelProperty("坐落")
    private String zl;

    @ApiModelProperty("义务人")
    private String ywr;

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("资源或台账名称")
    private String zyTzName;

    @ApiModelProperty("操作IP")
    private String ip;

    @ApiModelProperty("请求服务地址")
    private String requestUrl;

    @ApiModelProperty("事件名")
    private String eventName;

    @ApiModelProperty("用户名")
    private String principal;

    @ApiModelProperty("姓名")
    private String alias;

    @ApiModelProperty("查询参数")
    private String param;

    @ApiModelProperty("查询参数 中文")
    private String paramCha;

    @ApiModelProperty("响应结果")
    private String response;

    @ApiModelProperty("接口请求参数")
    private String request;

    @ApiModelProperty("操作时间")
    private Date time;

    @ApiModelProperty("台账类型")
    private String viewType;

    @ApiModelProperty("台账类型名称")
    private String viewTypeName;

    @ApiModelProperty("台账地址 用于日志重现")
    private String viewAddress;

    @ApiModelProperty("综合查询 查询条件")
    private String cxtj;

    @ApiModelProperty("综合查询 查询结果")
    private String cxjg;

    @ApiModelProperty("综合查询 IP地址")
    private String ipaddress;

    @ApiModelProperty("组织机构")
    private String organization;

    @ApiModelProperty("IP地址")
    private String remoteAddr;

    @ApiModelProperty("客户端IP地址")
    private String clientIp;

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getCxtj() {
        return cxtj;
    }

    public void setCxtj(String cxtj) {
        this.cxtj = cxtj;
    }

    public String getCxjg() {
        return cxjg;
    }

    public void setCxjg(String cxjg) {
        this.cxjg = cxjg;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getModelUrl() {
        return modelUrl;
    }

    public void setModelUrl(String modelUrl) {
        this.modelUrl = modelUrl;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public String getZyTzName() {
        return zyTzName;
    }

    public void setZyTzName(String zyTzName) {
        this.zyTzName = zyTzName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

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

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
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

    public String getXmlStr() {
        return xmlStr;
    }

    public void setXmlStr(String xmlStr) {
        this.xmlStr = xmlStr;
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

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
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

    public String getYwr() {
        return ywr;
    }

    public void setYwr(String ywr) {
        this.ywr = ywr;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }
}
