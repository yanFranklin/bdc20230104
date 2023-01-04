package cn.gtmap.realestate.common.core.domain.accept;

import cn.gtmap.realestate.common.util.CommonConstantUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/10/31
 * @description 不动产受理收费信息
 */
@Table(name = "BDC_SL_SFXX")
@ApiModel(value = "BdcSlSfxxDO", description = "不动产受理收费信息")
public class BdcSlSfxxDO implements Serializable {
    private static final long serialVersionUID = 8976331572255826712L;
    @Id
    @ApiModelProperty(value = "收费信息ID")
    private String sfxxid;
    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;
    @ApiModelProperty(value = "项目ID")
    private String xmid;
    @ApiModelProperty(value = "收费时间")
    private Date sfsj;
    @ApiModelProperty(value = "金额单位")
    private String jedw;
    @ApiModelProperty(value = "合计")
    private Double hj;
    @ApiModelProperty(value = "备注")
    private String bz;
    @ApiModelProperty(value = "收费单初审人")
    private String sfdcsr;
    @ApiModelProperty(value = "收费单复审人")
    private String sfdfsr;
    @ApiModelProperty(value = "核收金额")
    private Double hsje;
    @ApiModelProperty(value = "收费单位名称")
    private String sfdwmc;
    @ApiModelProperty(value = "缴费人姓名")
    private String jfrxm;
    @ApiModelProperty(value = "收费人姓名")
    private String sfrxm;
    @ApiModelProperty(value = "收费人账号")
    private String sfrzh;
    @ApiModelProperty(value = "收费人开户银行")
    private String sfrkhyh;
    @ApiModelProperty(value = "收费单位代码")
    private String sfdwdm;
    @ApiModelProperty(value = "收费状态")
    private Integer sfzt;
    @ApiModelProperty(value = "收费状态操作人姓名")
    private String sfztczrxm;
    @ApiModelProperty(value = "收费状态操作时间")
    private Date sfztczsj;
    @ApiModelProperty(value = "发票号")
    private String fph;
    @ApiModelProperty(value = "财政条形码编号")
    private String cztxmbh;
    @ApiModelProperty(value = "付费方")
    private String fff;
    @ApiModelProperty(value = "权利人类别")
    private String qlrlb;
    @ApiModelProperty(value = "收费人开户银行网点代码")
    private String sfrkhyhwddm;
    @ApiModelProperty(value = "开票方式")
    private String kpfs;
    @ApiModelProperty(value = "开票人姓名")
    private String kprxm;
    @ApiModelProperty(value = "缴款方式")
    private String jkfs;
    @ApiModelProperty(value = "缴费书编码(缴费清单单号)")
    private String jfsbm;
    @ApiModelProperty(value = "缴费书二维码url")
    private String jfsewmurl;
    @ApiModelProperty(value = "义务人")
    private String ywr;
    @ApiModelProperty(value = "是否点击保存按钮")
    private Integer bczt;

    @ApiModelProperty(value = "银行缴库入库状态")
    private Integer yhjkrkzt;

    @ApiModelProperty(value = "退款单号")
    private String tkdh;

    @ApiModelProperty(value = "交易凭证号")
    private String jypzh;

    @ApiModelProperty(value = "终端号")
    private String zdh;

    @ApiModelProperty(value = "参考号")
    private String ckh;

    @ApiModelProperty(value = "商户代码")
    private String shdm;

    @ApiModelProperty(value = "是否低保")
    private Integer sfdb;
    @ApiModelProperty(value = "是否下岗再就业")
    private Integer sfxgzjy;
    @ApiModelProperty(value = "是否收登记费")
    private Integer sfsdjf;
    @ApiModelProperty(value = "是否收产权证工本费")
    private Integer sfscqzgbf;
    @ApiModelProperty(value = "是否是共有证工本费")
    private Integer sfsgyzgbf;
    @ApiModelProperty(value = "支票号")
    private String zph;
    @ApiModelProperty(value = "是否作废（1：作废）")
    private Integer sfzf;
    @ApiModelProperty(value = "退付原因")
    private String tfyy;
    @ApiModelProperty(value = "是否开票（1：已开票）")
    private Integer sfkp;
    @ApiModelProperty(value = "退付人姓名")
    private String tfrxm;
    @ApiModelProperty(value = "小微类型")
    private String xwlx;
    @ApiModelProperty(value = "是否月结（1：月结）")
    private Integer sfyj;

    @ApiModelProperty(value = "是否线上缴费")
    private Integer sfxsjf;

    @ApiModelProperty(value = "缴库入库时间")
    private Date jkrksj;
    @ApiModelProperty(value = "受理时间（冗余字段，用于银行月结查询）")
    private Date slsj;

    @ApiModelProperty(value = "非税收入发票号")
    private String fssrfph;
    @ApiModelProperty(value = "结算凭证发票号")
    private String jspzfph;
    @ApiModelProperty(value = "付款人开户银行")
    private String fkrkhyh;
    @ApiModelProperty(value = "付款人账号")
    private String fkrzh;
    @ApiModelProperty(value = "缴款日期")
    private Date jkrq;
    @ApiModelProperty(value = "缴款渠道")
    private String jkqd;
    @ApiModelProperty(value = "登簿时间（冗余字段，用于银行月结查询）")
    private Date dbsj;
    @ApiModelProperty(value = "获取发票号时间")
    private Date hqfphsj;
    @ApiModelProperty(value = "缴款码日期")
    private Date jkmsj;
    @ApiModelProperty(value = "减免原因")
    private String jmyy;
    @ApiModelProperty(value = "减免事由")
    private String jmsy;

    @ApiModelProperty(value = "合一支付url")
    private String hyzfurl;

    @ApiModelProperty(value = "合一支付订单id")
    private String hyzfddid;

    @ApiModelProperty(value = "缴费人电话")
    private String jfrdh;

    @ApiModelProperty(value = "是否小微企业")
    private Integer xwqy;

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "是否删除")
    private Integer sfsc;

    @ApiModelProperty(value = "小计总额")
    private Double xjze;
    @ApiModelProperty(value = "是否减免")
    private Integer sfjm;

    @ApiModelProperty(value = "是否一次支付")
    private Integer sfyczf;

    @ApiModelProperty(value = "合一支付缴费途径1.商业银行 2. 合一支付")
    private Integer hyzfjftj;

    @ApiModelProperty(value = "合一支付缴费类型 1.登记费2.税费统缴")
    private Integer hyzfjflx;

    public Integer getSfjm() {
        return sfjm;
    }

    public void setSfjm(Integer sfjm) {
        this.sfjm = sfjm;
    }

    public Double getXjze() {
        return xjze;
    }

    public void setXjze(Double xjze) {
        this.xjze = xjze;
    }

    public Integer getXwqy() {
        return xwqy;
    }

    public void setXwqy(Integer xwqy) {
        this.xwqy = xwqy;
    }

    public String getXwlx() {
        return xwlx;
    }

    public void setXwlx(String xwlx) {
        this.xwlx = xwlx;
    }

    public String getKprxm() {
        return kprxm;
    }

    public void setKprxm(String kprxm) {
        this.kprxm = kprxm;
    }

    public String getSfxxid() {
        return sfxxid;
    }

    public void setSfxxid(String sfxxid) {
        this.sfxxid = sfxxid;
    }

    public Integer getYhjkrkzt() {
        return yhjkrkzt;
    }

    public void setYhjkrkzt(Integer yhjkrkzt) {
        this.yhjkrkzt = yhjkrkzt;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public Date getSfsj() {
        return sfsj;
    }

    public void setSfsj(Date sfsj) {
        this.sfsj = sfsj;
    }

    public String getJedw() {
        return jedw;
    }

    public void setJedw(String jedw) {
        this.jedw = jedw;
    }

    public Double getHj() {
        return hj;
    }

    public void setHj(Double hj) {
        this.hj = hj;
    }

    public String getSfdcsr() {
        return sfdcsr;
    }

    public void setSfdcsr(String sfdcsr) {
        this.sfdcsr = sfdcsr;
    }

    public String getSfdfsr() {
        return sfdfsr;
    }

    public void setSfdfsr(String sfdfsr) {
        this.sfdfsr = sfdfsr;
    }

    public Double getHsje() {
        return hsje;
    }

    public void setHsje(Double hsje) {
        this.hsje = hsje;
    }

    public String getSfdwmc() {
        return sfdwmc;
    }

    public void setSfdwmc(String sfdwmc) {
        this.sfdwmc = sfdwmc;
    }

    public String getJfrxm() {
        return jfrxm;
    }

    public void setJfrxm(String jfrxm) {
        this.jfrxm = jfrxm;
    }

    public String getSfrxm() {
        return sfrxm;
    }

    public void setSfrxm(String sfrxm) {
        this.sfrxm = sfrxm;
    }

    public String getSfrzh() {
        return sfrzh;
    }

    public Integer getSfxsjf() {
        return sfxsjf;
    }

    public void setSfxsjf(Integer sfxsjf) {
        this.sfxsjf = sfxsjf;
    }

    public void setSfrzh(String sfrzh) {
        this.sfrzh = sfrzh;
    }

    public String getSfrkhyh() {
        return sfrkhyh;
    }

    public void setSfrkhyh(String sfrkhyh) {
        this.sfrkhyh = sfrkhyh;
    }

    public String getSfdwdm() {
        return sfdwdm;
    }

    public void setSfdwdm(String sfdwdm) {
        this.sfdwdm = sfdwdm;
    }

    public Integer getSfzt() {
        return sfzt;
    }

    public void setSfzt(Integer sfzt) {
        this.sfzt = sfzt;
    }

    public String getSfztczrxm() {
        return sfztczrxm;
    }

    public void setSfztczrxm(String sfztczrxm) {
        this.sfztczrxm = sfztczrxm;
    }

    public Date getSfztczsj() {
        return sfztczsj;
    }

    public void setSfztczsj(Date sfztczsj) {
        this.sfztczsj = sfztczsj;
    }

    public String getFph() {
        return fph;
    }

    public void setFph(String fph) {
        this.fph = fph;
    }

    public void setFph(String fph, String fplb) {
        if (StringUtils.isBlank(fplb)) {
            this.setFph(fph);
        }
        if (StringUtils.equals(fplb, CommonConstantUtils.FPLB_FSSR)) {
            this.setFssrfph(fph);
        }
        if (StringUtils.equals(fplb, CommonConstantUtils.FPLB_JSPZ)) {
            this.setJspzfph(fph);
        }
    }

    public String getCztxmbh() {
        return cztxmbh;
    }

    public void setCztxmbh(String cztxmbh) {
        this.cztxmbh = cztxmbh;
    }

    public String getFff() {
        return fff;
    }

    public void setFff(String fff) {
        this.fff = fff;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getSfrkhyhwddm() {
        return sfrkhyhwddm;
    }

    public void setSfrkhyhwddm(String sfrkhyhwddm) {
        this.sfrkhyhwddm = sfrkhyhwddm;
    }

    public String getKpfs() {
        return kpfs;
    }

    public void setKpfs(String kpfs) {
        this.kpfs = kpfs;
    }

    public String getJkfs() {
        return jkfs;
    }

    public void setJkfs(String jkfs) {
        this.jkfs = jkfs;
    }

    public String getQlrlb() {
        return qlrlb;
    }

    public void setQlrlb(String qlrlb) {
        this.qlrlb = qlrlb;
    }

    public String getJfsbm() {
        return jfsbm;
    }

    public void setJfsbm(String jfsbm) {
        this.jfsbm = jfsbm;
    }

    public String getJfsewmurl() {
        return jfsewmurl;
    }

    public void setJfsewmurl(String jfsewmurl) {
        this.jfsewmurl = jfsewmurl;
    }

    public String getYwr() {
        return ywr;
    }

    public void setYwr(String ywr) {
        this.ywr = ywr;
    }

    public Integer getBczt() {
        return bczt;
    }

    public void setBczt(Integer bczt) {
        this.bczt = bczt;
    }

    public String getTkdh() {
        return tkdh;
    }

    public void setTkdh(String tkdh) {
        this.tkdh = tkdh;
    }

    public String getJypzh() {
        return jypzh;
    }

    public void setJypzh(String jypzh) {
        this.jypzh = jypzh;
    }

    public String getZdh() {
        return zdh;
    }

    public void setZdh(String zdh) {
        this.zdh = zdh;
    }

    public String getCkh() {
        return ckh;
    }

    public void setCkh(String ckh) {
        this.ckh = ckh;
    }

    public String getShdm() {
        return shdm;
    }

    public void setShdm(String shdm) {
        this.shdm = shdm;
    }

    public Integer getSfdb() {
        return sfdb;
    }

    public void setSfdb(Integer sfdb) {
        this.sfdb = sfdb;
    }

    public Integer getSfxgzjy() {
        return sfxgzjy;
    }

    public void setSfxgzjy(Integer sfxgzjy) {
        this.sfxgzjy = sfxgzjy;
    }

    public Integer getSfsdjf() {
        return sfsdjf;
    }

    public void setSfsdjf(Integer sfsdjf) {
        this.sfsdjf = sfsdjf;
    }

    public Integer getSfscqzgbf() {
        return sfscqzgbf;
    }

    public void setSfscqzgbf(Integer sfscqzgbf) {
        this.sfscqzgbf = sfscqzgbf;
    }

    public Integer getSfsgyzgbf() {
        return sfsgyzgbf;
    }

    public void setSfsgyzgbf(Integer sfsgyzgbf) {
        this.sfsgyzgbf = sfsgyzgbf;
    }

    public String getZph() {
        return zph;
    }

    public void setZph(String zph) {
        this.zph = zph;
    }

    public Integer getSfzf() {
        return sfzf;
    }

    public void setSfzf(Integer sfzf) {
        this.sfzf = sfzf;
    }

    public String getTfyy() {
        return tfyy;
    }

    public void setTfyy(String tfyy) {
        this.tfyy = tfyy;
    }

    public Integer getSfkp() {
        return sfkp;
    }

    public void setSfkp(Integer sfkp) {
        this.sfkp = sfkp;
    }

    public String getTfrxm() {
        return tfrxm;
    }

    public void setTfrxm(String tfrxm) {
        this.tfrxm = tfrxm;
    }

    public Integer getSfyj() {
        return sfyj;
    }

    public void setSfyj(Integer sfyj) {
        this.sfyj = sfyj;
    }

    public Date getJkrksj() {
        return jkrksj;
    }

    public void setJkrksj(Date jkrksj) {
        this.jkrksj = jkrksj;
    }

    public Date getSlsj() {
        return slsj;
    }

    public void setSlsj(Date slsj) {
        this.slsj = slsj;
    }

    public String getFssrfph() {
        return fssrfph;
    }

    public void setFssrfph(String fssrfph) {
        this.fssrfph = fssrfph;
    }

    public String getJspzfph() {
        return jspzfph;
    }

    public void setJspzfph(String jspzfph) {
        this.jspzfph = jspzfph;
    }

    public String getFkrkhyh() {
        return fkrkhyh;
    }

    public void setFkrkhyh(String fkrkhyh) {
        this.fkrkhyh = fkrkhyh;
    }

    public String getFkrzh() {
        return fkrzh;
    }

    public void setFkrzh(String fkrzh) {
        this.fkrzh = fkrzh;
    }

    public Date getJkrq() {
        return jkrq;
    }

    public void setJkrq(Date jkrq) {
        this.jkrq = jkrq;
    }

    public String getJkqd() {
        return jkqd;
    }

    public void setJkqd(String jkqd) {
        this.jkqd = jkqd;
    }

    public Date getDbsj() {
        return dbsj;
    }

    public void setDbsj(Date dbsj) {
        this.dbsj = dbsj;
    }

    public Date getHqfphsj() {
        return hqfphsj;
    }

    public void setHqfphsj(Date hqfphsj) {
        this.hqfphsj = hqfphsj;
    }

    public Date getJkmsj() {
        return jkmsj;
    }

    public void setJkmsj(Date jkmsj) {
        this.jkmsj = jkmsj;
    }

    public String getJmyy() {
        return jmyy;
    }

    public void setJmyy(String jmyy) {
        this.jmyy = jmyy;
    }

    public String getJmsy() {
        return jmsy;
    }

    public void setJmsy(String jmsy) {
        this.jmsy = jmsy;
    }

    public String getHyzfurl() {
        return hyzfurl;
    }

    public void setHyzfurl(String hyzfurl) {
        this.hyzfurl = hyzfurl;
    }

    public String getHyzfddid() {
        return hyzfddid;
    }

    public void setHyzfddid(String hyzfddid) {
        this.hyzfddid = hyzfddid;
    }

    public String getJfrdh() {
        return jfrdh;
    }

    public void setJfrdh(String jfrdh) {
        this.jfrdh = jfrdh;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public Integer getSfsc() {
        return sfsc;
    }

    public void setSfsc(Integer sfsc) {
        this.sfsc = sfsc;
    }

    public Integer getSfyczf() {
        return sfyczf;
    }

    public void setSfyczf(Integer sfyczf) {
        this.sfyczf = sfyczf;
    }

    public Integer getHyzfjftj() {
        return hyzfjftj;
    }

    public void setHyzfjftj(Integer hyzfjftj) {
        this.hyzfjftj = hyzfjftj;
    }

    public Integer getHyzfjflx() {
        return hyzfjflx;
    }

    public void setHyzfjflx(Integer hyzfjflx) {
        this.hyzfjflx = hyzfjflx;
    }

    @Override
    public String toString() {
        return "BdcSlSfxxDO{" +
                "sfxxid='" + sfxxid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", sfsj=" + sfsj +
                ", jedw='" + jedw + '\'' +
                ", hj=" + hj +
                ", bz='" + bz + '\'' +
                ", sfdcsr='" + sfdcsr + '\'' +
                ", sfdfsr='" + sfdfsr + '\'' +
                ", hsje=" + hsje +
                ", sfdwmc='" + sfdwmc + '\'' +
                ", jfrxm='" + jfrxm + '\'' +
                ", sfrxm='" + sfrxm + '\'' +
                ", sfrzh='" + sfrzh + '\'' +
                ", sfrkhyh='" + sfrkhyh + '\'' +
                ", sfdwdm='" + sfdwdm + '\'' +
                ", sfzt=" + sfzt +
                ", sfztczrxm='" + sfztczrxm + '\'' +
                ", sfztczsj=" + sfztczsj +
                ", fph='" + fph + '\'' +
                ", cztxmbh='" + cztxmbh + '\'' +
                ", fff='" + fff + '\'' +
                ", qlrlb='" + qlrlb + '\'' +
                ", sfrkhyhwddm='" + sfrkhyhwddm + '\'' +
                ", kpfs='" + kpfs + '\'' +
                ", kprxm='" + kprxm + '\'' +
                ", jkfs='" + jkfs + '\'' +
                ", jfsbm='" + jfsbm + '\'' +
                ", jfsewmurl='" + jfsewmurl + '\'' +
                ", ywr='" + ywr + '\'' +
                ", bczt=" + bczt +
                ", yhjkrkzt=" + yhjkrkzt +
                ", tkdh='" + tkdh + '\'' +
                ", jypzh='" + jypzh + '\'' +
                ", zdh='" + zdh + '\'' +
                ", ckh='" + ckh + '\'' +
                ", shdm='" + shdm + '\'' +
                ", sfdb=" + sfdb +
                ", sfxgzjy=" + sfxgzjy +
                ", sfsdjf=" + sfsdjf +
                ", sfscqzgbf=" + sfscqzgbf +
                ", sfsgyzgbf=" + sfsgyzgbf +
                ", zph='" + zph + '\'' +
                ", sfzf=" + sfzf +
                ", tfyy='" + tfyy + '\'' +
                ", sfkp=" + sfkp +
                ", tfrxm='" + tfrxm + '\'' +
                ", xwlx='" + xwlx + '\'' +
                ", sfyj=" + sfyj +
                ", sfxsjf=" + sfxsjf +
                ", jkrksj=" + jkrksj +
                ", slsj=" + slsj +
                ", fssrfph='" + fssrfph + '\'' +
                ", jspzfph='" + jspzfph + '\'' +
                ", fkrkhyh='" + fkrkhyh + '\'' +
                ", fkrzh='" + fkrzh + '\'' +
                ", jkrq=" + jkrq +
                ", jkqd='" + jkqd + '\'' +
                ", dbsj=" + dbsj +
                ", hqfphsj=" + hqfphsj +
                ", jkmsj=" + jkmsj +
                ", jmyy='" + jmyy + '\'' +
                ", jmsy='" + jmsy + '\'' +
                ", hyzfurl='" + hyzfurl + '\'' +
                ", hyzfddid='" + hyzfddid + '\'' +
                ", jfrdh='" + jfrdh + '\'' +
                ", xwqy=" + xwqy +
                ", slbh='" + slbh + '\'' +
                ", sfsc=" + sfsc +
                ", xjze=" + xjze +
                ", sfjm=" + sfjm +
                ", sfyczf=" + sfyczf +
                ", hyzfjftj=" + hyzfjftj +
                ", hyzfjflx=" + hyzfjflx +
                '}';
    }
}
