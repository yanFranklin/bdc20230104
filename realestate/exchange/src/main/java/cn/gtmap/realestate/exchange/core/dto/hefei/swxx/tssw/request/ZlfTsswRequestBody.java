package cn.gtmap.realestate.exchange.core.dto.hefei.swxx.tssw.request;

import java.util.List;

/*
 * @author <a href="mailto:huangzijian@gtmap.cn">huangzijian</a>
 * @version 1.0, 2018/11/28
 * @description 增量房推送税务信息 请求体
 */
public class ZlfTsswRequestBody {
    // 备案日期
    private String  barq;
    // 不含增值税金额
    private String bhzzsje;
    // 单元
    private String dy;
    // 房号
    private String fh;
    // 房产编码
    private String fcbm;
    // 图片
    private Fctpfjsy fctpfjsy;
    // 房屋栋号
    private String fwdh;
    // 房屋完整地址
    private String fwwzdz;
    // 房屋用途
    private String fwyt;
    // 合同编号
    private String htbh;
    // 合同金额
    private String htje;
    // 合同签订日期
    private String htqdrq;
    // 建成年代
    private String jcnd;
    // 街道乡镇
    private String jdxz;
    // 建筑面积
    private String jzcx;
    // 建筑结构
    private String jzjg;
    // 建筑面积
    private String jzmj;
    // 开发商信息列表
    private List<Kfsxxlb> kfsxxlb;
    // 权属转移对象
    private String qszydx;
    // 权属转移方式
    private String qszyfs;
    // 权属转移用途代码
    private String qszyyt;
//    private List<Srfxxjtcylb> srfxxjtcylb;
    // 受让人列表
    private List<Srfxxlb> srfxxlb;
    // 所属地
    private String ssd;
    // 税务机关代码
    private String swjgbm;
    // 所在楼层
    private String szlc;
    // 套内面积
    private String tnmj;
    // 小区名称
    private String xqmc;
    // 行政区划
    private String xzqh;
    // 业务受理单号
    private String ywsldh;
    // 总楼层
    private String zlc;
    // 经办机构
    private String jbjg;
    // 增值税税额
    private String zzsse;
    // 经办人姓名
    private String jbrxm;
    // 经办人电话
    private String jbrdh;
    // 房屋内码
    private String fwnm;

    private String bdcdyh;

    public String getBarq() {
        return barq;
    }

    public void setBarq(String barq) {
        this.barq = barq;
    }

    public String getBhzzsje() {
        return bhzzsje;
    }

    public void setBhzzsje(String bhzzsje) {
        this.bhzzsje = bhzzsje;
    }

    public String getDy() {
        return dy;
    }

    public void setDy(String dy) {
        this.dy = dy;
    }

    public String getFh() {
        return fh;
    }

    public void setFh(String fh) {
        this.fh = fh;
    }

    public String getFcbm() {
        return fcbm;
    }

    public void setFcbm(String fcbm) {
        this.fcbm = fcbm;
    }

    public String getFwdh() {
        return fwdh;
    }

    public void setFwdh(String fwdh) {
        this.fwdh = fwdh;
    }

    public String getFwwzdz() {
        return fwwzdz;
    }

    public void setFwwzdz(String fwwzdz) {
        this.fwwzdz = fwwzdz;
    }

    public String getFwyt() {
        return fwyt;
    }

    public void setFwyt(String fwyt) {
        this.fwyt = fwyt;
    }

    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    public String getHtje() {
        return htje;
    }

    public void setHtje(String htje) {
        this.htje = htje;
    }

    public String getHtqdrq() {
        return htqdrq;
    }

    public void setHtqdrq(String htqdrq) {
        this.htqdrq = htqdrq;
    }

    public String getJcnd() {
        return jcnd;
    }

    public void setJcnd(String jcnd) {
        this.jcnd = jcnd;
    }

    public String getJdxz() {
        return jdxz;
    }

    public void setJdxz(String jdxz) {
        this.jdxz = jdxz;
    }

    public String getJzcx() {
        return jzcx;
    }

    public void setJzcx(String jzcx) {
        this.jzcx = jzcx;
    }

    public String getJzjg() {
        return jzjg;
    }

    public void setJzjg(String jzjg) {
        this.jzjg = jzjg;
    }

    public String getJzmj() {
        return jzmj;
    }

    public void setJzmj(String jzmj) {
        this.jzmj = jzmj;
    }

    public List<Kfsxxlb> getKfsxxlb() {
        return kfsxxlb;
    }

    public void setKfsxxlb(List<Kfsxxlb> kfsxxlb) {
        this.kfsxxlb = kfsxxlb;
    }

    public String getQszydx() {
        return qszydx;
    }

    public void setQszydx(String qszydx) {
        this.qszydx = qszydx;
    }

    public String getQszyfs() {
        return qszyfs;
    }

    public void setQszyfs(String qszyfs) {
        this.qszyfs = qszyfs;
    }

    public String getQszyyt() {
        return qszyyt;
    }

    public void setQszyyt(String qszyyt) {
        this.qszyyt = qszyyt;
    }

    public List<Srfxxlb> getSrfxxlb() {
        return srfxxlb;
    }

    public void setSrfxxlb(List<Srfxxlb> srfxxlb) {
        this.srfxxlb = srfxxlb;
    }

    public String getSsd() {
        return ssd;
    }

    public void setSsd(String ssd) {
        this.ssd = ssd;
    }

    public String getSwjgbm() {
        return swjgbm;
    }

    public void setSwjgbm(String swjgbm) {
        this.swjgbm = swjgbm;
    }

    public String getSzlc() {
        return szlc;
    }

    public void setSzlc(String szlc) {
        this.szlc = szlc;
    }

    public String getTnmj() {
        return tnmj;
    }

    public void setTnmj(String tnmj) {
        this.tnmj = tnmj;
    }

    public String getXqmc() {
        return xqmc;
    }

    public void setXqmc(String xqmc) {
        this.xqmc = xqmc;
    }

    public String getXzqh() {
        return xzqh;
    }

    public void setXzqh(String xzqh) {
        this.xzqh = xzqh;
    }

    public String getYwsldh() {
        return ywsldh;
    }

    public void setYwsldh(String ywsldh) {
        this.ywsldh = ywsldh;
    }

    public String getZlc() {
        return zlc;
    }

    public void setZlc(String zlc) {
        this.zlc = zlc;
    }

    public String getJbjg() {
        return jbjg;
    }

    public void setJbjg(String jbjg) {
        this.jbjg = jbjg;
    }

    public String getZzsse() {
        return zzsse;
    }

    public void setZzsse(String zzsse) {
        this.zzsse = zzsse;
    }

    public String getJbrxm() {
        return jbrxm;
    }

    public void setJbrxm(String jbrxm) {
        this.jbrxm = jbrxm;
    }

    public String getJbrdh() {
        return jbrdh;
    }

    public void setJbrdh(String jbrdh) {
        this.jbrdh = jbrdh;
    }

    public String getFwnm() {
        return fwnm;
    }

    public void setFwnm(String fwnm) {
        this.fwnm = fwnm;
    }

    public Fctpfjsy getFctpfjsy() {
        return fctpfjsy;
    }

    public void setFctpfjsy(Fctpfjsy fctpfjsy) {
        this.fctpfjsy = fctpfjsy;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }
}
