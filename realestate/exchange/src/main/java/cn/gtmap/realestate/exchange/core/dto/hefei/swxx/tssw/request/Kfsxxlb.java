package cn.gtmap.realestate.exchange.core.dto.hefei.swxx.tssw.request;
/*
 * @author <a href="mailto:huangzijian@gtmap.cn">huangzijian</a>
 * @version 1.0, 2018/11/28
 * @description 
 */
public class Kfsxxlb {
    // 不动产项目编号
    private String bdcxmbh;
    // 开发商名称
    private String kfsmc;
    // 开发商纳税人识别号
    private String kfsnsrsbh;

    public String getBdcxmbh() {
        return bdcxmbh;
    }

    public void setBdcxmbh(String bdcxmbh) {
        this.bdcxmbh = bdcxmbh;
    }

    public String getKfsmc() {
        return kfsmc;
    }

    public void setKfsmc(String kfsmc) {
        this.kfsmc = kfsmc;
    }

    public String getKfsnsrsbh() {
        return kfsnsrsbh;
    }

    public void setKfsnsrsbh(String kfsnsrsbh) {
        this.kfsnsrsbh = kfsnsrsbh;
    }
}
