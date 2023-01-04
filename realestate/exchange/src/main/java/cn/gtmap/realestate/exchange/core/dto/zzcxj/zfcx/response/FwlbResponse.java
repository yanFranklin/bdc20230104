package cn.gtmap.realestate.exchange.core.dto.zzcxj.zfcx.response;

/**
 * @author <a href="mailto:huanghui@gtmap.cn">huanghui</a>
 * @version 1.0  2022-11-15
 * @description 房屋信息查询返回
 */
public class FwlbResponse {

    private String qlr;
    private String bdcdyh;
    private String zl;
    private String yt;
    private String mj;
    private String djsj;
    private String bdcqzh;
    private String zslx;
    private String qlzt;
    private String qszt;

    public String getZl() { return zl; }

    public void setZl(String zl) { this.zl = zl; }

    public String getYt() { return yt; }

    public void setYt(String yt) { this.yt = yt; }

    public String getZslx() { return zslx; }

    public void setZslx(String zslx) { this.zslx = zslx; }

    public String getQlzt() { return qlzt; }

    public void setQlzt(String qlzt) { this.qlzt = qlzt; }

    public String getQszt() {
        return qszt;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
    }

    public String getDjsj() {
        return djsj;
    }

    public void setDjsj(String djsj) {
        this.djsj = djsj;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getMj() {
        return mj;
    }

    public void setMj(String mj) {
        this.mj = mj;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    @Override
    public String toString() {
        return "ZfcxResponseFw{" +
                ", zl='" + zl + '\'' +
                ", yt='" + yt + '\'' +
                ", zslx='" + zslx + '\'' +
                ", qlzt='" + qlzt + '\'' +
                ", qszt='" + qszt + '\'' +
                ", djsj='" + djsj + '\'' +
                ", qlr='" + qlr + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", mj='" + mj + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                '}';
    }
}
