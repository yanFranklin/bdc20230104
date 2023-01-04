package cn.gtmap.interchange.core.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zq
 * Date: 15-12-29
 * Time: 下午11:15
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "inf_apply_process")
public class InfApplyProcess {
	@Id
	private String no;//流水号
	private Integer no_ord;//同一办件多个岗位（环节）的编号
	private String org_id;//部门编码
	private String internal_no;//部门内部办件编号
	private String item_id;//权力编码
	private String tache_name;//岗位（环节）名称
	private String department;//办理处室
	private String user_staff_code;//办理人员工号
	private String user_name;//办理人员姓名
	private String status;//岗位（环节）状态
	private Integer promise;//承诺时限
	private String promise_type;//承诺时限单位
	private String promise_start_sign;//是否承诺时限起始标志
	private Integer isrisk;//是否风险点
	private String risktype;//风险点类别
	private String riskdescription;//风险点描述
	private String riskresult;//风险内控的手段与结果
	private String note;//处理意见
	private String attachment;//办理附件
	private Date process_date;//处理时间
	private Date create_date;//录入自建系统时间
	private Date update_date;//写入前置机时间
	private Date sync_date;//信息同步读取时间
	private String sync_sign;//信息同步标志
	private String sync_error_desc;//信息同步错误原因

	private String event_code;//事件代码
	private String event_name;//事件名称
	private Date start_time;//过程申请日期
	private Integer event_time;//过程申请时限
	private String event_time_type;//过程时限单位
	private String start_note;//过程申请说明
	private String start_user_name;//过程申请批准人姓名
	private String start_tel;//过程申请批准人电话
	private String start_phone;//过程批准人手机
	private Date end_time;//事件实际结束时间
	private String end_note;//事件结束说明
	private byte[] process_report;//事件结束上传附件 blob类型
	private String process_report_name;//事件结束上传附件名称
	private String end_user_name;//事件结束操作人姓名
	private String remark;//备注
	private String data_sources;//数据来源
	private String eventname;

	public String getEventname() {
		return eventname;
	}

	public void setEventname(String eventname) {
		this.eventname = eventname;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Integer getNo_ord() {
		return no_ord;
	}

	public void setNo_ord(Integer no_ord) {
		this.no_ord = no_ord;
	}

	public String getOrg_id() {
		return org_id;
	}

	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}

	public String getInternal_no() {
		return internal_no;
	}

	public void setInternal_no(String internal_no) {
		this.internal_no = internal_no;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getTache_name() {
		return tache_name;
	}

	public void setTache_name(String tache_name) {
		this.tache_name = tache_name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getUser_staff_code() {
		return user_staff_code;
	}

	public void setUser_staff_code(String user_staff_code) {
		this.user_staff_code = user_staff_code;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
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

	public String getPromise_type() {
		return promise_type;
	}

	public void setPromise_type(String promise_type) {
		this.promise_type = promise_type;
	}

	public String getPromise_start_sign() {
		return promise_start_sign;
	}

	public void setPromise_start_sign(String promise_start_sign) {
		this.promise_start_sign = promise_start_sign;
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

	public Date getProcess_date() {
		return process_date;
	}

	public void setProcess_date(Date process_date) {
		this.process_date = process_date;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public Date getSync_date() {
		return sync_date;
	}

	public void setSync_date(Date sync_date) {
		this.sync_date = sync_date;
	}

	public String getSync_sign() {
		return sync_sign;
	}

	public void setSync_sign(String sync_sign) {
		this.sync_sign = sync_sign;
	}

	public String getSync_error_desc() {
		return sync_error_desc;
	}

	public void setSync_error_desc(String sync_error_desc) {
		this.sync_error_desc = sync_error_desc;
	}

	public String getEvent_code() {
		return event_code;
	}

	public void setEvent_code(String event_code) {
		this.event_code = event_code;
	}

	public String getEvent_name() {
		return event_name;
	}

	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Integer getEvent_time() {
		return event_time;
	}

	public void setEvent_time(Integer event_time) {
		this.event_time = event_time;
	}

	public String getEvent_time_type() {
		return event_time_type;
	}

	public void setEvent_time_type(String event_time_type) {
		this.event_time_type = event_time_type;
	}

	public String getStart_note() {
		return start_note;
	}

	public void setStart_note(String start_note) {
		this.start_note = start_note;
	}

	public String getStart_user_name() {
		return start_user_name;
	}

	public void setStart_user_name(String start_user_name) {
		this.start_user_name = start_user_name;
	}

	public String getStart_tel() {
		return start_tel;
	}

	public void setStart_tel(String start_tel) {
		this.start_tel = start_tel;
	}

	public String getStart_phone() {
		return start_phone;
	}

	public void setStart_phone(String start_phone) {
		this.start_phone = start_phone;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public String getEnd_note() {
		return end_note;
	}

	public void setEnd_note(String end_note) {
		this.end_note = end_note;
	}

	public byte[] getProcess_report() {
		return process_report;
	}

	public void setProcess_report(byte[] process_report) {
		this.process_report = process_report;
	}

	public String getProcess_report_name() {
		return process_report_name;
	}

	public void setProcess_report_name(String process_report_name) {
		this.process_report_name = process_report_name;
	}

	public String getEnd_user_name() {
		return end_user_name;
	}

	public void setEnd_user_name(String end_user_name) {
		this.end_user_name = end_user_name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getData_sources() {
		return data_sources;
	}

	public void setData_sources(String data_sources) {
		this.data_sources = data_sources;
	}

}
