package cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.shuifeicx.request;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-18
 * @description 合肥  水费 查询 请求参数
 */
public class HefeiShuifeicxRequestDTO {

    // 用户代码
    private String yhdm;

    private String slbh;

    private String xmid;

    private String gzlslid;

    //第三方服务标识
    private String dsffwbs;

    public String getYhdm() {
        return yhdm;
    }

    public void setYhdm(String yhdm) {
        this.yhdm = yhdm;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getDsffwbs() {
        return dsffwbs;
    }

    public void setDsffwbs(String dsffwbs) {
        this.dsffwbs = dsffwbs;
    }
}

