package cn.gtmap.realestate.exchange.core.domain.yzw;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/11/26
 * @description 共享一张网推送记录信息
 */
@Table(name = "GX_YZW_TSJL")
public class GxYzwTsjlDO {

    @Id
    @ApiModelProperty(value = "推送信息ID")
    private String tsxxid;

    @ApiModelProperty(value = "一张网编号")
    private String yzwbh;

    @ApiModelProperty(value = "推送时间")
    private Date tssj;

    @ApiModelProperty(value = "业务受理编号")
    private String ywslbh;

    @ApiModelProperty(value = "业务ID")
    private String ywid;

    @ApiModelProperty(value = "推送状态")
    private Integer tszt;

    @ApiModelProperty(value = "验证状态")
    private Integer yzzt;

    @ApiModelProperty(value = "节点名称")
    private String jdmc;

    @ApiModelProperty(value = "推送人姓名")
    private String tsrxm;

    @ApiModelProperty(value = "失败类型")
    private Integer sblx;

    @ApiModelProperty(value = "失败异常信息")
    private String sbycxx;

    public String getTsxxid() {
        return tsxxid;
    }

    public void setTsxxid(String tsxxid) {
        this.tsxxid = tsxxid;
    }

    public String getYzwbh() {
        return yzwbh;
    }

    public void setYzwbh(String yzwbh) {
        this.yzwbh = yzwbh;
    }

    public Date getTssj() {
        return tssj;
    }

    public void setTssj(Date tssj) {
        this.tssj = tssj;
    }

    public String getYwslbh() {
        return ywslbh;
    }

    public void setYwslbh(String ywslbh) {
        this.ywslbh = ywslbh;
    }

    public String getYwid() {
        return ywid;
    }

    public void setYwid(String ywid) {
        this.ywid = ywid;
    }

    public Integer getTszt() {
        return tszt;
    }

    public void setTszt(Integer tszt) {
        this.tszt = tszt;
    }

    public Integer getYzzt() {
        return yzzt;
    }

    public void setYzzt(Integer yzzt) {
        this.yzzt = yzzt;
    }

    public String getJdmc() {
        return jdmc;
    }

    public void setJdmc(String jdmc) {
        this.jdmc = jdmc;
    }

    public String getTsrxm() {
        return tsrxm;
    }

    public void setTsrxm(String tsrxm) {
        this.tsrxm = tsrxm;
    }

    public Integer getSblx() {
        return sblx;
    }

    public void setSblx(Integer sblx) {
        this.sblx = sblx;
    }

    public String getSbycxx() {
        return sbycxx;
    }

    public void setSbycxx(String sbycxx) {
        this.sbycxx = sbycxx;
    }
}
