package cn.gtmap.realestate.etl.core.domian.ycsl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * generated by Generate POJOs.groovy
 * <p>Date: Thu Aug 02 16:19:23 CST 2018.</p>
 *
 * @author huangzijian
 */

@Entity
@Table(name = "YCSL_GXR")
public class YcslGxrDo implements Serializable {


    private static final long serialVersionUID = 8745631348178123117L;


    /**
     * 关系人主键
     */
    @Id
    @Column(name = "GXRID")
    private String gxrid;

    /**
     * 项目id
     */
    @Column(name = "PROID")
    private String proid;

    /**
     * 流程实例id
     */
    @Column(name = "WIID")
    private String wiid;

    /**
     * 关系人名称
     */
    @Column(name = "GXRMC")
    private String gxrmc;

    /**
     * 关系人身份证件种类
     */
    @Column(name = "GXRSFZJZL")
    private String gxrsfzjzl;

    /**
     * 关系人证件号
     */
    @Column(name = "GXRZJH")
    private String gxrzjh;

    /**
     * 关系人通讯地址
     */
    @Column(name = "GXRTXDZ")
    private String gxrtxdz;

    /**
     * 关系人邮编
     */
    @Column(name = "GXRYB")
    private String gxryb;

    /**
     * 关系人法定代表人
     */
    @Column(name = "GXRFDDBR")
    private String gxrfddbr;

    /**
     * 关系人法定代表人电话
     */
    @Column(name = "GXRFDDBRDH")
    private String gxrfddbrdh;

    /**
     * 关系人代理人
     */
    @Column(name = "GXRDLR")
    private String gxrdlr;

    /**
     * 关系人代理人电话
     */
    @Column(name = "GXRDLRDH")
    private String gxrdlrdh;

    /**
     * 关系人代理机构
     */
    @Column(name = "GXRDLJG")
    private String gxrdljg;

    /**
     * 关系人类型
     */
    @Column(name = "GXRLX")
    private String gxrlx;

    /**
     * 权利比例
     */
    @Column(name = "QLBL")
    private String qlbl;

    /**
     * 共有方式
     */
    @Column(name = "GYFS")
    private String gyfs;

    /**
     * 共有情况
     */
    @Column(name = "GYQK")
    private String gyqk;

    /**
     * 关系人性质
     */
    @Column(name = "GXRXZ")
    private String gxrxz;

    /**
     * 关系人联系电话
     */
    @Column(name = "GXRLXDH")
    private String gxrlxdh;

    /**
     * 所属行业
     */
    @Column(name = "SSHY")
    private String sshy;

    /**
     * 备注
     */
    @Column(name = "BZ")
    private String bz;

    /**
     * 是否持证
     */
    @Column(name = "SFCZR")
    private String sfczr;

    /**
     * 顺序号
     */
    @Column(name = "SXH")
    private Integer sxh;

    /**
     * 性别
     */
    @Column(name = "XB")
    private String xb;

    /**
     * 代理人证件种类
     */
    @Column(name = "GXRDLRZJZL")
    private String gxrdlrzjzl;

    /**
     * 代理人证件号
     */
    @Column(name = "GXRDLRZJH")
    private String gxrdlrzjh;

    /**
     * 其余共有人
     */
    @Column(name = "QYGYR")
    private String qygyr;

    /**
     * 填写方式 1:手动修改2：身份证读取
     */
    @Column(name = "TXFS")
    private String txfs;

    /**
     * 国籍
     */
    @Column(name = "GJ")
    private String gj;

    /**
     * 纳税人名称
     */
    @Column(name = "NSRMC")
    private String nsrmc;

    /**
     * 是否主产权人（1：是；0：否）（份额最大的为主产权为标志，份额一样的写两人，共同共同共有也为两人）
     */
    @Column(name = "SFZCQR")
    private String sfzcqr;

    /**
     * 关系人代理人证件种类
     */
    @Column(name = "GXRDLRSFZJLX")
    private String gxrdlrsfzjlx;

    /**
     * 关系人代理人国籍
     */
    @Column(name = "GXRDLRGJ")
    private String gxrdlrgj;

    /**
     * 权利转入或转出属性（1：转出，卖方；0：转入，买方）
     */
    @Column(name = "QLZRZCSX")
    private String qlzrzcsx;

    /**
     * 房屋套次
     */
    @Column(name = "FWTC")
    private String fwtc;

    /**
     * 关系人法定代表人证件种类
     */
    @Column(name = "GXRFDDBRZJZL")
    private String gxrfddbrzjzl;

    /**
     * 关系人法定代表人证件号
     */
    @Column(name = "GXRFDDBRZJH")
    private String gxrfddbrzjh;

    /**
     * 上次取得房屋时间
     */
    @Column(name = "SCQDFWSJ")
    private Date scqdfwsj;

    /**
     * 上次取得房屋成本
     */
    @Column(name = "SCQDFWCB")
    private String scqdfwcb;

    /**
     * 上次取得房屋方式
     */
    @Column(name = "SCQDFWFS")
    private String scqdfwfs;

    /**
     * 登记注册类型
     */
    @Column(name = "DJZCLX")
    private String djzclx;

    /**
     * 婚姻状况
     */
    @Column(name = "HYZK")
    private String hyzk;

    /**
     * 1为通过
     */
    @Column(name = "YZJG")
    private Integer yzjg;

    /**
     * 是否本地户籍
     */
    @Column(name = "SFBDHJ")
    private String sfbdhj;
    /**
     * 交易比例
     */
    @Column(name = "JYBL")
    private String jybl;

    /**
     * 家庭唯一满五住房（1、0）
     */
    @Column(name = "JTWYMWZF")
    private String jtwymwzf;

    /**
     * 是否直系亲属（是、否)
     */
    @Column(name = "SFZXQS")
    private String sfzxqs;

    /**
     * 共有人是否夫妻（1、0）
     */
    @Column(name = "GYRSFFQ")
    private String gyrsffq;

    /**
     * 维修资金状态
     */
    @Column(name = "WXZJZT")
    private String wxzjzt;

    /**
     * 税款减免
     */
    @Column(name = "SKJM")
    private String skjm;

    /**
     * 是否共有人  1=是  0=否
     */
    @Column(name = "SFGYR")
    private String sfgyr;

    /**
     * 申报房屋套次
     */
    @Column(name = "SBFWTC")
    private String sbfwtc;
    /**
     * 确认房屋套次
     */
    @Column(name = "QRFWTC")
    private String qrfwtc;
    /**
     * 代理人地址
     */
    @Column(name = "GXRDLRDZ")
    private String gxrdlrdz;


    public String getGxrid() {
        return this.gxrid;
    }

    public void setGxrid(String gxrid) {
        this.gxrid = gxrid;
    }

    public String getProid() {
        return this.proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    public String getWiid() {
        return this.wiid;
    }

    public void setWiid(String wiid) {
        this.wiid = wiid;
    }

    public String getGxrmc() {
        return this.gxrmc;
    }

    public void setGxrmc(String gxrmc) {
        this.gxrmc = gxrmc;
    }

    public String getGxrsfzjzl() {
        return this.gxrsfzjzl;
    }

    public void setGxrsfzjzl(String gxrsfzjzl) {
        this.gxrsfzjzl = gxrsfzjzl;
    }

    public String getGxrzjh() {
        return this.gxrzjh;
    }

    public void setGxrzjh(String gxrzjh) {
        this.gxrzjh = gxrzjh;
    }

    public String getGxrtxdz() {
        return this.gxrtxdz;
    }

    public void setGxrtxdz(String gxrtxdz) {
        this.gxrtxdz = gxrtxdz;
    }

    public String getGxryb() {
        return this.gxryb;
    }

    public void setGxryb(String gxryb) {
        this.gxryb = gxryb;
    }

    public String getGxrfddbr() {
        return this.gxrfddbr;
    }

    public void setGxrfddbr(String gxrfddbr) {
        this.gxrfddbr = gxrfddbr;
    }

    public String getGxrfddbrdh() {
        return this.gxrfddbrdh;
    }

    public void setGxrfddbrdh(String gxrfddbrdh) {
        this.gxrfddbrdh = gxrfddbrdh;
    }

    public String getGxrdlr() {
        return this.gxrdlr;
    }

    public void setGxrdlr(String gxrdlr) {
        this.gxrdlr = gxrdlr;
    }

    public String getGxrdlrdh() {
        return this.gxrdlrdh;
    }

    public void setGxrdlrdh(String gxrdlrdh) {
        this.gxrdlrdh = gxrdlrdh;
    }

    public String getGxrdljg() {
        return this.gxrdljg;
    }

    public void setGxrdljg(String gxrdljg) {
        this.gxrdljg = gxrdljg;
    }

    public String getGxrlx() {
        return this.gxrlx;
    }

    public void setGxrlx(String gxrlx) {
        this.gxrlx = gxrlx;
    }

    public String getQlbl() {
        return this.qlbl;
    }

    public void setQlbl(String qlbl) {
        this.qlbl = qlbl;
    }

    public String getGyfs() {
        return this.gyfs;
    }

    public void setGyfs(String gyfs) {
        this.gyfs = gyfs;
    }

    public String getGyqk() {
        return this.gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    public String getGxrxz() {
        return this.gxrxz;
    }

    public void setGxrxz(String gxrxz) {
        this.gxrxz = gxrxz;
    }

    public String getGxrlxdh() {
        return this.gxrlxdh;
    }

    public void setGxrlxdh(String gxrlxdh) {
        this.gxrlxdh = gxrlxdh;
    }

    public String getSshy() {
        return this.sshy;
    }

    public void setSshy(String sshy) {
        this.sshy = sshy;
    }

    public String getBz() {
        return this.bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getSfczr() {
        return this.sfczr;
    }

    public void setSfczr(String sfczr) {
        this.sfczr = sfczr;
    }

    public Integer getSxh() {
        return this.sxh;
    }

    public void setSxh(Integer sxh) {
        this.sxh = sxh;
    }

    public String getXb() {
        return this.xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getGxrdlrzjzl() {
        return this.gxrdlrzjzl;
    }

    public void setGxrdlrzjzl(String gxrdlrzjzl) {
        this.gxrdlrzjzl = gxrdlrzjzl;
    }

    public String getGxrdlrzjh() {
        return this.gxrdlrzjh;
    }

    public void setGxrdlrzjh(String gxrdlrzjh) {
        this.gxrdlrzjh = gxrdlrzjh;
    }

    public String getQygyr() {
        return this.qygyr;
    }

    public void setQygyr(String qygyr) {
        this.qygyr = qygyr;
    }

    public String getTxfs() {
        return this.txfs;
    }

    public void setTxfs(String txfs) {
        this.txfs = txfs;
    }

    public String getGj() {
        return this.gj;
    }

    public void setGj(String gj) {
        this.gj = gj;
    }

    public String getNsrmc() {
        return this.nsrmc;
    }

    public void setNsrmc(String nsrmc) {
        this.nsrmc = nsrmc;
    }

    public String getSfzcqr() {
        return this.sfzcqr;
    }

    public void setSfzcqr(String sfzcqr) {
        this.sfzcqr = sfzcqr;
    }

    public String getGxrdlrsfzjlx() {
        return this.gxrdlrsfzjlx;
    }

    public void setGxrdlrsfzjlx(String gxrdlrsfzjlx) {
        this.gxrdlrsfzjlx = gxrdlrsfzjlx;
    }

    public String getGxrdlrgj() {
        return this.gxrdlrgj;
    }

    public void setGxrdlrgj(String gxrdlrgj) {
        this.gxrdlrgj = gxrdlrgj;
    }

    public String getQlzrzcsx() {
        return this.qlzrzcsx;
    }

    public void setQlzrzcsx(String qlzrzcsx) {
        this.qlzrzcsx = qlzrzcsx;
    }

    public String getFwtc() {
        return this.fwtc;
    }

    public void setFwtc(String fwtc) {
        this.fwtc = fwtc;
    }

    public String getGxrfddbrzjzl() {
        return this.gxrfddbrzjzl;
    }

    public void setGxrfddbrzjzl(String gxrfddbrzjzl) {
        this.gxrfddbrzjzl = gxrfddbrzjzl;
    }

    public String getGxrfddbrzjh() {
        return this.gxrfddbrzjh;
    }

    public void setGxrfddbrzjh(String gxrfddbrzjh) {
        this.gxrfddbrzjh = gxrfddbrzjh;
    }

    public Date getScqdfwsj() {
        return this.scqdfwsj;
    }

    public void setScqdfwsj(Date scqdfwsj) {
        this.scqdfwsj = scqdfwsj;
    }

    public String getScqdfwcb() {
        return this.scqdfwcb;
    }

    public void setScqdfwcb(String scqdfwcb) {
        this.scqdfwcb = scqdfwcb;
    }

    public String getScqdfwfs() {
        return this.scqdfwfs;
    }

    public void setScqdfwfs(String scqdfwfs) {
        this.scqdfwfs = scqdfwfs;
    }

    public String getDjzclx() {
        return this.djzclx;
    }

    public void setDjzclx(String djzclx) {
        this.djzclx = djzclx;
    }

    public String getHyzk() {
        return this.hyzk;
    }

    public void setHyzk(String hyzk) {
        this.hyzk = hyzk;
    }

    public Integer getYzjg() {
        return this.yzjg;
    }

    public void setYzjg(Integer yzjg) {
        this.yzjg = yzjg;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSfbdhj() {
        return sfbdhj;
    }

    public void setSfbdhj(String sfbdhj) {
        this.sfbdhj = sfbdhj;
    }

    public String getJybl() {
        return jybl;
    }

    public void setJybl(String jybl) {
        this.jybl = jybl;
    }

    public String getSfzxqs() {
        return sfzxqs;
    }

    public void setSfzxqs(String sfzxqs) {
        this.sfzxqs = sfzxqs;
    }

    public String getJtwymwzf() {
        return jtwymwzf;
    }

    public void setJtwymwzf(String jtwymwzf) {
        this.jtwymwzf = jtwymwzf;
    }

    public String getGyrsffq() {
        return gyrsffq;
    }

    public void setGyrsffq(String gyrsffq) {
        this.gyrsffq = gyrsffq;
    }

    public String getWxzjzt() {
        return wxzjzt;
    }

    public void setWxzjzt(String wxzjzt) {
        this.wxzjzt = wxzjzt;
    }

    public String getSkjm() {
        return skjm;
    }

    public void setSkjm(String skjm) {
        this.skjm = skjm;
    }

    public String getSfgyr() {
        return sfgyr;
    }

    public void setSfgyr(String sfgyr) {
        this.sfgyr = sfgyr;
    }

    public String getSbfwtc() {
        return sbfwtc;
    }

    public void setSbfwtc(String sbfwtc) {
        this.sbfwtc = sbfwtc;
    }

    public String getQrfwtc() {
        return qrfwtc;
    }

    public void setQrfwtc(String qrfwtc) {
        this.qrfwtc = qrfwtc;
    }

    public String getGxrdlrdz() {
        return gxrdlrdz;
    }

    public void setGxrdlrdz(String gxrdlrdz) {
        this.gxrdlrdz = gxrdlrdz;
    }
}
