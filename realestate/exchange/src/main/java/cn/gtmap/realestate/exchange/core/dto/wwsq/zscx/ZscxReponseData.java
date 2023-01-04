package cn.gtmap.realestate.exchange.core.dto.wwsq.zscx;

/**
 * 证书查询响应信息
 *
 * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
 * @version 下午2:36 2021/3/1
 */
public class ZscxReponseData {
    //编号
    private String bh;
    //证明权利或事项
    private String zmqlhsx;
    //权利人(申请人）
    private String qlr;
    //义务人
    private String ywr;
    //坐落
    private String zl;
    //不动产单元号
    private String bdcdyh;
    //其他（权利其他状况）
    private String qlqtzk;
    //附记
    private String fj;
    //证书类型
    private Integer zslx;
    //共有情况
    private String gyqkmc;
    //权利类型
    private String qllxmc;
    //权利性质
    private String qlxzmc;
    //用途
    private String ytmc;
    //面积
    private String mj;
    //使用期限
    private String syqx;

    public String getBh() {
        return bh;
    }

    public void setBh(String bh) {
        this.bh = bh;
    }

    public String getZmqlhsx() {
        return zmqlhsx;
    }

    public void setZmqlhsx(String zmqlhsx) {
        this.zmqlhsx = zmqlhsx;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getYwr() {
        return ywr;
    }

    public void setYwr(String ywr) {
        this.ywr = ywr;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getQlqtzk() {
        return qlqtzk;
    }

    public void setQlqtzk(String qlqtzk) {
        this.qlqtzk = qlqtzk;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public Integer getZslx() {
        return zslx;
    }

    public void setZslx(Integer zslx) {
        this.zslx = zslx;
    }

    public String getGyqkmc() {
        return gyqkmc;
    }

    public void setGyqkmc(String gyqkmc) {
        this.gyqkmc = gyqkmc;
    }

    public String getQllxmc() {
        return qllxmc;
    }

    public void setQllxmc(String qllxmc) {
        this.qllxmc = qllxmc;
    }

    public String getQlxzmc() {
        return qlxzmc;
    }

    public void setQlxzmc(String qlxzmc) {
        this.qlxzmc = qlxzmc;
    }

    public String getYtmc() {
        return ytmc;
    }

    public void setYtmc(String ytmc) {
        this.ytmc = ytmc;
    }

    public String getMj() {
        return mj;
    }

    public void setMj(String mj) {
        this.mj = mj;
    }

    public String getSyqx() {
        return syqx;
    }

    public void setSyqx(String syqx) {
        this.syqx = syqx;
    }

    @Override
    public String toString() {
        return "ZscxReponseData{" +
                "bh='" + bh + '\'' +
                ", zmqlhsx='" + zmqlhsx + '\'' +
                ", qlr='" + qlr + '\'' +
                ", ywr='" + ywr + '\'' +
                ", zl='" + zl + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", qlqtzk='" + qlqtzk + '\'' +
                ", fj='" + fj + '\'' +
                ", zslx='" + zslx + '\'' +
                ", gyqkmc='" + gyqkmc + '\'' +
                ", qllxmc='" + qllxmc + '\'' +
                ", qlxzmc='" + qlxzmc + '\'' +
                ", ytmc='" + ytmc + '\'' +
                ", mj='" + mj + '\'' +
                ", syqx='" + syqx + '\'' +
                '}';
    }


}
