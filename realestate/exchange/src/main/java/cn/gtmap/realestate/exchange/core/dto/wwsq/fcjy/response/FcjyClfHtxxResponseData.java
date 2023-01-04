package cn.gtmap.realestate.exchange.core.dto.wwsq.fcjy.response;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-26
 * @description 房产交易存量房合同信息
 */
public class FcjyClfHtxxResponseData {

    // 房产备案合同号
    private String fcbahth;

    // 坐落
    private String zl;

    // 交易价格
    private String jyjg;

    // 备案日期
    private String barq;
    // 付款方式
    private String fkfs;
    // 付款方式名称
    private String fkfsmc;

    // 合同签订日期
    private String htqdrq;

    // 关系人信息
    private List<FcjyClfHtxxResponseGxr> gxrxx;

    public String getFkfs() {
        return fkfs;
    }

    public void setFkfs(String fkfs) {
        this.fkfs = fkfs;
    }

    public String getFkfsmc() {
        return fkfsmc;
    }

    public void setFkfsmc(String fkfsmc) {
        this.fkfsmc = fkfsmc;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getJyjg() {
        return jyjg;
    }

    public void setJyjg(String jyjg) {
        this.jyjg = jyjg;
    }

    public List<FcjyClfHtxxResponseGxr> getGxrxx() {
        return gxrxx;
    }

    public void setGxrxx(List<FcjyClfHtxxResponseGxr> gxrxx) {
        this.gxrxx = gxrxx;
    }

    public String getBarq() {
        return barq;
    }

    public void setBarq(String barq) {
        this.barq = barq;
    }

    public String getHtqdrq() {
        return htqdrq;
    }

    public void setHtqdrq(String htqdrq) {
        this.htqdrq = htqdrq;
    }

    public String getFcbahth() {
        return fcbahth;
    }

    public void setFcbahth(String fcbahth) {
        this.fcbahth = fcbahth;
    }
}
