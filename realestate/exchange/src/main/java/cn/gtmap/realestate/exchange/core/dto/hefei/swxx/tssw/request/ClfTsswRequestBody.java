package cn.gtmap.realestate.exchange.core.dto.hefei.swxx.tssw.request;

import java.util.List;

/*
 * @author <a href="mailto:huangzijian@gtmap.cn">huangzijian</a>
 * @version 1.0, 2018/11/28
 * @description 存量房推送税务信息 请求体
 */
public class ClfTsswRequestBody {

    // 备案日期
    private String barq;
    // 出让方信息列表
    private List<Crfxxlb> crfxxlb;
    // 交易单价
    private String dj;
    // 单元
    private String dy;
    // 不动产权证号
    private String fcbm;
    // 图片附件
    private Fctpfjsy fctpfjsy;
    // 房间号
    private String fh;
    // 附属设施类型
    private String fssslx;
    // 附属物面积
    private String fswmj;
    // 房屋栋号
    private String fwdh;
    // 房屋代码
    private String fwnm;
    // 房屋完整地址
    private String fwwzdz;
    // 房屋用途代码
    private String fwyt;
    // 网签合同号
    private String htbh;
    // 合同金额
    private String htje;
    // 合同签订日期
    private String htqdrq;
    // 经办人电话
    private String jbrdh;
    // 经办人姓名
    private String jbrxm;
    // 建成年代
    private String jcnd;
    // 建筑朝向 传默认值“FWCX012”
    private String jzcx;
    // 建筑结构 代码
    private String jzjg;
    // 建筑面积
    private String jzmj;
    // 评估价格
    private String pgjg;
    //  前次交易类型 字典表
    private String qcjylx;
    // 前次交易日期
    private String qcjyrq;
    // 权属转移对象 FWLX字典
    private String qszydx;
    // 权属转移方式
    private String qszyfs;
    // 权属转移用途代码 取值和房屋用途保持一致，字典
    private String qszyyt;
    // 权证填制日期
    private String qztzrq;
    // 申报价格
    private String sbjg;
    // 受让方信息列表
    private List<Srfxxlb> srfxxlb;
    // 征收税务机关代码
    private String swjgbm;
    // 所在楼层
    private String szlc;
    // 套内面积
    private String tnmj;
    // 街道乡镇
    private String jdxz;
    // 小区名称
    private String xqmc;
    // 行政区划
    private String xzqh;
    // 业务受理编号
    private String ywsldh;
    // 中介公司ID
    private String zjgscode;
    // 总楼层
    private String zlc;
    // 经办机构
    private String jbjg;

    private String bdcdyh;

    // 是否直系亲属
    private String sfzxqs;
    // 个人所得税差额征收标记
    private String grsdscezsbj;
    // 房产交易业务来源标记
    private String ywlybj;
    // 前次交易价格
    private String qcjyjg;
    //差额征收项目
    private CrfSdsjcxm sdsjcxm;
    //非住宅征收项目
    private CrfFzzjsxm fzzjsxm;

    public String getQcjyjg() {
        return qcjyjg;
    }

    public void setQcjyjg(String qcjyjg) {
        this.qcjyjg = qcjyjg;
    }

    public String getYwlybj() {
        return ywlybj;
    }

    public void setYwlybj(String ywlybj) {
        this.ywlybj = ywlybj;
    }

    public CrfSdsjcxm getSdsjcxm() {
        return sdsjcxm;
    }

    public void setSdsjcxm(CrfSdsjcxm sdsjcxm) {
        this.sdsjcxm = sdsjcxm;
    }

    public CrfFzzjsxm getFzzjsxm() {
        return fzzjsxm;
    }

    public void setFzzjsxm(CrfFzzjsxm fzzjsxm) {
        this.fzzjsxm = fzzjsxm;
    }

    public String getGrsdscezsbj() {
        return grsdscezsbj;
    }

    public void setGrsdscezsbj(String grsdscezsbj) {
        this.grsdscezsbj = grsdscezsbj;
    }

    public String getBarq() {
        return barq;
    }

    public void setBarq(String barq) {
        this.barq = barq;
    }

    public List<Crfxxlb> getCrfxxlb() {
        return crfxxlb;
    }

    public void setCrfxxlb(List<Crfxxlb> crfxxlb) {
        this.crfxxlb = crfxxlb;
    }

    public String getDj() {
        return dj;
    }

    public void setDj(String dj) {
        this.dj = dj;
    }

    public String getDy() {
        return dy;
    }

    public void setDy(String dy) {
        this.dy = dy;
    }

    public String getFcbm() {
        return fcbm;
    }

    public void setFcbm(String fcbm) {
        this.fcbm = fcbm;
    }

    public String getFh() {
        return fh;
    }

    public void setFh(String fh) {
        this.fh = fh;
    }

    public String getFssslx() {
        return fssslx;
    }

    public void setFssslx(String fssslx) {
        this.fssslx = fssslx;
    }

    public String getFswmj() {
        return fswmj;
    }

    public void setFswmj(String fswmj) {
        this.fswmj = fswmj;
    }

    public String getFwdh() {
        return fwdh;
    }

    public void setFwdh(String fwdh) {
        this.fwdh = fwdh;
    }

    public String getFwnm() {
        return fwnm;
    }

    public void setFwnm(String fwnm) {
        this.fwnm = fwnm;
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

    public String getJbrdh() {
        return jbrdh;
    }

    public void setJbrdh(String jbrdh) {
        this.jbrdh = jbrdh;
    }

    public String getJbrxm() {
        return jbrxm;
    }

    public void setJbrxm(String jbrxm) {
        this.jbrxm = jbrxm;
    }

    public String getJcnd() {
        return jcnd;
    }

    public void setJcnd(String jcnd) {
        this.jcnd = jcnd;
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

    public String getPgjg() {
        return pgjg;
    }

    public void setPgjg(String pgjg) {
        this.pgjg = pgjg;
    }

    public String getQcjylx() {
        return qcjylx;
    }

    public void setQcjylx(String qcjylx) {
        this.qcjylx = qcjylx;
    }

    public String getQcjyrq() {
        return qcjyrq;
    }

    public void setQcjyrq(String qcjyrq) {
        this.qcjyrq = qcjyrq;
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

    public String getQztzrq() {
        return qztzrq;
    }

    public void setQztzrq(String qztzrq) {
        this.qztzrq = qztzrq;
    }

    public String getSbjg() {
        return sbjg;
    }

    public void setSbjg(String sbjg) {
        this.sbjg = sbjg;
    }

    public List<Srfxxlb> getSrfxxlb() {
        return srfxxlb;
    }

    public void setSrfxxlb(List<Srfxxlb> srfxxlb) {
        this.srfxxlb = srfxxlb;
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

    public String getZjgscode() {
        return zjgscode;
    }

    public void setZjgscode(String zjgscode) {
        this.zjgscode = zjgscode;
    }

    public String getZlc() {
        return zlc;
    }

    public void setZlc(String zlc) {
        this.zlc = zlc;
    }

    public String getJdxz() {
        return jdxz;
    }

    public void setJdxz(String jdxz) {
        this.jdxz = jdxz;
    }

    public Fctpfjsy getFctpfjsy() {
        return fctpfjsy;
    }

    public void setFctpfjsy(Fctpfjsy fctpfjsy) {
        this.fctpfjsy = fctpfjsy;
    }

    public String getJbjg() {
        return jbjg;
    }

    public void setJbjg(String jbjg) {
        this.jbjg = jbjg;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getSfzxqs() {
        return sfzxqs;
    }

    public void setSfzxqs(String sfzxqs) {
        this.sfzxqs = sfzxqs;
    }
}
