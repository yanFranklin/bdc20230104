package cn.gtmap.realestate.common.core.qo.inquiry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @ApiModelProperty("工作流定义名称")
    private String processDefinitionName;

    @ApiModelProperty("节点名称")
    private String activityName;

    @ApiModelProperty("工作流实例ID")
    private String processInstanceId;

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

    @ApiModelProperty(value = "全选导出条数配置")
    private Boolean dctspz;

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

    @ApiModelProperty("日志类别:用于区分日志类别，在查询es日志时传对应的参数key")
    private String logCategory;

    @ApiModelProperty("扩展查询参数")
    private Map<String,String> extendMap;

    @ApiModelProperty("es日志查询条件")
    private String condition;

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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getViewTypeName() {
        return viewTypeName;
    }

    public void setViewTypeName(String viewTypeName) {
        this.viewTypeName = viewTypeName;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
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

    public String getProcessDefinitionName() {
        return processDefinitionName;
    }

    public void setProcessDefinitionName(String processDefinitionName) {
        this.processDefinitionName = processDefinitionName;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getCxbh() {
        return cxbh;
    }

    public void setCxbh(String cxbh) {
        this.cxbh = cxbh;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
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

    public String getLogCategory() {
        return logCategory;
    }

    public void setLogCategory(String logCategory) {
        this.logCategory = logCategory;
    }

    public Map<String, String> getExtendMap() {
        return extendMap;
    }

    public void setExtendMap(Map<String, String> extendMap) {
        this.extendMap = extendMap;
    }

    public Boolean getDctspz() {
        return dctspz;
    }

    public void setDctspz(Boolean dctspz) {
        this.dctspz = dctspz;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcLogQO{");
        sb.append("eventName='").append(eventName).append('\'');
        sb.append(", beginTime=").append(beginTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", principal='").append(principal).append('\'');
        sb.append(", alias='").append(alias).append('\'');
        sb.append(", viewTypeName='").append(viewTypeName).append('\'');
        sb.append(", qlr='").append(qlr).append('\'');
        sb.append(", slbh='").append(slbh).append('\'');
        sb.append(", zjh='").append(zjh).append('\'');
        sb.append(", cqzh='").append(cqzh).append('\'');
        sb.append(", bdcdyh='").append(bdcdyh).append('\'');
        sb.append(", processDefinitionName='").append(processDefinitionName).append('\'');
        sb.append(", activityName='").append(activityName).append('\'');
        sb.append(", processInstanceId='").append(processInstanceId).append('\'');
        sb.append(", response='").append(response).append('\'');
        sb.append(", cxbh='").append(cxbh).append('\'');
        sb.append(", zl='").append(zl).append('\'');
        sb.append(", cxfs='").append(cxfs).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", dctspz=").append(dctspz);
        sb.append(", zmbh='").append(zmbh).append('\'');
        sb.append(", printType='").append(printType).append('\'');
        sb.append(", ipaddress='").append(ipaddress).append('\'');
        sb.append(", organization='").append(organization).append('\'');
        sb.append(", cxtj='").append(cxtj).append('\'');
        sb.append(", cxjg='").append(cxjg).append('\'');
        sb.append(", logCategory='").append(logCategory).append('\'');
        sb.append(", extendMap=").append(extendMap);
        sb.append(", condition='").append(condition).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
