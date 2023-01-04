package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/1/16
 * @description 打印主表配置表
 */
@Table(name = "BDC_DYSJ_PZ")
public class BdcDysjPzDO {
    @Id
    @ApiModelProperty(value = "主键ID")
    private String id;
    /**
     * 打印数据源
     */
    @ApiModelProperty(value = "打印数据源")
    private String dysjy;
    /**
     * 打印类型
     */
    @ApiModelProperty(value = "打印类型")
    private String dylx;
    /**
     * 参数
     */
    @ApiModelProperty(value = "参数")
    private String cs;

    /**
     * 参数
     */
    @ApiModelProperty(value = "加密参数")
    private String jmcs;


    /**
     * 参数
     */
    @ApiModelProperty(value = "结果解密")
    private String jmjg;

    /**
     * 打印字段（xml配置）
     */
    @ApiModelProperty(value = "打印字段")
    private String dyzd;
    @ApiModelProperty(value = "fr3打印模板地址")
    private String fr3path;
    @ApiModelProperty(value = "pdf打印模板地址")
    private String pdfpath;
    @ApiModelProperty(value = "配置用途或者名称")
    private String ytmc;
    @ApiModelProperty(value = "数据来源")
    private String sjly;
    @ApiModelProperty(value = "请求应用")
    private String qqyy;
    @ApiModelProperty(value = "服务方式")
    private String fwfs;
    @ApiModelProperty(value = "数据库源")
    private String dbsource;
    @ApiModelProperty(value = "配置日期")
    private Date pzrq;

    public String getFr3path() {
        return fr3path;
    }

    public void setFr3path(String fr3path) {
        this.fr3path = fr3path;
    }

    public String getPdfpath() {
        return pdfpath;
    }

    public String getDbsource() {
        return dbsource;
    }

    public void setDbsource(String dbsource) {
        this.dbsource = dbsource;
    }

    public void setPdfpath(String pdfpath) {
        this.pdfpath = pdfpath;
    }

    public String getYtmc() {
        return ytmc;
    }

    public void setYtmc(String ytmc) {
        this.ytmc = ytmc;
    }

    public String getSjly() {
        return sjly;
    }

    public void setSjly(String sjly) {
        this.sjly = sjly;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDysjy() {
        return dysjy;
    }

    public void setDysjy(String dysjy) {
        this.dysjy = dysjy;
    }

    public String getQqyy() {
        return qqyy;
    }

    public void setQqyy(String qqyy) {
        this.qqyy = qqyy;
    }

    public String getFwfs() {
        return fwfs;
    }

    public void setFwfs(String fwfs) {
        this.fwfs = fwfs;
    }

    public String getDylx() {
        return dylx;
    }

    public void setDylx(String dylx) {
        this.dylx = dylx;
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }

    public String getJmcs() {
        return jmcs;
    }

    public void setJmcs(String jmcs) {
        this.jmcs = jmcs;
    }

    public String getJmjg() {
        return jmjg;
    }

    public void setJmjg(String jmjg) {
        this.jmjg = jmjg;
    }

    public String getDyzd() {
        return dyzd;
    }

    public void setDyzd(String dyzd) {
        this.dyzd = dyzd;
    }

    public Date getPzrq() {
        return pzrq;
    }

    public void setPzrq(Date pzrq) {
        this.pzrq = pzrq;
    }

    @Override
    public String toString() {
        return "BdcDysjPzDO{" +
                "id='" + id + '\'' +
                ", dysjy='" + dysjy + '\'' +
                ", dylx='" + dylx + '\'' +
                ", cs='" + cs + '\'' +
                ", dyzd='" + dyzd + '\'' +
                ", fr3path='" + fr3path + '\'' +
                ", pdfpath='" + pdfpath + '\'' +
                ", ytmc='" + ytmc + '\'' +
                ", sjly='" + sjly + '\'' +
                ", qqyy='" + qqyy + '\'' +
                ", fwfs='" + fwfs + '\'' +
                ", dbsource='" + dbsource + '\'' +
                ", pzrq=" + pzrq +
                '}';
    }
}
