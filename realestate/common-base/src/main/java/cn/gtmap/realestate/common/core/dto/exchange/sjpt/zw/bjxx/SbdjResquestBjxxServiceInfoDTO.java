package cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.bjxx;

import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/7/25
 * @description 5.14    办结信息定义实体
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SbdjResquestBjxxServiceInfoDTO {

    @XmlElement(name = "UNID")
    @ApiModelProperty(value = "由业务系统自动产生(32位UUID，唯一标识，防重，排查使用，无业务含义)", required = true)
    private String unId;

    @XmlElement(name = "PROJID")
    @ApiModelProperty(value = "可为空，首次调用时为空；部分系统存在驳回重新申报的场景，重新申报时，需要传递办件编号", required = true)
    private String projId;

    @XmlElement(name = "TRANSACT_USER_UNID")
    @ApiModelProperty(value = "办理人标识", required = false)
    private String transactUserUnid;

    @XmlElement(name = "TRANSACT_USERNAME")
    @ApiModelProperty(value = "办理人员", required = true)
    private String transactUsername;

    @XmlElement(name = "TRANSACT_DEPTNAME")
    @ApiModelProperty(value = "办理人员所属部门名称", required = true)
    private String transactDeptname;

    @XmlElement(name = "TRANSACT_DEPTID")
    @ApiModelProperty(value = "办理人员所属部门编码", required = true)
    private String transactDeptid;

    @XmlElement(name = "TRANSACT_AREACODE")
    @ApiModelProperty(value = "办理人员所属部门的所在行政区划编码", required = true)
    private String transactAreacode;


    @XmlElement(name = "TRANSACT_TIME")
    @ApiModelProperty(value = "办结时间", required = true)
    private String transactTime;

    @XmlElement(name = "TRANSACT_RESULT")
    @ApiModelProperty(value = "办理结果", required = true)
    private String transactResult;

    @XmlElement(name = "TRANSACT_RESULT_CODE")
    @ApiModelProperty(value = "办理结果编码", required = true)
    private String transactResultCode;

    @XmlElement(name = "TRANSACT_DESCRIBE")
    @ApiModelProperty(value = "办理结果描述", required = true)
    private String transactDescribe;

    @XmlElement(name = "RESULT_ISNEED")
    @ApiModelProperty(value = "是否需要出证", required = true)
    private String resultIsneed;

    @XmlElement(name = "RESULT_CODE")
    @ApiModelProperty(value = "结果证照编号", required = false)
    private String resultCode;

    @XmlElement(name = "RESULT_ENTITY")
    @ApiModelProperty(value = "办件结果电子文书", required = false)
    private String resultEntity;

    @XmlElement(name = "RESULT_DESCRIBE")
    @ApiModelProperty(value = "办件结果描述。", required = false)
    private String resultDescribe;

    @XmlElement(name = "RESULT_DEADLINE")
    @ApiModelProperty(value = "结果时效。", required = false)
    private String resultDeadline;

    @XmlElement(name = "REMARK")
    @ApiModelProperty(value = "备注。", required = false)
    private String remark;

    @XmlElement(name = "BELONGSYSTEM")
    @ApiModelProperty(value = "所属单位。", required = true)
    private String belongSystem;

    public String getUnId() {
        return unId;
    }

    public void setUnId(String unId) {
        this.unId = unId;
    }

    public String getProjId() {
        return projId;
    }

    public void setProjId(String projId) {
        this.projId = projId;
    }

    public String getTransactUserUnid() {
        return transactUserUnid;
    }

    public void setTransactUserUnid(String transactUserUnid) {
        this.transactUserUnid = transactUserUnid;
    }

    public String getTransactUsername() {
        return transactUsername;
    }

    public void setTransactUsername(String transactUsername) {
        this.transactUsername = transactUsername;
    }

    public String getTransactDeptname() {
        return transactDeptname;
    }

    public void setTransactDeptname(String transactDeptname) {
        this.transactDeptname = transactDeptname;
    }

    public String getTransactDeptid() {
        return transactDeptid;
    }

    public void setTransactDeptid(String transactDeptid) {
        this.transactDeptid = transactDeptid;
    }

    public String getTransactAreacode() {
        return transactAreacode;
    }

    public void setTransactAreacode(String transactAreacode) {
        this.transactAreacode = transactAreacode;
    }

    public String getTransactTime() {
        return transactTime;
    }

    public void setTransactTime(String transactTime) {
        this.transactTime = transactTime;
    }

    public String getTransactResult() {
        return transactResult;
    }

    public void setTransactResult(String transactResult) {
        this.transactResult = transactResult;
    }

    public String getTransactResultCode() {
        return transactResultCode;
    }

    public void setTransactResultCode(String transactResultCode) {
        this.transactResultCode = transactResultCode;
    }

    public String getTransactDescribe() {
        return transactDescribe;
    }

    public void setTransactDescribe(String transactDescribe) {
        this.transactDescribe = transactDescribe;
    }

    public String getResultIsneed() {
        return resultIsneed;
    }

    public void setResultIsneed(String resultIsneed) {
        this.resultIsneed = resultIsneed;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultEntity() {
        return resultEntity;
    }

    public void setResultEntity(String resultEntity) {
        this.resultEntity = resultEntity;
    }

    public String getResultDescribe() {
        return resultDescribe;
    }

    public void setResultDescribe(String resultDescribe) {
        this.resultDescribe = resultDescribe;
    }

    public String getResultDeadline() {
        return resultDeadline;
    }

    public void setResultDeadline(String resultDeadline) {
        this.resultDeadline = resultDeadline;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBelongSystem() {
        return belongSystem;
    }

    public void setBelongSystem(String belongSystem) {
        this.belongSystem = belongSystem;
    }

    @Override
    public String toString() {
        return "SbdjResquestBjxxServiceInfoDTO{" +
                "unId='" + unId + '\'' +
                ", projId='" + projId + '\'' +
                ", transactUserUnid='" + transactUserUnid + '\'' +
                ", transactUsername='" + transactUsername + '\'' +
                ", transactDeptname='" + transactDeptname + '\'' +
                ", transactDeptid='" + transactDeptid + '\'' +
                ", transactAreacode='" + transactAreacode + '\'' +
                ", transactTime='" + transactTime + '\'' +
                ", transactResult='" + transactResult + '\'' +
                ", transactResultCode='" + transactResultCode + '\'' +
                ", transactDescribe='" + transactDescribe + '\'' +
                ", resultIsneed='" + resultIsneed + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", resultEntity='" + resultEntity + '\'' +
                ", resultDescribe='" + resultDescribe + '\'' +
                ", resultDeadline='" + resultDeadline + '\'' +
                ", remark='" + remark + '\'' +
                ", belongSystem='" + belongSystem + '\'' +
                '}';
    }
}
