package cn.gtmap.realestate.common.core.domain.exchange.yzw;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2017/9/28
 * @description EMS物流信息
 */
@Table(name = "inf_apply_wlxx")
public class InfApplyWlxxDO {
    @Id
    @ApiModelProperty(value = "唯一标识")
    private String no;
    @ApiModelProperty(value = "办件编号")
    private String internalNo;
    @ApiModelProperty(value = "政务服务中心编码")
    private String zwfwzxCode;
    @ApiModelProperty(value = "信封流水号")
    private String emsOrdNo;
    @ApiModelProperty(value = "快递单号")
    private String mailNum;
    @ApiModelProperty(value = "受理台席名称（窗口号）")
    private String govTbName;
    @ApiModelProperty(value = "邮寄类型")
    private String postType;
    @ApiModelProperty(value = "寄件内容类型")
    private String netType;
    @ApiModelProperty(value = "收费类型")
    private String bussType;
    @ApiModelProperty(value = "发件人姓名")
    private String sendName;
    @ApiModelProperty(value = "发件人省")
    private String sendProv;
    @ApiModelProperty(value = "发件人市")
    private String sendCity;
    @ApiModelProperty(value = "发件人区县")
    private String sendCountry;
    @ApiModelProperty(value = "发件地址")
    private String sendStrect;
    @ApiModelProperty(value = "发件人手机")
    private String sendPhone;
    @ApiModelProperty(value = "发件人联系电话")
    private String sendCall;
    @ApiModelProperty(value = "收件人名称")
    private String rcvName;
    @ApiModelProperty(value = "收件人省")
    private String rcvProv;
    @ApiModelProperty(value = "收件人市")
    private String rcvCity;
    @ApiModelProperty(value = "收件人区县")
    private String rcvCountry;
    @ApiModelProperty(value = "收件地址")
    private String rcvStrect;
    @ApiModelProperty(value = "收件人手机")
    private String rcvPhone;
    @ApiModelProperty(value = "收件人联系电话")
    private String rcvCall;
    @ApiModelProperty(value = "收件人邮编")
    private String rcvPostcode;
    @ApiModelProperty(value = "邮寄材料说明")
    private String item;
    @ApiModelProperty(value = "取件验证码")
    private String chkCode;
    @ApiModelProperty(value = "办理部门编码")
    private String deptno;
    @ApiModelProperty(value = "办理部门")
    private String orgname;
    @ApiModelProperty(value = "是否已经寄送")
    private String issend;
    @ApiModelProperty(value = "寄送时间")
    private Date sendtime;
    @ApiModelProperty(value = "信息同步读取时间")
    private Date syncDate;
    @ApiModelProperty(value = "信息同步标志")
    private String syncSign;
    @ApiModelProperty(value = "信息同步错误原因")
    private String syncErrorDesc;
    @ApiModelProperty(value = "数据来源")
    private String dataSources;

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

    public String getZwfwzxCode() {
        return zwfwzxCode;
    }

    public void setZwfwzxCode(String zwfwzxCode) {
        this.zwfwzxCode = zwfwzxCode;
    }

    public String getEmsOrdNo() {
        return emsOrdNo;
    }

    public void setEmsOrdNo(String emsOrdNo) {
        this.emsOrdNo = emsOrdNo;
    }

    public String getMailNum() {
        return mailNum;
    }

    public void setMailNum(String mailNum) {
        this.mailNum = mailNum;
    }

    public String getGovTbName() {
        return govTbName;
    }

    public void setGovTbName(String govTbName) {
        this.govTbName = govTbName;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getBussType() {
        return bussType;
    }

    public void setBussType(String bussType) {
        this.bussType = bussType;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getSendProv() {
        return sendProv;
    }

    public void setSendProv(String sendProv) {
        this.sendProv = sendProv;
    }

    public String getSendCity() {
        return sendCity;
    }

    public void setSendCity(String sendCity) {
        this.sendCity = sendCity;
    }

    public String getSendCountry() {
        return sendCountry;
    }

    public void setSendCountry(String sendCountry) {
        this.sendCountry = sendCountry;
    }

    public String getSendStrect() {
        return sendStrect;
    }

    public void setSendStrect(String sendStrect) {
        this.sendStrect = sendStrect;
    }

    public String getSendPhone() {
        return sendPhone;
    }

    public void setSendPhone(String sendPhone) {
        this.sendPhone = sendPhone;
    }

    public String getSendCall() {
        return sendCall;
    }

    public void setSendCall(String sendCall) {
        this.sendCall = sendCall;
    }

    public String getRcvName() {
        return rcvName;
    }

    public void setRcvName(String rcvName) {
        this.rcvName = rcvName;
    }

    public String getRcvProv() {
        return rcvProv;
    }

    public void setRcvProv(String rcvProv) {
        this.rcvProv = rcvProv;
    }

    public String getRcvCity() {
        return rcvCity;
    }

    public void setRcvCity(String rcvCity) {
        this.rcvCity = rcvCity;
    }

    public String getRcvCountry() {
        return rcvCountry;
    }

    public void setRcvCountry(String rcvCountry) {
        this.rcvCountry = rcvCountry;
    }

    public String getRcvStrect() {
        return rcvStrect;
    }

    public void setRcvStrect(String rcvStrect) {
        this.rcvStrect = rcvStrect;
    }

    public String getRcvPhone() {
        return rcvPhone;
    }

    public void setRcvPhone(String rcvPhone) {
        this.rcvPhone = rcvPhone;
    }

    public String getRcvCall() {
        return rcvCall;
    }

    public void setRcvCall(String rcvCall) {
        this.rcvCall = rcvCall;
    }

    public String getRcvPostcode() {
        return rcvPostcode;
    }

    public void setRcvPostcode(String rcvPostcode) {
        this.rcvPostcode = rcvPostcode;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getChkCode() {
        return chkCode;
    }

    public void setChkCode(String chkCode) {
        this.chkCode = chkCode;
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getIssend() {
        return issend;
    }

    public void setIssend(String issend) {
        this.issend = issend;
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
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

    public String getDataSources() {
        return dataSources;
    }

    public void setDataSources(String dataSources) {
        this.dataSources = dataSources;
    }

    @Override
    public String toString() {
        return "InfApplyWlxxDO{" +
                "no='" + no + '\'' +
                ", internalNo='" + internalNo + '\'' +
                ", zwfwzxCode='" + zwfwzxCode + '\'' +
                ", emsOrdNo='" + emsOrdNo + '\'' +
                ", mailNum='" + mailNum + '\'' +
                ", govTbName='" + govTbName + '\'' +
                ", postType='" + postType + '\'' +
                ", netType='" + netType + '\'' +
                ", bussType='" + bussType + '\'' +
                ", sendName='" + sendName + '\'' +
                ", sendProv='" + sendProv + '\'' +
                ", sendCity='" + sendCity + '\'' +
                ", sendCountry='" + sendCountry + '\'' +
                ", sendStrect='" + sendStrect + '\'' +
                ", sendPhone='" + sendPhone + '\'' +
                ", sendCall='" + sendCall + '\'' +
                ", rcvName='" + rcvName + '\'' +
                ", rcvProv='" + rcvProv + '\'' +
                ", rcvCity='" + rcvCity + '\'' +
                ", rcvCountry='" + rcvCountry + '\'' +
                ", rcvStrect='" + rcvStrect + '\'' +
                ", rcvPhone='" + rcvPhone + '\'' +
                ", rcvCall='" + rcvCall + '\'' +
                ", rcvPostcode='" + rcvPostcode + '\'' +
                ", item='" + item + '\'' +
                ", chkCode='" + chkCode + '\'' +
                ", deptno='" + deptno + '\'' +
                ", orgname='" + orgname + '\'' +
                ", issend='" + issend + '\'' +
                ", sendtime=" + sendtime +
                ", syncDate=" + syncDate +
                ", syncSign='" + syncSign + '\'' +
                ", syncErrorDesc='" + syncErrorDesc + '\'' +
                ", dataSources='" + dataSources + '\'' +
                '}';
    }
}
