package cn.gtmap.realestate.exchange.core.domain.yzw.yancheng;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/11/23
 * @description 办件结果信息表
 */
@Table(name = "T_BM_CASERESULT")
public class TBmCaseresultDO {

    @Id
    @ApiModelProperty(value = "唯一标识")
    private String onlymark;

    @ApiModelProperty(value = "办件编号")
    private String caseno;

    @ApiModelProperty(value = "办结结果")
    private String resultstatus;

    @ApiModelProperty(value = "办结日期")
    private Date caseenddatetime;

    @ApiModelProperty(value = "办理人员姓名")
    private String caseenduser;

    @ApiModelProperty(value = "办理结果描述")
    private String resultdesc;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "同步时间")
    private Date syncdatetime;

    @ApiModelProperty(value = "同步标识")
    private Integer syncsign;

    @ApiModelProperty(value = "同步错误信息")
    private String syncerrormsg;

    @ApiModelProperty(value = "结果证照名称")
    private String resultcetrname;

    @ApiModelProperty(value = "结果证照编号")
    private String resultcetrno;

    @ApiModelProperty(value = "是否快递递送结果")
    private String isdeliveryresults;

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

    public String getResultstatus() {
        return resultstatus;
    }

    public void setResultstatus(String resultstatus) {
        this.resultstatus = resultstatus;
    }

    public Date getCaseenddatetime() {
        return caseenddatetime;
    }

    public void setCaseenddatetime(Date caseenddatetime) {
        this.caseenddatetime = caseenddatetime;
    }

    public String getCaseenduser() {
        return caseenduser;
    }

    public void setCaseenduser(String caseenduser) {
        this.caseenduser = caseenduser;
    }

    public String getResultdesc() {
        return resultdesc;
    }

    public void setResultdesc(String resultdesc) {
        this.resultdesc = resultdesc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getSyncerrormsg() {
        return syncerrormsg;
    }

    public void setSyncerrormsg(String syncerrormsg) {
        this.syncerrormsg = syncerrormsg;
    }

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
}
