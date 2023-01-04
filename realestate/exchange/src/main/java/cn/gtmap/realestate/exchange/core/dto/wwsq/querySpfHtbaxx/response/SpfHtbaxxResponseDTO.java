package cn.gtmap.realestate.exchange.core.dto.wwsq.querySpfHtbaxx.response;

import java.util.List;

/**
 * @description 商品房合同备案信息响应对象
 * @author:  <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @date: 2022/10/26 15:59
 **/
public class SpfHtbaxxResponseDTO {

    /**
     * 合同备案号
     */
    private String bah;

    /**
     * 套内面积
     */
    private Double tnmj;

    /**
     * 备案日期
     */
    private String barq;

    /**
     * 交易价格
     */
    private Double jyjg;

    /**
     * 用途
     */
    private String yt;

    /**
     * 房屋结构
     */
    private String fwjg;

    /**
     * 小区名称
     */
    private String xqmc;

    /**
     * 房屋编码
     */
    private String fwbm;

    /**
     * 建筑面积
     */
    private String jzmj;

    /**
     * 坐落
     */
    private String zl;

    /**
     * 房间号
     */
    private String fjh;

    /**
     * ？
     */
    private String jydj;

    /**
     * 幢号
     */
    private String zrzh;

    /**
     * 所在层
     */
    private String szc;

    /**
     * 总层数
     */
    private String zcs;

    /**
     * 合同签订日期
     */
    private String htqdrq;

    /**
     * 不动产单元号
     */
    private String bdcdyh;

    /**
     * 行政区划所在代码
     */
    private String xzqhszdm;

    /**
     * 证书项目id
     */
    private String zsxmid;

    /**
     * 权利人信息
     */
    private List<SpfHtbaxxQlrxxDTO> qlr;

    public String getBah() {
        return bah;
    }

    public void setBah(String bah) {
        this.bah = bah;
    }

    public Double getTnmj() {
        return tnmj;
    }

    public void setTnmj(Double tnmj) {
        this.tnmj = tnmj;
    }

    public String getBarq() {
        return barq;
    }

    public void setBarq(String barq) {
        this.barq = barq;
    }

    public Double getJyjg() {
        return jyjg;
    }

    public void setJyjg(Double jyjg) {
        this.jyjg = jyjg;
    }

    public String getYt() {
        return yt;
    }

    public void setYt(String yt) {
        this.yt = yt;
    }

    public String getFwjg() {
        return fwjg;
    }

    public void setFwjg(String fwjg) {
        this.fwjg = fwjg;
    }

    public String getXqmc() {
        return xqmc;
    }

    public void setXqmc(String xqmc) {
        this.xqmc = xqmc;
    }

    public String getFwbm() {
        return fwbm;
    }

    public void setFwbm(String fwbm) {
        this.fwbm = fwbm;
    }

    public String getJzmj() {
        return jzmj;
    }

    public void setJzmj(String jzmj) {
        this.jzmj = jzmj;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getFjh() {
        return fjh;
    }

    public void setFjh(String fjh) {
        this.fjh = fjh;
    }

    public String getJydj() {
        return jydj;
    }

    public void setJydj(String jydj) {
        this.jydj = jydj;
    }

    public String getZrzh() {
        return zrzh;
    }

    public void setZrzh(String zrzh) {
        this.zrzh = zrzh;
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

    public String getHtqdrq() {
        return htqdrq;
    }

    public void setHtqdrq(String htqdrq) {
        this.htqdrq = htqdrq;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getXzqhszdm() {
        return xzqhszdm;
    }

    public void setXzqhszdm(String xzqhszdm) {
        this.xzqhszdm = xzqhszdm;
    }

    public String getZsxmid() {
        return zsxmid;
    }

    public void setZsxmid(String zsxmid) {
        this.zsxmid = zsxmid;
    }

    public List<SpfHtbaxxQlrxxDTO> getQlr() {
        return qlr;
    }

    public void setQlr(List<SpfHtbaxxQlrxxDTO> qlr) {
        this.qlr = qlr;
    }

    @Override
    public String toString() {
        return "SpfHtbaxxResponseDTO{" +
                "bah='" + bah + '\'' +
                ", tnmj=" + tnmj +
                ", barq='" + barq + '\'' +
                ", jyjg=" + jyjg +
                ", yt='" + yt + '\'' +
                ", fwjg='" + fwjg + '\'' +
                ", xqmc='" + xqmc + '\'' +
                ", fwbm='" + fwbm + '\'' +
                ", jzmj='" + jzmj + '\'' +
                ", zl='" + zl + '\'' +
                ", fjh='" + fjh + '\'' +
                ", jydj='" + jydj + '\'' +
                ", zrzh='" + zrzh + '\'' +
                ", szc='" + szc + '\'' +
                ", zcs='" + zcs + '\'' +
                ", htqdrq='" + htqdrq + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", xzqhszdm='" + xzqhszdm + '\'' +
                ", zsxmid='" + zsxmid + '\'' +
                ", qlr=" + qlr +
                '}';
    }
}
