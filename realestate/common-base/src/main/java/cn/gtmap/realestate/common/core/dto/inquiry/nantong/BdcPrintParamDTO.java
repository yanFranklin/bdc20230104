package cn.gtmap.realestate.common.core.dto.inquiry.nantong;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2021/01/26
 * @description 南通综合查询 利害关系人查询、律师查询 打印参数DTO
 */
public class BdcPrintParamDTO {
    /** 打印类型：lhgxrcx 利害关系人查询； lscx 律师查询 */
    private String dylx;

    /** 律师事务所 */
    private String lssws;

    /** 律师名称 */
    private String lsmc;

    /** 利害关系人 */
    private String lhgxr;

    /** 利害关系人证号 */
    private String lhgxrzjh;

    /** 所选记录 XMID */
    private List<String> xmids;

    /** register应用地址（二维码需要） */
    private String registerUi;

    /** 经办人 */
    private String jbr;

    /** 查询编号 */
    private String cxbh;

    /** 二维码地址 */
    private String ewmurl;

    /** 缓存xmid到临时参数表的名称 */
    private String xmidkey;


    public String getJbr() {
        return jbr;
    }

    public void setJbr(String jbr) {
        this.jbr = jbr;
    }

    public String getCxbh() {
        return cxbh;
    }

    public void setCxbh(String cxbh) {
        this.cxbh = cxbh;
    }

    public String getEwmurl() {
        return ewmurl;
    }

    public void setEwmurl(String ewmurl) {
        this.ewmurl = ewmurl;
    }

    public String getXmidkey() {
        return xmidkey;
    }

    public void setXmidkey(String xmidkey) {
        this.xmidkey = xmidkey;
    }

    public String getRegisterUi() {
        return registerUi;
    }

    public void setRegisterUi(String registerUi) {
        this.registerUi = registerUi;
    }

    public String getDylx() {
        return dylx;
    }

    public void setDylx(String dylx) {
        this.dylx = dylx;
    }

    public String getLssws() {
        return lssws;
    }

    public void setLssws(String lssws) {
        this.lssws = lssws;
    }

    public String getLsmc() {
        return lsmc;
    }

    public void setLsmc(String lsmc) {
        this.lsmc = lsmc;
    }

    public String getLhgxr() {
        return lhgxr;
    }

    public void setLhgxr(String lhgxr) {
        this.lhgxr = lhgxr;
    }

    public String getLhgxrzjh() {
        return lhgxrzjh;
    }

    public void setLhgxrzjh(String lhgxrzjh) {
        this.lhgxrzjh = lhgxrzjh;
    }

    public List<String> getXmids() {
        return xmids;
    }

    public void setXmids(List<String> xmids) {
        this.xmids = xmids;
    }

    @Override
    public String toString() {
        return "BdcPrintParamDTO{" +
                "dylx='" + dylx + '\'' +
                ", lssws='" + lssws + '\'' +
                ", lsmc='" + lsmc + '\'' +
                ", lhgxr='" + lhgxr + '\'' +
                ", lhgxrzjh='" + lhgxrzjh + '\'' +
                ", xmids=" + xmids +
                '}';
    }
}
