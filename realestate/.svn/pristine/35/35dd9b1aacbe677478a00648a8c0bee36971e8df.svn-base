package cn.gtmap.interchange.core.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: ibm
 * Date: 2017/9/26.
 * Time: 15:10
 * To change this template use File | Settings | File Templates.
 */
@Table(
        name = "T_BM_CASEPROCESS"
)
public class TBmCaseprocess {
    @Id
    private String onlymark;
    private String caseno;//办件编号
    private String nodename;//办理环节名称
    private String username;//办理人
    private String tachenote;//办理意见
    private String tachestatus;//业务动作
    private Date tachestartdatetime;//环节开始时间
    private Date tacheenddatetime;//环节办理时间
    private String nextnodename;//下一环节名称
    private String remark;//备注
    private Date syncdatetime;//同步时间
    private Integer syncsign;//同步标识
    private String syncerrormsg;
    private String emsOrdNo;//信封流水号
    private String mailNum;//快递单号
    private Date sendtime;//寄送时间
    private String netType;//寄件内容类型1.申请材料寄送 2.审批结果寄送
    private String eventname;//业务动作 1通过 2退回 9其他（默认值取1）

    public String getOnlymark() {
        return onlymark;
    }

    public void setOnlymark(String onlymark) {
        this.onlymark = onlymark;
    }

    public String getCaseno() {
        return caseno;
    }

    public void setCaseno(String caseno) {
        this.caseno = caseno;
    }

    public String getNodename() {
        return nodename;
    }

    public void setNodename(String nodename) {
        this.nodename = nodename;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTachenote() {
        return tachenote;
    }

    public void setTachenote(String tachenote) {
        this.tachenote = tachenote;
    }

    public String getTachestatus() {
        return tachestatus;
    }

    public void setTachestatus(String tachestatus) {
        this.tachestatus = tachestatus;
    }

    public String getNextnodename() {
        return nextnodename;
    }

    public void setNextnodename(String nextnodename) {
        this.nextnodename = nextnodename;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSyncerrormsg() {
        return syncerrormsg;
    }

    public void setSyncerrormsg(String syncerrormsg) {
        this.syncerrormsg = syncerrormsg;
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

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public Date getTachestartdatetime() {
        return tachestartdatetime;
    }

    public void setTachestartdatetime(Date tachestartdatetime) {
        this.tachestartdatetime = tachestartdatetime;
    }

    public Date getTacheenddatetime() {
        return tacheenddatetime;
    }

    public void setTacheenddatetime(Date tacheenddatetime) {
        this.tacheenddatetime = tacheenddatetime;
    }

    public Date getSyncdatetime() {
        return syncdatetime;
    }

    public void setSyncdatetime(Date syncdatetime) {
        this.syncdatetime = syncdatetime;
    }

    public Integer getSyncsign() {
        return syncsign;
    }

    public void setSyncsign(Integer syncsign) {
        this.syncsign = syncsign;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

}
