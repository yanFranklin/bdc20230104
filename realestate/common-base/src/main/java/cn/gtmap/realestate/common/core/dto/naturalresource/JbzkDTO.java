package cn.gtmap.realestate.common.core.dto.naturalresource;

import cn.gtmap.realestate.common.core.domain.naturalresource.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
@EqualsAndHashCode
public class JbzkDTO implements Serializable {
    private static final long serialVersionUID = -8766465584426388953L;
    //基本状况
    private String dcjg;

    private String dcsj;

    private String zrzydjdyh;

    private String djdylx;

    private String mc;

    private String dyxxb;

    private String ysdm;

    private String zl;

    private String dyszb;

    private String dyszd;

    private String dyszn;

    private String dyszx;

    private String zrzydjdcb;

    private String zrzydjdcbbh;

    private String zrzydjdyhzhsl;

    private BigDecimal djdyzmj;

    private BigDecimal gymj;

    private BigDecimal jtmj;

    private BigDecimal zyqmj;

    private String xmqy;

    private String ssqdm;

    //权属
    private String dbxszt;

    private String dlxszt;

    private String syqzt;

    private String qlxsfs;

    private String xsnr;

    private String djzt;

    private String djjg;

    private Date dbsj;

    private String dbr;

    //自然状况
    private String bz;

    private BigDecimal cymj;

    private BigDecimal fzrzyzmj;

    private BigDecimal hdmj;

    private BigDecimal qtmj;

    private BigDecimal sdmj;

    private BigDecimal slmj;

    private BigDecimal szymj;

    private BigDecimal dynzrzyzmj;

    /**
     * 公共管制关联信息属性结构描
     */
    List<GggzglxxDO> gggzglxxDOList;
    /**
     * 不动产权利关联信息属性结构
     */
    List<BdcqlglxxDO> bdcqlglxxDOList;
    /**
     * 矿业权关联信息属性结构
     */
    List<KyqglxxDO> kyqglxxDOList;
    /**
     * 取水权关联信息属性结构
     */
    List<QsqglxxDO> qsqglxxDOList;
    /**
     * 排污权关联信息属性结构
     */
    List<PwqglxxDO> pwqglxxDOList;

    /***************自然状况*******************/
    /**
     * 水流状况信息表属性结构
     */
    List<SzyzkxxDO> szyzkxxDOList;
    /**
     * 湿地状况
     */
    List<SdzkxxDO> sdzkxxDOList;
    /**
     * 森林状况
     */
    List<SlzkxxDO> slzkxxDOList;

    /**
     * 海域状况
     */
    List<HyzkxxDO> hyzkxxDOList;

    /**
     * 草原状况
     */
    List<CyzkxxDO> cyzkxxDOList;
    /**
     * 荒地状况
     */
    List<HdzkxxDO> hdzkxxDOList;

    /**
     * 无居民海岛状况
     */
    List<WjmhdzkxxDO> wjmhdzkxxDOList;


    /**
     * 探明储量矿产资源状况
     */
    List<TmclkczyzkxxDO> tmclkczyzkxxDOList;

    public String getDcjg() {
        return dcjg;
    }

    public void setDcjg(String dcjg) {
        this.dcjg = dcjg;
    }

    public String getDcsj() {
        return dcsj;
    }

    public void setDcsj(String dcsj) {
        this.dcsj = dcsj;
    }

    public String getZrzydjdyh() {
        return zrzydjdyh;
    }

    public void setZrzydjdyh(String zrzydjdyh) {
        this.zrzydjdyh = zrzydjdyh;
    }

    public String getDjdylx() {
        return djdylx;
    }

    public void setDjdylx(String djdylx) {
        this.djdylx = djdylx;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getDyxxb() {
        return dyxxb;
    }

    public void setDyxxb(String dyxxb) {
        this.dyxxb = dyxxb;
    }

    public String getYsdm() {
        return ysdm;
    }

    public void setYsdm(String ysdm) {
        this.ysdm = ysdm;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getDyszb() {
        return dyszb;
    }

    public void setDyszb(String dyszb) {
        this.dyszb = dyszb;
    }

    public String getDyszd() {
        return dyszd;
    }

    public void setDyszd(String dyszd) {
        this.dyszd = dyszd;
    }

    public String getDyszn() {
        return dyszn;
    }

    public void setDyszn(String dyszn) {
        this.dyszn = dyszn;
    }

    public String getDyszx() {
        return dyszx;
    }

    public void setDyszx(String dyszx) {
        this.dyszx = dyszx;
    }

    public String getZrzydjdcb() {
        return zrzydjdcb;
    }

    public void setZrzydjdcb(String zrzydjdcb) {
        this.zrzydjdcb = zrzydjdcb;
    }

    public String getZrzydjdcbbh() {
        return zrzydjdcbbh;
    }

    public void setZrzydjdcbbh(String zrzydjdcbbh) {
        this.zrzydjdcbbh = zrzydjdcbbh;
    }

    public String getZrzydjdyhzhsl() {
        return zrzydjdyhzhsl;
    }

    public void setZrzydjdyhzhsl(String zrzydjdyhzhsl) {
        this.zrzydjdyhzhsl = zrzydjdyhzhsl;
    }

    public BigDecimal getDjdyzmj() {
        return djdyzmj;
    }

    public void setDjdyzmj(BigDecimal djdyzmj) {
        this.djdyzmj = djdyzmj;
    }

    public BigDecimal getGymj() {
        return gymj;
    }

    public void setGymj(BigDecimal gymj) {
        this.gymj = gymj;
    }

    public BigDecimal getJtmj() {
        return jtmj;
    }

    public void setJtmj(BigDecimal jtmj) {
        this.jtmj = jtmj;
    }

    public BigDecimal getZyqmj() {
        return zyqmj;
    }

    public void setZyqmj(BigDecimal zyqmj) {
        this.zyqmj = zyqmj;
    }

    public String getXmqy() {
        return xmqy;
    }

    public void setXmqy(String xmqy) {
        this.xmqy = xmqy;
    }

    public String getSsqdm() {
        return ssqdm;
    }

    public void setSsqdm(String ssqdm) {
        this.ssqdm = ssqdm;
    }

    public String getDbxszt() {
        return dbxszt;
    }

    public void setDbxszt(String dbxszt) {
        this.dbxszt = dbxszt;
    }

    public String getDlxszt() {
        return dlxszt;
    }

    public void setDlxszt(String dlxszt) {
        this.dlxszt = dlxszt;
    }

    public String getSyqzt() {
        return syqzt;
    }

    public void setSyqzt(String syqzt) {
        this.syqzt = syqzt;
    }

    public String getQlxsfs() {
        return qlxsfs;
    }

    public void setQlxsfs(String qlxsfs) {
        this.qlxsfs = qlxsfs;
    }

    public String getXsnr() {
        return xsnr;
    }

    public void setXsnr(String xsnr) {
        this.xsnr = xsnr;
    }

    public String getDjzt() {
        return djzt;
    }

    public void setDjzt(String djzt) {
        this.djzt = djzt;
    }

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    public Date getDbsj() {
        return dbsj;
    }

    public void setDbsj(Date dbsj) {
        this.dbsj = dbsj;
    }

    public String getDbr() {
        return dbr;
    }

    public void setDbr(String dbr) {
        this.dbr = dbr;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public BigDecimal getCymj() {
        return cymj;
    }

    public void setCymj(BigDecimal cymj) {
        this.cymj = cymj;
    }

    public BigDecimal getFzrzyzmj() {
        return fzrzyzmj;
    }

    public void setFzrzyzmj(BigDecimal fzrzyzmj) {
        this.fzrzyzmj = fzrzyzmj;
    }

    public BigDecimal getHdmj() {
        return hdmj;
    }

    public void setHdmj(BigDecimal hdmj) {
        this.hdmj = hdmj;
    }

    public BigDecimal getQtmj() {
        return qtmj;
    }

    public void setQtmj(BigDecimal qtmj) {
        this.qtmj = qtmj;
    }

    public BigDecimal getSdmj() {
        return sdmj;
    }

    public void setSdmj(BigDecimal sdmj) {
        this.sdmj = sdmj;
    }

    public BigDecimal getSlmj() {
        return slmj;
    }

    public void setSlmj(BigDecimal slmj) {
        this.slmj = slmj;
    }

    public BigDecimal getSzymj() {
        return szymj;
    }

    public void setSzymj(BigDecimal szymj) {
        this.szymj = szymj;
    }

    public BigDecimal getDynzrzyzmj() {
        return dynzrzyzmj;
    }

    public void setDynzrzyzmj(BigDecimal dynzrzyzmj) {
        this.dynzrzyzmj = dynzrzyzmj;
    }

    public List<GggzglxxDO> getGggzglxxDOList() {
        return gggzglxxDOList;
    }

    public void setGggzglxxDOList(List<GggzglxxDO> gggzglxxDOList) {
        this.gggzglxxDOList = gggzglxxDOList;
    }

    public List<BdcqlglxxDO> getBdcqlglxxDOList() {
        return bdcqlglxxDOList;
    }

    public void setBdcqlglxxDOList(List<BdcqlglxxDO> bdcqlglxxDOList) {
        this.bdcqlglxxDOList = bdcqlglxxDOList;
    }

    public List<KyqglxxDO> getKyqglxxDOList() {
        return kyqglxxDOList;
    }

    public void setKyqglxxDOList(List<KyqglxxDO> kyqglxxDOList) {
        this.kyqglxxDOList = kyqglxxDOList;
    }

    public List<QsqglxxDO> getQsqglxxDOList() {
        return qsqglxxDOList;
    }

    public void setQsqglxxDOList(List<QsqglxxDO> qsqglxxDOList) {
        this.qsqglxxDOList = qsqglxxDOList;
    }

    public List<PwqglxxDO> getPwqglxxDOList() {
        return pwqglxxDOList;
    }

    public void setPwqglxxDOList(List<PwqglxxDO> pwqglxxDOList) {
        this.pwqglxxDOList = pwqglxxDOList;
    }

    public List<SzyzkxxDO> getSzyzkxxDOList() {
        return szyzkxxDOList;
    }

    public void setSzyzkxxDOList(List<SzyzkxxDO> szyzkxxDOList) {
        this.szyzkxxDOList = szyzkxxDOList;
    }

    public List<SdzkxxDO> getSdzkxxDOList() {
        return sdzkxxDOList;
    }

    public void setSdzkxxDOList(List<SdzkxxDO> sdzkxxDOList) {
        this.sdzkxxDOList = sdzkxxDOList;
    }

    public List<SlzkxxDO> getSlzkxxDOList() {
        return slzkxxDOList;
    }

    public void setSlzkxxDOList(List<SlzkxxDO> slzkxxDOList) {
        this.slzkxxDOList = slzkxxDOList;
    }

    public List<CyzkxxDO> getCyzkxxDOList() {
        return cyzkxxDOList;
    }

    public void setCyzkxxDOList(List<CyzkxxDO> cyzkxxDOList) {
        this.cyzkxxDOList = cyzkxxDOList;
    }

    public List<HdzkxxDO> getHdzkxxDOList() {
        return hdzkxxDOList;
    }

    public void setHdzkxxDOList(List<HdzkxxDO> hdzkxxDOList) {
        this.hdzkxxDOList = hdzkxxDOList;
    }

    public List<WjmhdzkxxDO> getWjmhdzkxxDOList() {
        return wjmhdzkxxDOList;
    }

    public void setWjmhdzkxxDOList(List<WjmhdzkxxDO> wjmhdzkxxDOList) {
        this.wjmhdzkxxDOList = wjmhdzkxxDOList;
    }

    public List<TmclkczyzkxxDO> getTmclkczyzkxxDOList() {
        return tmclkczyzkxxDOList;
    }

    public void setTmclkczyzkxxDOList(List<TmclkczyzkxxDO> tmclkczyzkxxDOList) {
        this.tmclkczyzkxxDOList = tmclkczyzkxxDOList;
    }
}