package cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.response;

import cn.gtmap.realestate.exchange.core.dto.zzcxj.fwqlcx.response.FwqlCxResponseSdxx;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-26
 * @description 个人档案查询 申请信息
 */
public class GrdacxSqxx {

    //预告信息
    private List<GrdacxYgxx> ygxx;

    //锁定信息
    private List<FwqlCxResponseSdxx> sdxx;


    // 抵押信息
    private List<GrdacxDyxx> dyxx;

    // 居住信息
    private List<GrdacxJzqxx> jzqxx;

    // 查封信息
    private List<GrdacxCfxx> cfxx;

    // 异议信息
    private List<GrdacxYyxx> yyxx;

    // 权利人信息
    private List<GrdacxQlr> qlr;

    // 项目ID
    private String proid;

    // 不动产单元号
    private String bdcdyh;

    // 产权证号
    private String cqzh;

    // 不动产类型
    private String bdclx;

    // 定作物用途
    private String dzwyt;

    // 宗地宗海用途
    private String zdzhyt;

    // 宗地宗海权利性质
    private String zdzhqlxz;

    // 坐落
    private String zl;

    // 定作物面积
    private String dzwmj;


    private String xzqhszdm;

    private String jdxzdm;

    // 宗地宗海面积
    private String zdzhmj;

    // 房屋性质
    private String fwxz;

    // 房屋结构
    private String fwjg;

    // 所在层
    private String szc;

    private String dscs;

    // 总层数
    private String zcs;

    // 物理层
    private String wlc;

    // 房屋类型
    private String fwlx;

    // 所在名义层
    private String szmyc;

    // 层高
    private String cg;

    // 自然幢号
    private String zrzh;

    // 房间号
    private String fjh;

    // 建筑面积
    private String jzmj;

    // 套内面积
    private String tnmj;

    // 朝向
    private String cx;

    // 建筑年份
    private String jznf;

    // 备注
    private String bz;

    // 产权来源
    private String cqly;

    // 房屋用途名称
    private String fwytmc;

    // 房产证发证时间
    private String fczfzsj;


    private String execmark;

    // 权利人名称
    private String qlrmc;

    // 权利人证件号码
    private String qlrzjhm;

    // 共有方式
    private String gyfs;

    // 共有比例
    private String gybl;

    // 证书ID
    private String certid;

    // 不动产单元编号
    private String bdcdybh;

    // 宗地代码
    private String zddm;

    // 权利类型
    private String qllx;

    // 权利起始时间
    private String qlqssj;

    // 权利结束时间
    private String qljssj;

    // 土地使用开始期限
    private String tdsyksqx;

    // 土地使用结束期限
    private String tdsyjsqx;

    // 权利性质
    private String qlxz;

    // 宗地权利类型
    private String zdqllx;

    // 分摊面积
    private String ftmj;

    // 登记类型
    private String djlx;

    // 登记原因
    private String djyy;

    // 土地使用面积
    private String tdsymj;

    // 附记
    private String fj;

    private String ygdjzl;

    //土地等级时间
    private String tddjj;

    //独占土地面积
    private String dytdmj;

    //分摊土地面积
    private String fttdmj;

    //土地所有权人
    private String tdsyqr;

    //权籍管理代码
    private String qjgldm;

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    public String getTdsyqr() {
        return tdsyqr;
    }

    public void setTdsyqr(String tdsyqr) {
        this.tdsyqr = tdsyqr;
    }

    public String getFttdmj() {
        return fttdmj;
    }

    public void setFttdmj(String fttdmj) {
        this.fttdmj = fttdmj;
    }

    public String getDytdmj() {
        return dytdmj;
    }

    public void setDytdmj(String dytdmj) {
        this.dytdmj = dytdmj;
    }

    public String getTddjj() {
        return tddjj;
    }

    public void setTddjj(String tddjj) {
        this.tddjj = tddjj;
    }

    public List<GrdacxYgxx> getYgxx() {
        return ygxx;
    }

    public void setYgxx(List<GrdacxYgxx> ygxx) {
        this.ygxx = ygxx;
    }

    public String getYgdjzl() {
        return ygdjzl;
    }

    public void setYgdjzl(String ygdjzl) {
        this.ygdjzl = ygdjzl;
    }

    public List<FwqlCxResponseSdxx> getSdxx() {
        return sdxx;
    }

    public void setSdxx(List<FwqlCxResponseSdxx> sdxx) {
        this.sdxx = sdxx;
    }


    public List<GrdacxDyxx> getDyxx() {
        return dyxx;
    }

    public void setDyxx(List<GrdacxDyxx> dyxx) {
        this.dyxx = dyxx;
    }

    public List<GrdacxCfxx> getCfxx() {
        return cfxx;
    }

    public void setCfxx(List<GrdacxCfxx> cfxx) {
        this.cfxx = cfxx;
    }

    public List<GrdacxYyxx> getYyxx() {
        return yyxx;
    }

    public void setYyxx(List<GrdacxYyxx> yyxx) {
        this.yyxx = yyxx;
    }

    public List<GrdacxQlr> getQlr() {
        return qlr;
    }

    public void setQlr(List<GrdacxQlr> qlr) {
        this.qlr = qlr;
    }

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getCqzh() {
        return cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
    }

    public String getBdclx() {
        return bdclx;
    }

    public void setBdclx(String bdclx) {
        this.bdclx = bdclx;
    }

    public String getDzwyt() {
        return dzwyt;
    }

    public void setDzwyt(String dzwyt) {
        this.dzwyt = dzwyt;
    }

    public String getZdzhyt() {
        return zdzhyt;
    }

    public void setZdzhyt(String zdzhyt) {
        this.zdzhyt = zdzhyt;
    }

    public String getZdzhqlxz() {
        return zdzhqlxz;
    }

    public void setZdzhqlxz(String zdzhqlxz) {
        this.zdzhqlxz = zdzhqlxz;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getDzwmj() {
        return dzwmj;
    }

    public void setDzwmj(String dzwmj) {
        this.dzwmj = dzwmj;
    }

    public String getXzqhszdm() {
        return xzqhszdm;
    }

    public void setXzqhszdm(String xzqhszdm) {
        this.xzqhszdm = xzqhszdm;
    }

    public String getJdxzdm() {
        return jdxzdm;
    }

    public void setJdxzdm(String jdxzdm) {
        this.jdxzdm = jdxzdm;
    }

    public String getZdzhmj() {
        return zdzhmj;
    }

    public void setZdzhmj(String zdzhmj) {
        this.zdzhmj = zdzhmj;
    }

    public String getFwxz() {
        return fwxz;
    }

    public void setFwxz(String fwxz) {
        this.fwxz = fwxz;
    }

    public String getFwjg() {
        return fwjg;
    }

    public void setFwjg(String fwjg) {
        this.fwjg = fwjg;
    }

    public String getSzc() {
        return szc;
    }

    public void setSzc(String szc) {
        this.szc = szc;
    }

    public String getZcs() {
        return zcs;
    }

    public void setZcs(String zcs) {
        this.zcs = zcs;
    }

    public String getWlc() {
        return wlc;
    }

    public void setWlc(String wlc) {
        this.wlc = wlc;
    }

    public String getFwlx() {
        return fwlx;
    }

    public void setFwlx(String fwlx) {
        this.fwlx = fwlx;
    }

    public String getSzmyc() {
        return szmyc;
    }

    public void setSzmyc(String szmyc) {
        this.szmyc = szmyc;
    }

    public String getCg() {
        return cg;
    }

    public void setCg(String cg) {
        this.cg = cg;
    }

    public String getZrzh() {
        return zrzh;
    }

    public void setZrzh(String zrzh) {
        this.zrzh = zrzh;
    }

    public String getFjh() {
        return fjh;
    }

    public void setFjh(String fjh) {
        this.fjh = fjh;
    }

    public String getJzmj() {
        return jzmj;
    }

    public void setJzmj(String jzmj) {
        this.jzmj = jzmj;
    }

    public String getTnmj() {
        return tnmj;
    }

    public void setTnmj(String tnmj) {
        this.tnmj = tnmj;
    }

    public String getCx() {
        return cx;
    }

    public void setCx(String cx) {
        this.cx = cx;
    }

    public String getJznf() {
        return jznf;
    }

    public void setJznf(String jznf) {
        this.jznf = jznf;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getCqly() {
        return cqly;
    }

    public void setCqly(String cqly) {
        this.cqly = cqly;
    }

    public String getFwytmc() {
        return fwytmc;
    }

    public void setFwytmc(String fwytmc) {
        this.fwytmc = fwytmc;
    }

    public String getFczfzsj() {
        return fczfzsj;
    }

    public void setFczfzsj(String fczfzsj) {
        this.fczfzsj = fczfzsj;
    }

    public String getExecmark() {
        return execmark;
    }

    public void setExecmark(String execmark) {
        this.execmark = execmark;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getQlrzjhm() {
        return qlrzjhm;
    }

    public void setQlrzjhm(String qlrzjhm) {
        this.qlrzjhm = qlrzjhm;
    }

    public String getGyfs() {
        return gyfs;
    }

    public void setGyfs(String gyfs) {
        this.gyfs = gyfs;
    }

    public String getGybl() {
        return gybl;
    }

    public void setGybl(String gybl) {
        this.gybl = gybl;
    }

    public String getCertid() {
        return certid;
    }

    public void setCertid(String certid) {
        this.certid = certid;
    }

    public String getBdcdybh() {
        return bdcdybh;
    }

    public void setBdcdybh(String bdcdybh) {
        this.bdcdybh = bdcdybh;
    }

    public String getZddm() {
        return zddm;
    }

    public void setZddm(String zddm) {
        this.zddm = zddm;
    }

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public String getQlqssj() {
        return qlqssj;
    }

    public void setQlqssj(String qlqssj) {
        this.qlqssj = qlqssj;
    }

    public String getQljssj() {
        return qljssj;
    }

    public void setQljssj(String qljssj) {
        this.qljssj = qljssj;
    }

    public String getQlxz() {
        return qlxz;
    }

    public void setQlxz(String qlxz) {
        this.qlxz = qlxz;
    }

    public String getDscs() {
        return dscs;
    }

    public void setDscs(String dscs) {
        this.dscs = dscs;
    }

    public String getZdqllx() {
        return zdqllx;
    }

    public void setZdqllx(String zdqllx) {
        this.zdqllx = zdqllx;
    }

    public String getFtmj() {
        return ftmj;
    }

    public void setFtmj(String ftmj) {
        this.ftmj = ftmj;
    }

    public String getDjlx() {
        return djlx;
    }

    public void setDjlx(String djlx) {
        this.djlx = djlx;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public String getTdsymj() {
        return tdsymj;
    }

    public void setTdsymj(String tdsymj) {
        this.tdsymj = tdsymj;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public List<GrdacxJzqxx> getJzqxx() {
        return jzqxx;
    }

    public void setJzqxx(List<GrdacxJzqxx> jzqxx) {
        this.jzqxx = jzqxx;
    }

    public String getTdsyksqx() {
        return tdsyksqx;
    }

    public void setTdsyksqx(String tdsyksqx) {
        this.tdsyksqx = tdsyksqx;
    }

    public String getTdsyjsqx() {
        return tdsyjsqx;
    }

    public void setTdsyjsqx(String tdsyjsqx) {
        this.tdsyjsqx = tdsyjsqx;
    }

    @Override
    public String toString() {
        return "GrdacxSqxx{" +
                "ygxx=" + ygxx +
                ", sdxx=" + sdxx +
                ", dyxx=" + dyxx +
                ", cfxx=" + cfxx +
                ", yyxx=" + yyxx +
                ", qlr=" + qlr +
                ", proid='" + proid + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", cqzh='" + cqzh + '\'' +
                ", bdclx='" + bdclx + '\'' +
                ", dzwyt='" + dzwyt + '\'' +
                ", zdzhyt='" + zdzhyt + '\'' +
                ", zdzhqlxz='" + zdzhqlxz + '\'' +
                ", zl='" + zl + '\'' +
                ", dzwmj='" + dzwmj + '\'' +
                ", xzqhszdm='" + xzqhszdm + '\'' +
                ", jdxzdm='" + jdxzdm + '\'' +
                ", zdzhmj='" + zdzhmj + '\'' +
                ", fwxz='" + fwxz + '\'' +
                ", fwjg='" + fwjg + '\'' +
                ", szc='" + szc + '\'' +
                ", dscs='" + dscs + '\'' +
                ", zcs='" + zcs + '\'' +
                ", wlc='" + wlc + '\'' +
                ", fwlx='" + fwlx + '\'' +
                ", szmyc='" + szmyc + '\'' +
                ", cg='" + cg + '\'' +
                ", zrzh='" + zrzh + '\'' +
                ", fjh='" + fjh + '\'' +
                ", jzmj='" + jzmj + '\'' +
                ", tnmj='" + tnmj + '\'' +
                ", cx='" + cx + '\'' +
                ", jznf='" + jznf + '\'' +
                ", bz='" + bz + '\'' +
                ", cqly='" + cqly + '\'' +
                ", fwytmc='" + fwytmc + '\'' +
                ", fczfzsj='" + fczfzsj + '\'' +
                ", execmark='" + execmark + '\'' +
                ", qlrmc='" + qlrmc + '\'' +
                ", qlrzjhm='" + qlrzjhm + '\'' +
                ", gyfs='" + gyfs + '\'' +
                ", gybl='" + gybl + '\'' +
                ", certid='" + certid + '\'' +
                ", bdcdybh='" + bdcdybh + '\'' +
                ", zddm='" + zddm + '\'' +
                ", qllx='" + qllx + '\'' +
                ", qlqssj='" + qlqssj + '\'' +
                ", qljssj='" + qljssj + '\'' +
                ", qlxz='" + qlxz + '\'' +
                ", zdqllx='" + zdqllx + '\'' +
                ", ftmj='" + ftmj + '\'' +
                ", djlx='" + djlx + '\'' +
                ", djyy='" + djyy + '\'' +
                ", tdsymj='" + tdsymj + '\'' +
                ", fj='" + fj + '\'' +
                ", ygdjzl='" + ygdjzl + '\'' +
                ", jzqxx='" + jzqxx + '\'' +
                '}';
    }
}
