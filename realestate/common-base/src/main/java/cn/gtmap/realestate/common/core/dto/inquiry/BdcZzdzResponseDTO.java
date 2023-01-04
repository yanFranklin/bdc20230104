package cn.gtmap.realestate.common.core.dto.inquiry;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2019/10/24
 * @description 自助打证responseDTO
 */
public class BdcZzdzResponseDTO {
    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "证书证明ID")
    private String zsid;

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    @ApiModelProperty(value = "权利类型")
    private Integer qllx;

    @ApiModelProperty(value = "权利类型名称")
    private String qllxmc;

    @ApiModelProperty(value = "权利性质")
    private String qlxz;

    @ApiModelProperty(value = "登记机构")
    private String djjg;

    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;

    @ApiModelProperty(value = "原土地地证号")
    private String ytdzh;

    @ApiModelProperty(value = "原不动产单元号")
    private String ybdcdyh;

    @ApiModelProperty(value = "原产权证号")
    private String ycqzh;

    @ApiModelProperty(value = "证书类型")
    private Integer zslx;

    @ApiModelProperty(value = "证书类型名称")
    private String zslxmc;

    @ApiModelProperty(value = "区县代码")
    private String qxdm;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "权属状态")
    private Integer qszt;

    @ApiModelProperty(value = "权属状态名称")
    private String qsztmc;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "权利人名称")
    private String qlrmc;

    @ApiModelProperty(value = "权利人证件号")
    private String qlrzjh;

    @ApiModelProperty(value = "义务人名称")
    private String ywrmc;

    @ApiModelProperty(value = "义务人证件号")
    private String ywrzjh;

    @ApiModelProperty(value = "幢号")
    private String zh;

    @ApiModelProperty(value = "房间号")
    private String fjh;

    @ApiModelProperty(value = "缮证人")
    private String szr;

    @ApiModelProperty(value = "登记原因")
    private String djyy;

    @ApiModelProperty(value = "登记时间（缮证时间）")
    private Date djsj;

    @ApiModelProperty(value = "缮证时间")
    private Date szsj;

    @ApiModelProperty(value = "不动产类型")
    private Integer bdclx;

    @ApiModelProperty(value = "不动产类型名称")
    private String bdclxmc;

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "房屋编号")
    private String fwbh;

    @ApiModelProperty(value = "证号流水号")
    private String zhlsh;

    @ApiModelProperty(value = "规划用途")
    private Integer ghyt;

    @ApiModelProperty(value = "规划用途名称")
    private String ghytmc;

    @ApiModelProperty(value = "原系统产权证号")
    private String yxtcqzh;

    @ApiModelProperty(value = "附记")
    private String fj;

    @ApiModelProperty(value = "证明权利或事项")
    private String zmqlsx;

    @ApiModelProperty(value = "共有情况")
    private String gyqk;

    @ApiModelProperty(value = "权利其他状况")
    private String qlqtzk;

    @ApiModelProperty(value = "面积")
    private String mj;

    @ApiModelProperty(value = "使用期限")
    private String syqx;

    @ApiModelProperty(value = "二维码内容")
    private String ewmnr;

    @ApiModelProperty(value = "共有方式")
    private Integer gyfs;

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public String getYxtcqzh() {
        return yxtcqzh;
    }

    public void setYxtcqzh(String yxtcqzh) {
        this.yxtcqzh = yxtcqzh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getZsid() {
        return zsid;
    }

    public void setZsid(String zsid) {
        this.zsid = zsid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getYcqzh() {
        return ycqzh;
    }

    public void setYcqzh(String ycqzh) {
        this.ycqzh = ycqzh;
    }

    public Integer getZslx() {
        return zslx;
    }

    public void setZslx(Integer zslx) {
        this.zslx = zslx;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public Integer getQszt() {
        return qszt;
    }

    public void setQszt(Integer qszt) {
        this.qszt = qszt;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
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

    public String getYwrmc() {
        return ywrmc;
    }

    public void setYwrmc(String ywrmc) {
        this.ywrmc = ywrmc;
    }

    public String getYwrzjh() {
        return ywrzjh;
    }

    public void setYwrzjh(String ywrzjh) {
        this.ywrzjh = ywrzjh;
    }

    public String getSzr() {
        return szr;
    }

    public void setSzr(String szr) {
        this.szr = szr;
    }

    public Date getSzsj() {
        return szsj;
    }

    public void setSzsj(Date szsj) {
        this.szsj = szsj;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }

    public String getFjh() {
        return fjh;
    }

    public void setFjh(String fjh) {
        this.fjh = fjh;
    }

    public Integer getBdclx() {
        return bdclx;
    }

    public void setBdclx(Integer bdclx) {
        this.bdclx = bdclx;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getFwbh() {
        return fwbh;
    }

    public void setFwbh(String fwbh) {
        this.fwbh = fwbh;
    }

    public String getZhlsh() {
        return zhlsh;
    }

    public void setZhlsh(String zhlsh) {
        this.zhlsh = zhlsh;
    }

    public Integer getGhyt() {
        return ghyt;
    }

    public void setGhyt(Integer ghyt) {
        this.ghyt = ghyt;
    }

    public String getYtdzh() {
        return ytdzh;
    }

    public void setYtdzh(String ytdzh) {
        this.ytdzh = ytdzh;
    }

    public String getYbdcdyh() {
        return ybdcdyh;
    }

    public void setYbdcdyh(String ybdcdyh) {
        this.ybdcdyh = ybdcdyh;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public String getZslxmc() {
        return zslxmc;
    }

    public void setZslxmc(String zslxmc) {
        this.zslxmc = zslxmc;
    }

    public String getQsztmc() {
        return qsztmc;
    }

    public void setQsztmc(String qsztmc) {
        this.qsztmc = qsztmc;
    }

    public String getBdclxmc() {
        return bdclxmc;
    }

    public void setBdclxmc(String bdclxmc) {
        this.bdclxmc = bdclxmc;
    }

    public String getGhytmc() {
        return ghytmc;
    }

    public void setGhytmc(String ghytmc) {
        this.ghytmc = ghytmc;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public String getQllxmc() {
        return qllxmc;
    }

    public void setQllxmc(String qllxmc) {
        this.qllxmc = qllxmc;
    }

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    public String getQlxz() {
        return qlxz;
    }

    public void setQlxz(String qlxz) {
        this.qlxz = qlxz;
    }

    public String getZmqlsx() {
        return zmqlsx;
    }

    public void setZmqlsx(String zmqlsx) {
        this.zmqlsx = zmqlsx;
    }

    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    public String getQlqtzk() {
        return qlqtzk;
    }

    public void setQlqtzk(String qlqtzk) {
        this.qlqtzk = qlqtzk;
    }

    public String getMj() {
        return mj;
    }

    public void setMj(String mj) {
        this.mj = mj;
    }

    public String getSyqx() {
        return syqx;
    }

    public void setSyqx(String syqx) {
        this.syqx = syqx;
    }

    public String getEwmnr() {
        return ewmnr;
    }

    public void setEwmnr(String ewmnr) {
        this.ewmnr = ewmnr;
    }

    public Integer getGyfs() {
        return gyfs;
    }

    public void setGyfs(Integer gyfs) {
        this.gyfs = gyfs;
    }

    @Override
    public String toString() {
        return "BdcZzdzResponseDTO{" +
                "xmid='" + xmid + '\'' +
                ", zsid='" + zsid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", qllx=" + qllx +
                ", qllxmc='" + qllxmc + '\'' +
                ", qlxz='" + qlxz + '\'' +
                ", djjg='" + djjg + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", ytdzh='" + ytdzh + '\'' +
                ", ybdcdyh='" + ybdcdyh + '\'' +
                ", ycqzh='" + ycqzh + '\'' +
                ", zslx=" + zslx +
                ", zslxmc='" + zslxmc + '\'' +
                ", qxdm='" + qxdm + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", qszt=" + qszt +
                ", qsztmc='" + qsztmc + '\'' +
                ", zl='" + zl + '\'' +
                ", qlrmc='" + qlrmc + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", ywrmc='" + ywrmc + '\'' +
                ", ywrzjh='" + ywrzjh + '\'' +
                ", zh='" + zh + '\'' +
                ", fjh='" + fjh + '\'' +
                ", szr='" + szr + '\'' +
                ", djyy='" + djyy + '\'' +
                ", djsj=" + djsj +
                ", szsj=" + szsj +
                ", bdclx=" + bdclx +
                ", bdclxmc='" + bdclxmc + '\'' +
                ", slbh='" + slbh + '\'' +
                ", fwbh='" + fwbh + '\'' +
                ", zhlsh='" + zhlsh + '\'' +
                ", ghyt=" + ghyt +
                ", ghytmc='" + ghytmc + '\'' +
                ", yxtcqzh='" + yxtcqzh + '\'' +
                ", fj='" + fj + '\'' +
                ", zmqlsx='" + zmqlsx + '\'' +
                ", gyqk='" + gyqk + '\'' +
                ", qlqtzk='" + qlqtzk + '\'' +
                ", mj='" + mj + '\'' +
                ", syqx='" + syqx + '\'' +
                ", ewmnr='" + ewmnr + '\'' +
                ", gyfs=" + gyfs +
                '}';
    }
}
