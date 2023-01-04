package cn.gtmap.realestate.exchange.core.domain.yzw.yancheng;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/11/23
 * @description 材料附件信息表
 */
@Table(name = "T_BM_CASEATTACHMENT")
public class TBmCaseattachmentDO {

    @Id
    @ApiModelProperty(value = "唯一标识")
    private String onlymark;//no

    @ApiModelProperty(value = "办件编号")
    private String caseno;

    @ApiModelProperty(value = "单位编码")
    private String deptno;

    @ApiModelProperty(value = "材料唯一标识")
    private String matecode;

    @ApiModelProperty(value = "附件名称")
    private String attachmentname;

    @ApiModelProperty(value = "附件内容")
    private String attacontent;

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

    public String getMatecode() {
        return matecode;
    }

    public void setMatecode(String matecode) {
        this.matecode = matecode;
    }

    public String getAttachmentname() {
        return attachmentname;
    }

    public void setAttachmentname(String attachmentname) {
        this.attachmentname = attachmentname;
    }

    public String getAttacontent() {
        return attacontent;
    }

    public void setAttacontent(String attacontent) {
        this.attacontent = attacontent;
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
