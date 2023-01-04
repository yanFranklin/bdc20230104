package cn.gtmap.realestate.exchange.core.dto.zzcxj.dbxxcx.response;

import cn.gtmap.realestate.exchange.core.dto.zzcxj.fwqlcx.response.FwqlCxResponseFwxx;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.fwqlcx.response.FwqlCxResponseYgxx;
import lombok.Data;

import java.util.List;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/5/6.
 * @description
 */
public class DbxxCxResponseQl {
    private String xh;
    private String qlr;
    private String sfzh;
//    private String fjh;
    private String fwzl;
    private String qlbsm;
    private String hth;
    private String fwyt;
    private String qllx;
    private String qlxz;
    private String qszt;
    private String xzzt;
    private String bdcdyh;
    private String cdsj;
    private String djsj;
    private String zyzxsj;
    private String bdclx;
    private String tdsyqx;
    private String gyfs;
    /**
     * 	总层数/所在层数
     */
    private String cs;

    private String fwjg;

    private String mj;

    /**
     * 	宗地面积/建筑面积
     */
    private String jzmj;
    /**
     * 	宗地面积
     */
    private String zdmj;
    private String ftmj;
    private String dymj;
    /**
     * 土地用途
     */
    private String tdyt;

    private String bz;

    private String fj;


    private List<DbxxCxResponseFwxx> fwfzxxlist;
    private List<DbxxCxResponseQlrxx> qlrxxlist;
    private List<DbxxCxResponseDyaqxx> dyaqxxlist;
    private List<DbxxCxResponseCfxx> cfxxlist;

    private List<FwqlCxResponseYgxx> ygxxlist;

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getFwzl() {
        return fwzl;
    }

    public void setFwzl(String fwzl) {
        this.fwzl = fwzl;
    }

    public String getQlbsm() {
        return qlbsm;
    }

    public void setQlbsm(String qlbsm) {
        this.qlbsm = qlbsm;
    }

    public String getHth() {
        return hth;
    }

    public void setHth(String hth) {
        this.hth = hth;
    }

    public String getFwyt() {
        return fwyt;
    }

    public void setFwyt(String fwyt) {
        this.fwyt = fwyt;
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

    public String getXzzt() {
        return xzzt;
    }

    public void setXzzt(String xzzt) {
        this.xzzt = xzzt;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getCdsj() {
        return cdsj;
    }

    public void setCdsj(String cdsj) {
        this.cdsj = cdsj;
    }

    public String getDjsj() {
        return djsj;
    }

    public void setDjsj(String djsj) {
        this.djsj = djsj;
    }

    public String getZyzxsj() {
        return zyzxsj;
    }

    public void setZyzxsj(String zyzxsj) {
        this.zyzxsj = zyzxsj;
    }

    public String getBdclx() {
        return bdclx;
    }

    public void setBdclx(String bdclx) {
        this.bdclx = bdclx;
    }

    public String getTdsyqx() {
        return tdsyqx;
    }

    public void setTdsyqx(String tdsyqx) {
        this.tdsyqx = tdsyqx;
    }

    public String getGyfs() {
        return gyfs;
    }

    public void setGyfs(String gyfs) {
        this.gyfs = gyfs;
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }

    public String getFwjg() {
        return fwjg;
    }

    public void setFwjg(String fwjg) {
        this.fwjg = fwjg;
    }

    public String getMj() {
        return mj;
    }

    public void setMj(String mj) {
        this.mj = mj;
    }

    public String getJzmj() {
        return jzmj;
    }

    public void setJzmj(String jzmj) {
        this.jzmj = jzmj;
    }

    public String getZdmj() {
        return zdmj;
    }

    public void setZdmj(String zdmj) {
        this.zdmj = zdmj;
    }

    public String getFtmj() {
        return ftmj;
    }

    public void setFtmj(String ftmj) {
        this.ftmj = ftmj;
    }

    public String getDymj() {
        return dymj;
    }

    public void setDymj(String dymj) {
        this.dymj = dymj;
    }

    public String getTdyt() {
        return tdyt;
    }

    public void setTdyt(String tdyt) {
        this.tdyt = tdyt;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public List<DbxxCxResponseFwxx> getFwfzxxlist() {
        return fwfzxxlist;
    }

    public void setFwfzxxlist(List<DbxxCxResponseFwxx> fwfzxxlist) {
        this.fwfzxxlist = fwfzxxlist;
    }

    public List<DbxxCxResponseQlrxx> getQlrxxlist() {
        return qlrxxlist;
    }

    public void setQlrxxlist(List<DbxxCxResponseQlrxx> qlrxxlist) {
        this.qlrxxlist = qlrxxlist;
    }

    public List<DbxxCxResponseDyaqxx> getDyaqxxlist() {
        return dyaqxxlist;
    }

    public void setDyaqxxlist(List<DbxxCxResponseDyaqxx> dyaqxxlist) {
        this.dyaqxxlist = dyaqxxlist;
    }

    public List<DbxxCxResponseCfxx> getCfxxlist() {
        return cfxxlist;
    }

    public void setCfxxlist(List<DbxxCxResponseCfxx> cfxxlist) {
        this.cfxxlist = cfxxlist;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public List<FwqlCxResponseYgxx> getYgxxlist() {
        return ygxxlist;
    }

    public void setYgxxlist(List<FwqlCxResponseYgxx> ygxxlist) {
        this.ygxxlist = ygxxlist;
    }
}
