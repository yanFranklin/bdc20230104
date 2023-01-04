package cn.gtmap.realestate.etl.core.domian.nantong;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2017/10/24
 * @description 南通办理过程信息表
 */
@Table(name = "bn_inf_apply_process")
public class BnInfApplyProcess {
    @Id
    private String no;//唯一标识
    private String internalNo;//唯一办件编号
    private Integer eventNumber;//序号
    private String eventCode;//事件代码
    private String eventName;//事件名称
    private String department;//办理处室
    private Date startTime;//过程申请日期
    private Integer eventTime;//过程申请时限
    private String eventTimeType;//过程时限单位
    private String startNote;//过程申请说明
    private String startUserName;//过程申请批准人姓名
    private String startTel;//过程申请批准人电话
    private String startPhone;//过程批准人手机
    private Date endTime;//事件实际结束时间
    private String endNote;//事件结束说明
    private String processReportName;//事件结束上传附件名称
    private byte[] processReport;//事件结束上传附件
    private String endUserName;//事件结束操作人姓名
    private String remark;//备注
    private String dataSources;//数据来源
    private Date syncDate;//信息同步时间
    //新增字段


    /**
     * 38386 【南通】一张网推送逻辑修改需求
     */
  /*  private String syncSign;//信息同步标志
    private String syncErrorDesc;//信息同步错误原因
    private Date jhptUpdateTime;//数据推送时间
    private String eventname;//业务动作*/

    /**
     * 38386 【南通】一张网推送逻辑修改需求 新增以下字段
     */
    private String updateSign;
    private Date updateDate;
    private String updateErrorDesc;

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

    public Date getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(Date syncDate) {
        this.syncDate = syncDate;
    }


    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getInternalNo() {
        return internalNo;
    }

    public void setInternalNo(String internalNo) {
        this.internalNo = internalNo;
    }

    public Integer getEventNumber() {
        return eventNumber;
    }

    public void setEventNumber(Integer eventNumber) {
        this.eventNumber = eventNumber;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public String getProcessReportName() {
        return processReportName;
    }

    public void setProcessReportName(String processReportName) {
        this.processReportName = processReportName;
    }

    public byte[] getProcessReport() {
        return processReport;
    }

    public void setProcessReport(byte[] processReport) {
        this.processReport = processReport;
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


}
