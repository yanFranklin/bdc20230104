package cn.gtmap.realestate.common.core.domain.analysis;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author by hy.
 * @version 1.0, 2018/8/27
 * @description
 */
@Table(name = "dtcx_cxjg")
public class DtcxCxjgDO {
    @Id
    @ApiModelProperty("主键：结果id")
    private String jgid;

    @ApiModelProperty("查询id")
    private String cxid;

    @ApiModelProperty("查询代号")
    private String cxdh;

    @ApiModelProperty("结果字段id")
    private String jgzdid;

    @ApiModelProperty("结果字段名称")
    private String jgzdname;

    @ApiModelProperty("是否默认显示")
    private String mrxs;

    @ApiModelProperty("结果类型")
    private String jgtype;

    @ApiModelProperty("对应字典id")
    private String zdid;

    @ApiModelProperty("列宽")
    private String lk;

    @ApiModelProperty("对齐方式")
    private String dqfs;

    @ApiModelProperty("定位")
    private String dw;

    @ApiModelProperty("优先级顺序")
    private Integer priority;

    public String getJgid() {
        return jgid;
    }

    public void setJgid(String jgid) {
        this.jgid = jgid;
    }

    public String getCxid() {
        return cxid;
    }

    public void setCxid(String cxid) {
        this.cxid = cxid;
    }

    public String getCxdh() {
        return cxdh;
    }

    public void setCxdh(String cxdh) {
        this.cxdh = cxdh;
    }

    public String getJgzdid() {
        return jgzdid;
    }

    public void setJgzdid(String jgzdid) {
        this.jgzdid = jgzdid;
    }

    public String getJgzdname() {
        return jgzdname;
    }

    public void setJgzdname(String jgzdname) {
        this.jgzdname = jgzdname;
    }

    public String getMrxs() {
        return mrxs;
    }

    public void setMrxs(String mrxs) {
        this.mrxs = mrxs;
    }

    public String getJgtype() {
        return jgtype;
    }

    public void setJgtype(String jgtype) {
        this.jgtype = jgtype;
    }

    public String getZdid() {
        return zdid;
    }

    public void setZdid(String zdid) {
        this.zdid = zdid;
    }

    public String getLk() {
        return lk;
    }

    public void setLk(String lk) {
        this.lk = lk;
    }

    public String getDqfs() {
        return dqfs;
    }

    public void setDqfs(String dqfs) {
        this.dqfs = dqfs;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
