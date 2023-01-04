package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @program: realestate
 * @description: 不动产房屋建设状态查看实体
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-07-20 10:42
 **/
@Table(name = "BDC_FWJSZTCK")
public class BdcFwjsztckDO {
    @Id
    @ApiModelProperty(value = "外业查看id")
    private String wyckid;
    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;
    @ApiModelProperty(value = "房屋建设状态")
    private Integer fwjszt;
    @ApiModelProperty(value = "房屋结构")
    private Integer fwjg;
    @ApiModelProperty(value = "东")
    private String d;
    @ApiModelProperty(value = "西")
    private String x;
    @ApiModelProperty(value = "南")
    private String n;
    @ApiModelProperty(value = "北")
    private String b;
    @ApiModelProperty(value = "资料核实")
    private Integer zlhs;
    @ApiModelProperty(value = "调查人")
    private String dcr;
    @ApiModelProperty(value = "调查时间")
    private Date dcsj;
    @ApiModelProperty(value = "权籍审核人")
    private String qjshr;
    @ApiModelProperty(value = "权籍审核时间")
    private Date qjshsj;
    @ApiModelProperty(value = "备注")
    private String bz;

    public String getWyckid() {
        return wyckid;
    }

    public void setWyckid(String wyckid) {
        this.wyckid = wyckid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public Integer getFwjszt() {
        return fwjszt;
    }

    public void setFwjszt(Integer fwjszt) {
        this.fwjszt = fwjszt;
    }

    public Integer getFwjg() {
        return fwjg;
    }

    public void setFwjg(Integer fwjg) {
        this.fwjg = fwjg;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public Integer getZlhs() {
        return zlhs;
    }

    public void setZlhs(Integer zlhs) {
        this.zlhs = zlhs;
    }

    public String getDcr() {
        return dcr;
    }

    public void setDcr(String dcr) {
        this.dcr = dcr;
    }

    public Date getDcsj() {
        return dcsj;
    }

    public void setDcsj(Date dcsj) {
        this.dcsj = dcsj;
    }

    public String getQjshr() {
        return qjshr;
    }

    public void setQjshr(String qjshr) {
        this.qjshr = qjshr;
    }

    public Date getQjshsj() {
        return qjshsj;
    }

    public void setQjshsj(Date qjshsj) {
        this.qjshsj = qjshsj;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @Override
    public String toString() {
        return "BdcFwjsztckDO{" +
                "wyckid='" + wyckid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", fwjszt=" + fwjszt +
                ", fwjg=" + fwjg +
                ", d='" + d + '\'' +
                ", x='" + x + '\'' +
                ", n='" + n + '\'' +
                ", b='" + b + '\'' +
                ", zlhs=" + zlhs +
                ", dcr='" + dcr + '\'' +
                ", dcsj=" + dcsj +
                ", qjshr='" + qjshr + '\'' +
                ", qjshsj=" + qjshsj +
                ", bz='" + bz + '\'' +
                '}';
    }
}
