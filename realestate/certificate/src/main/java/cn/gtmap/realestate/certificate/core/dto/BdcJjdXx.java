package cn.gtmap.realestate.certificate.core.dto;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description （海门）2.0系统中档案移交的的交接单信息实体（为了契合原有档案系统，3.0传输的数据格式不变）
 */
public class BdcJjdXx {
    private String jjdxxid;        // 交接单信息id
    private String jjdid;          // 关联的交接单ID
    private String slh;            // 受理号
    private String proid;          // 项目主键
    private String ywdjlx;         // 业务登记类型
    private String sqr;            // 申请人
    private String bdcdyh;         // 不动产单元描述
    private String bdcdyhs;        // 多个不动产单元
    private String zl;             // 坐落
    private String xl;             // 序号
    private String cqzh;           // 产权证号

    public String getJjdxxid() {
        return jjdxxid;
    }

    public void setJjdxxid(String jjdxxid) {
        this.jjdxxid = jjdxxid;
    }

    public String getSlh() {
        return slh;
    }

    public void setSlh(String slh) {
        this.slh = slh;
    }

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    public String getYwdjlx() {
        return ywdjlx;
    }

    public void setYwdjlx(String ywdjlx) {
        this.ywdjlx = ywdjlx;
    }

    public String getSqr() {
        return sqr;
    }

    public void setSqr(String sqr) {
        this.sqr = sqr;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getBdcdyhs() {
        return bdcdyhs;
    }

    public void setBdcdyhs(String bdcdyhs) {
        this.bdcdyhs = bdcdyhs;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getXl() {
        return xl;
    }

    public void setXl(String xl) {
        this.xl = xl;
    }

    public String getJjdid() {
        return jjdid;
    }

    public void setJjdid(String jjdid) {
        this.jjdid = jjdid;
    }

    public String getCqzh() {
        return cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
    }
}
