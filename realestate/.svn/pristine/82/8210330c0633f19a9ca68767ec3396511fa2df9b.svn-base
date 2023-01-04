package cn.gtmap.realestate.common.core.domain.certificate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/12/4
 * @description 不动产印制号
 */
@Table(name = "bdc_yzh")
@ApiModel(value = "BdcYzhDO", description = "不动产印制号对象")
public class BdcYzhDO {
    @Id
    @ApiModelProperty(value = "主键")
    String yzhid;
    @ApiModelProperty(value = "年份")
    String nf;
    @ApiModelProperty(value = "区县代码")
    String qxdm;
    @ApiModelProperty(value = "证书类型")
    Integer zslx;
    @ApiModelProperty(value = "权证印刷序列号")
    String qzysxlh;
    @ApiModelProperty(value = "使用情况")
    Integer syqk;
    @ApiModelProperty(value = "领取人")
    String lqr;
    @ApiModelProperty(value = "领取人ID")
    String lqrid;
    @ApiModelProperty(value = "领取部门")
    String lqbm;
    @ApiModelProperty(value = "领取部门ID")
    String lqbmid;
    @ApiModelProperty(value = "创建人")
    String cjr;
    @ApiModelProperty(value = "创建人ID")
    String cjrid;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间",example = "2018-10-01 12:18:48")
    Date cjsj;
    @ApiModelProperty(value = "证书ID")
    String zsid;

    @ApiModelProperty(value = "备注")
    String bz;

    @ApiModelProperty(value = "生产厂商")
    String sccs;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "提取时间")
    Date tqsj;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "领取时间")
    Date lqsj;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "使用时间")
    Date sysj;

    /**
     * add by cyc at 2019-07-19
     */
//    @ApiModelProperty(value = "使用部门")
//    String sybmmc;

//    @ApiModelProperty(value = "使用人")
//    String syr;

//    @ApiModelProperty(value = "受理编号")
//    String slbh;

    public String getYzhid() {
        return yzhid;
    }

    public void setYzhid(String yzhid) {
        this.yzhid = yzhid;
    }

    public String getNf() {
        return nf;
    }

    public void setNf(String nf) {
        this.nf = nf;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public Integer getZslx() {
        return zslx;
    }

    public void setZslx(Integer zslx) {
        this.zslx = zslx;
    }

    public String getQzysxlh() {
        return qzysxlh;
    }

    public void setQzysxlh(String qzysxlh) {
        this.qzysxlh = qzysxlh;
    }

    public Integer getSyqk() {
        return syqk;
    }

    public void setSyqk(Integer syqk) {
        this.syqk = syqk;
    }

    public String getLqr() {
        return lqr;
    }

    public void setLqr(String lqr) {
        this.lqr = lqr;
    }

    public String getLqrid() {
        return lqrid;
    }

    public void setLqrid(String lqrid) {
        this.lqrid = lqrid;
    }

    public String getLqbm() {
        return lqbm;
    }

    public void setLqbm(String lqbm) {
        this.lqbm = lqbm;
    }

    public String getLqbmid() {
        return lqbmid;
    }

    public void setLqbmid(String lqbmid) {
        this.lqbmid = lqbmid;
    }

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    public String getCjrid() {
        return cjrid;
    }

    public void setCjrid(String cjrid) {
        this.cjrid = cjrid;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public String getZsid() {
        return zsid;
    }

    public void setZsid(String zsid) {
        this.zsid = zsid;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getSccs() {
        return sccs;
    }

    public void setSccs(String sccs) {
        this.sccs = sccs;
    }

    public Date getTqsj() {
        return tqsj;
    }

    public void setTqsj(Date tqsj) {
        this.tqsj = tqsj;
    }

    public Date getLqsj() {
        return lqsj;
    }

    public void setLqsj(Date lqsj) {
        this.lqsj = lqsj;
    }

    public Date getSysj() {
        return sysj;
    }

    public void setSysj(Date sysj) {
        this.sysj = sysj;
    }

    @Override
    public String toString() {
        return "BdcYzhDO{" +
                "yzhid='" + yzhid + '\'' +
                ", nf='" + nf + '\'' +
                ", qxdm='" + qxdm + '\'' +
                ", zslx=" + zslx +
                ", qzysxlh='" + qzysxlh + '\'' +
                ", syqk=" + syqk +
                ", lqr='" + lqr + '\'' +
                ", lqrid='" + lqrid + '\'' +
                ", lqbm='" + lqbm + '\'' +
                ", lqbmid='" + lqbmid + '\'' +
                ", cjr='" + cjr + '\'' +
                ", cjrid='" + cjrid + '\'' +
                ", cjsj=" + cjsj +
                ", zsid='" + zsid + '\'' +
                ", bz='" + bz + '\'' +
                ", sccs='" + sccs + '\'' +
                ", tqsj=" + tqsj +
                ", lqsj=" + lqsj +
                ", sysj=" + sysj +
                '}';
    }
}
