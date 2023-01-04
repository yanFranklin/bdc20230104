package cn.gtmap.realestate.etl.core.domian.nantong;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2017/10/24
 * @description 南通办件基本信息
 */
@Table(name = "bn_inf_apply")
@XmlRootElement(name = "ApplyInfo")
public class BnInfApply {
    @Id
    private String no;//唯一标识
    private String areaNo;//行政区划编码
    private String areaName;//行政区划名称
    private String orgId;//部门编码
    private String orgName;//部门名称
    private String internalNo;//办件编号
    private String deptQlRegNo;//32位事项编码
    private String deptQlName;//申请事项名称
    private String deptYwRegNo;//32位或者34业务编码
    private String deptYwName;//申请业务名称
    private String department;//经办处室
    private String transactAffairName;//办件名称
    private String content;//办件摘要
    private String ifUrgent;//是否加急
    private String sqWay;//申请方式
    private String applicantType;//申请人类型
    private String applicantName;//申请者名称
    private String applicantPaperType;//证件类型
    private String applicantPaperCode;//证件号
    private String applicantMobile;//手机
    private String applicantPhone;//联系电话
    private String applicantAddress;//联系地址
    private String applicantZipcode;//邮政编码
    private String applicantEmall;//电子邮箱
    private String applicantCode;//组织机构代码
    private String operManName;//法定代表人姓名
    private String linkmanName;//申请方经办人姓名
    private String linkmanPaperType;//申请方经办人证件类型
    private String linkmanPaperCode;//申请方经办人证件号码
    private String linkmanMobile;//申请方经办人手机
    private String linkmanPhone;//申请方经办人联系电话
    private String linkmanAddress;//申请方经办人联系地址
    private String linkmanZipcode;//申请方经办人邮政编码
    private String linkmanEmail;//申请方经办人邮箱地址
    private String yeMs;//办件业务说明
    private String sjFileRemark;//办件附件说明
    private Integer anticipate;//法定办结时间
    private String anticipateDayType;//法定办结时间计量单位
    private Integer promise;//承诺办结时间
    private String promiseType;//承诺办结时间计量单位
    private Date wapplyDate;//网上申请时间
    private Date applyDate;//收件时间
    private String bjStatu;//办件状态
    private String dataSources;//数据来源
    private Date syncDate;//信息同步时间


    //lst 26640 新增字段
    private String applyType;//办件类型
    private String applyDocno;//受理文书编号
    private String qlKind;//事项类型
    private String state;//事项类型
    private String wwsqWay;//外网申请方式

    /**
     * 38386 【南通】一张网推送逻辑修改需求 删除以下字段
     */
  /*  private String syncSign;//信息同步标志
    private String syncErrorDesc;//信息同步错误原因
    private String rojectExamNo;//投资项目编号
    private String catalogCode;//目录编码
    private String taskVersion;//事项版本号
    private Date jhptUpdateTime;//数据推送时间
*/
    /**
     * 38386 【南通】一张网推送逻辑修改需求 新增以下字段
     */
    private String updateSign;
    private Date updateDate;
    private String updateErrorDesc;
    private String catalogCode;//目录编码
    public String getUpdateSign() {
        return updateSign;
    }

    public void setUpdateSign(String updateSign) {
        this.updateSign = updateSign;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateErrorDesc() {
        return updateErrorDesc;
    }

    public void setUpdateErrorDesc(String updateErrorDesc) {
        this.updateErrorDesc = updateErrorDesc;
    }

    @XmlTransient
    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    @XmlTransient
    public String getAreaNo() {
        return areaNo;
    }

    public void setAreaNo(String areaNo) {
        this.areaNo = areaNo;
    }

    @XmlTransient
    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @XmlTransient
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @XmlTransient
    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @XmlElement(name = "INTERNAL_NO")
    public String getInternalNo() {
        return internalNo;
    }

    public void setInternalNo(String internalNo) {
        this.internalNo = internalNo;
    }

    @XmlTransient
    public String getDeptQlRegNo() {
        return deptQlRegNo;
    }

    public void setDeptQlRegNo(String deptQlRegNo) {
        this.deptQlRegNo = deptQlRegNo;
    }

    @XmlTransient
    public String getDeptQlName() {
        return deptQlName;
    }

    public void setDeptQlName(String deptQlName) {
        this.deptQlName = deptQlName;
    }

    @XmlTransient
    public String getDeptYwRegNo() {
        return deptYwRegNo;
    }

    public void setDeptYwRegNo(String deptYwRegNo) {
        this.deptYwRegNo = deptYwRegNo;
    }

    @XmlTransient
    public String getDeptYwName() {
        return deptYwName;
    }

    public void setDeptYwName(String deptYwName) {
        this.deptYwName = deptYwName;
    }

    @XmlTransient
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @XmlTransient
    public String getTransactAffairName() {
        return transactAffairName;
    }

    public void setTransactAffairName(String transactAffairName) {
        this.transactAffairName = transactAffairName;
    }

    @XmlTransient
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @XmlTransient
    public String getIfUrgent() {
        return ifUrgent;
    }

    public void setIfUrgent(String ifUrgent) {
        this.ifUrgent = ifUrgent;
    }

    @XmlTransient
    public String getSqWay() {
        return sqWay;
    }

    public void setSqWay(String sqWay) {
        this.sqWay = sqWay;
    }

    @XmlTransient
    public String getApplicantType() {
        return applicantType;
    }

    public void setApplicantType(String applicantType) {
        this.applicantType = applicantType;
    }

    @XmlTransient
    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    @XmlTransient
    public String getApplicantPaperType() {
        return applicantPaperType;
    }

    public void setApplicantPaperType(String applicantPaperType) {
        this.applicantPaperType = applicantPaperType;
    }

    @XmlTransient
    public String getApplicantPaperCode() {
        return applicantPaperCode;
    }

    public void setApplicantPaperCode(String applicantPaperCode) {
        this.applicantPaperCode = applicantPaperCode;
    }

    @XmlTransient
    public String getApplicantMobile() {
        return applicantMobile;
    }

    public void setApplicantMobile(String applicantMobile) {
        this.applicantMobile = applicantMobile;
    }

    @XmlTransient
    public String getApplicantPhone() {
        return applicantPhone;
    }

    public void setApplicantPhone(String applicantPhone) {
        this.applicantPhone = applicantPhone;
    }

    @XmlTransient
    public String getApplicantAddress() {
        return applicantAddress;
    }

    public void setApplicantAddress(String applicantAddress) {
        this.applicantAddress = applicantAddress;
    }

    @XmlTransient
    public String getApplicantZipcode() {
        return applicantZipcode;
    }

    public void setApplicantZipcode(String applicantZipcode) {
        this.applicantZipcode = applicantZipcode;
    }

    @XmlTransient
    public String getApplicantEmall() {
        return applicantEmall;
    }

    public void setApplicantEmall(String applicantEmall) {
        this.applicantEmall = applicantEmall;
    }

    @XmlTransient
    public String getApplicantCode() {
        return applicantCode;
    }

    public void setApplicantCode(String applicantCode) {
        this.applicantCode = applicantCode;
    }

    @XmlTransient
    public String getOperManName() {
        return operManName;
    }

    public void setOperManName(String operManName) {
        this.operManName = operManName;
    }

    @XmlTransient
    public String getLinkmanName() {
        return linkmanName;
    }

    public void setLinkmanName(String linkmanName) {
        this.linkmanName = linkmanName;
    }

    @XmlTransient
    public String getLinkmanPaperType() {
        return linkmanPaperType;
    }

    public void setLinkmanPaperType(String linkmanPaperType) {
        this.linkmanPaperType = linkmanPaperType;
    }

    @XmlTransient
    public String getLinkmanPaperCode() {
        return linkmanPaperCode;
    }

    public void setLinkmanPaperCode(String linkmanPaperCode) {
        this.linkmanPaperCode = linkmanPaperCode;
    }

    @XmlTransient
    public String getLinkmanMobile() {
        return linkmanMobile;
    }

    public void setLinkmanMobile(String linkmanMobile) {
        this.linkmanMobile = linkmanMobile;
    }

    @XmlTransient
    public String getLinkmanPhone() {
        return linkmanPhone;
    }

    public void setLinkmanPhone(String linkmanPhone) {
        this.linkmanPhone = linkmanPhone;
    }

    @XmlTransient
    public String getLinkmanAddress() {
        return linkmanAddress;
    }

    public void setLinkmanAddress(String linkmanAddress) {
        this.linkmanAddress = linkmanAddress;
    }

    @XmlTransient
    public String getLinkmanZipcode() {
        return linkmanZipcode;
    }

    public void setLinkmanZipcode(String linkmanZipcode) {
        this.linkmanZipcode = linkmanZipcode;
    }

    @XmlTransient
    public String getLinkmanEmail() {
        return linkmanEmail;
    }

    public void setLinkmanEmail(String linkmanEmail) {
        this.linkmanEmail = linkmanEmail;
    }

    @XmlTransient
    public String getYeMs() {
        return yeMs;
    }

    public void setYeMs(String yeMs) {
        this.yeMs = yeMs;
    }

    @XmlTransient
    public String getSjFileRemark() {
        return sjFileRemark;
    }

    public void setSjFileRemark(String sjFileRemark) {
        this.sjFileRemark = sjFileRemark;
    }

    @XmlTransient
    public Integer getAnticipate() {
        return anticipate;
    }

    public void setAnticipate(Integer anticipate) {
        this.anticipate = anticipate;
    }

    @XmlTransient
    public String getAnticipateDayType() {
        return anticipateDayType;
    }

    public void setAnticipateDayType(String anticipateDayType) {
        this.anticipateDayType = anticipateDayType;
    }

    @XmlTransient
    public Integer getPromise() {
        return promise;
    }

    public void setPromise(Integer promise) {
        this.promise = promise;
    }

    @XmlTransient
    public String getPromiseType() {
        return promiseType;
    }

    public void setPromiseType(String promiseType) {
        this.promiseType = promiseType;
    }

    @XmlTransient
    public Date getWapplyDate() {
        return wapplyDate;
    }

    public void setWapplyDate(Date wapplyDate) {
        this.wapplyDate = wapplyDate;
    }

    @XmlTransient
    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    @XmlTransient
    public String getBjStatu() {
        return bjStatu;
    }

    public void setBjStatu(String bjStatu) {
        this.bjStatu = bjStatu;
    }

    @XmlTransient
    public String getDataSources() {
        return dataSources;
    }

    public void setDataSources(String dataSources) {
        this.dataSources = dataSources;
    }

    @XmlTransient
    public Date getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(Date syncDate) {
        this.syncDate = syncDate;
    }


    @XmlTransient
    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    @XmlTransient
    public String getApplyDocno() {
        return applyDocno;
    }

    public void setApplyDocno(String applyDocno) {
        this.applyDocno = applyDocno;
    }

    @XmlTransient
    public String getQlKind() {
        return qlKind;
    }

    public void setQlKind(String qlKind) {
        this.qlKind = qlKind;
    }

    @XmlTransient
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @XmlTransient
    public String getWwsqWay() {
        return wwsqWay;
    }

    public void setWwsqWay(String wwsqWay) {
        this.wwsqWay = wwsqWay;
    }

    public String getCatalogCode() {
        return catalogCode;
    }

    public void setCatalogCode(String catalogCode) {
        this.catalogCode = catalogCode;
    }
}
