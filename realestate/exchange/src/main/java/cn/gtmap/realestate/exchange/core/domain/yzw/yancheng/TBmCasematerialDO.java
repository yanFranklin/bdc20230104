package cn.gtmap.realestate.exchange.core.domain.yzw.yancheng;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/11/23
 * @description 申报材料信息表
 */
@Table(name = "T_BM_CASEMATERIAL")
public class TBmCasematerialDO {

    @Id
    @ApiModelProperty(value = "唯一标识")
    private String onlymark;

    @ApiModelProperty(value = "办件编号")
    private String caseno;

    @ApiModelProperty(value = "部门编码")
    private String deptno;

    @ApiModelProperty(value = "材料名称")
    private String matename;

    @ApiModelProperty(value = "材料收取方式")
    private String taketype;

    @ApiModelProperty(value = "是否已收取")
    private String istake;

    @ApiModelProperty(value = "收取数量")
    private Integer takenumber;

    @ApiModelProperty(value = "收取时间")
    private Date taketime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "同步时间")
    private Date syncdatetime;

    @ApiModelProperty(value = "同步标识")
    private Integer syncsign;

    @ApiModelProperty(value = "数据来源")
    private String datasource;

    @ApiModelProperty(value = "同步错误信息")
    private String syncerrormsg;

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

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno;
    }

    public String getMatename() {
        return matename;
    }

    public void setMatename(String matename) {
        this.matename = matename;
    }

    public String getTaketype() {
        return taketype;
    }

    public void setTaketype(String taketype) {
        this.taketype = taketype;
    }

    public String getIstake() {
        return istake;
    }

    public void setIstake(String istake) {
        this.istake = istake;
    }

    public Integer getTakenumber() {
        return takenumber;
    }

    public void setTakenumber(Integer takenumber) {
        this.takenumber = takenumber;
    }

    public Date getTaketime() {
        return taketime;
    }

    public void setTaketime(Date taketime) {
        this.taketime = taketime;
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

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public String getSyncerrormsg() {
        return syncerrormsg;
    }

    public void setSyncerrormsg(String syncerrormsg) {
        this.syncerrormsg = syncerrormsg;
    }
}


