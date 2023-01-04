package cn.gtmap.realestate.common.core.domain.exchange.yzw;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2017/11/6
 * @description 申请审核信息表
 */
@Table(name = "inf_apply_verify")
public class InfApplyVerifyDO {
    @Id
    @ApiModelProperty(value = "主键")
    private String no;
    @ApiModelProperty(value = "部门内部办件编号")
    private String internalNo;
    @ApiModelProperty(value = "审核原因")
    private String verifyReason;
    @ApiModelProperty(value = "审核时间")
    private Date verifyTime;
    @ApiModelProperty(value = "办理用户名")
    private String userName;
    @ApiModelProperty(value = "办理用户id")
    private String userId;
    @ApiModelProperty(value = "数据来源")
    private String dataSources;
    @ApiModelProperty(value = "信息同步时间")
    private Date syncDate;
    @ApiModelProperty(value = "信息同步标志")
    private String syncSign;
    @ApiModelProperty(value = "信息同步错误原因")
    private String syncErrorDesc;

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

    @Override
    public String toString() {
        return "InfApplyVerifyDO{" +
                "no='" + no + '\'' +
                ", internalNo='" + internalNo + '\'' +
                ", verifyReason='" + verifyReason + '\'' +
                ", verifyTime=" + verifyTime +
                ", userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                ", dataSources='" + dataSources + '\'' +
                ", syncDate=" + syncDate +
                ", syncSign='" + syncSign + '\'' +
                ", syncErrorDesc='" + syncErrorDesc + '\'' +
                '}';
    }
}
