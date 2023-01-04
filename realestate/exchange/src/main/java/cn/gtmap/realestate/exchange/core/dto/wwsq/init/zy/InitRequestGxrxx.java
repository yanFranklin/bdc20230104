package cn.gtmap.realestate.exchange.core.dto.wwsq.init.zy;

import cn.gtmap.realestate.exchange.core.bo.anno.IgnoreCast;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-11
 * @description 关系人实体
 */
@IgnoreCast(ignoreNum = 3)
public class InitRequestGxrxx {

    private String gxrmc;

    private String gxrsfzjzl;

    private String gxrzjh;

    private String gxrlx;

    private String qlbl;

    private String fwtc;

    // 是否本地户籍
    private String sfbdhj;

    // 婚姻状态
    private String hyzt;

    // 税号
    private String sh;

    private String gxrdlr;

    private String gxrdlrzjzl;

    private String gxrdlrzjh;

    private String gxrlxdh;

    // 税款减免
    private String skjm;

    private String gxrzldm;

    private String gxrzlmc;

    private String gyfs;

    private String gyfsmc;

    private String gxrdlrdh;

    private String sfzxqs;

    private String sxh;
    // 通讯地址
    private String txdz;
    // 是否持证人
    private Integer sfczr;
    // 是否领证人
    private Integer sflzr;

    private List<InitDlrDO> gxrdlrList;

    public List<InitDlrDO> getGxrdlrList() {
        return gxrdlrList;
    }

    public void setGxrdlrList(List<InitDlrDO> gxrdlrList) {
        this.gxrdlrList = gxrdlrList;
    }

    public String getSxh() {
        return sxh;
    }

    public void setSxh(String sxh) {
        this.sxh = sxh;
    }

    private List<InitRequestJtcy> jtcy;

    public String getSfzxqs() {
        return sfzxqs;
    }

    public void setSfzxqs(String sfzxqs) {
        this.sfzxqs = sfzxqs;
    }

    public String getGxrmc() {
        return gxrmc;
    }

    public void setGxrmc(String gxrmc) {
        this.gxrmc = gxrmc;
    }

    public String getGxrsfzjzl() {
        return gxrsfzjzl;
    }

    public void setGxrsfzjzl(String gxrsfzjzl) {
        this.gxrsfzjzl = gxrsfzjzl;
    }

    public String getGxrzjh() {
        return gxrzjh;
    }

    public void setGxrzjh(String gxrzjh) {
        this.gxrzjh = gxrzjh;
    }

    public String getGxrlx() {
        return gxrlx;
    }

    public void setGxrlx(String gxrlx) {
        this.gxrlx = gxrlx;
    }

    public String getQlbl() {
        return qlbl;
    }

    public void setQlbl(String qlbl) {
        this.qlbl = qlbl;
    }

    public String getFwtc() {
        return fwtc;
    }

    public void setFwtc(String fwtc) {
        this.fwtc = fwtc;
    }

    public String getSfbdhj() {
        return sfbdhj;
    }

    public void setSfbdhj(String sfbdhj) {
        this.sfbdhj = sfbdhj;
    }

    public String getHyzt() {
        return hyzt;
    }

    public void setHyzt(String hyzt) {
        this.hyzt = hyzt;
    }

    public String getSh() {
        return sh;
    }

    public void setSh(String sh) {
        this.sh = sh;
    }

    public String getGxrdlr() {
        return gxrdlr;
    }

    public void setGxrdlr(String gxrdlr) {
        this.gxrdlr = gxrdlr;
    }

    public String getGxrdlrzjzl() {
        return gxrdlrzjzl;
    }

    public void setGxrdlrzjzl(String gxrdlrzjzl) {
        this.gxrdlrzjzl = gxrdlrzjzl;
    }

    public String getGxrdlrzjh() {
        return gxrdlrzjh;
    }

    public void setGxrdlrzjh(String gxrdlrzjh) {
        this.gxrdlrzjh = gxrdlrzjh;
    }

    public String getGxrlxdh() {
        return gxrlxdh;
    }

    public void setGxrlxdh(String gxrlxdh) {
        this.gxrlxdh = gxrlxdh;
    }

    public String getSkjm() {
        return skjm;
    }

    public void setSkjm(String skjm) {
        this.skjm = skjm;
    }

    public List<InitRequestJtcy> getJtcy() {
        return jtcy;
    }

    public void setJtcy(List<InitRequestJtcy> jtcy) {
        this.jtcy = jtcy;
    }

    public String getGxrzldm() {
        return gxrzldm;
    }

    public void setGxrzldm(String gxrzldm) {
        this.gxrzldm = gxrzldm;
    }

    public String getGxrzlmc() {
        return gxrzlmc;
    }

    public void setGxrzlmc(String gxrzlmc) {
        this.gxrzlmc = gxrzlmc;
    }

    public String getGyfs() {
        return gyfs;
    }

    public void setGyfs(String gyfs) {
        this.gyfs = gyfs;
    }

    public String getGyfsmc() {
        return gyfsmc;
    }

    public void setGyfsmc(String gyfsmc) {
        this.gyfsmc = gyfsmc;
    }

    public String getGxrdlrdh() {
        return gxrdlrdh;
    }

    public void setGxrdlrdh(String gxrdlrdh) {
        this.gxrdlrdh = gxrdlrdh;
    }

    public String getTxdz() {
        return txdz;
    }

    public void setTxdz(String txdz) {
        this.txdz = txdz;
    }

    public Integer getSfczr() {
        return sfczr;
    }

    public void setSfczr(Integer sfczr) {
        this.sfczr = sfczr;
    }

    public Integer getSflzr() {
        return sflzr;
    }

    public void setSflzr(Integer sflzr) {
        this.sflzr = sflzr;
    }
}
