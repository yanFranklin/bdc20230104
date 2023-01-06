package cn.gtmap.realestate.etl.core.domian.huaian;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2017/10/24
 * @description 南通办理结果信息表
 */
@Table(name = "bn_inf_apply_result")
public class BnInfApplyResult {
    @Id
    private String no;//唯一标识
    private String internalNo;//唯一办件编号
    private String status;//办理结果
    private String resultNo;//许可/不予许可文号
    private String note;//办理意见
    private String resultFileName;//决定附件名称
    private byte[] resultFile;//决定附件
    private Date createDate;//决定时间
    private String dataSources;//数据来源
    private Date syncDate;//信息同步时间
    //新增字段
    private String resultcetrname;//结果证照名称
    private String resultcetrno;//结果证照编号
    private String isdeliveryresults;//是否快递递送结果
    private String satisfaction;//满意度

    /**
     * 38386 【南通】一张网推送逻辑修改需求 删除以下字段
     */
   /* private String syncSign;//信息同步标志
    private String syncErrorDesc;//信息同步错误原因
    private Date jhptUpdateTime;//数据推送时间*/
    private Date jhptUpdateTime;

    public Date getJhptUpdateTime() {
        return jhptUpdateTime;
    }

    public void setJhptUpdateTime(Date jhptUpdateTime) {
        this.jhptUpdateTime = jhptUpdateTime;
    }

    /**
     * 38386 【南通】一张网推送逻辑修改需求 新增以下字段
     */
//    private String updateSign;
//    private Date updateDate;
//    private String updateErrorDesc;


//    public String getUpdateSign() {
//        return updateSign;
//    }
//
//    public void setUpdateSign(String updateSign) {
//        this.updateSign = updateSign;
//    }
//
//    public Date getUpdateDate() {
//        return updateDate;
//    }
//
//    public void setUpdateDate(Date updateDate) {
//        this.updateDate = updateDate;
//    }
//
//    public String getUpdateErrorDesc() {
//        return updateErrorDesc;
//    }
//
//    public void setUpdateErrorDesc(String updateErrorDesc) {
//        this.updateErrorDesc = updateErrorDesc;
//    }

    public String getResultcetrname() {
        return resultcetrname;
    }

    public void setResultcetrname(String resultcetrname) {
        this.resultcetrname = resultcetrname;
    }

    public String getResultcetrno() {
        return resultcetrno;
    }

    public void setResultcetrno(String resultcetrno) {
        this.resultcetrno = resultcetrno;
    }

    public String getIsdeliveryresults() {
        return isdeliveryresults;
    }

    public void setIsdeliveryresults(String isdeliveryresults) {
        this.isdeliveryresults = isdeliveryresults;
    }

    public String getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(String satisfaction) {
        this.satisfaction = satisfaction;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResultNo() {
        return resultNo;
    }

    public void setResultNo(String resultNo) {
        this.resultNo = resultNo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getResultFileName() {
        return resultFileName;
    }

    public void setResultFileName(String resultFileName) {
        this.resultFileName = resultFileName;
    }

    public byte[] getResultFile() {
        return resultFile;
    }

    public void setResultFile(byte[] resultFile) {
        this.resultFile = resultFile;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDataSources() {
        return dataSources;
    }

    public void setDataSources(String dataSources) {
        this.dataSources = dataSources;
    }

    public Date getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(Date syncDate) {
        this.syncDate = syncDate;
    }


}
