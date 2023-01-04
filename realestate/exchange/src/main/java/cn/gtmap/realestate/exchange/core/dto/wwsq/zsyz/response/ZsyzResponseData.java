package cn.gtmap.realestate.exchange.core.dto.wwsq.zsyz.response;

import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-07-01
 * @description 证书验证 响应信息
 */
public class ZsyzResponseData {

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
    private Date fczfzsj;


    private String execmark;

    // 权利人名称
    private String qlrmc;

    // 权利人证件号码
    private String qlrzjhm;

    // 共有方式
    private String gyfs;

    // 证书ID
    private String certid;

    // 权利类型
    private String qllx;

    // 权利性质
    private String qlxz;

    // 权属状态
    private String qszt;
    //宗地代码
    private String zddm;
    //地籍区
    private String djq;
    //地籍子区
    private String djzq;

    private List<ZsyzDyxxResponseData> dyxx;

    private List<ZsyzCfxxResponseData> cfxx;

    public List<ZsyzDyxxResponseData> getDyxx() {
        return dyxx;
    }

    public void setDyxx(List<ZsyzDyxxResponseData> dyxx) {
        this.dyxx = dyxx;
    }

    public List<ZsyzCfxxResponseData> getCfxx() {
        return cfxx;
    }

    public void setCfxx(List<ZsyzCfxxResponseData> cfxx) {
        this.cfxx = cfxx;
    }

    /*//抵押信息
    private String dyxx;

    //查封信息
    private String cfxx;*/

    //权利其他状况
    private String qlqtzk;

   /* public String getDyxx() {
        return dyxx;
    }

    public void setDyxx(String dyxx) {
        this.dyxx = dyxx;
    }

    public String getCfxx() {
        return cfxx;
    }

    public void setCfxx(String cfxx) {
        this.cfxx = cfxx;
    }*/

    public String getQlqtzk() {
        return qlqtzk;
    }

    public void setQlqtzk(String qlqtzk) {
        this.qlqtzk = qlqtzk;
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

    public Date getFczfzsj() {
        return fczfzsj;
    }

    public void setFczfzsj(Date fczfzsj) {
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

    public String getCertid() {
        return certid;
    }

    public void setCertid(String certid) {
        this.certid = certid;
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

    public String getQszt() {
        return qszt;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
    }

    public String getZddm() {
        return zddm;
    }

    public void setZddm(String zddm) {
        this.zddm = zddm;
    }

    public String getDjq() {
        return djq;
    }

    public void setDjq(String djq) {
        this.djq = djq;
    }

    public String getDjzq() {
        return djzq;
    }

    public void setDjzq(String djzq) {
        this.djzq = djzq;
    }
}
