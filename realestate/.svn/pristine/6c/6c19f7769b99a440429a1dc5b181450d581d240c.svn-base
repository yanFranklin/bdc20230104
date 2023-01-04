package cn.gtmap.realestate.common.core.dto.certificate.eCertificate;

import cn.gtmap.realestate.common.util.validator.certificate.DzzzZm;
import cn.gtmap.realestate.common.util.validator.certificate.DzzzZs;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 存量电子证照实体
 *
 * @author <a href="mailto:lixin1@lixin.com">lixin</a>
 * @version 下午1:57 2021/4/20
 */
public class DzzzClDTO {
    @NotBlank(message = "证照类型代码不为空", groups = {DzzzZm.class, DzzzZs.class})
    private String zzlxdm;
    @NotBlank(message = "证照颁发机构不为空", groups = {DzzzZm.class, DzzzZs.class})
    private String zzbfjg;
    @NotBlank(message = "证照颁发机构代码不为空", groups = {DzzzZm.class, DzzzZs.class})
    private String zzbfjgdm;
    @NotNull(message = "证照颁发日期不为空", groups = {DzzzZm.class, DzzzZs.class})
    private Long zzbfrq;

    @NotBlank(message = "持证主体", groups = {DzzzZm.class, DzzzZs.class})
    private String czzt;
    @NotBlank(message = "持证主体代码", groups = {DzzzZm.class, DzzzZs.class})
    private String czztdm;
    @NotBlank(message = "持证主体代码类型", groups = {DzzzZm.class, DzzzZs.class})
    private String czztdmlx;
    @NotBlank(message = "持证主体代码类型代码", groups = {DzzzZm.class, DzzzZs.class})
    private String czztdmlxdm;

    @NotBlank(message = "业务号不为空", groups = {DzzzZm.class, DzzzZs.class})
    private String ywh;
    @NotBlank(message = "不动产权证号", groups = {DzzzZm.class, DzzzZs.class})
    private String bdcqzh;
    @NotBlank(message = "单位代码", groups = {DzzzZm.class, DzzzZs.class})
    private String dwdm;
    @NotBlank(message = "省区市简称", groups = {DzzzZm.class, DzzzZs.class})
    private String sqsjc;
    @NotBlank(message = "所在市县全称", groups = {DzzzZm.class, DzzzZs.class})
    private String szsxqc;
    @NotNull(message = "发证日期", groups = {DzzzZm.class, DzzzZs.class})
    private Long fzrq;
    @NotBlank(message = "不动产单元号", groups = {DzzzZm.class, DzzzZs.class})
    private String bdcdyh;
    @NotBlank(message = "坐落", groups = {DzzzZm.class, DzzzZs.class})
    private String zl;
    @NotBlank(message = "权利类型", groups = {DzzzZm.class, DzzzZs.class})
    private String qllx;
    @NotBlank(message = "年份", groups = {DzzzZm.class, DzzzZs.class})
    private String nf;
    @NotBlank(message = "证号流水号", groups = {DzzzZm.class, DzzzZs.class})
    private String zhlsh;
    @NotBlank(message = "权利人", groups = {DzzzZm.class, DzzzZs.class})
    private String qlr;
    @NotBlank(message = "权利类型代码", groups = {DzzzZm.class, DzzzZs.class})
    private String qllxdm;


    // 权利人信息
    @Valid
    @NotNull(message = "权利人信息", groups = {DzzzZm.class, DzzzZs.class})
    private List<EQlrxxDTO> qlrxx;


    // 证明
    @NotBlank(message = "证明权利或事项", groups = {DzzzZm.class})
    private String zmqlsx;
    @NotBlank(message = "义务人", groups = {DzzzZm.class})
    private String ywr;
    // 义务人信息
    private List<EYwrxxDTO> ywrxx;
    @NotBlank(message = "关联不动产权证号", groups = {DzzzZm.class})
    private String glbdcqzh;

    // 证书
    @NotBlank(message = "共有情况", groups = {DzzzZs.class})
    private String gyqk;
    @NotBlank(message = "权利性质", groups = {DzzzZs.class})
    private String qlxz;
    @NotBlank(message = "权利性质代码", groups = {DzzzZs.class})
    private String qlxzdm;
    @NotBlank(message = "用途", groups = {DzzzZs.class})
    private String yt;
    @NotBlank(message = "用途代码", groups = {DzzzZs.class})
    private String ytdm;
    @NotBlank(message = "面积", groups = {DzzzZs.class})
    private String mj;
    @NotBlank(message = "面积单位", groups = {DzzzZs.class})
    private String mjdw;
    @NotBlank(message = "面积单位代码", groups = {DzzzZs.class})
    private String mjdwdm;
    @NotBlank(message = "使用期限", groups = {DzzzZs.class})
    private String syqx;

    // 非必填
    // 权利其他状况
    private String qlqtzk;
    // 其他
    private String qt;
    // 附记
    private String fj;
    // 电子签章唯一标识
    private String dzqzwybs;
    // 是否异步
    private Boolean sfyb = false;

    // 取登记的土地使用起始时间和证明的债权起始时间
    // 证照有效期起始日期
    private Long zzyxqqsrq;
    // 证照有效期截止日期
    private Long zzyxqjzrq;


    public String getQt() {
        return qt;
    }

    public void setQt(String qt) {
        this.qt = qt;
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

    public Long getFzrq() {
        return fzrq;
    }

    public void setFzrq(Long fzrq) {
        this.fzrq = fzrq;
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

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public String getNf() {
        return nf;
    }

    public void setNf(String nf) {
        this.nf = nf;
    }

    public String getZhlsh() {
        return zhlsh;
    }

    public void setZhlsh(String zhlsh) {
        this.zhlsh = zhlsh;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getQllxdm() {
        return qllxdm;
    }

    public void setQllxdm(String qllxdm) {
        this.qllxdm = qllxdm;
    }

    public List<EQlrxxDTO> getQlrxx() {
        return qlrxx;
    }

    public void setQlrxx(List<EQlrxxDTO> qlrxx) {
        this.qlrxx = qlrxx;
    }

    public String getZmqlsx() {
        return zmqlsx;
    }

    public void setZmqlsx(String zmqlsx) {
        this.zmqlsx = zmqlsx;
    }

    public String getYwr() {
        return ywr;
    }

    public void setYwr(String ywr) {
        this.ywr = ywr;
    }

    public List<EYwrxxDTO> getYwrxx() {
        return ywrxx;
    }

    public void setYwrxx(List<EYwrxxDTO> ywrxx) {
        this.ywrxx = ywrxx;
    }

    public String getGlbdcqzh() {
        return glbdcqzh;
    }

    public void setGlbdcqzh(String glbdcqzh) {
        this.glbdcqzh = glbdcqzh;
    }

    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    public String getQlxz() {
        return qlxz;
    }

    public void setQlxz(String qlxz) {
        this.qlxz = qlxz;
    }

    public String getQlxzdm() {
        return qlxzdm;
    }

    public void setQlxzdm(String qlxzdm) {
        this.qlxzdm = qlxzdm;
    }

    public String getYt() {
        return yt;
    }

    public void setYt(String yt) {
        this.yt = yt;
    }

    public String getYtdm() {
        return ytdm;
    }

    public void setYtdm(String ytdm) {
        this.ytdm = ytdm;
    }

    public String getMj() {
        return mj;
    }

    public void setMj(String mj) {
        this.mj = mj;
    }

    public String getMjdw() {
        return mjdw;
    }

    public void setMjdw(String mjdw) {
        this.mjdw = mjdw;
    }

    public String getMjdwdm() {
        return mjdwdm;
    }

    public void setMjdwdm(String mjdwdm) {
        this.mjdwdm = mjdwdm;
    }

    public String getSyqx() {
        return syqx;
    }

    public void setSyqx(String syqx) {
        this.syqx = syqx;
    }

    public String getQlqtzk() {
        return qlqtzk;
    }

    public void setQlqtzk(String qlqtzk) {
        this.qlqtzk = qlqtzk;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public String getDzqzwybs() {
        return dzqzwybs;
    }

    public void setDzqzwybs(String dzqzwybs) {
        this.dzqzwybs = dzqzwybs;
    }

    public Boolean getSfyb() {
        return sfyb;
    }

    public void setSfyb(Boolean sfyb) {
        this.sfyb = sfyb;
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
}
