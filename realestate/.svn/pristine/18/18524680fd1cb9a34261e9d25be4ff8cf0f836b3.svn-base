package cn.gtmap.realestate.common.core.dto.certificate.eCertificate;

import cn.gtmap.realestate.common.util.validator.certificate.DzzzZm;
import cn.gtmap.realestate.common.util.validator.certificate.DzzzZs;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 电子证照实体类
 * <p>
 * 所有 Date 类型均转换为 13 位时间戳，String 作为类型
 * </p>
 *
 * @author <a href="mailto:lixin1@lixin.com">lixin</a>
 * @version 下午1:57 2021/1/8
 */
public class EZsTimeStampDTO {
    @NotBlank(message = "证照类型代码不为空")
    private String zzlxdm;
    @NotBlank(message = "证照颁发机构不为空")
    private String zzbfjg;
    @NotBlank(message = "证照颁发机构代码不为空")
    private String zzbfjgdm;
    @NotNull(message = "证照颁发日期不为空")
    private Long zzbfrq;

    @NotBlank(message = "持证主体")
    private String czzt;
    @NotBlank(message = "持证主体代码")
    private String czztdm;
    @NotBlank(message = "持证主体代码类型")
    private String czztdmlx;
    @NotBlank(message = "持证主体代码类型代码")
    private String czztdmlxdm;

    @NotBlank(message = "业务号不为空")
    private String ywh;
    @NotBlank(message = "不动产权证号")
    private String bdcqzh;
    @NotBlank(message = "单位代码")
    private String dwdm;
    @NotBlank(message = "省区市简称")
    private String sqsjc;
    @NotBlank(message = "所在市县全称")
    private String szsxqc;
    @NotNull(message = "发证日期")
    private Long fzrq;
    @NotBlank(message = "不动产单元号")
    private String bdcdyh;
    @NotBlank(message = "坐落")
    private String zl;
    @NotBlank(message = "权利类型")
    private String qllx;
    @NotBlank(message = "年份")
    private String nf;
    @NotBlank(message = "证号流水号")
    private String zhlsh;
    @NotBlank(message = "权利人")
    private String qlr;


    private List<EQlrxxDTO> qlrxx;

    // 证明
    @NotBlank(message = "证明权利或事项", groups = {DzzzZm.class})
    private String zmqlsx;
    @NotBlank(message = "义务人", groups = {DzzzZm.class})
    private String ywr;

    // 证书
    @NotBlank(message = "共有情况", groups = {DzzzZs.class})
    private String gyqk;
    @NotBlank(message = "权利性质", groups = {DzzzZs.class})
    private String qlxz;
    @NotBlank(message = "用途", groups = {DzzzZs.class})
    private String yt;
    @NotBlank(message = "面积", groups = {DzzzZs.class})
    private String mj;
    @NotBlank(message = "使用期限", groups = {DzzzZs.class})
    private String syqx;

    // 非必填
    // 权利其他状况
    private String qlqtzk;
    // 附记
    private String fj;

    // 取登记的土地使用起始时间和证明的债权起始时间
    // 证照有效期起始日期
    private Long zzyxqqsrq;
    // 证照有效期截止日期
    private Long zzyxqjzrq;

    //编号 印制号
    private String yzh;
    //区分记录是证明还是证书
    private String zstype;
    //二维码内容
    private String ewmnr;
    //权利人证件种类
    private String qlrzjzl;
    //权利人证件号
    private String qlrzjh;
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
    private Long jzjzzsj;
    //加注件制作者
    private String jzjzzz;
    //加注件制作事由
    private String jzjzzsy;
    //加注件有限期截止时间
    private Long jzjyxqjzsj;
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

    public List<EQlrxxDTO> getQlrxx() {
        return qlrxx;
    }

    public void setQlrxx(List<EQlrxxDTO> qlrxx) {
        this.qlrxx = qlrxx;
    }

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

    public Long getZzbfrq() {
        return zzbfrq;
    }

    public void setZzbfrq(Long zzbfrq) {
        this.zzbfrq = zzbfrq;
    }

    public Long getZzyxqqsrq() {
        return zzyxqqsrq;
    }

    public void setZzyxqqsrq(Long zzyxqqsrq) {
        this.zzyxqqsrq = zzyxqqsrq;
    }

    public Long getZzyxqjzrq() {
        return zzyxqjzrq;
    }

    public void setZzyxqjzrq(Long zzyxqjzrq) {
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

    public Long getFzrq() {
        return fzrq;
    }

    public void setFzrq(Long fzrq) {
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

    public Long getJzjzzsj() {
        return jzjzzsj;
    }

    public void setJzjzzsj(Long jzjzzsj) {
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

    public Long getJzjyxqjzsj() {
        return jzjyxqjzsj;
    }

    public void setJzjyxqjzsj(Long jzjyxqjzsj) {
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
}
