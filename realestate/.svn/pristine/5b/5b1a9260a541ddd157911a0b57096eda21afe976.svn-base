package cn.gtmap.realestate.common.core.domain.check;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author lst
 * @version 1.0, 2020-01-03
 * @description 检测规则信息表
 */
@Table(name = "CHECK_GZXX")
@ApiModel(value = "CheckGzxxDO",description = "检测规则信息表")
public class CheckGzxxDO {

    @Id
    @ApiModelProperty(value = "规则编码")
    private String gzid;
    @ApiModelProperty(value = "规则Code")
    private String gzcode;
    @ApiModelProperty(value = "规则类型")
    private Integer gzlx;
    @ApiModelProperty(value = "包含流程")
    private String bhlc;
    @ApiModelProperty(value = "去除流程")
    private String qclc;
    @ApiModelProperty(value = "提示信息")
    private String tsxx;
    @ApiModelProperty(value = "规则等级")
    private Integer gzdj;
    @ApiModelProperty(value = "规则描述")
    private String gzms;
    @ApiModelProperty(value = "规则分组")
    private Integer gzfz;
    @ApiModelProperty(value = "是否忽略")
    private Integer sfhl;

    public String getGzid() {
        return gzid;
    }

    public void setGzid(String gzid) {
        this.gzid = gzid;
    }

    public String getGzcode() {
        return gzcode;
    }

    public void setGzcode(String gzcode) {
        this.gzcode = gzcode;
    }

    public Integer getGzlx() {
        return gzlx;
    }

    public void setGzlx(Integer gzlx) {
        this.gzlx = gzlx;
    }

    @Override
    public String toString() {
        return "CheckGzxxDO{" +
                "gzid='" + gzid + '\'' +
                ", gzcode='" + gzcode + '\'' +
                ", gzlx=" + gzlx +
                ", bhlc='" + bhlc + '\'' +
                ", qclc='" + qclc + '\'' +
                ", tsxx='" + tsxx + '\'' +
                ", gzdj=" + gzdj +
                ", gzms='" + gzms + '\'' +
                ", gzfz=" + gzfz +
                ", sfhl=" + sfhl +
                '}';
    }

    public String getBhlc() {
        return bhlc;
    }

    public void setBhlc(String bhlc) {
        this.bhlc = bhlc;
    }

    public String getQclc() {
        return qclc;
    }

    public void setQclc(String qclc) {
        this.qclc = qclc;
    }

    public String getTsxx() {
        return tsxx;
    }

    public void setTsxx(String tsxx) {
        this.tsxx = tsxx;
    }

    public Integer getGzdj() {
        return gzdj;
    }

    public void setGzdj(Integer gzdj) {
        this.gzdj = gzdj;
    }

    public String getGzms() {
        return gzms;
    }

    public void setGzms(String gzms) {
        this.gzms = gzms;
    }

    public Integer getGzfz() {
        return gzfz;
    }

    public void setGzfz(Integer gzfz) {
        this.gzfz = gzfz;
    }

    public Integer getSfhl() {
        return sfhl;
    }

    public void setSfhl(Integer sfhl) {
        this.sfhl = sfhl;
    }
}
