package cn.gtmap.realestate.common.core.domain.exchange;

import cn.gtmap.realestate.common.core.dto.exchange.openapi.BdcOpenApiParamDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/7/21 09:10
 */
@Table(name = "bdc_dw_jk_cs")
public class BdcDwJkCsDO {

    @Id
    @ApiModelProperty(value = "主键,参数id")
    private String csid;

    @ApiModelProperty(value = "参数父id")
    private String csfid;

    @ApiModelProperty(value = "参数名")
    private String csm;

    @ApiModelProperty(value = "字段类型")
    private String cszdlx;

    @ApiModelProperty(value = "参数说明")
    private String cssm;

    @ApiModelProperty(value = "参数类型")
    private Integer cslx;

    @ApiModelProperty(value = "默认值")
    private String csmrz;

    @ApiModelProperty(value = "层级")
    private String cscj;

    @ApiModelProperty(value = "创建人")
    private String cjr;

    @ApiModelProperty(value = "创建时间")
    private Date cjsj;

    @ApiModelProperty(value = "修改人")
    private String xgr;

    @ApiModelProperty(value = "修改时间")
    private Date xgsj;

    @ApiModelProperty(value = "是否删除")
    private Integer sfsc;

    @ApiModelProperty(value = "是否必填 0是 1否")
    private Integer sfbt;

    @ApiModelProperty(value = "接口id")
    private String jkid;

    @ApiModelProperty(value = "接口参数其他属性")
    private String jkcsext;

    @ApiModelProperty(value = "参数字典名称")
    private String cszdmc;

    @ApiModelProperty(value = "参数拼接配置")
    private String cspj;

    public void convertDefaultValue(BdcDwJkCsDO bdcDwJkCsDO){
        bdcDwJkCsDO.setCjr("system");
        bdcDwJkCsDO.setCjsj(new Date());
        bdcDwJkCsDO.setXgr("system");
        bdcDwJkCsDO.setXgsj(new Date());
        bdcDwJkCsDO.setSfsc(0);
    }

    public void convertDTO(BdcOpenApiParamDTO bdcOpenApiParamDTO){
        this.setCsid(bdcOpenApiParamDTO.getId());
        this.setCsfid(bdcOpenApiParamDTO.getParentId());
        this.setCsm(bdcOpenApiParamDTO.getFieldName());
        this.setCszdlx(bdcOpenApiParamDTO.getFieldType());
        this.setCssm(bdcOpenApiParamDTO.getFieldRemark());
        this.setCslx(bdcOpenApiParamDTO.getParamType());
        this.setCsmrz(bdcOpenApiParamDTO.getDefaultValue());
        this.setCscj(bdcOpenApiParamDTO.getLevel());
        this.setXgr(bdcOpenApiParamDTO.getUpdatedBy());
        this.setXgsj(new Date());
        this.setCjr(bdcOpenApiParamDTO.getCreatedBy());
        this.setCjsj(new Date());
        this.setSfbt(bdcOpenApiParamDTO.getRequired());
        this.setCszdmc(bdcOpenApiParamDTO.getCszdmc());
        this.setCspj(bdcOpenApiParamDTO.getCspj());
        this.setSfsc(0);
    }

    public String getCszdmc() {
        return cszdmc;
    }

    public void setCszdmc(String cszdmc) {
        this.cszdmc = cszdmc;
    }

    public String getJkcsext() {
        return jkcsext;
    }

    public String getCspj() {
        return cspj;
    }

    public void setCspj(String cspj) {
        this.cspj = cspj;
    }

    public void setJkcsext(String jkcsext) {
        this.jkcsext = jkcsext;
    }

    public String getCsid() {
        return csid;
    }

    public void setCsid(String csid) {
        this.csid = csid;
    }

    public String getCsfid() {
        return csfid;
    }

    public void setCsfid(String csfid) {
        this.csfid = csfid;
    }

    public String getCsm() {
        return csm;
    }

    public void setCsm(String csm) {
        this.csm = csm;
    }

    public String getCszdlx() {
        return cszdlx;
    }

    public void setCszdlx(String cszdlx) {
        this.cszdlx = cszdlx;
    }

    public String getCssm() {
        return cssm;
    }

    public void setCssm(String cssm) {
        this.cssm = cssm;
    }

    public Integer getCslx() {
        return cslx;
    }

    public void setCslx(Integer cslx) {
        this.cslx = cslx;
    }

    public String getCsmrz() {
        return csmrz;
    }

    public void setCsmrz(String csmrz) {
        this.csmrz = csmrz;
    }

    public String getCscj() {
        return cscj;
    }

    public void setCscj(String cscj) {
        this.cscj = cscj;
    }

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public String getXgr() {
        return xgr;
    }

    public void setXgr(String xgr) {
        this.xgr = xgr;
    }

    public Date getXgsj() {
        return xgsj;
    }

    public void setXgsj(Date xgsj) {
        this.xgsj = xgsj;
    }

    public Integer getSfsc() {
        return sfsc;
    }

    public void setSfsc(Integer sfsc) {
        this.sfsc = sfsc;
    }

    public Integer getSfbt() {
        return sfbt;
    }

    public void setSfbt(Integer sfbt) {
        this.sfbt = sfbt;
    }

    public String getJkid() {
        return jkid;
    }

    public void setJkid(String jkid) {
        this.jkid = jkid;
    }

    @Override
    public String toString() {
        return "BdcDwJkCsDO{" +
                "csid='" + csid + '\'' +
                ", csfid='" + csfid + '\'' +
                ", csm='" + csm + '\'' +
                ", cszdlx='" + cszdlx + '\'' +
                ", cssm='" + cssm + '\'' +
                ", cslx=" + cslx +
                ", csmrz='" + csmrz + '\'' +
                ", cscj='" + cscj + '\'' +
                ", cjr='" + cjr + '\'' +
                ", cjsj=" + cjsj +
                ", xgr='" + xgr + '\'' +
                ", xgsj=" + xgsj +
                ", sfsc=" + sfsc +
                ", sfbt=" + sfbt +
                ", jkid='" + jkid + '\'' +
                '}';
    }
}
