package cn.gtmap.realestate.common.core.domain.inquiry;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @className: BdcZqDjDO
 * @description:  征迁冻结表
 * @author: <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @date: 2020/12/16
 **/
@Api(value = "BdcZqDjDO", description = "征迁冻结表")
@Table(name = "BDC_ZQ_DJ")
public class BdcZqDjDO {

    @Id
    @ApiModelProperty(value = "冻结信息ID")
    private String djxxid;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "不动产单元唯一编号")
    private String bdcdywybh;

    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "权利类型")
    private Integer qllx;

    @ApiModelProperty(value = "登记类型")
    private Integer djlx;

    @ApiModelProperty(value = "不动产类型")
    private Integer bdclx;

    @ApiModelProperty(value = "权利人名称")
    private String qlrmc;

    @ApiModelProperty(value = "权利人证件号")
    private String qlrzjh;

    @ApiModelProperty(value = "联系电话")
    private String lxdh;

    @ApiModelProperty(value = "建筑面积")
    private Double jzmj;

    @ApiModelProperty(value = "宗地宗海面积")
    private Double zdzhmj;

    @ApiModelProperty(value = "冻结原因")
    private String djyy;

    @ApiModelProperty(value = "冻结申请人")
    private String djsqr;

    @ApiModelProperty(value = "冻结申请人ID")
    private String djsqrid;

    @ApiModelProperty(value = "冻结申请时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date djsqsj;

    @ApiModelProperty(value = "冻结开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date djkssj;

    @ApiModelProperty(value = "冻结结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date djjssj;

    @ApiModelProperty(value = "冻结文号")
    private String djwh;

    @ApiModelProperty(value = "解冻原因")
    private String jdyy;

    @ApiModelProperty(value = "解冻人")
    private String jdr;

    @ApiModelProperty(value = "解冻人ID")
    private String jdrid;

    @ApiModelProperty(value = "解冻时间")
    private Date jdsj;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "冻结状态")
    private Integer djzt;


    public Date getDjkssj() {
        return djkssj;
    }

    public void setDjkssj(Date djkssj) {
        this.djkssj = djkssj;
    }

    public Date getDjjssj() {
        return djjssj;
    }

    public void setDjjssj(Date djjssj) {
        this.djjssj = djjssj;
    }

    public String getBdcdywybh() {
        return bdcdywybh;
    }

    public void setBdcdywybh(String bdcdywybh) {
        this.bdcdywybh = bdcdywybh;
    }

    public String getDjxxid() {
        return djxxid;
    }

    public void setDjxxid(String djxxid) {
        this.djxxid = djxxid;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public Integer getDjlx() {
        return djlx;
    }

    public void setDjlx(Integer djlx) {
        this.djlx = djlx;
    }

    public Integer getBdclx() {
        return bdclx;
    }

    public void setBdclx(Integer bdclx) {
        this.bdclx = bdclx;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public Double getJzmj() {
        return jzmj;
    }

    public void setJzmj(Double jzmj) {
        this.jzmj = jzmj;
    }

    public Double getZdzhmj() {
        return zdzhmj;
    }

    public void setZdzhmj(Double zdzhmj) {
        this.zdzhmj = zdzhmj;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public String getDjsqr() {
        return djsqr;
    }

    public void setDjsqr(String djsqr) {
        this.djsqr = djsqr;
    }

    public String getDjsqrid() {
        return djsqrid;
    }

    public void setDjsqrid(String djsqrid) {
        this.djsqrid = djsqrid;
    }

    public Date getDjsqsj() {
        return djsqsj;
    }

    public void setDjsqsj(Date djsqsj) {
        this.djsqsj = djsqsj;
    }

    public String getDjwh() {
        return djwh;
    }

    public void setDjwh(String djwh) {
        this.djwh = djwh;
    }

    public String getJdyy() {
        return jdyy;
    }

    public void setJdyy(String jdyy) {
        this.jdyy = jdyy;
    }

    public String getJdr() {
        return jdr;
    }

    public void setJdr(String jdr) {
        this.jdr = jdr;
    }

    public String getJdrid() {
        return jdrid;
    }

    public void setJdrid(String jdrid) {
        this.jdrid = jdrid;
    }

    public Date getJdsj() {
        return jdsj;
    }

    public void setJdsj(Date jdsj) {
        this.jdsj = jdsj;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Integer getDjzt() {
        return djzt;
    }

    public void setDjzt(Integer djzt) {
        this.djzt = djzt;
    }

    @Override
    public String toString() {
        return "BdcZqDjDO{" +
                "djxxid='" + djxxid + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", bdcdywybh='" + bdcdywybh + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", zl='" + zl + '\'' +
                ", qllx=" + qllx +
                ", djlx=" + djlx +
                ", bdclx=" + bdclx +
                ", qlrmc='" + qlrmc + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", lxdh='" + lxdh + '\'' +
                ", jzmj=" + jzmj +
                ", zdzhmj=" + zdzhmj +
                ", djyy='" + djyy + '\'' +
                ", djsqr='" + djsqr + '\'' +
                ", djsqrid='" + djsqrid + '\'' +
                ", djsqsj=" + djsqsj +
                ", djkssj=" + djkssj +
                ", djjssj=" + djjssj +
                ", djwh='" + djwh + '\'' +
                ", jdyy='" + jdyy + '\'' +
                ", jdr='" + jdr + '\'' +
                ", jdrid='" + jdrid + '\'' +
                ", jdsj=" + jdsj +
                ", bz='" + bz + '\'' +
                ", djzt=" + djzt +
                '}';
    }
}
