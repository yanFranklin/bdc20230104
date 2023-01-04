package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:fanghao@gtmap.cn">fanghao</a>
 * @version 1.0, 2022/08/29
 * @description
 */
@Table(name = "CERT_INFO_QZK")
public class DzzzQzkDO {
    @Id
    @ApiModelProperty(value = "唯一标志")
    private String rowguid;

    @ApiModelProperty(value = "证照编号")
    private String certno;

    @ApiModelProperty(value = "证照名称")
    private String certname;

    @ApiModelProperty(value = "可信等级(ABCD,默认A)")
    private String certlevel;

    @ApiModelProperty(value = "颁发单位名称")
    private String certawarddept;

    @ApiModelProperty(value = "颁发单位统一社会信用代码")
    private String certawarddeptorgcode;

    @ApiModelProperty(value = "持有人姓名")
    private String certownername;

    @ApiModelProperty(value = "持有人类型")
    private String certownertype;

    @ApiModelProperty(value = "持有人证件类型")
    private String certownercerttype;

    @ApiModelProperty(value = "持有人证件号码")
    private String certownerno;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "颁证日期")
    private Date awarddate;

    @ApiModelProperty(value = "证照目录唯一标志")
    private String certcatalogid;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "证照照面信息")
    private String certinfoextension;

    @ApiModelProperty(value = "操作类型")
    private String operatetype;

    @ApiModelProperty(value = "来源部门统一社会信用代码")
    private String orgcode;

    public String getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }

    public String getRowguid() {
        return rowguid;
    }

    public void setRowguid(String rowguid) {
        this.rowguid = rowguid;
    }

    public String getCertno() {
        return certno;
    }

    public void setCertno(String certno) {
        this.certno = certno;
    }

    public String getCertname() {
        return certname;
    }

    public void setCertname(String certname) {
        this.certname = certname;
    }

    public String getCertlevel() {
        return certlevel;
    }

    public void setCertlevel(String certlevel) {
        this.certlevel = certlevel;
    }

    public String getCertawarddept() {
        return certawarddept;
    }

    public void setCertawarddept(String certawarddept) {
        this.certawarddept = certawarddept;
    }

    public String getCertawarddeptorgcode() {
        return certawarddeptorgcode;
    }

    public void setCertawarddeptorgcode(String certawarddeptorgcode) {
        this.certawarddeptorgcode = certawarddeptorgcode;
    }

    public String getCertownername() {
        return certownername;
    }

    public void setCertownername(String certownername) {
        this.certownername = certownername;
    }

    public String getCertownertype() {
        return certownertype;
    }

    public void setCertownertype(String certownertype) {
        this.certownertype = certownertype;
    }

    public String getCertownercerttype() {
        return certownercerttype;
    }

    public void setCertownercerttype(String certownercerttype) {
        this.certownercerttype = certownercerttype;
    }

    public String getCertownerno() {
        return certownerno;
    }

    public void setCertownerno(String certownerno) {
        this.certownerno = certownerno;
    }

    public Date getAwarddate() {
        return awarddate;
    }

    public void setAwarddate(Date awarddate) {
        this.awarddate = awarddate;
    }

    public String getCertcatalogid() {
        return certcatalogid;
    }

    public void setCertcatalogid(String certcatalogid) {
        this.certcatalogid = certcatalogid;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getCertinfoextension() {
        return certinfoextension;
    }

    public void setCertinfoextension(String certinfoextension) {
        this.certinfoextension = certinfoextension;
    }

    public String getOperatetype() {
        return operatetype;
    }

    public void setOperatetype(String operatetype) {
        this.operatetype = operatetype;
    }
}
