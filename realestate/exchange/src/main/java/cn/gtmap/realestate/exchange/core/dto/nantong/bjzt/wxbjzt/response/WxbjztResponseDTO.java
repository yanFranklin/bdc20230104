package cn.gtmap.realestate.exchange.core.dto.nantong.bjzt.wxbjzt.response;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-20
 * @description 微信办件状态响应结构
 */
public class WxbjztResponseDTO {

    // 查询到数据为 true 否则为false
    private String success;

    private String slbh;

    private String djlx;

    private String sqr;

    private String zl;

    // 办件状态 已受理、可领证、已办结
    private String bjzt;

    // 接件日期
    private String jjrq;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getDjlx() {
        return djlx;
    }

    public void setDjlx(String djlx) {
        this.djlx = djlx;
    }

    public String getSqr() {
        return sqr;
    }

    public void setSqr(String sqr) {
        this.sqr = sqr;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getBjzt() {
        return bjzt;
    }

    public void setBjzt(String bjzt) {
        this.bjzt = bjzt;
    }

    public String getJjrq() {
        return jjrq;
    }

    public void setJjrq(String jjrq) {
        this.jjrq = jjrq;
    }
}
