package cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.slxx;

import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/7/25
 * @description 5.14    受理信息定义实体
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SbdjResquestSlxxServiceInfoDTO {

    @XmlElement(name = "UNID")
    @ApiModelProperty(value = "由业务系统自动产生(32位UUID，唯一标识，防重，排查使用，无业务含义)", required = true)
    private String unId;

    @XmlElement(name = "PROJID")
    @ApiModelProperty(value = "可为空，首次调用时为空；部分系统存在驳回重新申报的场景，重新申报时，需要传递办件编号", required = true)
    private String projId;

    @XmlElement(name = "ACCEPT_USERUNID")
    @ApiModelProperty(value = "受理人标识", required = false)
    private String acceptUserunid;

    @XmlElement(name = "ACCEPT_USERNAME")
    @ApiModelProperty(value = "受理人员", required = true)
    private String acceptUsername;

    @XmlElement(name = "ACCEPT_DEPTNAME")
    @ApiModelProperty(value = "受理人员所属部门名称", required = true)
    private String acceptDeptname;

    @XmlElement(name = "ACCEPT_DEPTID")
    @ApiModelProperty(value = "受理人员所属部门编码", required = true)
    private String acceptDeptid;

    @XmlElement(name = "ACCEPT_AREACODE")
    @ApiModelProperty(value = "受理人员所属部门的所在行政区划编码", required = true)
    private String acceptAreacode;


    @XmlElement(name = "ACCEPT_TIME")
    @ApiModelProperty(value = "受理时间", required = true)
    private String acceptTime;

    @XmlElement(name = "PROMISEVALUE")
    @ApiModelProperty(value = "承诺期限", required = true)
    private String promiseValue;

    @XmlElement(name = "PROMISETYPE")
    @ApiModelProperty(value = "承诺期限单位", required = true)
    private String promiseType;

    @XmlElement(name = "PROMISE_ETIME")
    @ApiModelProperty(value = "承诺办结时间", required = true)
    private String promiseEtime;

    @XmlElement(name = "ACCEPT_DOCUMENT")
    @ApiModelProperty(value = "受理文书编号", required = true)
    private String acceptDocument;

    @XmlElement(name = "ACCEPT_RESULT_CODE")
    @ApiModelProperty(value = "受理结果", required = true)
    private String acceptResultCode;

    @XmlElement(name = "BELONGSYSTEM")
    @ApiModelProperty(value = "所属单位", required = true)
    private String belongSystem;

    @XmlElement(name = "REMARK")
    @ApiModelProperty(value = "受理意见。", required = true)
    private String remark;

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

    public String getAcceptUserunid() {
        return acceptUserunid;
    }

    public void setAcceptUserunid(String acceptUserunid) {
        this.acceptUserunid = acceptUserunid;
    }

    public String getAcceptUsername() {
        return acceptUsername;
    }

    public void setAcceptUsername(String acceptUsername) {
        this.acceptUsername = acceptUsername;
    }

    public String getAcceptDeptname() {
        return acceptDeptname;
    }

    public void setAcceptDeptname(String acceptDeptname) {
        this.acceptDeptname = acceptDeptname;
    }

    public String getAcceptDeptid() {
        return acceptDeptid;
    }

    public void setAcceptDeptid(String acceptDeptid) {
        this.acceptDeptid = acceptDeptid;
    }

    public String getAcceptAreacode() {
        return acceptAreacode;
    }

    public void setAcceptAreacode(String acceptAreacode) {
        this.acceptAreacode = acceptAreacode;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getPromiseValue() {
        return promiseValue;
    }

    public void setPromiseValue(String promiseValue) {
        this.promiseValue = promiseValue;
    }

    public String getPromiseType() {
        return promiseType;
    }

    public void setPromiseType(String promiseType) {
        this.promiseType = promiseType;
    }

    public String getPromiseEtime() {
        return promiseEtime;
    }

    public void setPromiseEtime(String promiseEtime) {
        this.promiseEtime = promiseEtime;
    }

    public String getAcceptDocument() {
        return acceptDocument;
    }

    public void setAcceptDocument(String acceptDocument) {
        this.acceptDocument = acceptDocument;
    }

    public String getAcceptResultCode() {
        return acceptResultCode;
    }

    public void setAcceptResultCode(String acceptResultCode) {
        this.acceptResultCode = acceptResultCode;
    }

    public String getBelongSystem() {
        return belongSystem;
    }

    public void setBelongSystem(String belongSystem) {
        this.belongSystem = belongSystem;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "SbdjResquestSLxxServiceInfoDTO{" +
                "unId='" + unId + '\'' +
                ", projId='" + projId + '\'' +
                ", acceptUserunid='" + acceptUserunid + '\'' +
                ", acceptUsername='" + acceptUsername + '\'' +
                ", acceptDeptname='" + acceptDeptname + '\'' +
                ", acceptDeptid='" + acceptDeptid + '\'' +
                ", acceptAreacode='" + acceptAreacode + '\'' +
                ", acceptTime='" + acceptTime + '\'' +
                ", promiseValue='" + promiseValue + '\'' +
                ", promiseType='" + promiseType + '\'' +
                ", promiseEtime='" + promiseEtime + '\'' +
                ", acceptDocument='" + acceptDocument + '\'' +
                ", acceptResultCode='" + acceptResultCode + '\'' +
                ", belongSystem='" + belongSystem + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
