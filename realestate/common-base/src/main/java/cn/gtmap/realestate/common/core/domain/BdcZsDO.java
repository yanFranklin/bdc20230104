package cn.gtmap.realestate.common.core.domain;

import cn.gtmap.realestate.common.core.annotations.Zd;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/11/2
 * @description 不动产证书
 */
@Table(name = "BDC_ZS")
@ApiModel(value = "BdcZsDO", description = "不动产证书")
public class BdcZsDO {
    @Id
    /**证书ID*/
    @ApiModelProperty(value = "证书ID")
    @NotBlank(message = "证书ID不能为空")
    private String zsid;
    /**不动产权证号*/
    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;
    /**证号流水号*/
    @ApiModelProperty(value = "证号流水号")
    private String zhlsh;
    /**年份*/
    @ApiModelProperty(value = "年份", hidden = true)
    private String nf;
    /**权证印刷序列号*/
    @ApiModelProperty(value = "权证印刷序列号", hidden = true)
    private String qzysxlh;
    /**
     * 打印状态
     **/
    @Zd(tableClass = BdcZdSfDO.class)
    @ApiModelProperty(value = "打印状态")
    private Integer dyzt;
    /**权利其他状况*/
    @ApiModelProperty(value = "权利其他状况")
    private String qlqtzk;
    /**区县代码*/
    @ApiModelProperty(value = "区县代码", hidden = true)
    private String qxdm;
    /**省区市简称*/
    @ApiModelProperty(value = "省区市简称", hidden = true)
    private String sqsjc;
    /**所在市县全称*/
    @ApiModelProperty(value = "所有市县全称", hidden = true)
    private String szsxqc;
    /**发证人*/
    @ApiModelProperty(value = "发证人", hidden = true)
    private String fzr;
    /**发证人ID*/
    @ApiModelProperty(value = "发证人ID", hidden = true)
    private String fzrid;
    /**发证时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "发证时间", hidden = true,example = "2018-10-01 12:18:48")
    private Date fzsj;
    /**领证人*/
    @ApiModelProperty(value = "领证人")
    private String lzr;
    /**领证人电话*/
    @ApiModelProperty(value = "领证人电话")
    private String lzrdh;
    /**领证人证件种类*/
    @Zd(tableClass = BdcZdZjzlDO.class)
    @ApiModelProperty(value = "领证人证件种类")
    private Integer lzrzjzl;
    /**领证人证件号*/
    @ApiModelProperty(value = "领证人证件号")
    private String lzrzjh;
    /**领证时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "领证时间", hidden = true,example = "2018-10-01 12:18:48")
    private Date lzsj;
    /**缮证时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "缮证时间", hidden = true,example = "2018-10-01 12:18:48")
    private Date szsj;
    /**缮证人*/
    @ApiModelProperty(value = "缮证人", hidden = true)
    private String szr;
    /**缮证人ID*/
    @ApiModelProperty(value = "缮证人ID", hidden = true)
    private String szrid;
    /**二维码内容*/
    @ApiModelProperty(value = "二维码内容", hidden = true)
    private String ewmnr;
    /**允许打印时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "允许打印时间", hidden = true,example = "2018-10-01 12:18:48")
    private Date yxdysj;
    /**共用不动产权证号*/
    @ApiModelProperty(value = "公用不动产权证号", hidden = true)
    private String gybdcqzh;
    /**不动产权证号简称*/
    @ApiModelProperty(value = "不动产权证号简称", hidden = true)
    private String bdcqzhjc;
    /**证书类型*/
    @Zd(tableClass = BdcZdZslxDO.class)
    @ApiModelProperty(value = "证书类型")
    private Integer zslx;
    /**附记*/
    @ApiModelProperty(value = "附记")
    private String fj;
    /**不动产单元号*/
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    /**坐落*/
    @ApiModelProperty(value = "坐落")
    private String zl;
    /**证明权利或事项*/
    @ApiModelProperty(value = "证明权利或事项")
    private String zmqlsx;
    /**权利人*/
    @ApiModelProperty(value = "权利人")
    private String qlr;
    /**义务人*/
    @ApiModelProperty(value = "义务人")
    private String ywr;
    /**共有方式*/
    @Zd(tableClass = BdcZdGyfsDO.class)
    @ApiModelProperty(value = "共有方式")
    private Integer gyfs;
    /**权利类型*/
    @Zd(tableClass = BdcZdQllxDO.class)
    @ApiModelProperty(value = "权利类型")
    private Integer qllx;
    /**权利性质*/
    @ApiModelProperty(value = "权利性质")
    private String qlxz;
    /**用途*/
    @ApiModelProperty(value = "用途")
    private String yt;
    /**面积*/
    @ApiModelProperty(value = "面积")
    private String mj;
    /**使用期限*/
    @ApiModelProperty(value = "使用期限")
    private String syqx;
    /**
     * 登记部门代码
     */
    @ApiModelProperty(value = "登记部门代码")
    private String djbmdm;

    @ApiModelProperty(value = "证书序列号")
    private Integer zhxlh;

    @ApiModelProperty(value = "电子证照标识")
    private String zzbs;

    /**
     * 常州地区使用此字端保存 电子印章标示
     */
    @ApiModelProperty(value = "电子证照id")
    private String zzid;

    /**
     * 常州地区使用此字端保存 电子印章 url 地址
     */
    @ApiModelProperty(value = "电子证照地址")
    private String zzdz;

    @ApiModelProperty(value = "电子证照目录编码")
    private String zzmlbm;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "证照签发时间", hidden = true, example = "2018-10-01 12:18:48")
    private Date zzqfsj;

    @ApiModelProperty(value = "电子证照状态")
    private Integer dzzzzt;

    /**
     * 用于判断电子证照是否上传成功
     */
    @ApiModelProperty(value = "电子证照文件id")
    private String storageid;

    public Integer getDyzt() {
        return dyzt;
    }

    public void setDyzt(Integer dyzt) {
        this.dyzt = dyzt;
    }

    public String getZzid() {
        return zzid;
    }

    public void setZzid(String zzid) {
        this.zzid = zzid;
    }

    public String getZzdz() {
        return zzdz;
    }

    public void setZzdz(String zzdz) {
        this.zzdz = zzdz;
    }

    public String getZzmlbm() {
        return zzmlbm;
    }

    public void setZzmlbm(String zzmlbm) {
        this.zzmlbm = zzmlbm;
    }


    public Date getZzqfsj() {
        return zzqfsj;
    }

    public void setZzqfsj(Date zzqfsj) {
        this.zzqfsj = zzqfsj;
    }

    public String getStorageid() {
        return storageid;
    }

    public void setStorageid(String storageid) {
        this.storageid = storageid;
    }

    public String getZzbs() {
        return zzbs;
    }

    public void setZzbs(String zzbs) {
        this.zzbs = zzbs;
    }

    public Integer getZhxlh() {
        return zhxlh;
    }

    public void setZhxlh(Integer zhxlh) {
        this.zhxlh = zhxlh;
    }

    public String getDjbmdm() {
        return djbmdm;
    }

    public void setDjbmdm(String djbmdm) {
        this.djbmdm = djbmdm;
    }

    public String getLzrdh() {
        return lzrdh;
    }

    public void setLzrdh(String lzrdh) {
        this.lzrdh = lzrdh;
    }

    public String getZsid() {
        return zsid;
    }

    public void setZsid(String zsid) {
        this.zsid = zsid;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getZhlsh() {
        return zhlsh;
    }

    public void setZhlsh(String zhlsh) {
        this.zhlsh = zhlsh;
    }

    public String getNf() {
        return nf;
    }

    public void setNf(String nf) {
        this.nf = nf;
    }

    public String getQzysxlh() {
        return qzysxlh;
    }

    public void setQzysxlh(String qzysxlh) {
        this.qzysxlh = qzysxlh;
    }

    public String getQlqtzk() {
        return qlqtzk;
    }

    public void setQlqtzk(String qlqtzk) {
        this.qlqtzk = qlqtzk;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getSqsjc() {
        return sqsjc;
    }

    public void setSqsjc(String sqsjc) {
        this.sqsjc = sqsjc;
    }

    public String getSzsxqc() {
        return szsxqc;
    }

    public void setSzsxqc(String szsxqc) {
        this.szsxqc = szsxqc;
    }

    public String getFzr() {
        return fzr;
    }

    public void setFzr(String fzr) {
        this.fzr = fzr;
    }

    public String getFzrid() {
        return fzrid;
    }

    public void setFzrid(String fzrid) {
        this.fzrid = fzrid;
    }

    public Date getFzsj() {
        return fzsj;
    }

    public void setFzsj(Date fzsj) {
        this.fzsj = fzsj;
    }

    public String getLzr() {
        return lzr;
    }

    public void setLzr(String lzr) {
        this.lzr = lzr;
    }

    public Integer getLzrzjzl() {
        return lzrzjzl;
    }

    public void setLzrzjzl(Integer lzrzjzl) {
        this.lzrzjzl = lzrzjzl;
    }

    public String getLzrzjh() {
        return lzrzjh;
    }

    public void setLzrzjh(String lzrzjh) {
        this.lzrzjh = lzrzjh;
    }

    public Date getLzsj() {
        return lzsj;
    }

    public void setLzsj(Date lzsj) {
        this.lzsj = lzsj;
    }

    public Date getSzsj() {
        return szsj;
    }

    public void setSzsj(Date szsj) {
        this.szsj = szsj;
    }

    public String getSzr() {
        return szr;
    }

    public void setSzr(String szr) {
        this.szr = szr;
    }

    public String getSzrid() {
        return szrid;
    }

    public void setSzrid(String szrid) {
        this.szrid = szrid;
    }

    public String getEwmnr() {
        return ewmnr;
    }

    public void setEwmnr(String ewmnr) {
        this.ewmnr = ewmnr;
    }

    public Date getYxdysj() {
        return yxdysj;
    }

    public void setYxdysj(Date yxdysj) {
        this.yxdysj = yxdysj;
    }

    public String getGybdcqzh() {
        return gybdcqzh;
    }

    public void setGybdcqzh(String gybdcqzh) {
        this.gybdcqzh = gybdcqzh;
    }

    public String getBdcqzhjc() {
        return bdcqzhjc;
    }

    public void setBdcqzhjc(String bdcqzhjc) {
        this.bdcqzhjc = bdcqzhjc;
    }

    public Integer getZslx() {
        return zslx;
    }

    public void setZslx(Integer zslx) {
        this.zslx = zslx;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getZmqlsx() {
        return zmqlsx;
    }

    public void setZmqlsx(String zmqlsx) {
        this.zmqlsx = zmqlsx;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getYwr() {
        return ywr;
    }

    public void setYwr(String ywr) {
        this.ywr = ywr;
    }

    public Integer getGyfs() {
        return gyfs;
    }

    public void setGyfs(Integer gyfs) {
        this.gyfs = gyfs;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public String getQlxz() {
        return qlxz;
    }

    public void setQlxz(String qlxz) {
        this.qlxz = qlxz;
    }

    public String getYt() {
        return yt;
    }

    public void setYt(String yt) {
        this.yt = yt;
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

    public Integer getDzzzzt() {return dzzzzt;}

    public void setDzzzzt(Integer dzzzzt) {this.dzzzzt = dzzzzt;}

    @Override
    public String toString() {
        return "BdcZsDO{" +
                "zsid='" + zsid + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", zhlsh='" + zhlsh + '\'' +
                ", nf='" + nf + '\'' +
                ", qzysxlh='" + qzysxlh + '\'' +
                ", dyzt=" + dyzt +
                ", qlqtzk='" + qlqtzk + '\'' +
                ", qxdm='" + qxdm + '\'' +
                ", sqsjc='" + sqsjc + '\'' +
                ", szsxqc='" + szsxqc + '\'' +
                ", fzr='" + fzr + '\'' +
                ", fzrid='" + fzrid + '\'' +
                ", fzsj=" + fzsj +
                ", lzr='" + lzr + '\'' +
                ", lzrdh='" + lzrdh + '\'' +
                ", lzrzjzl=" + lzrzjzl +
                ", lzrzjh='" + lzrzjh + '\'' +
                ", lzsj=" + lzsj +
                ", szsj=" + szsj +
                ", szr='" + szr + '\'' +
                ", szrid='" + szrid + '\'' +
                ", ewmnr='" + ewmnr + '\'' +
                ", yxdysj=" + yxdysj +
                ", gybdcqzh='" + gybdcqzh + '\'' +
                ", bdcqzhjc='" + bdcqzhjc + '\'' +
                ", zslx=" + zslx +
                ", fj='" + fj + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", zl='" + zl + '\'' +
                ", zmqlsx='" + zmqlsx + '\'' +
                ", qlr='" + qlr + '\'' +
                ", ywr='" + ywr + '\'' +
                ", gyfs=" + gyfs +
                ", qllx=" + qllx +
                ", qlxz='" + qlxz + '\'' +
                ", yt='" + yt + '\'' +
                ", mj='" + mj + '\'' +
                ", syqx='" + syqx + '\'' +
                ", djbmdm='" + djbmdm + '\'' +
                ", zhxlh=" + zhxlh +
                ", zzbs='" + zzbs + '\'' +
                ", zzid='" + zzid + '\'' +
                ", zzdz='" + zzdz + '\'' +
                ", zzmlbm='" + zzmlbm + '\'' +
                ", zzqfsj=" + zzqfsj +
                ", storageid='" + storageid + '\'' +
                ", dzzzzt='" + dzzzzt + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof BdcZsDO)){
            return false;
        }
        BdcZsDO bdcZsDO = (BdcZsDO) o;
        return Objects.equals(zsid, bdcZsDO.zsid) && Objects.equals(bdcqzh, bdcZsDO.bdcqzh) && Objects.equals(zhlsh, bdcZsDO.zhlsh) && Objects.equals(nf, bdcZsDO.nf) && Objects.equals(qzysxlh, bdcZsDO.qzysxlh) && Objects.equals(dyzt, bdcZsDO.dyzt) && Objects.equals(qlqtzk, bdcZsDO.qlqtzk) && Objects.equals(qxdm, bdcZsDO.qxdm) && Objects.equals(sqsjc, bdcZsDO.sqsjc) && Objects.equals(szsxqc, bdcZsDO.szsxqc) && Objects.equals(fzr, bdcZsDO.fzr) && Objects.equals(fzrid, bdcZsDO.fzrid) && Objects.equals(fzsj, bdcZsDO.fzsj) && Objects.equals(lzr, bdcZsDO.lzr) && Objects.equals(lzrdh, bdcZsDO.lzrdh) && Objects.equals(lzrzjzl, bdcZsDO.lzrzjzl) && Objects.equals(lzrzjh, bdcZsDO.lzrzjh) && Objects.equals(lzsj, bdcZsDO.lzsj) && Objects.equals(szsj, bdcZsDO.szsj) && Objects.equals(szr, bdcZsDO.szr) && Objects.equals(szrid, bdcZsDO.szrid) && Objects.equals(ewmnr, bdcZsDO.ewmnr) && Objects.equals(yxdysj, bdcZsDO.yxdysj) && Objects.equals(gybdcqzh, bdcZsDO.gybdcqzh) && Objects.equals(bdcqzhjc, bdcZsDO.bdcqzhjc) && Objects.equals(zslx, bdcZsDO.zslx) && Objects.equals(fj, bdcZsDO.fj) && Objects.equals(bdcdyh, bdcZsDO.bdcdyh) && Objects.equals(zl, bdcZsDO.zl) && Objects.equals(zmqlsx, bdcZsDO.zmqlsx) && Objects.equals(qlr, bdcZsDO.qlr) && Objects.equals(ywr, bdcZsDO.ywr) && Objects.equals(gyfs, bdcZsDO.gyfs) && Objects.equals(qllx, bdcZsDO.qllx) && Objects.equals(qlxz, bdcZsDO.qlxz) && Objects.equals(yt, bdcZsDO.yt) && Objects.equals(mj, bdcZsDO.mj) && Objects.equals(syqx, bdcZsDO.syqx) && Objects.equals(djbmdm, bdcZsDO.djbmdm) && Objects.equals(zhxlh, bdcZsDO.zhxlh) && Objects.equals(zzbs, bdcZsDO.zzbs) && Objects.equals(zzid, bdcZsDO.zzid) && Objects.equals(zzdz, bdcZsDO.zzdz) && Objects.equals(zzmlbm, bdcZsDO.zzmlbm) && Objects.equals(zzqfsj, bdcZsDO.zzqfsj) && Objects.equals(dzzzzt, bdcZsDO.dzzzzt) && Objects.equals(storageid, bdcZsDO.storageid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zsid, bdcqzh, zhlsh, nf, qzysxlh, dyzt, qlqtzk, qxdm, sqsjc, szsxqc, fzr, fzrid, fzsj, lzr, lzrdh, lzrzjzl, lzrzjh, lzsj, szsj, szr, szrid, ewmnr, yxdysj, gybdcqzh, bdcqzhjc, zslx, fj, bdcdyh, zl, zmqlsx, qlr, ywr, gyfs, qllx, qlxz, yt, mj, syqx, djbmdm, zhxlh, zzbs, zzid, zzdz, zzmlbm, zzqfsj, dzzzzt, storageid);
    }
}
