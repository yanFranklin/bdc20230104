package cn.gtmap.realestate.common.core.domain.register;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @program: realestate
 * @description: 工作流接口实体信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-03-30 14:56
 **/
@Table(name = "BDC_GZLJK")
@ApiModel(value = "BdcGzlJkDO", description = "工作流接口实体信息")
public class BdcGzlJkDO {

    @Id
    @ApiModelProperty(value = "接口id")
    private String jkid;

    @ApiModelProperty(value = "接口名称")
    private String jkmc;

    @ApiModelProperty(value = "接口说明")
    private String jksm;

    @ApiModelProperty(value = "接口类型")
    private Integer jklx;

    @ApiModelProperty(value = "创建时间")
    private Date cjsj;

    @ApiModelProperty(value = "创建人")
    private String cjr;

    @ApiModelProperty(value = "应用名称")
    private String yymc;

    @ApiModelProperty(value = "请求方式")
    private String qqfs;
    @ApiModelProperty(value = "是否同步")
    private Integer sftb;

    @ApiModelProperty(value = "是否忽略异常")
    private Integer sfhlyc;

    public String getJkid() {
        return jkid;
    }

    public void setJkid(String jkid) {
        this.jkid = jkid;
    }

    public String getJkmc() {
        return jkmc;
    }

    public void setJkmc(String jkmc) {
        this.jkmc = jkmc;
    }

    public String getJksm() {
        return jksm;
    }

    public void setJksm(String jksm) {
        this.jksm = jksm;
    }

    public Integer getJklx() {
        return jklx;
    }

    public void setJklx(Integer jklx) {
        this.jklx = jklx;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    public String getYymc() {
        return yymc;
    }

    public void setYymc(String yymc) {
        this.yymc = yymc;
    }

    public String getQqfs() {
        return qqfs;
    }

    public void setQqfs(String qqfs) {
        this.qqfs = qqfs;
    }

    public Integer getSftb() {
        return sftb;
    }

    public void setSftb(Integer sftb) {
        this.sftb = sftb;
    }

    public Integer getSfhlyc() {
        return sfhlyc;
    }

    public void setSfhlyc(Integer sfhlyc) {
        this.sfhlyc = sfhlyc;
    }

    @Override
    public String toString() {
        return "BdcGzlJkDO{" +
                "jkid='" + jkid + '\'' +
                ", jkmc='" + jkmc + '\'' +
                ", jksm='" + jksm + '\'' +
                ", jklx=" + jklx +
                ", cjsj=" + cjsj +
                ", cjr='" + cjr + '\'' +
                ", yymc='" + yymc + '\'' +
                ", qqfs='" + qqfs + '\'' +
                ", sftb=" + sftb +
                ", sfhlyc=" + sfhlyc +
                '}';
    }
}
