package cn.gtmap.realestate.common.core.dto.inquiry;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/09/05
 * @description 查询子系统：房产证明DTO （主要用于南通房产证明）
 */
public class BdcFczmDTO {
    /**
     * 编号
     */
    private String bh;
    /**
     * 查询申请人
     */
    private String cxsqr;
    /**
     * 身份证号
     */
    private String sfzh;
    /**
     * 查询时点
     */
    private String cxsd;
    /**
     * 证明内容
     */
    private String zmnr;
    /**
     * 证明时间
     */
    private String zmsj;
    /**
     * 经办人
     */
    private String jbr;

    /**
     * 证明类型：yfwfzm 有房无房证明  qhkzm 迁户口证明
     */
    private String type;

    private String dyldTime;

    public String getDyldTime() {
        return dyldTime;
    }

    public void setDyldTime(String dyldTime) {
        this.dyldTime = dyldTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJbr() {
        return jbr;
    }

    public void setJbr(String jbr) {
        this.jbr = jbr;
    }

    public String getBh() {
        return bh;
    }

    public void setBh(String bh) {
        this.bh = bh;
    }

    public String getCxsqr() {
        return cxsqr;
    }

    public void setCxsqr(String cxsqr) {
        this.cxsqr = cxsqr;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getZmnr() {
        return zmnr;
    }

    public void setZmnr(String zmnr) {
        this.zmnr = zmnr;
    }

    public String getZmsj() {
        return zmsj;
    }

    public void setZmsj(String zmsj) {
        this.zmsj = zmsj;
    }

    public String getCxsd() {
        return cxsd;
    }

    public void setCxsd(String cxsd) {
        this.cxsd = cxsd;
    }

    @Override
    public String toString() {
        return "BdcFczmDTO{" +
                "bh='" + bh + '\'' +
                ", cxsqr='" + cxsqr + '\'' +
                ", sfzh='" + sfzh + '\'' +
                ", cxsd='" + cxsd + '\'' +
                ", zmnr='" + zmnr + '\'' +
                ", zmsj='" + zmsj + '\'' +
                ", jbr='" + jbr + '\'' +
                ", dyldTime='" + dyldTime + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
