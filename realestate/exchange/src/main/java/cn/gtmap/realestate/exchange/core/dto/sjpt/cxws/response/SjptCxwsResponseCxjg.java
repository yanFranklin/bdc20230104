package cn.gtmap.realestate.exchange.core.dto.sjpt.cxws.response;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-21
 * @description
 */
public class SjptCxwsResponseCxjg {

    // 序号
    private String xh;

    // 文件名称
    private String wjmc;

    // 文件类型
    private String wjlx;

    // 文书内容
    private String wsnr;

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getWjmc() {
        return wjmc;
    }

    public void setWjmc(String wjmc) {
        this.wjmc = wjmc;
    }

    public String getWjlx() {
        return wjlx;
    }

    public void setWjlx(String wjlx) {
        this.wjlx = wjlx;
    }

    public String getWsnr() {
        return wsnr;
    }

    public void setWsnr(String wsnr) {
        this.wsnr = wsnr;
    }
}
