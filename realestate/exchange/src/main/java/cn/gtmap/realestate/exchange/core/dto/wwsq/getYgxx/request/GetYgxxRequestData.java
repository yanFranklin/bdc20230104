package cn.gtmap.realestate.exchange.core.dto.wwsq.getYgxx.request;

import cn.gtmap.realestate.exchange.core.bo.anno.IgnoreCast;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-14
 * @description
 */
@IgnoreCast(ignoreNum = 3)
public class GetYgxxRequestData {

    private String gxrmc;

    private String gxrzjh;
    private String ygzmh;
    private String bdcdyh;
    private String zl;
    private String xmid;

    //盐城需求，添加关系人名称模糊
    private String gxrmcmh;

    //盐城需求，添加坐落模糊查询
    private String zlmh;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  预告证明号简称
     */
    private String ygzmhjc;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  预告证明号模糊
     */
    private String ygzmhmh;

    public String getZlmh() {
        return zlmh;
    }

    public void setZlmh(String zlmh) {
        this.zlmh = zlmh;
    }

    public String getGxrmcmh() {
        return gxrmcmh;
    }

    public void setGxrmcmh(String gxrmcmh) {
        this.gxrmcmh = gxrmcmh;
    }

    public String getYgzmh() {
        return ygzmh;
    }

    public void setYgzmh(String ygzmh) {
        this.ygzmh = ygzmh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getGxrmc() {
        return gxrmc;
    }

    public void setGxrmc(String gxrmc) {
        this.gxrmc = gxrmc;
    }

    public String getGxrzjh() {
        return gxrzjh;
    }

    public void setGxrzjh(String gxrzjh) {
        this.gxrzjh = gxrzjh;
    }

    public String getYgzmhjc() {
        return ygzmhjc;
    }

    public void setYgzmhjc(String ygzmhjc) {
        this.ygzmhjc = ygzmhjc;
    }

    public String getYgzmhmh() {
        return ygzmhmh;
    }

    public void setYgzmhmh(String ygzmhmh) {
        this.ygzmhmh = ygzmhmh;
    }
}
