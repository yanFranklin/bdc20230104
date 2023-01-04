package cn.gtmap.realestate.common.core.domain;

import cn.gtmap.realestate.common.core.annotations.Zd;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/10/31
 * @description 不动产查封
 */
@Table(name = "BDC_CF")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz",defaultImpl = BdcCfDO.class)
@ApiModel(value = "BdcCfDO", description = "不动产查封信息")
public class BdcCfDO implements BdcQl {
    @Id
    @ApiModelProperty(value = "权利id")
    private String qlid;
    @ApiModelProperty(value = "查封机关")
    private String cfjg;
    @Zd(tableClass = BdcZdCflxDO.class)
    @ApiModelProperty(value = "查封类型")
    private Integer cflx;
    @ApiModelProperty(value = "查封文号")
    private String cfwh;
    @ApiModelProperty(value = "查封范围")
    private String cffw;
    @ApiModelProperty(value = "解封业务号")
    private String jfywh;
    @ApiModelProperty(value = "解封机关")
    private String jfjg;
    @ApiModelProperty(value = "解封文件")
    private String jfwj;
    @ApiModelProperty(value = "解封文号")
    private String jfwh;
    @ApiModelProperty(value = "解封时间",example = "2018-10-01 12:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date jfsj;
    @ApiModelProperty(value = "解封登记时间",example = "2018-10-01 12:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date jfdjsj;
    @ApiModelProperty(value = "解封登簿人")
    private String jfdbr;
    @ApiModelProperty(value = "查封起始时间",example = "2018-10-01 12:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date cfqssj;
    @ApiModelProperty(value = "查封结束时间",example = "2018-10-01 12:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date cfjssj;
    @ApiModelProperty(value = "执行申请人")
    private String zxsqr;
    @ApiModelProperty(value = "被执行人")
    private String bzxr;
    @ApiModelProperty(value = "查封原因")
    private String cfyy;
    @ApiModelProperty(value = "查封顺序")
    private Integer cfsx;
    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @ApiModelProperty(value = "项目ID")
    private String xmid;
    @ApiModelProperty(value = "共有情况")
    private String gyqk;
    @ApiModelProperty(value = "登记时间",example = "2018-10-01 12:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date djsj;
    @ApiModelProperty(value = "登记机构")
    private String djjg;
    @ApiModelProperty(value = "登簿人")
    private String dbr;
    @ApiModelProperty(value = "附记")
    private String fj;
    @Zd(tableClass = BdcZdQsztDO.class)
    @ApiModelProperty(value = "权属状态")
    private Integer qszt;
    @ApiModelProperty(value = "查封时间",example = "2018-10-01 12:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date cfsj;
    @ApiModelProperty(value = "查封文件")
    private String cfwj;
    @ApiModelProperty(value = "轮候查封期限")
    private String lhcfqx;
    @ApiModelProperty(value = "法院送达人")
    private String fysdr;
    @ApiModelProperty(value = "法院送达人联系方式")
    private String fysdrlxfs;
    @ApiModelProperty(value = "备注")
    private String bz;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "不动产单元编号")
    private String bdcdywybh;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @ApiModelProperty(value = "解封原因")
    private String jfyy;
    @ApiModelProperty(value = "解封送达人")
    private String jfsdr;
    @ApiModelProperty(value = "解封法院送达人联系电话")
    private String jffysdrlxdh;
    @ApiModelProperty(value = "查封编号")
    private String cfbh;

    public String getJfyy() {
        return jfyy;
    }

    public void setJfyy(String jfyy) {
        this.jfyy = jfyy;
    }

    public String getJfsdr() {
        return jfsdr;
    }

    public void setJfsdr(String jfsdr) {
        this.jfsdr = jfsdr;
    }

    public String getJffysdrlxdh() {
        return jffysdrlxdh;
    }

    public void setJffysdrlxdh(String jffysdrlxdh) {
        this.jffysdrlxdh = jffysdrlxdh;
    }

    public String getQlid() {
        return qlid;
    }

    public void setQlid(String qlid) {
        this.qlid = qlid;
    }

    public String getCfjg() {
        return cfjg;
    }

    public void setCfjg(String cfjg) {
        this.cfjg = cfjg;
    }

    public Integer getCflx() {
        return cflx;
    }

    public void setCflx(Integer cflx) {
        this.cflx = cflx;
    }

    public String getCfwh() {
        return cfwh;
    }

    public void setCfwh(String cfwh) {
        this.cfwh = cfwh;
    }

    public String getCffw() {
        return cffw;
    }

    public void setCffw(String cffw) {
        this.cffw = cffw;
    }

    public String getJfywh() {
        return jfywh;
    }

    public void setJfywh(String jfywh) {
        this.jfywh = jfywh;
    }

    public String getJfjg() {
        return jfjg;
    }

    public void setJfjg(String jfjg) {
        this.jfjg = jfjg;
    }

    public String getJfwj() {
        return jfwj;
    }

    public void setJfwj(String jfwj) {
        this.jfwj = jfwj;
    }

    public String getJfwh() {
        return jfwh;
    }

    public void setJfwh(String jfwh) {
        this.jfwh = jfwh;
    }

    public Date getJfsj() {
        return jfsj;
    }

    public void setJfsj(Date jfsj) {
        this.jfsj = jfsj;
    }

    public Date getJfdjsj() {
        return jfdjsj;
    }

    public void setJfdjsj(Date jfdjsj) {
        this.jfdjsj = jfdjsj;
    }

    public String getJfdbr() {
        return jfdbr;
    }

    public void setJfdbr(String jfdbr) {
        this.jfdbr = jfdbr;
    }

    public Date getCfqssj() {
        return cfqssj;
    }

    public void setCfqssj(Date cfqssj) {
        this.cfqssj = cfqssj;
    }

    public Date getCfjssj() {
        return cfjssj;
    }

    public void setCfjssj(Date cfjssj) {
        this.cfjssj = cfjssj;
    }

    public String getZxsqr() {
        return zxsqr;
    }

    public void setZxsqr(String zxsqr) {
        this.zxsqr = zxsqr;
    }

    public String getBzxr() {
        return bzxr;
    }

    public void setBzxr(String bzxr) {
        this.bzxr = bzxr;
    }

    public String getCfyy() {
        return cfyy;
    }

    public void setCfyy(String cfyy) {
        this.cfyy = cfyy;
    }

    public Integer getCfsx() {
        return cfsx;
    }

    public void setCfsx(Integer cfsx) {
        this.cfsx = cfsx;
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

    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
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

    public Date getCfsj() {
        return cfsj;
    }

    public void setCfsj(Date cfsj) {
        this.cfsj = cfsj;
    }

    public String getCfwj() {
        return cfwj;
    }

    public void setCfwj(String cfwj) {
        this.cfwj = cfwj;
    }

    public String getLhcfqx() {
        return lhcfqx;
    }

    public void setLhcfqx(String lhcfqx) {
        this.lhcfqx = lhcfqx;
    }

    public String getFysdr() {
        return fysdr;
    }

    public void setFysdr(String fysdr) {
        this.fysdr = fysdr;
    }

    public String getFysdrlxfs() {
        return fysdrlxfs;
    }

    public void setFysdrlxfs(String fysdrlxfs) {
        this.fysdrlxfs = fysdrlxfs;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
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

    public String getCfbh() {
        return cfbh;
    }

    public void setCfbh(String cfbh) {
        this.cfbh = cfbh;
    }

    @Override
    public String toString() {
        return "BdcCfDO{" +
                "qlid='" + qlid + '\'' +
                ", cfjg='" + cfjg + '\'' +
                ", cflx=" + cflx +
                ", cfwh='" + cfwh + '\'' +
                ", cffw='" + cffw + '\'' +
                ", jfywh='" + jfywh + '\'' +
                ", jfjg='" + jfjg + '\'' +
                ", jfwj='" + jfwj + '\'' +
                ", jfwh='" + jfwh + '\'' +
                ", jfsj=" + jfsj +
                ", jfdjsj=" + jfdjsj +
                ", jfdbr='" + jfdbr + '\'' +
                ", cfqssj=" + cfqssj +
                ", cfjssj=" + cfjssj +
                ", zxsqr='" + zxsqr + '\'' +
                ", bzxr='" + bzxr + '\'' +
                ", cfyy='" + cfyy + '\'' +
                ", cfsx=" + cfsx +
                ", slbh='" + slbh + '\'' +
                ", xmid='" + xmid + '\'' +
                ", gyqk='" + gyqk + '\'' +
                ", djsj=" + djsj +
                ", djjg='" + djjg + '\'' +
                ", dbr='" + dbr + '\'' +
                ", fj='" + fj + '\'' +
                ", qszt=" + qszt +
                ", cfsj=" + cfsj +
                ", cfwj='" + cfwj + '\'' +
                ", lhcfqx='" + lhcfqx + '\'' +
                ", fysdr='" + fysdr + '\'' +
                ", fysdrlxfs='" + fysdrlxfs + '\'' +
                ", bz='" + bz + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", bdcdywybh='" + bdcdywybh + '\'' +
                ", zl='" + zl + '\'' +
                ", jfyy='" + jfyy + '\'' +
                ", jfsdr='" + jfsdr + '\'' +
                ", jffysdrlxdh='" + jffysdrlxdh + '\'' +
                ", cfbh='" + cfbh + '\'' +
                '}';
    }
}
