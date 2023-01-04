package cn.gtmap.realestate.common.core.domain.exchange.yzw;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zq
 * Date: 15-12-29
 * Time: 下午11:15
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "inf_apply_process")
public class InfApplyProcessDO {
	@Id
	@ApiModelProperty(value = "流水号")
	private String no;
	@ApiModelProperty(value = "同一办件多个岗位（环节）的编号")
	private Integer noOrd;
	@ApiModelProperty(value = "部门编码")
	private String orgId;
	@ApiModelProperty(value = "部门内部办件编号")
	private String internalNo;
	@ApiModelProperty(value = "权力编码")
	private String itemId;
	@ApiModelProperty(value = "岗位（环节）名称")
	private String tacheName;
	@ApiModelProperty(value = "办理处室")
	private String department;
	@ApiModelProperty(value = "办理人员工号")
	private String userStaffCode;
	@ApiModelProperty(value = "办理人员姓名")
	private String userName;
	@ApiModelProperty(value = "岗位（环节）状态")
	private String status;
	@ApiModelProperty(value = "承诺时限")
	private Integer promise;
	@ApiModelProperty(value = "承诺时限单位")
	private String promiseType;
	@ApiModelProperty(value = "是否承诺时限起始标志")
	private String promiseStartSign;
	@ApiModelProperty(value = "是否风险点")
	private Integer isrisk;
	@ApiModelProperty(value = "风险点类别")
	private String risktype;
	@ApiModelProperty(value = "风险点描述")
	private String riskdescription;
	@ApiModelProperty(value = "风险内控的手段与结果")
	private String riskresult;
	@ApiModelProperty(value = "处理意见")
	private String note;
	@ApiModelProperty(value = "办理附件")
	private String attachment;
	@ApiModelProperty(value = "处理时间")
	private Date processDate;
	@ApiModelProperty(value = "录入自建系统时间")
	private Date createDate;
	@ApiModelProperty(value = "写入前置机时间")
	private Date updateDate;
	@ApiModelProperty(value = "信息同步读取时间")
	private Date syncDate;
	@ApiModelProperty(value = "信息同步标志")
	private String syncSign;
	@ApiModelProperty(value = "信息同步错误原因")
	private String syncErrorDesc;
	@ApiModelProperty(value = "事件代码")
	private String eventCode;
	@ApiModelProperty(value = "事件名称")
	private String event_name;
	@ApiModelProperty(value = "过程申请日期")
	private Date startTime;
	@ApiModelProperty(value = "过程申请时限")
	private Integer eventTime;
	@ApiModelProperty(value = "过程时限单位")
	private String eventTimeType;
	@ApiModelProperty(value = "过程申请说明")
	private String startNote;
	@ApiModelProperty(value = "过程申请批准人姓名")
	private String startUserName;
	@ApiModelProperty(value = "过程申请批准人电话")
	private String startTel;
	@ApiModelProperty(value = "过程批准人手机")
	private String startPhone;
	@ApiModelProperty(value = "事件实际结束时间")
	private Date endTime;
	@ApiModelProperty(value = "事件结束说明")
	private String endNote;
	@ApiModelProperty(value = "事件结束上传附件 blob类型")
	private byte[] processReport;
	@ApiModelProperty(value = "事件结束上传附件名称")
	private String processReportName;
	@ApiModelProperty(value = "事件结束操作人姓名")
	private String endUserName;
	@ApiModelProperty(value = "备注")
	private String remark;
	@ApiModelProperty(value = "数据来源")
	private String dataSources;
	@ApiModelProperty(value = "业务动作")
	private String eventname;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Integer getNoOrd() {
		return noOrd;
	}

	public void setNoOrd(Integer noOrd) {
		this.noOrd = noOrd;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getInternalNo() {
		return internalNo;
	}

	public void setInternalNo(String internalNo) {
		this.internalNo = internalNo;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getTacheName() {
		return tacheName;
	}

	public void setTacheName(String tacheName) {
		this.tacheName = tacheName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getUserStaffCode() {
		return userStaffCode;
	}

	public void setUserStaffCode(String userStaffCode) {
		this.userStaffCode = userStaffCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPromise() {
		return promise;
	}

	public void setPromise(Integer promise) {
		this.promise = promise;
	}

	public String getPromiseType() {
		return promiseType;
	}

	public void setPromiseType(String promiseType) {
		this.promiseType = promiseType;
	}

	public String getPromiseStartSign() {
		return promiseStartSign;
	}

	public void setPromiseStartSign(String promiseStartSign) {
		this.promiseStartSign = promiseStartSign;
	}

	public Integer getIsrisk() {
		return isrisk;
	}

	public void setIsrisk(Integer isrisk) {
		this.isrisk = isrisk;
	}

	public String getRisktype() {
		return risktype;
	}

	public void setRisktype(String risktype) {
		this.risktype = risktype;
	}

	public String getRiskdescription() {
		return riskdescription;
	}

	public void setRiskdescription(String riskdescription) {
		this.riskdescription = riskdescription;
	}

	public String getRiskresult() {
		return riskresult;
	}

	public void setRiskresult(String riskresult) {
		this.riskresult = riskresult;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getSyncDate() {
		return syncDate;
	}

	public void setSyncDate(Date syncDate) {
		this.syncDate = syncDate;
	}

	public String getSyncSign() {
		return syncSign;
	}

	public void setSyncSign(String syncSign) {
		this.syncSign = syncSign;
	}

	public String getSyncErrorDesc() {
		return syncErrorDesc;
	}

	public void setSyncErrorDesc(String syncErrorDesc) {
		this.syncErrorDesc = syncErrorDesc;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getEvent_name() {
		return event_name;
	}

	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Integer getEventTime() {
		return eventTime;
	}

	public void setEventTime(Integer eventTime) {
		this.eventTime = eventTime;
	}

	public String getEventTimeType() {
		return eventTimeType;
	}

	public void setEventTimeType(String eventTimeType) {
		this.eventTimeType = eventTimeType;
	}

	public String getStartNote() {
		return startNote;
	}

	public void setStartNote(String startNote) {
		this.startNote = startNote;
	}

	public String getStartUserName() {
		return startUserName;
	}

	public void setStartUserName(String startUserName) {
		this.startUserName = startUserName;
	}

	public String getStartTel() {
		return startTel;
	}

	public void setStartTel(String startTel) {
		this.startTel = startTel;
	}

	public String getStartPhone() {
		return startPhone;
	}

	public void setStartPhone(String startPhone) {
		this.startPhone = startPhone;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getEndNote() {
		return endNote;
	}

	public void setEndNote(String endNote) {
		this.endNote = endNote;
	}

	public byte[] getProcessReport() {
		return processReport;
	}

	public void setProcessReport(byte[] processReport) {
		this.processReport = processReport;
	}

	public String getProcessReportName() {
		return processReportName;
	}

	public void setProcessReportName(String processReportName) {
		this.processReportName = processReportName;
	}

	public String getEndUserName() {
		return endUserName;
	}

	public void setEndUserName(String endUserName) {
		this.endUserName = endUserName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDataSources() {
		return dataSources;
	}

	public void setDataSources(String dataSources) {
		this.dataSources = dataSources;
	}

	public String getEventname() {
		return eventname;
	}

	public void setEventname(String eventname) {
		this.eventname = eventname;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("InfApplyProcessDO{");
		sb.append("no='").append(no).append('\'');
		sb.append(", noOrd=").append(noOrd);
		sb.append(", orgId='").append(orgId).append('\'');
		sb.append(", internalNo='").append(internalNo).append('\'');
		sb.append(", itemId='").append(itemId).append('\'');
		sb.append(", tacheName='").append(tacheName).append('\'');
		sb.append(", department='").append(department).append('\'');
		sb.append(", userStaffCode='").append(userStaffCode).append('\'');
		sb.append(", userName='").append(userName).append('\'');
		sb.append(", status='").append(status).append('\'');
		sb.append(", promise=").append(promise);
		sb.append(", promiseType='").append(promiseType).append('\'');
		sb.append(", promiseStartSign='").append(promiseStartSign).append('\'');
		sb.append(", isrisk=").append(isrisk);
		sb.append(", risktype='").append(risktype).append('\'');
		sb.append(", riskdescription='").append(riskdescription).append('\'');
		sb.append(", riskresult='").append(riskresult).append('\'');
		sb.append(", note='").append(note).append('\'');
		sb.append(", attachment='").append(attachment).append('\'');
		sb.append(", processDate=").append(processDate);
		sb.append(", createDate=").append(createDate);
		sb.append(", updateDate=").append(updateDate);
		sb.append(", syncDate=").append(syncDate);
		sb.append(", syncSign='").append(syncSign).append('\'');
		sb.append(", syncErrorDesc='").append(syncErrorDesc).append('\'');
		sb.append(", eventCode='").append(eventCode).append('\'');
		sb.append(", event_name='").append(event_name).append('\'');
		sb.append(", startTime=").append(startTime);
		sb.append(", eventTime=").append(eventTime);
		sb.append(", eventTimeType='").append(eventTimeType).append('\'');
		sb.append(", startNote='").append(startNote).append('\'');
		sb.append(", startUserName='").append(startUserName).append('\'');
		sb.append(", startTel='").append(startTel).append('\'');
		sb.append(", startPhone='").append(startPhone).append('\'');
		sb.append(", endTime=").append(endTime);
		sb.append(", endNote='").append(endNote).append('\'');
		sb.append(", processReport=").append(Arrays.toString(processReport));
		sb.append(", processReportName='").append(processReportName).append('\'');
		sb.append(", endUserName='").append(endUserName).append('\'');
		sb.append(", remark='").append(remark).append('\'');
		sb.append(", dataSources='").append(dataSources).append('\'');
		sb.append(", eventname='").append(eventname).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
