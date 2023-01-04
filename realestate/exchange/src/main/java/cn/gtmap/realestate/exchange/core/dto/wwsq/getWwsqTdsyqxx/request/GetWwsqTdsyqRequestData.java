package cn.gtmap.realestate.exchange.core.dto.wwsq.getWwsqTdsyqxx.request;

import cn.gtmap.realestate.exchange.core.bo.anno.IgnoreCast;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-30
 * @description
 */
@IgnoreCast
public class GetWwsqTdsyqRequestData {

    // gxrmc 与 xmid 必填
    private String gxrmc;

    private String gxrzjh;

    // gxrmc 与 xmid 必填
    private String gxrmcmh;

    private String gxrzjhmh;

    // 模糊
    private String bdcdyh;

    // 模糊
    private String zl;

    private String cqzh;

    private String cqzhjc;

    // gxrmc 与 xmid 必填
    private String xmid;
    // 常州 精确查询坐落
    private String zljq;

    private String bdcdybh;

    //36730 【盐城】查询土地使用权接口增加土地证模糊查询参数
    private String cqzhmh;

    public String getGxrmcmh() {
        return gxrmcmh;
    }

    public void setGxrmcmh(String gxrmcmh) {
        this.gxrmcmh = gxrmcmh;
    }

    public String getGxrzjhmh() {
        return gxrzjhmh;
    }

    public void setGxrzjhmh(String gxrzjhmh) {
        this.gxrzjhmh = gxrzjhmh;
    }

    public String getCqzhmh() {
        return cqzhmh;
    }

    public void setCqzhmh(String cqzhmh) {
        this.cqzhmh = cqzhmh;
    }

    public String getBdcdybh() {
        return bdcdybh;
    }

    public void setBdcdybh(String bdcdybh) {
        this.bdcdybh = bdcdybh;
    }

    public String getZljq() {
        return zljq;
    }

    public void setZljq(String zljq) {
        this.zljq = zljq;
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

    public String getCqzh() {
        return cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
    }

    public String getCqzhjc() {
        return cqzhjc;
    }

    public void setCqzhjc(String cqzhjc) {
        this.cqzhjc = cqzhjc;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }
}
