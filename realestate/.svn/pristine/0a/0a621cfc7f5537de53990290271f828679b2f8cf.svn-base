package cn.gtmap.interchange.core.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2017/11/6
 * @description 申请审核信息表
 */
@Table(name = "inf_apply_verify")
public class InfApplyVerify {
    @Id
    private String no;//主键
    private String internal_no;//部门内部办件编号
    private String verifyReason;//审核原因
    private Date verifyTime;//审核时间
    private String userName;//办理用户名
    private String userId;//办理用户id
    private String dataSources;//数据来源
    private Date syncDate;//信息同步时间
    private String syncSign;//信息同步标志
    private String syncErrorDesc;//信息同步错误原因

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getInternal_no() {
        return internal_no;
    }

    public void setInternal_no(String internal_no) {
        this.internal_no = internal_no;
    }

    public String getVerifyReason() {
        return verifyReason;
    }

    public void setVerifyReason(String verifyReason) {
        this.verifyReason = verifyReason;
    }

    public Date getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(Date verifyTime) {
        this.verifyTime = verifyTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}
