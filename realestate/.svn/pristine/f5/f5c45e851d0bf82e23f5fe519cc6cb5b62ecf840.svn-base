package cn.gtmap.interchange.core.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/*
 * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
 * @version 1.0, 2018/12/07
 * @description 金坛办理结果信息表
 */
@Table(name = "bn_inf_apply_result")

public class BnInfApplyResult implements Serializable {
    @Id
    private String no;

    private String internalNo;

    private String status;

    private String resultNo;

    private String note;

    private String resultFileName;

    private Date createDate;

    private String dataSources;

    private Date syncDate;

    private String syncSign;

    private String syncErrorDesc;

    private Date upDate;

    private byte[] resultFile;

    private static final long serialVersionUID = 1L;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no == null ? null : no.trim();
    }

    public String getInternalNo() {
        return internalNo;
    }

    public void setInternalNo(String internalNo) {
        this.internalNo = internalNo == null ? null : internalNo.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getResultNo() {
        return resultNo;
    }

    public void setResultNo(String resultNo) {
        this.resultNo = resultNo == null ? null : resultNo.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getResultFileName() {
        return resultFileName;
    }

    public void setResultFileName(String resultFileName) {
        this.resultFileName = resultFileName == null ? null : resultFileName.trim();
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
        this.dataSources = dataSources == null ? null : dataSources.trim();
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
        this.syncSign = syncSign == null ? null : syncSign.trim();
    }

    public String getSyncErrorDesc() {
        return syncErrorDesc;
    }

    public void setSyncErrorDesc(String syncErrorDesc) {
        this.syncErrorDesc = syncErrorDesc == null ? null : syncErrorDesc.trim();
    }

    public Date getUpDate() {
        return upDate;
    }

    public void setUpDate(Date upDate) {
        this.upDate = upDate;
    }

    public byte[] getResultFile() {
        return resultFile;
    }

    public void setResultFile(byte[] resultFile) {
        this.resultFile = resultFile;
    }
}