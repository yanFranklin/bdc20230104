package cn.gtmap.realestate.common.core.domain;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2021/1/8
 * @description 居住权
 */
@Table(
        name = "BDC_JZQ"
)
@ApiModel(value = "BdcJzqDO",description = "居住权")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz", defaultImpl = BdcJzqDO.class)
public class BdcJzqDO implements BdcQl{

    @Id
    @ApiModelProperty(value = "权利id")
    private String qlid;

    @ApiModelProperty(value = "登记类型")
    private Integer djlx;

    @ApiModelProperty(value = "登记原因")
    private String djyy;

    @ApiModelProperty(value = "不动产单元唯一编号")
    private String bdcdywybh;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "居住范围")
    private String jzfw;

    @ApiModelProperty(value = "居住条件和要求")
    private String jztjhyq;

    @ApiModelProperty(value = "权利类型")
    private Integer qllx;

    @ApiModelProperty(value = "房屋所有权人")
    private String fwsyqr;

    @ApiModelProperty(value = "居住权开始时间")
    private Date jzqkssj;

    @ApiModelProperty(value = "居住权结束时间")
    private Date jzqjssj;

    @ApiModelProperty(value = "注销居住权业务号")
    private String zxjzqywh;

    @ApiModelProperty(value = "注销居住权原因")
    private String zxjzqyy;

    @ApiModelProperty(value = "注销居住权登记时间")
    private Date zxjzqdjsj;

    @ApiModelProperty(value = "注销居住权登簿人")
    private String zxjzqdbr;

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "登记机构")
    private String djjg;

    @ApiModelProperty(value = "登记时间", example = "2018-10-01 12:18:48")
    private Date djsj;

    @ApiModelProperty(value = "登簿人")
    private String dbr;

    @ApiModelProperty(value = "附记")
    private String fj;

    @ApiModelProperty(value = "权属状态")
    private Integer qszt;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "共有情况")
    private String gyqk;

    @ApiModelProperty(value = "终生居住")
    private Integer zsjz;

    @ApiModelProperty(value = "是否有偿")
    private Integer sfyc;

    @ApiModelProperty(value = "是否同意设立居住权")
    private Integer sftysljzq;

    @ApiModelProperty(value = "设立方式")
    private Integer slfs;

    public Integer getSlfs() {
        return slfs;
    }

    public void setSlfs(Integer slfs) {
        this.slfs = slfs;
    }

    public String getQlid() {
        return qlid;
    }

    public void setQlid(String qlid) {
        this.qlid = qlid;
    }

    public Integer getDjlx() {
        return djlx;
    }

    public void setDjlx(Integer djlx) {
        this.djlx = djlx;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public String getBdcdywybh() {
        return bdcdywybh;
    }

    public void setBdcdywybh(String bdcdywybh) {
        this.bdcdywybh = bdcdywybh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getJzfw() {
        return jzfw;
    }

    public void setJzfw(String jzfw) {
        this.jzfw = jzfw;
    }

    public String getJztjhyq() {
        return jztjhyq;
    }

    public void setJztjhyq(String jztjhyq) {
        this.jztjhyq = jztjhyq;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public String getFwsyqr() {
        return fwsyqr;
    }

    public void setFwsyqr(String fwsyqr) {
        this.fwsyqr = fwsyqr;
    }

    public Date getJzqkssj() {
        return jzqkssj;
    }

    public void setJzqkssj(Date jzqkssj) {
        this.jzqkssj = jzqkssj;
    }

    public Date getJzqjssj() {
        return jzqjssj;
    }

    public void setJzqjssj(Date jzqjssj) {
        this.jzqjssj = jzqjssj;
    }

    public String getZxjzqywh() {
        return zxjzqywh;
    }

    public void setZxjzqywh(String zxjzqywh) {
        this.zxjzqywh = zxjzqywh;
    }

    public String getZxjzqyy() {
        return zxjzqyy;
    }

    public void setZxjzqyy(String zxjzqyy) {
        this.zxjzqyy = zxjzqyy;
    }

    public Date getZxjzqdjsj() {
        return zxjzqdjsj;
    }

    public void setZxjzqdjsj(Date zxjzqdjsj) {
        this.zxjzqdjsj = zxjzqdjsj;
    }

    public String getZxjzqdbr() {
        return zxjzqdbr;
    }

    public void setZxjzqdbr(String zxjzqdbr) {
        this.zxjzqdbr = zxjzqdbr;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public String getDbr() {
        return dbr;
    }

    public void setDbr(String dbr) {
        this.dbr = dbr;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public Integer getQszt() {
        return qszt;
    }

    public void setQszt(Integer qszt) {
        this.qszt = qszt;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    @Override
    public String getGyqk() {
        return gyqk;
    }

    @Override
    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    public Integer getZsjz() {
        return zsjz;
    }

    public void setZsjz(Integer zsjz) {
        this.zsjz = zsjz;
    }

    public Integer getSfyc() {
        return sfyc;
    }

    public void setSfyc(Integer sfyc) {
        this.sfyc = sfyc;
    }

    public Integer getSftysljzq() {
        return sftysljzq;
    }

    public void setSftysljzq(Integer sftysljzq) {
        this.sftysljzq = sftysljzq;
    }
}
