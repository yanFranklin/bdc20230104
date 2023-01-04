package cn.gtmap.realestate.inquiry.ui.core.dto;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/10/18
 * @description 不动产计算缮证时长DTO
 */
public class BdcCountSzscDTO {

    /**
     * 开始时间
     */
    private String kssj;
    /**
     * 项目ID
     */
    private String xmid;
    /**
     * 创建时间
     */
    private String cjsj;
    /**
     * 缮证时间
     */
    private String szsj;

    /**
     * 缮证时长
     */
    private Double szsc;

    public String getKssj() {
        return kssj;
    }

    public void setKssj(String kssj) {
        this.kssj = kssj;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getCjsj() {
        return cjsj;
    }

    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    public String getSzsj() {
        return szsj;
    }

    public void setSzsj(String szsj) {
        this.szsj = szsj;
    }

    public Double getSzsc() {
        return szsc;
    }

    public void setSzsc(Double szsc) {
        this.szsc = szsc;
    }
}
