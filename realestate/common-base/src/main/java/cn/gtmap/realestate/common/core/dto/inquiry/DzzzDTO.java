package cn.gtmap.realestate.common.core.dto.inquiry;

import java.util.Date;
import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:huangjian@gtmap.cn>huanagjian</a>"
 * @version 1.0, 2019/12/18
 * @description 电子正常实体DTO
 */
public class DzzzDTO {
    //业务号
    private String ywh;
    //不动产权证号
    private String bdcqzh;
    //证照类型代码
    private String zzlxdm;
    //证照颁发机构
    private String zzbfjg;
    //证照颁发机构代码
    private String zzbfjgdm;
    //证照颁发日期
    private Date zzbfrq;
    //证照有效期起始日期
    private Date zzyxqqsrq;
    //证照有效期截止日期
    private Date zzyxqjzrq;
    //证号流水号
    private String zhlsh;
    //年份
    private String nf;
    //编号 印制号
    private String yzh;
    //权利其他状况
    private String qlqtzk;
    //单位代码
    private String dwdm;
    //省区市简称
    private String sqsjc;
    //所在市县全称
    private String szsxqc;
    //区分记录是证明还是证书
    private String zstype;
    //发证日期
    private Date fzrq;
    //二维码内容
    private String ewmnr;
    //附记
    private String fj;
    //不动产单元号
    private String bdcdyh;
    //坐落
    private String zl;
    //证明权利或事项
    private String zmqlsx;
    //共有情况
    private String gyqk;
    //权利类型
    private String qllx;
    //权利性质
    private String qlxz;
    //用途
    private String yt;
    //面积
    private String mj;
    //使用期限
    private String syqx;
    //权利人
    private String qlr;
    //权利人证件种类
    private String qlrzjzl;
    //权利人证件号
    private String qlrzjh;
    //义务人
    private String ywr;
    //义务人证件种类
    private String ywrzjzl;
    //义务人证件号
    private String ywrzjh;
    //发证日期年
    private String year;
    //发证日期月
    private String month;
    //发证日期日
    private String day;
    //房屋结构
    private String fwjg;
    //幢号
    private String zh;
    //总层数
    private String zcs;
    //房间号
    private String fjh;
    //所在名义层
    private String szmyc;
    //建筑面积
    private String jzmj;
    //定作物用途
    private String dzwyt;
    //套内建筑面积
    private String tnjzmj;
    //分摊建筑面积
    private String ftjzmj;
    //房屋类型
    private String fwlx;
    //建筑年代
    private String jznd;
    //打印状态（0 未打印，1 已打印）
    private String dyzt;
    //加注件制作时间
    private Date jzjzzsj;
    //加注件制作者
    private String jzjzzz;
    //加注件制作事由
    private String jzjzzsy;
    //加注件有限期截止时间
    private Date jzjyxqjzsj;
    //加注件id
    private String jzjid;
    //数字签名
    private String szqm;
    //其他
    private String qt;
    //证照编号
    private String zzbh;
    //证照状态(1:现势,2:注销)
    private Integer zzzt;
    //持证主体
    private String czzt;
    //持证主体代码
    private String czztdm;
    //持证主体代码类型
    private String czztdmlx;
    //持证主体证件号
    private String czztdmlxdm;
    //权利人信息
    private List<DzzzQlrxxDTO> qlrxx;

    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getZzlxdm() {
        return zzlxdm;
    }

    public void setZzlxdm(String zzlxdm) {
        this.zzlxdm = zzlxdm;
    }

    public String getZzbfjg() {
        return zzbfjg;
    }

    public void setZzbfjg(String zzbfjg) {
        this.zzbfjg = zzbfjg;
    }

    public String getZzbfjgdm() {
        return zzbfjgdm;
    }

    public void setZzbfjgdm(String zzbfjgdm) {
        this.zzbfjgdm = zzbfjgdm;
    }

    public Date getZzbfrq() {
        return zzbfrq;
    }

    public void setZzbfrq(Date zzbfrq) {
        this.zzbfrq = zzbfrq;
    }

    public Date getZzyxqqsrq() {
        return zzyxqqsrq;
    }

    public void setZzyxqqsrq(Date zzyxqqsrq) {
        this.zzyxqqsrq = zzyxqqsrq;
    }

    public Date getZzyxqjzrq() {
        return zzyxqjzrq;
    }

    public void setZzyxqjzrq(Date zzyxqjzrq) {
        this.zzyxqjzrq = zzyxqjzrq;
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

    public String getYzh() {
        return yzh;
    }

    public void setYzh(String yzh) {
        this.yzh = yzh;
    }

    public String getQlqtzk() {
        return qlqtzk;
    }

    public void setQlqtzk(String qlqtzk) {
        this.qlqtzk = qlqtzk;
    }

    public String getDwdm() {
        return dwdm;
    }

    public void setDwdm(String dwdm) {
        this.dwdm = dwdm;
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

    public String getZstype() {
        return zstype;
    }

    public void setZstype(String zstype) {
        this.zstype = zstype;
    }

    public Date getFzrq() {
        return fzrq;
    }

    public void setFzrq(Date fzrq) {
        this.fzrq = fzrq;
    }

    public String getEwmnr() {
        return ewmnr;
    }

    public void setEwmnr(String ewmnr) {
        this.ewmnr = ewmnr;
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

    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
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

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getQlrzjzl() {
        return qlrzjzl;
    }

    public void setQlrzjzl(String qlrzjzl) {
        this.qlrzjzl = qlrzjzl;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getYwr() {
        return ywr;
    }

    public void setYwr(String ywr) {
        this.ywr = ywr;
    }

    public String getYwrzjzl() {
        return ywrzjzl;
    }

    public void setYwrzjzl(String ywrzjzl) {
        this.ywrzjzl = ywrzjzl;
    }

    public String getYwrzjh() {
        return ywrzjh;
    }

    public void setYwrzjh(String ywrzjh) {
        this.ywrzjh = ywrzjh;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getFwjg() {
        return fwjg;
    }

    public void setFwjg(String fwjg) {
        this.fwjg = fwjg;
    }

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }

    public String getZcs() {
        return zcs;
    }

    public void setZcs(String zcs) {
        this.zcs = zcs;
    }

    public String getFjh() {
        return fjh;
    }

    public void setFjh(String fjh) {
        this.fjh = fjh;
    }

    public String getSzmyc() {
        return szmyc;
    }

    public void setSzmyc(String szmyc) {
        this.szmyc = szmyc;
    }

    public String getJzmj() {
        return jzmj;
    }

    public void setJzmj(String jzmj) {
        this.jzmj = jzmj;
    }

    public String getDzwyt() {
        return dzwyt;
    }

    public void setDzwyt(String dzwyt) {
        this.dzwyt = dzwyt;
    }

    public String getTnjzmj() {
        return tnjzmj;
    }

    public void setTnjzmj(String tnjzmj) {
        this.tnjzmj = tnjzmj;
    }

    public String getFtjzmj() {
        return ftjzmj;
    }

    public void setFtjzmj(String ftjzmj) {
        this.ftjzmj = ftjzmj;
    }

    public String getFwlx() {
        return fwlx;
    }

    public void setFwlx(String fwlx) {
        this.fwlx = fwlx;
    }

    public String getJznd() {
        return jznd;
    }

    public void setJznd(String jznd) {
        this.jznd = jznd;
    }

    public String getDyzt() {
        return dyzt;
    }

    public void setDyzt(String dyzt) {
        this.dyzt = dyzt;
    }

    public Date getJzjzzsj() {
        return jzjzzsj;
    }

    public void setJzjzzsj(Date jzjzzsj) {
        this.jzjzzsj = jzjzzsj;
    }

    public String getJzjzzz() {
        return jzjzzz;
    }

    public void setJzjzzz(String jzjzzz) {
        this.jzjzzz = jzjzzz;
    }

    public String getJzjzzsy() {
        return jzjzzsy;
    }

    public void setJzjzzsy(String jzjzzsy) {
        this.jzjzzsy = jzjzzsy;
    }

    public Date getJzjyxqjzsj() {
        return jzjyxqjzsj;
    }

    public void setJzjyxqjzsj(Date jzjyxqjzsj) {
        this.jzjyxqjzsj = jzjyxqjzsj;
    }

    public String getJzjid() {
        return jzjid;
    }

    public void setJzjid(String jzjid) {
        this.jzjid = jzjid;
    }

    public String getSzqm() {
        return szqm;
    }

    public void setSzqm(String szqm) {
        this.szqm = szqm;
    }

    public String getQt() {
        return qt;
    }

    public void setQt(String qt) {
        this.qt = qt;
    }

    public String getZzbh() {
        return zzbh;
    }

    public void setZzbh(String zzbh) {
        this.zzbh = zzbh;
    }

    public Integer getZzzt() {
        return zzzt;
    }

    public void setZzzt(Integer zzzt) {
        this.zzzt = zzzt;
    }

    public String getCzzt() {
        return czzt;
    }

    public void setCzzt(String czzt) {
        this.czzt = czzt;
    }

    public String getCzztdm() {
        return czztdm;
    }

    public void setCzztdm(String czztdm) {
        this.czztdm = czztdm;
    }

    public String getCzztdmlx() {
        return czztdmlx;
    }

    public void setCzztdmlx(String czztdmlx) {
        this.czztdmlx = czztdmlx;
    }

    public String getCzztdmlxdm() {
        return czztdmlxdm;
    }

    public void setCzztdmlxdm(String czztdmlxdm) {
        this.czztdmlxdm = czztdmlxdm;
    }

    public List<DzzzQlrxxDTO> getQlrxx() {
        return qlrxx;
    }

    public void setQlrxx(List<DzzzQlrxxDTO> qlrxx) {
        this.qlrxx = qlrxx;
    }

    @Override
    public String toString() {
        return "DzzzDTO{" +
                "ywh='" + ywh + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", zzlxdm='" + zzlxdm + '\'' +
                ", zzbfjg='" + zzbfjg + '\'' +
                ", zzbfjgdm='" + zzbfjgdm + '\'' +
                ", zzbfrq=" + zzbfrq +
                ", zzyxqqsrq=" + zzyxqqsrq +
                ", zzyxqjzrq=" + zzyxqjzrq +
                ", zhlsh='" + zhlsh + '\'' +
                ", nf='" + nf + '\'' +
                ", yzh='" + yzh + '\'' +
                ", qlqtzk='" + qlqtzk + '\'' +
                ", dwdm='" + dwdm + '\'' +
                ", sqsjc='" + sqsjc + '\'' +
                ", szsxqc='" + szsxqc + '\'' +
                ", zstype='" + zstype + '\'' +
                ", fzrq=" + fzrq +
                ", ewmnr='" + ewmnr + '\'' +
                ", fj='" + fj + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", zl='" + zl + '\'' +
                ", zmqlsx='" + zmqlsx + '\'' +
                ", gyqk='" + gyqk + '\'' +
                ", qllx='" + qllx + '\'' +
                ", qlxz='" + qlxz + '\'' +
                ", yt='" + yt + '\'' +
                ", mj='" + mj + '\'' +
                ", syqx='" + syqx + '\'' +
                ", qlr='" + qlr + '\'' +
                ", qlrzjzl='" + qlrzjzl + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", ywr='" + ywr + '\'' +
                ", ywrzjzl='" + ywrzjzl + '\'' +
                ", ywrzjh='" + ywrzjh + '\'' +
                ", year='" + year + '\'' +
                ", month='" + month + '\'' +
                ", day='" + day + '\'' +
                ", fwjg='" + fwjg + '\'' +
                ", zh='" + zh + '\'' +
                ", zcs='" + zcs + '\'' +
                ", fjh='" + fjh + '\'' +
                ", szmyc='" + szmyc + '\'' +
                ", jzmj='" + jzmj + '\'' +
                ", dzwyt='" + dzwyt + '\'' +
                ", tnjzmj='" + tnjzmj + '\'' +
                ", ftjzmj='" + ftjzmj + '\'' +
                ", fwlx='" + fwlx + '\'' +
                ", jznd='" + jznd + '\'' +
                ", dyzt='" + dyzt + '\'' +
                ", jzjzzsj=" + jzjzzsj +
                ", jzjzzz='" + jzjzzz + '\'' +
                ", jzjzzsy='" + jzjzzsy + '\'' +
                ", jzjyxqjzsj=" + jzjyxqjzsj +
                ", jzjid='" + jzjid + '\'' +
                ", szqm='" + szqm + '\'' +
                ", qt='" + qt + '\'' +
                ", zzbh='" + zzbh + '\'' +
                ", zzzt=" + zzzt +
                ", czzt='" + czzt + '\'' +
                ", czztdm='" + czztdm + '\'' +
                ", czztdmlx='" + czztdmlx + '\'' +
                ", czztdmlxdm='" + czztdmlxdm + '\'' +
                ", qlrxx=" + qlrxx +
                '}';
    }
}
