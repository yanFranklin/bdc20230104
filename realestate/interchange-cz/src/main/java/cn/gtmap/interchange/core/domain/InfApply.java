package cn.gtmap.interchange.core.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.StringJoiner;

/**
 * Created with IntelliJ IDEA.
 * User: zq
 * Date: 15-12-29
 * Time: 下午11:15
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "inf_apply")
public class InfApply implements Serializable {
	@Id
	private String no;//流水号
	private String org_id;//部门编码
	private String internal_no;//部门内部办件编号
	private String item_id;//权力编码
	private String department;//业务处室
	private String transact_affair_name;//办件名称
	private String content;//办件摘要
	private String apply_way;//办件申请方式
	private String form;//办件申请表格
	private String stuff;//办件申请提交材料
	private String applicant_code;//组织机构代码
	private String applicant_type;//申请者（自然人/法人）
	private String applicant_name;//申请者名称
	private String applicant_paper_type;//申请者证件类型
	private String applicant_paper_code;//申请者证件号码
	private String applicant_phone;//申请者电话
	private String applicant_mobile;//申请者手机
	private String applicant_address;//申请者地址
	private String applicant_zipcode;//申请者邮编
	private String applicant_email;//申请者电子邮件
	private String linkman;//法定代表人
	private String linkmanzjhm;//法定代表人证件号码
	private String linkmantel;//法人代表联系电话
	private String linkman_name;//联系人/代理人姓名
	private String linkman_paper_type;//联系人/代理人证件类型
	private String linkman_paper_code;//联系人/代理人证件号码
	private String linkman_phone;//联系人/代理人电话
	private String linkman_mobile;//联系人/代理人手机
	private String linkman_address;//联系人/代理人地址
	private String linkman_zipcode;//联系人/代理人邮编
	private String linkman_email;//联系人/代理人电子邮件
	private Integer promise;//承诺时限
	private String promise_type;//承诺时限单位
	private Integer isrisk;//是否风险点
	private String risktype;//风险点类别
	private String riskdescription;//风险点描述
	private String riskresult;//风险内控的手段与结果
	private Date apply_date;//办件申请时间
	private Date create_date;//录入自建系统时间
	private Date update_date;//写入前置机时间
	private Date sync_date;//信息同步读取时间
	private String sync_sign;//信息同步标志
	private String sync_error_desc;//信息同步错误原因

	private String area_no;//行政区划编码
	private String area_name;//行政区划名称
	private String org_name;//部门名称
	private String dept_ql_reg_no;//32 位事项编码
	private String dept_ql_name;//申请事项名称
	private String dept_yw_reg_no;//32 位或者34位业务编码
	private String dept_yw_name;//申请业务名称
	private String if_urgent;//是否加急
	private String ye_ms;//办件业务说明
	private String sj_file_remark;//办件附件说明
	private Integer anticipate;//法定办结时间
	private String anticipate_day_type;//法定办结时间计量单位
	private Date wapply_date;//网上申请时间（在线申办时必填）
	private String bj_statu;//办件状态
	private String data_sources;//数据来源
	private String internal_id;//针对苏州推送返回的业务id
	private String acceptUser;//办件受理人
	private String catalog_code;
	private String task_version;
	private String apply_type;

	// 扬州增加 32981 扬州_一张网字段可配置默认值需求2
	private String wwsq_way;

	public String getApply_type() {
		return apply_type;
	}

	public void setApply_type(String apply_type) {
		this.apply_type = apply_type;
	}

	public String getCatalog_code() {
		return catalog_code;
	}

	public void setCatalog_code(String catalog_code) {
		this.catalog_code = catalog_code;
	}

	public String getTask_version() {
		return task_version;
	}

	public void setTask_version(String task_version) {
		this.task_version = task_version;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getTransact_affair_name() {
		return transact_affair_name;
	}

	public void setTransact_affair_name(String transact_affair_name) {
		this.transact_affair_name = transact_affair_name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getApply_way() {
		return apply_way;
	}

	public void setApply_way(String apply_way) {
		this.apply_way = apply_way;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public String getStuff() {
		return stuff;
	}

	public void setStuff(String stuff) {
		this.stuff = stuff;
	}

	public String getApplicant_code() {
		return applicant_code;
	}

	public void setApplicant_code(String applicant_code) {
		this.applicant_code = applicant_code;
	}

	public String getApplicant_type() {
		return applicant_type;
	}

	public void setApplicant_type(String applicant_type) {
		this.applicant_type = applicant_type;
	}

	public String getApplicant_name() {
		return applicant_name;
	}

	public void setApplicant_name(String applicant_name) {
		this.applicant_name = applicant_name;
	}

	public String getApplicant_paper_type() {
		return applicant_paper_type;
	}

	public void setApplicant_paper_type(String applicant_paper_type) {
		this.applicant_paper_type = applicant_paper_type;
	}

	public String getApplicant_paper_code() {
		return applicant_paper_code;
	}

	public void setApplicant_paper_code(String applicant_paper_code) {
		this.applicant_paper_code = applicant_paper_code;
	}

	public String getApplicant_phone() {
		return applicant_phone;
	}

	public void setApplicant_phone(String applicant_phone) {
		this.applicant_phone = applicant_phone;
	}

	public String getApplicant_mobile() {
		return applicant_mobile;
	}

	public void setApplicant_mobile(String applicant_mobile) {
		this.applicant_mobile = applicant_mobile;
	}

	public String getApplicant_address() {
		return applicant_address;
	}

	public void setApplicant_address(String applicant_address) {
		this.applicant_address = applicant_address;
	}

	public String getApplicant_zipcode() {
		return applicant_zipcode;
	}

	public void setApplicant_zipcode(String applicant_zipcode) {
		this.applicant_zipcode = applicant_zipcode;
	}

	public String getApplicant_email() {
		return applicant_email;
	}

	public void setApplicant_email(String applicant_email) {
		this.applicant_email = applicant_email;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getLinkman_name() {
		return linkman_name;
	}

	public void setLinkman_name(String linkman_name) {
		this.linkman_name = linkman_name;
	}

	public String getLinkman_paper_type() {
		return linkman_paper_type;
	}

	public void setLinkman_paper_type(String linkman_paper_type) {
		this.linkman_paper_type = linkman_paper_type;
	}

	public String getLinkman_paper_code() {
		return linkman_paper_code;
	}

	public void setLinkman_paper_code(String linkman_paper_code) {
		this.linkman_paper_code = linkman_paper_code;
	}

	public String getLinkman_phone() {
		return linkman_phone;
	}

	public void setLinkman_phone(String linkman_phone) {
		this.linkman_phone = linkman_phone;
	}

	public String getLinkman_mobile() {
		return linkman_mobile;
	}

	public void setLinkman_mobile(String linkman_mobile) {
		this.linkman_mobile = linkman_mobile;
	}

	public String getLinkman_address() {
		return linkman_address;
	}

	public void setLinkman_address(String linkman_address) {
		this.linkman_address = linkman_address;
	}

	public String getLinkman_zipcode() {
		return linkman_zipcode;
	}

	public void setLinkman_zipcode(String linkman_zipcode) {
		this.linkman_zipcode = linkman_zipcode;
	}

	public String getLinkman_email() {
		return linkman_email;
	}

	public void setLinkman_email(String linkman_email) {
		this.linkman_email = linkman_email;
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

	public Date getApply_date() {
		return apply_date;
	}

	public void setApply_date(Date apply_date) {
		this.apply_date = apply_date;
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

	public String getArea_no() {
		return area_no;
	}

	public void setArea_no(String area_no) {
		this.area_no = area_no;
	}

	public String getArea_name() {
		return area_name;
	}

	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public String getDept_ql_reg_no() {
		return dept_ql_reg_no;
	}

	public void setDept_ql_reg_no(String dept_ql_reg_no) {
		this.dept_ql_reg_no = dept_ql_reg_no;
	}

	public String getDept_ql_name() {
		return dept_ql_name;
	}

	public void setDept_ql_name(String dept_ql_name) {
		this.dept_ql_name = dept_ql_name;
	}

	public String getDept_yw_reg_no() {
		return dept_yw_reg_no;
	}

	public void setDept_yw_reg_no(String dept_yw_reg_no) {
		this.dept_yw_reg_no = dept_yw_reg_no;
	}

	public String getDept_yw_name() {
		return dept_yw_name;
	}

	public void setDept_yw_name(String dept_yw_name) {
		this.dept_yw_name = dept_yw_name;
	}

	public String getIf_urgent() {
		return if_urgent;
	}

	public void setIf_urgent(String if_urgent) {
		this.if_urgent = if_urgent;
	}

	public String getYe_ms() {
		return ye_ms;
	}

	public void setYe_ms(String ye_ms) {
		this.ye_ms = ye_ms;
	}

	public String getSj_file_remark() {
		return sj_file_remark;
	}

	public void setSj_file_remark(String sj_file_remark) {
		this.sj_file_remark = sj_file_remark;
	}

	public Integer getAnticipate() {
		return anticipate;
	}

	public void setAnticipate(Integer anticipate) {
		this.anticipate = anticipate;
	}

	public String getAnticipate_day_type() {
		return anticipate_day_type;
	}

	public void setAnticipate_day_type(String anticipate_day_type) {
		this.anticipate_day_type = anticipate_day_type;
	}

	public Date getWapply_date() {
		return wapply_date;
	}

	public void setWapply_date(Date wapply_date) {
		this.wapply_date = wapply_date;
	}

	public String getBj_statu() {
		return bj_statu;
	}

	public void setBj_statu(String bj_statu) {
		this.bj_statu = bj_statu;
	}

	public String getData_sources() {
		return data_sources;
	}

	public void setData_sources(String data_sources) {
		this.data_sources = data_sources;
	}

	public String getLinkmanzjhm() {
		return linkmanzjhm;
	}

	public void setLinkmanzjhm(String linkmanzjhm) {
		this.linkmanzjhm = linkmanzjhm;
	}

	public String getLinkmantel() {
		return linkmantel;
	}

	public void setLinkmantel(String linkmantel) {
		this.linkmantel = linkmantel;
	}

	public String getInternal_id() {
		return internal_id;
	}

	public void setInternal_id(String internal_id) {
		this.internal_id = internal_id;
	}

	public String getAcceptUser() {
		return acceptUser;
	}

	public void setAcceptUser(String acceptUser) {
		this.acceptUser = acceptUser;
	}

	public String getWwsq_way() {
		return wwsq_way;
	}

	public void setWwsq_way(String wwsq_way) {
		this.wwsq_way = wwsq_way;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", InfApply.class.getSimpleName() + "[", "]")
				.add("no='" + no + "'")
				.add("org_id='" + org_id + "'")
				.add("internal_no='" + internal_no + "'")
				.add("item_id='" + item_id + "'")
				.add("department='" + department + "'")
				.add("transact_affair_name='" + transact_affair_name + "'")
				.add("content='" + content + "'")
				.add("apply_way='" + apply_way + "'")
				.add("form='" + form + "'")
				.add("stuff='" + stuff + "'")
				.add("applicant_code='" + applicant_code + "'")
				.add("applicant_type='" + applicant_type + "'")
				.add("applicant_name='" + applicant_name + "'")
				.add("applicant_paper_type='" + applicant_paper_type + "'")
				.add("applicant_paper_code='" + applicant_paper_code + "'")
				.add("applicant_phone='" + applicant_phone + "'")
				.add("applicant_mobile='" + applicant_mobile + "'")
				.add("applicant_address='" + applicant_address + "'")
				.add("applicant_zipcode='" + applicant_zipcode + "'")
				.add("applicant_email='" + applicant_email + "'")
				.add("linkman='" + linkman + "'")
				.add("linkmanzjhm='" + linkmanzjhm + "'")
				.add("linkmantel='" + linkmantel + "'")
				.add("linkman_name='" + linkman_name + "'")
				.add("linkman_paper_type='" + linkman_paper_type + "'")
				.add("linkman_paper_code='" + linkman_paper_code + "'")
				.add("linkman_phone='" + linkman_phone + "'")
				.add("linkman_mobile='" + linkman_mobile + "'")
				.add("linkman_address='" + linkman_address + "'")
				.add("linkman_zipcode='" + linkman_zipcode + "'")
				.add("linkman_email='" + linkman_email + "'")
				.add("promise=" + promise)
				.add("promise_type='" + promise_type + "'")
				.add("isrisk=" + isrisk)
				.add("risktype='" + risktype + "'")
				.add("riskdescription='" + riskdescription + "'")
				.add("riskresult='" + riskresult + "'")
				.add("apply_date=" + apply_date)
				.add("create_date=" + create_date)
				.add("update_date=" + update_date)
				.add("sync_date=" + sync_date)
				.add("sync_sign='" + sync_sign + "'")
				.add("sync_error_desc='" + sync_error_desc + "'")
				.add("area_no='" + area_no + "'")
				.add("area_name='" + area_name + "'")
				.add("org_name='" + org_name + "'")
				.add("dept_ql_reg_no='" + dept_ql_reg_no + "'")
				.add("dept_ql_name='" + dept_ql_name + "'")
				.add("dept_yw_reg_no='" + dept_yw_reg_no + "'")
				.add("dept_yw_name='" + dept_yw_name + "'")
				.add("if_urgent='" + if_urgent + "'")
				.add("ye_ms='" + ye_ms + "'")
				.add("sj_file_remark='" + sj_file_remark + "'")
				.add("anticipate=" + anticipate)
				.add("anticipate_day_type='" + anticipate_day_type + "'")
				.add("wapply_date=" + wapply_date)
				.add("bj_statu='" + bj_statu + "'")
				.add("data_sources='" + data_sources + "'")
				.add("internal_id='" + internal_id + "'")
				.add("acceptUser='" + acceptUser + "'")
				.add("catalog_code='" + catalog_code + "'")
				.add("task_version='" + task_version + "'")
				.add("apply_type='" + apply_type + "'")
				.add("wwsq_way='" + wwsq_way + "'")
				.toString();
	}
}
